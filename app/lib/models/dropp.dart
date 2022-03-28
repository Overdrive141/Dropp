import 'enums.dart';

class Dropp {
  final DateTime time;
  final double lat;
  final double lng;
  final String id;
  final String content;
  final DroppType type;
  bool isSeen;

  Dropp({
    required this.time,
    required this.lat,
    required this.lng,
    required this.id,
    required this.type,
    required this.content,
    this.isSeen = false,
  });
}
