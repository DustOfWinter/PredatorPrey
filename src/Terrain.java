import javax.swing.*;
import java.awt.*;

public class Terrain
{
	//Seasons were originally intended to be included in this project,
	//but were cut to fit time
	
	protected Animal currentAnimal; //the animal that currently occupies this space
	
	protected boolean foliage; //does this space have any plant growth
	protected int x,y; //coordinates of this space
	protected int growthDays; //how many days it's been since foliage has been eaten
	protected int growthLength; //how many days it takes for foliage to grow
	protected int season; //1 = spring, 2 = summer, 3 = fall, 4 = winter
	//images for this space
	protected ImageIcon currentImage;
	protected ImageIcon spring = new ImageIcon("terrain_spring.png");
	protected ImageIcon spring_herb1 = new ImageIcon("terrain_spring_herb1.png");
	protected ImageIcon spring_herb2 = new ImageIcon("terrain_spring_herb2.png");
	protected ImageIcon spring_herb3 = new ImageIcon("terrain_spring_herb3.png");
	protected ImageIcon spring_herb4 = new ImageIcon("terrain_spring_herb4.png");
	protected ImageIcon spring_carn1 = new ImageIcon("terrain_spring_carn1.png");
	protected ImageIcon spring_carn2 = new ImageIcon("terrain_spring_carn2.png");
	protected ImageIcon spring_foliage = new ImageIcon("terrain_spring_foliage.png");
	protected ImageIcon spring_foliage_herb1 = new ImageIcon("terrain_spring_foliage_herb1.png");
	protected ImageIcon spring_foliage_herb2 = new ImageIcon("terrain_spring_foliage_herb2.png");
	protected ImageIcon spring_foliage_herb3 = new ImageIcon("terrain_spring_foliage_herb3.png");
	protected ImageIcon spring_foliage_herb4 = new ImageIcon("terrain_spring_foliage_herb4.png");
	protected ImageIcon spring_foliage_carn1 = new ImageIcon("terrain_spring_foliage_carn1.png");
	protected ImageIcon spring_foliage_carn2 = new ImageIcon("terrain_spring_foliage_carn2.png");
	//JLabel to hold image
	protected JLabel label = new JLabel();
	
	//constructor
	public Terrain(int a, int b, int d, int l, int s)
	{
		setCoord(a,b);
		setGrowthDays(d);
		setGrowthLength(l);
		setSeason(s);
		grow();
		updateImage();
	}
	
