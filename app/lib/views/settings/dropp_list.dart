import 'package:flutter/material.dart';
import 'package:intl/intl.dart';

import '../../models/dropp.dart';
import '../../models/enums.dart';

class DroppList extends StatelessWidget {
  final List<Dropp> dropps;
  final Function(Dropp)? onTap;
  const DroppList({Key? key, required this.dropps, this.onTap})
      : super(key: key);

  @override
  Widget build(BuildContext context) {
    return ListView.separated(
        itemCount: dropps.length,
        separatorBuilder: (context, index) => const Divider(height: 0),
        itemBuilder: (context, index) {
          final dropp = dropps[index];
          return ListTile(
            trailing: Icon(
              getIcon(dropp.type),
              color: getColor(dropp.type),
              size: 32,
            ),
            horizontalTitleGap: 0,
            dense: true,
            title: Text(
              DateFormat("h:mm a - dd MMM, yyyy").format(dropp.time),
              style: const TextStyle(color: Colors.black, fontSize: 16),
            ),
            subtitle: Text(
              "${dropp.lat}, ${dropp.lng}",
              style: const TextStyle(color: Colors.grey),
            ),
            onTap: () async {
              onTap?.call(dropp);
            },
          );
        });
  }
}

IconData getIcon(DroppType type) {
  switch (type) {
    case DroppType.photo:
      return Icons.image;
    case DroppType.video:
      return Icons.videocam_rounded;
    case DroppType.text:
      return Icons.text_fields;
    case DroppType.ar:
      return Icons.android;
  }
}

Color getColor(DroppType type) {
  switch (type) {
    case DroppType.photo:
      return Colors.red;
    case DroppType.video:
      return Colors.blue;
    case DroppType.text:
      return Colors.purple;
    case DroppType.ar:
      return Colors.green;
  }
}
