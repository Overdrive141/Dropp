import 'package:dropp/models/enums.dart';
import 'package:flutter/material.dart';
import 'package:story_view/story_view.dart';

import '../../models/dropp.dart';

class MoreStories extends StatefulWidget {
  const MoreStories({
    Key? key,
    required this.dropp,
  }) : super(key: key);

  final Dropp dropp;

  @override
  _MoreStoriesState createState() => _MoreStoriesState();
}

class _MoreStoriesState extends State<MoreStories> {
  final storyController = StoryController();

  @override
  void dispose() {
    storyController.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      extendBodyBehindAppBar: true,
      appBar: AppBar(
        backgroundColor: Colors.transparent,
        elevation: 0,
        automaticallyImplyLeading: true,
        iconTheme: const IconThemeData(color: Colors.white),
        actions: [
          GestureDetector(
            child: const Icon(Icons.favorite_border),
            onTap: () {},
          ),
          const SizedBox(width: 16),
        ],
      ),
      body: StoryView(
        storyItems: [
          if (widget.dropp.type == DroppType.text)
            StoryItem.text(
              title: widget.dropp.content,
              backgroundColor: Colors.purple,
              textStyle: const TextStyle(
                fontSize: 40,
              ),
            )
          else if (widget.dropp.type == DroppType.video)
            StoryItem.pageVideo(
              widget.dropp.content,
              controller: storyController,
            )
          else
            StoryItem.pageImage(
              url: widget.dropp.content,
              controller: storyController,
            ),
        ],
        onVerticalSwipeComplete: (_) {
          Navigator.pop(context);
        },
        onStoryShow: (s) {},
        onComplete: () {
          Navigator.pop(context);
        },
        progressPosition: ProgressPosition.top,
        repeat: false,
        controller: storyController,
      ),
    );
  }
}
