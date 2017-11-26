package taytoRosters;

import java.awt.Color;

import processing.core.PApplet;
import processing.core.PImage;

public class Banner {
	float height;
	float width;
	float xpos;
	float ypos;
	PApplet parent;
	Color backgroundColor = new Color(10, 203, 252);
	boolean onScreen = true;
	PImage mainLogo ;
	
	Banner(PApplet parent,float height,float width,float xpos,float ypos)
	{
		this.parent = parent;
		this.ypos =  ypos;
		this.xpos = xpos;
		this.height = height;
		this.width = width;
		System.out.println("width = "+width);
		mainLogo = parent.loadImage("data/autoRoster.png");
		
	}
	
	void draw()
	{
		if(onScreen)
		{
		parent.stroke(new Color(145, 251, 255).getRGB());
		parent.fill(backgroundColor.getRGB());
		parent.rect(xpos,ypos,width,height);
		parent.image(mainLogo, width-150, 10,120,120);
		
		}
		
	}
	
	public void setBackgroundColor(Color newColor)
	{
		this.backgroundColor  = newColor;
	}
}
