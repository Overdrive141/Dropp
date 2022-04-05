import 'dart:typed_data';
import 'dart:ui' as ui;

import 'package:background_location/background_location.dart';
import 'package:dropp/assets.dart';
import 'package:dropp/models/dropp.dart';
import 'package:dropp/models/enums.dart';
import 'package:dropp/services/api_service.dart';
import 'package:dropp/services/location_service.dart';
import 'package:dropp/services/user_service.dart';
import 'package:dropp/views/ar/ar_dropp_view.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:google_maps_flutter/google_maps_flutter.dart';
import 'package:hooks_riverpod/hooks_riverpod.dart';

import '../../services/compass_service.dart';
import '../../services/permission_service.dart';
import '../dropp_view/dropp_story_view.dart';
import 'map_style.dart';

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

  List<Dropp> dropps = [];

  void showContent(Dropp dropp) async {
    // final distance = ref
    //     .read(LocationService.provider.originProvider)
    //     .getDistanceFromCurrentLocation(dropp.lat, dropp.lng);
    // if (distance == null || distance > 100) {
    //   ScaffoldMessenger.of(context).clearSnackBars();
    //   ScaffoldMessenger.of(context).showSnackBar(const SnackBar(
    //     content: Text('Please move closer to the dropp'),
    //   ));
    // }
    if (dropp.type == DroppType.ar) {
      await Navigator.push(
        context,
        MaterialPageRoute(
          builder: (_) => ARDroppView(
            dropp: dropp,
          ),
        ),
      );
    } else {
      await Navigator.push(
        context,
        MaterialPageRoute(
          builder: (_) => DroppStoryView(
            dropp: dropp,
          ),
        ),
      );
    }
    dropp = dropp.copyWith(isSeen: true);
    setState(() {});
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

  @override
  initState() {
    _loadMarker();
    _loadDropMarkers();

    super.initState();
  }

  @override
  void dispose() {
    _mapController?.dispose();
    super.dispose();
  }

  Uint8List? droppMarkerBytes;

  Set<Marker> get markers {
    if (droppMarkerBytes != null) {
      return dropps
          .map(
            (m) => Marker(
              onTap: () => showContent(m),
              icon: BitmapDescriptor.fromBytes(droppMarkerBytes!),
              markerId: MarkerId(m.id),
              position: LatLng(m.lat, m.lng),
              alpha: m.isSeen ? 0.3 : 1,
            ),
          )
          .toSet();
    }
    return {};
  }

  Future _loadDropMarkers() async {
    droppMarkerBytes = await getBytesFromAsset(AppAssets.dropMarker, 200);
    dropps = await ref.read(ApiService.provider).getAllDropps();
    setState(() {});
  }

  Future _loadMarker() async {
    final image = ref.read(UserService.provider).avatarMarker;
    final bytes = await getBytesFromAsset(image, 150);
    avatar = avatar.copyWith(iconParam: BitmapDescriptor.fromBytes(bytes));
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

  void _onMapCreated(GoogleMapController controller) async {
    _mapController = controller;
    _mapController?.setMapStyle(mapStyle);
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
      markers: {avatar}..addAll(isLocationEnabled ? markers : {}),
    );
  }
}
