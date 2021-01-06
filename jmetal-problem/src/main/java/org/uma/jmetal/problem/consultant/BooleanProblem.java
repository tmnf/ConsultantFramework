package org.uma.jmetal.problem.consultant;

import org.uma.jmetal.problem.impl.AbstractBinaryProblem;
import org.uma.jmetal.solution.BinarySolution;

@SuppressWarnings("serial")
public class BooleanProblem extends AbstractBinaryProblem {

	private String integration;

	public BooleanProblem(InputProblem problem) {
		setNumberOfVariables(problem.getVariables().size());
		setNumberOfObjectives(problem.getObjectives());
		setName(problem.getName());

		this.integration = problem.getMethod();
	}

	@Override
	public void evaluate(BinarySolution solution) {
		if (integration.contains(".jar"))
			JarEvaluate.evaluateDoublesWithJar(solution, integration);
		else
			WebserviceEvaluate.evaluateWithService(solution);
	}

	@Override
	protected int getBitsPerVariable(int index) {
		return 0;
	}

	public String evaluate(String solution) {
		if (integration.contains(".jar"))
			return JarEvaluate.evaluateWithJar(solution, getNumberOfObjectives(), getNumberOfVariables(), integration);
		else
			return WebserviceEvaluate.evaluateWithService(solution);
	}
}
