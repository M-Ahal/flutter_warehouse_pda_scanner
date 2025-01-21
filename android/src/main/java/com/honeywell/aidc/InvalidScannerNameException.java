package com.honeywell.aidc;

public final class InvalidScannerNameException extends AidcException {
   private static final long serialVersionUID = 1L;

   InvalidScannerNameException(String message) {
      super(message);
   }

   InvalidScannerNameException(String message, Throwable innerException) {
      super(message, innerException);
   }
}
