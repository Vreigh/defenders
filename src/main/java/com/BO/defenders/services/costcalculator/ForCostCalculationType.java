package com.BO.defenders.services.costcalculator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.BO.defenders.services.solver.SolveType;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ForCostCalculationType {

  CostCalculationType costCalculationType();
}
