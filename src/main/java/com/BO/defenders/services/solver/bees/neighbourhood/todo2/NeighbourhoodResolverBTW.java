package com.BO.defenders.services.solver.bees.neighbourhood.todo2;

import com.BO.defenders.model.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Service;

import com.BO.defenders.services.solver.bees.neighbourhood.ForHoodType;
import com.BO.defenders.services.solver.bees.neighbourhood.HoodType;
import com.BO.defenders.services.solver.bees.neighbourhood.NeighbourhoodResolver;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@ForHoodType(hoodType = HoodType.BEST_TO_WORST)
public class NeighbourhoodResolverBTW implements NeighbourhoodResolver {

  /**
   * Wyszukuje sektor gdzie atakujacy zwyciezaja najbardziej tj suma roznicy wszystkich attr jest
   * najwieszka i dodaje do tego sektora najlepszego obronce z pozostalych sektorow tj takiego ktory ma
   * najwieksze statystyki
   */

  @Override
  public Solution getNeighbouringSolution(Problem problem, Solution prevSolution) {
    FieldMatrix attackers = problem.getAttackersMatrix();
    FieldMatrix defenders = prevSolution.getDefendersMatrix();
    List<Unit> attackersUnits = problem.getAttackersMatrix().getUnits();
    List<Unit> defenderUnits = problem.getDefenders();
    Result result = calculateBiggestDifference(attackers, defenders, attackersUnits, defenderUnits, problem.getProblemConfig());
    if (result.sector == -1)
      return prevSolution;
    swapDefenderUnit(result, defenders, defenderUnits);
    return prevSolution;
  }

  private void swapDefenderUnit(Result result, FieldMatrix defenders, List<Unit> defenderUnits) {
    int bestValue = Integer.MAX_VALUE;
    int bestIndex = -1;
    int bestSector = -1;
    int worstValue = Integer.MIN_VALUE;
    int worstIndex = -1;
    for (int i = 0; i<defenderUnits.size(); i++) {
      Unit unit = defenderUnits.get(i);
      if(defenders.getMatrix()[result.getSector()][i]) {
        if (calculateWorst(unit, result.getDifference()) > worstValue) {
          worstValue = calculateWorst(unit, result.getDifference());
          worstIndex = i;
        }
      } else {
        if (calculateBest(unit, result.getDifference()) < bestValue) {
          bestValue = calculateBest(unit, result.getDifference());
          bestIndex = i;
          bestSector = findSector(defenders.getMatrix(), i);
        }
      }
    }
//    swapDefenders(bestIndex, worstIndex, defenderUnits);
    defenders.getMatrix()[bestSector][bestIndex] = false;
    defenders.getMatrix()[result.getSector()][bestIndex] = true;
  }

  private int findSector(boolean[][] matrixView, int i) {
    for(int j = 0; j < matrixView.length; j++)
      if(matrixView[j][i])
        return j;
      return -1;
  }

  private int calculateBest(Unit unit, Unit difference) {
    int result = 0;
    for (int i =0; i < unit.getStatsNumber(); i++)
      result += unit.getStat(i) - difference.getStat(i);
    return result;
  }

  private int calculateWorst(Unit unit, Unit difference) {
    int result = 0;
    for (int i =0; i < unit.getStatsNumber(); i++)
      result +=  unit.getStat(i) - difference.getStat(i);
    return result;
  }

  private void swapDefenders(int i, int j, List<Unit> defenders) {
    Unit u = defenders.get(i);
    defenders.set(i, defenders.get(j));
    defenders.set(j, u);
  }

  private Result calculateBiggestDifference(FieldMatrix attackers, FieldMatrix defenders, List<Unit> attackersUnits, List<Unit> defenderUnits, ProblemConfig problemConfig) {
    int biggestDifference = Integer.MIN_VALUE;
    Unit result = null;
    int sector = -1;
    for(int i = 0; i < attackers.getSectorsNumber(); i++){
      List<Unit> attackersList = new LinkedList<>();
      List<Unit> defendersList = new LinkedList<>();
      for (int j = 0; j < attackers.getUnitsNumber(); j++) {
        if (attackers.getMatrix()[i][j])
          attackersList.add(attackersUnits.get(j));
      }
      for (int j = 0; j < defenders.getUnitsNumber(); j++) {
        if (defenders.getMatrix()[i][j])
          defendersList.add(defenderUnits.get(j));
      }
      Unit attackerSum = calc(attackersList, problemConfig);
      Unit defenderSum = calc(defendersList, problemConfig);
      List<Integer> resultList = new LinkedList<>();
      for (int x = 0; x<defenders.getUnits().get(0).getStatsNumber(); x++){
        int sum = attackerSum.getStat(x) - defenderSum.getStat(x);
        resultList.add(x, sum < 0 ? 0 : sum);
        if(sum > biggestDifference){
          biggestDifference = sum;
          result = new Unit(resultList);
          sector = i;
        }
      }
    }
    System.out.println("Sector: "+sector + " :" + result);
    return new Result(result, sector);
  }

  private Unit calc(List<Unit> unitsList, ProblemConfig problemConfig) {
    List<Integer> stats = new LinkedList<>();
    for (int i = 0; i< problemConfig.getStatsNumber(); i++)
      stats.add(0);
    for (Unit unit : unitsList){
      for (int i = 0; i<unit.getStatsNumber(); i++) {
        stats.set(i,stats.get(i) + unit.getStat(i));
      }
    }
    return new Unit(stats);
  }

  @Getter
  @AllArgsConstructor
  private class Result{
    private Unit difference;
    private int sector;
  }
}
