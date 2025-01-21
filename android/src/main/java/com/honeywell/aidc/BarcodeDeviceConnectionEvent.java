package com.honeywell.aidc;

import java.util.EventObject;

public final class BarcodeDeviceConnectionEvent extends EventObject {
   private static final long serialVersionUID = 1L;
   private BarcodeReaderInfo mBarcodeDevice;
   private int mStatus;

   BarcodeDeviceConnectionEvent(Object source, BarcodeReaderInfo barcodeDevice, int status) {
      super(source);
      this.mBarcodeDevice = barcodeDevice;
      this.mStatus = status;
   }

   public BarcodeReaderInfo getBarcodeReaderInfo() {
      return this.mBarcodeDevice;
   }

   public int getConnectionStatus() {
      return this.mStatus;
   }
}
