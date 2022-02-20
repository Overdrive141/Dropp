import 'package:dropp/assets.dart';
import 'package:dropp/colors.dart';
import 'package:dropp/models/enums.dart';
import 'package:flutter/material.dart';

class AuthenticationView extends StatelessWidget {
  const AuthenticationView({Key? key}) : super(key: key);

  void handleAuth(BuildContext context) {
    ScaffoldMessenger.of(context).clearSnackBars();
    ScaffoldMessenger.of(context).showSnackBar(
      const SnackBar(
        content: Text('Feature Coming soon!'),
      ),
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Colors.white,
      body: Padding(
        padding: const EdgeInsets.all(24.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.center,
          children: [
            const Spacer(),
            const Spacer(),
            Image.asset(
              'assets/images/logo.png',
              width: MediaQuery.of(context).size.width / 3,
            ),
            const Spacer(),
            const Spacer(),
            OutlinedButton(
              onPressed: () => handleAuth(context),
              child: const Text(
                'Sign in with Email',
                style: TextStyle(color: Colors.black, fontSize: 16),
              ),
            ),
            const Spacer(),
            const Text(
              'OR',
              style: TextStyle(color: Colors.grey),
            ),
            const Spacer(),
            Row(
              mainAxisAlignment: MainAxisAlignment.center,
              children: [
                Flexible(
                    child: OAuthButton(
                  alignment: Alignment.bottomRight,
                  type: AuthType.google,
                  onTap: () => handleAuth(context),
                )),
                const SizedBox(width: 16),
                Flexible(
                    child: OAuthButton(
                  alignment: Alignment.bottomLeft,
                  type: AuthType.facebook,
                  onTap: () => handleAuth(context),
                )),
              ],
            ),
            const SizedBox(height: 16),
            Row(
              mainAxisAlignment: MainAxisAlignment.center,
              children: [
                Flexible(
                    child: OAuthButton(
                  alignment: Alignment.topRight,
                  type: AuthType.github,
                  onTap: () => handleAuth(context),
                )),
                const SizedBox(width: 16),
                Flexible(
                    child: OAuthButton(
                  alignment: Alignment.topLeft,
                  type: AuthType.twitter,
                  onTap: () => handleAuth(context),
                )),
              ],
            ),
            const Spacer(),
          ],
        ),
      ),
      bottomNavigationBar: Container(
        color: AppColors.primary,
        height: 50,
      ),
    );
  }
}

class OAuthButton extends StatelessWidget {
  const OAuthButton({
    this.alignment = Alignment.topRight,
    Key? key,
    required this.type,
    this.onTap,
  }) : super(key: key);

  static const radius = Radius.circular(100);

  final Alignment alignment;
  final AuthType type;
  final VoidCallback? onTap;

  Color get authColor {
    switch (type) {
      case AuthType.google:
        return AppColors.google;
      case AuthType.facebook:
        return AppColors.facebook;
      case AuthType.github:
        return AppColors.github;
      case AuthType.twitter:
        return AppColors.twitter;
    }
  }

  String get iconPath {
    switch (type) {
      case AuthType.google:
        return AppAssets.googleIcon;
      case AuthType.facebook:
        return AppAssets.facebookIcon;
      case AuthType.github:
        return AppAssets.githubIcon;
      case AuthType.twitter:
        return AppAssets.twitterIcon;
    }
  }

  @override
  Widget build(BuildContext context) {
    return GestureDetector(
      onTap: onTap,
      child: Container(
        width: 120,
        height: 120,
        decoration: BoxDecoration(
          color: authColor,
          borderRadius: BorderRadius.only(
            topRight: alignment == Alignment.topRight ? Radius.zero : radius,
            topLeft: alignment == Alignment.topLeft ? Radius.zero : radius,
            bottomLeft:
                alignment == Alignment.bottomLeft ? Radius.zero : radius,
            bottomRight:
                alignment == Alignment.bottomRight ? Radius.zero : radius,
          ),
        ),
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Image.asset(
              iconPath,
              height: 40,
            ),
          ],
        ),
      ),
    );
  }
}
