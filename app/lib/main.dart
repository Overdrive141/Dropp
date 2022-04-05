import 'package:dropp/services/api_service.dart';
import 'package:dropp/services/user_service.dart';
import 'package:dropp/views/authentication/authentication_view.dart';
import 'package:dropp/views/home/home_view.dart';
import 'package:firebase_core/firebase_core.dart';
import 'package:flutter/material.dart';
import 'package:hooks_riverpod/hooks_riverpod.dart';

Future<void> main() async {
  WidgetsFlutterBinding.ensureInitialized();

  await Firebase.initializeApp();

  runApp(ProviderScope(
    overrides: [ApiService.provider.overrideWithValue(FakeApi())],
    child: const MyApp(),
  ));
}

class MyApp extends ConsumerWidget {
  const MyApp({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context, WidgetRef ref) {
    final isLoggedIn = ref.watch(UserService.provider).hasUser;
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      title: 'Dropp',
      theme: ThemeData(
        primarySwatch: Colors.orange,
      ),
      home: (isLoggedIn ? const HomeView() : const AuthenticationView()),
    );
  }
}
