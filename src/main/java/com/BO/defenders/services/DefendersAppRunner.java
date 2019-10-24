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
}
