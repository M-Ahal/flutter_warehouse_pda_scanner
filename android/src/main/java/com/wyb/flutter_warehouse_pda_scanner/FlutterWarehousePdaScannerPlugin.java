package com.wyb.flutter_warehouse_pda_scanner;

import android.content.Context;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.widget.ListView;

import androidx.annotation.NonNull;

import com.honeywell.aidc.AidcManager;
import com.honeywell.aidc.BarcodeFailureEvent;
import com.honeywell.aidc.BarcodeReadEvent;
import com.honeywell.aidc.BarcodeReader;
import com.honeywell.aidc.InvalidScannerNameException;
import com.honeywell.aidc.UnsupportedPropertyException;

import java.util.HashMap;
import java.util.Map;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.EventChannel;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;

/**
 * FlutterWarehousePdaScannerPlugin
 */
public class FlutterWarehousePdaScannerPlugin implements FlutterPlugin, MethodCallHandler, BarcodeReader.BarcodeListener {
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
        initHoneywell();
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

    private com.honeywell.aidc.BarcodeReader barcodeReader;
    private ListView barcodeList;
    private AidcManager manager;

    private void initHoneywell() {
        // create the AidcManager providing a Context and a
        // CreatedCallback implementation.
        AidcManager.create(applicationContext, aidcManager -> {
            manager = aidcManager;
            try{
                barcodeReader = manager.createBarcodeReader();
            }
            catch (InvalidScannerNameException e){
                System.out.println("Invalid Scanner Name Exception: " + e.getMessage());
            }
            catch (Exception e){
                System.out.println("Exception: " + e.getMessage());
            }
        });

        if (barcodeReader == null) {
            System.out.println("Error: No barcode reader created");
            return;
        }
        barcodeReader.addBarcodeListener(this);
        // set the trigger mode to client control
        try {
            barcodeReader.setProperty(BarcodeReader.PROPERTY_TRIGGER_CONTROL_MODE,
                    BarcodeReader.TRIGGER_CONTROL_MODE_AUTO_CONTROL);
        } catch (UnsupportedPropertyException e) {
            System.out.println("Failed to apply properties");
        }
        Map<String, Object> properties = new HashMap<>();
        // Set Symbologies On/Off
        properties.put(BarcodeReader.PROPERTY_CODE_128_ENABLED, true);
        properties.put(BarcodeReader.PROPERTY_GS1_128_ENABLED, true);
        properties.put(BarcodeReader.PROPERTY_QR_CODE_ENABLED, true);
        properties.put(BarcodeReader.PROPERTY_CODE_39_ENABLED, true);
        properties.put(BarcodeReader.PROPERTY_DATAMATRIX_ENABLED, true);
        properties.put(BarcodeReader.PROPERTY_UPC_A_ENABLE, true);
        properties.put(BarcodeReader.PROPERTY_EAN_13_ENABLED, false);
        properties.put(BarcodeReader.PROPERTY_AZTEC_ENABLED, false);
        properties.put(BarcodeReader.PROPERTY_CODABAR_ENABLED, false);
        properties.put(BarcodeReader.PROPERTY_INTERLEAVED_25_ENABLED, false);
        properties.put(BarcodeReader.PROPERTY_PDF_417_ENABLED, false);
        // Set Max Code 39 barcode length
        properties.put(BarcodeReader.PROPERTY_CODE_39_MAXIMUM_LENGTH, 10);
        // Turn on center decoding
        properties.put(BarcodeReader.PROPERTY_CENTER_DECODE, true);
        // Enable bad read response
        properties.put(BarcodeReader.PROPERTY_NOTIFICATION_BAD_READ_ENABLED, true);
        // Sets time period for decoder timeout in any mode
        properties.put(BarcodeReader.PROPERTY_DECODER_TIMEOUT,  400);
        // Apply the settings
        barcodeReader.setProperties(properties);
    }

    @Override
    public void onBarcodeEvent(BarcodeReadEvent barcodeReadEvent) {
        System.out.println("Barcode read event happened -> " + '"' + barcodeReadEvent.getBarcodeData() + '"');
    }

    @Override
    public void onFailureEvent(BarcodeFailureEvent barcodeFailureEvent) {
        System.out.println("Barcode failure event happened -> " + '"' + barcodeFailureEvent.toString() + '"');
    }
}
