package com.BO.defenders.services.reader.command;

import com.BO.defenders.services.costcalculator.CostCalculationType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NewCommand extends Command {

  private int sectorsNumber = 8;
  private int attackersNumber = 40;
  private int defendersNumber = 35;
  private CostCalculationType costType = CostCalculationType.CHANCE_FOR_ALL;
  private int statsNumber = 3;
  private int statsSum = 7;


  @Override
  public CommandType getType() {
    return CommandType.NEW;
  }
}
