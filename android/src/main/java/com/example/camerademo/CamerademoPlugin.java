package com.example.camerademo;

import android.app.Activity;
import android.content.Intent;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;

/** CamerademoPlugin */
public class CamerademoPlugin implements MethodCallHandler {
  public static Activity activity;
  /** Plugin registration. */
  public static void registerWith(Registrar registrar) {
    activity = registrar.activity();
    final MethodChannel channel = new MethodChannel(registrar.messenger(), "camerademo");
    channel.setMethodCallHandler(new CamerademoPlugin());
  }

  @Override
  public void onMethodCall(MethodCall call, Result result) {
    if (call.method.equals("getPlatformVersion")) {
      result.success("Android " + android.os.Build.VERSION.RELEASE);
    } else  if (call.method.equals("jumpToNative")) {
        //接收来自flutter的指令oneAct
        //跳转到指定Activity
        Intent intent = new Intent(activity, FirstActivity.class);
        activity.startActivity(intent);

        //返回给flutter的参数
        result.success("success");
      }
      //接收来自flutter的指令twoAct
      else if (call.method.equals("jumpWithParams")) {

        //解析参数
        String text = call.argument("flutter");

        //带参数跳转到指定Activity
        Intent intent = new Intent(activity, SecondActivity.class);
        intent.putExtra(SecondActivity.VALUE, text);
        activity.startActivity(intent);

        //返回给flutter的参数
        result.success("success");
      }
    else {
      result.notImplemented();
    }
  }

}
