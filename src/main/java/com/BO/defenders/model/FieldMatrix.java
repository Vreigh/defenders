package com.BO.defenders.model;

import java.util.List;

/**
 * Field with units assignment representation
 */
public interface FieldMatrix {

  int getSectorsNumber();

  int getUnitsNumber();

  List<Unit> getUnits();

  Unit getUnit(int unitIndex);

  int getUnitSector(int unitIndex);

  boolean isAssigned(int sectorIndex, int unitIndex);

  void assign(int sectorIndex, int unitIndex, boolean assign);

  void assign(int sectorIndex, int unitIndex);

  void unassign(int sectorIndex, int unitIndex);

  List<Unit> getSectorUnits(int sectorIndex);

  boolean[][] getMatrixView();

  String present();

  FieldMatrix clone();
}
