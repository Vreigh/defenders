package com.BO.defenders.services.reader.error;

import lombok.Getter;

@Getter
public class CommandParseException extends Exception {

  public CommandParseException(String message) {
    super(message);
  }
}

