package com.BO.defenders.services.costcalculator;

import java.util.List;

import com.BO.defenders.model.Problem;
import com.BO.defenders.model.ProblemConfig;
import com.BO.defenders.model.Solution;
import com.BO.defenders.model.Unit;

public interface CostCalculator {

  /**
   * Calculates cost of the given solution for the given problem
   *
   * @param problem  the base problem
   * @param solution the proposed solution
   * @return calculated cost of the solution for the problem
   */
  void calculateCost(Problem problem, Solution solution);

  default boolean calculateLocalCostAvailable() {
    return false;
  }

  default double calculateLocalCost(List<Unit> sectorAttackers, List<Unit> sectorDefenders, ProblemConfig config) {
    return -1.0;
  }

  default double mergeTwoLocalCosts(double one, double two) {
    return -1.0;
  }
}
