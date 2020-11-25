package org.uma.jmetal.problem.consultant;

import org.uma.jmetal.problem.Problem;

public class ProblemFactory {

	public static Problem<?> generateProblem(String[] problemDescr) {

		String problem, typeOfVariables, upperBound, lowerBound, integration;
		int numOfVariables, numOfObjectives;

		problem = problemDescr[0];
		numOfVariables = Integer.parseInt(problemDescr[1]);
		numOfObjectives = Integer.parseInt(problemDescr[2]);
		typeOfVariables = problemDescr[3];
		lowerBound = problemDescr[4];
		upperBound = problemDescr[5];
		integration = problemDescr[6];

		Problem<?> result = null;

		switch (typeOfVariables) {
		case "double":
			result = new DoubleProblem(problem, numOfVariables, numOfObjectives, upperBound, lowerBound, integration);
			break;

		case "integer":
			result = new IntegerProblem(problem, numOfVariables, numOfObjectives, upperBound, lowerBound, integration);
			break;

		case "boolean":
			result = new BooleanProblem(problem, numOfVariables, numOfObjectives, integration);
			break;

		default:
			throw new IllegalArgumentException("Invalid type of variables");
		}

		return result;
	}

}
