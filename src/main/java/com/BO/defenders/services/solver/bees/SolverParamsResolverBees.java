package com.BO.defenders.services.solver.bees;

import java.util.List;

import org.springframework.stereotype.Service;

import com.BO.defenders.error.OperationTechnicalException;
import com.BO.defenders.services.solver.ForSolveType;
import com.BO.defenders.services.solver.SolveType;
import com.BO.defenders.services.solver.SolverParamsResolver;
import com.BO.defenders.services.solver.bees.neighbourhood.HoodType;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;

@Slf4j
@Service
@RequiredArgsConstructor
@ForSolveType(solveType = SolveType.BEES)
public class SolverParamsResolverBees implements SolverParamsResolver<BeesParams> {

  @Override
  public BeesParams resolveParams(List<String> rawParams) {
    BeesParams beesParams = new BeesParams();
    try {
      if (isNotEmpty(rawParams)) {
        if (rawParams.size() > 0) {
          beesParams.setIterations(Integer.valueOf(rawParams.get(0)));
        }
        if (rawParams.size() > 1) {
          beesParams.setNeighbourhoodType(HoodType.ofCommandCode(rawParams.get(1).toUpperCase()));
        }
        if (rawParams.size() > 2) {
          beesParams.setPopulation(Integer.valueOf(rawParams.get(2)));
        }
        if (rawParams.size() > 3) {
          beesParams.setSelected(Integer.valueOf(rawParams.get(3)));
        }
        if (rawParams.size() > 4) {
          beesParams.setElite(Integer.valueOf(rawParams.get(4)));
        }
        if (rawParams.size() > 5) {
          beesParams.setRecruitedAroundNormal(Integer.valueOf(rawParams.get(5)));
        }
        if (rawParams.size() > 6) {
          beesParams.setRecruitedAroundElite(Integer.valueOf(rawParams.get(6)));
        }
      }
    } catch (IllegalArgumentException e) {
      throw new OperationTechnicalException("Provided params could not be resolved");
    }
    return beesParams;
  }

}
