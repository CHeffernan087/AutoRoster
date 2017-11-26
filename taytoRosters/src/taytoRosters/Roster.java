package taytoRosters;

import java.awt.Color;
import java.util.Arrays;
import java.util.Random;

public class Roster 
{
	Employee[] workers;
	Month currentMonth;
	Roster(int month, In inputStream)
	{
		this.currentMonth = new Month(month);
		String input = inputStream.readAll();
		input = input.replaceAll(", ", ",");
		String[]employeeNames = input.split(",");
		workers = new Employee[employeeNames.length];
		for(int i = 0;i<employeeNames.length;i++)
		{
			workers[i] = new Employee(employeeNames[i]);
		}
		System.out.println("workers:"+ workers.length);
		
		
	}
	
	public void printWorkersNames()
	{
		for(int i = 0; i<workers.length;i++)
		{
			System.out.println(workers[i].name);
		}
	}
	
	public Employee selectStaff(int date,String startTime)
	{
//		Random generator = new Random();
//		int selectedWorker = generator.nextInt(workers.length);
//		while(!(workers[selectedWorker].availableToWork(date)))
//		{
//			selectedWorker = generator.nextInt(workers.length);
//			
//		}
		sortEmployees();
		for(int i = 0;i<workers.length;i++)
		{
			if(workers[i].availableToWork(date) && workers[i].notWorkingToday(date))
			{
				workers[i].addDateToRoster(date,startTime);
				workers[i].calculateHoursWorked();
				workers[i].calculateDaysWorked();
				sortEmployees();
				return workers[i];
			}
		}
		
		return null;
	}
	
	public Employee[] getStaffNeededForADay(int numberOfStaffRequired,int date,String[] shifts)
	{
		
		Employee[] staffForTheDay = new Employee[numberOfStaffRequired];
		for(int i  = 0;i< numberOfStaffRequired;i++)
		{
			staffForTheDay[i] = selectStaff(date,shifts[i]);
		}
		giveDaysOff(date);
		return staffForTheDay;
	}
	
	public Employee[][] getStaffNeededForAMonth()
	{
		randomiseEmployeeList();
		int daysInMonth = currentMonth.daysInMonth;
		String[] shiftsForTheDay = {"9","9:30","11","12:30"};
		Employee[][] staffForTheMonth = new Employee[daysInMonth][];
		for(int i  = 1;i<= daysInMonth;i++)
		{
			staffForTheMonth[i-1] = getStaffNeededForADay(shiftsForTheDay.length,i,shiftsForTheDay);
		}
		sortEmployeesAlphabetically();
		return staffForTheMonth;
	}
	public void giveDaysOff(int date)
	{
		for(int i =0;i<workers.length;i++)
		{
			if(workers[i].notWorkingToday(date))
			{
				workers[i].numberOfDaysWorked = 0;
			}
		}
	}
	public boolean reallocateShift(Employee worker, int date)
	{
		int indexOfCurrentWorker = Arrays.asList(workers).indexOf(worker);
		Shift shiftToSwitch = workers[indexOfCurrentWorker].getDesiredShift(date);
		Employee replacementWorker = getReplacementWorker(date,worker);
		if(replacementWorker!=null){
		replacementWorker.addExtraDateToRoster(shiftToSwitch.date, shiftToSwitch.startTime);
		int indexOfReplacement = Arrays.asList(workers).indexOf(replacementWorker);
		Main.testTable.tableCells[indexOfReplacement+1][date].cellText  = shiftToSwitch.startTime;
		Main.testTable.tableCells[indexOfReplacement+1][date].cellColor = Color.WHITE;
		replacementWorker.calculateHoursWorked();
		replacementWorker.calculateDaysWorked();
		replacementWorker.sortShiftsByDate();
		System.out.println(replacementWorker.shiftsToString());
		workers[indexOfCurrentWorker].removeShift(date);
		workers[indexOfCurrentWorker].adjustNullShiftElements();
		worker.calculateDaysWorked();
		worker.calculateHoursWorked();
		Main.testTable.updateWorkerInfo(workers);
		Main.testTable.updateStatBoxes();
		return true;
		}
		else
		{
			System.out.println("Could not get staff to cover this shift!");
			return false;
		}
		
	}
	
