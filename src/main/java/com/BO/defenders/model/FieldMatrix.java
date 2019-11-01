package com.BO.defenders.model;

import java.util.ArrayList;
import java.util.List;

import com.BO.defenders.util.MatrixUtils;

import lombok.Getter;
import lombok.ToString;

@ToString
public class FieldMatrix {

  @Getter
  private final int sectorsNumber;
  @Getter
  private final int unitsNumber;
  private boolean[][] matrix;

  private List<List<Unit>> cachedSectors;

  public FieldMatrix(int sectorsNumber, int unitsNumber) {
    this.sectorsNumber = sectorsNumber;
    this.unitsNumber = unitsNumber;
    matrix = new boolean[sectorsNumber][unitsNumber];
    cachedSectors = null;
  }

  public boolean get(int sectorIndex, int unitIndex) {
    return matrix[sectorIndex][unitIndex];
  }

  public void set(int sectorIndex, int unitIndex, boolean assigned) {
    if (hasCache()) {
      throw new IllegalArgumentException("This matrix was supposed to have cache");
    }
    matrix[sectorIndex][unitIndex] = assigned;
  }

  // OPTIONAL - using units cache may quicken the cost calculation (bees will have n+m complexity, not n*m)
  public void cacheElements(List<Unit> units) {
    cachedSectors = new ArrayList<>();
    for (int i = 0; i < sectorsNumber; i++) {
      cachedSectors.add(MatrixUtils.getSectorUnitsWithoutCache(this, i, units));
    }
  }

  public boolean hasCache() {
    return cachedSectors != null;
  }

  public void disableCache() {
    cachedSectors = null;
  }

  public List<Unit> getCachedSector(int sectorNumber) {
    return cachedSectors.get(sectorNumber);
  }

  public void setWithCache(int sectorIndex, int unitIndex, boolean assigned, Unit unit) {
    if (!hasCache()) {
      throw new IllegalArgumentException("This matrix does not have cache");
    }
    matrix[sectorIndex][unitIndex] = assigned;
    if (assigned) {
      List<Unit> cachedSector = cachedSectors.get(sectorIndex);
      if (!cachedSector.contains(unit)) {
        cachedSector.add(unit);
      }
      cachedSectors.get(sectorIndex).add(unit);
    } else {
      cachedSectors.get(sectorIndex).remove(unit);
    }
  }
}
