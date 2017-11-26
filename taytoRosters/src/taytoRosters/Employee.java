package taytoRosters;

import java.util.Arrays;

public class Employee {
	int numberOfDaysWorked = 0;
	int maxNumberOfDaysAllowed=6;
	int[] datesToWorkThisMonth = new int[31];
	Shift[] shifts = new Shift[31];
	String name;
	float hoursThisMonth = 0;
	int daysThisMonth = 0;
	Employee(String name)
	{
		this.name = name;
		for(int i =0;i< datesToWorkThisMonth.length;i++)
		{
			datesToWorkThisMonth[i] = 0;
		}
	}
	
	public void addDateToRoster(int date,String startTime)
	{
		for(int i =0;i< datesToWorkThisMonth.length;i++)
		{
			if(!(datesToWorkThisMonth[i]>0))
			{
				datesToWorkThisMonth[i] = date;
				shifts[i] = new Shift(this.name,date,startTime);
				i=datesToWorkThisMonth.length;
			}
		}
		this.numberOfDaysWorked++; 
	}
	public void addExtraDateToRoster(int date,String startTime)
	{
		for(int i =0;i< datesToWorkThisMonth.length;i++)
		{
			if(!(datesToWorkThisMonth[i]>0))
			{
				datesToWorkThisMonth[i] = date;
				shifts[i] = new Shift(this.name,date,startTime);
				i=datesToWorkThisMonth.length;
			}
		}
		 
	}
	
	public String toString()
	{
		return new String("Name:"+name+"\nDatesToWork: "+Arrays.toString(datesToWorkThisMonth));
	}
	public String shiftsToString()
	{
		String allShifts = "";
		for(int i=0;i<shifts.length;i++)
		{
			if(shifts[i]!=null){
				allShifts+= shifts[i].toString();
			}
		}
		return allShifts;
	}
	public boolean notWorkingToday(int date)
	{
		
		for(int i = 0;i<datesToWorkThisMonth.length;i++)
		{
			if(datesToWorkThisMonth[i]==date)
			{
				return false;
			}
		}
		return true;
	}
	
	public boolean workingToday(int date)
	{
		if(notWorkingToday(date))
		{
			return false;
		}
		return true;
	}
	
	public boolean availableToWork(int date)
	{
		if(numberOfDaysWorked<=6 && notWorkingToday(date))
		{
			return true;
		}
		return false;
	}
	
	public Shift getShift(int date)
	{
		for(int i = 0;i<shifts.length;i++)
		{
			if(shifts[i].date==date)
			{
				return shifts[i];
			}
		}
		return null;
	}
	
	public boolean availableToWorkDate(int date)
	{
		if(workingToday(date))
		{
			return false;
		}
		if(workingToday(date-6)&&workingToday(date-5)&&workingToday(date-4)&&workingToday(date-3)&&workingToday(date-2)&&workingToday(date-1))
		{
			return false;
		}
		else if(workingToday(date+1)&&workingToday(date-5)&&workingToday(date-4)&&workingToday(date-3)&&workingToday(date-2)&&workingToday(date-1))
		{
			return false;
		}
		else if(workingToday(date+1)&&workingToday(date+2)&&workingToday(date-4)&&workingToday(date-3)&&workingToday(date-2)&&workingToday(date-1))
		{
			return false;
		}
		else if(workingToday(date+1)&&workingToday(date+2)&&workingToday(date+3)&&workingToday(date-3)&&workingToday(date-2)&&workingToday(date-1))
		{
			return false;
		}
		else if(workingToday(date+1)&&workingToday(date+2)&&workingToday(date+3)&&workingToday(date+4)&&workingToday(date-2)&&workingToday(date-1))
		{
			return false;
		}
		else if(workingToday(date+1)&&workingToday(date+2)&&workingToday(date+3)&&workingToday(date+4)&&workingToday(date+5)&&workingToday(date-1))
		{
			return false;
		}
		else if(workingToday(date+1)&&workingToday(date+2)&&workingToday(date+3)&&workingToday(date+4)&&workingToday(date+5)&&workingToday(date+6))
		{
			return false;
		}
		return true;
	}
	
	public void removeShift(int date)
	{
		for(int i = 0;i<datesToWorkThisMonth.length;i++)
		{
			if(datesToWorkThisMonth[i]==date)
			{
				datesToWorkThisMonth[i] = 0;
			}
		}
		for(int i = 0;i<shifts.length;i++)
		{
			if(shifts[i].date==date)
			{
				shifts[i] = null;
				i = shifts.length;
			}
		}
	}
	
	public void adjustNullShiftElements()
	{
		int indexOfNullElement = 0;
		for(int i = 0;i<shifts.length;i++)
		{
			if(shifts[i]==null)
			{
				indexOfNullElement = i;
				i = shifts.length;
			}
		}
		for(int i = indexOfNullElement ;i<shifts.length-1;i++)
		{
			shifts[i] = shifts[i+1];
		}
	}
	
	public Shift getDesiredShift(int date)
	{
		for(int i = 0;i<shifts.length;i++)
		{
			
			if(shifts[i].date==date)
			{
				
				return shifts[i];
				
			}
		}
		return null;
	}
	
	public void calculateHoursWorked()
	{
		hoursThisMonth = 0;
		for(int i = 0;i<shifts.length;i++)
		{
			if(shifts[i]!=null)
			{
				//if start time on the hour, dealt with here
				String startTime =shifts[i].startTime;
				if(startTime.length()<=2)
				{
					hoursThisMonth+= (19 - Integer.parseInt(startTime));
				}
				else
				{
					//if start time is on the half hour,dealt with here
					String[] brokenUpStartTime = startTime.split(":");
					hoursThisMonth+= (19 - (Integer.parseInt(brokenUpStartTime[0])+0.5));
				}
				
			}
		}
	}
	
	public void calculateDaysWorked()
	{
		int daysWorked=0;
		for(int i = 0;i<shifts.length;i++)
		{
			if(shifts[i]!=null)
			{
				daysWorked++;
				
			}
		
		}
		this.daysThisMonth = daysWorked;
		
	}
	public String getHoursWorkedThisMonth()
	{
		return new String("Name : "+name+"\nHours Worked This month: "+ hoursThisMonth);
	}
	
	public void sortShiftsByDate()
	{
		int[] dates = new int[shifts.length];
		for(int i = 0;i<shifts.length;i++){
			if(shifts[i]!=null)
			{
				dates[i] = shifts[i].date;
			}
			else
			{
				dates[i]= 1000;
			}
		}
		Arrays.sort(dates);
		Shift[] sortedList = new Shift[shifts.length];
		for(int i=0;i<shifts.length;i++)
		{
			if(shifts[i]!=null){
				int correctIndex = Arrays.binarySearch(dates, shifts[i].date);
				sortedList[correctIndex] = shifts[i];
			}
		}
		shifts = sortedList;
	}
}
