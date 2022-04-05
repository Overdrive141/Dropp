import 'package:dropp/assets.dart';
import 'package:dropp/models/enums.dart';
import 'package:firebase_auth/firebase_auth.dart';
import 'package:flutter/material.dart';
import 'package:hooks_riverpod/hooks_riverpod.dart';

class UserService extends ChangeNotifier {
  late FirebaseAuth _auth;

  UserService({FirebaseAuth? auth}) {
    _auth = auth ?? FirebaseAuth.instance;
    _type = AvatarType.values.byName(user?.photoURL ?? AvatarType.male.name);
  }

  static final provider = ChangeNotifierProvider((_) => UserService());

  User? get user => _auth.currentUser;
  bool get hasUser => user != null;

  String get avatarMarker =>
      type == AvatarType.male ? AppAssets.marker0 : AppAssets.marker1;

  String get avatarPreview =>
      type == AvatarType.male ? AppAssets.avatarMale : AppAssets.avatarFemale;

  AvatarType _type = AvatarType.male;

  AvatarType get type => _type;

  set type(AvatarType type) {
    _type = type;
    if (user != null) {
      user!.updatePhotoURL(type.name).catchError((e) => debugPrint(e));
    }
    notifyListeners();
  }

  Future<String?> get token async {
    if (hasUser) {
      return user!.getIdToken();
    }
    return null;
  }

  Future<void> logout() async {
    await _auth.signOut();
  }
}
