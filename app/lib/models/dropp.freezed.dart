// coverage:ignore-file
// GENERATED CODE - DO NOT MODIFY BY HAND
// ignore_for_file: unused_element, deprecated_member_use, deprecated_member_use_from_same_package, use_function_type_syntax_for_parameters, unnecessary_const, avoid_init_to_null, invalid_override_different_default_values_named, prefer_expression_function_bodies, annotate_overrides, invalid_annotation_target

part of 'dropp.dart';

// **************************************************************************
// FreezedGenerator
// **************************************************************************

T _$identity<T>(T value) => value;

final _privateConstructorUsedError = UnsupportedError(
    'It seems like you constructed your class using `MyClass._()`. This constructor is only meant to be used by freezed and you are not supposed to need it nor use it.\nPlease check the documentation here for more informations: https://github.com/rrousselGit/freezed#custom-getters-and-methods');

Dropp _$DroppFromJson(Map<String, dynamic> json) {
  return _Dropp.fromJson(json);
}

/// @nodoc
class _$DroppTearOff {
  const _$DroppTearOff();

  _Dropp call(
      {required String id,
      required DateTime time,
      required double lat,
      required double lng,
      required String content,
      required DroppType type,
      bool isSeen = false}) {
    return _Dropp(
      id: id,
      time: time,
      lat: lat,
      lng: lng,
      content: content,
      type: type,
      isSeen: isSeen,
    );
  }

  Dropp fromJson(Map<String, Object?> json) {
    return Dropp.fromJson(json);
  }
}

/// @nodoc
const $Dropp = _$DroppTearOff();

