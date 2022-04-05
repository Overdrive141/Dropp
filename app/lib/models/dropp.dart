import 'dart:math';

import 'package:faker/faker.dart';
import 'package:freezed_annotation/freezed_annotation.dart';
import 'package:google_maps_flutter/google_maps_flutter.dart';

import '../assets.dart';
import 'enums.dart';

part 'dropp.freezed.dart';
part 'dropp.g.dart';

@freezed
class Dropp with _$Dropp {
  factory Dropp({
    required String id,
    required DateTime time,
    required double lat,
    required double lng,
    required String content,
    required DroppType type,
    @Default(false) bool isSeen,
  }) = _Dropp;

  factory Dropp.fromJson(Map<String, dynamic> json) => _$DroppFromJson(json);
}

@freezed
class DroppUser with _$DroppUser {
  factory DroppUser({
    required String username,
    required int score,
  }) = _DroppUser;

  factory DroppUser.fromJson(Map<String, dynamic> json) =>
      _$DroppUserFromJson(json);
}

class FakeDropp {
  static const _kdefaultCenter = LatLng(42.30554813345436, -83.06151891841292);

  static String getContent(DroppType type) {
    switch (type) {
      case DroppType.photo:
        return Faker().image.image(random: true, width: 400);
      case DroppType.video:
        return "https://filesamples.com/samples/video/mp4/sample_640x360.mp4";
      case DroppType.text:
        return Faker().lorem.sentence();
      case DroppType.ar:
        return (AppAssets.modelOptions.toList()..shuffle()).first;
    }
  }

  static Dropp get() {
    final type = (DroppType.values.toList()..shuffle()).first;
    return Dropp(
      type: type,
      content: getContent(type),
      id: Faker().guid.guid(),
      lat: _kdefaultCenter.latitude - (Random().nextDouble() / 200),
      lng: _kdefaultCenter.longitude - (Random().nextDouble() / 200),
      time: DateTime.now().subtract(
        Duration(seconds: Random().nextInt(1000000)),
      ),
    );
  }

  static List<Dropp> list(int? length) {
    return List.generate(length ?? 20, (m) => FakeDropp.get());
  }
}

class FakeDroppUser {
  static DroppUser get() {
    return DroppUser(
      score: Random().nextInt(500),
      username: Faker().person.firstName(),
    );
  }

  static List<DroppUser> list(int? length) {
    return List.generate(length ?? 10, (m) => FakeDroppUser.get());
  }
}
