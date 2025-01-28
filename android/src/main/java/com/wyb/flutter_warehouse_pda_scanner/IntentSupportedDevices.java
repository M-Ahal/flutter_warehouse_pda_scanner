package com.wyb.flutter_warehouse_pda_scanner;

import androidx.annotation.Nullable;

public enum IntentSupportedDevices {
    UROVO("android.intent.ACTION_DECODE_DATA", "barcode_string"),
    NEWLAND("nlscan.action.SCANNER_RESULT", "SCAN_BARCODE1");

    final String scanIntentAction;
    final String extendedDataName;

    IntentSupportedDevices(String scanIntentAction, String extendedDataName) {
        this.scanIntentAction = scanIntentAction;
        this.extendedDataName = extendedDataName;
    }

    @Nullable
    static IntentSupportedDevices fromAction(String scanAction) {
        for (IntentSupportedDevices device : IntentSupportedDevices.values()) {
            if (device.scanIntentAction.equals(scanAction)) {
                return device;
            }
        }
        return null;
    }
}
