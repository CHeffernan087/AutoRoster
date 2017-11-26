package taytoRosters;

import java.awt.Color;

import processing.core.PApplet;

public class Table {
	
	PApplet parent;
	float xpos;
	float ypos;
	Employee[] workerData;
	Cell [][] tableCells;
	boolean cellFocused = false;
	int columnFocused=0;
	int rowFocused = 0;
	String month = "January 2017";
	boolean removeShiftEnabled =false;
	Color[] dotColors = {Color.BLUE,Color.CYAN,Color.YELLOW,Color.GREEN,Color.MAGENTA,Color.ORANGE};
	int colorIndex = 0;
	Table(PApplet parent, float xpos,float ypos,Employee[] workers)
	
	{
		this.parent = parent;
		this.xpos = xpos;
		this.ypos =ypos;
		this.workerData = workers;
		tableCells = new Cell[workers.length+1][Main.thisMonthsRoster.currentMonth.daysInMonth+1]; 
		
		for(int i = 0;i<tableCells.length;i++)
		{
			tableCells[i][0] = new Cell(parent,xpos,ypos+(i*30));
			tableCells[i][0].setWidth(140);
			tableCells[i][0].nameCell = true;
			
			
			if(i>0){
			tableCells[i][0].employeeStats = new InfoBox(parent,tableCells[i][0].xpos+tableCells[i][0].width+2,tableCells[i][0].ypos-70,160,200);
			tableCells[i][0].cellColor = new Color(33, 206, 140);

			tableCells[i][0].employeeStats.writeStats(workerData[i-1]);
			}
			else
			{
				tableCells[i][0].cellColor = new Color(210,210,210);	
			}
		}
		
		for(int i = 0;i<tableCells.length;i++)
		{
			for(int j = 1;j<tableCells[0].length;j++)
			{
				tableCells[i][j] = new Cell(parent,tableCells[i][j-1].xpos+tableCells[i][j-1].width,ypos+(i*30));
				tableCells[i][j].setWidth(Main.screenSize.width/(tableCells[0].length+5));
				if(j>0)
				{
					tableCells[i][j].dotColor = dotColors[colorIndex];
					
				}
			}
			colorIndex++;
			if(colorIndex==dotColors.length)
			{
				colorIndex = 0;
			}
		}
	}
	
	public void draw()
	{
		for(int i = 0;i<tableCells.length;i++)
		{
			for(int j = 0;j<tableCells[0].length;j++)
			{
				tableCells[i][j].draw();
			}
		}
	}
	
	public void setHeadings(String[] headings)
	{
		tableCells[0][0].cellText = month;
		for(int i = 1;i<tableCells[0].length;i++)
		{
			
			tableCells[0][i].cellText = Integer.toString(i);
			tableCells[0][i].cellColor = new Color(210,210,210);
		}
	}
	
	public void writeNames()
	{
		for(int i = 1;i<tableCells.length;i++)
		{
			tableCells[i][0].cellText = workerData[i-1].name;
		
		}
	}
	
	public void drawRoster()
	{
		for(int i = 1;i<tableCells.length;i++)
		{
			for(int j = 1;j<tableCells[0].length;j++)
			{
				if(!(workerData[i-1].notWorkingToday(j)))
				{
					tableCells[i][j].cellText = workerData[i-1].getShift(j).startTime;
				}
				else
				{
					
					tableCells[i][j].cellColor = Color.YELLOW;
					
				}
			}
		}
	}
	
	public boolean clicked()
	{
		if(!Main.slidingMenu.visible){
			for(int i = 0;i<tableCells.length;i++)
			{
				for(int j = 0;j<tableCells[0].length;j++)
				{
					if(tableCells[i][j].clicked())
					{
						if(!removeShiftEnabled)
						{
						unfocusCells();
						if(tableCells[i][j].cellColor.equals(Color.BLACK))
						{
							tableCells[i][j].cellColor = new Color(255,255,255);
						}
						tableCells[i][j].focused = true;
						cellFocused = true;
						rowFocused = i;
						columnFocused = j;
						
						}
						else
						{
							//remove shifts
							
							if(!tableCells[i][j].cellText.equals("") && !tableCells[i][j].cellColor.equals(Color.BLACK))
							{
								if(Main.thisMonthsRoster.workers[i-1].workingToday(j)){
								
									if(Main.thisMonthsRoster.reallocateShift(Main.thisMonthsRoster.workers[i-1], j))
									{
										tableCells[i][j].cellColor = Color.BLACK;
										tableCells[i][j].cellText = "";
									}
								}
								
							}
							else
							{
							
								tableCells[i][j].cellColor = Color.BLACK;
							}
							
						}
						return true;
					}
				}
			}
		}
		return false;
	}
	
	
	public void unfocusCells()
	{
		for(int i = 0;i<tableCells.length;i++)
		{
			for(int j = 0;j<tableCells[0].length;j++)
			{
				tableCells[i][j].focused = false;
				
			}
		}
	}
	
	public void keyDown(char value)
	{
		if(cellFocused)
		{
			if(value==8 )
			{
				if(tableCells[rowFocused][columnFocused].cellText.length()>=1){
				tableCells[rowFocused][columnFocused].cellText = tableCells[rowFocused][columnFocused].cellText.substring(0,tableCells[rowFocused][columnFocused].cellText.length()-1);
				}
				
				if(!containsLetter(tableCells[rowFocused][columnFocused].cellText))
				{
					tableCells[rowFocused][columnFocused].cellColor = Color.WHITE;
				}

			}
			else{
				
			tableCells[rowFocused][columnFocused].cellText += value;
			
			
			if(value < 48 || value > 58 && Character.getNumericValue(value)!=-1 && value!=8)
			{
				Character.getNumericValue(value);
				tableCells[rowFocused][columnFocused].cellColor = new Color(247, 103, 103);
			}
			}
		}
	}
	
	public void setYpos(float ypos)
	{
		this.ypos = ypos;
		for(int i = 0;i<tableCells.length;i++){
			for(int j = 0;j<tableCells[0].length;j++){
		
				tableCells[i][j].ypos= ypos+(i*30);
			
			}
		}
	}
	
	public boolean containsLetter(String text)
	{
		for(int i = 0;i<text.length();i++)
		{
			char currentChar = text.charAt(i);
			if((currentChar<48 || currentChar > 59) && Character.getNumericValue(currentChar)!=-1)
			{
				return true;
			}
		}
		return false;
	}
	
	public void mouseOver()
	{
		for(int i = 0;i<tableCells.length;i++){
		
				tableCells[i][0].mouseOver();
			
			
		}
	}
	
	public void updateStatBoxes()
	{
		for(int i = 1;i<tableCells.length;i++)
		{
			
			tableCells[i][0].employeeStats.writeStats(workerData[i-1]);
		}
	}
	
	public void updateWorkerInfo(Employee[] workerData)
	{
		this.workerData = workerData;
	}
	
}
