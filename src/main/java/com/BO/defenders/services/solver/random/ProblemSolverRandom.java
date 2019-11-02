package com.BO.defenders.services.solver.random;

import java.util.Random;

import org.springframework.stereotype.Service;

import com.BO.defenders.model.FieldMatrix;
import com.BO.defenders.model.Problem;
import com.BO.defenders.model.Solution;
import com.BO.defenders.model.factory.FieldMatrixFactory;
import com.BO.defenders.services.costcalculator.CostCalculatorManager;
import com.BO.defenders.services.solver.ForSolveType;
import com.BO.defenders.services.solver.ProblemSolver;
import com.BO.defenders.services.solver.SolveType;
import com.BO.defenders.util.MatrixUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@ForSolveType(solveType = SolveType.RANDOM)
public class ProblemSolverRandom implements ProblemSolver<Integer> {

  private final CostCalculatorManager costCalculatorManager;
  private final FieldMatrixFactory fieldMatrixFactory;
  private final Random random = new Random();

  @Override
  public Solution solve(Problem problem, Integer iterations) {
    log.info("Solving the problem randomly...");
    Solution solution = createNewRandomSolution(problem);
    costCalculatorManager.calculateCost(problem, solution);
    for (int i = 0; i < iterations; i++) {
      Solution otherSolution = createNewRandomSolution(problem);
      costCalculatorManager.calculateCost(problem, otherSolution);
      if (otherSolution.getCost() < solution.getCost()) {
        solution = otherSolution;
      }
    }
    return solution;
  }

  private Solution createNewRandomSolution(Problem problem) {
    FieldMatrix defendersMatrix = fieldMatrixFactory.newMatrix(problem.getProblemConfig().getSectorsNumber(), problem.getDefenders());
    MatrixUtils.randomButEnsureOneInAllSectorsFillUnitMatrix(defendersMatrix, random);
    return new Solution(defendersMatrix);
  }
}
