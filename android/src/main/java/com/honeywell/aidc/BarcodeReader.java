package com.honeywell.aidc;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.Parcelable.Creator;
import com.honeywell.IExecutor;
import com.honeywell.Message;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.EventObject;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class BarcodeReader implements Parcelable {
   public static final String PROPERTY_IMAGER_LIGHT_INTENSITY = "IMG_ILLUM_INTENSITY";
   public static final String PROPERTY_IMAGER_EXPOSURE = "IMG_EXPOSURE";
   public static final String PROPERTY_IMAGER_GAIN = "IMG_GAIN";
   public static final String PROPERTY_IMAGER_MAXIMUM_EXPOSURE = "IMG_MAX_EXPOSURE";
   public static final String PROPERTY_IMAGER_MAXIMUM_GAIN = "IMG_MAX_GAIN";
   public static final String PROPERTY_IMAGER_TARGET_VALUE = "IMG_TARGET_VALUE";
   public static final String PROPERTY_IMAGER_TARGET_ACCEPTABLE_OFFSET = "IMG_TARGET_ACCEPTABLE_OFFSET";
   public static final String PROPERTY_IMAGER_REJECTION_LIMIT = "IMG_REJECTION_LIMIT";
   public static final String PROPERTY_IMAGER_TARGET_PERCENTILE = "IMG_TARGET_PERCENTILE";
   public static final String PROPERTY_IMAGER_EXPOSURE_MODE = "IMG_EXPOSURE_MODE";
   public static final String PROPERTY_IMAGER_SAMPLE_METHOD = "IMG_SAMPLE_METHOD";
   public static final String IMAGER_EXPOSURE_MODE_FIXED = "fixed";
   public static final String IMAGER_EXPOSURE_MODE_AUTO_SENSOR = "autoSensor";
   public static final String IMAGER_EXPOSURE_MODE_AUTO_EXPOSURE = "autoExposure";
   public static final String IMAGER_EXPOSURE_MODE_CONTEXT_SENSITIVE = "contextSensitive";
   public static final String IMAGER_SAMPLE_METHOD_UNIFORM = "uniform";
   public static final String IMAGER_SAMPLE_METHOD_CENTER = "center";
   public static final String IMAGER_SAMPLE_METHOD_CENTER_WEIGHTED = "centerWeighted";
   public static final String PROPERTY_IMAGER_OVERRIDE_RECOMMENDED_VALUES = "IMG_OVERRIDE_RECOMMENDED_VALUES";
   public static final String PROPERTY_NOTIFICATION_GOOD_READ_ENABLED = "NTF_GOOD_READ_ENABLED";
   public static final String PROPERTY_NOTIFICATION_BAD_READ_ENABLED = "NTF_BAD_READ_ENABLED";
   public static final String PROPERTY_NOTIFICATION_VIBRATE_ENABLED = "NTF_VIBRATE_ENABLED";
   public static final String PROPERTY_TRIGGER_CONTROL_MODE = "TRIG_CONTROL_MODE";
   public static final String PROPERTY_TRIGGER_AUTO_MODE_TIMEOUT = "TRIG_AUTO_MODE_TIMEOUT";
   public static final String TRIGGER_CONTROL_MODE_DISABLE = "disable";
   public static final String TRIGGER_CONTROL_MODE_AUTO_CONTROL = "autoControl";
   public static final String TRIGGER_CONTROL_MODE_CLIENT_CONTROL = "clientControl";
   public static final String PROPERTY_TRIGGER_SCAN_DELAY = "TRIG_SCAN_DELAY";
   public static final String PROPERTY_TRIGGER_SCAN_MODE = "TRIG_SCAN_MODE";
   public static final String TRIGGER_SCAN_MODE_ONESHOT = "oneShot";
   public static final String TRIGGER_SCAN_MODE_CONTINUOUS = "continuous";
   public static final String TRIGGER_SCAN_MODE_READ_ON_RELEASE = "readOnRelease";
   public static final String TRIGGER_SCAN_MODE_READ_ON_SECOND_TRIGGER_PRESS = "readOnSecondTriggerPress";
   public static final String PROPERTY_TRIGGER_SCAN_SAME_SYMBOL_TIMEOUT_ENABLED = "TRIG_SCAN_SAME_SYMBOL_TIMEOUT_ENABLED";
   public static final String PROPERTY_TRIGGER_SCAN_SAME_SYMBOL_TIMEOUT = "TRIG_SCAN_SAME_SYMBOL_TIMEOUT";
   public static final String PROPERTY_CODE_128_ENABLED = "DEC_CODE128_ENABLED";
   public static final String PROPERTY_CODE_128_MINIMUM_LENGTH = "DEC_CODE128_MIN_LENGTH";
   public static final String PROPERTY_CODE_128_MAXIMUM_LENGTH = "DEC_CODE128_MAX_LENGTH";
   public static final String PROPERTY_CODE_128_SHORT_MARGIN = "DEC_C128_SHORT_MARGIN";
   public static final String SHORT_MARGIN_DISABLED = "disabled";
   public static final String SHORT_MARGIN_ENABLED = "partial";
   public static final String SHORT_MARGIN_ENABLE_BOTH_ENDS = "full";
   public static final String PROPERTY_GS1_128_ENABLED = "DEC_GS1_128_ENABLED";
   public static final String PROPERTY_GS1_128_MINIMUM_LENGTH = "DEC_GS1_128_MIN_LENGTH";
   public static final String PROPERTY_GS1_128_MAXIMUM_LENGTH = "DEC_GS1_128_MAX_LENGTH";
   public static final String PROPERTY_ISBT_128_ENABLED = "DEC_C128_ISBT_ENABLED";
   public static final String PROPERTY_CODE_39_ENABLED = "DEC_CODE39_ENABLED";
   public static final String PROPERTY_CODE_39_MINIMUM_LENGTH = "DEC_CODE39_MIN_LENGTH";
   public static final String PROPERTY_CODE_39_MAXIMUM_LENGTH = "DEC_CODE39_MAX_LENGTH";
   public static final String PROPERTY_CODE_39_CHECK_DIGIT_MODE = "DEC_CODE39_CHECK_DIGIT_MODE";
   public static final String PROPERTY_CODE_39_FULL_ASCII_ENABLED = "DEC_CODE39_FULL_ASCII_ENABLED";
   public static final String PROPERTY_CODE_39_START_STOP_TRANSMIT_ENABLED = "DEC_CODE39_START_STOP_TRANSMIT";
   public static final String CODE_39_CHECK_DIGIT_MODE_NO_CHECK = "noCheck";
   public static final String CODE_39_CHECK_DIGIT_MODE_CHECK = "check";
   public static final String CODE_39_CHECK_DIGIT_MODE_CHECK_AND_STRIP = "checkAndStrip";
   public static final String PROPERTY_CODE_39_BASE_32_ENABLED = "DEC_CODE39_BASE32_ENABLED";
   public static final String PROPERTY_DATAMATRIX_ENABLED = "DEC_DATAMATRIX_ENABLED";
   public static final String PROPERTY_DATAMATRIX_MINIMUM_LENGTH = "DEC_DATAMATRIX_MIN_LENGTH";
   public static final String PROPERTY_DATAMATRIX_MAXIMUM_LENGTH = "DEC_DATAMATRIX_MAX_LENGTH";
   public static final String PROPERTY_GRIDMATRIX_ENABLED = "DEC_GRIDMATRIX_ENABLED";
   public static final String PROPERTY_GRIDMATRIX_MINIMUM_LENGTH = "DEC_GRIDMATRIX_MIN_LENGTH";
   public static final String PROPERTY_GRIDMATRIX_MAXIMUM_LENGTH = "DEC_GRIDMATRIX_MAX_LENGTH";
   public static final String PROPERTY_UPC_A_ENABLE = "DEC_UPCA_ENABLE";
   public static final String PROPERTY_UPC_A_TRANSLATE_EAN13 = "DEC_UPCA_TRANSLATE_TO_EAN13";
   public static final String PROPERTY_UPC_A_COUPON_CODE_MODE_ENABLED = "DEC_COUPON_CODE_MODE";
   public static final String PROPERTY_UPC_A_COMBINE_COUPON_CODE_MODE_ENABLED = "DEC_COMBINE_COUPON_CODES";
   public static final String PROPERTY_UPC_A_CHECK_DIGIT_TRANSMIT_ENABLED = "DEC_UPCA_CHECK_DIGIT_TRANSMIT";
   public static final String PROPERTY_UPC_A_NUMBER_SYSTEM_TRANSMIT_ENABLED = "DEC_UPCA_NUMBER_SYSTEM_TRANSMIT";
   public static final String PROPERTY_UPC_A_TWO_CHAR_ADDENDA_ENABLED = "DEC_UPCA_2CHAR_ADDENDA_ENABLED";
   public static final String PROPERTY_UPC_A_FIVE_CHAR_ADDENDA_ENABLED = "DEC_UPCA_5CHAR_ADDENDA_ENABLED";
   public static final String PROPERTY_UPC_A_ADDENDA_REQUIRED_ENABLED = "DEC_UPCA_ADDENDA_REQUIRED";
   public static final String PROPERTY_UPC_A_ADDENDA_SEPARATOR_ENABLED = "DEC_UPCA_ADDENDA_SEPARATOR";
   public static final String PROPERTY_UPC_E_ENABLED = "DEC_UPCE0_ENABLED";
   public static final String PROPERTY_UPC_E_E1_ENABLED = "DEC_UPCE1_ENABLED";
   public static final String PROPERTY_UPC_E_EXPAND_TO_UPC_A = "DEC_UPCE_EXPAND";
   public static final String PROPERTY_UPC_E_CHECK_DIGIT_TRANSMIT_ENABLED = "DEC_UPCE_CHECK_DIGIT_TRANSMIT";
   public static final String PROPERTY_UPC_E_NUMBER_SYSTEM_TRANSMIT_ENABLED = "DEC_UPCE_NUMBER_SYSTEM_TRANSMIT";
   public static final String PROPERTY_UPC_E_TWO_CHAR_ADDENDA_ENABLED = "DEC_UPCE_2CHAR_ADDENDA_ENABLED";
   public static final String PROPERTY_UPC_E_FIVE_CHAR_ADDENDA_ENABLED = "DEC_UPCE_5CHAR_ADDENDA_ENABLED";
   public static final String PROPERTY_UPC_E_ADDENDA_REQUIRED_ENABLED = "DEC_UPCE_ADDENDA_REQUIRED";
   public static final String PROPERTY_UPC_E_ADDENDA_SEPARATOR_ENABLED = "DEC_UPCE_ADDENDA_SEPARATOR";
   public static final String PROPERTY_EAN_8_ENABLED = "DEC_EAN8_ENABLED";
   public static final String PROPERTY_EAN_8_CHECK_DIGIT_TRANSMIT_ENABLED = "DEC_EAN8_CHECK_DIGIT_TRANSMIT";
   public static final String PROPERTY_EAN_8_TWO_CHAR_ADDENDA_ENABLED = "DEC_EAN8_2CHAR_ADDENDA_ENABLED";
   public static final String PROPERTY_EAN_8_FIVE_CHAR_ADDENDA_ENABLED = "DEC_EAN8_5CHAR_ADDENDA_ENABLED";
   public static final String PROPERTY_EAN_8_ADDENDA_REQUIRED_ENABLED = "DEC_EAN8_ADDENDA_REQUIRED";
   public static final String PROPERTY_EAN_8_ADDENDA_SEPARATOR_ENABLED = "DEC_EAN8_ADDENDA_SEPARATOR";
   public static final String PROPERTY_EAN_13_ENABLED = "DEC_EAN13_ENABLED";
   public static final String PROPERTY_EAN_13_CHECK_DIGIT_TRANSMIT_ENABLED = "DEC_EAN13_CHECK_DIGIT_TRANSMIT";
   public static final String PROPERTY_EAN_13_TWO_CHAR_ADDENDA_ENABLED = "DEC_EAN13_2CHAR_ADDENDA_ENABLED";
   public static final String PROPERTY_EAN_13_FIVE_CHAR_ADDENDA_ENABLED = "DEC_EAN13_5CHAR_ADDENDA_ENABLED";
   public static final String PROPERTY_EAN_13_ADDENDA_REQUIRED_ENABLED = "DEC_EAN13_ADDENDA_REQUIRED";
   public static final String PROPERTY_EAN_13_ADDENDA_SEPARATOR_ENABLED = "DEC_EAN13_ADDENDA_SEPARATOR";
   public static final String PROPERTY_AZTEC_ENABLED = "DEC_AZTEC_ENABLED";
   public static final String PROPERTY_AZTEC_MINIMUM_LENGTH = "DEC_AZTEC_MIN_LENGTH";
   public static final String PROPERTY_AZTEC_MAXIMUM_LENGTH = "DEC_AZTEC_MAX_LENGTH";
   public static final String PROPERTY_CHINA_POST_ENABLED = "DEC_HK25_ENABLED";
   public static final String PROPERTY_CHINA_POST_MINIMUM_LENGTH = "DEC_HK25_MIN_LENGTH";
   public static final String PROPERTY_CHINA_POST_MAXIMUM_LENGTH = "DEC_HK25_MAX_LENGTH";
   public static final String PROPERTY_CODABAR_ENABLED = "DEC_CODABAR_ENABLED";
   public static final String PROPERTY_CODABAR_MINIMUM_LENGTH = "DEC_CODABAR_MIN_LENGTH";
   public static final String PROPERTY_CODABAR_MAXIMUM_LENGTH = "DEC_CODABAR_MAX_LENGTH";
   public static final String PROPERTY_CODABAR_START_STOP_TRANSMIT_ENABLED = "DEC_CODABAR_START_STOP_TRANSMIT";
   public static final String PROPERTY_CODABAR_CHECK_DIGIT_MODE = "DEC_CODABAR_CHECK_DIGIT_MODE";
   public static final String PROPERTY_CODABAR_CONCAT_ENABLED = "DEC_CODABAR_CONCAT_ENABLED";
   public static final String CODABAR_CHECK_DIGIT_MODE_NO_CHECK = "noCheck";
   public static final String CODABAR_CHECK_DIGIT_MODE_CHECK = "check";
   public static final String CODABAR_CHECK_DIGIT_MODE_CHECK_AND_STRIP = "checkAndStrip";
   public static final String PROPERTY_CODABLOCK_A_ENABLED = "DEC_CODABLOCK_A_ENABLED";
   public static final String PROPERTY_CODABLOCK_A_MINIMUM_LENGTH = "DEC_CODABLOCK_A_MIN_LENGTH";
   public static final String PROPERTY_CODABLOCK_A_MAXIMUM_LENGTH = "DEC_CODABLOCK_A_MAX_LENGTH";
   public static final String PROPERTY_CODABLOCK_F_ENABLED = "DEC_CODABLOCK_F_ENABLED";
   public static final String PROPERTY_CODABLOCK_F_MINIMUM_LENGTH = "DEC_CODABLOCK_F_MIN_LENGTH";
   public static final String PROPERTY_CODABLOCK_F_MAXIMUM_LENGTH = "DEC_CODABLOCK_F_MAX_LENGTH";
   public static final String PROPERTY_CODE_11_ENABLED = "DEC_CODE11_ENABLED";
   public static final String PROPERTY_CODE_11_MINIMUM_LENGTH = "DEC_CODE11_MIN_LENGTH";
   public static final String PROPERTY_CODE_11_MAXIMUM_LENGTH = "DEC_CODE11_MAX_LENGTH";
   public static final String PROPERTY_CODE_11_CHECK_DIGIT_MODE = "DEC_CODE11_CHECK_DIGIT_MODE";
   public static final String CODE_11_CHECK_DIGIT_MODE_DOUBLE_DIGIT_CHECK = "doubleDigitCheck";
   public static final String CODE_11_CHECK_DIGIT_MODE_SINGLE_DIGIT_CHECK = "singleDigitCheck";
   public static final String CODE_11_CHECK_DIGIT_MODE_DOUBLE_DIGIT_CHECK_AND_STRIP = "doubleDigitCheckAndStrip";
   public static final String CODE_11_CHECK_DIGIT_MODE_SINGLE_DIGIT_CHECK_AND_STRIP = "singleDigitCheckAndStrip";
   public static final String PROPERTY_CODE_93_ENABLED = "DEC_CODE93_ENABLED";
   public static final String PROPERTY_CODE_93_MINIMUM_LENGTH = "DEC_CODE93_MIN_LENGTH";
   public static final String PROPERTY_CODE_93_MAXIMUM_LENGTH = "DEC_CODE93_MAX_LENGTH";
   public static final String PROPERTY_DEC_CODE93_HIGH_DENSITY = "DEC_CODE93_HIGH_DENSITY";
   public static final String PROPERTY_COMPOSITE_ENABLED = "DEC_COMPOSITE_ENABLED";
   public static final String PROPERTY_COMPOSITE_MINIMUM_LENGTH = "DEC_COMPOSITE_MIN_LENGTH";
   public static final String PROPERTY_COMPOSITE_MAXIMUM_LENGTH = "DEC_COMPOSITE_MAX_LENGTH";
   public static final String PROPERTY_COMPOSITE_WITH_UPC_ENABLED = "DEC_COMPOSITE_WITH_UPC_ENABLED";
   public static final String PROPERTY_COMBINE_COMPOSITES = "DEC_COMBINE_COMPOSITES";
   public static final String PROPERTY_DIGIMARC_ENABLED = "DEC_DIGIMARC_ENABLED";
   public static final String PROPERTY_DIGIMARC_CONVERSION = "DEC_DIGIMARC_CONVERSION";
   public static final String DIGIMARC_CONVERSION_NO_CONVERSION = "noConversion";
   public static final String DIGIMARC_CONVERSION_CONVERT_TO_EQUIVALENT = "convertToEquivalent";
   public static final String PROPERTY_DIGIMARC_SCALE_BLOCKS = "DEC_DIGIMARC_SCALE_BLOCKS";
   public static final String DIGIMARC_SCALE_BLOCKS_USE_BOTH_SCALE1_AND_SCALE3_BLOCKS = "useBothScale1AndScale3Blocks";
   public static final String DIGIMARC_SCALE_BLOCKS_USE_SCALE1_BLOCKS = "useScale1Blocks";
   public static final String DIGIMARC_SCALE_BLOCKS_USE_SCALE3_BLOCKS = "useScale3Blocks";
   public static final String PROPERTY_DIGIMARC_SHAPE_DETECTION = "DEC_DIGIMARC_SHAPE_DETECTION";
   public static final String PROPERTY_CODE_DOTCODE_ENABLED = "DEC_DOTCODE_ENABLED";
   public static final String PROPERTY_CODE_DOTCODE_MINIMUM_LENGTH = "DEC_DOTCODE_MIN_LENGTH";
   public static final String PROPERTY_CODE_DOTCODE_MAXIMUM_LENGTH = "DEC_DOTCODE_MAX_LENGTH";
   public static final String PROPERTY_EANUCC_EMULATION_MODE = "DEC_EANUCC_EMULATION_MODE";
   public static final String EANUCC_EMULATION_MODE_GS1_EMULATION_OFF = "gs1EmulationOff";
   public static final String EANUCC_EMULATION_MODE_GS1_128_EMULATION = "gs1128Emulation";
   public static final String EANUCC_EMULATION_MODE_GS1_DATABAR_EMULATION = "gs1DatabarEmulation";
   public static final String EANUCC_EMULATION_MODE_GS1_CODE_EXPANSION_OFF = "gs1CodeExpansionOff";
   public static final String EANUCC_EMULATION_MODE_GS1_EAN8_TO_EAN13_CONVERSION = "gs1EAN8toEAN13Conversion";
   public static final String PROPERTY_HAX_XIN_ENABLED = "DEC_HANXIN_ENABLED";
   public static final String PROPERTY_HAX_XIN_MINIMUM_LENGTH = "DEC_HANXIN_MIN_LENGTH";
   public static final String PROPERTY_HAX_XIN_MAXIMUM_LENGTH = "DEC_HANXIN_MAX_LENGTH";
   public static final String PROPERTY_IATA_25_ENABLED = "DEC_IATA25_ENABLED";
   public static final String PROPERTY_IATA_25_MINIMUM_LENGTH = "DEC_IATA25_MIN_LENGTH";
   public static final String PROPERTY_IATA_25_MAXIMUM_LENGTH = "DEC_IATA25_MAX_LENGTH";
   public static final String PROPERTY_INTERLEAVED_25_ENABLED = "DEC_I25_ENABLED";
   public static final String PROPERTY_INTERLEAVED_25_MINIMUM_LENGTH = "DEC_I25_MIN_LENGTH";
   public static final String PROPERTY_INTERLEAVED_25_MAXIMUM_LENGTH = "DEC_I25_MAX_LENGTH";
   public static final String PROPERTY_INTERLEAVED_25_CHECK_DIGIT_MODE = "DEC_I25_CHECK_DIGIT_MODE";
   public static final String INTERLEAVED_25_CHECK_DIGIT_MODE_NO_CHECK = "noCheck";
   public static final String INTERLEAVED_25_CHECK_DIGIT_MODE_CHECK = "check";
   public static final String INTERLEAVED_25_CHECK_DIGIT_MODE_CHECK_AND_STRIP = "checkAndStrip";
   public static final String PROPERTY_KOREAN_POST_ENABLED = "DEC_KOREA_POST_ENABLED";
   public static final String PROPERTY_KOREAN_POST_MINIMUM_LENGTH = "DEC_KOREA_POST_MIN_LENGTH";
   public static final String PROPERTY_KOREAN_POST_MAXIMUM_LENGTH = "DEC_KOREA_POST_MAX_LENGTH";
   public static final String PROPERTY_MATRIX_25_ENABLED = "DEC_M25_ENABLED";
   public static final String PROPERTY_MATRIX_25_MINIMUM_LENGTH = "DEC_M25_MIN_LENGTH";
   public static final String PROPERTY_MATRIX_25_MAXIMUM_LENGTH = "DEC_M25_MAX_LENGTH";
   public static final String PROPERTY_MAXICODE_ENABLED = "DEC_MAXICODE_ENABLED";
   public static final String PROPERTY_MAXICODE_MINIMUM_LENGTH = "DEC_MAXICODE_MIN_LENGTH";
   public static final String PROPERTY_MAXICODE_MAXIMUM_LENGTH = "DEC_MAXICODE_MAX_LENGTH";
   public static final String PROPERTY_MICRO_PDF_417_ENABLED = "DEC_MICROPDF_ENABLED";
   public static final String PROPERTY_MICRO_PDF_417_MINIMUM_LENGTH = "DEC_MICROPDF_MIN_LENGTH";
   public static final String PROPERTY_MICRO_PDF_417_MAXIMUM_LENGTH = "DEC_MICROPDF_MAX_LENGTH";
   public static final String PROPERTY_MSI_ENABLED = "DEC_MSI_ENABLED";
   public static final String PROPERTY_MSI_MINIMUM_LENGTH = "DEC_MSI_MIN_LENGTH";
   public static final String PROPERTY_MSI_MAXIMUM_LENGTH = "DEC_MSI_MAX_LENGTH";
   public static final String PROPERTY_MSI_CHECK_DIGIT_MODE = "DEC_MSI_CHECK_DIGIT_MODE";
   public static final String PROPERTY_MSI_SHORT_MARGIN = "DEC_MSIP_SHORT_MARGIN";
   public static final String PROPERTY_MSI_OUT_OF_SPEC_SYMBOL = "DEC_PROP_MSIP_OUT_OF_SPEC_SYMBOL";
   public static final String MSI_CHECK_DIGIT_MODE_NO_CHECK = "noCheck";
   public static final String MSI_CHECK_DIGIT_MODE_SINGLE_MOD_10_CHECK = "singleMod10Check";
   public static final String MSI_CHECK_DIGIT_MODE_SINGLE_MOD_11_PLUS_MOD_10_CHECK = "singleMod11PlusMod10Check";
   public static final String MSI_CHECK_DIGIT_MODE_DOUBLE_MOD_10_CHECK = "doubleMod10Check";
   public static final String MSI_CHECK_DIGIT_MODE_SINGLE_MOD_10_CHECK_AND_STRIP = "singleMod10CheckAndStrip";
   public static final String MSI_CHECK_DIGIT_MODE_SINGLE_MOD_11_PLUS_MOD_10_CHECK_AND_STRIP = "singleMod11PlusMod10CheckAndStrip";
   public static final String MSI_CHECK_DIGIT_MODE_DOUBLE_MOD_10_CHECK_AND_STRIP = "doubleMod10CheckAndStrip";
   public static final String PROPERTY_PDF_417_ENABLED = "DEC_PDF417_ENABLED";
   public static final String PROPERTY_PDF_417_MINIMUM_LENGTH = "DEC_PDF417_MIN_LENGTH";
   public static final String PROPERTY_PDF_417_MAXIMUM_LENGTH = "DEC_PDF417_MAX_LENGTH";
   public static final String PROPERTY_QR_CODE_ENABLED = "DEC_QR_ENABLED";
   public static final String PROPERTY_QR_CODE_MINIMUM_LENGTH = "DEC_QR_MIN_LENGTH";
   public static final String PROPERTY_QR_CODE_MAXIMUM_LENGTH = "DEC_QR_MAX_LENGTH";
   public static final String PROPERTY_RSS_ENABLED = "DEC_RSS_14_ENABLED";
   public static final String PROPERTY_RSS_LIMITED_ENABLED = "DEC_RSS_LIMITED_ENABLED";
   public static final String PROPERTY_RSS_EXPANDED_ENABLED = "DEC_RSS_EXPANDED_ENABLED";
   public static final String PROPERTY_RSS_EXPANDED_MINIMUM_LENGTH = "DEC_RSS_EXPANDED_MIN_LENGTH";
   public static final String PROPERTY_RSS_EXPANDED_MAXIMUM_LENGTH = "DEC_RSS_EXPANDED_MAX_LENGTH";
   public static final String PROPERTY_STANDARD_25_ENABLED = "DEC_S25_ENABLED";
   public static final String PROPERTY_STANDARD_25_MINIMUM_LENGTH = "DEC_S25_MIN_LENGTH";
   public static final String PROPERTY_STANDARD_25_MAXIMUM_LENGTH = "DEC_S25_MAX_LENGTH";
   public static final String PROPERTY_TELEPEN_ENABLED = "DEC_TELEPEN_ENABLED";
   public static final String PROPERTY_TELEPEN_MINIMUM_LENGTH = "DEC_TELEPEN_MIN_LENGTH";
   public static final String PROPERTY_TELEPEN_MAXIMUM_LENGTH = "DEC_TELEPEN_MAX_LENGTH";
   public static final String PROPERTY_TELEPEN_OLD_STYLE_ENABLED = "DEC_TELEPEN_OLD_STYLE";
   public static final String PROPERTY_TLC_39_ENABLED = "DEC_TLC39_ENABLED";
   public static final String PROPERTY_TRIOPTIC_ENABLED = "DEC_TRIOPTIC_ENABLED";
   public static final String PROPERTY_POSTAL_2D_MODE = "DEC_POSTAL_ENABLED";
   public static final String PROPERTY_POSTAL_2D_POSTNET_CHECK_DIGIT_TRANSMIT_ENABLED = "DEC_POSTNET_CHECK_DIGIT_TRANSMIT";
   public static final String PROPERTY_POSTAL_2D_PLANET_CHECK_DIGIT_TRANSMIT_ENABLED = "DEC_PLANETCODE_CHECK_DIGIT_TRANSMIT";
   public static final String POSTAL_2D_MODE_NONE = "none";
   public static final String POSTAL_2D_MODE_AUSTRALIA = "australia";
   public static final String POSTAL_2D_MODE_INFOMAIL = "infomail";
   public static final String POSTAL_2D_MODE_JAPAN = "japan";
   public static final String POSTAL_2D_MODE_CANADA = "canada";
   public static final String POSTAL_2D_MODE_DUTCH = "dutch";
   public static final String POSTAL_2D_MODE_PLANET = "planet";
   public static final String POSTAL_2D_MODE_POSTNET = "postnet";
   public static final String POSTAL_2D_MODE_BPO = "bpo";
   public static final String POSTAL_2D_MODE_INFOMAIL_AND_BPO = "infomailAndBpo";
   public static final String POSTAL_2D_MODE_UPU = "upu";
   public static final String POSTAL_2D_MODE_USPS = "usps";
   public static final String POSTAL_2D_MODE_POSTNET_PLUS_BNB = "postnetPlusBnB";
   public static final String POSTAL_2D_MODE_PLANET_AND_POSTNET = "planetAndPostnet";
   public static final String POSTAL_2D_MODE_PLANET_AND_UPU = "planetAndUpu";
   public static final String POSTAL_2D_MODE_POSTNET_AND_UPU = "postnetAndUpu";
   public static final String POSTAL_2D_MODE_PLANET_AND_USPS = "planetAndUsps";
   public static final String POSTAL_2D_MODE_POSTNET_AND_USPS = "postnetAndUsps";
   public static final String POSTAL_2D_MODE_UPU_AND_USPS = "upuAndUsps";
   public static final String POSTAL_2D_MODE_PLANET_AND_POSTNET_PLUS_BNB = "planetAndPostnetPlusBnB";
   public static final String POSTAL_2D_MODE_POSTNET_AND_UPU_PLUS_BNB = "postnetAndUpuPlusBnB";
   public static final String POSTAL_2D_MODE_POSTNET_AND_USPS_PLUS_BNB = "postnetAndUspsPlusBnB";
   public static final String POSTAL_2D_MODE_PLANET_AND_POSTNET_AND_UPU = "planetAndPostnetAndUpu";
   public static final String POSTAL_2D_MODE_PLANET_AND_POSTNET_AND_USPS = "planetAndPostnetAndUsps";
   public static final String POSTAL_2D_MODE_PLANET_AND_UPU_AND_USPS = "planetAndUpuAndUsps";
   public static final String POSTAL_2D_MODE_POSTNET_AND_UPU_AND_USPS = "postnetAndUpuAndUsps";
   public static final String POSTAL_2D_MODE_PLANET_AND_POSTNET_AND_UPU_PLUS_BNB = "planetAndPostnetAndUpuPlusBnB";
   public static final String POSTAL_2D_MODE_PLANET_AND_POSTNET_AND_USPS_PLUS_BNB = "planetAndPostnetAndUspsPlusBnB";
   public static final String POSTAL_2D_MODE_POSTNET_AND_UPU_AND_USPS_PLUS_BNB = "postnetAndUpuAndUspsPlusBnB";
   public static final String POSTAL_2D_MODE_PLANET_AND_POSTNET_AND_UPU_AND_USPS = "planetAndPostnetAndUpuAndUsps";
   public static final String POSTAL_2D_MODE_PLANET_AND_POSTNET_AND_UPU_AND_USPS_PLUS_BNB = "planetAndPostnetAndUpuAndUspsPlusBnB";
   public static final String PROPERTY_DATA_PROCESSOR_CHARSET = "DPR_CHARSET";
   public static final String PROPERTY_DATA_PROCESSOR_PREFIX = "DPR_PREFIX";
   public static final String PROPERTY_DATA_PROCESSOR_SUFFIX = "DPR_SUFFIX";
   public static final String PROPERTY_DATA_PROCESSOR_SYMBOLOGY_PREFIX = "DPR_SYMBOLOGY_PREFIX";
   public static final String PROPERTY_DATA_PROCESSOR_EDIT_DATA_PLUGIN = "DPR_EDIT_DATA_PLUGIN";
   public static final String DATA_PROCESSOR_SYMBOLOGY_ID_NONE = "none";
   public static final String DATA_PROCESSOR_SYMBOLOGY_ID_HONEYWELL = "honeywell";
   public static final String DATA_PROCESSOR_SYMBOLOGY_ID_AIM = "aim";
   public static final String PROPERTY_DATA_PROCESSOR_LAUNCH_BROWSER = "DPR_LAUNCH_BROWSER";
   public static final String PROPERTY_DATA_PROCESSOR_SCAN_TO_INTENT = "DPR_SCAN_TO_INTENT";
   public static final String PROPERTY_DATA_PROCESSOR_LAUNCH_EZ_CONFIG = "DPR_LAUNCH_EZ_CONFIG";
   public static final String PROPERTY_DATA_PROCESSOR_DATA_INTENT = "DPR_DATA_INTENT";
   public static final String PROPERTY_DATA_PROCESSOR_DATA_INTENT_ACTION = "DPR_DATA_INTENT_ACTION";
   public static final String PROPERTY_DATA_PROCESSOR_DATA_INTENT_CATEGORY = "DPR_DATA_INTENT_CATEGORY";
   public static final String PROPERTY_DATA_PROCESSOR_DATA_INTENT_PACKAGE_NAME = "DPR_DATA_INTENT_PACKAGE_NAME";
   public static final String PROPERTY_DATA_PROCESSOR_DATA_INTENT_CLASS_NAME = "DPR_DATA_INTENT_CLASS_NAME";
   public static final String PROPERTY_GROUP_SYMBOLOGY = "SYMBOLOGY_SETTINGS";
   public static final String PROPERTY_GROUP_IMAGER = "IMAGER_SETTINGS";
   public static final String PROPERTY_GROUP_TRIGGER = "TRIGGER_SETTINGS";
   public static final String PROPERTY_GROUP_NOTIFICATION = "NOTIFICATION_SETTINGS";
   public static final String PROPERTY_GROUP_DATA_PROCESSING = "DATA_PROCESSING_SETTINGS";
   public static final String PROPERTY_OCR_MODE = "DEC_OCR_MODE";
   public static final String POSTAL_OCR_MODE_OFF = "off";
   public static final String POSTAL_OCR_MODE_NORMAL = "normalVideo";
   public static final String POSTAL_OCR_MODE_INVERSE = "inverseVideo";
   public static final String POSTAL_OCR_MODE_NORMAL_AND_INVERSE = "normalAndInverseVideo";
   public static final String PROPERTY_OCR_ACTIVE_TEMPLATE = "DEC_OCR_ACTIVE_TEMPLATES";
   public static final String PROPERTY_OCR_TEMPLATE = "DEC_OCR_TEMPLATE";
   public static final String PROPERTY_DEC_DPM_ENABLED = "DEC_DPM_ENABLED";
   public static final String DPM_ENABLED_NO_DPM_OPTIMIZATION = "none";
   public static final String DPM_ENABLED_DOTPEEN_DECODING = "dotpeen";
   public static final String DPM_ENABLED_REFLECTIVE_DECODING = "reflective";
   public static final String PROPERTY_DEC_ID_PROP_USE_ROI = "DEC_ID_PROP_USE_ROI";
   public static final String DEC_ID_PROP_USE_ROI_DISABLE = "Disable";
   public static final String DEC_ID_PROP_USE_ROI_STANDARD = "Standard";
   public static final String DEC_ID_PROP_USE_ROI_STANDARD_AIMER_CENTERED = "Standard, Aimer centered";
   public static final String DEC_ID_PROP_USE_ROI_DPM_AIMER_CENTERED = "DPM, Aimer centered";
   public static final String DEC_ID_PROP_USE_ROI_KIOSK_OR_PRESENTATION = "Kiosk/Presentation application";
   public static final String PROPERTY_CENTER_DECODE = "DEC_WINDOW_MODE";
   public static final String PROPERTY_DECODE_WINDOW_TOP = "DEC_WINDOW_TOP";
   public static final String PROPERTY_DECODE_WINDOW_BOTTOM = "DEC_WINDOW_BOTTOM";
   public static final String PROPERTY_DECODE_WINDOW_LEFT = "DEC_WINDOW_LEFT";
   public static final String PROPERTY_DECODE_WINDOW_RIGHT = "DEC_WINDOW_RIGHT";
   public static final String PROPERTY_DECODE_SECURITY_LEVEL = "DEC_SECURITY_LEVEL";
   public static final String PROPERTY_LINEAR_DAMAGE_IMPROVEMENTS = "DEC_LINEAR_DAMAGE_IMPROVEMENTS";
   public static final String PROPERTY_VIDEO_REVERSE_ENABLED = "DEC_VIDEO_REVERSE_ENABLED";
   public static final String VIDEO_REVERSE_ENABLED_NORMAL = "normal";
   public static final String VIDEO_REVERSE_ENABLED_INVERSE = "inverse";
   public static final String VIDEO_REVERSE_ENABLED_BOTH = "both";
   public static final String PROPERTY_DECODER_TIMEOUT = "DEC_DECODER_TIMEOUT";
   public static final String PROPERTY_DECODE_MOBILE_READ_ENABLE = "DECODE_MOBILE_READ_ENABLE";
   private final IExecutor mExecutor;
   private static Map<Class<?>, Map<Object, IExecutor>> sListeners = new HashMap<>();
   private static Map<Class<?>, Map<Object, Integer>> sListenerCounts = new HashMap<>();
   private boolean mBarcodeReaderClosed = false;
   public static final String GOOD_READ_NOTIFICATION = "goodRead";
   public static final String BAD_READ_NOTIFICATION = "badRead";
   public static final Creator<BarcodeReader> CREATOR = new Creator<BarcodeReader>() {
      public BarcodeReader createFromParcel(Parcel in) {
         return new BarcodeReader(in);
      }

      public BarcodeReader[] newArray(int size) {
         return new BarcodeReader[size];
      }
   };

   BarcodeReader(IExecutor scannerInterface) {
      super();
      DebugLog.d("Enter BarcodeReader constructor");
      this.mExecutor = scannerInterface;
      DebugLog.d("Exit BarcodeReader constructor");
   }

   public void addBarcodeListener(BarcodeReader.BarcodeListener listener) {
      this.addListener(listener, BarcodeReader.BarcodeListener.class);
   }

   public void addTriggerListener(BarcodeReader.TriggerListener listener) {
      this.addListener(listener, BarcodeReader.TriggerListener.class);
   }

   private void addListener(final Object listener, final Class<?> listenerClass) {
      if (this.mBarcodeReaderClosed) {
         throw new IllegalStateException("BarcodeReader is closed");
      } else {
         IExecutor wrapper = null;
         synchronized (sListeners) {
            Map<Object, IExecutor> mapIExecutor = sListeners.get(listenerClass);
            if (null != mapIExecutor) {
               wrapper = mapIExecutor.get(listener);
               if (wrapper == null) {
                  wrapper = new IExecutor.Stub() {
                     @Override
                     public void executeAsync(Message m, IExecutor callback) throws RemoteException {
                        this.execute(m);
                     }

                     @Override
                     public Message execute(Message m) throws RemoteException {
                        EventObject event = DcsJsonRpcHelper.getEvent(BarcodeReader.this, m);
                        if (event instanceof BarcodeReadEvent && BarcodeReader.BarcodeListener.class.equals(listenerClass)) {
                           ((BarcodeReader.BarcodeListener)listener).onBarcodeEvent((BarcodeReadEvent)event);
                        } else if (event instanceof BarcodeFailureEvent && BarcodeReader.BarcodeListener.class.equals(listenerClass)) {
                           ((BarcodeReader.BarcodeListener)listener).onFailureEvent((BarcodeFailureEvent)event);
                        } else if (event instanceof TriggerStateChangeEvent && BarcodeReader.TriggerListener.class.equals(listenerClass)) {
                           ((BarcodeReader.TriggerListener)listener).onTriggerEvent((TriggerStateChangeEvent)event);
                        }

                        return null;
                     }
                  };
                  this.incrementListeners(listenerClass, listener, wrapper);
               } else {
                  this.incrementListeners(listenerClass, listener, null);
               }
            }
         }

         Message request = DcsJsonRpcHelper.build("scanner.addListener");
         request.extras.put("listener", wrapper);
         Message response = this.execute(request);
         DcsJsonRpcHelper.checkRuntimeError(response);
      }
   }

   public void removeBarcodeListener(BarcodeReader.BarcodeListener listener) {
      this.removeListener(listener, BarcodeReader.BarcodeListener.class);
   }

   public void removeTriggerListener(BarcodeReader.TriggerListener listener) {
      this.removeListener(listener, BarcodeReader.TriggerListener.class);
   }

   private void removeListener(Object listener, Class<?> listenerClass) {
      IExecutor wrapper = null;
      synchronized (sListeners) {
         wrapper = sListeners.get(listenerClass).get(listener);
         if (wrapper == null) {
            return;
         }

         this.decrementListeners(listenerClass, listener);
      }

      Message request = DcsJsonRpcHelper.build("scanner.removeListener");
      request.extras.put("listener", wrapper);
      Message response = this.execute(request);
      DcsJsonRpcHelper.checkRuntimeError(response);
   }

   public BarcodeReaderInfo getInfo() throws ScannerUnavailableException {
      if (this.mBarcodeReaderClosed) {
         throw new IllegalStateException("BarcodeReader is closed");
      } else {
         Message request = DcsJsonRpcHelper.build("scanner.getInfo");
         Message response = this.execute(request);
         DcsJsonRpcHelper.checkScannerUnavailable(response);
         DcsJsonRpcHelper.checkRuntimeError(response);

         try {
            JSONObject responseObj = new JSONObject(response.action);
            JSONObject resultObj = responseObj.getJSONObject("result");
            JSONObject infoObj = resultObj.getJSONObject("info");
            return DcsJsonRpcHelper.buildBarcodeReaderInfo(infoObj);
         } catch (JSONException var6) {
            throw new RuntimeException("Failed to retrieve barcode reader info", var6);
         }
      }
   }

   public void claim() throws ScannerUnavailableException {
      if (this.mBarcodeReaderClosed) {
         throw new IllegalStateException("BarcodeReader is closed");
      } else {
         Message request = DcsJsonRpcHelper.build("scanner.claim");
         Message response = this.execute(request);
         DcsJsonRpcHelper.checkScannerUnavailable(response);
         DcsJsonRpcHelper.checkRuntimeError(response);
      }
   }

   public void decode(boolean on) throws ScannerNotClaimedException, ScannerUnavailableException {
      if (this.mBarcodeReaderClosed) {
         throw new IllegalStateException("BarcodeReader is closed");
      } else {
         Message request = DcsJsonRpcHelper.build("scanner.decode", "state", on);
         Message response = this.execute(request);
         DcsJsonRpcHelper.checkScannerNotClaimedException(response);
         DcsJsonRpcHelper.checkScannerUnavailable(response);
         DcsJsonRpcHelper.checkRuntimeError(response);
      }
   }

   public void aim(boolean on) throws ScannerNotClaimedException, ScannerUnavailableException {
      if (this.mBarcodeReaderClosed) {
         throw new IllegalStateException("BarcodeReader is closed");
      } else {
         Message request = DcsJsonRpcHelper.build("scanner.aim", "state", on);
         Message response = this.execute(request);
         DcsJsonRpcHelper.checkScannerNotClaimedException(response);
         DcsJsonRpcHelper.checkScannerUnavailable(response);
         DcsJsonRpcHelper.checkRuntimeError(response);
      }
   }

   public void light(boolean on) throws ScannerNotClaimedException, ScannerUnavailableException {
      if (this.mBarcodeReaderClosed) {
         throw new IllegalStateException("BarcodeReader is closed");
      } else {
         Message request = DcsJsonRpcHelper.build("scanner.light", "state", on);
         Message response = this.execute(request);
         DcsJsonRpcHelper.checkScannerNotClaimedException(response);
         DcsJsonRpcHelper.checkScannerUnavailable(response);
         DcsJsonRpcHelper.checkRuntimeError(response);
      }
   }

   public Signature getSignature(SignatureParameters parameters) throws ScannerNotClaimedException, ScannerUnavailableException {
      if (this.mBarcodeReaderClosed) {
         throw new IllegalStateException("BarcodeReader is closed");
      } else {
         Map<String, Object> params = new HashMap<>();
         params.put("aspectRatio", parameters.getAspectRatio());
         params.put("horizontalOffset", parameters.getHorizontalOffset());
         params.put("verticalOffset", parameters.getVerticalOffset());
         params.put("width", parameters.getWidth());
         params.put("height", parameters.getHeight());
         params.put("resolution", parameters.getResolution());
         params.put("binarized", parameters.isBinarized());
         Message request = DcsJsonRpcHelper.build("scanner.getSignature", params);
         Message response = this.execute(request);
         DcsJsonRpcHelper.checkScannerNotClaimedException(response);
         DcsJsonRpcHelper.checkScannerUnavailable(response);
         DcsJsonRpcHelper.checkRuntimeError(response);
         String guidance = null;

         try {
            JSONObject responseObj = new JSONObject(response.action);
            JSONObject resultObj = responseObj.getJSONObject("result");
            guidance = resultObj.getString("guidance");
         } catch (JSONException var8) {
            throw new RuntimeException("An error occurred while communicating with the scanner service.", var8);
         }

         Bitmap image = null;
         if (response.extras != null && response.extras.containsKey("image")) {
            image = (Bitmap)response.extras.get("image");
         }

         return new Signature(guidance, image);
      }
   }

   public void release() {
      if (this.mBarcodeReaderClosed) {
         throw new IllegalStateException("BarcodeReader is closed");
      } else {
         Message request = DcsJsonRpcHelper.build("scanner.release");
         Message response = this.execute(request);
         DcsJsonRpcHelper.checkRuntimeError(response);
      }
   }

   public void close() {
      if (this.mBarcodeReaderClosed) {
         throw new IllegalStateException("BarcodeReader is closed");
      } else {
         this.mBarcodeReaderClosed = true;
         Message request = DcsJsonRpcHelper.build("scanner.disconnect");
         Message response = this.execute(request);
         DcsJsonRpcHelper.checkRuntimeError(response);
      }
   }

   public void setProperty(String name, int value) throws UnsupportedPropertyException {
      this.setProperty(name, Integer.valueOf(value));
      int newValue = this.getIntProperty(name);
      if (newValue != value) {
         throw new UnsupportedPropertyException("Property was rejected by the scanner service.");
      }
   }

   public void setProperty(String name, boolean value) throws UnsupportedPropertyException {
      this.setProperty(name, Boolean.valueOf(value));
      boolean newValue = this.getBooleanProperty(name);
      if (newValue != value) {
         throw new UnsupportedPropertyException("Property was rejected by the scanner service.");
      }
   }

   public void setProperty(String name, String value) throws UnsupportedPropertyException {
      this.setProperty(name, (Object)value);
      String newValue = this.getStringProperty(name);
      if (value != null && !value.equals(newValue)) {
         throw new UnsupportedPropertyException("Property was rejected by the scanner service.");
      }
   }

   private void setProperty(String name, Object value) {
      if (name != null && value != null) {
         Map<String, Object> properties = new HashMap<>();
         properties.put(name, value);
         this.setProperties(properties);
      } else {
         throw new IllegalArgumentException("Parameters cannot be null.");
      }
   }

   public void setProperties(Map<String, Object> properties) {
      if (this.mBarcodeReaderClosed) {
         throw new IllegalStateException("BarcodeReader is closed");
      } else {
         Map<String, Object> params = new HashMap<>();
         params.put("values", properties);
         Message request = DcsJsonRpcHelper.build("scanner.setProperties", params);
         Message response = this.execute(request);
         DcsJsonRpcHelper.checkRuntimeError(response);
      }
   }

   public List<String> getProfileNames() {
      DebugLog.d("Enter getProfileNames");
      if (this.mBarcodeReaderClosed) {
         throw new IllegalStateException("getProfileNames, BarcodeReader is closed");
      } else {
         List<String> profiles = new ArrayList<>();
         Message request = DcsJsonRpcHelper.build("scanner.getProfileNames");
         Message response = this.execute(request);
         DcsJsonRpcHelper.checkRuntimeError(response);

         try {
            JSONObject responseObj = new JSONObject(response.action);
            JSONObject resultObj = responseObj.getJSONObject("result");
            JSONArray valuesObj = resultObj.getJSONArray("values");

            for (int i = 0; i < valuesObj.length(); i++) {
               profiles.add(valuesObj.getJSONObject(i).getString("profile"));
            }
         } catch (JSONException var8) {
            throw new RuntimeException("An error occurred while communicating with the scanner service.", var8);
         }

         DebugLog.d("Exit getProfileNames");
         return profiles;
      }
   }

   public boolean loadProfile(String profileName) {
      DebugLog.d("Enter loadProfile");
      if (profileName == null || profileName.length() == 0) {
         DebugLog.d("loadProfile, profile param is empty");
         throw new IllegalArgumentException("profile param is empty");
      } else if (this.mBarcodeReaderClosed) {
         throw new IllegalStateException("loadProfile, BarcodeReader is closed");
      } else {
         new HashMap();
         Map<String, Object> params = new HashMap<>();
         params.put("profile", profileName);
         Message request = DcsJsonRpcHelper.build("scanner.loadProfile", params);
         Message response = this.execute(request);
         DcsJsonRpcHelper.checkRuntimeError(response);
         boolean profileFound = false;

         try {
            JSONObject responseObj = new JSONObject(response.action);
            JSONObject resultObj = responseObj.getJSONObject("result");
            JSONObject valuesObj = resultObj.getJSONObject("values");
            profileFound = valuesObj.getBoolean("profileFound");
         } catch (JSONException var10) {
            throw new RuntimeException("An error occurred while communicating with the scanner service.", var10);
         }

         DebugLog.d("Exit loadProfile");
         return profileFound;
      }
   }

   public int getIntProperty(String name) throws UnsupportedPropertyException {
      return this.getTypedProperty(name, Integer.class);
   }

   public boolean getBooleanProperty(String name) throws UnsupportedPropertyException {
      return this.getTypedProperty(name, Boolean.class);
   }

   public String getStringProperty(String name) throws UnsupportedPropertyException {
      return this.getTypedProperty(name, String.class);
   }

   private <T> T getTypedProperty(String name, Class<T> type) throws UnsupportedPropertyException {
      if (name == null) {
         throw new IllegalArgumentException("Parameters cannot be null.");
      } else {
         Set<String> properties = new TreeSet<>();
         properties.add(name);
         Map<String, Object> propertyMap = this.getProperties(properties);
         if (!propertyMap.containsKey(name)) {
            throw new UnsupportedPropertyException("Property not found: " + name);
         } else if (!type.isAssignableFrom(propertyMap.get(name).getClass())) {
            throw new RuntimeException("Property is not of type " + type.getSimpleName());
         } else {
            return type.cast(propertyMap.get(name));
         }
      }
   }

   public Map<String, Object> getProperties(Set<String> names) {
      if (this.mBarcodeReaderClosed) {
         throw new IllegalStateException("BarcodeReader is closed");
      } else if (names == null) {
         throw new IllegalArgumentException("Names set cannot be null.");
      } else {
         return (Map<String, Object>)(names.isEmpty() ? new HashMap<>() : this.internalGetProperties(names));
      }
   }

   public Map<String, Object> getAllProperties() {
      if (this.mBarcodeReaderClosed) {
         throw new IllegalStateException("BarcodeReader is closed");
      } else {
         return this.internalGetProperties(new HashSet<>());
      }
   }

   public Map<String, Object> getAllDefaultProperties() {
      if (this.mBarcodeReaderClosed) {
         throw new IllegalStateException("BarcodeReader is closed");
      } else {
         return this.internalGetProperties(new HashSet<>(), true);
      }
   }

   private Map<String, Object> internalGetProperties(Set<String> names) {
      return this.internalGetProperties(names, false);
   }

   private Map<String, Object> internalGetProperties(Set<String> names, boolean getDefaults) {
      Map<String, Object> result = new HashMap<>();
      Map<String, Object> params = new HashMap<>();
      params.put("names", names.toArray());
      String methodName = getDefaults ? "scanner.getDefaultProperties" : "scanner.getProperties";
      Message request = DcsJsonRpcHelper.build(methodName, params);
      Message response = this.execute(request);
      DcsJsonRpcHelper.checkRuntimeError(response);

      try {
         JSONObject responseObj = new JSONObject(response.action);
         JSONObject resultObj = responseObj.getJSONObject("result");
         JSONObject valuesObj = resultObj.getJSONObject("values");
         Iterator<?> itr = valuesObj.keys();

         while (itr.hasNext()) {
            String key = (String)itr.next();
            result.put(key, valuesObj.get(key));
         }

         return result;
      } catch (JSONException var13) {
         throw new RuntimeException("An error occurred while communicating with the scanner service.", var13);
      }
   }

   public void notify(String notification) {
      Message request = DcsJsonRpcHelper.build("scanner.notify", "notification", notification);
      Message response = this.execute(request);
      DcsJsonRpcHelper.checkRuntimeError(response);
   }

   public Bitmap captureImage() {
      Message request = DcsJsonRpcHelper.build("scanner.captureImage");
      Message response = this.execute(request);
      DcsJsonRpcHelper.checkRuntimeError(response);
      return (Bitmap)response.extras.get("image");
   }

   public Bitmap getLastImage(int sensorType) {
      Message request = DcsJsonRpcHelper.build("scanner.getLastImage", "sensorType", sensorType);
      Message response = this.execute(request);
      DcsJsonRpcHelper.checkRuntimeError(response);
      return (Bitmap)response.extras.get("image");
   }

   public void startPropertyEditor(Context context) {
      this.startPropertyEditor(context, null, null);
   }

   public void startPropertyEditor(Context context, String propertyGroup, String activityTitle) {
      if (this.mBarcodeReaderClosed) {
         throw new IllegalStateException("BarcodeReader is closed");
      } else {
         Intent intent = new Intent("com.honeywell.decode.intent.action.EDIT_SETTINGS");
         intent.putExtra("barcodeReader", this);
         if (propertyGroup != null) {
            intent.putExtra("propertyGroup", propertyGroup);
         }

         if (activityTitle != null) {
            intent.putExtra("activityTitle", activityTitle);
         }

         context.startActivity(intent);
      }
   }

   public void softwareTrigger(boolean state) throws ScannerNotClaimedException, ScannerUnavailableException {
      if (this.mBarcodeReaderClosed) {
         throw new IllegalStateException("BarcodeReader is closed");
      } else {
         Message request = DcsJsonRpcHelper.build("internal.setTrigger", "state", state);
         Message response = this.execute(request);
         DcsJsonRpcHelper.checkScannerNotClaimedException(response);
         DcsJsonRpcHelper.checkScannerUnavailable(response);
         DcsJsonRpcHelper.checkRuntimeError(response);
      }
   }

   Message execute(Message request) {
      try {
         return this.mExecutor.execute(request);
      } catch (RemoteException var3) {
         throw new RuntimeException("An error occurred while communicating with the scanner service.", var3);
      }
   }

   BarcodeReader(Parcel in) {
      super();
      DebugLog.d("Enter BarcodeReader constructor");
      this.mExecutor = IExecutor.Stub.asInterface(in.readStrongBinder());
      DebugLog.d("Exit BarcodeReader constructor");
   }

   public int describeContents() {
      return 0;
   }

   public void writeToParcel(Parcel dest, int flags) {
      dest.writeStrongBinder(this.mExecutor.asBinder());
   }

   private void incrementListeners(Class<?> listenerClass, Object listener, IExecutor wrapper) {
      Map<Object, Integer> listenerCountMap = sListenerCounts.get(listenerClass);
      if (null != listenerCountMap) {
         if (null != wrapper) {
            Map<Object, IExecutor> listenerMap = sListeners.get(listenerClass);
            if (null != listenerMap) {
               listenerMap.put(listener, wrapper);
               listenerCountMap.put(listener, 1);
            }
         } else {
            Integer count = listenerCountMap.get(listener);
            listenerCountMap.put(listener, count + 1);
         }
      }
   }

   private void decrementListeners(Class<?> listenerClass, Object listener) {
      Map<Object, Integer> listenerCountMap = sListenerCounts.get(listenerClass);
      if (null != listenerCountMap) {
         int count = listenerCountMap.get(listener);
         if (count == 1) {
            Map<Object, IExecutor> listenerMap = sListeners.get(listenerClass);
            if (null != listenerMap) {
               listenerMap.remove(listener);
               listenerCountMap.remove(listener);
            }
         } else {
            listenerCountMap.put(listener, count - 1);
         }
      }
   }

   static {
      sListeners.put(BarcodeReader.BarcodeListener.class, new HashMap<>());
      sListeners.put(BarcodeReader.TriggerListener.class, new HashMap<>());
      sListenerCounts.put(BarcodeReader.BarcodeListener.class, new HashMap<>());
      sListenerCounts.put(BarcodeReader.TriggerListener.class, new HashMap<>());
   }

   public interface BarcodeListener extends EventListener {
      void onBarcodeEvent(BarcodeReadEvent var1);

      void onFailureEvent(BarcodeFailureEvent var1);
   }

   public interface TriggerListener extends EventListener {
      void onTriggerEvent(TriggerStateChangeEvent var1);
   }
}
