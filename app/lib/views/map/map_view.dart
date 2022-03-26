import 'dart:math';
import 'dart:typed_data';
import 'dart:ui' as ui;

import 'package:background_location/background_location.dart';
import 'package:dropp/assets.dart';
import 'package:dropp/services/location_service.dart';
import 'package:dropp/services/user_service.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:google_maps_flutter/google_maps_flutter.dart';
import 'package:hooks_riverpod/hooks_riverpod.dart';

import '../../services/compass_service.dart';
import '../../services/permission_service.dart';
import '../dropp_view/story_view.dart';

class MapView extends ConsumerStatefulWidget {
  final bool enableLocationPicker;
  final Function(LatLng)? onLocationSelected;
  final Function(LatLng, double zoom)? onCameraMove;
  final LatLng? defaultLocation;
  final double? initialZoom;
  final LatLng? initialCenter;
  final Set<Marker>? markers;

  const MapView({
    Key? key,
    this.enableLocationPicker = false,
    this.onLocationSelected,
    this.markers,
    this.defaultLocation,
    this.onCameraMove,
    this.initialZoom,
    this.initialCenter,
  }) : super(key: key);

  @override
  MapViewState createState() => MapViewState();
}

class MapViewState extends ConsumerState<MapView> {
  GoogleMapController? _mapController;

  LatLng _currentPosition = _kdefaultCenter;

  static const _kdefaultCenter = LatLng(42.30554813345436, -83.06151891841292);
  static const double _kdefaultZoom = 18;
  static const double _kdefaultTilt = 90;
  static const _kdefaultZoomBounds = MinMaxZoomPreference(17, 20);

  Marker avatar = const Marker(
    markerId: MarkerId('avatar'),
    position: _kdefaultCenter,
  );

  void showContent() {
    Navigator.push(
      context,
      MaterialPageRoute(
        builder: (_) => const MoreStories(),
      ),
    );
  }

  Future<Uint8List> getBytesFromAsset(String path, int width) async {
    ByteData data = await rootBundle.load(path);
    ui.Codec codec = await ui.instantiateImageCodec(
      data.buffer.asUint8List(),
      targetWidth: width,
    );
    ui.FrameInfo fi = await codec.getNextFrame();
    return (await fi.image.toByteData(format: ui.ImageByteFormat.png))!
        .buffer
        .asUint8List();
  }

  Set<Marker> markers = {};

  @override
  initState() {
    _loadMarker();
    super.initState();
  }

  @override
  void dispose() {
    super.dispose();
  }

  void _onMapCreated(GoogleMapController controller) async {
    _mapController = controller;
    _mapController?.setMapStyle(_mapStyle);
  }

  Future _loadDropMarkers() async {
    final bytes = await getBytesFromAsset(AppAssets.dropMarker, 200);
    markers = List.filled(20, 0)
        .map((m) => Marker(
            onTap: showContent,
            icon: BitmapDescriptor.fromBytes(bytes),
            markerId: MarkerId(Random().nextInt(1000).toString()),
            position: LatLng(
                _kdefaultCenter.latitude + (Random().nextDouble() / 100),
                _kdefaultCenter.longitude + (Random().nextDouble() / 100))))
        .toSet();

    setState(() {});
  }

  Future _loadMarker() async {
    final image = ref.read(UserService.provider).avatarMarker;
    final bytes = await getBytesFromAsset(image, 150);
    avatar = avatar.copyWith(iconParam: BitmapDescriptor.fromBytes(bytes));
    await _loadDropMarkers();
    setState(() {});
  }

  Future _updateMarker() async {
    avatar = avatar.copyWith(positionParam: _currentPosition);
    setState(() {});
  }

  void _updateLocation(Location? oldLocation, Location? newLocation) async {
    if (newLocation != null) {
      final newPosition = LatLng(
        newLocation.latitude ?? _currentPosition.latitude,
        newLocation.longitude ?? _currentPosition.longitude,
      );
      if (newPosition == _currentPosition) {
        return;
      }
      _currentPosition = newPosition;
      await _updateMarker();
      _mapController?.animateCamera(CameraUpdate.newLatLng(_currentPosition));
    }
  }

  void _handleCompass(double? oldBearing, double? newBearing) {
    if (newBearing != null) {
      _mapController?.animateCamera(
        CameraUpdate.newCameraPosition(
          CameraPosition(
            target: _currentPosition,
            zoom: 18,
            tilt: 80,
            bearing: newBearing,
          ),
        ),
      );
    }
  }

  void _handleAvatarChange(UserService? _, UserService us) {
    _loadMarker();
  }

  @override
  Widget build(BuildContext context) {
    final isLocationEnabled = ref.watch(PermissionService.provider);

    ref.listen<Location?>(LocationService.provider, _updateLocation);
    ref.listen<double?>(CompassService.provider, _handleCompass);
    ref.listen<UserService>(UserService.provider, _handleAvatarChange);

    return GoogleMap(
      mapType: MapType.normal,
      initialCameraPosition: const CameraPosition(
        target: _kdefaultCenter,
        zoom: _kdefaultZoom,
        tilt: _kdefaultTilt,
      ),
      zoomControlsEnabled: false,
      compassEnabled: false,
      myLocationEnabled: false,
      myLocationButtonEnabled: false,
      onMapCreated: _onMapCreated,
      minMaxZoomPreference: _kdefaultZoomBounds,
      buildingsEnabled: true,
      indoorViewEnabled: false,
      mapToolbarEnabled: false,
      tiltGesturesEnabled: false,
      trafficEnabled: false,
      markers: {avatar}..addAll(!isLocationEnabled ? markers : {}),
    );
  }
}

const String _mapStyle = '''[
    {
        "featureType": "all",
        "elementType": "all",
        "stylers": [
            {
                "saturation": "32"
            },
            {
                "lightness": "-3"
            },
            {
                "visibility": "on"
            },
            {
                "weight": "1.18"
            }
        ]
    },
    {
        "featureType": "administrative",
        "elementType": "labels",
        "stylers": [
            {
                "visibility": "off"
            }
        ]
    },
    {
        "featureType": "landscape",
        "elementType": "labels",
        "stylers": [
            {
                "visibility": "off"
            }
        ]
    },
    {
        "featureType": "landscape.man_made",
        "elementType": "all",
        "stylers": [
            {
                "saturation": "-70"
            },
            {
                "lightness": "14"
            }
        ]
    },
    {
        "featureType": "poi",
        "elementType": "labels",
        "stylers": [
            {
                "visibility": "off"
            }
        ]
    },
    {
        "featureType": "road",
        "elementType": "labels",
        "stylers": [
            {
                "visibility": "off"
            }
        ]
    },
    {
        "featureType": "transit",
        "elementType": "labels",
        "stylers": [
            {
                "visibility": "off"
            }
        ]
    },
    {
        "featureType": "water",
        "elementType": "all",
        "stylers": [
            {
                "saturation": "100"
            },
            {
                "lightness": "-14"
            }
        ]
    },
    {
        "featureType": "water",
        "elementType": "labels",
        "stylers": [
            {
                "visibility": "off"
            },
            {
                "lightness": "12"
            }
        ]
    }
]''';
