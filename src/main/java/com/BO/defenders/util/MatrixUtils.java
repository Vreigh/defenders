package com.BO.defenders.util;

import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import com.BO.defenders.model.FieldMatrix;

import lombok.experimental.UtilityClass;

import static java.util.stream.Collectors.toList;

@UtilityClass
public class MatrixUtils {

  public static void randomFillUnitMatrix(FieldMatrix matrix, Random random) {
    for (int i = 0; i < matrix.getUnitsNumber(); i++) {
      int randomSectorIndex = random.nextInt(matrix.getSectorsNumber());
      matrix.assign(randomSectorIndex, i);
    }
  }

  public static void randomButEnsureOneInAllSectorsFillUnitMatrix(FieldMatrix matrix, Random random) {
    List<Integer> availableUnitIndexes = IntStream.range(0, matrix.getUnitsNumber())
      .boxed().collect(toList());

    int sectorIndex = -1;
    while (!availableUnitIndexes.isEmpty() && ++sectorIndex != matrix.getSectorsNumber()) {
      Integer unitIndex = availableUnitIndexes.get(random.nextInt(availableUnitIndexes.size())); // get random unit index
      matrix.assign(sectorIndex, unitIndex); // assign the unit
      availableUnitIndexes.remove(unitIndex); // remove from available (removing the Integer object, not by index in availableUnitIndexes)
    }

    while (!availableUnitIndexes.isEmpty()) {
      Integer unitIndex = availableUnitIndexes.get(random.nextInt(availableUnitIndexes.size()));
      int randomSectorIndex = random.nextInt(matrix.getSectorsNumber());
      matrix.assign(randomSectorIndex, unitIndex);
      availableUnitIndexes.remove(unitIndex);
    }
  }

}
