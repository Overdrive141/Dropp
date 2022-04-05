import 'dart:async';

import 'package:flutter_compass/flutter_compass.dart';
import 'package:hooks_riverpod/hooks_riverpod.dart';

class CompassService extends StateNotifier<double?> {
  CompassService() : super(null);

  StreamSubscription? _streamSubscription;

  static final provider =
      StateNotifierProvider.autoDispose<CompassService, double?>((ref) {
    final compassService = CompassService();
    compassService.trackHeading();
    return compassService;
  });

  void trackHeading() {
    _streamSubscription = FlutterCompass.events?.listen((event) {
      final heading = event.heading ?? 0;
      final bearing = heading < 0 ? 360 + heading : heading;
      if (((state ?? 0) - bearing).abs() > 1) {
        state = bearing;
      }
    });
  }

  @override
  void dispose() {
    _streamSubscription?.cancel();
    _streamSubscription = null;
    super.dispose();
  }
}
