package com.BO.defenders.services.reader.parser;

import java.util.function.Function;

import org.springframework.stereotype.Service;

import com.BO.defenders.services.costcalculator.CostCalculationType;
import com.BO.defenders.services.reader.command.*;
import com.BO.defenders.services.reader.error.CommandParseException;
import com.BO.defenders.services.solver.SolveType;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static java.util.Arrays.asList;
import static java.util.Objects.isNull;
import static java.util.Optional.ofNullable;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommandParser {

  public Command parse(String commandLine) throws CommandParseException {
    String[] args = ofNullable(commandLine).map(input -> input.split("\\s+")).orElse(null);
    if (isNull(args) || args.length == 0) {
      throw new CommandParseException("Couldn't parse the command");
    }
    CommandType commandType = resolveEnumOrThrowParseException(CommandType::valueOf, args[0]);
    try {
      switch (commandType) {
        case NEW:
          return buildNewCommand(args);
        case GET:
          return new GetCommand();
        case SOLVE:
          return buildSolveCommand(args);
        case END:
          return new EndCommand();
      }
    } catch (RuntimeException e) {
      throw new CommandParseException("Unexpected error during command parsing, origin: " + e.getMessage());
    }
    return null;
  }

  private NewCommand buildNewCommand(String[] args) throws CommandParseException {
    NewCommand result = new NewCommand();
    if (args.length > 1) {
      result.setCostType(resolveEnumOrThrowParseException(CostCalculationType::ofCommandCode, args[1]));
    }
    if (args.length > 2) {
      result.setSectorsNumber(Integer.valueOf(args[2]));
    }
    if (args.length > 3) {
      result.setStatsNumber(Integer.valueOf(args[3]));
    }
    if (args.length > 4) {
      result.setStatsSum(Integer.valueOf(args[4]));
    }
    if (args.length > 5) {
      result.setAttackersNumber(Integer.valueOf(args[5]));
    }
    if (args.length > 6) {
      result.setDefendersNumber(Integer.valueOf(args[6]));
    }
    //TODO (opcjonalne): dodać jakieś proste walidacje żeby np nie dopuszczać ujemnych liczb
    return result;
  }

  private SolveCommand buildSolveCommand(String[] args) throws CommandParseException {
    if (args.length < 2) {
      throw new CommandParseException("Couldn't parse the command");
    }
    SolveType solveType = resolveEnumOrThrowParseException(SolveType::valueOf, args[1]);
    return new SolveCommand(solveType, asList(args).subList(2, args.length));
  }

  private <E> E resolveEnumOrThrowParseException(Function<String, E> enumCreator, String name) throws CommandParseException {
    try {
      return enumCreator.apply(name.toUpperCase());
    } catch (IllegalArgumentException e) {
      throw new CommandParseException("Unrecognised name " + name);
    }
  }
}
