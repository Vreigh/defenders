package com.BO.defenders.services;

import org.springframework.stereotype.Service;

import com.BO.defenders.error.OperationTechnicalException;
import com.BO.defenders.model.Problem;
import com.BO.defenders.model.ProblemConfig;
import com.BO.defenders.model.Solution;
import com.BO.defenders.repository.ProblemRepository;
import com.BO.defenders.services.generator.ProblemGenerator;
import com.BO.defenders.services.reader.CommandReader;
import com.BO.defenders.services.reader.command.*;
import com.BO.defenders.services.solver.ProblemSolverManager;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class DefendersAppRunner {

  private final CommandReader commandReader;
  private final ProblemGenerator problemGenerator;
  private final ProblemSolverManager problemSolverManager;

  private ProblemRepository problemRepository;

  public void start(ProblemRepository problemRepository) {
    this.problemRepository = problemRepository;
    initExampleProblem();
    System.out.println("\nWelcome to the defenders application");
    System.out.println("Available commands: NEW, GET, SOLVE, END, HELP");


    Command command;
    do {
      command = commandReader.nextCommand();
      log.info("Recognised command {}", command);
      try {
        switch (command.getType()) {
          case NEW:
            handleNew((NewCommand) command);
            break;
          case GET:
            handleGet((GetCommand) command);
            break;
          case SOLVE:
            handleSolve((SolveCommand) command);
            break;
          case HELP:
            handleHelp((HelpCommand) command);
        }
      } catch(OperationTechnicalException e) {
        log.error("Technical exception during operation: {}, message: {}", command.getType(), e.getMessage());
      }
    } while (command.getType() != CommandType.END);
  }



  private void initExampleProblem() {
    handleNew(new NewCommand());
  }

  private void handleNew(NewCommand command) {
    ProblemConfig config = ProblemConfig.builder()
      .costType(command.getCostType())
      .sectorsNumber(command.getSectorsNumber())
      .statsNumber(command.getStatsNumber())
      .statsSum(command.getStatsSum())
      .attackersNumber(command.getAttackersNumber())
      .defendersNumber(command.getDefendersNumber())
      .build();

    Problem newProblem = problemGenerator.generateProblem(config);
    problemRepository.store(newProblem);
  }

  private void handleGet(GetCommand command) {
    problemRepository.get().present();
  }

  private void handleSolve(SolveCommand command) {
    Solution solution = problemSolverManager.solve(problemRepository.get(), command);
    solution.present();
  }

  private void handleHelp(HelpCommand command)
  {
    System.out.println("Available commands: NEW, GET, SOLVE, END, HELP");

    System.out.println("NEW   - creates new problem");
    System.out.println("> Usage: NEW [sectors] [attackers] [defenders] [cost type] [stats number] [stats sum]");
    System.out.println("> parameters are optional, if a parameter is not given default is set");

    System.out.println("GET   - prints problem description");
    System.out.println("SOLVE - solves previously declared problem");
    System.out.println("> Usage: SOLVE [solver type] [iterations] [neighbourhood type]");
    System.out.println("> Solver types: RANDOM - as name suggests,\n" +
                       "                BEES   - bees algorithm,\n" +
                       "                FORCE  - best solution calculated by brute force");
    System.out.println("> Neighbourhood types: R2B - Random 2 units best choice\n" +
                       "                       BTW - Best to worst");

    System.out.println("END   - closes program");
    System.out.println("HELP  - prints this help message");

  }
}
