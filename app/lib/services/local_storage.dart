import 'package:hooks_riverpod/hooks_riverpod.dart';
import 'package:localstore/localstore.dart';

import '../models/dropp.dart';

class LocalStorageService {
  static final provider =
      Provider<LocalStorageService>((ref) => LocalStorageService());

  final Localstore db = Localstore.instance;

  CollectionRef get savedCollection => db.collection('saved');
  CollectionRef get droppCollection => db.collection('dropps');

  Future addDropp(Dropp dropp) async {
    await droppCollection.doc(dropp.id).set(dropp.toJson());
  }

  Future<List<Dropp>> getMyDropps() async {
    final docs = await droppCollection.get();
    final dropps =
        docs?.entries.map(((entry) => Dropp.fromJson(entry.value))).toList();
    return dropps ?? [];
  }

  Future saveDropp(Dropp dropp) async {
    await savedCollection.doc(dropp.id).set(dropp.toJson());
  }

  Future unsaveDropp(Dropp dropp) async {
    await savedCollection.doc(dropp.id).set({"isFav": false});
    savedCollection.doc(dropp.id).delete();
  }

  Future<bool> isSavedDropp(Dropp dropp) async {
    final doc = await savedCollection.doc(dropp.id).get();
    final isSaved = doc != null && doc["isFav"] != false;
    return isSaved;
  }

  Future<List<Dropp>> getSavedDropps() async {
    final docs = await savedCollection.get();
    final dropps =
        docs?.entries.map(((entry) => Dropp.fromJson(entry.value))).toList();
    return dropps ?? [];
  }
}
