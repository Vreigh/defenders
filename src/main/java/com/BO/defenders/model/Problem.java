package com.BO.defenders.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
@Builder
public class Problem {

  private ProblemConfig problemConfig;
  private List<Unit> attackers;
  private List<Unit> defenders;
  private FieldMatrix attackersMatrix;

  public void present() {
    //TODO: zaprezentować problem lepiej
    System.out.println("Presenting the problem");
    System.out.println(toString());
  }
}
