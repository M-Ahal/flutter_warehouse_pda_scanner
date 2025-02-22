package com.wyb.flutter_warehouse_pda_scanner;

import android.content.Context;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;

import androidx.annotation.NonNull;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.EventChannel;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;

/**
 * FlutterWarehousePdaScannerPlugin
 */
public class FlutterWarehousePdaScannerPlugin implements FlutterPlugin, MethodCallHandler {
    /// The MethodChannel that will the communication between Flutter and native Android
    ///
    /// This local reference serves to register the plugin with the Flutter Engine and unregister it
    /// when the Flutter Engine is detached from the Activity
    private MethodChannel channel;

    private Context applicationContext;

    private static final String _channel = "wyb.com/pda_scanner";

    @Override
    public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
        channel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), "flutter_warehouse_pda_scanner");
        channel.setMethodCallHandler(this);


        new EventChannel(flutterPluginBinding.getBinaryMessenger(), _channel).setStreamHandler(
                new EventChannel.StreamHandler() {

                    private BroadcastReceiver barCodeReceiver;

                    @Override
                    public void onListen(Object ignoredArguments, EventChannel.EventSink events) {
                        barCodeReceiver = createReceiver(events);
                        IntentFilter filter = new IntentFilter();
                        for (IntentSupportedDevices device : IntentSupportedDevices.values()) {
                            filter.addAction(device.scanIntentAction);
                        }
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                            applicationContext.registerReceiver(barCodeReceiver, filter, Context.RECEIVER_NOT_EXPORTED);
                        }
                        else {
                            applicationContext.registerReceiver(barCodeReceiver, filter);
                        }
                    }

                    @Override
                    public void onCancel(Object ignoredArguments) {
                        applicationContext.unregisterReceiver(barCodeReceiver);
                        barCodeReceiver = null;
                    }
                }
        );

        applicationContext = flutterPluginBinding.getApplicationContext();

    }

    @Override
    public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
        if (call.method.equals("getPlatformVersion")) {
            result.success("Android " + android.os.Build.VERSION.RELEASE);
        } else {
            result.notImplemented();
        }
    }

    @Override
    public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
        channel.setMethodCallHandler(null);
    }

    private BroadcastReceiver createReceiver(final EventChannel.EventSink events) {
        return new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String actionName = intent.getAction();
                System.out.println(actionName);
                var deviceWithInfo = IntentSupportedDevices.fromAction(actionName);
                if (deviceWithInfo == null) {
                    events.error("error", "error: NoSuchAction", null);
                    return;
                }

                String intentData = intent.getStringExtra(deviceWithInfo.extendedDataName);
                System.out.println("Broadcast data received -> " + '"' + intentData + '"');
                events.success(intentData);
            }
        };
    }
}
