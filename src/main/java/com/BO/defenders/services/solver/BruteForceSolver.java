package com.BO.defenders.services.solver;

import com.BO.defenders.model.FieldMatrixArray;
import com.BO.defenders.model.Problem;
import com.BO.defenders.model.Solution;
import com.BO.defenders.services.costcalculator.chance.CostCalculatorChanceForAll;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@ForSolveType(solveType = SolveType.FORCE)
public class BruteForceSolver implements ProblemSolver<Integer> {

    private Double bestChance = 100D;
    private Solution bestSolution;

    @Override
    public Solution solve(Problem problem, Integer params) {
        Solution solution = new Solution(new FieldMatrixArray(problem.getProblemConfig().getSectorsNumber(), problem.getDefenders()));
        solve(solution ,problem, 0);
        return bestSolution;
    }

    private void solve(Solution solution ,Problem problem, int defenderIndex){
        if(defenderIndex == problem.getDefenders().size()) {
            calculateChance(solution, problem);
            return;
        }
        for(int i = 0; i<problem.getProblemConfig().getSectorsNumber(); i++){
            solution.getDefendersMatrix().getMatrix()[i][defenderIndex] = true;
            solve(solution, problem, defenderIndex + 1);
            solution.getDefendersMatrix().getMatrix()[i][defenderIndex] = true;
        }
    }

    private void calculateChance(Solution solution, Problem problem) {
        new CostCalculatorChanceForAll().calculateCost(problem, solution);
        Double chance = solution.getCost();
        if(chance < bestChance){
            bestChance = chance;
            this.bestSolution = solution.clone();
        }
    }
}
