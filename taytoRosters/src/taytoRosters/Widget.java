package taytoRosters;

import java.awt.Color;

import processing.core.PApplet;
import processing.core.PImage;

public class Widget {
float xpos;
float ypos;
PApplet parent;
float height;
float width;
Color backgroundColor;
String buttonText = "";
PImage icon;
int textMargin = 0;


Widget(PApplet parent, float xpos, float ypos, float height, float width,Color backgroundColor)
{
	this.xpos = xpos;
	this.ypos = ypos;
	this.parent = parent;
	this.height = height;
	this.width = width;
	this.backgroundColor = backgroundColor;
}


public void draw()
{
	
	if(mouseOver())
	{
		parent.strokeWeight(3);
		parent.stroke(255);
	}
	else
	{
		parent.strokeWeight(1);
		parent.stroke(new Color(145, 251, 255).getRGB());
	
	}
	parent.fill(backgroundColor.getRGB());
	parent.rect(xpos,ypos, width,height,8);
	parent.textFont(Main.menuFont);
	parent.stroke(255);
	parent.fill(255);
	parent.text(buttonText,xpos+10+textMargin,(float)(ypos+height*(2.0/3.0)));

	if(icon!=null)
	{
		
		parent.image(icon,(float) (xpos+(width*.7)), ypos + (float)(height*0.2),(float)(height*.7),(float)(height*.7));
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
public boolean mouseOver()
{
	if(parent.mouseX>xpos && parent.mouseX< xpos+width && parent.mouseY>ypos && parent.mouseY< ypos+height)
	{
		if(this.equals(Main.stopDaysOffButton))
		{
			Main.stopDaysOffButton.backgroundColor = Color.RED;
		}
		return true;
	}
	if(Main.stopDaysOffButton.backgroundColor.equals(Color.RED))
	{
		Main.stopDaysOffButton.backgroundColor = Main.mainBanner.backgroundColor;
	}
	return false;
}


}
