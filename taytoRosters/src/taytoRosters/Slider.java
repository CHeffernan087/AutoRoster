package taytoRosters;

import java.awt.Color;
import java.awt.Component;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import processing.core.PApplet;
import processing.core.PImage;
import processing.pdf.*;
public class Slider {
	public static PGraphicsPDF pdf;
	PApplet parent;
	float height;
	float width;
	float xpos;
	float ypos;
	float actualXpos;
	boolean visible = false;
	Color backgroundColor = new Color(10, 203, 252);
	boolean transitioning = false;
	boolean recording = false;
	boolean onScreen = true;
	float endSecond;
	PImage taytoLogo;
	
	Widget[] menuButtons = new Widget[5];
	String[] buttonNames = {"Days Off","New Roster","Edit Employees","Save Pdf","",""};
	PImage[] icons = {Main.daysOffIcon,Main.refreshIcon,Main.editIcon,Main.saveIcon,null,null};
	Widget DAYS_OFF_BUTTON;
	Widget REFRESH_BUTTON;
	Widget EDIT_EMPLOYEES_BUTTON;
	Widget SAVE_PDF_BUTTON;
	Slider(PApplet parent,float xpos,float ypos,float height,float width)
	{
		this.parent = parent;
		this.ypos =  ypos;
		this.xpos = xpos;
		this.height = height;
		this.width = width;
		this.actualXpos = xpos-width ;
		
		for(int i =0;i<menuButtons.length;i++)
		{
			menuButtons[i] = new Widget(parent, actualXpos,(float)( ypos+ i*(this.height/10.0)), (float)(this.height/10.0), width, backgroundColor);
			menuButtons[i].buttonText = buttonNames[i];
			menuButtons[i].icon = icons[i];
		}
		DAYS_OFF_BUTTON = menuButtons[0];
		REFRESH_BUTTON = menuButtons[1];
		EDIT_EMPLOYEES_BUTTON = menuButtons[2];
		SAVE_PDF_BUTTON = menuButtons[3];
		
		pdf = (PGraphicsPDF) createGraphics(Main.screenSize.width, Main.screenSize.height, Main.PDF, "pause-resume.pdf");
		taytoLogo = parent.loadImage("data/tayto.png");
		
	}
	
	private PGraphicsPDF createGraphics(float width2, float height2, String pdf2, String string) {
		// TODO Auto-generated method stub
		return null;
	}

	public void show()
	{
		this.visible = true;
		this.xpos = 0;
		this.transitioning = true;
		
	}
	
	public void  hide()
	{
		this.visible = false;
		this.xpos = 0-width;
		this.transitioning = true;
	}
	
	public void transition()
	{
		if(visible)
		{
			
			if(actualXpos < xpos)
			{
				
				actualXpos+=15;
				for(int i = 0;i<menuButtons.length;i++)
				{
					menuButtons[i].xpos+=15;
				}
			}
			else
			{
				transitioning = false;
			}
		}
		else if(!visible)
		{
			if(actualXpos >= xpos)
			{
				actualXpos-=15;
				for(int i = 0;i<menuButtons.length;i++)
				{
					menuButtons[i].xpos-=15;
				}
			}
			else
			{
				transitioning = false;
			}
		}
	}
	
	public void draw()
	{
		if(onScreen){
			parent.stroke(new Color(145, 251, 255).getRGB());
			parent.fill(backgroundColor.getRGB());
			parent.rect(actualXpos, ypos, width,height);
			for(int i = 0;i<menuButtons.length;i++)
			{
				menuButtons[i].draw();
			}
			if(taytoLogo!=null){
			parent.image(taytoLogo,actualXpos-85,(float)(height*.6));
			}
		}
		if(recording)
		{
			if(parent.millis()>endSecond)
			{
				parent.endRecord();
				Main.slidingMenu.onScreen = true;
				Main.mainBanner.onScreen = true;
				Main.dropDownIcon.onScreen = true;
				Main.testTable.setYpos(150);
				recording = false;
			}
		}
	}
	
	public boolean click() 
	{
		for(int i = 0;i< menuButtons.length;i++)
		{
			if(DAYS_OFF_BUTTON.click())
			{
				Main.slidingMenu.hide();
				if(Main.testTable.removeShiftEnabled == false){
					
						Main.testTable.removeShiftEnabled = true;
					}
					else if(Main.testTable.removeShiftEnabled )
					{
						Main.testTable.removeShiftEnabled = false;
					
					}
				return true;	
			}
			else if(REFRESH_BUTTON.click())
			{
				Main.inputStream = new In(Main.textFile);
				Main.thisMonthsRoster = new Roster(1,Main.inputStream);
				
				Employee[][] staffNeededEveryDay = Main.thisMonthsRoster.getStaffNeededForAMonth();
				
				String[][] names = Main.thisMonthsRoster.convertEmployeeToString2d(staffNeededEveryDay);

				//new JTableUsage().start(days);	
				Main.testTable = new Table(parent,(float)50,(float)150,Main.thisMonthsRoster.workers);
				Main.testTable.writeNames();
				System.out.println(3);
				Main.testTable.drawRoster();
				Main.testTable.setHeadings(Main.days);
				
				
				return true;
			}
			else if(EDIT_EMPLOYEES_BUTTON.click())
			{

				String cmd = "open data/trains_employees.txt";
				try {
					Process p = Runtime.getRuntime().exec(cmd);
				
				} catch (IOException e) {
					// TODO Auto-generated catch block
					
					e.printStackTrace();
				}
				return true;
			}
			else if(SAVE_PDF_BUTTON.click())
			{
				String getFileName = JOptionPane.showInputDialog("Name Of File To Save");
				Scanner fileNameInput = new Scanner(getFileName);
				String fileName = "/"+fileNameInput.nextLine()+".pdf";
				System.out.println(fileName);
				//fileName = fileName.replaceAll(" ", "_");
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				
				try {
					Thread.sleep(30);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			    fileChooser.showDialog(null,"Select Folder");
			    fileChooser.setVisible(true);
			    File filename = fileChooser.getSelectedFile();
			    String saveLocation = filename.getAbsolutePath();
			    saveLocation+=fileName;
			    Main.slidingMenu.onScreen = false;
				Main.mainBanner.onScreen = false;
				Main.dropDownIcon.onScreen = false;
				Main.testTable.setYpos(20);
				parent.beginRecord(Main.PDF, saveLocation); 
				recording = true;
				endSecond = parent.millis()+300;
				
				
			
				
				return true;
				
			}
		}
		return false;
	}
	public String promptForFolder( Component parent )
	{
	    JFileChooser fc = new JFileChooser();
	    fc.setFileSelectionMode( JFileChooser.DIRECTORIES_ONLY );

	    if( fc.showOpenDialog( parent ) == JFileChooser.APPROVE_OPTION )
	    {
	        return fc.getSelectedFile().getAbsolutePath();
	    }
		
	    return null;
	}
}
