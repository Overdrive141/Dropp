import 'package:dropp/assets.dart';
import 'package:dropp/colors.dart';
import 'package:dropp/models/enums.dart';
import 'package:dropp/views/home/home_view.dart';
import 'package:flutter/material.dart';

class AvatarSelectionView extends StatefulWidget {
  const AvatarSelectionView({Key? key}) : super(key: key);

  @override
  State<AvatarSelectionView> createState() => _AvatarSelectionViewState();
}

class _AvatarSelectionViewState extends State<AvatarSelectionView> {
  AvatarType selectedType = AvatarType.male;

  void showHomeView(BuildContext context) {
    Navigator.pushReplacement(
      context,
      MaterialPageRoute(
        builder: (_) => const HomeView(),
      ),
    );
  }

  void setAvatarType(AvatarType type) {
    selectedType = type;
    setState(() {});
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
            Image.asset(
              'assets/images/logo.png',
              width: MediaQuery.of(context).size.width / 3,
            ),
            const Spacer(),
            Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: const [
                Text(
                  'Select your Avatar',
                  style: TextStyle(color: Colors.black, fontSize: 16),
                ),
                Text(
                  'You can change your avatar later in the settings.',
                  style: TextStyle(color: Colors.grey),
                ),
              ],
            ),
            const SizedBox(height: 40),
            Row(
              mainAxisAlignment: MainAxisAlignment.center,
              children: [
                const SizedBox(width: 16),
                Flexible(
                    child: AvatarOption(
                  isSelected: selectedType == AvatarType.male,
                  type: AvatarType.male,
                  onTap: () => setAvatarType(AvatarType.male),
                )),
                const SizedBox(width: 32),
                Flexible(
                    child: AvatarOption(
                  isSelected: selectedType == AvatarType.female,
                  type: AvatarType.female,
                  onTap: () => setAvatarType(AvatarType.female),
                )),
                const SizedBox(width: 16),
              ],
            ),
            const Spacer(flex: 4),
            SizedBox(
              width: double.infinity,
              child: OutlinedButton(
                onPressed: () {
                  // ScaffoldMessenger.of(context).clearSnackBars();
                  // ScaffoldMessenger.of(context).showSnackBar(
                  //   const SnackBar(
                  //     content: Text('Feature Coming soon!'),
                  //   ),
                  // );
                  showHomeView(context);
                },
                child: const Padding(
                  padding: EdgeInsets.symmetric(vertical: 16.0),
                  child: Text(
                    'CONTINUE',
                    style: TextStyle(fontSize: 18),
                  ),
                ),
              ),
            )
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

class AvatarOption extends StatelessWidget {
  const AvatarOption({
    this.isSelected = false,
    Key? key,
    required this.type,
    this.onTap,
  }) : super(key: key);

  static const radius = Radius.circular(100);

  final bool isSelected;
  final AvatarType type;
  final VoidCallback? onTap;

  Color get avatarColor {
    switch (type) {
      case AvatarType.male:
        return const Color(0xffF1F8FE);
      case AvatarType.female:
        return const Color(0xffF1F8FE);
    }
  }

  String get avatarPath {
    switch (type) {
      case AvatarType.male:
        return AppAssets.avatarMale;
      case AvatarType.female:
        return AppAssets.avatarFemale;
    }
  }

  @override
  Widget build(BuildContext context) {
    return GestureDetector(
      onTap: onTap,
      child: Stack(
        clipBehavior: Clip.none,
        children: [
          Container(
            clipBehavior: Clip.antiAlias,
            decoration: BoxDecoration(
                color: avatarColor,
                borderRadius: BorderRadius.circular(8),
                border: Border.all(
                  color: isSelected ? AppColors.primary : Colors.grey[300],
                  width: 3,
                )),
            child: Container(
              clipBehavior: Clip.antiAlias,
              decoration: BoxDecoration(
                color: avatarColor,
                borderRadius: BorderRadius.circular(8),
              ),
              child: AspectRatio(
                aspectRatio: 1,
                child: Image.asset(
                  avatarPath,
                  fit: BoxFit.cover,
                ),
              ),
            ),
          ),
          if (isSelected)
            Positioned(
              top: -10,
              right: -10,
              child: Container(
                decoration: BoxDecoration(
                  color: Colors.white,
                  borderRadius: BorderRadius.circular(100),
                ),
                child: Icon(
                  Icons.check_circle_rounded,
                  color: AppColors.primary,
                  size: 30,
                ),
              ),
            )
        ],
      ),
    );
  }
}
