package com.BO.defenders.services.costcalculator;

import com.BO.defenders.model.Problem;
import com.BO.defenders.model.Solution;

public interface CostCalculator {

  /**
   * Calculates cost of the given solution for the given problem
   *
   * @param problem  the base problem
   * @param solution the proposed solution
   * @return calculated cost of the solution for the problem
   */
  void calculateCost(Problem problem, Solution solution);
}
