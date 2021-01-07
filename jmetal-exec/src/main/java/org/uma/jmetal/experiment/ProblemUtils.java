package org.uma.jmetal.experiment;

import java.util.ArrayList;
import java.util.List;

import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.algorithm.multiobjective.nsgaii.NSGAIIBuilder;
import org.uma.jmetal.operator.impl.crossover.SBXCrossover;
import org.uma.jmetal.operator.impl.mutation.PolynomialMutation;
import org.uma.jmetal.problem.consultant.BooleanProblem;
import org.uma.jmetal.problem.consultant.IntegerProblem;
import org.uma.jmetal.solution.DoubleSolution;
import org.uma.jmetal.util.experiment.Experiment;
import org.uma.jmetal.util.experiment.ExperimentBuilder;
import org.uma.jmetal.util.experiment.component.ExecuteAlgorithms;
import org.uma.jmetal.util.experiment.util.ExperimentAlgorithm;
import org.uma.jmetal.util.experiment.util.ExperimentProblem;

public class ProblemUtils {

	private static final int INDEPENDENT_RUNS = 1;
	private static final String experimentBaseDirectory = "ADS-Solutions-And-Results";

	/*
	 * =========================== INTEGER ALGORITHM HANDLING
	 * ===========================
	 */

	public static void runIntegerAlgorithm(List<IntegerProblem> problems) {
		ArrayList<Thread> threads = new ArrayList<>();

		for (IntegerProblem prob : problems) {
			AlgorithmSolver as = new AlgorithmSolver(prob, experimentBaseDirectory);
			threads.add(as);
			as.start();
		}

		for (Thread t : threads)
			try {
				t.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	}

	/*
	 * =========================== DOUBLE ALGORITHM HANDLING
	 * ===========================
	 */
	public static void runDoubleAlgorithm(List<ExperimentProblem<DoubleSolution>> doubleProblems) {
		System.out.println("Entrou");
		List<ExperimentAlgorithm<DoubleSolution, List<DoubleSolution>>> algorithmList = configureDoubleAlgorithmList(
				doubleProblems);

		Experiment<DoubleSolution, List<DoubleSolution>> experiment = new ExperimentBuilder<DoubleSolution, List<DoubleSolution>>(
				"ADS").setAlgorithmList(algorithmList).setProblemList(doubleProblems)
						.setExperimentBaseDirectory(experimentBaseDirectory).setOutputParetoFrontFileName("FUN")
						.setOutputParetoSetFileName("VAR").setReferenceFrontDirectory("/pareto_fronts")
						.setIndependentRuns(INDEPENDENT_RUNS).setNumberOfCores(4).build();

		new ExecuteAlgorithms<>(experiment).run();
	}

	private static List<ExperimentAlgorithm<DoubleSolution, List<DoubleSolution>>> configureDoubleAlgorithmList(
			List<ExperimentProblem<DoubleSolution>> problemList) {
		List<ExperimentAlgorithm<DoubleSolution, List<DoubleSolution>>> algorithms = new ArrayList<>();

		System.out.println("Correu");
		for (int run = 0; run < INDEPENDENT_RUNS; run++) {
			for (int i = 0; i < problemList.size(); i++) {
				Algorithm<List<DoubleSolution>> algorithm = new NSGAIIBuilder<>(problemList.get(i).getProblem(),
						new SBXCrossover(1.0, 5),
						new PolynomialMutation(1.0 / problemList.get(i).getProblem().getNumberOfVariables(), 10.0))
								.setMaxEvaluations(1000).setPopulationSize(100).build();
				algorithms.add(new ExperimentAlgorithm<>(algorithm, "ADS", problemList.get(i), run));
			}
		}
		return algorithms;
	}

	/*
	 * =========================== BINARY ALGORITHM HANDLING
	 * ===========================
	 */

	public static void runBinaryAlgorithm(List<BooleanProblem> problems) {
		ArrayList<Thread> threads = new ArrayList<>();

		for (BooleanProblem prob : problems) {
			AlgorithmSolver as = new AlgorithmSolver(prob, experimentBaseDirectory);
			threads.add(as);
			as.start();
		}

		for (Thread t : threads)
			try {
				t.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	}

}
