import 'package:dropp/colors.dart';

import 'package:dropp/services/permission_service.dart';
import 'package:dropp/views/settings/settings_view.dart';
import 'package:fab_circular_menu/fab_circular_menu.dart';
import 'package:flutter/material.dart';
import 'package:hooks_riverpod/hooks_riverpod.dart';

import '../../text_editor/text_editor_view.dart';
import '../ar/ar_dropp_view.dart';
import '../camera/camera_view.dart';
import '../map/map_view.dart';

class HomeView extends ConsumerWidget {
  const HomeView({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context, WidgetRef ref) {
    final isLocationEnabled = ref.watch(PermissionService.provider);
    return Scaffold(
      extendBodyBehindAppBar: true,
      appBar: AppBar(
        backgroundColor: Colors.transparent,
        elevation: 0,
        actions: [
          CircleAvatar(
            radius: 20,
            backgroundColor: Colors.black45,
            child: IconButton(
              icon: const Icon(Icons.emoji_events_outlined),
              color: Colors.white,
              onPressed: () {
                ScaffoldMessenger.of(context).showSnackBar(const SnackBar(
                  content: Text('Feature Coming Soon'),
                ));
              },
            ),
          ),
          const SizedBox(width: 10),
          CircleAvatar(
            radius: 20,
            backgroundColor: Colors.black45,
            child: IconButton(
              color: Colors.white,
              icon: const Icon(Icons.settings),
              onPressed: () {
                Navigator.push(
                  context,
                  MaterialPageRoute(
                    builder: (_) => const SettingsView(),
                  ),
                );
              },
            ),
          ),
          const SizedBox(width: 10)
        ],
      ),
      floatingActionButton: !isLocationEnabled
          ? null
          : FabCircularMenu(
              ringColor: Colors.blueGrey[300],
              fabColor: AppColors.primary,
              fabOpenColor: Colors.grey,
              fabOpenIcon: const Icon(
                Icons.pin_drop_rounded,
                color: Colors.white,
              ),
              fabCloseIcon: const Icon(
                Icons.cancel,
                color: Colors.white,
              ),
              children: <Widget>[
                  IconButton(
                      icon: const Icon(Icons.text_fields),
                      color: Colors.white,
                      iconSize: 36,
                      onPressed: () async {
                        Navigator.push(
                            context,
                            MaterialPageRoute(
                              builder: (context) => TextEditor(
                                onDone: (_) {
                                  Navigator.pop(context);
                                },
                              ),
                            ));
                      }),
                  IconButton(
                      icon: const Icon(Icons.image),
                      color: Colors.white,
                      iconSize: 36,
                      onPressed: () async {
                        final route = MaterialPageRoute(
                          fullscreenDialog: true,
                          builder: (_) => const CameraView(isVideo: false),
                        );
                        Navigator.push(context, route);
                      }),
                  IconButton(
                      icon: const Icon(Icons.videocam_rounded),
                      color: Colors.white,
                      iconSize: 36,
                      onPressed: () async {
                        final route = MaterialPageRoute(
                          fullscreenDialog: true,
                          builder: (_) => const CameraView(isVideo: true),
                        );
                        Navigator.push(context, route);
                      }),
                  IconButton(
                    icon: const Icon(Icons.android),
                    color: Colors.white,
                    iconSize: 36,
                    onPressed: () async {
                      Navigator.push(
                          context,
                          MaterialPageRoute(
                            builder: (context) => ARDroppView(
                              onDone: () {
                                Navigator.pop(context);
                              },
                            ),
                          ));
                    },
                  ),
                ]),
      body: Stack(
        children: [
          const MapView(),
          if (!isLocationEnabled)
            const Positioned(
              bottom: 56,
              left: 16,
              right: 16,
              child: LocationError(),
            ),
        ],
      ),
    );
  }
}

class LocationError extends StatelessWidget {
  const LocationError({
    Key? key,
  }) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Container(
      decoration: BoxDecoration(
        borderRadius: BorderRadius.circular(8),
        color: AppColors.primary,
      ),
      child: const ListTile(
        leading: Padding(
          padding: EdgeInsets.all(8.0),
          child: Icon(
            Icons.warning,
            color: Colors.white,
          ),
        ),
        horizontalTitleGap: 8,
        title: Text(
          'Location Required',
          style: TextStyle(fontWeight: FontWeight.bold, color: Colors.white),
        ),
        subtitle: Text(
          'Please enable location from device settings',
          style: TextStyle(color: Colors.white70),
        ),
      ),
    );
  }
}
