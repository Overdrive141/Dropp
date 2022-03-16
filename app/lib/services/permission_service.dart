import 'dart:async';

import 'package:hooks_riverpod/hooks_riverpod.dart';
import 'package:permission_handler/permission_handler.dart';

class PermissionService extends StateNotifier<bool> {
  PermissionService() : super(true);

  Timer? timer;

  static final provider = StateNotifierProvider<PermissionService, bool>((ref) {
    final permissionService = PermissionService();
    permissionService.listenToPermissions();
    return permissionService;
  });

  void listenToPermissions() async {
    timer ??= Timer.periodic(const Duration(seconds: 2), (_) async {
      final status = await Permission.locationWhenInUse.serviceStatus.isEnabled;
      if (state != status) {
        state = status;
      }
    });
  }
}
