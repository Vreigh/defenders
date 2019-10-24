package com.BO.defenders.util;

import java.util.Arrays;
import java.util.List;

import com.BO.defenders.model.Unit;

import lombok.experimental.UtilityClass;

@UtilityClass
public class UnitsUtils {

  /**
   * @param units nonempty list of nonnull units containing the same number of stats
   * @return totalled stats
   */
  public static List<Integer> sumUnits(List<Unit> units) {
    int statsNumber = units.get(0).getStatsNumber();
    Integer totalStats[] = new Integer[statsNumber];
    Arrays.fill(totalStats, 0);
    units.forEach(unit -> {
      for (int i = 0; i < statsNumber; i++) {
        totalStats[i] += unit.getStat(i);
      }
    });
    return Arrays.asList(totalStats);
  }
}
