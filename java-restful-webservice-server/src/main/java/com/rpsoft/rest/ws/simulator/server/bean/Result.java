package com.rpsoft.rest.ws.simulator.server.bean;

/**
 * Result Class.
 * 
 * @author vivek.ksingh
 */

public class Result {

	double input;
	double output;
	String action;

	public Result() {
	}

	public Result(String action) {
		this.action = action;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public double getInput() {
		return input;
	}

	public void setInput(double input) {
		this.input = input;
	}

	public double getOutput() {
		return output;
	}

	public void setOutput(double output) {
		this.output = output;
	}

}
