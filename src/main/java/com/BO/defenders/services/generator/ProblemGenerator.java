package com.BO.defenders.services.generator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.BO.defenders.model.FieldMatrix;
import com.BO.defenders.model.Problem;
import com.BO.defenders.model.ProblemConfig;
import com.BO.defenders.model.Unit;
import com.BO.defenders.util.MatrixUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProblemGenerator {

  private final Random random = new Random();

  public Problem generateProblem(ProblemConfig config) {
    log.info("Generating a new problem for config {}", config);
    FieldMatrix attackersMatrix = new FieldMatrix(config.getSectorsNumber(), config.getAttackersNumber());
    MatrixUtils.randomFillUnitMatrix(attackersMatrix, random);
    List<Unit> attackers = generateUnits(config.getAttackersNumber(), config.getStatsNumber(), config.getStatsSum());
    List<Unit> defenders = generateUnits(config.getDefendersNumber(), config.getStatsNumber(), config.getStatsSum());
    return new Problem(config, attackers, defenders, attackersMatrix);
  }

  private List<Unit> generateUnits(int number, int statsNumber, int statsSum) {
    List<Unit> units = new ArrayList<>(number);
    for (int i = 0; i < number; i++) {
      units.add(generateUnit(statsNumber, statsSum));
    }
    return units;
  }

  private Unit generateUnit(int statsNumber, int statsSum) {
    List<Integer> stats = new ArrayList<>(statsNumber);
    //TODO: zaimplementować bardziej sensowne losowanie stat, żeby działało dla dużych statsSum
    for (int i = 0; i < statsNumber; i++) {
      stats.add(0);
    }
    for (int i = 0; i < statsSum; i++) {
      int randomIndex = random.nextInt(statsNumber);
      stats.set(randomIndex, stats.get(randomIndex) + 1);
    }
    return new Unit(stats);
  }

}
