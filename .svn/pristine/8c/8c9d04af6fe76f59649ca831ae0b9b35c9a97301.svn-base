package com.dat.whm.exception;


 /**
  * @author Zaw Than Oo
  */
 public class DAOException extends RuntimeException {
     private String errorCode;
     
     public DAOException(String errorCode, String message, Throwable throwable){
         super(message, throwable);
         this.errorCode = errorCode;
     }

     public String getErrorCode() {
         return errorCode;
     }
 }
