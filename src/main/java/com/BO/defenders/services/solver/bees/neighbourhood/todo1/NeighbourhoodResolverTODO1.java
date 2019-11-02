package com.BO.defenders.services.solver.bees.neighbourhood.todo1;

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
@ForHoodType(hoodType = HoodType.TODO1)
public class NeighbourhoodResolverTODO1 implements NeighbourhoodResolver {

  @Override
  public Solution getNeighbouringSolution(Problem problem, Solution prevSolution) {
    return null;
  }
}
