/* Chapter No. All
   File Name:          PredatorPrey.java
   Programmer:         Dustin Himes
   Date Last Modified: 5/14/14
   Problem Statement: Create a predator/prey simulation that uses a GUI, and displays
   graphics for the user to observe
   
   Overall Plan:
   1. define the classes for the animals
   	-predators and prey should inherit from the same animal class
   	-methods for movement, eating, and reproduction will be unique to prey/preds
   2. define a terrain class
   	-the terrain class should hold places for an animal to occupy
   	-it should also have a set rate of growth for foliage for prey to eat
   	-it will have icons for each state it could be in, occupied, foliage, or lackthereof
   	-when this is built into a two dimensional array, a map can be drawn
   3. create the GUI
   	-the main game window will hold the map, as well as a button to advance time
   	-the start window will allow the user to define how many of each animal there will be within the map
   		-these numbers will be passed to the game window, which will randomly place the animals within the world
   		-the map will be generated randomly, with foliage placed randomly as well
   	-the turn logic will be controlled in the actionListener for the button
   		-predators move first, prey go second, predators move again
   4.the simulation will continue to run whenever the button is pressed.
   
   Classes needed and Purpose (Input, Processing, Output)
	PredatorPrey - input, processing, output
	Animal - processing
		Prey - processing
		Predator - processing
	Terrain - processing
	StartWindow - input, processing
	Tester - processing
*/


import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.Random;

public class PredatorPrey extends JFrame
{
	Terrain[][] environment;
	JPanel panel;
	JPanel environPanel;
	JButton passTime;
	
	public PredatorPrey(int ra, int de, int sq, int rc, int wo, int sr)
	{
		super("Predator and Prey Simulation");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		buildPanel();
		populateEnvironment(ra,de,sq,rc,wo,sr);
		pack();
		
		setVisible(true);
	}
	public void buildPanel()
	{
		panel = new JPanel();
		panel.setLayout(new BorderLayout());
		
		environPanel = new JPanel();
		environPanel.setLayout(new GridLayout(50,50));
		
		passTime = new JButton("Pass Time");
		passTime.addActionListener(new PassTimeListener());
		
		panel.add(passTime, BorderLayout.SOUTH);
		panel.add(environPanel, BorderLayout.CENTER);
		add(panel);
	}
	public void populateEnvironment(int ra, int de, int sq, int rc, int wo, int sr)
	{
		//prey animal blueprints
		Prey rabbit = new Prey("Rabbit",1,15);
		
		Prey deer = new Prey("Deer",2,20);
		deer.setAntlers(true);
		deer.setHooves(true);
		
		Prey squirrel = new Prey("Squirrel",1,5);
		squirrel.setTeeth(true);
		
		Prey raccoon = new Prey("Raccoon",2,30);
		raccoon.setTeeth(true);
		raccoon.setClaws(true);
		
		//predator blueprints
		Predator wolf = new Predator("Wolf",2,20);
		wolf.setFangs(true);
		wolf.setThickFur(true);
		wolf.setStealth(true);
		
		//extremely overpowered and to be feared
		Predator superRaccoon = new Predator("Super Raccoon",2,30);
		superRaccoon.setFangs(true);
		superRaccoon.setThickFur(true);
		superRaccoon.setStealth(true);
		superRaccoon.setClaws(true);
		superRaccoon.setVenom(true);
		superRaccoon.setHorns(true);
		
		
		//this generates the terrain, with random amounts of foliage
		System.out.println("Generating Terrain");
		environment = new Terrain[50][50];
		Random lottery = new Random();

		for(int x = 0; x < environment.length; x++)
		{
			for(int y = 0; y < environment[x].length; y++)
			{
				environment[x][y] = new Terrain(x,y, lottery.nextInt(51), 50, 1);
				environPanel.add(environment[x][y].getLabel());
			}
		}
		
		//checks to see how many animals were input by the user
		if(ra > 0)
		{
			placeAnimals(rabbit, ra, environment);
		}
		if(de > 0)
		{
			placeAnimals(deer, de, environment);
		}
		if(sq > 0)
		{
			placeAnimals(squirrel, sq, environment);
		}
		if(rc > 0)
		{
			placeAnimals(raccoon, rc, environment);
		}
		if(wo > 0)
		{
			placeAnimals(wolf, wo, environment);
		}
		if(sr > 0)
		{
			placeAnimals(superRaccoon, sr, environment);
		}
		
	}
	
