import 'package:flutter_compass/flutter_compass.dart';
import 'package:hooks_riverpod/hooks_riverpod.dart';

class CompassService extends StateNotifier<double?> {
  CompassService() : super(null);

  static final provider = StateNotifierProvider<CompassService, double?>((ref) {
    final compassService = CompassService();
    compassService.trackHeading();
    return compassService;
  });

  void trackHeading() {
    FlutterCompass.events?.listen((event) {
      final heading = event.heading ?? 0;
      final bearing = heading < 0 ? 360 + heading : heading;
      if (((state ?? 0) - bearing).abs() > 1) {
        state = bearing;
      }
    });
  }
}
