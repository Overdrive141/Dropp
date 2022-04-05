import 'package:flutter/material.dart';

class AppError extends StatelessWidget {
  final VoidCallback? onRetry;
  final bool showRetry;
  final String text;

  const AppError({
    Key? key,
    this.onRetry,
    this.showRetry = true,
    this.text = 'Oops! Something went wrong',
  }) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Column(
      mainAxisAlignment: MainAxisAlignment.center,
      children: [
        const Icon(
          Icons.warning_rounded,
          color: Colors.deepOrange,
          size: 100,
        ),
        const SizedBox(height: 16),
        Text(
          text,
          style: const TextStyle(color: Colors.grey, fontSize: 18),
        ),
        const SizedBox(height: 12),
        OutlinedButton.icon(
          label: const Text('Retry'),
          icon: const Icon(Icons.refresh),
          onPressed: () {
            onRetry?.call();
          },
        )
      ],
    );
  }
}
