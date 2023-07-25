package com.example.auditing.exception;

public class UserNotFoundException extends RuntimeException {
  public UserNotFoundException(String userName) {
    super("Could not find the user with the following id " + userName);
  }
}
