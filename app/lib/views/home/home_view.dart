import 'package:dropp/colors.dart';
import 'package:dropp/services/permission_service.dart';
import 'package:fab_circular_menu/fab_circular_menu.dart';
import 'package:flutter/material.dart';
import 'package:hooks_riverpod/hooks_riverpod.dart';

import '../map/map_view.dart';

class HomeView extends ConsumerWidget {
  const HomeView({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context, WidgetRef ref) {
    final isLocationEnabled = ref.watch(PermissionService.provider);
    return Scaffold(
      floatingActionButton: FabCircularMenu(
        ringColor: Colors.blueGrey[300],
        fabColor: Colors.blueGrey,
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
          const Icon(Icons.text_fields),
          const Icon(Icons.image),
          const Icon(Icons.videocam_rounded),
          const Icon(Icons.android),
        ]
            .map((i) => IconButton(
                icon: i, color: Colors.white, iconSize: 36, onPressed: () {}))
            .toList(),
      ),
      body: Stack(
        children: [
          const Expanded(child: MapView()),
          if (!isLocationEnabled)
            const Positioned(
              top: 56,
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