	public String[][] convertEmployeeToString2d(Employee[][] array)
	{
		String[][] nameVersion  = new String[array.length][];
		for(int i = 0; i<array.length;i++)
		{
			String[] names = new String[array[i].length];
			for(int j=0;j<names.length;j++)
			{
				names[j] = array[i][j].name;
			}
			nameVersion[i] = names; 
		}
		return nameVersion;
	}
	public void printHoursWorked()
	{
		for(int i = 0;i<workers.length;i++)
		{
			workers[i].calculateHoursWorked();
			workers[i].calculateDaysWorked();
		}
		Main.testTable.updateWorkerInfo(workers);
	}
	
	public void sortEmployees()
	{
		for(int i = 0;i<workers.length;i++)
		{
			for(int j = 0;j<workers.length-1;j++)
			{
				if(workers[j].hoursThisMonth>workers[j+1].hoursThisMonth)
				{
					Employee temp = workers[j];
					workers[j] = workers[j+1];
					workers[j+1] = temp;
				}
			}
		}
	}
	
	public void sortEmployeesByHoursWorked(Employee[] workers)
	{
		for(int i = 0;i<workers.length;i++)
		{
			for(int j = 0;j<workers.length-1;j++)
			{
				if(workers[j].hoursThisMonth>workers[j+1].hoursThisMonth)
				{
					Employee temp = workers[j];
					workers[j] = workers[j+1];
					workers[j+1] = temp;
				}
			}
		}
	}
	
	public void randomiseEmployeeList()
	{
		Random generator = new Random();
		Employee[] randomisedListing = new Employee[workers.length];
		for(int i = 0;i< workers.length;i++)
		{
			int randomIndex = generator.nextInt(workers.length);
			while(randomisedListing[randomIndex]!=null)
			{
				randomIndex = generator.nextInt(workers.length);
			}
			randomisedListing[randomIndex] = workers[i];
		}
		workers = randomisedListing;
	}
	
	public void sortEmployeesAlphabetically()
	{
		String[] names = new String[workers.length];
		for(int i = 0;i<workers.length;i++){
			
			names[i] = workers[i].name;
		}
		Arrays.sort(names);
		Employee[] sortedList = new Employee[workers.length];
		for(int i=0;i<workers.length;i++)
		{
			int correctIndex = Arrays.asList(names).indexOf(workers[i].name);
			sortedList[correctIndex] = workers[i];
		}
		workers = sortedList;
	}
	
	public Employee getReplacementWorker(int date, Employee workerToBeReplaced)
	{
		 return getAvailableWorkerWithLeastHours(date,workerToBeReplaced);
	}
	
	public Employee getAvailableWorkerWithLeastHours(int date)
	{
		Employee[] copyOfWorkerArray = new Employee[workers.length];
		for(int i = 0;i<workers.length;i++)
		{
			copyOfWorkerArray[i] = workers[i];
		}
		sortEmployeesByHoursWorked(copyOfWorkerArray);
		
		for(int i = 0;i<copyOfWorkerArray.length;i++)
		{
			if(copyOfWorkerArray[i].availableToWork(date))
			{
				return copyOfWorkerArray[i];
			}
		}
		return null;
	}
	
	public Employee getAvailableWorkerWithLeastHours(int date,Employee unwantedEmployee)
	{
		Employee[] copyOfWorkerArray = new Employee[workers.length];
		for(int i = 0;i<workers.length;i++)
		{
			copyOfWorkerArray[i] = workers[i];
		}
		sortEmployeesByHoursWorked(copyOfWorkerArray);
		
		for(int i = 0;i<copyOfWorkerArray.length;i++)
		{
			if(copyOfWorkerArray[i].availableToWorkDate(date)&&copyOfWorkerArray[i].notWorkingToday(date)&&!copyOfWorkerArray[i].equals(unwantedEmployee))
			{
				return copyOfWorkerArray[i];
			}
		}
		return null;
	}
}
