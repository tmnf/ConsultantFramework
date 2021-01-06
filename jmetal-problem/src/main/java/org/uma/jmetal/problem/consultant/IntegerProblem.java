package org.uma.jmetal.problem.consultant;

import org.uma.jmetal.problem.impl.AbstractIntegerProblem;
import org.uma.jmetal.solution.IntegerSolution;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class IntegerProblem extends AbstractIntegerProblem {

	private String integration;

	public IntegerProblem(InputProblem problem) {
		setNumberOfVariables(problem.getVariables().size());
		setNumberOfObjectives(problem.getObjectives());
		setName(problem.getName());

		this.integration = problem.getMethod();

		List<Integer> upperLimit = new ArrayList<>(getNumberOfVariables());
		List<Integer> lowerLimit = new ArrayList<>(getNumberOfVariables());

		for (int i = 0; i < getNumberOfVariables(); i++) {
			Variable var = problem.getVariables().get(i);

			upperLimit.add((int) var.getMax());
			lowerLimit.add((int) var.getMin());
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

	public String evaluate(String solution) {
		if (integration.contains(".jar"))
			return JarEvaluate.evaluateWithJar(solution, getNumberOfObjectives(), getNumberOfVariables(), integration);
		else
			return WebserviceEvaluate.evaluateWithService(solution);
	}
}
