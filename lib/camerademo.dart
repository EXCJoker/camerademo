import 'dart:async';

import 'package:flutter/services.dart';

class Camerademo {
  static const MethodChannel _channel =
      const MethodChannel('camerademo');

  static Future<String> get platformVersion async {
    final String version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }

  static Future<String> get jumpResult async{
    String result = await _channel.invokeMethod("jumpToNative");
    return result;
  }

  static Future<String> get jumpWithParams async{
    Map<String, String> map = { "flutter": "这是一条来自flutter的参数"};
    String result = await _channel.invokeMethod("jumpWithParams",map);
    return result;
  }
}
