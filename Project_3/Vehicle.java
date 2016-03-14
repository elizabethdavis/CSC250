/**
*	The purpose of the Vehicle class is to create a vehicle that will enter the intersection simulation.
*	The Vehicle class contains enumerated types, random streets, directions, and lanes that each vehicle
*	is assigned to, accessors to get the direction, street, lane, and vehicle number, set the departure time
*	for a vehicle, and accessors to get information about the direction, as well as where the direction that 
*	the vehicle travels after either turning right or going straight through the intersection.
*
*	@author Elizabeth Davis
*/

public class Vehicle
{
	/**
	*	The Vehicle class has three different enumerated types, Direction, Street, and Lane.
	*	The enumerated type Direction is declared here, it can be N(north), E(east), S(south), or W(west).
	*	The enumerated type Street is declared here, it can be either Main(which runs east/west) or Church(which runs north/south).
	*	The enumerated type Lane is declared here, it can be either Left or Right. The left lane goes straight and the right lane turns
	*	right in this simulation.
	*
	*	vehicleNumber represents the vehicle number. It increases by one for each vehicle that enters the intersection, and stops after 120 vehicles are counted.
	*	arrivalTime represents the time that the vehicle enters the intersection. For the first population, the arrivalTime is zero.
	*	departureTime represents the time that the vehicle leaves the intersection. 
	*
	*	The street variable represents which street the vehicle is on when it enters the intersection simulation.
	*	The direction variable represents which direction the vehicle is heading when it enters the intersection simulation.
	*	The lane variable represents which lane on the street that the vehicle is travelling on when it enters the intersection simulation.
	*/

	public enum Direction{N, E, S, W};
	public enum Street{Main, Church};
	public enum Lane{Left, Right};

	private int arrivalTime, departureTime, vehicleNumber;
	private Direction direction;
	private Street street;
	private Lane lane;

	/**
	*	Constructor: constructs a vehicle object. Stores the instance data vehicleNumber, arrivalTime, departureTime, 
	*	a random direction that the vehicle is heading, a random street that a vehicle is assigned to, and a random 
	*	lane(right or straight) for each vehicle object that is created.
	*
	*	@param number The integer value that represents the number of the vehicle.
	*	@param arrive The integer value that represents when a vehicle enters the intersection.
	*	@param depart The integer value that represents when a vehicle leaves the intersection.
	*/

	public Vehicle(int num, int arrive, int depart)
	{
		vehicleNumber = num;
		arrivalTime = arrive;
		departureTime = depart;
		direction = randomDir();
		street = randomStr();
		lane = randomLane();
	}

	/**
	*	A method that assigns a random direction to a vehicle based on a number. Each number represents a direction
	*	and is randomly selected through the use of the Math.random method. Had to cast the value to be an integer value.
	*	For direction, 1 represents north, 2 represents east, 3 represents south, and 4 represents west.
	*
	*	@return direction 	The random direction that the vehicle is headed.
	*/

	public Direction randomDir()
	{
		int direct = (int)(Math.random() * (4 - 0) + 0);
			if(direct == 1)
				direction = Direction.S;
			else if(direct == 2)
				direction = Direction.W;
			else if(direct == 3)
				direction = Direction.N;
			else
				direction = Direction.E;

			return direction;
	}

	/**
	*	A method that assigns a random street to a vehicle based on a number. Each number represents either Main street
	*	or Church street and is based on the result of the direction the vehicle is headed. If the vehicle is going north 
	*	or south, the vehicle is on Church Street, otherwise, we know the vehicle will be on Main Street.
	*
	*	@return street 		The street that the vehicle is on, based on direction headed.
	*/

	public Street randomStr()
	{
		if (direction == Direction.S || direction == Direction.N)
			street = Street.Church;
		else
			street = Street.Main;
		
		return street;
	}

