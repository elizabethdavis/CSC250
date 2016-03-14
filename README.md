# CSC250

<h1>Vehicle Traffic Simulator</h1>

Objective: Simulate the traffic flow of a four-way traffic intersection given specific flow requirements

The input to this program is the random generation of vehicles and assignment to a street/directional queue codified in the program.

Each Vehicle object should store the following instance data:
  - vehicleNumber(int)
  - street (enum)
  - direction (enum)
  - arrivalTime (int)
  - departureTime (int)
  
Output for this project will consist of a listing of alternating state changes (light changes) and each vehicle’s data in the order the vehicle proceeded through the intersection. 
Output should be written to a file named “output.txt”.
