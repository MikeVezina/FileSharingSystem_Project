/**
 * Title: SYSC 3110 Project
 * Created By: Raymond Wu
 * Student Number: 100938326
 * Team: nullSquad
 */
package nullSquad.simulator;

import nullSquad.document.*;
import nullSquad.network.*;
import java.util.*;

public class Simulator
{
	private Network	network;
	private Random	randomNumber;

	private enum tags
	{
		MUSIC, BOOKS, GAMING, SCHOOL, SPORTS, PROGRAMMING
	}


	/**
	 * Creates a simulator with specified network
	 * 
	 * @param network Network of all the users/documents
	 * @author Raymond Wu
	 */
	public Simulator(Network network)
	{
		this.network = network;
		randomNumber = new Random();
	}

	/**
	 * Adds the specified number of consumers into the network
	 *
	 * 
	 * @param numberOfConsumers Number of consumers in the network
	 * @author Raymond Wu
	 */
	private void createConsumers(int numberOfConsumers)
	{
		for (int x = 0; x < numberOfConsumers; x++)
		{
			User user = new Consumer("Consumer" + x, tags.values()[randomNumber.nextInt(tags.values().length)].toString());
			user.registerUser(network);
		}
	}

	/**
	 * Adds the specified number of producers into the network
	 * 
	 * 
	 * @param numberOfProducers Number of producers in the network
	 * @author Raymond Wu
	 */
	private void createProducers(int numberOfProducers)
	{
		for (int x = 0; x < numberOfProducers; x++)
		{
			User user = new Producer("Producer" + x, tags.values()[randomNumber.nextInt(tags.values().length)].toString());
			user.registerUser(network);
		}
	}

	/**
	 * Performs one simulation cycle which calls
	 * a user to "act"
	 * 
	 * @author Raymond Wu
	 */
	public void simulationStep()
	{
		// Generate a random number so the simulation can get a random user
		User randomUser = network.getUsers().get(randomNumber.nextInt(network.getUsers().size()));

		// Ask the random user to "act"
		System.out.println("User: " + randomUser.getUserName() + " is acting!");
		randomUser.act(network);
	}

	/**
	 * Simple-console based implementation of the
	 * simulation
	 * 
	 * @author Raymond Wu
	 */
	public void simulationRun()
	{
		int numberOfTags;
		int numberOfProducers;
		int numberOfConsumers;
		String start;
		Scanner in = new Scanner(System.in);

		System.out.println("Welcome to a simulation of a file-sharing social network");
		
		  System.out.println("Enter the number of consumers:");
		  numberOfConsumers = in.nextInt();
		  System.out.println("Enter the number of producers:");
		  numberOfProducers = in.nextInt();
		 

		System.out.println("Would you like to run the simulation? (yes/no)");
		start = in.next();

		if (start.equals("yes"))
		{
			System.out.println("Simulation starting:");
			
			createProducers(numberOfProducers);
			createConsumers(numberOfConsumers);

			while (true)
			{
				// Break if user enters no
				if (start.equalsIgnoreCase("no") || start.equalsIgnoreCase("stop"))
					break;
				
				int numSteps = 1;
				
				switch (start.toLowerCase())
				{

				// Continue the simulation
					case "step":
						numSteps = 1;
						break;
						
					case "go":
						System.out.println("How many steps would you like to simulate?: ");
						numSteps = in.nextInt();
						break;

					// Print out all the stats
					case "stats":
					{
						System.out.println("Current users in the simulation:");
						for (User user : network.getUsers())
						{
							System.out.print(user.toString() + "\n");
						}

						System.out.println("Current documents in the simulation: " + network.getAllDocuments().size());
						for (Document document : network.getAllDocuments())
						{
							System.out.print(document.toString() + "\n\n");
						}
					}

					// Any other input results in asking the user again
					default:
						System.out.println("Please Enter a Command (GO, STEP, STOP, STATS)");
						start = in.next();

						continue;

				}
				for(int i=0; i<numSteps; i++)
					simulationStep();

				System.out.println("Please Enter a Command (GO, STEP, STOP, STATS):");
				start = in.next();

				System.out.println();

			}
		}
		System.out.println("Exiting...");
		System.out.println("Simulation Halted.");
	}

	public static void main(String[] args)
	{

		Network network = new Network();
		Simulator simulator = new Simulator(network);
		simulator.simulationRun();
	}

}
