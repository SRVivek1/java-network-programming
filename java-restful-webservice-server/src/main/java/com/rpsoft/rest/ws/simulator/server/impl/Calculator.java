/**
 * :: Restful controller ::
 * =========================
 * The calculator will compute the squareRoot and square of any number. 
 * It will return a simple JSON response with the name of the action, the input and the output.
 * 
 */
package com.rpsoft.rest.ws.simulator.server.impl;


import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.rpsoft.rest.ws.simulator.server.bean.Result;

/**
 * @author vivek.ksingh
 */
@Path("calculator")
public class Calculator {

	/**
	 * This method calculates square root of given number.
	 *  
	 * @param input
	 * @return {@link Result}
	 */
	@GET
	@Path("squareRoot")
	@Produces(MediaType.APPLICATION_JSON)
	public Result squareRoot(@QueryParam("input") double input) {
		Result result = new Result("Square Root");
		result.setInput(input);
		result.setOutput(Math.sqrt(result.getInput()));
		return result;
	}

	/**
	 * This method calculates square of given number.
	 *  
	 * @param input
	 * @return {@link Result}
	 */
	@GET
	@Path("square")
	@Produces(MediaType.APPLICATION_JSON)
	public Result square(@QueryParam("input") double input) {
		Result result = new Result("Square");
		result.setInput(input);
		result.setOutput(result.getInput() * result.getInput());
		return result;
	}
}