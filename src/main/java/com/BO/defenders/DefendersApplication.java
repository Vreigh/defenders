package com.BO.defenders;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.BO.defenders.repository.ProblemRepositoryReference;
import com.BO.defenders.services.DefendersAppRunner;

import lombok.RequiredArgsConstructor;

@SpringBootApplication
@RequiredArgsConstructor
public class DefendersApplication implements CommandLineRunner {

  private final DefendersAppRunner defendersAppManager;


  public static void main(String[] args) {
    SpringApplication.run(DefendersApplication.class, args);
  }

  /**
   * Repository is not autowired to avoid problems with multiple references, hence should only be used at the top-layer
   * (DefendersAppManager).
   */
  public void run(String... args) throws Exception {
    defendersAppManager.start(new ProblemRepositoryReference());
  }
}
