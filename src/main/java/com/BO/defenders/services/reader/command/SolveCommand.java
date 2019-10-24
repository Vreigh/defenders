package com.BO.defenders.services.reader.command;

import java.util.List;

import com.BO.defenders.services.solver.SolveType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SolveCommand extends Command {

  private SolveType solveType;
  private List<String> params;

  @Override
  public CommandType getType() {
    return CommandType.SOLVE;
  }
}
