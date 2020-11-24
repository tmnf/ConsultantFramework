package org.uma.jmetal.problem.consultant;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.uma.jmetal.solution.BinarySolution;
import org.uma.jmetal.solution.DoubleSolution;
import org.uma.jmetal.solution.IntegerSolution;
import org.uma.jmetal.solution.Solution;

public class JarEvaluate {

	// Gets the variables, calls the evaluate jar and sets the final quality of the
	// solution
	public static void evaluateDoublesWithJar(Solution<?> solution, String jarName) {
		String x = "";

		for (int i = 0; i < solution.getNumberOfVariables(); i++)
			x += solution.getVariableValue(i) + ";";

		String quality = readOutputFromJar(jarName, solution.getNumberOfObjectives(), solution.getNumberOfVariables(),
				x.substring(0, x.length() - 1));

		if (quality != null)
			parseValuesAndSetObjectives(solution, quality.split(";"));
	}

	// Parses the string received from the jar to the correct type and sets the
	// final objectives
	private static void parseValuesAndSetObjectives(Solution<?> solution, String[] quality) {
		if (solution instanceof DoubleSolution) {
			double[] fx = new double[solution.getNumberOfObjectives()];

			for (int i = 0; i < fx.length; i++)
				fx[i] = Double.parseDouble(quality[i]);

			for (int i = 0; i < solution.getNumberOfObjectives(); i++)
				solution.setObjective(i, fx[i]);

		} else if (solution instanceof IntegerSolution) {
			int[] fx = new int[solution.getNumberOfObjectives()];

			for (int i = 0; i < fx.length; i++)
				fx[i] = Integer.parseInt(quality[i]);

			for (int i = 0; i < solution.getNumberOfObjectives(); i++)
				solution.setObjective(i, fx[i]);

		} else if (solution instanceof BinarySolution) {
			throw new IllegalArgumentException("Boolean solution not implemented yet");
		} else {
			throw new IllegalArgumentException("Invalid solution!");
		}
	}

	// Calls the jar with the correct parameters and gets the output
	private static String readOutputFromJar(String jarName, int numOfObjectives, int numOfVariables, String variables) {
		String quality = "";
		try {
			String[] cmd = { "java", "-jar", "../jars/" + jarName, String.valueOf(numOfObjectives),
					String.valueOf(numOfVariables), variables };

			Process p = Runtime.getRuntime().exec(cmd);
			p.waitFor();

			BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
			BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));

			String aux = stdInput.readLine();

			if (aux != null)
				quality = aux;

			aux = stdError.readLine();

			if (aux != null)
				System.out.println(aux);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return quality;
	}

}
