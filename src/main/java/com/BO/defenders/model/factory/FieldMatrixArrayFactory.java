package com.BO.defenders.model.factory;

import java.util.List;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import com.BO.defenders.model.FieldMatrix;
import com.BO.defenders.model.FieldMatrixArray;
import com.BO.defenders.model.Unit;

@Component
@Primary
public class FieldMatrixArrayFactory extends FieldMatrixFactory {

  @Override
  public FieldMatrix newMatrix(int sectorNumber, List<Unit> units) {
    return new FieldMatrixArray(sectorNumber, units);
  }
}
