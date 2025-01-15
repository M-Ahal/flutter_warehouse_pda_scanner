package com.wyb.flutter_warehouse_pda_scanner;

import androidx.annotation.Nullable;

public enum SupportedDevices {
    UROVO("android.intent.ACTION_DECODE_DATA", "barcode_string"),
    NEWLAND("nlscan.action.SCANNER_RESULT", "SCAN_BARCODE1"),
    HONEYWELL("com.honeywell.decode.intent.action.EDIT_DATA", "data");

    final String scanAction;
    final String extendedDataName;

    SupportedDevices(String scanAction, String extendedDataName) {
        this.scanAction = scanAction;
        this.extendedDataName = extendedDataName;
    }

    @Nullable
    static SupportedDevices fromAction(String scanAction) {
        for (SupportedDevices device : SupportedDevices.values()) {
            if (device.scanAction.equals(scanAction)) {
                return device;
            }
        }
        return null;
    }
}
