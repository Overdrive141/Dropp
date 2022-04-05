// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'dropp.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

_$_Dropp _$$_DroppFromJson(Map<String, dynamic> json) => _$_Dropp(
      id: json['id'] as String,
      time: DateTime.parse(json['time'] as String),
      lat: (json['lat'] as num).toDouble(),
      lng: (json['lng'] as num).toDouble(),
      content: json['content'] as String,
      type: $enumDecode(_$DroppTypeEnumMap, json['type']),
      isSeen: json['isSeen'] as bool? ?? false,
    );

Map<String, dynamic> _$$_DroppToJson(_$_Dropp instance) => <String, dynamic>{
      'id': instance.id,
      'time': instance.time.toIso8601String(),
      'lat': instance.lat,
      'lng': instance.lng,
      'content': instance.content,
      'type': _$DroppTypeEnumMap[instance.type],
      'isSeen': instance.isSeen,
    };

const _$DroppTypeEnumMap = {
  DroppType.photo: 'photo',
  DroppType.video: 'video',
  DroppType.ar: 'ar',
  DroppType.text: 'text',
};

_$_DroppUser _$$_DroppUserFromJson(Map<String, dynamic> json) => _$_DroppUser(
      username: json['username'] as String,
      score: json['score'] as int,
    );

Map<String, dynamic> _$$_DroppUserToJson(_$_DroppUser instance) =>
    <String, dynamic>{
      'username': instance.username,
      'score': instance.score,
    };
