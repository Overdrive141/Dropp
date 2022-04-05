import 'package:flutter/material.dart';
import 'package:hooks_riverpod/hooks_riverpod.dart';

import '../models/dropp.dart';
import '../services/local_storage.dart';

class SaveButton extends ConsumerWidget {
  final Dropp dropp;
  SaveButton({Key? key, required this.dropp}) : super(key: key);

  late final favorites = FutureProvider<bool>((ref) {
    return ref.read(LocalStorageService.provider).isSavedDropp(dropp);
  });

  @override
  Widget build(BuildContext context, WidgetRef ref) {
    final getSaved = ref.watch(favorites);
    return getSaved.when(
      data: (isSaved) => GestureDetector(
        child: Icon(
          isSaved ? Icons.favorite_rounded : Icons.favorite_border,
          color: isSaved ? Colors.red : Colors.white,
        ),
        onTap: () async {
          if (isSaved) {
            await ref.read(LocalStorageService.provider).unsaveDropp(dropp);
            ref.refresh(favorites);
          } else {
            await ref.read(LocalStorageService.provider).saveDropp(dropp);
            ref.refresh(favorites);
          }
        },
      ),
      error: (_, __) => const Center(),
      loading: () => const Center(),
    );
  }
}
