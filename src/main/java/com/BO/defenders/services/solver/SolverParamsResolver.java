package com.BO.defenders.services.solver;

import java.util.List;

public interface SolverParamsResolver<T> {

  T resolveParams(List<String> rawParams);
}
