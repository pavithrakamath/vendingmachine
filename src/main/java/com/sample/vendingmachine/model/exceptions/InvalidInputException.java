package com.sample.vendingmachine.model.exceptions;

public class InvalidInputException extends Exception {
  public InvalidInputException(String message) {
    super(message);
  }
}
