abstract class AppAssets {
  static get googleIcon => 'assets/images/google.png';
  static get facebookIcon => 'assets/images/facebook.png';
  static get twitterIcon => 'assets/images/twitter.png';
  static get githubIcon => 'assets/images/github.png';

  static get avatarMale => 'assets/images/avatar1.png';
  static get avatarFemale => 'assets/images/avatar2.png';

  static get marker0 => 'assets/markers/0.png';
  static get marker1 => 'assets/markers/1.png';
  static get dropMarker => 'assets/markers/flag.png';

  static get progressAnimation => 'assets/lottie/progress.json';
  static get bubblesAnimation => 'assets/lottie/bubbles.json';

  static List<String> get modelOptions => [
        'Duck',
        'Buggy',
        'RiggedFigure',
        'Lantern',
        'CesiumMan',
        'CesiumMilkTruck'
      ];
}
