import 'package:dropp/models/enums.dart';
import 'package:dropp/services/local_storage.dart';
import 'package:dropp/views/settings/dropp_list.dart';
import 'package:dropp/widgets/spinner.dart';
import 'package:flutter/material.dart';
import 'package:hooks_riverpod/hooks_riverpod.dart';

import '../../models/dropp.dart';
import '../../widgets/app_error.dart';
import '../ar/ar_dropp_view.dart';
import '../dropp_view/dropp_story_view.dart';

final myDropps = FutureProvider.autoDispose<List<Dropp>>(((ref) {
  return ref.read(LocalStorageService.provider).getMyDropps();
}));

class MyDroppsView extends ConsumerWidget {
  const MyDroppsView({Key? key}) : super(key: key);

  Future showDropp(BuildContext context, Dropp dropp) async {
    if (dropp.type == DroppType.ar) {
      await Navigator.push(
        context,
        MaterialPageRoute(
          builder: (_) => ARDroppView(
            dropp: dropp,
            showSaveButton: false,
          ),
        ),
      );
    } else {
      await Navigator.push(
        context,
        MaterialPageRoute(
          builder: (_) => DroppStoryView(
            dropp: dropp,
            showSaveButton: false,
          ),
        ),
      );
    }
  }

  @override
  Widget build(BuildContext context, WidgetRef ref) {
    final dropps = ref.watch(myDropps);
    return Scaffold(
      appBar: AppBar(
        backgroundColor: Colors.transparent,
        elevation: 0,
        iconTheme: const IconThemeData(color: Colors.black),
        title: const Text('My Dropps'),
      ),
      backgroundColor: Colors.white,
      body: RefreshIndicator(
        onRefresh: () async {
          return await ref.refresh(myDropps);
        },
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.center,
          children: [
            const Divider(height: 0),
            Expanded(
              child: dropps.when(
                data: (data) {
                  if (data.isEmpty) {
                    return const Text('No dropps yet');
                  }
                  return DroppList(
                    dropps: data,
                    onTap: (dropp) async {
                      await showDropp(context, dropp);
                    },
                  );
                },
                error: (_, __) => AppError(
                  onRetry: () {
                    ref.refresh(myDropps);
                  },
                ),
                loading: () => const Spinner(),
              ),
            ),
          ],
        ),
      ),
    );
  }
}
