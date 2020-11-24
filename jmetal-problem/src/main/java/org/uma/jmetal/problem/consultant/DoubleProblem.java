package org.uma.jmetal.problem.consultant;

import org.uma.jmetal.problem.impl.AbstractDoubleProblem;
import org.uma.jmetal.solution.DoubleSolution;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class DoubleProblem extends AbstractDoubleProblem {

	private String integration;

	public DoubleProblem(String problem, int numberOfVariables, int numOfObjectives, String upperBound,
			String lowerBound, String integration) {
		setNumberOfVariables(numberOfVariables);
		setNumberOfObjectives(numOfObjectives);
		setName(problem);

		this.integration = integration;

		List<Double> lowerLimit = new ArrayList<>(getNumberOfVariables());
		List<Double> upperLimit = new ArrayList<>(getNumberOfVariables());

		for (int i = 0; i < getNumberOfVariables(); i++) {
			upperLimit.add(Double.parseDouble(upperBound));
			lowerLimit.add(Double.parseDouble(lowerBound));
		}

		setLowerLimit(lowerLimit);
		setUpperLimit(upperLimit);
	}

	public void evaluate(DoubleSolution solution) {
		if (integration.contains(".jar"))
			JarEvaluate.evaluateDoublesWithJar(solution, integration);
		else
			WebserviceEvaluate.evaluateWithService(solution);
	}
}
