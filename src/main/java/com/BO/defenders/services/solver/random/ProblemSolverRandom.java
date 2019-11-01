package com.BO.defenders.services.solver.random;

import java.util.Random;

import org.springframework.stereotype.Service;

import com.BO.defenders.model.*;
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
public class ProblemSolverRandom implements ProblemSolver<Void> {

  private final CostCalculatorManager costCalculatorManager;
  private final FieldMatrixFactory fieldMatrixFactory;
  private final Random random = new Random();

  @Override
  public Solution solve(Problem problem, Void params) {
    log.info("Solving the problem...");
    ProblemConfig config = problem.getProblemConfig();

    FieldMatrix defendersMatrix = fieldMatrixFactory.newMatrix(config.getSectorsNumber(), problem.getDefenders());
    MatrixUtils.randomButEnsureOneInAllSectorsFillUnitMatrix(defendersMatrix, random);

    Solution solution = new Solution(defendersMatrix);
    costCalculatorManager.calculateCost(problem, solution);
    return solution;
  }
}
