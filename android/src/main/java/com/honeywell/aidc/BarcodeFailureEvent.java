package com.honeywell.aidc;

import java.util.EventObject;

public final class BarcodeFailureEvent extends EventObject {
   private static final long serialVersionUID = 1L;
   private String mTimestamp;

   BarcodeFailureEvent(Object source, String timestamp) {
      super(source);
      DebugLog.d("Enter constructor");
      DebugLog.d("timestamp = " + timestamp);
      this.mTimestamp = timestamp;
      DebugLog.d("Exit constructor");
   }

   public String getTimestamp() {
      return this.mTimestamp;
   }
}
