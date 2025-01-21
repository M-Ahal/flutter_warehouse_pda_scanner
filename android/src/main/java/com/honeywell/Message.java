package com.honeywell;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import java.util.Map;

public class Message implements Parcelable {
   public String action = null;
   public Map<String, Object> extras = null;
   public static Creator<Message> CREATOR = new Creator<Message>() {
      public Message[] newArray(int size) {
         return new Message[size];
      }

      public Message createFromParcel(Parcel source) {
         Message m = new Message();
         m.action = (String)source.readValue(this.getClass().getClassLoader());
         m.extras = (Map<String, Object>)source.readValue(this.getClass().getClassLoader());
         return m;
      }
   };

   public Message() {
      super();
   }

   public Message(String action) {
      super();
      this.action = action;
   }

   public Message(String action, Map<String, Object> extras) {
      super();
      this.action = action;
      this.extras = extras;
   }

   public int describeContents() {
      return Parcelable.CONTENTS_FILE_DESCRIPTOR;
   }

   public void writeToParcel(Parcel dest, int flags) {
      dest.writeValue(this.action);
      dest.writeValue(this.extras);
   }
}
