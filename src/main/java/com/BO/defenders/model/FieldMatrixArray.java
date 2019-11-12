package com.BO.defenders.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class FieldMatrixArray implements FieldMatrix {

  @Getter
  private final int sectorsNumber;
  @Getter
  private final int unitsNumber;
  @Getter
  private final List<Unit> units;

  private boolean[][] matrix;

  public FieldMatrixArray(int sectorsNumber, List<Unit> units) {
    this.sectorsNumber = sectorsNumber;
    this.unitsNumber = units.size();
    this.units = units;
    matrix = new boolean[sectorsNumber][unitsNumber];
  }

  @Override
  public Unit getUnit(int unitIndex) {
    return units.get(unitIndex);
  }

  @Override
  public int getUnitSector(int unitIndex) {
    for (int i = 0; i < sectorsNumber; i++) {
      if (matrix[i][unitIndex]) {
        return i;
      }
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean isAssigned(int sectorIndex, int unitIndex) {
    return matrix[sectorIndex][unitIndex];
  }

  @Override
  public void assign(int sectorIndex, int unitIndex, boolean assigned) {
    matrix[sectorIndex][unitIndex] = assigned;
  }

  @Override
  public void assign(int sectorIndex, int unitIndex) {
    matrix[sectorIndex][unitIndex] = true;
  }

  @Override
  public void unassign(int sectorIndex, int unitIndex) {
    matrix[sectorIndex][unitIndex] = false;
  }

  @Override
  public List<Unit> getSectorUnits(int sectorIndex) {
    List<Unit> result = new ArrayList<>(unitsNumber);
    for (int unitIndex = 0; unitIndex < unitsNumber; unitIndex++) {
      if (matrix[sectorIndex][unitIndex]) {
        result.add(units.get(unitIndex));
      }
    }
    return result;
  }

  @Override
  public boolean[][] getMatrix() {
    return matrix;
  }



  @Override
  public String present()
  {
//    return this.toString();
    StringBuilder str = new StringBuilder();
    for(boolean[] row: this.matrix)
      str.append(Arrays.toString(row)).append("\n");

    return  "\n> Sectors number: " + sectorsNumber +
            "\n> Units number: " + unitsNumber +
            "\n> Units: " + units.toString() +
            "\n> Matrix: \nget" + str
            ;
  }

  @Override
  public FieldMatrix clone() {
    FieldMatrixArray clone = new FieldMatrixArray(sectorsNumber, units.size(), units);
    clone.matrix = matrix.clone();
    return clone;
  }
}
