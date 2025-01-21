package com.honeywell.aidc;

import com.honeywell.Message;
import java.util.EventObject;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import org.json.JSONException;
import org.json.JSONObject;

public class DcsJsonRpcHelper {
   private static AtomicInteger sId = new AtomicInteger();

   public DcsJsonRpcHelper() {
      super();
   }

   public static Message build(String method, Map<String, Object> params) {
      JSONObject obj = new JSONObject();

      try {
         obj.put("id", sId.incrementAndGet());
         obj.put("jsonrpc", "2.0");
         obj.put("method", method);
         obj.put("params", JsonUtil.mapToJson(params));
      } catch (JSONException var4) {
         var4.printStackTrace();
      }

      Message message = new Message(obj.toString());
      message.extras = new HashMap<>();
      return message;
   }

   public static Message build(String method, String paramName, Object paramValue) {
      Map<String, Object> params = new HashMap<>();
      params.put(paramName, paramValue);
      return build(method, params);
   }

   public static Message build(String method) {
      JSONObject obj = new JSONObject();

      try {
         obj.put("id", sId.incrementAndGet());
         obj.put("jsonrpc", "2.0");
         obj.put("method", method);
      } catch (JSONException var3) {
         var3.printStackTrace();
      }

      Message message = new Message(obj.toString());
      message.extras = new HashMap<>();
      return message;
   }

   static EventObject getEvent(Object source, Message m) {
      EventObject event = null;

      try {
         JSONObject action = new JSONObject(m.action);
         String eventMethod = action.getString("method");
         if (eventMethod.equals("scanner.barcodeEvent")) {
            JSONObject params = action.getJSONObject("params");
            JSONObject barcode = params.getJSONObject("barcode");
            String bounds = null;
            if (barcode.has("bounds")) {
               bounds = barcode.getString("bounds");
            }

            event = new BarcodeReadEvent(
               source,
               barcode.getString("data"),
               barcode.getString("charset"),
               barcode.getString("honeywellId"),
               barcode.getString("aimId"),
               barcode.getString("timestamp"),
               bounds
            );
         } else if (eventMethod.equals("scanner.failureEvent")) {
            JSONObject params = action.getJSONObject("params");
            event = new BarcodeFailureEvent(source, params.getString("timestamp"));
         } else if (eventMethod.equals("scanner.triggerEvent")) {
            JSONObject params = action.getJSONObject("params");
            boolean state = params.getBoolean("state");
            event = new TriggerStateChangeEvent(eventMethod, state);
         } else if (eventMethod.equals("internal.scannerDeviceEvent")) {
            JSONObject params = action.getJSONObject("params");
            JSONObject scannerObj = params.getJSONObject("info");
            BarcodeReaderInfo info = buildBarcodeReaderInfo(scannerObj);
            int status = scannerObj.optInt("IMG_CONNECTION_STATUS", 0);
            event = new BarcodeDeviceConnectionEvent(eventMethod, info, status);
         }

         return event;
      } catch (JSONException var9) {
         throw new RuntimeException("An error occurred while communicating with the scanner service.", var9);
      }
   }

   static BarcodeReaderInfo buildBarcodeReaderInfo(JSONObject scannerObj) {
      String scannerName = "";
      if (scannerObj.has("IMG_SCANNER_NAME")) {
         scannerName = scannerObj.optString("IMG_SCANNER_NAME", "dcs.scanner.imager");
      } else if (scannerObj.has("scanner")) {
         scannerObj.optString("scanner", "dcs.scanner.imager");
      } else {
         scannerName = "dcs.scanner.imager";
      }

      return new BarcodeReaderInfo(
         scannerName,
         scannerObj.optString("IMG_FRIENDLY_SCANNER_NAME", "Internal Scanner"),
         scannerObj.optString("IMG_SCAN_ENGINE", null),
         scannerObj.optString("DEC_REVISION_FULL_DECODER", null),
         scannerObj.optString("DEC_REVISION_FAST_DECODER", null),
         scannerObj.optString("DEC_REVISION_CONTROL_LOGIC", null),
         scannerObj.optString("IMG_SCAN_ENGINE_VERSION", null),
         scannerObj.optString("IMG_SCAN_ENGINE_FIRMWARE_VERSION", null),
         scannerObj.optString("IMG_SCAN_ENGINE_SERIAL_NUMBER", null),
         scannerObj.optInt("IMG_FRAME_HEIGHT"),
         scannerObj.optInt("IMG_FRAME_WIDTH")
      );
   }

   public static void checkRuntimeError(Message m) {
      String errorMessage = null;

      try {
         if (m.action != null) {
            JSONObject result = new JSONObject(m.action);
            if (result.has("error")) {
               JSONObject error = result.getJSONObject("error");
               switch (error.getInt("code")) {
                  case -32602:
                     throw new IllegalArgumentException("Invalid parameter(s) provided.");
                  default:
                     errorMessage = error.getString("message");
               }
            }
         } else {
            errorMessage = "Action is null";
         }
      } catch (JSONException var4) {
         throw new RuntimeException("An error occurred while communicating with the scanner service.", var4);
      }

      if (errorMessage != null) {
         throw new RuntimeException(errorMessage);
      }
   }

   static void checkScannerNotClaimedException(Message m) throws ScannerNotClaimedException {
      int errorCode = 0;

      try {
         JSONObject result = new JSONObject(m.action);
         if (result.has("error")) {
            JSONObject error = result.getJSONObject("error");
            errorCode = error.getInt("code");
         }
      } catch (JSONException var4) {
         throw new RuntimeException("An error occurred while communicating with the scanner service.", var4);
      }

      if (errorCode == -30000) {
         throw new ScannerNotClaimedException("Scanner is not claimed.");
      }
   }

   static void checkScannerUnavailable(Message m) throws ScannerUnavailableException {
      int errorCode = 0;

      try {
         JSONObject result = new JSONObject(m.action);
         if (result.has("error")) {
            JSONObject error = result.getJSONObject("error");
            errorCode = error.getInt("code");
         }
      } catch (JSONException var4) {
         throw new RuntimeException("An error occurred while communicating with the scanner service.", var4);
      }

      if (errorCode == -30001) {
         throw new ScannerUnavailableException("Specified scanner is unavailable.");
      }
   }

   static void checkRuntimeAndScannerExceptions(Message m) throws InvalidScannerNameException {
      String errorMessage = null;

      try {
         if (m.action != null) {
            JSONObject result = new JSONObject(m.action);
            if (result.has("error")) {
               JSONObject error = result.getJSONObject("error");
               switch (error.getInt("code")) {
                  case -32602:
                     throw new IllegalArgumentException("Invalid parameter(s) provided.");
                  case -30002:
                     throw new InvalidScannerNameException("Specified scanner name is invalid.");
                  default:
                     errorMessage = error.getString("message");
               }
            }
         } else {
            errorMessage = "Action is null";
         }
      } catch (JSONException var4) {
         throw new RuntimeException("An error occurred while communicating with the scanner service.", var4);
      }

      if (errorMessage != null) {
         throw new RuntimeException(errorMessage);
      }
   }
}
