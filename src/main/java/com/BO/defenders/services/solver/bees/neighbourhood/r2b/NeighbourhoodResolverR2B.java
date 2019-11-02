package com.BO.defenders.services.solver.bees.neighbourhood.r2b;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import com.BO.defenders.model.*;
import com.BO.defenders.services.costcalculator.CostCalculator;
import com.BO.defenders.services.costcalculator.CostCalculatorManager;
import com.BO.defenders.services.solver.bees.neighbourhood.ForHoodType;
import com.BO.defenders.services.solver.bees.neighbourhood.HoodType;
import com.BO.defenders.services.solver.bees.neighbourhood.NeighbourhoodResolver;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static java.util.Objects.nonNull;

@Slf4j
@Service
@RequiredArgsConstructor
@ForHoodType(hoodType = HoodType.RANDOM_TWO_UNITS_BEST_CHOICE)
public class NeighbourhoodResolverR2B implements NeighbourhoodResolver {

  private static final int RETRIES_IF_SAME_SECTOR_UNITS_SELECTED = 15;

  private final Random random = new Random();
  private final CostCalculatorManager costCalculatorManager;

  @Override
  public Solution getNeighbouringSolution(Problem problem, Solution prevSolution) {
    log.debug("Searching neighbouring solution for r2b");
    ProblemConfig config = problem.getProblemConfig();
    FieldMatrix defendersMatrix = prevSolution.getDefendersMatrix();
    RandomUnit one = getRandomDefender(defendersMatrix, problem.getAttackersMatrix(), null);
    RandomUnit two = getRandomDefender(defendersMatrix, problem.getAttackersMatrix(), one);

    CostCalculator costCalculator = costCalculatorManager.getCalculator(problem.getProblemConfig().getCostType());
    boolean calculateLocalCostAvailable = costCalculator.calculateLocalCostAvailable();
    List<AlterOperation> possibleOperations = new ArrayList<>();
    possibleOperations.add(new AlterOperation(matrix -> alterMatrixByPush(matrix, one, two),
                                              calculateLocalCostAvailable ? calcCostOfPush(costCalculator, one, two, config) : random.nextDouble()));
    possibleOperations.add(new AlterOperation(matrix -> alterMatrixByPush(matrix, two, one),
                                              calculateLocalCostAvailable ? calcCostOfPush(costCalculator, two, one, config) : random.nextDouble()));
    possibleOperations.add(new AlterOperation(matrix -> alterMatrixBySwitch(matrix, one, two),
                                              calculateLocalCostAvailable ? calcCostOfSwitch(costCalculator, one, two, config) : random.nextDouble()));

    possibleOperations.stream()
      .min(Comparator.comparing(AlterOperation::getCost)).get().getOperation()
      .accept(defendersMatrix);

    return prevSolution;
  }

  private RandomUnit getRandomDefender(FieldMatrix defendersMatrix, FieldMatrix attackersMatrix, @Nullable RandomUnit otherThan) {
    int defIndex = random.nextInt(defendersMatrix.getUnitsNumber());
    int defSectorIndex = defendersMatrix.getUnitSector(defIndex);

    if (nonNull(otherThan)) {
      for (int i = 0; i < RETRIES_IF_SAME_SECTOR_UNITS_SELECTED && defSectorIndex == otherThan.getUnitSector(); i++) {
        defIndex = random.nextInt(defendersMatrix.getUnitsNumber());
        defSectorIndex = defendersMatrix.getUnitSector(defIndex);
      }
    }

    Unit defender = defendersMatrix.getUnit(defIndex);
    List<Unit> defSectorDefenders = defendersMatrix.getSectorUnits(defSectorIndex);
    List<Unit> defSectorAttackers = attackersMatrix.getSectorUnits(defSectorIndex);
    return new RandomUnit(defender, defIndex, defSectorIndex, defSectorDefenders, defSectorAttackers);
  }

  private void alterMatrixBySwitch(FieldMatrix defendersMatrix, RandomUnit one, RandomUnit two) {
    defendersMatrix.unassign(one.getUnitSector(), one.getUnitIndex());
    defendersMatrix.assign(two.getUnitSector(), one.getUnitIndex());

    defendersMatrix.unassign(two.getUnitSector(), two.getUnitIndex());
    defendersMatrix.assign(one.getUnitSector(), two.getUnitIndex());
  }

  private void alterMatrixByPush(FieldMatrix defendersMatrix, RandomUnit from, RandomUnit to) {
    defendersMatrix.unassign(from.getUnitSector(), from.getUnitIndex());
    defendersMatrix.assign(to.getUnitSector(), from.getUnitIndex());
  }

  private Double calcCostOfSwitch(CostCalculator costCalculator, RandomUnit one, RandomUnit two, ProblemConfig config) {
    List<Unit> oneDefenders = new ArrayList<>(one.getUnitSectorDefenders());
    List<Unit> twoDefenders = new ArrayList<>(two.getUnitSectorDefenders());

    oneDefenders.remove(one.getUnit());
    twoDefenders.add(one.getUnit());

    twoDefenders.remove(two.getUnit());
    oneDefenders.add(two.getUnit());

    return costCalculator.mergeTwoLocalCosts(costCalculator.calculateLocalCost(one.getUnitSectorAttackers(), oneDefenders, config),
                                             costCalculator.calculateLocalCost(two.getUnitSectorAttackers(), twoDefenders, config));
  }

  private Double calcCostOfPush(CostCalculator costCalculator, RandomUnit from, RandomUnit to, ProblemConfig config) {
    List<Unit> fromDefenders = new ArrayList<>(from.getUnitSectorDefenders());
    List<Unit> toDefenders = new ArrayList<>(to.getUnitSectorDefenders());

    fromDefenders.remove(from.getUnit());
    toDefenders.add(from.getUnit());

    return costCalculator.mergeTwoLocalCosts(costCalculator.calculateLocalCost(from.getUnitSectorAttackers(), fromDefenders, config),
                                             costCalculator.calculateLocalCost(to.getUnitSectorAttackers(), toDefenders, config));
  }

}
