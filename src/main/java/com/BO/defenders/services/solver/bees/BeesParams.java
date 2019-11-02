package com.BO.defenders.services.solver.bees;

import com.BO.defenders.services.solver.bees.neighbourhood.HoodType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BeesParams {

  private HoodType neighbourhoodType = HoodType.RANDOM_TWO_UNITS_BEST_CHOICE;
  private int population = 10;
  private int selected = 5;
  private int elite = 2;
  private int recruitedAroundNormal = 2;
  private int recruitedAroundElite = 4;
  private int iterations = 100;
}
