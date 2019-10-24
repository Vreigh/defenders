package com.BO.defenders.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class Solution {

  private final FieldMatrix defendersMatrix;
  @Setter
  private Double cost;

  public void present() {
    //TODO: zaprezentować rozwiazanie lepiej, dobrze zaimplementowac funckję do wypisywania human-friendly FieldMatrix
    System.out.println("Present the solution");
    System.out.println(toString());
  }
}
