import 'dart:math';

import 'package:fab_circular_menu/fab_circular_menu.dart';
import 'package:flutter/material.dart';

import '../map/map_view.dart';

class HomeView extends StatelessWidget {
  const HomeView({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
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
      body: MapView(
        initialZoom: Random().nextDouble(),
      ),
    );
  }
}
