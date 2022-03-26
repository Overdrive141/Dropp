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
}
