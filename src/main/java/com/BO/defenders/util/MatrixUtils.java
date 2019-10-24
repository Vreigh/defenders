package com.BO.defenders.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import com.BO.defenders.model.FieldMatrix;
import com.BO.defenders.model.Unit;

import lombok.experimental.UtilityClass;

import static java.util.stream.Collectors.toList;

@UtilityClass
public class MatrixUtils {

  public static void randomFillUnitMatrix(FieldMatrix matrix, Random random) {
    for (int i = 0; i < matrix.getUnitsNumber(); i++) {
      int randomSectorIndex = random.nextInt(matrix.getSectorsNumber());
      matrix.set(randomSectorIndex, i, true);
    }
  }

  public static void randomButEnsureOneInAllSectorsFillUnitMatrix(FieldMatrix matrix, Random random) {
    List<Integer> availableUnitIndexes = IntStream.range(0, matrix.getUnitsNumber())
      .boxed().collect(toList());

    int sectorIndex = 0;
    while (!availableUnitIndexes.isEmpty()) {
      Integer unitIndex = availableUnitIndexes.get(random.nextInt(availableUnitIndexes.size())); // get random unit index
      matrix.set(sectorIndex, unitIndex, true); // assign the unit
      availableUnitIndexes.remove(unitIndex); // remove from available (removing the Integer object, not by index in availableUnitIndexes)

      sectorIndex++;
      if (sectorIndex == matrix.getSectorsNumber()) {
        sectorIndex = 0;
      }
    }
  }

  public static List<Unit> getSectorUnits(FieldMatrix matrix, int sector, List<Unit> units) {
    List<Unit> result = new ArrayList<>(matrix.getUnitsNumber());
    for (int unitIndex = 0; unitIndex < matrix.getUnitsNumber(); unitIndex++) {
      if (matrix.get(sector, unitIndex)) {
        result.add(units.get(unitIndex));
      }
    }
    return result;
  }
}