/// @nodoc
mixin _$Dropp {
  String get id => throw _privateConstructorUsedError;
  DateTime get time => throw _privateConstructorUsedError;
  double get lat => throw _privateConstructorUsedError;
  double get lng => throw _privateConstructorUsedError;
  String get content => throw _privateConstructorUsedError;
  DroppType get type => throw _privateConstructorUsedError;
  bool get isSeen => throw _privateConstructorUsedError;

  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;
  @JsonKey(ignore: true)
  $DroppCopyWith<Dropp> get copyWith => throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $DroppCopyWith<$Res> {
  factory $DroppCopyWith(Dropp value, $Res Function(Dropp) then) =
      _$DroppCopyWithImpl<$Res>;
  $Res call(
      {String id,
      DateTime time,
      double lat,
      double lng,
      String content,
      DroppType type,
      bool isSeen});
}

/// @nodoc
class _$DroppCopyWithImpl<$Res> implements $DroppCopyWith<$Res> {
  _$DroppCopyWithImpl(this._value, this._then);

  final Dropp _value;
  // ignore: unused_field
  final $Res Function(Dropp) _then;

  @override
  $Res call({
    Object? id = freezed,
    Object? time = freezed,
    Object? lat = freezed,
    Object? lng = freezed,
    Object? content = freezed,
    Object? type = freezed,
    Object? isSeen = freezed,
  }) {
    return _then(_value.copyWith(
      id: id == freezed
          ? _value.id
          : id // ignore: cast_nullable_to_non_nullable
              as String,
      time: time == freezed
          ? _value.time
          : time // ignore: cast_nullable_to_non_nullable
              as DateTime,
      lat: lat == freezed
          ? _value.lat
          : lat // ignore: cast_nullable_to_non_nullable
              as double,
      lng: lng == freezed
          ? _value.lng
          : lng // ignore: cast_nullable_to_non_nullable
              as double,
      content: content == freezed
          ? _value.content
          : content // ignore: cast_nullable_to_non_nullable
              as String,
      type: type == freezed
          ? _value.type
          : type // ignore: cast_nullable_to_non_nullable
              as DroppType,
      isSeen: isSeen == freezed
          ? _value.isSeen
          : isSeen // ignore: cast_nullable_to_non_nullable
              as bool,
    ));
  }
}

/// @nodoc
abstract class _$DroppCopyWith<$Res> implements $DroppCopyWith<$Res> {
  factory _$DroppCopyWith(_Dropp value, $Res Function(_Dropp) then) =
      __$DroppCopyWithImpl<$Res>;
  @override
  $Res call(
      {String id,
      DateTime time,
      double lat,
      double lng,
      String content,
      DroppType type,
      bool isSeen});
}

/// @nodoc
class __$DroppCopyWithImpl<$Res> extends _$DroppCopyWithImpl<$Res>
    implements _$DroppCopyWith<$Res> {
  __$DroppCopyWithImpl(_Dropp _value, $Res Function(_Dropp) _then)
      : super(_value, (v) => _then(v as _Dropp));

  @override
  _Dropp get _value => super._value as _Dropp;

  @override
  $Res call({
    Object? id = freezed,
    Object? time = freezed,
    Object? lat = freezed,
    Object? lng = freezed,
    Object? content = freezed,
    Object? type = freezed,
    Object? isSeen = freezed,
  }) {
    return _then(_Dropp(
      id: id == freezed
          ? _value.id
          : id // ignore: cast_nullable_to_non_nullable
              as String,
      time: time == freezed
          ? _value.time
          : time // ignore: cast_nullable_to_non_nullable
              as DateTime,
      lat: lat == freezed
          ? _value.lat
          : lat // ignore: cast_nullable_to_non_nullable
              as double,
      lng: lng == freezed
          ? _value.lng
          : lng // ignore: cast_nullable_to_non_nullable
              as double,
      content: content == freezed
          ? _value.content
          : content // ignore: cast_nullable_to_non_nullable
              as String,
      type: type == freezed
          ? _value.type
          : type // ignore: cast_nullable_to_non_nullable
              as DroppType,
      isSeen: isSeen == freezed
          ? _value.isSeen
          : isSeen // ignore: cast_nullable_to_non_nullable
              as bool,
    ));
  }
}

/// @nodoc
@JsonSerializable()
class _$_Dropp implements _Dropp {
  _$_Dropp(
      {required this.id,
      required this.time,
      required this.lat,
      required this.lng,
      required this.content,
      required this.type,
      this.isSeen = false});

  factory _$_Dropp.fromJson(Map<String, dynamic> json) =>
      _$$_DroppFromJson(json);

  @override
  final String id;
  @override
  final DateTime time;
  @override
  final double lat;
  @override
  final double lng;
  @override
  final String content;
  @override
  final DroppType type;
  @JsonKey()
  @override
  final bool isSeen;

  @override
  String toString() {
    return 'Dropp(id: $id, time: $time, lat: $lat, lng: $lng, content: $content, type: $type, isSeen: $isSeen)';
  }

  @override
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _Dropp &&
            const DeepCollectionEquality().equals(other.id, id) &&
            const DeepCollectionEquality().equals(other.time, time) &&
            const DeepCollectionEquality().equals(other.lat, lat) &&
            const DeepCollectionEquality().equals(other.lng, lng) &&
            const DeepCollectionEquality().equals(other.content, content) &&
            const DeepCollectionEquality().equals(other.type, type) &&
            const DeepCollectionEquality().equals(other.isSeen, isSeen));
  }

  @override
  int get hashCode => Object.hash(
      runtimeType,
      const DeepCollectionEquality().hash(id),
      const DeepCollectionEquality().hash(time),
      const DeepCollectionEquality().hash(lat),
      const DeepCollectionEquality().hash(lng),
      const DeepCollectionEquality().hash(content),
      const DeepCollectionEquality().hash(type),
      const DeepCollectionEquality().hash(isSeen));

  @JsonKey(ignore: true)
  @override
  _$DroppCopyWith<_Dropp> get copyWith =>
      __$DroppCopyWithImpl<_Dropp>(this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$_DroppToJson(this);
  }
}

abstract class _Dropp implements Dropp {
  factory _Dropp(
      {required String id,
      required DateTime time,
      required double lat,
      required double lng,
      required String content,
      required DroppType type,
      bool isSeen}) = _$_Dropp;

  factory _Dropp.fromJson(Map<String, dynamic> json) = _$_Dropp.fromJson;

  @override
  String get id;
  @override
  DateTime get time;
  @override
  double get lat;
  @override
  double get lng;
  @override
  String get content;
  @override
  DroppType get type;
  @override
  bool get isSeen;
  @override
  @JsonKey(ignore: true)
  _$DroppCopyWith<_Dropp> get copyWith => throw _privateConstructorUsedError;
}

