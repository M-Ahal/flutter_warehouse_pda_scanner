package com.honeywell.aidc;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import com.honeywell.IExecutor;
import com.honeywell.Message;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.EventObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class AidcManager {
   private Context mContext;
   private ServiceConnection mConnection;
   private IExecutor mService;
   private static Map<Class<?>, Map<Object, IExecutor>> sListeners = new HashMap<>();
   private static Map<Class<?>, Map<Object, Integer>> sListenerCounts = new HashMap<>();
   public static final int BARCODE_DEVICE_DISCONNECTED = 0;
   public static final int BARCODE_DEVICE_CONNECTED = 1;

   public void addBarcodeDeviceListener(AidcManager.BarcodeDeviceListener listener) {
      this.addListener(listener, AidcManager.BarcodeDeviceListener.class);
   }

   private void addListener(final Object listener, final Class<?> listenerClass) {
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
                     EventObject event = DcsJsonRpcHelper.getEvent(AidcManager.this, m);
                     if (event instanceof BarcodeDeviceConnectionEvent && AidcManager.BarcodeDeviceListener.class.equals(listenerClass)) {
                        ((AidcManager.BarcodeDeviceListener)listener).onBarcodeDeviceConnectionEvent((BarcodeDeviceConnectionEvent)event);
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

      Message request = DcsJsonRpcHelper.build("internal.addListener");
      request.extras.put("listener", wrapper);
      Message response = this.execute(request);
      DcsJsonRpcHelper.checkRuntimeError(response);
   }

   public void removeBarcodeDeviceListener(AidcManager.BarcodeDeviceListener listener) {
      this.removeListener(listener, AidcManager.BarcodeDeviceListener.class);
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

      Message request = DcsJsonRpcHelper.build("internal.removeListener");
      request.extras.put("listener", wrapper);
      Message response = this.execute(request);
      DcsJsonRpcHelper.checkRuntimeError(response);
   }

   public static void create(Context context, final AidcManager.CreatedCallback callback) {
      DebugLog.d("Enter AidcManager.create()");
      if (context != null && callback != null) {
         final Context appContext = context.getApplicationContext();
         appContext.bindService(
            new Intent("com.honeywell.decode.DecodeService")
               .setComponent(new ComponentName("com.intermec.datacollectionservice", "com.intermec.datacollectionservice.DataCollectionService")),
            new ServiceConnection() {
               public void onServiceDisconnected(ComponentName name) {
                  DebugLog.d("Enter onServiceDisconnected");
                  DebugLog.d("Exit onServiceDisconnected");
               }

               public void onServiceConnected(ComponentName name, IBinder service) {
                  DebugLog.d("Enter onServiceConnected");
                  IExecutor scannerService = IExecutor.Stub.asInterface(service);
                  AidcManager manager = new AidcManager(appContext, this, scannerService);
                  callback.onCreated(manager);
                  DebugLog.d("Exit onServiceConnected");
               }
            },
            1
         );
         DebugLog.d("Exit AidcManager constructor");
      } else {
         throw new IllegalArgumentException("The parameters cannot be null.");
      }
   }

   AidcManager(Context context, ServiceConnection connection, IExecutor service) {
      super();
      DebugLog.d("Enter AidcManager constructor");
      this.mContext = context;
      this.mConnection = connection;
      this.mService = service;
      DebugLog.d("Exit AidcManager constructor");
   }

   @Override
   protected void finalize() throws Throwable {
      super.finalize();
      this.close();
   }

   public List<BarcodeReaderInfo> listBarcodeDevices() {
      List<BarcodeReaderInfo> scanners = new ArrayList<>();

      try {
         Message request = DcsJsonRpcHelper.build("scanner.listScanners");
         Message response = this.execute(request);

         try {
            DcsJsonRpcHelper.checkRuntimeError(response);
         } catch (RuntimeException var9) {
            if (var9.getMessage().contains("Method not found")) {
               DebugLog.d("This method is not supported for the installed DCS version.");
               return null;
            }

            if (var9.getMessage().contains("Action is null")) {
               DebugLog.d("Message action is null.");
               return null;
            }

            throw var9;
         }

         JSONObject responseObj = new JSONObject(response.action);
         JSONObject resultObj = responseObj.getJSONObject("result");
         JSONArray scannersArr = resultObj.getJSONArray("scanners");

         for (int i = 0; i < scannersArr.length(); i++) {
            JSONObject scannerObj = scannersArr.getJSONObject(i);
            scanners.add(DcsJsonRpcHelper.buildBarcodeReaderInfo(scannerObj));
         }

         return scanners;
      } catch (JSONException var10) {
         throw new RuntimeException("An error occurred while communicating with the scanner service.", var10);
      }
   }

   public List<BarcodeReaderInfo> listConnectedBarcodeDevices() {
      List<BarcodeReaderInfo> scanners = new ArrayList<>();

      try {
         Message request = DcsJsonRpcHelper.build("scanner.listConnectedScanners");
         Message response = this.execute(request);

         try {
            DcsJsonRpcHelper.checkRuntimeError(response);
         } catch (RuntimeException var9) {
            if (var9.getMessage().contains("Method not found")) {
               DebugLog.d("This method is not supported for the installed DCS version.");
               return null;
            }

            throw var9;
         }

         JSONObject responseObj = new JSONObject(response.action);
         JSONObject resultObj = responseObj.getJSONObject("result");
         JSONArray scannersArr = resultObj.getJSONArray("scanners");

         for (int i = 0; i < scannersArr.length(); i++) {
            JSONObject scannerObj = scannersArr.getJSONObject(i);
            scanners.add(DcsJsonRpcHelper.buildBarcodeReaderInfo(scannerObj));
         }

         return scanners;
      } catch (JSONException var10) {
         throw new RuntimeException("An error occurred while communicating with the scanner service.", var10);
      }
   }

   public void close() {
      DebugLog.d("Enter AidcManager.close()");
      if (this.mConnection != null) {
         this.mContext.unbindService(this.mConnection);
         this.mConnection = null;
      }

      DebugLog.d("Exit AidcManager.close()");
   }

   public BarcodeReader createBarcodeReader() throws InvalidScannerNameException {
      List<BarcodeReaderInfo> scanners = this.listConnectedBarcodeDevices();
      BarcodeReader barcodeReader = null;
      int iCountConnectedScanners = scanners.size();

      for (int i = 0; i < iCountConnectedScanners; i++) {
         if (scanners.get(i).getName().equals("dcs.scanner.imager")) {
            return this.createBarcodeReader("dcs.scanner.imager");
         }

         if (scanners.get(i).getName().equals("dcs.scanner.serial1")) {
            return this.createBarcodeReader("dcs.scanner.serial1");
         }
      }

      return barcodeReader;
   }

   public BarcodeReader createBarcodeReader(String scannerName) throws InvalidScannerNameException {
      Message request = DcsJsonRpcHelper.build("scanner.connect", "scanner", scannerName);
      Message response = this.execute(request);
      DcsJsonRpcHelper.checkRuntimeAndScannerExceptions(response);
      IExecutor session = IExecutor.Stub.asInterface((IBinder)response.extras.get("session"));
      return new BarcodeReader(session);
   }

   public Message execute(Message request) {
      try {
         return this.mService.execute(request);
      } catch (RemoteException var3) {
         throw new RuntimeException("Failed to execute request", var3);
      }
   }

   public Context getContext() {
      return this.mContext;
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
      sListeners.put(AidcManager.BarcodeDeviceListener.class, new HashMap<>());
      sListenerCounts.put(AidcManager.BarcodeDeviceListener.class, new HashMap<>());
   }

   public interface BarcodeDeviceListener extends EventListener {
      void onBarcodeDeviceConnectionEvent(BarcodeDeviceConnectionEvent var1);
   }

   public interface CreatedCallback {
      void onCreated(AidcManager var1);
   }
}
