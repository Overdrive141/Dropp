import 'dart:io';

import 'package:flutter/material.dart';

class CameraViewPage extends StatelessWidget {
  const CameraViewPage({Key? key, required this.path}) : super(key: key);
  final String path;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Colors.black,
      appBar: AppBar(
        automaticallyImplyLeading: true,
        iconTheme: const IconThemeData(color: Colors.white),
        backgroundColor: Colors.black,
        actions: const [
          Icon(Icons.check),
          SizedBox(width: 16),
        ],
      ),
      body: SizedBox(
        width: MediaQuery.of(context).size.width,
        height: MediaQuery.of(context).size.height,
        child: Stack(
          children: [
            SizedBox(
              width: MediaQuery.of(context).size.width,
              height: MediaQuery.of(context).size.height - 150,
              child: Image.file(
                File(path),
                fit: BoxFit.cover,
              ),
            ),
            Positioned(
              bottom: 0,
              child: Container(
                color: Colors.black38,
                width: MediaQuery.of(context).size.width,
                padding: const EdgeInsets.symmetric(vertical: 5, horizontal: 8),
                child: TextFormField(
                  style: const TextStyle(
                    color: Colors.white,
                    fontSize: 17,
                  ),
                  maxLines: 6,
                  minLines: 1,
                ),
              ),
            ),
          ],
        ),
      ),
    );
  }
}
