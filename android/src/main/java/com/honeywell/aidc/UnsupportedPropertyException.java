package com.honeywell.aidc;

public final class UnsupportedPropertyException extends AidcException {
   private static final long serialVersionUID = 1L;

   UnsupportedPropertyException(String message) {
      super(message);
   }

   UnsupportedPropertyException(String message, Throwable innerException) {
      super(message, innerException);
   }
}
