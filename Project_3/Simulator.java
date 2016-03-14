/**
*	The purpose of the Simulator class is to simulate an intersection. It uses eight Linked Queues to line up and move 120
*	vehicles through an intersection. There are four directions (north, south, east, west), two streets (Main, Church), and
*	two lanes for each street in each direction. A vehicle is randomly assigned to one of eight queues, and the direction 
*	that they continue in is based on their lane, direction, and street assignment.
*
*	@author Elizabeth Davis
*/

import java.lang.Math;
import java.io.*;
import jsjf.*;
import jsjf.exceptions.*;
import java.util.*;
import java.net.*;

/**
*	The Simulator class has eight different queues. Every street has two lanes in each direction, one for turning(the right lane),
*	and the other for moving straight(the left lane).
*
*	The clockTime is an integer value that keeps a timer for the entire program to keep track of both the arrival time and departure time of each vehicle.
*	The vehicleNumber is an integer that starts at one and increases for each vehicle that is added to the intersection. A maximum of 120 vehicles may be added.
*
*	The PrintWriter, FileWriter, and BufferedWriter are used to create and write a file called "output.txt". (explained above Simulate method)
*/

public class Simulator
{
	private LinkedQueue<Vehicle> LaneOneNR = new LinkedQueue<Vehicle>();
	private LinkedQueue<Vehicle> LaneTwoNL = new LinkedQueue<Vehicle>();
	private LinkedQueue<Vehicle> LaneThreeER = new LinkedQueue<Vehicle>();
	private LinkedQueue<Vehicle> LaneFourEL = new LinkedQueue<Vehicle>();
	private LinkedQueue<Vehicle> LaneFiveSR = new LinkedQueue<Vehicle>();
	private LinkedQueue<Vehicle> LaneSixSL = new LinkedQueue<Vehicle>();
	private LinkedQueue<Vehicle> LaneSevenWR = new LinkedQueue<Vehicle>();
	private LinkedQueue<Vehicle> LaneEightWL = new LinkedQueue<Vehicle>();

	private int clockTime = 0, vehiclesThrough = 0, vehicleNumber = 1;
	PrintWriter out;
	FileWriter file;
	BufferedWriter buffer;

	/**
	*	Constructor: constructs a Simulator object that can be instantiated in the Driver. This constructor is necessary because the 
	*	Driver instantiates a Simulator object, which then calls the other methods in Simulator class, specifically, it will call simulate.
	*/

	public Simulator() {}

	/**
	*	Method: The Simulate method has a try-catch statement. The puropose of the try block is to try the lines of code inside of the brackets. The catch
	*	block handles the exceptions, and handles the exceptions indicated, which in this case is the IOException. The try block is where the program prints out the
	*	information of the vehicles to an output file called "output.txt". 
	*
	*	The PrintWriter prints formatted representations of objects to a text-output stream. While the lanes are not empty, the program continues to write the output to the
	*	file "output.txt". After all of the queues are empty, the PrintWriter closes using the .close() method, which closes the stream and releases any system resources associated with it.
	*
	*	The FileWriter is a convenience class for writing character files. The FileWriter is meant for writing streams of characters. FileOutputStream
	*	was not used because it processes streams of raw bytes, and FileWriter processes characters.
	*	
	*	The BufferedWriter is needed because it will buffer the PrintWriter's output to the file. It is wrapped around FileWriter to reduce the risk of a FileWriter. 
	*	Without buffering each invocation of a print() method would cause characters to be converted into bytes that would them be written immediately to the file, which can be very inefficient.
	*/

	public void Simulate()
	{
		try {
		
			file = new FileWriter("output.txt");
			buffer = new BufferedWriter(file);
			out = new PrintWriter(buffer);
	
			out.print("---Start of simulation, time set to 0.--- \n");
			IncomingVehiclePopulation((int)(Math.random() * (13 - 7) + 7));
			
			while(!LaneOneNR.isEmpty() || !LaneTwoNL.isEmpty() || !LaneThreeER.isEmpty() || !LaneFourEL.isEmpty() || !LaneFiveSR.isEmpty() || !LaneSixSL.isEmpty() || !LaneSevenWR.isEmpty() || !LaneEightWL.isEmpty()) 
			{
				out.print("---Light changed. Now processing north/south-bound traffic--- \n");
				moveNorthSouth();

				IncomingVehiclePopulation((int)(Math.random() * (16 - 8) + 8));
				out.println();
				out.print("---Light changed. Now processing east/west-bound traffic---\n");
				
				moveEastWest();
				IncomingVehiclePopulation((int)(Math.random() * (16 - 3) + 3));
				out.println();
			}	
			out.close();
		}
		catch(IOException e){
			System.err.println("Caught IOException: Error printing file.");
		}
	}

	/**
	*	Method: IncomingVehiclePopulation calls the enumerated types in the Vehicle class (Direction, Street and Lane), and places the vehicles in the correct queue from
	*	this information. 
	*
	*	@param randomNum The integer value of the number of vehicles that will be added to the intersection for each round (see above method for specific ranges). 
	*/

