package com.honeywell.aidc;

public final class ScannerNotClaimedException extends AidcException {
   private static final long serialVersionUID = 1L;

   ScannerNotClaimedException(String message) {
      super(message);
   }

   ScannerNotClaimedException(String message, Throwable innerException) {
      super(message, innerException);
   }
}
