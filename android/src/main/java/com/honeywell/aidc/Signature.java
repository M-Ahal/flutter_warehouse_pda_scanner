package com.honeywell.aidc;

import android.graphics.Bitmap;

public class Signature {
   public static final String GUIDANCE_MOVE_LEFT = "moveLeft";
   public static final String GUIDANCE_MOVE_RIGHT = "moveRight";
   public static final String GUIDANCE_MOVE_UP = "moveUp";
   public static final String GUIDANCE_MOVE_DOWN = "moveDown";
   public static final String GUIDANCE_MOVE_OUT = "moveOut";
   public static final String GUIDANCE_UNSUPPORTED_SYMBOLOGY = "unsupportedSymbology";
   public static final String GUIDANCE_SUCCESS = "success";
   private Bitmap mImage;
   private String mGuidance;

   Signature(String guidance, Bitmap image) {
      super();
      this.mImage = image;
      this.mGuidance = guidance;
   }

   public Bitmap getImage() {
      return this.mImage;
   }

   public String getGuidance() {
      return this.mGuidance;
   }
}
