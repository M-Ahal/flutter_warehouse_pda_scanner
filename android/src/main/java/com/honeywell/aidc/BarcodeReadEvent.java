package com.honeywell.aidc;

import android.graphics.Point;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;

public final class BarcodeReadEvent extends EventObject {
   private static final long serialVersionUID = 1L;
   private static final int NUM_CORNERS = 4;
   private String mData;
   private String mCharset;
   private String mCodeId;
   private String mAimId;
   private String mTimestamp;
   private List<Point> mBounds;

   BarcodeReadEvent(Object source, String data, String charset, String codeId, String aimId, String timestamp, String bounds) {
      super(source);
      DebugLog.d("Enter constructor");
      DebugLog.d("data = " + data);
      DebugLog.d("charset = " + charset);
      DebugLog.d("codeid = " + codeId);
      DebugLog.d("aimid = " + aimId);
      DebugLog.d("timestamp = " + timestamp);
      this.mData = data;
      this.mCharset = charset;
      this.mCodeId = codeId;
      this.mAimId = aimId;
      this.mTimestamp = timestamp;
      this.mBounds = this.parseBounds(bounds);
      DebugLog.d("Exit constructor");
   }

   public String getBarcodeData() {
      return this.mData;
   }

   public Charset getCharset() {
      return Charset.forName(this.mCharset);
   }

   public String getCodeId() {
      return this.mCodeId;
   }

   public String getAimId() {
      return this.mAimId;
   }

   public String getTimestamp() {
      return this.mTimestamp;
   }

   public List<Point> getBarcodeBounds() {
      return this.mBounds;
   }

   private List<Point> parseBounds(String bounds) {
      List<Point> boundsList = null;
      if (bounds != null) {
         String[] boundsArray = bounds.split(";");
         if (boundsArray.length == 4) {
            boundsList = new ArrayList<>();

            for (int i = 0; i < 4; i++) {
               String[] coordinates = boundsArray[i].split(",");
               if (coordinates.length == 2) {
                  try {
                     boundsList.add(new Point(Integer.parseInt(coordinates[0]), Integer.parseInt(coordinates[1])));
                  } catch (NumberFormatException var7) {
                     DebugLog.d("Could Not Parse Barcode Bounds " + coordinates[0] + "," + coordinates[1]);
                  }
               }
            }

            if (boundsList.size() != 4) {
               boundsList = null;
            }
         }
      }

      return boundsList;
   }
}
