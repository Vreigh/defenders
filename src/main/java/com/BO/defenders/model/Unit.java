package com.BO.defenders.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class Unit {

  private final List<Integer> stats;

  public Integer getStat(int i) {
    return stats.get(i);
  }

  public Integer getStatsNumber() {
    return stats.size();
  }
}
