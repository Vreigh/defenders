package com.BO.defenders.services.solver.bees.neighbourhood;

import java.util.EnumMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.BO.defenders.error.OperationTechnicalException;
import com.BO.defenders.error.SetupTechnicalException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static java.util.Optional.ofNullable;

@Slf4j
@Service
@RequiredArgsConstructor
public class NeighbourhoodResolverManager {

  private final ApplicationContext ctx;

  private Map<HoodType, NeighbourhoodResolver> resolverMap;

  public NeighbourhoodResolver getResolver(HoodType hoodType) {
    return ofNullable(resolverMap.get(hoodType))
      .orElseThrow(() -> new OperationTechnicalException("No implementation for this neighbourhood type " + hoodType));
  }

  @PostConstruct
  public void init() {
    resolverMap = new EnumMap<>(HoodType.class);

    Map<String, Object> beans = ctx.getBeansWithAnnotation(ForHoodType.class);
    beans.forEach((beanName, bean) -> {
      ForHoodType forCostCalculationType = ctx.findAnnotationOnBean(beanName, ForHoodType.class);
      assert forCostCalculationType != null;
      if (bean instanceof NeighbourhoodResolver) {
        resolverMap.put(forCostCalculationType.hoodType(), (NeighbourhoodResolver) bean);
      } else {
        throw new SetupTechnicalException();
      }
    });
  }
}
