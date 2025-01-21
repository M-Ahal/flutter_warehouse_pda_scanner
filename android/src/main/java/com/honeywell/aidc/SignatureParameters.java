package com.honeywell.aidc;

public class SignatureParameters {
   private int mAspectRatio;
   private int mHorizontalOffset;
   private int mVerticalOffset;
   private int mWidth;
   private int mHeight;
   private int mResolution;
   private boolean mBinarized;

   public SignatureParameters() {
      super();
   }

   public SignatureParameters(int aspectRatio, int horizontalOffset, int verticalOffset, int width, int height, int resolution, boolean binarized) {
      super();
      this.mAspectRatio = aspectRatio;
      this.mHorizontalOffset = horizontalOffset;
      this.mVerticalOffset = verticalOffset;
      this.mWidth = width;
      this.mHeight = height;
      this.mResolution = resolution;
      this.mBinarized = binarized;
   }

   public int getAspectRatio() {
      return this.mAspectRatio;
   }

   public void setAspectRatio(int aspectRatio) {
      this.mAspectRatio = aspectRatio;
   }

   public int getHorizontalOffset() {
      return this.mHorizontalOffset;
   }

   public void setHorizontalOffset(int horizontalOffset) {
      this.mHorizontalOffset = horizontalOffset;
   }

   public int getVerticalOffset() {
      return this.mVerticalOffset;
   }

   public void setVerticalOffset(int verticalOffset) {
      this.mVerticalOffset = verticalOffset;
   }

   public void setWidth(int width) {
      this.mWidth = width;
   }

   public int getWidth() {
      return this.mWidth;
   }

   public void setHeight(int height) {
      this.mHeight = height;
   }

   public int getHeight() {
      return this.mHeight;
   }

   public void setResolution(int resolution) {
      this.mResolution = resolution;
   }

   public int getResolution() {
      return this.mResolution;
   }

   public void setBinarized(boolean binarized) {
      this.mBinarized = binarized;
   }

   public boolean isBinarized() {
      return this.mBinarized;
   }
}
