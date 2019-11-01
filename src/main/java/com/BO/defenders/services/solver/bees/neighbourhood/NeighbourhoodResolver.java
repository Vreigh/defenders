package com.BO.defenders.services.solver.bees.neighbourhood;

import com.BO.defenders.model.Problem;
import com.BO.defenders.model.Solution;

public interface NeighbourhoodResolver {

  /**
   * Gets a solution in neighbourhood to the provided previous solution
   *
   * @param problem      the original problem, should not be modified in any way
   * @param prevSolution the previous solution from which to start - should be a copy, thus can be edited
   * @return new neighbouring solution. May or may not have it's cost already calculated (if it has, the cost will not be calculated again)
   */
  Solution getNeighbouringSolution(Problem problem, Solution prevSolution);
}