	//setters
	public void setFoliage(boolean f)
	{
		foliage = f;
		updateImage();
	}
	public void setCoord(int a, int b)
	{
		x = a;
		y = b;
	}
	public void setGrowthDays(int d)
	{
		growthDays = d;
	}
	public void setGrowthLength(int l)
	{
		growthLength = l;
	}
	public void setCurrentAnimal(Animal a)
	{
		currentAnimal = a;
		updateImage();
	}
	public void setSeason(int s)
	{
		season = s;
	}
	//getters
	public boolean getFoliage()
	{
		return foliage;
	}
	public int getX()
	{
		return x;
	}
	public int getY()
	{
		return y;
	}
	public int getGrowthDays()
	{
		return growthDays;
	}
	public int getGrowthLength()
	{
		return growthLength;
	}
	public Animal getCurrentAnimal()
	{
		return currentAnimal;
	}
	public int getSeason()
	{
		return season;
	}
	public ImageIcon getImage()
	{
		return currentImage;
	}
	public JLabel getLabel()
	{
		return label;
	}
	//other methods
	public void incrementGrowthDays()
	{
		growthDays++;
	}
	public void resetGrowthDays()
	{
		growthDays = 0;
	}
	//check if it's time to grow, grows if so
	public void passTime()
	{
		if(getCurrentAnimal() != null)
		{
			getCurrentAnimal().incrementAge();
		}
		if(!getFoliage())
		{
			incrementGrowthDays();
		}
		else
		{
			resetGrowthDays();
		}
		grow();
	}
	public void grow()
	{
		switch(getSeason())
		{
			case 1:
			{
				if(getGrowthDays() >= getGrowthLength() && getFoliage() == false)
				{
					setFoliage(true);
				}
				break;
			}
			case 2:
			case 3:
			{
				if(getGrowthDays() >= (getGrowthLength()+5) && getFoliage() == false)
				{
					setFoliage(true);
				}
				break;
			}
			case 4:
			{
				if(getGrowthDays() >= (getGrowthLength()+10) && getFoliage() == false)
				{
					setFoliage(true);
				}
				break;
			}
			default:
			{
				updateImage();
			}
		}
	}
	//update the image of the space
	public void updateImage()
	{
		switch(getSeason())
		{
			default:
			{
				if(getFoliage())
				{
					if(getCurrentAnimal() != null)
					{
						if(getCurrentAnimal().getName().equals("Rabbit"))
						{
							currentImage = spring_foliage_herb1;
							label.setIcon(currentImage);
							label.setToolTipText(getCurrentAnimal().toString());
						}
						if(getCurrentAnimal().getName().equals("Deer"))
						{
							currentImage = spring_foliage_herb2;
							label.setIcon(currentImage);
							label.setToolTipText(getCurrentAnimal().toString());
						}
						if(getCurrentAnimal().getName().equals("Squirrel"))
						{
							currentImage = spring_foliage_herb3;
							label.setIcon(currentImage);
							label.setToolTipText(getCurrentAnimal().toString());
						}
						if(getCurrentAnimal().getName().equals("Raccoon"))
						{
							currentImage = spring_foliage_herb4;
							label.setIcon(currentImage);
							label.setToolTipText(getCurrentAnimal().toString());
						}
						if(getCurrentAnimal().getName().equals("Wolf"))
						{
							currentImage = spring_foliage_carn1;
							label.setIcon(currentImage);
							label.setToolTipText(getCurrentAnimal().toString());
						}
						if(getCurrentAnimal().getName().equals("Super Raccoon"))
						{
							currentImage = spring_foliage_carn2;
							label.setIcon(currentImage);
							label.setToolTipText(getCurrentAnimal().toString());
						}
					}
					else
					{
						currentImage = spring_foliage;
						label.setIcon(currentImage);
						label.setToolTipText("");
					}
				}
				else
				{
					if(getCurrentAnimal() != null)
					{
						if(getCurrentAnimal().getName().equals("Rabbit"))
						{
							currentImage = spring_herb1;
							label.setIcon(currentImage);
							label.setToolTipText(getCurrentAnimal().toString());
						}
						if(getCurrentAnimal().getName().equals("Deer"))
						{
							currentImage = spring_herb2;
							label.setIcon(currentImage);
							label.setToolTipText(getCurrentAnimal().toString());
						}
						if(getCurrentAnimal().getName().equals("Squirrel"))
						{
							currentImage = spring_herb3;
							label.setIcon(currentImage);
							label.setToolTipText(getCurrentAnimal().toString());
						}
						if(getCurrentAnimal().getName().equals("Raccoon"))
						{
							currentImage = spring_herb4;
							label.setIcon(currentImage);
							label.setToolTipText(getCurrentAnimal().toString());
						}
						if(getCurrentAnimal().getName().equals("Wolf"))
						{
							currentImage = spring_carn1;
							label.setIcon(currentImage);
							label.setToolTipText(getCurrentAnimal().toString());
						}
						if(getCurrentAnimal().getName().equals("Super Raccoon"))
						{
							currentImage = spring_carn2;
							label.setIcon(currentImage);
							label.setToolTipText(getCurrentAnimal().toString());
						}
					}
					else
					{
						currentImage = spring;
						label.setIcon(currentImage);
						label.setToolTipText("");
					}
				}
				break;
			}
		}
	}
}