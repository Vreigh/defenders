package com.BO.defenders.services.solver.bees.neighbourhood.r2b;

import org.springframework.stereotype.Service;

import com.BO.defenders.model.Problem;
import com.BO.defenders.model.Solution;
import com.BO.defenders.services.solver.bees.neighbourhood.ForHoodType;
import com.BO.defenders.services.solver.bees.neighbourhood.HoodType;
import com.BO.defenders.services.solver.bees.neighbourhood.NeighbourhoodResolver;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@ForHoodType(hoodType = HoodType.RANDOM_TWO_UNITS_BEST_CHOICE)
public class NeighbourhoodResolverR2B implements NeighbourhoodResolver {

  @Override
  public Solution getNeighbouringSolution(Problem problem, Solution prevSolution) {
    return null;
  }

}
