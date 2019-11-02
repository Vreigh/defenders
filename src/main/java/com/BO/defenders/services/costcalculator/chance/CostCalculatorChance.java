package com.BO.defenders.services.costcalculator.chance;

import java.util.ArrayList;
import java.util.List;

import com.BO.defenders.model.*;
import com.BO.defenders.services.costcalculator.CostCalculator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class CostCalculatorChance implements CostCalculator {

  @Override
  public void calculateCost(Problem problem, Solution solution) {
    log.debug("Calculating solution cost...");
    FieldMatrix attackersMatrix = problem.getAttackersMatrix();
    FieldMatrix defendersMatrix = solution.getDefendersMatrix();

    List<Double> surviveChances = new ArrayList<>();

    for (int sectorIndex = 0; sectorIndex < attackersMatrix.getSectorsNumber(); sectorIndex++) {
      List<Unit> sectorAttackers = attackersMatrix.getSectorUnits(sectorIndex);
      List<Unit> sectorDefenders = defendersMatrix.getSectorUnits(sectorIndex);
      addSectorSurviveChances(sectorAttackers, sectorDefenders, surviveChances, problem.getProblemConfig());
    }

    Double totalSurviveChance = getListProduct(surviveChances);
    solution.setCost(1.0 - totalSurviveChance);
    log.debug("Calculated solution cost is {}", solution.getCost());
  }

  @Override
  public boolean calculateLocalCostAvailable() {
    return true;
  }

  @Override
  public double calculateLocalCost(List<Unit> sectorAttackers, List<Unit> sectorDefenders, ProblemConfig config) {
    List<Double> surviveChances = new ArrayList<>();
    addSectorSurviveChances(sectorAttackers, sectorDefenders, surviveChances, config);
    Double localSurviveChance = getListProduct(surviveChances);
    return 1.0 - localSurviveChance;
  }

  @Override
  public double mergeTwoLocalCosts(double one, double two) {
    double chanceOne = 1.0 - one;
    double chanceTwo = 1.0 - two;
    return 1.0 - chanceOne * chanceTwo;
  }

  private Double getListProduct(List<Double> surviveChances) {
    return surviveChances.stream().mapToDouble(d -> d).reduce(1, (a, b) -> a * b);
  }

  protected abstract void addSectorSurviveChances(List<Unit> sectorAttackers, List<Unit> sectorDefenders, List<Double> surviveChances, ProblemConfig config);

}