DroppUser _$DroppUserFromJson(Map<String, dynamic> json) {
  return _DroppUser.fromJson(json);
}

/// @nodoc
class _$DroppUserTearOff {
  const _$DroppUserTearOff();

  _DroppUser call({required String username, required int score}) {
    return _DroppUser(
      username: username,
      score: score,
    );
  }

  DroppUser fromJson(Map<String, Object?> json) {
    return DroppUser.fromJson(json);
  }
}

/// @nodoc
const $DroppUser = _$DroppUserTearOff();

/// @nodoc
mixin _$DroppUser {
  String get username => throw _privateConstructorUsedError;
  int get score => throw _privateConstructorUsedError;

  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;
  @JsonKey(ignore: true)
  $DroppUserCopyWith<DroppUser> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $DroppUserCopyWith<$Res> {
  factory $DroppUserCopyWith(DroppUser value, $Res Function(DroppUser) then) =
      _$DroppUserCopyWithImpl<$Res>;
  $Res call({String username, int score});
}

/// @nodoc
class _$DroppUserCopyWithImpl<$Res> implements $DroppUserCopyWith<$Res> {
  _$DroppUserCopyWithImpl(this._value, this._then);

  final DroppUser _value;
  // ignore: unused_field
  final $Res Function(DroppUser) _then;

  @override
  $Res call({
    Object? username = freezed,
    Object? score = freezed,
  }) {
    return _then(_value.copyWith(
      username: username == freezed
          ? _value.username
          : username // ignore: cast_nullable_to_non_nullable
              as String,
      score: score == freezed
          ? _value.score
          : score // ignore: cast_nullable_to_non_nullable
              as int,
    ));
  }
}

/// @nodoc
abstract class _$DroppUserCopyWith<$Res> implements $DroppUserCopyWith<$Res> {
  factory _$DroppUserCopyWith(
          _DroppUser value, $Res Function(_DroppUser) then) =
      __$DroppUserCopyWithImpl<$Res>;
  @override
  $Res call({String username, int score});
}

/// @nodoc
class __$DroppUserCopyWithImpl<$Res> extends _$DroppUserCopyWithImpl<$Res>
    implements _$DroppUserCopyWith<$Res> {
  __$DroppUserCopyWithImpl(_DroppUser _value, $Res Function(_DroppUser) _then)
      : super(_value, (v) => _then(v as _DroppUser));

  @override
  _DroppUser get _value => super._value as _DroppUser;

  @override
  $Res call({
    Object? username = freezed,
    Object? score = freezed,
  }) {
    return _then(_DroppUser(
      username: username == freezed
          ? _value.username
          : username // ignore: cast_nullable_to_non_nullable
              as String,
      score: score == freezed
          ? _value.score
          : score // ignore: cast_nullable_to_non_nullable
              as int,
    ));
  }
}

/// @nodoc
@JsonSerializable()
class _$_DroppUser implements _DroppUser {
  _$_DroppUser({required this.username, required this.score});

  factory _$_DroppUser.fromJson(Map<String, dynamic> json) =>
      _$$_DroppUserFromJson(json);

  @override
  final String username;
  @override
  final int score;

  @override
  String toString() {
    return 'DroppUser(username: $username, score: $score)';
  }

  @override
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _DroppUser &&
            const DeepCollectionEquality().equals(other.username, username) &&
            const DeepCollectionEquality().equals(other.score, score));
  }

  @override
  int get hashCode => Object.hash(
      runtimeType,
      const DeepCollectionEquality().hash(username),
      const DeepCollectionEquality().hash(score));

  @JsonKey(ignore: true)
  @override
  _$DroppUserCopyWith<_DroppUser> get copyWith =>
      __$DroppUserCopyWithImpl<_DroppUser>(this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$_DroppUserToJson(this);
  }
}

abstract class _DroppUser implements DroppUser {
  factory _DroppUser({required String username, required int score}) =
      _$_DroppUser;

  factory _DroppUser.fromJson(Map<String, dynamic> json) =
      _$_DroppUser.fromJson;

  @override
  String get username;
  @override
  int get score;
  @override
  @JsonKey(ignore: true)
  _$DroppUserCopyWith<_DroppUser> get copyWith =>
      throw _privateConstructorUsedError;
}
