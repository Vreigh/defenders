package com.BO.defenders.services.costcalculator;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CostCalculationType {
  CHANCE_FOR_ALL("CFA");

  private final String commandCode;

  public static CostCalculationType ofCommandCode(String code) {
    for(CostCalculationType type : CostCalculationType.values()) {
      if(type.getCommandCode().equals(code)) {
        return type;
      }
    }
    throw new IllegalArgumentException("No cost calculation type corresponding to provided code " + code);
  }
}
