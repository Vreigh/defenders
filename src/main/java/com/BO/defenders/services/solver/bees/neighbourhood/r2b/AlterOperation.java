package com.BO.defenders.services.solver.bees.neighbourhood.r2b;

import java.util.function.Consumer;

import com.BO.defenders.model.FieldMatrix;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
public class AlterOperation {

  private Consumer<FieldMatrix> operation;
  @Setter
  private Double cost;
}
