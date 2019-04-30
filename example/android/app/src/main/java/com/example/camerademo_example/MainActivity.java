package com.example.camerademo_example;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.util.Log;

import io.flutter.app.FlutterActivity;
import io.flutter.plugin.common.EventChannel;
import io.flutter.plugins.GeneratedPluginRegistrant;

public class MainActivity extends FlutterActivity {
  public static final String CHANNELTOFLUTTER = "com.example/lib";
  private BroadcastReceiver chargingStateChangeReceiver;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    GeneratedPluginRegistrant.registerWith(this);
    new EventChannel(getFlutterView(),CHANNELTOFLUTTER).setStreamHandler(new EventChannel.StreamHandler() {
      // 接收电池广播的BroadcastReceiver。

      // 这个onListen是Flutter端开始监听这个channel时的回调，第二个参数 EventSink是用来传数据的载体。
      @Override
      public void onListen(Object o, EventChannel.EventSink eventSink) {
        Log.d("EventChannel","onListen");
        chargingStateChangeReceiver = createChargingStateChangeReceiver(eventSink);
        registerReceiver(chargingStateChangeReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));

      }

      @Override
      public void onCancel(Object o) {
        // 对面不再接收
        unregisterReceiver(chargingStateChangeReceiver);
        chargingStateChangeReceiver = null;
      }
    });
  }

  @Override
  protected void onDestroy() {
    unregisterReceiver(chargingStateChangeReceiver);
    chargingStateChangeReceiver = null;
    super.onDestroy();

  }

  private BroadcastReceiver createChargingStateChangeReceiver(final EventChannel.EventSink events) {
    return new BroadcastReceiver() {
      @Override
      public void onReceive(Context context, Intent intent) {
        int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        Log.d("EventChannel","onReceive");
        if (status == BatteryManager.BATTERY_STATUS_UNKNOWN) {
          events.error("UNAVAILABLE", "Charging status unavailable", null);
        } else {
          boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
                  status == BatteryManager.BATTERY_STATUS_FULL;
          // 把电池状态发给Flutter
          events.success(isCharging ? "charging" : "discharging");
        }
      }
    };
  }
}
