package com.BO.defenders.services.solver.bees.neighbourhood.r2b;

import java.util.List;

import com.BO.defenders.model.Unit;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RandomUnit {

  private Unit unit;
  private int unitIndex;
  private int unitSector;
  private List<Unit> unitSectorDefenders;
  private List<Unit> unitSectorAttackers;
}
