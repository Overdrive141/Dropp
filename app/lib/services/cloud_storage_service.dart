import 'dart:async';
import 'dart:io';

import 'package:firebase_storage/firebase_storage.dart';
import 'package:hooks_riverpod/hooks_riverpod.dart';

class CloudStorageService {
  static final provider =
      Provider<CloudStorageService>((ref) => CloudStorageService());

  void deleteImageFromDb(String url) {
    FirebaseStorage.instance.ref().child(url).delete();
  }

  Future<String> _uploadFileToDb({
    required File file,
    required String title,
    void Function(double)? progress,
  }) async {
    final ref = FirebaseStorage.instance
        .ref()
        .child(title + '_${DateTime.now().millisecondsSinceEpoch}');
    UploadTask uploadTask = ref.putFile(file);

    if (progress != null) {
      uploadTask.snapshotEvents.listen((TaskSnapshot snapshot) async {
        if (snapshot.state == TaskState.running) {
          progress(snapshot.bytesTransferred / snapshot.totalBytes);
        }
      });
    }

    String downloadUrl = await (await uploadTask).ref.getDownloadURL();
    return downloadUrl;
  }

  Future<String> uploadImageToDb({
    required File file,
    required String title,
    void Function(double)? progress,
  }) async {
    return await _uploadFileToDb(
      file: file,
      title: 'images/' + title,
      progress: progress,
    );
  }

  Future<String> uploadVideoToDb({
    required File file,
    required String title,
    void Function(double)? progress,
  }) async {
    return await _uploadFileToDb(
      file: file,
      title: 'videos/' + title,
      progress: progress,
    );
  }
}
