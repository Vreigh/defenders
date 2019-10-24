package com.BO.defenders.repository;

import com.BO.defenders.model.Problem;

public class ProblemRepositoryReference implements ProblemRepository {

  private Problem problem;

  @Override
  public Problem get() {
    return problem;
  }

  @Override
  public void store(Problem problem) {
    this.problem = problem;
  }
}
