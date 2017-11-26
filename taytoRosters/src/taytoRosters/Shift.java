package taytoRosters;

public class Shift {
	String workerName;
	String startTime;
	int date;
	Shift(String workerName, int date, String startTime)
	{
		this.workerName = workerName;
		this.date = date;
		this.startTime = startTime;
	}
	
	public String toString()
	{
		return new String("Name: "+workerName+"\nDate: "+date+"\nStart_Time: "+startTime+"\n\n");
	}
}
