package com.BO.defenders.model;

import com.BO.defenders.services.costcalculator.CostCalculationType;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ProblemConfig {

  private CostCalculationType costType;
  private int sectorsNumber;
  private int statsNumber;
  private int statsSum;
  private int attackersNumber;
  private int defendersNumber;
}
