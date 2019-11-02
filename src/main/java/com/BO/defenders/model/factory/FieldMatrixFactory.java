package com.BO.defenders.model.factory;

import java.util.List;

import com.BO.defenders.model.FieldMatrix;
import com.BO.defenders.model.Unit;

public abstract class FieldMatrixFactory {

  public abstract FieldMatrix newMatrix(int sectorNumber, List<Unit> units);
}
