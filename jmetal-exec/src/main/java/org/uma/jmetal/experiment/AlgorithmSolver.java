package org.uma.jmetal.experiment;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Random;

import org.uma.jmetal.problem.consultant.BooleanProblem;
import org.uma.jmetal.problem.consultant.IntegerProblem;

public class AlgorithmSolver extends Thread {

	private static final int TESTS = 30;

	private Random rnd = new Random();

	private String experimentBaseDirectory;

	private IntegerProblem integerProblem;
	private BooleanProblem booleanProblem;

	public AlgorithmSolver(IntegerProblem integerProblem, String experimentBaseDirectory) {
		this.integerProblem = integerProblem;
		this.experimentBaseDirectory = experimentBaseDirectory;
	}

	public AlgorithmSolver(BooleanProblem booleanProblem, String experimentBaseDirectory) {
		this.booleanProblem = booleanProblem;
		this.experimentBaseDirectory = experimentBaseDirectory;
	}

	@Override
	public void run() {
		generateSolutions();
	}

	private void generateSolutions() {
		String path = experimentBaseDirectory + "/";

		if (integerProblem != null)
			path += integerProblem.getName();

		if (booleanProblem != null)
			path += booleanProblem.getName();

		File solutions = new File(path + "_solutions.tsv");
		File qualities = new File(path + "_qualities.tsv");

		ArrayList<String> solutionNumbers = new ArrayList<>();
		ArrayList<String> qualityValues = new ArrayList<>();

		if (integerProblem != null) {
			for (int j = 0; j < TESTS; j++) {
				String auxSolution = "";

				for (int i = 0; i < integerProblem.getNumberOfVariables(); i++)
					auxSolution += getSolution(i, false);

				solutionNumbers.add(auxSolution);
				qualityValues.add(integerProblem.evaluate(auxSolution));
			}
		} else {
			for (int j = 0; j < TESTS; j++) {
				String auxSolution = "";

				for (int i = 0; i < booleanProblem.getNumberOfVariables(); i++)
					auxSolution += getSolution(i, true) + " ";

				solutionNumbers.add(auxSolution);
				qualityValues.add(booleanProblem.evaluate(auxSolution));
			}
		}

		writeToFile(solutions, qualities, solutionNumbers, qualityValues);
	}

	private void writeToFile(File solutions, File qualities, ArrayList<String> solutionNumbers,
			ArrayList<String> qualityValues) {
		try {
			solutions.createNewFile();
			qualities.createNewFile();

			FileWriter solutionsWriter = new FileWriter(solutions);
			FileWriter qualitiesWriter = new FileWriter(qualities);

			String finalSolution = "";
			for (String x : solutionNumbers)
				finalSolution += x + "\n";

			String finalQualities = "";
			for (String x : qualityValues)
				finalQualities += x + "\n";

			solutionsWriter.write(finalSolution);
			qualitiesWriter.write(finalQualities);

			solutionsWriter.close();
			qualitiesWriter.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Integer getSolution(int index, boolean isBoolean) {
		if (isBoolean)
			return rnd.nextInt(2);

		return integerProblem.getLowerBound(index) + rnd.nextInt(integerProblem.getUpperBound(index) + 1);
	}

}
