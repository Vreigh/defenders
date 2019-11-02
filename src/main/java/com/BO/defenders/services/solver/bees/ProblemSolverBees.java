package com.BO.defenders.services.solver.bees;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.BO.defenders.model.FieldMatrix;
import com.BO.defenders.model.Problem;
import com.BO.defenders.model.Solution;
import com.BO.defenders.model.factory.FieldMatrixFactory;
import com.BO.defenders.services.costcalculator.CostCalculator;
import com.BO.defenders.services.costcalculator.CostCalculatorManager;
import com.BO.defenders.services.solver.ForSolveType;
import com.BO.defenders.services.solver.ProblemSolver;
import com.BO.defenders.services.solver.SolveType;
import com.BO.defenders.services.solver.bees.neighbourhood.NeighbourhoodResolver;
import com.BO.defenders.services.solver.bees.neighbourhood.NeighbourhoodResolverManager;
import com.BO.defenders.util.MatrixUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static java.util.Objects.isNull;

@Slf4j
@Service
@RequiredArgsConstructor
@ForSolveType(solveType = SolveType.BEES)
public class ProblemSolverBees implements ProblemSolver<BeesParams> {

  private final CostCalculatorManager costCalculatorManager;
  private final NeighbourhoodResolverManager neighbourhoodResolverManager;
  private final FieldMatrixFactory fieldMatrixFactory;
  private final Random random = new Random();

  @Override
  public Solution solve(Problem problem, BeesParams params) {
    CostCalculator costCalculator = costCalculatorManager.getCalculator(problem.getProblemConfig().getCostType());
    NeighbourhoodResolver neighbourhoodResolver = neighbourhoodResolverManager.getResolver(params.getNeighbourhoodType());

    List<Solution> population = getRandomInitPopulation(problem, params);
    calcCostForSolutionsIfNeeded(population, problem, costCalculator);
    for (int iteration = 0; iteration < params.getIterations(); iteration++) {
      population.sort(Comparator.comparing(Solution::getCost));
      log.info("Current best cost: {}", population.get(0).getCost());

      List<Solution> newPopulation = new ArrayList<>();
      int populationIndex = 0;

      for (int i = 0; i < params.getElite(); i++) {
        Solution eliteSolution = population.get(populationIndex);
        newPopulation.add(eliteSolution);
        newPopulation.add(probeSolution(problem, eliteSolution, neighbourhoodResolver, costCalculator, params.getRecruitedAroundElite()));
        populationIndex++;
      }

      for (int i = 0; i < params.getSelected() - params.getElite(); i++) {
        Solution chosenNormalSolution = population.get(populationIndex);
        newPopulation.add(probeSolution(problem, chosenNormalSolution, neighbourhoodResolver, costCalculator, params.getRecruitedAroundNormal()));
        populationIndex++;
      }

      while (newPopulation.size() < params.getPopulation()) {
        newPopulation.add(createNewRandomSolution(problem));
      }

      population = newPopulation;
      calcCostForSolutionsIfNeeded(population, problem, costCalculator);
    }
    return population.stream().min(Comparator.comparing(Solution::getCost)).get();
  }

  private void calcCostForSolutionsIfNeeded(List<Solution> population, Problem problem, CostCalculator costCalculator) {
    population.forEach(solution -> {
      if (isNull(solution.getCost())) {
        costCalculator.calculateCost(problem, solution);
      }
    });
  }

  private Solution probeSolution(Problem problem, Solution prevSolution,
                                 NeighbourhoodResolver neighbourhoodResolver, CostCalculator costCalculator, int probes) {
    Solution bestAreaSolution = new Solution(prevSolution.getDefendersMatrix().clone());
    bestAreaSolution = neighbourhoodResolver.getNeighbouringSolution(problem, bestAreaSolution);
    costCalculator.calculateCost(problem, bestAreaSolution);
    for (int i = 0; i < probes - 1; i++) {
      Solution otherAreaSolution = new Solution(prevSolution.getDefendersMatrix().clone());
      otherAreaSolution = neighbourhoodResolver.getNeighbouringSolution(problem, otherAreaSolution);
      costCalculator.calculateCost(problem, otherAreaSolution);
      if (otherAreaSolution.getCost() < bestAreaSolution.getCost()) {
        bestAreaSolution = otherAreaSolution;
      }
    }
    return bestAreaSolution;
  }

  private List<Solution> getRandomInitPopulation(Problem problem, BeesParams params) {
    List<Solution> population = new ArrayList<>();
    for (int i = 0; i < params.getPopulation(); i++) {
      population.add(createNewRandomSolution(problem));
    }
    return population;
  }

  private Solution createNewRandomSolution(Problem problem) {
    FieldMatrix defendersMatrix = fieldMatrixFactory.newMatrix(problem.getProblemConfig().getSectorsNumber(), problem.getDefenders());
    MatrixUtils.randomButEnsureOneInAllSectorsFillUnitMatrix(defendersMatrix, random);
    return new Solution(defendersMatrix);
  }
}
