import 'package:dio/dio.dart';
import 'package:dropp/models/dropp.dart';
import 'package:dropp/services/user_service.dart';
import 'package:firebase_auth/firebase_auth.dart';
import 'package:flutter/material.dart';
import 'package:hooks_riverpod/hooks_riverpod.dart';

class ApiService {
  late Dio _api;
  final Ref ref;

  static final provider = Provider((ref) => ApiService(ref));

  final options = BaseOptions(
    baseUrl: 'http://20.228.245.184:8098/api/v1/',
  );

  ApiService(this.ref) {
    _api = Dio()
      ..interceptors.add(InterceptorsWrapper(
        onRequest: (options, handler) async {
          final token = await ref.read(UserService.provider).token;
          options.headers.putIfAbsent(
            'Authorization',
            () => token,
          );
          return handler.next(options);
        },
        onError: (DioError e, handler) {
          debugPrint(e.toString());
          return handler.next(e);
        },
      ));
  }

  Future<void> addUser(User user) async {
    await _api.post('/user', data: {
      'username': user.displayName,
      'email': user.email ?? "",
      'avatar': 'AV1',
      'contactNo': user.phoneNumber ?? "3712989712",
    });
  }

  Future<void> getUser(String email) async {
    await _api.get('/user/$email');
  }

  Future<Dropp> getDropp(String droppId) async {
    final res = await _api.get('/user/drop/$droppId');
    return Dropp.fromJson(res.data);
  }

  Future<List<Dropp>> getAllDropps() async {
    final res = await _api.get('/dropp');
    return res.data as List<Dropp>;
  }

  Future<void> markDropAsFavorite(String droppId) async {
    await _api.post('/favorite/$droppId');
  }

  Future<List<Dropp>> getMyDropps() async {
    final res = await _api.get('/myDropps');
    return res.data as List<Dropp>;
  }

  Future<List<DroppUser>> getLeaderBoard() async {
    final res = await _api.get('/leaderboard');
    return res.data as List<DroppUser>;
  }
}

class FakeApi implements ApiService {
  @override
  late Dio _api;

  @override
  BaseOptions get options => throw UnimplementedError();

  @override
  Ref get ref => throw UnimplementedError();

  Future sleep() async => await Future.delayed(const Duration(seconds: 2));

  @override
  Future<void> addUser(User user) {
    throw UnimplementedError();
  }

  @override
  Future<Dropp> getDropp(String droppId) async {
    return FakeDropp.get().copyWith(id: droppId);
  }

  @override
  Future<DroppUser> getUser(String email) async {
    return FakeDroppUser.get();
  }

  @override
  Future<void> markDropAsFavorite(String droppId) async {
    throw UnimplementedError();
  }

  @override
  Future<List<DroppUser>> getLeaderBoard() async {
    await sleep();
    return FakeDroppUser.list(20)..sort((a, b) => b.score - a.score);
  }

  @override
  Future<List<Dropp>> getMyDropps() async {
    await sleep();
    return FakeDropp.list(20);
  }

  @override
  Future<List<Dropp>> getAllDropps() async {
    await sleep();
    return FakeDropp.list(20);
  }
}
