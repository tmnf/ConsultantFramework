package org.uma.jmetal.problem.consultant;

import org.uma.jmetal.problem.impl.AbstractIntegerProblem;
import org.uma.jmetal.solution.IntegerSolution;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class IntegerProblem extends AbstractIntegerProblem {

	private String integration;

	public IntegerProblem(String problem, int numberOfVariables, int numOfObjectives, String upperBound,
			String lowerBound, String integration) {
		setNumberOfVariables(numberOfVariables);
		setNumberOfObjectives(numOfObjectives);
		setName(problem);

		this.integration = integration;

		List<Integer> lowerLimit = new ArrayList<>(getNumberOfVariables());
		List<Integer> upperLimit = new ArrayList<>(getNumberOfVariables());

		for (int i = 0; i < getNumberOfVariables(); i++) {
			upperLimit.add(Integer.parseInt(upperBound));
			lowerLimit.add(Integer.parseInt(lowerBound));
		}

		setLowerLimit(lowerLimit);
		setUpperLimit(upperLimit);
	}

	@Override
	public void evaluate(IntegerSolution solution) {
		if (integration.contains(".jar"))
			JarEvaluate.evaluateDoublesWithJar(solution, integration);
		else
			WebserviceEvaluate.evaluateWithService(solution);
	}
}
