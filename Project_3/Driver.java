/**
*	Assignment: Project 3: Simulating traffic flow at an intersection.
*	Due Date: November 22th, 2013
*	Instructor: Dr. DePasquale
*	Class Title: Accelerated Computer Science I & II
*	Submitted by: Elizabeth Davis
*	Obj: To simulate the traffic flow of a four - way traffic intersection given specific flow requirements.
*/

/**
*	The Driver class contains the main method and instantiates another class, called Simulator, which then calls 
*	an appropriate method in that class, called Simulate. 
*
*	@author Elizabeth Davis
*/

public class Driver
{
	/**
	*	The main method is where the Simulator object called intersection is instantiated. It then calls the Simulator method Simulate,
	*	which then starts the simulation.
	*/

	public static void main(String[] args)
	{
		Simulator intersection = new Simulator();
		intersection.Simulate();
	}
}