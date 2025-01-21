package com.honeywell.aidc;

public final class ScannerUnavailableException extends AidcException {
   private static final long serialVersionUID = 1L;

   ScannerUnavailableException(String message) {
      super(message);
   }

   ScannerUnavailableException(String message, Throwable innerException) {
      super(message, innerException);
   }
}