	/**
	*	A method that assigns a random lane to a vehicle based on a number. Each number represents either the left or right
	*	lane and is randomly selected through the use of the Math.random method. Had to cast the value to be an integer value.
	*	For lane assignment, 1 represents the right lane, so 2 would be the left lane.
	*
	*	@return lane 	The random lane that the vehicle is assigned to.
	*/

	public Lane randomLane()
	{
		int whichLane = (int)(Math.random() * (2 - 0) + 0);
		if (whichLane == 1)
			lane = Lane.Right;
		else
			lane = Lane.Left;

		return lane;
	}

	/**
	*	Accessor: gets the direction that the vehicle is headed.
	*
	*	@return direction 	The direction that the vehicle is headed.
	*/

	public Direction getDirection() 
	{
		return direction;
	}

	/**
	*	Accessor: gets the street that the vehicle is on.
	*
	*	@return street 	The street that the vehicle is on.
	*/

	public Street getStreet() 
	{
		return street;
	}

	/**
	*	Accessor: gets the lane that the vehicle is assigned to.
	*
	*	@return lane 	The lane that the vehicle is assigned to.
	*/

	public Lane getLane() 
	{
		return lane;
	}

	/**
	*	Modifier: sets the departure time that the vehicle is leaves the intersection.
	*/

	public void setDepartureTime(int time)
	{
		departureTime = time;
	}

	/**
	*	Accessor: gets the number of the vehicle that in the intersection.
	*
	*	@return vehicleNumber 	The number of the vehicle.
	*/

	public int getVehicleNumber() 
	{
		return vehicleNumber;
	}

	/**
	*	Accessor: gets the direction that the vehicle is heading.
	*
	*	@return "southbound" 	The vehicle is heading southbound.
	*	@return "northbound" 	The vehicle is heading northbound.
	*	@return "westbound" 	The vehicle is heading westbound.
	*	@return "eastbound" 	The vehicle is heading eastbound.
	*/

	public String getDirectionBound() 
	{
		if (direction == Direction.S)
			return "southbound";
		else if (direction == Direction.N)
			return "northbound";
		else if (direction == Direction.W)
			return "westbound";
		else
			return"eastbound";
	}

	/**
	*	Accessor: gets the direction that the vehicle is going after going through the intersection.
	*
	*	@return "continued straight" 					The vehicle was in the left lane, continued straight.
	*	@return "turned right and headed westbound" 	The vehicle was in the right lane, heading southbound, turned right, now heading westbound.
	*	@return "turned right and headed eastbound" 	The vehicle was in the right lane, heading northbound, turned right, now heading eastbound.
	*	@return "turned right and headed northbound" 	The vehicle was in the right lane, heading eastbound, turned right, now heading northbound.
	* 	@return "turned right and headed southbound"	The vehicle was in the right lane heading westbound, turned right, now heading southbound.
	*/

	private String getNewDirection() 
	{
		if (lane == Lane.Left)
			return "continued straight";
		else if (lane ==Lane.Right && direction == Direction.S)
			return "turned right and headed westbound";
		else if (lane ==Lane.Right && direction == Direction.N)
			return "turned right and headed eastbound";
		else if (lane ==Lane.Right && direction == Direction.W)
			return "turned right and headed northbound";
		else
			return "turned right and headed southbound";
	}

	/**
	*	Returns a string representation of the information for each vehicle. 
	*
	*	@return vehicleInformation Gives the arrival time, the vehicle number, the direction it was headed when it came into the intersection, the direction 
	*	it is going after moving through the intersection, and the total wait time from when the vehicle arrived to when it left the intersection.
	*/


	public String toString() 
	{
		String totalWait = String.format("%02d", (departureTime - arrivalTime));
		String vehicleInformation = "[Time " + String.format("%02d", departureTime) + "] Vehicle #" + vehicleNumber + " (" + getDirectionBound() + ") " + getNewDirection() + ". Total wait time " + totalWait + " seconds."; 
		return vehicleInformation;
	}

}