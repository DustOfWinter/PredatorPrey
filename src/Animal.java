import java.util.Random;

public abstract class Animal
{
	protected String name; //name of the animal
	protected int age;	//age (in days) of the animal (default at 0)
	protected int hunger;	//hunger of animal
	protected int hungerLimit; //the amount of time an animal can go without eating
	protected int vision;	//how many spaces in any direction an animal can see
	protected int combatScore; //the combat prowess of an animal
	protected boolean hasMoved; //too keep track of whether an animal has moved or not
	
	//constructors
	//basic constructor, used for the creation of new animals, age, hunger and moves start at 0.
	public Animal(String n, int v, int hL)
	{
		setName(n);
		setVision(v);
		setHungerLimit(hL);
		
		setAge(0);
		setHunger(0);
	}
	//a constructor to copy an animal
	public Animal(Animal a)
	{
		setName(a.getName());
		setVision(a.getVision());
		setHungerLimit(a.getHungerLimit());
		
		setAge(a.getAge());
		setHunger(a.getHunger());
		setMoved(a.checkIfMoved());
		
	}
	
	//setters
	public void setName(String n)
	{
		name = n;
	}
	public void setAge(int a)
	{
		age = a;
	}
	public void setHunger(int h)
	{
		hunger = h;
	}
	public void setHungerLimit(int hL)
	{
		hungerLimit = hL;
	}
	public void setVision(int v)
	{
		vision = v;
	}
	public void setMoved(boolean m)
	{
		hasMoved = m;
	}
	
	//getters
	public String getName()
	{
		return name;
	}
	public int getAge()
	{
		return age;
	}
	public int getHunger()
	{
		return hunger;
	}
	public int getHungerLimit()
	{
		return hungerLimit;
	}
	public int getVision()
	{
		return vision;
	}
	public int getCombatScore()
	{
		return combatScore;
	}
	public boolean checkIfMoved()
	{
		return hasMoved;
	}
	
	//other methods
	public void resetMovement()
	{
		hasMoved = false;
	}
	public void incrementHunger()
	{
		hunger = hunger +1;
	}
	public void resetHunger()
	{
		hunger = 0;
	}
	public void incrementAge()
	{
		age = age +1;
	}
	//checks if the animal has passed it's hunger limit
	public boolean checkIfStarved()
	{
		if(hunger >= hungerLimit)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
		//check to see if another animal is friendly
	public boolean checkIfSameSpecies(Object o)
	{
		if(o instanceof Animal)
		{
			if(((Animal)o).getName().equals(getName()))
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		else
		{
			return false;
		}
	}
	
	//checks a coordinate for animals, if it is the same species, it is not an
	//enemy
	public boolean checkForEnemy(Terrain t)
	{
		if(t.getCurrentAnimal() != null)
		{
			if(checkIfSameSpecies(t.getCurrentAnimal()))
			{
				return false;
			}
			else
			{
				return true;
			}
		}
		else
		{
			return false;
		}
	}
	
	//checks if a position is currently occupied
	public boolean checkForFreeSpace(Terrain t)
	{
		if(t.getCurrentAnimal() != null)
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	
	//if an animal meets the requirements, and there is room, return the direction of birth
	public int findReproduceLocation(Terrain[][] t, int x, int y)
	{
		if(hunger < 5 && age > 20)
		{
			//first check for free spaces
			boolean freeNorth;
			boolean freeSouth;
			boolean freeWest;
			boolean freeEast;
			if(x == 0)
			{
				freeNorth = false;
			}
			else
			{
				freeNorth = checkForFreeSpace(t[x-1][y]);
			}
			if(x == t.length-1)
			{
				freeSouth = false;
			}
			else
			{
				freeSouth = checkForFreeSpace(t[x+1][y]);
			}
			if(y == 0)
			{
				freeWest = false;
			}
			else
			{
				freeWest = checkForFreeSpace(t[x][y-1]);
			}
			if(y == t.length-1)
			{
				freeEast = false;
			}
			else
			{
				freeEast = checkForFreeSpace(t[x][y+1]);
			}
			Random lottery = new Random();
		
			boolean birthed = false;
			
			while(!birthed)
			{
				// 0 = north, 1 = south, 2 = west, 3 = east
				int direction = lottery.nextInt(4);
				if(direction == 0 && freeNorth)
				{
					return 0;
				}
				else if(direction == 1 && freeSouth)
				{
					return 1;
				}
				else if(direction == 2 && freeWest)
				{
					return 2;
				}
				else if(direction == 3 && freeEast)
				{
					return 3;
				}
				if(!freeNorth && !freeSouth && !freeWest && !freeEast)
				{
					return -1;
				}
			}
		}
		return -1;
	}
	//check if two animals are equal
	public boolean equals(Object o)
	{
		if(o instanceof Animal)
		{
			if(((Animal)o).getName().equals(getName()) && ((Animal)o).getAge() == getAge() && ((Animal)o).getHunger() == getHunger()
				&& ((Animal)o).getHungerLimit() == getHungerLimit() && ((Animal)o).getVision() == getVision())
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		else
		{
			return false;
		}
	}
	public String toString()
	{
		return (getName() + ": " + getAge() + " days old. \n" + getHunger() + " days since eaten.");
	}
	
	//methods that must be overridden
	public abstract void calculateCombatScore();
	public abstract boolean move(Terrain[][] t, int x, int y);
}