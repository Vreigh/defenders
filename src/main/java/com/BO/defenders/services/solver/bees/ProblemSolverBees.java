package com.BO.defenders.services.solver.bees;

import org.springframework.stereotype.Service;

import com.BO.defenders.model.Problem;
import com.BO.defenders.model.Solution;
import com.BO.defenders.services.solver.ForSolveType;
import com.BO.defenders.services.solver.ProblemSolver;
import com.BO.defenders.services.solver.SolveType;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@ForSolveType(solveType = SolveType.BEES)
public class ProblemSolverBees implements ProblemSolver<BeesParams> {

  @Override
  public Solution solve(Problem problem, BeesParams params) {
    //TODO: implement
    return null;
  }
}
