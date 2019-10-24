package com.BO.defenders.services.costcalculator.chance;

import java.util.ArrayList;
import java.util.List;

import com.BO.defenders.model.FieldMatrix;
import com.BO.defenders.model.Problem;
import com.BO.defenders.model.Solution;
import com.BO.defenders.model.Unit;
import com.BO.defenders.services.costcalculator.CostCalculator;
import com.BO.defenders.util.MatrixUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class CostCalculatorChance implements CostCalculator {

  @Override
  public void calculateCost(Problem problem, Solution solution) {
    log.debug("Calculating solution cost...");
    FieldMatrix attackersMatrix = problem.getAttackersMatrix();
    FieldMatrix defendersMatrix = solution.getDefendersMatrix();
    List<Unit> attackers = problem.getAttackers();
    List<Unit> defenders = problem.getDefenders();

    List<Double> surviveChances = new ArrayList<>();

    for (int sectorIndex = 0; sectorIndex < attackersMatrix.getSectorsNumber(); sectorIndex++) {
      List<Unit> sectorAttackers = MatrixUtils.getSectorUnits(attackersMatrix, sectorIndex, attackers);
      List<Unit> sectorDefenders = MatrixUtils.getSectorUnits(defendersMatrix, sectorIndex, defenders);
      addSectorSurviveChances(sectorAttackers, sectorDefenders, surviveChances);
    }

    Double totalSurviveChance = surviveChances.stream().mapToDouble(d -> d).reduce(1, (a, b) -> a * b);
    solution.setCost(1.0 - totalSurviveChance);
    log.debug("Calculated solution cost is {}", solution.getCost());
  }

  protected abstract void addSectorSurviveChances(List<Unit> sectorAttackers, List<Unit> sectorDefenders, List<Double> surviveChances);

}
