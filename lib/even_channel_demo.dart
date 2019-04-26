import 'dart:async';

import 'package:flutter/services.dart';

class EventChannelDemo{
  static const EventChannel _eventChannel = const EventChannel('com.example/lib');

  static void  initEvent(void backEvent(dynamic data),void backError(dynamic error)){
    _eventChannel.receiveBroadcastStream().listen(backEvent, onError: backError);
  }


  static void  stop(void backEvent(dynamic data),void backError(dynamic error)){
    _eventChannel.receiveBroadcastStream().listen(backEvent, onError:backError).cancel();
  }

}