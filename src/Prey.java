import java.util.Random;

public class Prey extends Animal
{
	//combat score additives
	protected boolean antlers; //worth 2 points
	protected boolean hooves; //worth 1 point
	protected boolean claws; //worth 2 points
	protected boolean teeth; //worth 1 point
	protected boolean horns; //worth 3 points
	protected boolean venom; //worth 3 points
	
	public Prey(String n, int v, int hL)
	{
		super(n,v,hL);
	}
	public Prey(Prey a)
	{
		super(a);
		setAntlers(a.hasAntlers());
		setHooves(a.hasHooves());
		setClaws(a.hasClaws());
		setTeeth(a.hasTeeth());
		setHorns(a.hasHorns());
		setVenom(a.hasVenom());
		calculateCombatScore();
	}
	
	//setters
	public void setAntlers(boolean a)
	{
		antlers = a;
	}
	public void setHooves(boolean h)
	{
		hooves = h;
	}
	public void setClaws(boolean c)
	{
		claws = c;
	}
	public void setTeeth(boolean t)
	{
		teeth = t;
	}
	public void setHorns(boolean h)
	{
		horns = h;
	}
	public void setVenom(boolean v)
	{
		venom = v;
	}
	
	//getters
	public boolean hasAntlers()
	{
		return antlers;
	}
	public boolean hasHooves()
	{
		return hooves;
	}
	public boolean hasClaws()
	{
		return claws;
	}
	public boolean hasTeeth()
	{
		return teeth;
	}
	public boolean hasHorns()
	{
		return horns;
	}
	public boolean hasVenom()
	{
		return venom;
	}
	
	//other methods
	public void calculateCombatScore()
	{
		combatScore = 0;
		//add up all bonuses
		if(hasAntlers())
		{
			combatScore+=2;
		}
		if(hasHooves())
		{
			combatScore+=1;
		}
		if(hasClaws())
		{
			combatScore+=2;
		}
		if(hasTeeth())
		{
			combatScore+=1;
		}
		if(hasHorns())
		{
			combatScore+=3;
		}
		if(hasVenom())
		{
			combatScore+=3;
		}
		//hunger decrements
		if(hunger >= 10)
		{
			combatScore-=3;
		}
		if(hunger >= 5 && hunger < 10)
		{
			combatScore-=1;
		}
	}
	//checks a coordinate for foliage, if there is some, returns true
	public boolean checkForFood(Terrain t)
	{
		if(t.getFoliage())
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	public void eat(Terrain t)
	{
		if(checkForFood(t) && hunger >= 3)
		{
			resetHunger();
			t.setFoliage(false);
			t.resetGrowthDays();
		}
		else
		{
			incrementHunger();
		}
	}
	public boolean move(Terrain[][] t, int x, int y)
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
		//if all directions are blocked, then animal does not move this day
		
		Random lottery = new Random();
		
		boolean moved = false;
		
		while(!moved)
		{
			// 0 = north, 1 = south, 2 = west, 3 = east
			int direction = lottery.nextInt(4);
			if(direction == 0 && freeNorth)
			{
				//check using the animal's vision to see if they're approaching
				//an enemy. if they are, do not move this direction
				if(x-getVision() <= -1 || checkForEnemy(t[x-getVision()][y]))
				{
					freeNorth = false;
				}
				else
				{
					t[x-1][y].setCurrentAnimal(new Prey(this));
					return true;
				}
			}
			else if(direction == 1 && freeSouth)
			{
				if((x+getVision() >= t.length-1) || checkForEnemy(t[x+getVision()][y]))
				{
					freeSouth = false;
				}
				else
				{
					t[x+1][y].setCurrentAnimal(new Prey(this));
					return true;
				}
			}
			else if(direction == 2 && freeWest)
			{
				if(y-getVision() <= -1 || checkForEnemy(t[x][y-getVision()]))
				{
					freeWest = false;
				}
				else
				{
					t[x][y-1].setCurrentAnimal(new Prey(this));
					return true;
				}
			}
			else if(direction == 3 && freeEast)
			{
				if((y+getVision() >= t[x].length-1) || checkForEnemy(t[x][y+getVision()]))
				{
					freeEast = false;
				}
				else
				{
					t[x][y+1].setCurrentAnimal(new Prey(this));
					return true;
				}
			}
			if(!freeNorth && !freeSouth && !freeWest && !freeEast)
			{
				moved = true;
				return false;
			}
		}
		return false;	
	}
	
	//reproduces if requirements are met
	public void reproduce(Terrain[][] t, int x, int y)
	{
		int direction = findReproduceLocation(t,x,y);
		switch(direction)
		{
			case 0:
				t[x-1][y].setCurrentAnimal(new Prey(this));
				t[x-1][y].getCurrentAnimal().resetHunger();
				t[x-1][y].getCurrentAnimal().setAge(0);
				t[x-1][y].getCurrentAnimal().resetMovement();
				break;
			case 1:
				t[x+1][y].setCurrentAnimal(new Prey(this));
				t[x+1][y].getCurrentAnimal().resetHunger();
				t[x+1][y].getCurrentAnimal().setAge(0);
				t[x+1][y].getCurrentAnimal().resetMovement();
				break;
			case 2:
				t[x][y-1].setCurrentAnimal(new Prey(this));
				t[x][y-1].getCurrentAnimal().resetHunger();
				t[x][y-1].getCurrentAnimal().setAge(0);
				t[x][y-1].getCurrentAnimal().resetMovement();
				break;
			case 3:
				t[x][y+1].setCurrentAnimal(new Prey(this));
				t[x][y+1].getCurrentAnimal().resetHunger();
				t[x][y+1].getCurrentAnimal().setAge(0);
				t[x][y+1].getCurrentAnimal().resetMovement();
				break;
			case -1:
				break;
		}
	}	
}
