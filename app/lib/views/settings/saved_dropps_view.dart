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

final savedDropps = FutureProvider<List<Dropp>>(((ref) {
  return ref.read(LocalStorageService.provider).getSavedDropps();
}));

class SavedDroppsView extends ConsumerWidget {
  const SavedDroppsView({Key? key}) : super(key: key);

  Future showDropp(BuildContext context, Dropp dropp) async {
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
  }

  @override
  Widget build(BuildContext context, WidgetRef ref) {
    final dropps = ref.watch(savedDropps);
    return Scaffold(
      appBar: AppBar(
        backgroundColor: Colors.transparent,
        elevation: 0,
        iconTheme: const IconThemeData(color: Colors.black),
        title: const Text('Saved Dropps'),
      ),
      backgroundColor: Colors.white,
      body: RefreshIndicator(
        onRefresh: () async {
          return await ref.refresh(savedDropps);
        },
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.center,
          children: [
            const Divider(height: 0),
            Expanded(
              child: dropps.when(
                data: (data) => DroppList(
                  dropps: data,
                  onTap: (dropp) async {
                    await showDropp(context, dropp);
                    ref.refresh(savedDropps);
                  },
                ),
                error: (_, __) => AppError(
                  onRetry: () {
                    ref.refresh(savedDropps);
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
