import 'package:faker/faker.dart';
import 'package:flutter/material.dart';
import 'package:hooks_riverpod/hooks_riverpod.dart';

import '../models/dropp.dart';
import '../models/enums.dart';
import '../services/local_storage.dart';

class TextEditor extends ConsumerWidget {
  TextEditor({
    Key? key,
    this.onDone,
  }) : super(key: key);

  final Function(String)? onDone;
  late final TextEditingController _controller = TextEditingController();

  @override
  Widget build(BuildContext context, WidgetRef ref) {
    return Scaffold(
      appBar: AppBar(
        backgroundColor: Colors.transparent,
        elevation: 0,
        iconTheme: const IconThemeData(color: Colors.white),
        actions: [
          IconButton(
            icon: const Icon(Icons.check),
            onPressed: () async {
              await ref.read(LocalStorageService.provider).addDropp(
                    Dropp(
                      content: _controller.text,
                      id: Faker().guid.guid(),
                      lat: 42.74921841,
                      lng: -83.079421412,
                      time: DateTime.now(),
                      type: DroppType.text,
                    ),
                  );
              onDone?.call(_controller.text);
            },
          )
        ],
      ),
      backgroundColor: Colors.purple,
      body: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          children: [
            Center(
              child: TextField(
                autofocus: true,
                minLines: 1,
                maxLines: 4,
                maxLength: 120,
                textAlign: TextAlign.center,
                controller: _controller,
                decoration: const InputDecoration(
                  border: InputBorder.none,
                  counterText: "",
                ),
                style: const TextStyle(
                  fontSize: 40.0,
                  height: 2.0,
                  color: Colors.white,
                ),
              ),
            ),
          ],
        ),
      ),
    );
  }
}
