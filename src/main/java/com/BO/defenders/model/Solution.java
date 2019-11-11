package com.BO.defenders.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class Solution implements Cloneable{

  private final FieldMatrix defendersMatrix;
  @Setter
  private Double cost;

  public void present() {
    //TODO: zaprezentować rozwiazanie lepiej, dobrze zaimplementowac funckję do wypisywania human-friendly FieldMatrix
    System.out.println("Present the solution");
    System.out.println("Final cost: " + cost);
    System.out.println(defendersMatrix.present());
  }

  @Override
  public Solution clone() {
      FieldMatrix matrix = new FieldMatrixArray(defendersMatrix.getSectorsNumber(), defendersMatrix.getUnits());
      for(int i = 0; i<defendersMatrix.getMatrixView().length; i++)
        for(int j = 0; j<defendersMatrix.getMatrixView()[i].length; j++)
          matrix.getMatrixView()[i][j] = defendersMatrix.getMatrixView()[i][j];
      return new Solution(matrix);
  }
}
