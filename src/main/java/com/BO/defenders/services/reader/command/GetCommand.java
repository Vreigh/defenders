package com.BO.defenders.services.reader.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetCommand extends Command {

  @Override
  public CommandType getType() {
    return CommandType.GET;
  }
}
