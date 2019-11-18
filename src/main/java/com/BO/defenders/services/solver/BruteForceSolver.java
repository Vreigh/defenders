package com.BO.defenders.services.solver;

import org.springframework.stereotype.Service;

import com.BO.defenders.model.Problem;
import com.BO.defenders.model.Solution;
import com.BO.defenders.model.factory.FieldMatrixFactory;
import com.BO.defenders.services.costcalculator.CostCalculatorManager;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@ForSolveType(solveType = SolveType.FORCE)
public class BruteForceSolver implements ProblemSolver<Integer> {

  private final CostCalculatorManager costCalculatorManager;
  private final FieldMatrixFactory fieldMatrixFactory;

  private Double bestChance = 1D;
  private Solution bestSolution;

  @Override
  public Solution solve(Problem problem, Integer params) {
    bestChance = 1D;
    Solution solution = new Solution(fieldMatrixFactory.newMatrix(problem.getProblemConfig().getSectorsNumber(), problem.getDefenders()));
    solve(solution, problem, 0);
    costCalculatorManager.calculateCost(problem, bestSolution);
    return bestSolution;
  }

  private void solve(Solution solution, Problem problem, int defenderIndex) {
    if (defenderIndex == problem.getDefenders().size()) {
      calculateChance(solution, problem);
      return;
    }
    for (int i = 0; i < problem.getProblemConfig().getSectorsNumber(); i++) {
      solution.getDefendersMatrix().assign(i, defenderIndex);
      solve(solution, problem, defenderIndex + 1);
      solution.getDefendersMatrix().unassign(i, defenderIndex);
    }
  }

  private void calculateChance(Solution solution, Problem problem) {
    costCalculatorManager.calculateCost(problem, solution);
    Double chance = solution.getCost();
    if (chance < bestChance) {
      bestChance = chance;
      this.bestSolution = solution.clone();
    }
  }
}
