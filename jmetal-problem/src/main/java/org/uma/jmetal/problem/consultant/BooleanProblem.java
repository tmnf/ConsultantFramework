package org.uma.jmetal.problem.consultant;

import org.uma.jmetal.problem.impl.AbstractBinaryProblem;
import org.uma.jmetal.solution.BinarySolution;

@SuppressWarnings("serial")
public class BooleanProblem extends AbstractBinaryProblem {

	private String integration;

	public BooleanProblem(String problem, int numberOfVariables, int numOfObjectives, String integration) {
		setNumberOfVariables(numberOfVariables);
		setNumberOfObjectives(numOfObjectives);
		setName(problem);

		this.integration = integration;
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
}
