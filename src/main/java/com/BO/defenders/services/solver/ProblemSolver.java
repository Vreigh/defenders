package com.BO.defenders.services.solver;

import com.BO.defenders.model.Problem;
import com.BO.defenders.model.Solution;

public interface ProblemSolver<T> {

  Solution solve(Problem problem, T params);
}
