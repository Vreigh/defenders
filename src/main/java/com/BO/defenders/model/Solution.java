package com.BO.defenders.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import static com.BO.defenders.util.Constants.detailedSolveLogs;


@Getter
@ToString
@RequiredArgsConstructor
public class Solution implements Cloneable{

  private final FieldMatrix defendersMatrix;
  @Setter
  private Double cost;
  public static final String ANSI_RESET = "\u001B[0m";
  public static final String ANSI_RED = "\u001B[31m";



  public void present() {
    //TODO: zaprezentować rozwiazanie lepiej, dobrze zaimplementowac funckję do wypisywania human-friendly FieldMatrix
    System.out.println("Present the solution");
    System.out.println(ANSI_RED + "Final cost: " + cost + ANSI_RESET);
    if(detailedSolveLogs)
      System.out.println(defendersMatrix.present());
  }

  @Override
  public Solution clone() {
      return new Solution(defendersMatrix.clone());
  }
}
