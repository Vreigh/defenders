package com.BO.defenders.model;

import lombok.Getter;
import lombok.ToString;

@ToString
public class FieldMatrix {

  @Getter
  private final int sectorsNumber;
  @Getter
  private final int unitsNumber;
  private boolean[][] matrix;

  public FieldMatrix(int sectorsNumber, int unitsNumber) {
    this.sectorsNumber = sectorsNumber;
    this.unitsNumber = unitsNumber;
    matrix = new boolean[sectorsNumber][unitsNumber];
  }

  public boolean get(int sectorIndex, int unitIndex) {
    return matrix[sectorIndex][unitIndex];
  }

  public void set(int sectorIndex, int unitIndex, boolean assigned) {
    matrix[sectorIndex][unitIndex] = assigned;
  }
}
