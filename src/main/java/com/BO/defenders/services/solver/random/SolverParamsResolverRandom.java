package com.BO.defenders.services.solver.random;

import java.util.List;

import org.springframework.stereotype.Service;

import com.BO.defenders.services.solver.ForSolveType;
import com.BO.defenders.services.solver.SolveType;
import com.BO.defenders.services.solver.SolverParamsResolver;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;

@Slf4j
@Service
@RequiredArgsConstructor
@ForSolveType(solveType = SolveType.RANDOM)
public class SolverParamsResolverRandom implements SolverParamsResolver<Integer> {

  @Override
  public Integer resolveParams(List<String> rawParams) {
    if (isNotEmpty(rawParams)) {
      return Integer.valueOf(rawParams.get(0));
    }
    return 1;
  }
}
