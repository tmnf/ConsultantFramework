package org.uma.jmetal.experiment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.algorithm.multiobjective.nsgaii.NSGAIIBuilder;
import org.uma.jmetal.operator.impl.crossover.SBXCrossover;
import org.uma.jmetal.operator.impl.mutation.PolynomialMutation;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.problem.consultant.DoubleProblem;
import org.uma.jmetal.problem.consultant.ProblemFactory;
import org.uma.jmetal.solution.DoubleSolution;
import org.uma.jmetal.util.experiment.Experiment;
import org.uma.jmetal.util.experiment.ExperimentBuilder;
import org.uma.jmetal.util.experiment.component.ExecuteAlgorithms;
import org.uma.jmetal.util.experiment.util.ExperimentAlgorithm;
import org.uma.jmetal.util.experiment.util.ExperimentProblem;

public class ADS {
	private static final int INDEPENDENT_RUNS = 1;
	private static final String experimentBaseDirectory = "ADS-Solutions-And-Results";

	public static void main(String[] args) throws IOException {

		List<ExperimentProblem<DoubleSolution>> problemList = new ArrayList<>();

		Problem<?> problem = ProblemFactory.generateProblem(args);

		if (problem instanceof DoubleProblem)
			problemList.add(new ExperimentProblem<>((DoubleProblem) problem));
		else if (problem instanceof DoubleProblem)
			problemList.add(new ExperimentProblem<>((DoubleProblem) problem));
		else if (problem instanceof DoubleProblem)
			problemList.add(new ExperimentProblem<>((DoubleProblem) problem));
		else
			throw new IllegalArgumentException("Invalid problem...");

		List<ExperimentAlgorithm<DoubleSolution, List<DoubleSolution>>> algorithmList = configureAlgorithmList(
				problemList);

		Experiment<DoubleSolution, List<DoubleSolution>> experiment = new ExperimentBuilder<DoubleSolution, List<DoubleSolution>>(
				"ADS").setAlgorithmList(algorithmList).setProblemList(problemList)
						.setExperimentBaseDirectory(experimentBaseDirectory).setOutputParetoFrontFileName("FUN")
						.setOutputParetoSetFileName("VAR").setReferenceFrontDirectory("/pareto_fronts")
						.setIndependentRuns(INDEPENDENT_RUNS).setNumberOfCores(4).build();

		new ExecuteAlgorithms<>(experiment).run();
	}

	static List<ExperimentAlgorithm<DoubleSolution, List<DoubleSolution>>> configureAlgorithmList(
			List<ExperimentProblem<DoubleSolution>> problemList) {
		List<ExperimentAlgorithm<DoubleSolution, List<DoubleSolution>>> algorithms = new ArrayList<>();

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
}
