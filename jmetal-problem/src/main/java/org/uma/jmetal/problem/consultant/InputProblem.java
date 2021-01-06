package org.uma.jmetal.problem.consultant;

import java.util.ArrayList;

public class InputProblem {

	private String name, type, method;

	private int objectives;

	private ArrayList<Variable> variables = new ArrayList<>();

	public InputProblem(String name, String type, String method, int objectives, ArrayList<Variable> variables) {
		this.name = name;
		this.type = type;
		this.method = method;
		this.variables = variables;
	}

	public InputProblem() {

	}

	public String getName() {
		return name;
	}

	public String getType() {
		return type;
	}

	public String getMethod() {
		return method;
	}

	public ArrayList<Variable> getVariables() {
		return variables;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public void setVariables(ArrayList<Variable> variables) {
		this.variables = variables;
	}

	public void addVariable(Variable var) {
		this.variables.add(var);
	}

	public int getObjectives() {
		return objectives;
	}

	public void setObjectives(int objectives) {
		this.objectives = objectives;
	}
}
