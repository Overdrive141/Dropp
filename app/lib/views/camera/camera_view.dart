import 'dart:io';

import 'package:dropp/assets.dart';
import 'package:dropp/services/cloud_storage_service.dart';
import 'package:dropp/views/camera/video_recording_view.dart';
import 'package:flutter/material.dart';
import 'package:hooks_riverpod/hooks_riverpod.dart';
import 'package:image_picker/image_picker.dart';
import 'package:lottie/lottie.dart';

class CameraView extends ConsumerStatefulWidget {
  final bool isVideo;
  const CameraView({
    Key? key,
    required this.isVideo,
  }) : super(key: key);

  @override
  _CameraViewState createState() => _CameraViewState();
}

class _CameraViewState extends ConsumerState<CameraView>
    with SingleTickerProviderStateMixin {
  late final AnimationController _controller;

  bool _isUploading = false;

  @override
  void initState() {
    if (widget.isVideo) {
      WidgetsBinding.instance?.addPostFrameCallback((_) {
        Navigator.push(
          context,
          MaterialPageRoute(
            builder: (builder) => VideoRecordingScreen(
              onDone: _uploadVideo,
            ),
          ),
        );
      });
    } else {
      _initCamera();
    }
    _controller = AnimationController(vsync: this)
      ..addListener(() {
        setState(() {});
      });
    super.initState();
  }

  @override
  void dispose() {
    _controller.dispose();
    super.dispose();
  }

  void _handleUploadProgress(double progress) {
    _controller.animateTo(progress);
  }

  _initCamera() async {
    final f = await ImagePicker().pickImage(
      source: ImageSource.camera,
      maxWidth: 1080,
      imageQuality: 80,
    );

    if (f != null) {
      _isUploading = true;
      setState(() {});
      final url = await ref.read(CloudStorageService.provider).uploadImageToDb(
            file: File(f.path),
            title: 'ras',
            progress: _handleUploadProgress,
          );
      await Future.delayed(const Duration(seconds: 3));
      Navigator.pop(context);
    } else {
      Navigator.pop(context);
    }
  }

  _uploadVideo(String filePath) async {
    _isUploading = true;
    setState(() {});
    final url = await ref.read(CloudStorageService.provider).uploadVideoToDb(
          file: File(filePath),
          title: 'ras',
          progress: _handleUploadProgress,
        );
    await Future.delayed(const Duration(seconds: 3));
    Navigator.pop(context);
  }

  @override
  Widget build(BuildContext context) {
    // if (!_isUploading) {
    //   return Center();
    // }
    return Scaffold(
      body: Opacity(
        opacity: _isUploading ? 1 : 0,
        child: Column(
          children: [
            const Spacer(),
            Center(
              child: SizedBox(
                height: 220,
                child: Lottie.asset(
                  AppAssets.progressAnimation,
                  controller: _controller,
                  onLoaded: (composition) {
                    _controller.duration = composition.duration;
                    setState(() {});
                  },
                ),
              ),
            ),
            const Spacer(),
            Lottie.asset(
              AppAssets.bubblesAnimation,
            ),
          ],
        ),
      ),
    );
  }
}
