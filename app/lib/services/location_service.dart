import 'dart:math';

import 'package:background_location/background_location.dart';
import 'package:hooks_riverpod/hooks_riverpod.dart';

class LocationService extends StateNotifier<Location?> {
  LocationService() : super(null);

  static final provider =
      StateNotifierProvider<LocationService, Location?>((ref) {
    final locationService = LocationService();
    locationService.track();
    return locationService;
  });

  void track() {
    stopTracking();
    BackgroundLocation.startLocationService(distanceFilter: 20);
    BackgroundLocation.getLocationUpdates((location) {
      state = location;
    });
  }

  void stopTracking() {
    BackgroundLocation.stopLocationService();
  }

  // Return distance in meters.
  double? getDistanceFromCurrentLocation(double lat, double lng) {
    if (state != null) {
      return _calculateDistance(
        state!.latitude ?? 0,
        state!.longitude ?? 0,
        lat,
        lng,
      );
    }
    return null;
  }

  // Return distance between 2 set of coordinates in meters.
  double _calculateDistance(
      double lat1, double lon1, double lat2, double lon2) {
    var p = 0.017453292519943295;
    var c = cos;
    var a = 0.5 -
        c((lat2 - lat1) * p) / 2 +
        c(lat1 * p) * c(lat2 * p) * (1 - c((lon2 - lon1) * p)) / 2;
    return 1000 * 12742 * asin(sqrt(a));
  }
}
