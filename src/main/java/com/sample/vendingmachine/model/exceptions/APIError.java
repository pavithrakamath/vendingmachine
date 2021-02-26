package com.sample.vendingmachine.model.exceptions;

public class APIError {
  long errorCode;
  String errorMessage;
  Throwable cause;

  public long getErrorCode() {
    return errorCode;
  }

  public void setErrorCode(long errorCode) {
    this.errorCode = errorCode;
  }

  public Throwable getCause() {
    return cause;
  }

  public void setCause(Throwable cause) {
    this.cause = cause;
  }

  public String getErrorMessage() {
    return errorMessage;
  }

  public void setErrorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
  }
}
