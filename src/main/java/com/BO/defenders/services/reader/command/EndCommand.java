package com.BO.defenders.services.reader.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class EndCommand extends Command {

  @Override
  public CommandType getType() {
    return CommandType.END;
  }
}
