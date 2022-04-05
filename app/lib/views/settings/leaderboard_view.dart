import 'package:dropp/colors.dart';
import 'package:dropp/services/api_service.dart';
import 'package:dropp/widgets/spinner.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:hooks_riverpod/hooks_riverpod.dart';

import '../../models/dropp.dart';
import '../../widgets/app_error.dart';

final leaderboard = FutureProvider<List<DroppUser>>(((ref) {
  return ref.read(ApiService.provider).getLeaderBoard();
}));

class LeaderboardView extends ConsumerStatefulWidget {
  const LeaderboardView({Key? key}) : super(key: key);

  @override
  ConsumerState<LeaderboardView> createState() => _LeaderboardViewState();
}

class _LeaderboardViewState extends ConsumerState<LeaderboardView>
    with SingleTickerProviderStateMixin {
  late final _controller = TabController(length: 2, vsync: this);

  @override
  Widget build(BuildContext context) {
    final leaderboardFuture = ref.watch(leaderboard);
    return Scaffold(
      appBar: AppBar(
        systemOverlayStyle: SystemUiOverlayStyle.light,
        backgroundColor: Colors.white,
        iconTheme: const IconThemeData(color: Colors.black),
        title: const Text('Leaderboards'),
        bottom: TabBar(
          controller: _controller,
          tabs: ["DROPPER", "EXPLORER"]
              .map((s) => Padding(
                    padding: const EdgeInsets.all(8.0),
                    child: Text(s),
                  ))
              .toList(),
        ),
      ),
      backgroundColor: Colors.white,
      body: TabBarView(
        controller: _controller,
        children: [
          leaderboardFuture.when(
            data: (data) => RefreshIndicator(
              onRefresh: () async {
                return await ref.refresh(leaderboard);
              },
              child: LeaderboardList(
                data: data,
              ),
            ),
            error: (_, __) => AppError(
              onRetry: () {
                ref.refresh(leaderboard);
              },
            ),
            loading: () => const Spinner(),
          ),
          leaderboardFuture.when(
            data: (data) => RefreshIndicator(
              onRefresh: () async {
                return await ref.refresh(leaderboard);
              },
              child: LeaderboardList(
                data: data,
              ),
            ),
            error: (_, __) => AppError(
              onRetry: () {
                ref.refresh(leaderboard);
              },
            ),
            loading: () => const Spinner(),
          ),
        ],
      ),
    );
  }
}

class LeaderboardList extends StatelessWidget {
  final List<DroppUser> data;
  const LeaderboardList({Key? key, required this.data}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return ListView.separated(
        itemCount: data.length,
        separatorBuilder: (context, index) => const Divider(height: 0),
        itemBuilder: (context, index) {
          final user = data[index];
          return ListTile(
            leading: CircleAvatar(
              backgroundColor: Colors.orange.withOpacity(0.1),
              child: Text(
                "${index + 1}",
                style: TextStyle(
                  color: AppColors.primary,
                  fontSize: 24,
                  fontWeight: FontWeight.bold,
                ),
              ),
            ),
            trailing: index <= 2
                ? const Icon(
                    Icons.emoji_events,
                    color: Colors.orangeAccent,
                    size: 32,
                  )
                : null,
            title: Text(
              user.username,
              style: const TextStyle(color: Colors.black, fontSize: 16),
            ),
            subtitle: Text(
              "${user.score} Points",
              style: const TextStyle(color: Colors.grey),
            ),
          );
        });
  }
}
