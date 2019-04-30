import 'package:flutter/material.dart';
import 'dart:async';

import 'package:flutter/services.dart';
import 'package:camerademo/camerademo.dart';
import 'package:camerademo/even_channel_demo.dart';

void main() => runApp(MyApp());

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  String _platformVersion = 'Unknown';
  String _chargingStatus = "Unknown";

  @override
  void initState() {
    super.initState();
    initPlatformState();
    initEvent();
  }

  void initEvent() {
    EventChannelDemo.initEvent(_onEvent, _onError);
  }

  void _onEvent(Object event) {
    print("flutter _onEvent");
    setState(() {
      _chargingStatus =
          "Battery status: ${event == 'charging' ? '' : 'dis'}  charging...";
    });
  }

  void _onError(Object error) {
    setState(() {
      _chargingStatus = 'Battery status: unknown.';
    });
  }

  // Platform messages are asynchronous, so we initialize in an async method.
  Future<void> initPlatformState() async {
    String platformVersion;
    // Platform messages may fail, so we use a try/catch PlatformException.
    try {
      platformVersion = await Camerademo.platformVersion;
    } on PlatformException {
      platformVersion = 'Failed to get platform version.';
    }

    // If the widget was removed from the tree while the asynchronous platform
    // message was in flight, we want to discard the reply rather than calling
    // setState to update our non-existent appearance.
    if (!mounted) return;

    setState(() {
      _platformVersion = platformVersion;
    });
  }

  Future<String> jumpToNative() async {
    String result;
    return result = await Camerademo.jumpResult;
  }

  jumpToNativeWithParams() async {
    await Camerademo.jumpWithParams;
  }

  stop() {
    EventChannelDemo.stop(_onEvent, _onError);
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body: Center(
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: <Widget>[
              Text('Running on: $_platformVersion\n'),
              const SizedBox(height: 16.0),
              Text(
                'Flutter to Android',
                style: new TextStyle(
                    color: Colors.blue,
                    fontSize: 20.0,
                    fontWeight: FontWeight.w700),
              ),
              RaisedButton(
                onPressed: () {
                  jumpToNative();
                },
                child: Text("跳转native - camera"),
              ),
              const SizedBox(height: 8.0),
              RaisedButton(
                  onPressed: () {
                    jumpToNativeWithParams();
                  },
                  child: Text("跳转native - 带参数")),
              const SizedBox(height: 16.0),
              Text(
                'Android to Flutter',
                style: new TextStyle(
                    color: Colors.blue,
                    fontSize: 20.0,
                    fontWeight: FontWeight.w700),
              ),
              const SizedBox(height: 8.0),
              Text(_chargingStatus),
              const SizedBox(height: 8.0),
              RaisedButton(
                onPressed: () {
                  print("flutter _onEvent");
                  stop();
                },
                child: Text("stop receive from na"),
              ),
            ],
          ),
        ),
      ),
    );
  }
}
