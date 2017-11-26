package taytoRosters;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Month {
Day[] days;
String month;
int daysInMonth;



Month(int month)
{
	switch(month)
	{
	case 1:this.month = "January";
		break;
	case 2:this.month = "Februry";
		break;
	case 3:this.month = "March";
		break;
	case 4:this.month = "April";
		break;
	case 5:this.month = "May";
		break;
	case 6:this.month = "June";
		break;
	case 7:this.month = "July";
		break;
	case 8:this.month = "August";
		break;
	case 9:this.month = "September";
		break;
	case 10:this.month = "October";
		break;
	case 11:this.month = "Novemeber";
		break;
	case 12:this.month = "Decemeber";
	break;
	}
	// Create a calendar object and set year and month
	Calendar mycal = new GregorianCalendar(Calendar.getInstance().get(Calendar.YEAR), month-1,1);

	// Get the number of days in that month
	 daysInMonth = mycal.getActualMaximum(Calendar.DAY_OF_MONTH);
	 days = new Day[daysInMonth];
}


}
