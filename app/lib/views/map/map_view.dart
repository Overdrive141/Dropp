import 'dart:typed_data';
import 'dart:ui' as ui;

import 'package:dropp/assets.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:google_maps_flutter/google_maps_flutter.dart';

class MapView extends StatefulWidget {
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
  State<MapView> createState() => MapViewState();
}

class MapViewState extends State<MapView> {
  late GoogleMapController _mapController;

  static const LatLng _kdefaultCenter =
      LatLng(42.30554813345436, -83.06151891841292);
  static const double _kdefaultZoom = 6;
  late BitmapDescriptor pinLocationIcon;

  LatLng? _selectedLocation;

  Marker x = const Marker(
    markerId: MarkerId('selectedLocation'),
    position: _kdefaultCenter,
  );

  Future<Uint8List> getBytesFromAsset(String path, int width) async {
    ByteData data = await rootBundle.load(path);
    ui.Codec codec = await ui.instantiateImageCodec(data.buffer.asUint8List(),
        targetWidth: width);
    ui.FrameInfo fi = await codec.getNextFrame();
    return (await fi.image.toByteData(format: ui.ImageByteFormat.png))!
        .buffer
        .asUint8List();
  }

  @override
  initState() {
    super.initState();

    _updateMarker(150);
    if (widget.defaultLocation != null) {
      _selectedLocation = widget.defaultLocation;
    }
  }

  void _onMapCreated(GoogleMapController controller) async {
    _mapController = controller;

    _mapController.setMapStyle(_mapStyle);
    // if (widget.enableLocationPicker) {
    //   final showCurrentLocation = await _model.askForCurrentLocation();
    //   if (!showCurrentLocation) return;
    //   final _location = await _model.getCurrentLocation();
    //   if (_location != null) {
    //     _mapController.animateCamera(CameraUpdate.newLatLng(_location));
    //   }
    // }
  }

  void _updateMarker(int width) async {
    final bytes = await getBytesFromAsset(AppAssets.marker0, width);
    pinLocationIcon = BitmapDescriptor.fromBytes(bytes);
    x = Marker(
      markerId: const MarkerId('selectedLocation'),
      position: _kdefaultCenter,
      icon: pinLocationIcon,
    );
    setState(() {});
  }

  void _handleCameraMove(CameraPosition position) {
    widget.onCameraMove?.call(position.target, position.zoom);
    _updateMarker((position.zoom ~/ 20) * 150);
    // getBytesFromAsset(AppAssets.marker0, (position.zoom ~/ 20) * 150).then((s) {
    //   pinLocationIcon = BitmapDescriptor.fromBytes(s);
    //   x = Marker(
    //     markerId: const MarkerId('selectedLocation'),
    //     position: _kdefaultCenter,
    //     icon: pinLocationIcon,
    //   );
    //   setState(() {});
    // });
  }

  void _handleSelectedLocation(LatLng location) {
    setState(() {
      _selectedLocation = location;
    });
    widget.onLocationSelected?.call(location);
  }

  @override
  Widget build(BuildContext context) {
    return GoogleMap(
      mapType: MapType.normal,
      onCameraMove: _handleCameraMove,
      initialCameraPosition: const CameraPosition(
          target: _kdefaultCenter, zoom: 20, tilt: 80, bearing: 10),
      zoomControlsEnabled: false,
      compassEnabled: false,
      myLocationEnabled: true,
      myLocationButtonEnabled: false,
      onMapCreated: _onMapCreated,
      // onTap: (_) {
      //   _mapController.animateCamera(CameraUpdate.newCameraPosition(
      //     CameraPosition(
      //         zoom: 19,
      //         tilt: 80,
      //         target: _kdefaultCenter,
      //         bearing: Random().nextDouble() * 360),
      //   ));
      // },
      markers: {x},
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
