package com.honeywell;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

public interface IExecutor extends IInterface {
   Message execute(Message var1) throws RemoteException;

   void executeAsync(Message var1, IExecutor var2) throws RemoteException;

   public abstract static class Stub extends Binder implements IExecutor {
      private static final String DESCRIPTOR = "com.honeywell.IExecutor";
      static final int TRANSACTION_execute = 1;
      static final int TRANSACTION_executeAsync = 2;

      public Stub() {
         super();
         this.attachInterface(this, "com.honeywell.IExecutor");
      }

      public static IExecutor asInterface(IBinder obj) {
         if (obj == null) {
            return null;
         } else {
            IInterface iin = obj.queryLocalInterface("com.honeywell.IExecutor");
            return (IExecutor)(iin != null && iin instanceof IExecutor ? (IExecutor)iin : new IExecutor.Stub.Proxy(obj));
         }
      }

      public IBinder asBinder() {
         return this;
      }

      public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
         String descriptor = "com.honeywell.IExecutor";
         switch (code) {
            case 1:
               data.enforceInterface(descriptor);
               Message _arg0;
               if (0 != data.readInt()) {
                  _arg0 = (Message)Message.CREATOR.createFromParcel(data);
               } else {
                  _arg0 = null;
               }

               Message _result = this.execute(_arg0);
               reply.writeNoException();
               if (_result != null) {
                  reply.writeInt(1);
                  _result.writeToParcel(reply, Parcelable.PARCELABLE_WRITE_RETURN_VALUE);
               } else {
                  reply.writeInt(0);
               }

               return true;
            case 2:
               data.enforceInterface(descriptor);
               Message _arg1;
               if (0 != data.readInt()) {
                  _arg1 = (Message)Message.CREATOR.createFromParcel(data);
               } else {
                  _arg1 = null;
               }

               IExecutor _arg2 = asInterface(data.readStrongBinder());
               this.executeAsync(_arg1, _arg2);
               return true;
            case 1598968902:
               reply.writeString(descriptor);
               return true;
            default:
               return super.onTransact(code, data, reply, flags);
         }
      }

      private static class Proxy implements IExecutor {
         private IBinder mRemote;

         Proxy(IBinder remote) {
            super();
            this.mRemote = remote;
         }

         public IBinder asBinder() {
            return this.mRemote;
         }

         public String getInterfaceDescriptor() {
            return "com.honeywell.IExecutor";
         }

         @Override
         public Message execute(Message m) throws RemoteException {
            Parcel _data = Parcel.obtain();
            Parcel _reply = Parcel.obtain();

            Message _result;
            try {
               _data.writeInterfaceToken("com.honeywell.IExecutor");
               if (m != null) {
                  _data.writeInt(1);
                  m.writeToParcel(_data, 0);
               } else {
                  _data.writeInt(0);
               }

               this.mRemote.transact(1, _data, _reply, 0);
               _reply.readException();
               if (0 != _reply.readInt()) {
                  _result = (Message)Message.CREATOR.createFromParcel(_reply);
               } else {
                  _result = null;
               }
            } finally {
               _reply.recycle();
               _data.recycle();
            }

            return _result;
         }

         @Override
         public void executeAsync(Message m, IExecutor callback) throws RemoteException {
            Parcel _data = Parcel.obtain();

            try {
               _data.writeInterfaceToken("com.honeywell.IExecutor");
               if (m != null) {
                  _data.writeInt(1);
                  m.writeToParcel(_data, 0);
               } else {
                  _data.writeInt(0);
               }

               _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
               this.mRemote.transact(2, _data, null, 1);
            } finally {
               _data.recycle();
            }
         }
      }
   }
}
