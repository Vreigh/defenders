package com.BO.defenders.repository;

import com.BO.defenders.model.Problem;

/**
 * Repository for the Problem. Most likely only one implementation will be used (ProblemRepositoryReference).
 */
public interface ProblemRepository {

  /**
   * Gets the current Problem definition.
   */
  Problem get();

  /**
   * Overrides the stored Problem
   */
  void store(Problem problem);
}
