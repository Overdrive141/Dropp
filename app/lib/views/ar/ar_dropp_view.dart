import 'dart:io';

import 'package:ar_flutter_plugin/ar_flutter_plugin.dart';
import 'package:ar_flutter_plugin/datatypes/config_planedetection.dart';
import 'package:ar_flutter_plugin/datatypes/hittest_result_types.dart';
import 'package:ar_flutter_plugin/datatypes/node_types.dart';
import 'package:ar_flutter_plugin/managers/ar_anchor_manager.dart';
import 'package:ar_flutter_plugin/managers/ar_location_manager.dart';
import 'package:ar_flutter_plugin/managers/ar_object_manager.dart';
import 'package:ar_flutter_plugin/managers/ar_session_manager.dart';
import 'package:ar_flutter_plugin/models/ar_anchor.dart';
import 'package:ar_flutter_plugin/models/ar_hittest_result.dart';
import 'package:ar_flutter_plugin/models/ar_node.dart';
import 'package:collection/collection.dart';
import 'package:dropp/assets.dart';
import 'package:dropp/colors.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:path_provider/path_provider.dart';
import 'package:vector_math/vector_math_64.dart' hide Colors;

class ARDroppView extends StatefulWidget {
  const ARDroppView({
    Key? key,
    this.onDone,
    this.model,
  }) : super(key: key);
  final VoidCallback? onDone;
  final String? model;

  @override
  _ARDroppViewState createState() => _ARDroppViewState();
}

class _ARDroppViewState extends State<ARDroppView> {
  late ARSessionManager arSessionManager;
  late ARObjectManager arObjectManager;
  late ARAnchorManager arAnchorManager;

  bool get isPreview => widget.model != null;

  List<ARNode> nodes = [];
  List<ARAnchor> anchors = [];

  int _currentOption = 0;
  ARNode get _currentNode => models[_currentOption];

  @override
  void initState() {
    loadModels();
    super.initState();
  }

  List<String> get options =>
      isPreview ? [widget.model!] : AppAssets.modelOptions;

  late List<ARNode> models = options
      .map((i) => ARNode(
            type: NodeType.fileSystemAppFolderGLB,
            uri: "$i.glb",
            scale: Vector3(0.2, 0.2, 0.2),
            position: Vector3(0.0, 0.0, 0.0),
            rotation: Vector4(1.0, 0.0, 0.0, 0.0),
          ))
      .toList();

  @override
  void dispose() {
    super.dispose();
    arSessionManager.dispose();
  }

  Future saveToAppDirectory(String path, String name) async {
    final imageBytes = await rootBundle.load('assets/models/$name.glb');
    final buffer = imageBytes.buffer;
    await File('$path/$name.glb').writeAsBytes(
      buffer.asUint8List(
        imageBytes.offsetInBytes,
        imageBytes.lengthInBytes,
      ),
    );
  }

  Future<void> loadModels() async {
    Directory appDocDir = await getApplicationDocumentsDirectory();
    String appDocPath = appDocDir.path;
    options.map((o) => saveToAppDirectory(appDocPath, o));
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      extendBodyBehindAppBar: true,
      appBar: AppBar(
        elevation: 0,
        backgroundColor: Colors.black38,
        title: Text(
          isPreview ? 'View dropp in AR' : 'Place a dropp in AR',
          style: const TextStyle(color: Colors.white),
        ),
        iconTheme: const IconThemeData(color: Colors.white),
        actions: isPreview
            ? null
            : [
                if (nodes.isNotEmpty)
                  GestureDetector(
                    child: const Icon(Icons.check),
                    onTap: () {
                      widget.onDone?.call();
                    },
                  ),
                const SizedBox(width: 16),
              ],
      ),
      body: Stack(
        children: [
          ARView(
            onARViewCreated: onARViewCreated,
            planeDetectionConfig: PlaneDetectionConfig.horizontalAndVertical,
          ),
          if (!isPreview)
            Align(
              alignment: FractionalOffset.bottomCenter,
              child: Padding(
                padding: const EdgeInsets.all(8.0),
                child: Column(
                  mainAxisAlignment: MainAxisAlignment.end,
                  children: [
                    Row(
                      children: [
                        ...options
                            .mapIndexed((index, i) => Flexible(
                                  child: GestureDetector(
                                    onTap: () {
                                      _currentOption = index;
                                      setState(() {});
                                    },
                                    child: AspectRatio(
                                      aspectRatio: 1,
                                      child: Container(
                                        clipBehavior: Clip.antiAlias,
                                        margin: const EdgeInsets.all(4),
                                        decoration: BoxDecoration(
                                          image: DecorationImage(
                                            image: AssetImage(
                                                'assets/models/$i.jpg'),
                                            fit: BoxFit.cover,
                                          ),
                                          border: Border.all(
                                              color: _currentOption == index
                                                  ? AppColors.primary
                                                  : Colors.grey,
                                              width: 2),
                                          borderRadius:
                                              BorderRadius.circular(8),
                                        ),
                                      ),
                                    ),
                                  ),
                                ))
                            .toList()
                      ],
                    ),
                  ],
                ),
              ),
            )
        ],
      ),
    );
  }

  void onARViewCreated(
    ARSessionManager arSessionManager,
    ARObjectManager arObjectManager,
    ARAnchorManager arAnchorManager,
    ARLocationManager arLocationManager,
  ) {
    this.arSessionManager = arSessionManager;
    this.arObjectManager = arObjectManager;
    this.arAnchorManager = arAnchorManager;

    this.arSessionManager.onInitialize(
          showFeaturePoints: false,
          showPlanes: true,
          customPlaneTexturePath: "assets/models/triangle.png",
          showWorldOrigin: true,
          handlePans: true,
          handleRotation: true,
        );
    this.arObjectManager.onInitialize();

    this.arSessionManager.onPlaneOrPointTap = onPlaneOrPointTapped;
  }

  Future<void> onPlaneOrPointTapped(
    List<ARHitTestResult> hitTestResults,
  ) async {
    var singleHitTestResult = hitTestResults.firstWhereOrNull(
        (hitTestResult) => hitTestResult.type == ARHitTestResultType.plane);
    if (singleHitTestResult != null) {
      await arObjectManager.removeNode(_currentNode);
      if (anchors.isNotEmpty) {
        await arAnchorManager.removeAnchor(anchors.first);
        anchors.clear();
      }
      var newAnchor = ARPlaneAnchor(
        transformation: singleHitTestResult.worldTransform,
      );

      bool didAddAnchor = await arAnchorManager.addAnchor(newAnchor) ?? false;
      if (didAddAnchor) {
        anchors.add(newAnchor);
        var newNode = models[_currentOption];
        bool didAddNodeToAnchor = await arObjectManager.addNode(
              newNode,
              planeAnchor: newAnchor,
            ) ??
            false;
        if (didAddNodeToAnchor) {
          nodes.clear();
          nodes.add(newNode);
          setState(() {});
        } else {
          arSessionManager.onError("Adding Node to Anchor failed");
        }
      } else {
        arSessionManager.onError("Adding Anchor failed");
      }
    }
  }
}
