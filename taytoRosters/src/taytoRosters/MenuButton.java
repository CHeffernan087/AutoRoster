package taytoRosters;

import java.awt.Color;

import processing.core.PApplet;

public class MenuButton {
	PApplet parent;
	float height;
	float width;
	float xpos;
	float ypos;
	boolean onScreen = true;
	MenuButton(PApplet parent,float xpos,float ypos,float height,float width)
	{
		this.parent = parent;
		this.ypos =  ypos;
		this.xpos = xpos;
		this.height = height;
		this.width = width;
		
	}
	
	public void draw()
	{	
		if(onScreen){
			
		
			parent.fill(255);
			parent.stroke(0);
			for(int i = 0;i<3;i++)
			{
				parent.rect(xpos, ypos+(i*((float)(height/3.0))), width, 10,7);
			}
		}
	}
	
	public boolean click()
	{
		
		if(parent.mouseX>xpos && parent.mouseX< xpos+width && parent.mouseY>ypos && parent.mouseY< ypos+height)
		{
			return true;
		}
		return false;
	}
}
