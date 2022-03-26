import 'package:flutter/material.dart';

class TextEditor extends StatelessWidget {
  TextEditor({
    Key? key,
    this.onDone,
  }) : super(key: key);

  final Function(String)? onDone;
  late final TextEditingController _controller = TextEditingController();

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        backgroundColor: Colors.transparent,
        elevation: 0,
        iconTheme: const IconThemeData(color: Colors.white),
        actions: [
          IconButton(
            icon: const Icon(Icons.check),
            onPressed: () {
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
