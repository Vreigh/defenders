package com.BO.defenders.services.costcalculator.chance;

import java.util.List;

import org.springframework.stereotype.Service;

import com.BO.defenders.model.Unit;
import com.BO.defenders.services.costcalculator.CostCalculationType;
import com.BO.defenders.services.costcalculator.ForCostCalculationType;
import com.BO.defenders.util.UnitsUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@ForCostCalculationType(costCalculationType = CostCalculationType.CHANCE_FOR_ALL)
public class CostCalculatorChanceForAll extends CostCalculatorChance {

  @Override
  protected void addSectorSurviveChances(List<Unit> sectorAttackers, List<Unit> sectorDefenders, List<Double> surviveChances) {
    List<Integer> totalAttackerStats = UnitsUtils.sumUnits(sectorAttackers);
    List<Integer> totalDefendersStats = UnitsUtils.sumUnits(sectorDefenders);
    for(int i=0; i<totalAttackerStats.size(); i++) {
      surviveChances.add(calculateSurviveChance(totalAttackerStats.get(i), totalDefendersStats.get(i)));
    }
  }

  private Double calculateSurviveChance(int totalAttackerStat, int totalDefenderStat) {
    if(totalDefenderStat >= totalAttackerStat) {
      return 1.0;
    }
    return (double) totalDefenderStat / (double) totalAttackerStat;
  }
}
