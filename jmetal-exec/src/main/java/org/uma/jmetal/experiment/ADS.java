package org.uma.jmetal.experiment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.uma.jmetal.problem.consultant.BooleanProblem;
import org.uma.jmetal.problem.consultant.DoubleProblem;
import org.uma.jmetal.problem.consultant.InputProblem;
import org.uma.jmetal.problem.consultant.IntegerProblem;
import org.uma.jmetal.problem.consultant.Variable;
import org.uma.jmetal.solution.DoubleSolution;
import org.uma.jmetal.util.experiment.util.ExperimentProblem;

public class ADS {

	public static void main(String[] args) throws IOException {

		List<InputProblem> problems = jsonToProblemList(args[0]);

		List<IntegerProblem> integerProblems = new ArrayList<>();
		List<ExperimentProblem<DoubleSolution>> doubleProblems = new ArrayList<>();
		List<BooleanProblem> booleanProblems = new ArrayList<>();

		for (InputProblem p : problems) {
			switch (p.getMethod()) {
			case "Integer":
				integerProblems.add(new IntegerProblem(p));
				break;
			case "Double":
				doubleProblems.add(new ExperimentProblem<>((new DoubleProblem(p))));
				break;
			case "Boolean":
				booleanProblems.add(new BooleanProblem(p));
				break;
			default:
				break;
			}
		}

		if (!integerProblems.isEmpty())
			ProblemUtils.runIntegerAlgorithm(integerProblems);

		if (!doubleProblems.isEmpty())
			ProblemUtils.runDoubleAlgorithm(doubleProblems);

		if (!booleanProblems.isEmpty())
			ProblemUtils.runBinaryAlgorithm(booleanProblems);

	}

	private static ArrayList<InputProblem> jsonToProblemList(String jsonString) {
		JSONObject jsonData = new JSONObject(jsonString);

		ArrayList<InputProblem> problems = new ArrayList<>();

		JSONArray jsonProblems = jsonData.getJSONArray("problems");

		for (int i = 0; i < jsonProblems.length(); i++) {
			JSONObject aux = jsonProblems.getJSONObject(i);

			InputProblem prob = new InputProblem();
			prob.setName(aux.getString("problem"));
			prob.setType(aux.getString("type"));
			prob.setMethod(aux.getString("evaluate"));
			prob.setObjectives(aux.getInt("objectives"));

			JSONArray variablesAux = aux.getJSONArray("variables");
			for (int j = 0; j < variablesAux.length(); j++) {
				JSONObject var = variablesAux.getJSONObject(j);

				Variable variable = new Variable();
				variable.setName(var.getString("name"));
				variable.setType(aux.getString("Type"));
				variable.setMax(var.getDouble("max"));
				variable.setMin(var.getDouble("min"));

				prob.addVariable(variable);
			}

			problems.add(prob);
		}

		return problems;
	}

}
