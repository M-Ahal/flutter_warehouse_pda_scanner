import 'dart:async';

import 'package:flutter/cupertino.dart';
import 'package:flutter/services.dart';

class FlutterWarehousePdaScannerPlugin {
  static FlutterWarehousePdaScannerPlugin instance =
      FlutterWarehousePdaScannerPlugin._();
  late final EventChannel _eventChannel;

  final StreamController _getStringStreamController =
      StreamController.broadcast();

  Stream get getBarcodeResp => _getStringStreamController.stream;
  static const String eventChannelName = "wyb.com/pda_scanner";

  FlutterWarehousePdaScannerPlugin._() {
    //Define a channel to receive messages proactively sent by the underlying operating system
    _eventChannel = const EventChannel(eventChannelName);
    // Register message callback function; Broadcast stream to process messages sent by EventChannel
    _eventChannel.receiveBroadcastStream().listen(
          _onEvent,
          onError: _onError,
        );
  }

  void _onEvent(event) {
    if (event != null) {
      String eventString = event;
      try {
        _getStringStreamController.add(eventString);
      } on FormatException catch (e) {
        debugPrint(e.message);
      } on NoSuchMethodError catch (e) {
        debugPrint(e.toString());
      }
    }
  }

  void _onError(ex) {}
}