	private void IncomingVehiclePopulation(int randomNum) {
		int count = 0;
		while (count < randomNum && vehicleNumber <=120) {
			Vehicle vehicle = new Vehicle(vehicleNumber, clockTime, clockTime);
			count++;
			vehicleNumber++;
			if (vehicle.getStreet() == Vehicle.Street.Main && vehicle.getDirection() == Vehicle.Direction.E && vehicle.getLane() == Vehicle.Lane.Left)
				LaneThreeER.enqueue(vehicle);
			else if (vehicle.getStreet() == Vehicle.Street.Main && vehicle.getDirection() == Vehicle.Direction.E && vehicle.getLane() == Vehicle.Lane.Right)
				LaneFourEL.enqueue(vehicle);
			else if (vehicle.getStreet() == Vehicle.Street.Main && vehicle.getDirection() == Vehicle.Direction.W && vehicle.getLane() == Vehicle.Lane.Left)
				LaneSevenWR.enqueue(vehicle);
			else if (vehicle.getStreet() == Vehicle.Street.Main && vehicle.getDirection() == Vehicle.Direction.W && vehicle.getLane() == Vehicle.Lane.Right)
				LaneEightWL.enqueue(vehicle);
			else if (vehicle.getStreet() == Vehicle.Street.Church && vehicle.getDirection() == Vehicle.Direction.N && vehicle.getLane() == Vehicle.Lane.Left)
				LaneOneNR.enqueue(vehicle);
			else if (vehicle.getStreet() == Vehicle.Street.Church && vehicle.getDirection() == Vehicle.Direction.N && vehicle.getLane() == Vehicle.Lane.Right)
				LaneTwoNL.enqueue(vehicle);
			else if (vehicle.getStreet() == Vehicle.Street.Church && vehicle.getDirection() == Vehicle.Direction.S && vehicle.getLane() == Vehicle.Lane.Left)
				LaneFiveSR.enqueue(vehicle);
			else
				LaneSixSL.enqueue(vehicle);
		}	
	}

	/**
	*	Method: Moves the vehicles in the north/south direction for a total of six seconds, moving up to eight vehicles(two from each lane). 
	*	
	*	The integer value vehiclesMoved is the number of vehicles that can be moved for each lane for each light in the north and/or south direction. The try catch statements 
	*	try to see if the queue is empty. If the queue is not empty, one of the vehicles from each lane is dequeued for three seconds. Then, if there is another vehicle in the queue,
	*	each queue in the north/south direction is dequeued once more before the light turns again, allowing the vehicles to then move in the east/west direction.
	*/

	private void moveNorthSouth() {
		int vehiclesMoved = 0;
		while (vehiclesMoved < 2) {
			clockTime = clockTime + 3;
			try {
				if (!LaneOneNR.isEmpty()) {
					Vehicle vehicle = new Vehicle(0, 0, 0);
					vehicle = LaneOneNR.dequeue();
					vehicle.setDepartureTime(clockTime);
					out.println(vehicle);					
				}
			}
			catch(EmptyCollectionException e) {}

			try {	
				if (!LaneTwoNL.isEmpty()) {
					Vehicle vehicle = new Vehicle(0, 0, 0);
					vehicle = LaneTwoNL.dequeue();
					vehicle.setDepartureTime(clockTime);
					out.println(vehicle);					
				}
			}
			catch(EmptyCollectionException e) {}

			try {
				if (!LaneFiveSR.isEmpty()) {
					Vehicle vehicle = new Vehicle(0, 0, 0);
					vehicle = LaneFiveSR.dequeue();
					vehicle.setDepartureTime(clockTime);
					out.println(vehicle);					
				}
			}
			catch(EmptyCollectionException e) {}

			try {
				if (!LaneSixSL.isEmpty()) {
					Vehicle vehicle = new Vehicle(0, 0, 0);
					vehicle = LaneSixSL.dequeue();
					vehicle.setDepartureTime(clockTime);
					out.println(vehicle);					
				}
			}
			catch(EmptyCollectionException e) {}
		vehiclesMoved++;
		}
	}

	/**
	*	Method: Moves the vehicles in the east/west direction for a total of nine seconds, moving up to twelve vehicles(three from each lane). 
	*	
	*	The integer value vehiclesMoved is the number of vehicles that can be moved for each lane for each light in the east and/or west direction. The try catch statements 
	*	try to see if the queue is empty. If the queue is not empty, one of the vehicles from each lane is dequeued for three seconds. The vehicles move in the east/west 
	*	direction for a total of nine seconds. If there are vehicles in the queues, up to a total of tweleve vehicles may be moved for each light change.
	*/

	private void moveEastWest() {
		int vehiclesMoved = 0;
		while (vehiclesMoved < 3) {
			clockTime = clockTime + 3;
			try {
				if (!LaneThreeER.isEmpty()) {
					Vehicle vehicle = new Vehicle(0, 0, 0);
					vehicle = LaneThreeER.dequeue();
					vehicle.setDepartureTime(clockTime);
					out.println(vehicle);					
				}
			}
			catch(EmptyCollectionException e) {}

			try {	
				if (!LaneFourEL.isEmpty()) {
					Vehicle vehicle = new Vehicle(0, 0, 0);
					vehicle = LaneFourEL.dequeue();
					vehicle.setDepartureTime(clockTime);
					out.println(vehicle);					
				}
			}
			catch(EmptyCollectionException e) {}

			try {	
				if (!LaneSevenWR.isEmpty()) {
					Vehicle vehicle = new Vehicle(0,0,0);
					vehicle = LaneSevenWR.dequeue();
					vehicle.setDepartureTime(clockTime);
					out.println(vehicle);					
				}
			}
			catch(EmptyCollectionException e) {}

			try {	
				if (!LaneEightWL.isEmpty()) {
					Vehicle vehicle = new Vehicle(0,0,0);
					vehicle = LaneEightWL.dequeue();
					vehicle.setDepartureTime(clockTime);
					out.println(vehicle);					
				}
			}
			catch(EmptyCollectionException e) {}

		vehiclesMoved++;
		}	
	}
}