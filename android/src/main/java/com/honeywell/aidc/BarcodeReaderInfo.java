package com.honeywell.aidc;

public final class BarcodeReaderInfo {
   private String mName;
   private String mFriendlyName;
   private String mScanEngineId;
   private String mFullDecoderVersion;
   private String mFastDecoderVersion;
   private String mControlLogicVersion;
   private String mScanEngineVersionNumber;
   private int mFrameWidth;
   private int mFrameHeight;
   private String mScanEngineFirmwareVersion;
   private String mScanEngineSerialNumber;

   BarcodeReaderInfo(
      String name,
      String friendlyName,
      String scanEngineId,
      String fullDecodeVersion,
      String fastDecodeVersion,
      String controlLogicVersion,
      String scanEngineVersionNumber,
      String scanEngineFirmwareVersion,
      String scanEngineSerialNumber,
      int frameHeight,
      int frameWidth
   ) {
      this(name, friendlyName, scanEngineId, fullDecodeVersion, fastDecodeVersion, controlLogicVersion, scanEngineVersionNumber, frameHeight, frameWidth);
      this.mScanEngineFirmwareVersion = scanEngineFirmwareVersion;
      this.mScanEngineSerialNumber = scanEngineSerialNumber;
   }

   BarcodeReaderInfo(
      String name,
      String friendlyName,
      String scanEngineId,
      String fullDecodeVersion,
      String fastDecodeVersion,
      String controlLogicVersion,
      String scanEngineVersionNumber,
      int frameHeight,
      int frameWidth
   ) {
      super();
      this.mName = name;
      this.mFriendlyName = friendlyName;
      this.mScanEngineId = scanEngineId;
      this.mFullDecoderVersion = fullDecodeVersion;
      this.mFastDecoderVersion = fastDecodeVersion;
      this.mControlLogicVersion = controlLogicVersion;
      this.mScanEngineVersionNumber = scanEngineVersionNumber;
      this.mFrameHeight = frameHeight;
      this.mFrameWidth = frameWidth;
   }

   public String getName() {
      return this.mName;
   }

   public String getFriendlyName() {
      return this.mFriendlyName;
   }

   public String getScannerId() {
      return this.mScanEngineId;
   }

   public String getFullDecodeVersion() {
      return this.mFullDecoderVersion;
   }

   public String getFastDecodeVersion() {
      return this.mFastDecoderVersion;
   }

   public String getControlLogicVersion() {
      return this.mControlLogicVersion;
   }

   public String getScannerVersionNumber() {
      return this.mScanEngineVersionNumber;
   }

   public String getScannerFirmwareVersion() {
      return this.mScanEngineFirmwareVersion;
   }

   public String getScannerSerialNumber() {
      return this.mScanEngineSerialNumber;
   }

   public int getFrameHeight() {
      return this.mFrameHeight;
   }

   public int getFrameWidth() {
      return this.mFrameWidth;
   }
}
