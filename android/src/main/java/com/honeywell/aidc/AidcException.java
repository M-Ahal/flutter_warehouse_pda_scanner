package com.honeywell.aidc;

import java.io.Serial;

public class AidcException extends Exception {
   @Serial
   private static final long serialVersionUID = 1L;

   AidcException(String message) {
      super(message);
   }

   AidcException(String message, Throwable innerException) {
      super(message, innerException);
   }
}
