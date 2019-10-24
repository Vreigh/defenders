package com.BO.defenders.services.solver;

import java.util.EnumMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.BO.defenders.error.OperationTechnicalException;
import com.BO.defenders.error.SetupTechnicalException;
import com.BO.defenders.model.Problem;
import com.BO.defenders.model.Solution;
import com.BO.defenders.services.reader.command.SolveCommand;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static java.util.Objects.isNull;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProblemSolverManager {

  private final ApplicationContext ctx;

  private Map<SolveType, SolverParamsResolver> paramsResolverMap;
  private Map<SolveType, ProblemSolver> solverMap;

  public Solution solve(Problem problem, SolveCommand solveCommand) {
    SolverParamsResolver solverParamsResolver = paramsResolverMap.get(solveCommand.getSolveType());
    ProblemSolver problemSolver = solverMap.get(solveCommand.getSolveType());
    if (isNull(solverParamsResolver) || isNull(problemSolver)) {
      throw new OperationTechnicalException("No implementation for this solve type " + solveCommand.getType());
    }
    return problemSolver.solve(problem, solverParamsResolver.resolveParams(solveCommand.getParams()));
  }

  @PostConstruct
  public void init() {
    paramsResolverMap = new EnumMap<>(SolveType.class);
    solverMap = new EnumMap<>(SolveType.class);

    Map<String, Object> beans = ctx.getBeansWithAnnotation(ForSolveType.class);
    beans.forEach((beanName, bean) -> {
      ForSolveType forSolveType = ctx.findAnnotationOnBean(beanName, ForSolveType.class);
      assert forSolveType != null;
      if (bean instanceof SolverParamsResolver) {
        paramsResolverMap.put(forSolveType.solveType(), (SolverParamsResolver) bean);
      } else if (bean instanceof ProblemSolver) {
        solverMap.put(forSolveType.solveType(), (ProblemSolver) bean);
      } else {
        throw new SetupTechnicalException();
      }
    });
  }
}