	//takes in the animal, amount, and environment, and randomly places the animals
	public void placeAnimals(Animal a, int num, Terrain[][] t)
	{
		Random lottery = new Random();
		
		int i = 0;
		
		while(i < num)
		{
			int x = lottery.nextInt(environment.length);
			int y = lottery.nextInt(environment.length);
			
			if(t[x][y].getCurrentAnimal() == null)
			{
				if(a instanceof Prey)
				{
					t[x][y].setCurrentAnimal(new Prey((Prey)a));
					i++;
				}
				if(a instanceof Predator)
				{
					t[x][y].setCurrentAnimal(new Predator((Predator)a));
					i++;
				}
			}
		}
	}
	
	
	//this action listener controls the turns of the animals
	//the flow is this:
	//predators move first, they do not eat or reproduce on this turn
	//prey moves second, they eat and reproduce
	//predators move once more, this time they eat and reproduce
	private class PassTimeListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			//predators make their actions first, don't eat yet
			for(int x = 0; x < environment.length; x++)
			{
				for(int y = 0; y < environment[x].length; y++)
				{
					environment[x][y].passTime();
					if(environment[x][y].getCurrentAnimal() != null)
					{
						boolean starved = environment[x][y].getCurrentAnimal().checkIfStarved();
						if(starved)
						{
							environment[x][y].setCurrentAnimal(null);
						}
						
						if(environment[x][y].getCurrentAnimal() instanceof Predator)
						{
							if(!environment[x][y].getCurrentAnimal().checkIfMoved())
							{
								boolean moved = environment[x][y].getCurrentAnimal().move(environment, x,y);
								if(moved)
								{
									environment[x][y].setCurrentAnimal(null);
								}
							}
							//boolean killed = ((Predator)environment[x][y].getCurrentAnimal()).checkIfKilled();
							//if(killed)
							//{
							//	environment[x][y].setCurrentAnimal(null);
							//}
						}
					}
				}
			}
			//prey come second
			for(int x = 0; x < environment.length; x++)
			{
				for(int y = 0; y < environment[x].length; y++)
				{
					if(environment[x][y].getCurrentAnimal() != null)
					{
						if(environment[x][y].getCurrentAnimal() instanceof Prey)
						{
							if(!environment[x][y].getCurrentAnimal().checkIfMoved())
							{
								((Prey)environment[x][y].getCurrentAnimal()).reproduce(environment,x,y);
								((Prey)environment[x][y].getCurrentAnimal()).eat(environment[x][y]);
								boolean moved = environment[x][y].getCurrentAnimal().move(environment, x,y);
								if(moved)
								{
									environment[x][y].setCurrentAnimal(null);
								}	
							}
						}
						else
						{
							//reset the predators for their next turn
							environment[x][y].getCurrentAnimal().resetMovement();
						}
					}
				}
			}
			//predators get a second turn, eat this turn
			for(int x = 0; x < environment.length; x++)
			{
				for(int y = 0; y < environment[x].length; y++)
				{
					if(environment[x][y].getCurrentAnimal() != null)
					{
						if(environment[x][y].getCurrentAnimal() instanceof Predator)
						{
							((Predator)environment[x][y].getCurrentAnimal()).eat(environment, x,y);
							if(!environment[x][y].getCurrentAnimal().checkIfMoved())
							{
								((Predator)environment[x][y].getCurrentAnimal()).reproduce(environment,x,y);
								boolean moved = environment[x][y].getCurrentAnimal().move(environment, x,y);
								if(moved)
								{
									environment[x][y].setCurrentAnimal(null);
								}
								//boolean killed = ((Predator)environment[x][y].getCurrentAnimal()).checkIfKilled();
								//if(killed)
								//{
								//	environment[x][y].setCurrentAnimal(null);
								//}
							}
						}
					}
				}
			}
			for(int x = 0; x < environment.length; x++)
			{
				for(int y = 0; y < environment[x].length; y++)
				{
					if(environment[x][y].getCurrentAnimal() != null)
					{
						environment[x][y].getCurrentAnimal().resetMovement();
					}
				}
			}
		}
	}	
}
