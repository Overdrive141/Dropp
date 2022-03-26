import 'package:dropp/services/user_service.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:mocktail/mocktail.dart';

import '../mocks.dart';

void main() {
  group('User Service', () {
    late MockFirebaseAuth mockFirebaseAuth;

    setUp(() {
      mockFirebaseAuth = MockFirebaseAuth();
    });

    test('Firebase auth logs out the user properly', () async {
      when(() => mockFirebaseAuth.signOut()).thenAnswer((_) => Future.value());
      await UserService(auth: mockFirebaseAuth).logout();
      verify(() => mockFirebaseAuth.signOut()).called(1);
    });
  });
}
