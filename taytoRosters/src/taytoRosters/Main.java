package taytoRosters;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.util.Arrays;

import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import processing.core.PApplet;
import processing.pdf.*;
import processing.core.PFont;
import processing.core.PImage;
public class Main extends PApplet {
	public static String[] days = {"","1","2","3","4","10","11","12","13","14","15","16","17","18","19","20","21","20","21","22","23","24","25","26","27","28","29","30","31"};
	public static Roster thisMonthsRoster;
	public static Table testTable;
	public static Dimension screenSize;
	public static PFont smallFont;
	public static PFont menuFont;
	public static PFont infoBoxFont;
	public int count = 0;
	public static Banner mainBanner;
	public static MenuButton dropDownIcon;
	public static Slider slidingMenu;
	public static PGraphicsPDF pdf;
	public static PImage daysOffIcon;
	public static PImage saveIcon;
	public static PImage editIcon;
	public static PImage refreshIcon;
	public static File textFile;
	public static In inputStream;
	public static Widget stopDaysOffButton;
	
	
	public static void main(String[] args)
	{
		PApplet.main("taytoRosters.Main");
	}
	public void settings()
	{
		screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		size(screenSize.width,screenSize.height);	

	}
	public void setup()
	{
		
		String[] options = {"Nest","Trains","High Ropes","Zipline","Token Office"};
		JComboBox mainBox = new JComboBox(options);
		String input = (String) JOptionPane.showInputDialog(null,
				"Which Roster Would You Like To Produce?",
				"", JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
		if (input.equals(options[1])) {
			System.out.println("Hi hello, how are ya");
			String[] monthOptions = {"Jan","Feb","Mar","April","May","June","July","Aug","Sept","Oct","Nov","Dec"};
			JComboBox monthBox = new JComboBox(monthOptions);
			String month = (String) JOptionPane.showInputDialog(null,
					"Which month is the roster for?",
					"", JOptionPane.QUESTION_MESSAGE, null, monthOptions, monthOptions[0]);
			JOptionPane.showConfirmDialog(null, "Do you have a previous months roster to work off?");
		}
	daysOffIcon = loadImage("data/sun.png");
	saveIcon = loadImage("data/cameraIcon.png");
	editIcon = loadImage("data/pencil.png");
	refreshIcon = loadImage("data/refresh.png");
	mainBanner =  new Banner(this,(float)(height/12.0),screenSize.width,0,0);
	dropDownIcon = new MenuButton(this,20,20,40,40);
	slidingMenu = new Slider(this,0,mainBanner.height,(float)(screenSize.height - mainBanner.height),(float)(screenSize.width/6.0));
	smallFont = createFont("data/OpenSans-Bold.ttf", 10);
	menuFont = createFont("data/OpenSans-Bold.ttf", 22);
	infoBoxFont = createFont("data/OpenSans-Bold.ttf", 14);
	stopDaysOffButton = new Widget(this, (float)(screenSize.width*.85),(float)( screenSize.height*.75), 70, 140, mainBanner.backgroundColor);
	stopDaysOffButton.buttonText = "Finish";
	stopDaysOffButton.textMargin = 25;
	pdf = (PGraphicsPDF) createGraphics(width, height, PDF, "pause-resume.pdf");

	textFont(smallFont);
	textFile = new File("data/trains_employees.txt");
	inputStream = new In(textFile);
	thisMonthsRoster = new Roster(1,inputStream);
	thisMonthsRoster.printWorkersNames();
	Employee[][] staffNeededEveryDay = thisMonthsRoster.getStaffNeededForAMonth();
	String[][] names = thisMonthsRoster.convertEmployeeToString2d(staffNeededEveryDay);
	for(int i = 0;i<names.length;i++)
	{
		System.out.println(Arrays.toString(names[i]));
	}
	for(int i = 0;i<thisMonthsRoster.workers.length;i++)
	{
		System.out.println(Arrays.toString(thisMonthsRoster.workers[i].datesToWorkThisMonth));
	}
	testTable = new Table(this,(float)50,(float)150,thisMonthsRoster.workers);
	testTable.writeNames();
	testTable.drawRoster();
	testTable.setHeadings(days);
	thisMonthsRoster.printHoursWorked();
	testTable.updateStatBoxes();
	
	}
	
	public void draw()
	{
		
		background(255);
		testTable.draw();
		mainBanner.draw();
		dropDownIcon.draw();
		slidingMenu.draw();
		if(testTable.removeShiftEnabled)
		{
		stopDaysOffButton.draw();
		}
		if(slidingMenu.transitioning)
		{
			slidingMenu.transition();
		}
		testTable.mouseOver();
	}
	
	
	public void mouseClicked(){
		if(!testTable.clicked())
		{
			testTable.unfocusCells();
			testTable.cellFocused = false;
		}

		if(dropDownIcon.click()){
			
			if(slidingMenu.visible)
			{
				slidingMenu.hide();
			}
			else
			{
			slidingMenu.show();
			}
		}
		else if(slidingMenu.click())
		{
			
		}
		else if(stopDaysOffButton.click())
		{
			testTable.removeShiftEnabled = false;
		}
		else
		{
			if(slidingMenu.visible)
			{
				slidingMenu.hide();
			}
		}
		
	}
	
	public void keyPressed()
	{
		testTable.keyDown(key);
	}
	
	
}
