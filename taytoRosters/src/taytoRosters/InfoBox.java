package taytoRosters;

import java.awt.Color;

import processing.core.PApplet;

public class InfoBox {
	PApplet parent;
	float height;
	float width;
	float xpos;
	float ypos;
	String infoText;
	String name;
	float hoursWorked;
	float totalHoursWorked;
	float nameMargin;
	int daysWorked = 0;
	Color darkerBlue = new Color(28, 115, 255);
	Color lightBlue = new Color (0, 161, 255);
	InfoBox(PApplet parent,float xpos,float ypos,float width,float height)
	{
		this.parent = parent;
		this.ypos =  ypos;
		this.xpos = xpos;
		this.height = height;
		this.width = width;
		nameMargin =(float)( width/2.0)-10;
	}
	
	public void draw()
	{
		if(!Main.slidingMenu.visible){
			parent.textFont(Main.infoBoxFont);
			parent.noStroke();
			parent.fill(Color.LIGHT_GRAY.getRGB(),140);
			parent.rect(xpos+15,ypos+15,width,height,8);
			parent.fill(255);
			parent.stroke(0);
			parent.rect(xpos,ypos,width,height,8);
			parent.fill(darkerBlue.getRGB());
			if(this.name!=null){
			parent.text(name,xpos+(width/2)+nameMargin,ypos+26);
			}
			parent.fill(darkerBlue.getRGB());
			parent.line(xpos+35, ypos+35, xpos+width-35, ypos+35);
			parent.textFont(Main.smallFont);
			parent.fill(lightBlue.getRGB());
			parent.text("Hours This Month:", xpos+10, ypos+60);
			parent.fill(darkerBlue.getRGB());
			parent.text(hoursWorked, xpos+10, ypos+75);
			parent.fill(lightBlue.getRGB());
			parent.text("Days This Month:", xpos+10, ypos+100);
			parent.fill(darkerBlue.getRGB());
			parent.text(daysWorked, xpos+10, ypos+115);
			parent.fill(lightBlue.getRGB());
			parent.text("Total Hours:", xpos+10, ypos+140);
			parent.fill(darkerBlue.getRGB());
			parent.text(totalHoursWorked, xpos+10, ypos+155);
		}
	}
	
	public void writeStats(Employee worker)
	{
		nameMargin = 0;
		this.name = worker.name;
		this.hoursWorked = worker.hoursThisMonth;
		this.totalHoursWorked =(float)( 2.3* hoursWorked);
		this.daysWorked = worker.daysThisMonth;
		if(name!=null){
		if(name.length()>14)
		{
			nameMargin -= name.length()*4;
		}
		else
		{
			nameMargin -= name.length()*3.5;
		}
		}
	
	}
}
