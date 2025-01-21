package com.honeywell.aidc;

public class AidcException extends Exception {
   private static final long serialVersionUID = 1L;

   AidcException(String message) {
      super(message);
   }

   AidcException(String message, Throwable innerException) {
      super(message, innerException);
   }
}
