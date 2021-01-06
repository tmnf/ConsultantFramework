package org.uma.jmetal.problem.consultant;

public class Variable {

	private String name, type;
	private double max, min;

	public Variable(String name, String type, double max, double min) {
		this.name = name;
		this.type = type;

		this.max = max;
		this.min = min;
	}
	
	public Variable() {
		
	}

	public String getName() {
		return name;
	}

	public String getType() {
		return type;
	}

	public double getMax() {
		return max;
	}

	public double getMin() {
		return min;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setMax(double max) {
		this.max = max;
	}

	public void setMin(double min) {
		this.min = min;
	}
	
	

}
