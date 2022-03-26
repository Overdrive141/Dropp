import 'dart:math';

import 'package:camera/camera.dart';
import 'package:flutter/material.dart';

import 'image_preview.dart';
import 'video_preview.dart';

class VideoRecordingScreen extends StatefulWidget {
  final Function(String url)? onDone;
  const VideoRecordingScreen({
    Key? key,
    this.onDone,
  }) : super(key: key);

  @override
  _VideoRecordingScreenState createState() => _VideoRecordingScreenState();
}

class _VideoRecordingScreenState extends State<VideoRecordingScreen> {
  late CameraController _cameraController;
  late Future<void> cameraValue;
  bool isRecording = false;
  bool flash = false;
  bool iscamerafront = true;
  double transform = 0;
  List<CameraDescription> cameras = [];

  @override
  void initState() {
    super.initState();
    cameraValue = _init();
  }

  Future _init() async {
    cameras = await availableCameras();
    _cameraController = CameraController(
      cameras.firstWhere((c) => c.lensDirection == CameraLensDirection.back),
      ResolutionPreset.high,
    );
    await _cameraController.initialize();
  }

  @override
  void dispose() {
    super.dispose();
    _cameraController.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return WillPopScope(
      onWillPop: () async {
        Navigator.pop(context);
        Navigator.pop(context);
        return true;
      },
      child: Scaffold(
        backgroundColor: Colors.black,
        body: Stack(
          children: [
            FutureBuilder(
                future: cameraValue,
                builder: (context, snapshot) {
                  if (snapshot.connectionState == ConnectionState.done) {
                    return SizedBox(
                        width: MediaQuery.of(context).size.width,
                        height: MediaQuery.of(context).size.height,
                        child: CameraPreview(_cameraController));
                  } else {
                    return const Center(
                      child: CircularProgressIndicator(),
                    );
                  }
                }),
            Positioned(
              bottom: 0.0,
              child: Container(
                color: Colors.black,
                padding: const EdgeInsets.only(top: 5, bottom: 5),
                width: MediaQuery.of(context).size.width,
                child: Column(
                  children: [
                    Row(
                      mainAxisSize: MainAxisSize.max,
                      mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                      children: [
                        IconButton(
                            icon: Icon(
                              flash ? Icons.flash_on : Icons.flash_off,
                              color: Colors.white,
                              size: 28,
                            ),
                            onPressed: () {
                              setState(() {
                                flash = !flash;
                              });
                              flash
                                  ? _cameraController
                                      .setFlashMode(FlashMode.torch)
                                  : _cameraController
                                      .setFlashMode(FlashMode.off);
                            }),
                        GestureDetector(
                          onTap: () async {
                            if (isRecording) {
                              XFile videopath =
                                  await _cameraController.stopVideoRecording();
                              setState(() {
                                isRecording = false;
                              });
                              Navigator.push(
                                  context,
                                  MaterialPageRoute(
                                      builder: (builder) => VideoPreview(
                                          path: videopath.path,
                                          onDone: () {
                                            Navigator.pop(context);
                                            widget.onDone?.call(videopath.path);
                                            Navigator.pop(context);
                                          })));
                            } else {
                              await _cameraController.startVideoRecording();
                              setState(() {
                                isRecording = true;
                              });
                            }
                          },
                          child: isRecording
                              ? const Icon(
                                  Icons.radio_button_on,
                                  color: Colors.red,
                                  size: 80,
                                )
                              : const Icon(
                                  Icons.panorama_fish_eye,
                                  color: Colors.white,
                                  size: 70,
                                ),
                        ),
                        IconButton(
                            icon: Transform.rotate(
                              angle: transform,
                              child: const Icon(
                                Icons.flip_camera_ios,
                                color: Colors.white,
                                size: 28,
                              ),
                            ),
                            onPressed: () async {
                              if (isRecording) return;
                              setState(() {
                                iscamerafront = !iscamerafront;
                                transform = transform + pi;
                              });
                              int cameraPos = iscamerafront ? 0 : 1;
                              _cameraController = CameraController(
                                  cameras[cameraPos], ResolutionPreset.high);
                              cameraValue = _cameraController.initialize();
                            }),
                      ],
                    ),
                    const SizedBox(
                      height: 4,
                    ),
                    Text(
                      "Tap to ${isRecording ? 'stop' : 'start'} recording",
                      style: const TextStyle(
                        color: Colors.white,
                      ),
                      textAlign: TextAlign.center,
                    )
                  ],
                ),
              ),
            ),
          ],
        ),
      ),
    );
  }

  void takePhoto(BuildContext context) async {
    XFile file = await _cameraController.takePicture();
    Navigator.push(
        context,
        MaterialPageRoute(
            builder: (builder) => CameraViewPage(
                  path: file.path,
                )));
  }
}
