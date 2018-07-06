import java.util.Random;

public class Predator extends Animal
{
	protected int combatLosses; //amount of times a predator has failed to kill it's prey
	
	protected boolean fangs; //worth 3 points
	protected boolean claws; //worth 2 points
	protected boolean venom; //worth 3 point
	protected boolean stealth; //worth 1 point
	protected boolean horns; //worth 2 points
	protected boolean thickFur; //worth 1 point
	
	public Predator(String n, int v, int hL)
	{
		super(n,v,hL);
	}
	public Predator(Predator p)
	{
		super(p);
		setFangs(p.hasFangs());
		setClaws(p.hasClaws());
		setVenom(p.hasVenom());
		setStealth(p.hasStealth());
		setHorns(p.hasHorns());
		setThickFur(p.hasThickFur());
		calculateCombatScore();
	}
	
	//setters
	public void setFangs(boolean f)
	{
		fangs = f;
	}
	public void setClaws(boolean c)
	{
		claws = c;
	}
	public void setVenom(boolean v)
	{
		venom = v;
	}
	public void setStealth(boolean s)
	{
		stealth = s;
	}
	public void setHorns(boolean h)
	{
		horns = h;
	}
	public void setThickFur(boolean f)
	{
		thickFur = f;
	}
	
	//getters
	public boolean hasFangs()
	{
		return fangs;
	}
	public boolean hasClaws()
	{
		return claws;
	}
	public boolean hasVenom()
	{
		return venom;
	}
	public boolean hasStealth()
	{
		return stealth;
	}
	public boolean hasHorns()
	{
		return horns;
	}
	public boolean hasThickFur()
	{
		return thickFur;
	}
	
	//other methods
	public void incrementCombatLosses()
	{
		combatLosses++;
	}
	public boolean checkIfKilled()
	{
		if(combatLosses > 3)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	public void calculateCombatScore()
	{
		combatScore = 0;
		//add up all bonuses
		if(hasFangs())
		{
			combatScore+=3;
		}
		if(hasClaws())
		{
			combatScore+=2;
		}
		if(hasVenom())
		{
			combatScore+=3;
		}
		if(hasStealth())
		{
			combatScore+=1;
		}
		if(hasHorns())
		{
			combatScore+=2;
		}
		if(hasThickFur())
		{
			combatScore+=1;
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
	
	//eats, if there is a source of food nearby
	public void eat(Terrain[][] t, int x, int y)
	{
		boolean ate = false;
		
		if(x-1 > -1 && checkForEnemy(t[x-1][y]))
		{
			if(t[x-1][y].getCurrentAnimal() != null)
			{
				if(getCombatScore() >= t[x-1][y].getCurrentAnimal().getCombatScore())
				{
					resetHunger();
					t[x-1][y].setCurrentAnimal(null);
					ate = true;
				}
				else
				{
					incrementCombatLosses();
				}
			}
		}
		else if(x+1 < t.length && checkForEnemy(t[x+1][y]))
		{
			if(t[x+1][y].getCurrentAnimal() != null)
			{
				if(getCombatScore() >= t[x+1][y].getCurrentAnimal().getCombatScore())
				{
					resetHunger();
					t[x+1][y].setCurrentAnimal(null);
					ate = true;
				}
				else
				{
					incrementCombatLosses();
				}
			}
		}
		else if(y-1 > -1 && checkForEnemy(t[x][y-1]))
		{
			if(t[x][y-1].getCurrentAnimal() != null)
			{
				if(getCombatScore() >= t[x][y-1].getCurrentAnimal().getCombatScore())
				{
					resetHunger();
					t[x][y-1].setCurrentAnimal(null);
					ate = false;
				}
				else
				{
					incrementCombatLosses();
				}
			}
		}
		else if(y+1 < t.length && checkForEnemy(t[x][y+1]))
		{
			if(t[x][y+1].getCurrentAnimal() != null)
			{
				if(getCombatScore() >= t[x][y+1].getCurrentAnimal().getCombatScore())
				{
					resetHunger();
					t[x][y+1].setCurrentAnimal(null);
					ate = false;
				}
				else
				{
					incrementCombatLosses();
				}
			}
		}
		if(!ate)
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
		if(x == (t.length-1))
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
		if(y == (t[x].length-1))
		{
			freeEast = false;
		}
		else
		{
			freeEast = checkForFreeSpace(t[x][y+1]);
		}
		
		//check amoung those free spaces for some food sources
		if(freeNorth && (x-getVision() >= 0) && checkForEnemy(t[x-getVision()][y]))
		{
			t[x-1][y].setCurrentAnimal(new Predator(this));
			return true;
		}
		else if(freeSouth && (x+getVision() <= t.length-1) && checkForEnemy(t[x+getVision()][y]))
		{
			t[x+1][y].setCurrentAnimal(new Predator(this));
			return true;
		}
		else if(freeWest && (y-getVision() >= 0) && checkForEnemy(t[x][y-getVision()]))
		{
			t[x][y-1].setCurrentAnimal(new Predator(this));
			return true;
		}
		else if(freeSouth && (y+getVision() <= t[x].length-1) && checkForEnemy(t[x][y+getVision()]))
		{
			t[x][y+1].setCurrentAnimal(new Predator(this));
			return true;
		}
		else //if there are none, move randomly
		{
			Random lottery = new Random();
		
			boolean moved = false;
			
			while(!moved)
			{
				// 0 = north, 1 = south, 2 = west, 3 = east
				int direction = lottery.nextInt(4);
				if(direction == 0 && freeNorth)
				{
					t[x-1][y].setCurrentAnimal(new Predator(this));
					return true;
				}
				else if(direction == 1 && freeSouth)
				{
					t[x+1][y].setCurrentAnimal(new Predator(this));
					return true;
				}
				else if(direction == 2 && freeWest)
				{
					t[x][y-1].setCurrentAnimal(new Predator(this));
					return true;
				}
				else if(direction == 3 && freeEast)
				{
					t[x][y+1].setCurrentAnimal(new Predator(this));
					return true;
				}
				//if all directions are blocked, don't move
				if(!freeNorth && !freeSouth && !freeWest && !freeEast)
				{
					moved = true;
					return false;
				}
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
				t[x-1][y].setCurrentAnimal(new Predator(this));
				t[x-1][y].getCurrentAnimal().resetHunger();
				t[x-1][y].getCurrentAnimal().setAge(0);
				t[x-1][y].getCurrentAnimal().resetMovement();
				break;
			case 1:
				t[x+1][y].setCurrentAnimal(new Predator(this));
				t[x+1][y].getCurrentAnimal().resetHunger();
				t[x+1][y].getCurrentAnimal().setAge(0);
				t[x+1][y].getCurrentAnimal().resetMovement();
				break;
			case 2:
				t[x][y-1].setCurrentAnimal(new Predator(this));
				t[x][y-1].getCurrentAnimal().resetHunger();
				t[x][y-1].getCurrentAnimal().setAge(0);
				t[x][y-1].getCurrentAnimal().resetMovement();
				break;
			case 3:
				t[x][y+1].setCurrentAnimal(new Predator(this));
				t[x][y+1].getCurrentAnimal().resetHunger();
				t[x][y+1].getCurrentAnimal().setAge(0);
				t[x][y+1].getCurrentAnimal().resetMovement();
				break;
			case -1:
				break;
		}
	}
	public String toString()
	{
		return (getName() + ": " + getAge() + " days old. \n" + getHunger() + " days since eaten. " + combatLosses + " combat losses."); 
	}
}
