import 'package:dropp/colors.dart';
import 'package:dropp/services/user_service.dart';
import 'package:dropp/views/authentication/authentication_view.dart';
import 'package:dropp/views/profile/avatar_selection_view.dart';
import 'package:flutter/material.dart';
import 'package:hooks_riverpod/hooks_riverpod.dart';

class SettingsView extends ConsumerStatefulWidget {
  const SettingsView({Key? key}) : super(key: key);

  @override
  ConsumerState<SettingsView> createState() => _SettingsViewState();
}

class _SettingsViewState extends ConsumerState<SettingsView> {
  void showAvatarView(BuildContext context) {
    Navigator.push(
      context,
      MaterialPageRoute(
        builder: (_) => AvatarSelectionView(
          onDone: () {
            Navigator.pop(context);
          },
        ),
      ),
    );
  }

  Future logout() async {
    await ref.read(UserService.provider).logout();
    Navigator.pushAndRemoveUntil(
        context,
        MaterialPageRoute(
          builder: (_) => const AuthenticationView(),
        ),
        (predicate) => false);
  }

  @override
  Widget build(BuildContext context) {
    final avatar = ref.watch(UserService.provider).avatarPreview;
    final user = ref.watch(UserService.provider).user;
    return Scaffold(
      appBar: AppBar(
        backgroundColor: Colors.transparent,
        elevation: 0,
        iconTheme: const IconThemeData(color: Colors.black),
        title: const Text('Profile Settings'),
      ),
      backgroundColor: Colors.white,
      body: Padding(
        padding: const EdgeInsets.all(24.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.center,
          children: [
            CircleAvatar(
              radius: 50,
              backgroundColor: Colors.grey[200],
              backgroundImage: AssetImage(
                avatar,
              ),
            ),
            const SizedBox(height: 16),
            Text(
              user?.displayName ?? '',
              style: const TextStyle(
                color: Colors.black,
                fontSize: 18,
                fontWeight: FontWeight.bold,
              ),
            ),
            Text(
              user?.email ?? '',
              style: const TextStyle(
                color: Colors.grey,
              ),
            ),
            const SizedBox(height: 24),
            const Divider(height: 16),
            const SizedBox(height: 8),
            ListTile(
              contentPadding: EdgeInsets.zero,
              leading: const Icon(
                Icons.person,
                color: Colors.blue,
              ),
              horizontalTitleGap: 0,
              dense: true,
              title: const Text(
                'Change your Avatar',
                style: TextStyle(color: Colors.black, fontSize: 16),
              ),
              subtitle: const Text(
                'You can choose a new profile avatar any time',
                style: TextStyle(color: Colors.grey),
              ),
              onTap: () => showAvatarView(context),
            ),
            ListTile(
              contentPadding: EdgeInsets.zero,
              leading: const Icon(
                Icons.favorite_rounded,
                color: Colors.red,
              ),
              horizontalTitleGap: 0,
              dense: true,
              title: const Text(
                'Saved Dropps',
                style: TextStyle(color: Colors.black, fontSize: 16),
              ),
              subtitle: const Text(
                'All your saved dropps can be viewed here',
                style: TextStyle(color: Colors.grey),
              ),
              onTap: () {
                ScaffoldMessenger.of(context).showSnackBar(const SnackBar(
                  content: Text('Feature Coming Soon'),
                ));
              },
            ),
            ListTile(
              contentPadding: EdgeInsets.zero,
              leading: const Icon(
                Icons.exit_to_app,
                color: Colors.blueGrey,
              ),
              horizontalTitleGap: 0,
              dense: true,
              title: const Text(
                'Logout',
                style: TextStyle(color: Colors.black, fontSize: 16),
              ),
              subtitle: const Text(
                'You will be logged out of your account',
                style: TextStyle(color: Colors.grey),
              ),
              onTap: logout,
            ),
          ],
        ),
      ),
      bottomNavigationBar: Container(
        color: AppColors.primary,
        height: 30,
      ),
    );
  }
}
