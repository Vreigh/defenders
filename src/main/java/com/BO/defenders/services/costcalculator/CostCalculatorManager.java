package com.BO.defenders.services.costcalculator;

import java.util.EnumMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.BO.defenders.error.OperationTechnicalException;
import com.BO.defenders.error.SetupTechnicalException;
import com.BO.defenders.model.Problem;
import com.BO.defenders.model.Solution;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static java.util.Objects.isNull;
import static java.util.Optional.ofNullable;

@Slf4j
@Service
@RequiredArgsConstructor
public class CostCalculatorManager {

  private final ApplicationContext ctx;

  private Map<CostCalculationType, CostCalculator> calculatorMap;

  public void calculateCost(Problem problem, Solution solution) {
    CostCalculator calculatorForProblemCostType = calculatorMap.get(problem.getProblemConfig().getCostType());
    if (isNull(calculatorForProblemCostType)) {
      throw new OperationTechnicalException("No implementation for this cost type " + problem.getProblemConfig().getCostType());
    }
    calculatorForProblemCostType.calculateCost(problem, solution);
  }

  public CostCalculator getCalculator(CostCalculationType costType) {
    return ofNullable(calculatorMap.get(costType))
      .orElseThrow(() -> new OperationTechnicalException("No implementation for this cost type " + costType));
  }

  @PostConstruct
  public void init() {
    calculatorMap = new EnumMap<>(CostCalculationType.class);

    Map<String, Object> beans = ctx.getBeansWithAnnotation(ForCostCalculationType.class);
    beans.forEach((beanName, bean) -> {
      ForCostCalculationType forCostCalculationType = ctx.findAnnotationOnBean(beanName, ForCostCalculationType.class);
      assert forCostCalculationType != null;
      if (bean instanceof CostCalculator) {
        calculatorMap.put(forCostCalculationType.costCalculationType(), (CostCalculator) bean);
      } else {
        throw new SetupTechnicalException();
      }
    });
  }
}
