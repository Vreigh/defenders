package com.BO.defenders.services.solver.bees;

import com.BO.defenders.services.solver.bees.neighbourhood.HoodType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BeesParams {

  private int iterations = 10;
  private HoodType neighbourhoodType = HoodType.TODO2;
  private int population = 10;
  private int selected = 5;
  private int elite = 2;
  private int recruitedAroundNormal = 2;
  private int recruitedAroundElite = 4;

}
