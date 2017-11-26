package taytoRosters;

import java.awt.Color;

import processing.core.PApplet;

public class Cell {
	int height = 30;
	float width = 70;
	float xpos;
	float ypos;
	PApplet parent;
	String cellText = "";
	Color cellColor =new Color (255,255,255);
	Color outlineColor = Color.BLACK;
	int outlineWeight = 1;
	boolean focused = false;
	int textMargin = 0;
	boolean nameCell = false;
	Color dotColor;
	InfoBox employeeStats;
	Cell(PApplet parent,float xpos, float ypos)
	{
		this.xpos = xpos;
		this.ypos = ypos;
		this.parent = parent;
	
	}
	
	public void draw()
	{
		parent.textFont(Main.smallFont);
		if(focused)
		{
			this.outlineWeight = 3;
			this.outlineColor = Color.BLUE;
		}
		else
		{
			this.outlineWeight = 1;
			this.outlineColor = Color.BLACK;
		}
		parent.strokeWeight(outlineWeight);
		parent.stroke(outlineColor.getRGB());
		parent.fill(cellColor.getRGB());
		parent.rect(xpos, ypos, width, height);
		parent.fill(0);
		if(!nameCell){
		parent.text(cellText,xpos+15+textMargin,ypos+height-(height/3));
		}
		else
		{
			parent.text(cellText,xpos+15,ypos+height-(height/3));
		}
		if(dotColor!=null && cellColor.equals(Color.WHITE) || cellColor.equals(new Color(247, 103, 103)))
		{
			parent.fill(dotColor.getRGB());
			parent.noStroke();
			parent.ellipse(xpos+5,ypos+5, 4, 4);
		}
		if(cellText.equals("") && this.cellColor.equals(Color.WHITE))
		{
			this.cellColor = Color.YELLOW;
		}
		else if(!cellText.equals("") && this.cellColor.equals(Color.YELLOW))
		{
			this.cellColor = Color.WHITE;
		}
		
		
		if(!nameCell && cellText.length()>2 && cellText.length()<6);
		{
			int difference = cellText.length()-2;
			this.textMargin = -2*difference;
		}
		
	}
	
	public void setWidth(float newWidth)
	{
		this.width = newWidth;
	}
	
	public boolean clicked()
	{
		if(parent.mouseX>xpos && parent.mouseX< xpos+width && parent.mouseY>ypos && parent.mouseY< ypos+height)
		{
			
			return true;
		}
		return false;
	}
	
	public void mouseOver()
	{
		if(parent.mouseX>xpos && parent.mouseX< xpos+width && parent.mouseY>ypos && parent.mouseY< ypos+height)
		{
			if(employeeStats!=null)
			{
				employeeStats.draw();
			}
		}
	}
	

}
