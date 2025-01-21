package com.honeywell.aidc;

import java.util.EventObject;

public final class TriggerStateChangeEvent extends EventObject {
   private static final long serialVersionUID = 1L;
   private boolean mState;

   TriggerStateChangeEvent(Object source, boolean state) {
      super(source);
      DebugLog.d("Enter constructor");
      DebugLog.d("state = " + (state ? "pressed" : "released"));
      this.mState = state;
      DebugLog.d("Exit constructor");
   }

   public boolean getState() {
      return this.mState;
   }
}
