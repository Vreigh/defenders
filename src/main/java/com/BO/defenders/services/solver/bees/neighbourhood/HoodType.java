package com.BO.defenders.services.solver.bees.neighbourhood;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum HoodType {
  RANDOM_TWO_UNITS_BEST_CHOICE("R2B"), TODO1("TODO1"), BEST_TO_WORST("BTW");

  private final String commandCode;

  public static HoodType ofCommandCode(String code) {
    for (HoodType type : HoodType.values()) {
      if (type.getCommandCode().equals(code)) {
        return type;
      }
    }
    throw new IllegalArgumentException("No neighbourhood type corresponding to provided code " + code);
  }
}
