package com.BO.defenders.services.solver;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;

@Slf4j
@Service
@RequiredArgsConstructor
@ForSolveType(solveType = SolveType.FORCE)
public class BruteForceResolver implements SolverParamsResolver<Integer> {
    @Override
    public Integer resolveParams(List<String> rawParams) {
        if (isNotEmpty(rawParams)) {
            return Integer.valueOf(rawParams.get(0));
        }
        return 1;
    }
}
