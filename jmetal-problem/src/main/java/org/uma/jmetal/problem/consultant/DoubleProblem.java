package org.uma.jmetal.problem.consultant;

import org.uma.jmetal.problem.impl.AbstractDoubleProblem;
import org.uma.jmetal.solution.DoubleSolution;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class DoubleProblem extends AbstractDoubleProblem {

	private String integration;

	public DoubleProblem(InputProblem problem) {
		setNumberOfVariables(problem.getVariables().size());
		setNumberOfObjectives(problem.getObjectives());
		setName(problem.getName());

		this.integration = problem.getMethod();

		List<Double> upperLimit = new ArrayList<>(getNumberOfVariables());
		List<Double> lowerLimit = new ArrayList<>(getNumberOfVariables());

		for (int i = 0; i < getNumberOfVariables(); i++) {
			Variable var = problem.getVariables().get(i);
			
			upperLimit.add(var.getMax());
			lowerLimit.add(var.getMin());
		}

		setLowerLimit(lowerLimit);
		setUpperLimit(upperLimit);
	}

	public void evaluate(DoubleSolution solution) {
		if (integration.contains("jar"))
			JarEvaluate.evaluateDoublesWithJar(solution, integration);
		else
			WebserviceEvaluate.evaluateWithService(solution);
	}
}
