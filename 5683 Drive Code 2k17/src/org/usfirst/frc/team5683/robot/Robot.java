//This is the 2017 5683 Drive Code. Written by Kira Kopcho.
//Vision processing done by Kira Kopcho and Kameron Markham. Autonomous done by Kira.
//Code Version 3.1 initialized motors, joysticks and established Mecanum drive (needs to be tested.) 2/8/2017 
//Code Version 3.2 Fixed Mecanum drive code and tested it. (IT'S ALIVE!!!!) 2/9/2017
//Code Version 3.3 Implemented Winch motor. Needs to be tested. 2/10/2017
//Code Version 3.4 Implemented state machine for Winch motor. Also added a state machine and flippedDrive Method. 2/11/2017
//Code Version 3.5 Tested state machine for flippedDrive. Switched it to a while loop due to code crashes. 2/13/2017
//Code Version 3.6 Set up basics for vision code- Auto is a work in progress. 2/17/2017
//Code Version 3.7 Added in vision loop for auto. Gets shape tracking and network tables. 2/19/2017
//Code Version 3.8 Tested vision in auto and changed values around. Final code before bag. 2/21/2017
//Code Version 3.9 Implemented an ultra sonic sensor 3/24/2017.
//Code Version 3.10 Added new ultra sonic sensor, and the math methods for auto. WIP 11/5/2017
//Code Version 3.11 Added Encoders and new auto. 11/5/2017
//This code is a project of the programming team. Ask us before you make any changes.
//Special thanks to Mrs. Katie- the most awesomesauce programming mentor in the world. :)
 

package org.usfirst.frc.team5683.robot;

import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.networktables.PersistentException;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Encoder;
import java.lang.Math;
//Import all the things


/**
SD imports in case we need them 
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
**/

public class Robot extends SampleRobot {
	//Set up variables/Booleans here
	MecanumDrive DoomCube;
	Joystick joystick;
	Victor frontLeft, frontRight, rearLeft, rearRight, wMotor;
	DigitalInput AutoL, AutoR;
	Encoder Encoder1, Encoder2, Encoder3, Encoder4;
	AnalogInput Ultra, Ultra2;
	
    Boolean MotorUp = false;
    Boolean MotorSlow = false;
    //Boolean MotorStop = false;
    Boolean MotorRev  = false;
	Boolean Up1 = false;
	Boolean Up2 = false;
	Boolean SlowButton1 = false;
	Boolean SlowButton2 = false;
	//Boolean Stop1 = false;
	//Boolean Stop2 = false;
	Boolean Rev1 = false;
	Boolean Rev2 = false;
	Boolean TrackingTargeted = false;
	Boolean XonTarget = false;
	Boolean LeftUltra = false;
	Boolean RightUltra = false;
	//Lots and lots of booleans
	
	Double SHAPE_X_COORD;//doubles from the network
	Double COG_X;
	NetworkTable table_xy;
	Double dirSpeed;
	
	//Doubles for mathy math
	double triHyp = 0; //Q
	double triHi = 0;  //R
	double triBase = 0; //S
	double pegAngle = 0; //Theta- angle of peg in rel to tri of int
	double RLength = 0; //length to center edge
	double RWidth = 0; //width to center edge
	double X = 0; //wall x tp corner of tri of int

	
	/** unused code
	//Double SHAPE_CONFIDENCE;
	//long autoStartTime;
	//int cog_x_int; //cog_y_int; //converts doubles to integers
	//int shape_x_int, shape_conf_int;
	**/

	public Robot() {
		//Empty for anything else that needs to be added.	
		
	}

	@Override
	public void robotInit() {
	
		
		//Initialize everything here
		frontLeft = new Victor(0);
		frontRight = new Victor(1);
		rearRight = new Victor(2);
		rearLeft = new Victor(3);
		
		wMotor = new Victor(4);
		
		AutoR = new DigitalInput(0);
		AutoL = new DigitalInput(1);
		
		Encoder1 = new Encoder(2,3, false, Encoder.EncodingType.k2X);
		Encoder2 = new Encoder(4,5, false, Encoder.EncodingType.k2X);
		Encoder3 = new Encoder(6,7, false, Encoder.EncodingType.k2X);
		Encoder4 = new Encoder(8,9, false, Encoder.EncodingType.k2X);
		
		Ultra = new AnalogInput(0);
		Ultra2 = new AnalogInput(1);
		
		DoomCube = new MecanumDrive(frontRight, frontLeft, rearLeft, rearRight);
		joystick = new Joystick(0);
		DoomCube.setExpiration(0.4); //changed or else the code repeatedly crashes. Didn't update fast enough before.
		
		//Vision track init
		table_xy = NetworkTable.getTable("");
		
	}


	@Override
	public void autonomous() {
         DoomCube.setSafetyEnabled(false); //Sets safety to false so we can actually move. Don't freak out.
         while (isAutonomous() && isEnabled()){
        	//autoStartTime = System.currentTimeMillis(); //fetches start time so we can break the loop if needed.
        	//while((System.currentTimeMillis() - autoStartTime) < 15000){
        	 SwitchUltra();
        	 
        	 if (LeftUltra = true) {
        		 double ultraInches = 0;
        		 UltraMathL(ultraInches);
        		 
        		 
        	 }
        	
        	
         }
        	
        	 
        /**
         Backup Auto- DO NOT DELETE	
        if(AutoL.get() == false && AutoR.get() == false){	
        	BaseLine();
            DoomCube.driveCartesian(0,0,0,0);
            Timer.delay(.25);
            //drives forward
            
        	try {    		
            	double ultraInches = UltraConversion();
            	if (ultraInches == 0.0){
            			System.out.println("ultraInches: 0, Failed Ultra, ");
        				System.out.println("calling backup original HomeStretch");
        		DoomCube.driveCartesian(.25,0.02,0,0);
        		Timer.delay(1);
        		DoomCube.driveCartesian(0,0,0,0);
        		Timer.delay(0.25);
        	    //Drives to gear collector and stops
        		
        		}//EO if
            	while (ultraInches > 8.0 && isAutonomous()){ //this should be six, the range limit
        				//System.out.println("From Turning(), Distance in inches: "  + ultraInches);
            		DoomCube.driveCartesian(.40,0.02,0,0); //Drive 40%
        			Timer.delay(0.025);//Delay for about 3 inches @.75
        			ultraInches = UltraConversion();//Update ultraInches & make it exit at some point.
        				//System.out.println("From Turning(), New Distance in inches: " + ultraInches);
        			if(ultraInches <= 8.0){
        				//System.out.println("Distance Reached 8 inches");
        			break;
        			} //Break if new check reads less than 6
            	} //EO while	
            
        			//System.out.println("Out of Distance While loop");
            	
        		DoomCube.driveCartesian(0,0,0,0);
            	Timer.delay(1);//short pause
        		
        			//System.out.println("Last 6-8 inches");
        			
            	//DoomCube.driveCartesian(.3,0.02,0,0); //travels eight inches 
        		//Timer.delay(0.2); 
            	//.00567203* motor speed [.5]= delay ratio [.011334405]
        		//delay ratio [.011334405] * distance in inches [8]= delay time[.090675241]
            	
            	} //EO try    	
            	catch (Exception e) { //if ultra doesn't work
            		System.out.println("Exception Failed Ultra, ");
        			System.out.println("calling backup HomeStretch");
        		DoomCube.driveCartesian(.25,0.02,0,0);
        		Timer.delay(1);
        		DoomCube.driveCartesian(0,0,0,0);
        		Timer.delay(0.25);
                //Drives to gear collector and stops
            	}//EO catch
        	
        	//DoomCube.driveCartesian(-.07,0,0,0);
        	//Timer.delay(0.25);
        	//Drives backwards
            
        	
        	//DoomCube.driveCartesian(0,0,0,0);
            //Timer.delay(.5);
            //makes sure the loop does not repeat.
    		
            return;
         }

         
         if (AutoL.get()== false && AutoR.get() == true){ //if on the right turn left.
        	BaseLine();
        	DoomCube.driveCartesian(0,0,0,0);
            Timer.delay(.25);
            //drives forward
            
        	Turning(-.35);
        	
        	DoomCube.driveCartesian(0,0,0,0);
        	Timer.delay(.25);
        	//calls vision and turns right.
        	
        	return;
           }
         
         if (AutoL.get()== true && AutoR.get() == false){ //if on the left turn right.
         	BaseLine();
         	DoomCube.driveCartesian(0,0,0,0);
            Timer.delay(.25);
            
        	Turning(.35);
        	
        	DoomCube.driveCartesian(0,0,0,0);
        	Timer.delay(.25);
        	//calls vision and turns left.
        	
        	return;
           }
         
         /**
		 if (AutoL.get()== true && AutoR.get()== true) { //fixing the "lemon" problem
			 trackingTape();
			 DoomCube.driveCartesian(0,0,0,0);
			 Timer.delay(.25);
			 //calls standard vision loop.
			  
		 }
         **/
         
         /**
         //Fail-safe in case the loop is not broken before this point.
            DoomCube.driveCartesian(0,0,0,0);
            Timer.delay(0.25);
            System.out.println("All else has failed");
            break;
           	    //}//End timer 
            }//End Auto
             //return; 
              **/
		}
		
	

	/**
	 * Runs the motors with Mecanum steering.
	 */
	@Override

	public void operatorControl() {
		DoomCube.setSafetyEnabled(true);
		while (isOperatorControl() && isEnabled()) {
			normalDrive(); //calls our basic drive method.
						
			while (joystick.getRawButton(1)) { //While the trigger is held
				flippedDrive();
				//flips X and Y Axis so steering is easier
			}
			
			while (MotorUp == true && !joystick.getRawButton(5) && !joystick.getRawButton(4) && !joystick.getRawButton(6)) {
				flippedDrive();
            	wMotor.setSpeed(1.0); //Sets wMotor to spin forwards at 100%
            	//Button 3
            }
			
			//while (joystick.getRawButton(5)) {
			while (MotorSlow == true && !joystick.getRawButton(3) && !joystick.getRawButton(4) && !joystick.getRawButton(6)) {
				flippedDrive();
				wMotor.setSpeed(0.2); //Sets wMotor to spin at 10%
				//Button 5
			}
			
			while (MotorRev == true && !joystick.getRawButton(3) && !joystick.getRawButton(5) && !joystick.getRawButton(6)) {
				flippedDrive();
				wMotor.setSpeed(-0.5); //Sets wMotor to spin backwards at 50%
				//Button 4
			}
			
			while (joystick.getRawButton(6)){ //resets/releases climb
				wMotor.setSpeed(0.0);
				MotorSlow = false;
				MotorUp = false;
				MotorRev = false; 
				//Full stop
			}
						
			//Start State Machine Loops
			Up1 = Up2;
			Up1 = joystick.getRawButton(3);
			SlowButton1 = SlowButton2;
			SlowButton1 = joystick.getRawButton(5);
			//Stop1 = Stop2;
			//Stop1 = joystick.getRawButton(6);
			Rev1 = Rev2;
			Rev1 = joystick.getRawButton(4);
						
			if (!MotorUp) { //Checks to see if motor is on and spinning reverse.
				if (Up1 && !Up2) {
					MotorUp = true;
				}
			}
			
			if (!MotorSlow) { //checks to see if the motor is slowing
				if (SlowButton1 && !SlowButton2){
					MotorSlow = true;
				}
			}
			
			if (!MotorRev){ //checks to see if motor is reverse
				if (Rev1 && !Rev2){
					MotorRev = true;
				}	
			}
			
			/**
			 * Original stop method
			if (!MotorStop) { //checks to see if motor is stopped
				if (Stop1 && !Stop2) {
					MotorStop = true;
					MotorSlow = false;
					MotorUp = false;
					//full stop
				}				
			}
			**/
		Timer.delay(0.005); // wait for a motor update time
		}
	}

	/**
	 * Runs during test mode
	 */
	@Override
	public void test() {
	}


//***Methods below here***
	
	public void normalDrive(){ //basic drive method
		DoomCube.setSafetyEnabled(true);
		DoomCube.driveCartesian(((-joystick.getY()*(-joystick.getThrottle()+ 1)/2)),
				((joystick.getTwist() * (-joystick.getThrottle() + 1)/2)),
				((-joystick.getX() * (-joystick.getThrottle() + 1)/2)),0);//really roundabout Mecanum drive
		Timer.delay(0.004);
	}
	
	public void flippedDrive() { //Method for flipping the robot drive
		DoomCube.setSafetyEnabled(true);
		DoomCube.driveCartesian(((-joystick.getX() * (-joystick.getThrottle() + 1)/2)), 
				((joystick.getTwist() * (-joystick.getThrottle() + 1)/2)),
				((joystick.getY()*(-joystick.getThrottle()+ 1)/2)),0);
		Timer.delay(0.004);
		//Our strafing axis (X) becomes our forward and backward. And our Y axis becomes our strafing.
	}
	
	public void BaseLine(){
		//Drives to Baseline
	    DoomCube.driveCartesian(.70,0.02,0,0);
		Timer.delay(.755);
		DoomCube.driveCartesian(0,0,0,0);
		Timer.delay(0.25);
		
	}
	
	public void HomeStretch(){
		//Drives to gear peg
			DoomCube.driveCartesian(.5,0.02,0,0);
			Timer.delay(.9);
			DoomCube.driveCartesian(0,0,0,0);
			Timer.delay(0.25);
		
	}
	
		
	public double UltraConversion() {
		//System.out.println("sets bits");
		//These are used for averaging raw values
			//Ultra.setOversampleBits(4);
			//Ultra.setAverageBits(2);
			//int val = Ultra.getValue();
		
		//System.out.println("Gets voltage back");
		double ultra_voltage = Ultra.getVoltage(); Ultra2.getVoltage();//this was being converted to an int ... don't think it should be	
		//System.out.println("Voltage:" + ultra_voltage);
					//System.out.println("Converts voltage into inches");
		double ultraInches = ((ultra_voltage/9.766) * 1000);//s/b .09766
			//System.out.println("Distance in inches:"  + ultraInches);
		return ultraInches;
	}
	

	public void SwitchUltra() {
		//Senses which ultrasonic sensor is on
		if (Ultra.getVoltage() > 25) { //if Ultra is on
			Boolean LeftUltra = true;
			System.out.println("Left ultrasonic is on" + LeftUltra);
	}
		
		if (Ultra2.getVoltage() > 25) { //if Ultra2 is on
			Boolean RightUltra = true;
			System.out.println("Right ultrasonic is on:" + RightUltra);
	}
	
		else{
			RightUltra = false;
			LeftUltra = false;
			System.out.println("Critical Error: Sensors offline! Check your wiring and regulator");
		}
	
}
	
	public void UltraMathR(double ultraInches) {
		//Righty Math
		ultraInches = UltraConversion(); //converts to int
		System.out.println("Distance in inches" + ultraInches);
		double wallDist = ultraInches * Math.cos(50); //takes ultraInches and multiplies it by the cosine of the angle
														//of the sensor mount
		                                           //B = A * cosC
		System.out.println("Distance to wall:" + wallDist);
		
		double centDist = RLength + wallDist - X + triBase; 
		System.out.println("Distance from center point:" + centDist); //Distance from vertex to center point.
														//L+B-X+S = H
		
		double vertDrive = centDist/Math.tan(pegAngle)- RWidth; // (H/tan(theta))- W = D
		System.out.println("Driving to hypotenuse" + vertDrive); 
		//This is the number we're gonna use to drive forward
		
		double driveF = (X - wallDist - RLength)/Math.sin(pegAngle); //(x-b-l)/sin(theta)
		System.out.println("Final distance" + driveF);
		
	}
	
	public void UltraMathL(double ultraInches) {
		//Lefty math
		ultraInches = UltraConversion(); //converts to int
		System.out.println("Distance in inches" + ultraInches);
		double wallDist = ultraInches * Math.cos(50); //takes ultraInches and multiplies it by the cosine of the angle
														//of the sensor mount
		                                           //B = A * cosC
		System.out.println("Distance to wall:" + wallDist);
		
		double centDist = RLength + wallDist - X + triBase;
		System.out.println("Distance from center point:" + centDist); //Distance from vertex to center point.
														//L+B-X+S = H
		
		
		double vertDrive = centDist/Math.tan(pegAngle)- RWidth; // (H/tan(theta))- W = D
		System.out.println("Driving to hypotenuse" + vertDrive);
		//This is the number we're gonna use to drive forward
		
		double driveF = (X - wallDist - RLength)/Math.sin(pegAngle); //(x-b-l)/sin(theta)
		System.out.println("Final distance" + driveF); 
		
	}
	
	
		
	
/*	first method for turning. Left in so we can fall back on it if needed.
 * public void TurnLeft(){
		DoomCube.driveCartesian(.3,0.01,0,0);
		Timer.delay(0.6);
			System.out.println("pivot 30 degrees");
		DoomCube.driveCartesian(0,-0.24,0,0);
		Timer.delay(1);
		DoomCube.driveCartesian(0,0,0,0);
				
			System.out.println("Turn Left.");
			System.out.println("Calling Vision Tracking");
		
		trackingTape();
		
		//Turns left until it finds target. 
		
		
	}*/
	
	public void Turning(Double dirSpeed){
		DoomCube.driveCartesian(.5,0.01,0,0);//inching forward from BaseLine
		Timer.delay(0.5);
		DoomCube.driveCartesian(0,dirSpeed,0,0);//turning
		Timer.delay(0.6);

		DoomCube.driveCartesian(0,0,0,0);//stops 
    	Timer.delay(.25);
    	
    	//Calling Vision Tracking
    	trackingTape();//Turns until it finds target.
    	
			//HomeStretch();//Moves to gear collector 
			//Timer.delay(1);
    	
		//calculate distance to target
    	try {    		
    	double ultraInches = UltraConversion();
    	if (ultraInches == 0.0){
				System.out.println("ultraInches: 0, Failed Ultra, ");
				System.out.println("calling backup HomeStretch");
			HomeStretch();
		}//EO if
    	while (ultraInches > 8.0 && isAutonomous()){ //this should be six, the range limit
				//System.out.println("From Turning(), Distance in inches: "  + ultraInches);
    		DoomCube.driveCartesian(.40,0.02,0,0); //Drive 75%
			Timer.delay(0.025);//Delay for about 3 inches @.75
			ultraInches = UltraConversion();//Update ultraInches & make it exit at some point.
				//System.out.println("From Turning(), New Distance in inches: " + ultraInches);
			if(ultraInches <= 8.0){
				//System.out.println("Distance Reached 8 inches");
			break;
			} //Break if new check reads less than 6
    	} //EO while	
    
			System.out.println("Out of Distance While loop," + ultraInches);
    	
		DoomCube.driveCartesian(0,0,0,0);
    	Timer.delay(1);//short pause
		
			//System.out.println("Last 6-8 inches");
			
    	//DoomCube.driveCartesian(.3,0.02,0,0); //travels eight inches 
		//Timer.delay(0.); // 
    	//.00567203* motor speed [.5]= delay ratio [.011334405]
		//delay ratio [.011334405] * distance in inches [8]= delay time[.090675241]
    	} //EO try    	
    	catch (Exception e) { //if ultra doesn't work
    		System.out.println("Exception Failed Ultra, ");
			System.out.println("calling backup HomeStretch");
		HomeStretch();
    	}//EO catch
    	
			
    	DoomCube.driveCartesian(0,0,0,0);//makes sure loop doesn't repeat
    	Timer.delay(.25);
    	
    	//DoomCube.driveCartesian(-.07,0,0,0);
    	//Timer.delay(0.25);
    	//Drives backwards
    	
    	//DoomCube.driveCartesian(0,0,0,0);
		//Timer.delay(0.25);
		   //final stop
	}
	
	
public void trackingTape(){
  //Boolean stopTurn = ((System.currentTimeMillis() - autoStartTime) < 1000);	
MainTracking:
	while (TrackingTargeted == false && isAutonomous()) {
		try{
			//get x and percentage values for vision
			getX();
			getCOGX();
			
			//Changes doubles into integers
			//int shape_x_int =(int) SHAPE_X_COORD.doubleValue();
			int cog_x_int = (int) COG_X.doubleValue();
			
			//Prints to the network
					//System.out.println(shape_x_int + ",");
					//System.out.println("COG_X" + cog_x_int);
			
			//On Target
			if (303 < cog_x_int && cog_x_int < 332){ //Was 330/370
				XonTarget = true;
				DoomCube.driveCartesian(0,0,0,0);
				Timer.delay(0.25);
				System.out.println("Target Acquired");
				} 
			
			//Turn Left     
	        else if(0 < cog_x_int && cog_x_int < 303 ) //TURN Left
				{
				XonTarget = false;
	        	DoomCube.driveCartesian(0,-.30,0,0);
	        	Timer.delay(.001);
	        	} 		
	         
	        //Turn Right             
	        else if(332 < cog_x_int && cog_x_int < 640 )//TURN Right
				{
	        	XonTarget = false;
	        	DoomCube.driveCartesian(0,.30,0,0);
	        	Timer.delay(.001);
				}
			
			//Spins back to find target if missed.
	        else if(cog_x_int == 0 && AutoL.get() == true){
	        	XonTarget = false;
	        	DoomCube.driveCartesian(0,-.30,0,0);
	        	Timer.delay(.001);
				}
				
	        else if(cog_x_int == 0 && AutoR.get() == true){
	        	XonTarget = false;
	        	DoomCube.driveCartesian(0,.30,0,0);
	        	Timer.delay(.001);
				}
				
			else {
				XonTarget = false;
				DoomCube.driveCartesian(0,-0.30,0,0);
				Timer.delay(.001);
				}
			
		if (XonTarget == true ){
			TrackingTargeted = true;
			DoomCube.driveCartesian(0,0,0,0);
			Timer.delay(.001);
			}
		}//EO Try
	    catch (PersistentException Ex) {
				System.out.println("MONKEYBUTTS!!!");
				System.out.println("Network Tables Not Working" + table_xy);//MSG: EXP
	        break MainTracking;
	    }//EO Catch
		
	}//EO While
		
	}//EO trackingTape
	
	public void getX(){ //Pulls our shape coordinates from the network
		try {
		SHAPE_X_COORD= table_xy.getNumber("/SHAPE_X_COORD", 0.0);	
		}//EO try		
   	catch(PersistentException Ex) {       		
   	   System.out.println("MONKEYBUTTS!!! X ");
       System.out.println("Network Tables Not Working" + table_xy);//MSG: EXP
      	}//EO catch
	}//EO getX
	
	public void getCOGX() { //Pulls the x value of the center of gravity from the network
		try {
			COG_X = table_xy.getNumber("/COG_X", 0.0);
		}//EO try		
	catch (PersistentException Ex) {
		System.out.println("MONKEYBUTTS!!! COG_X");
	    System.out.println("Network Tables Not Working" + table_xy);//MSG: EXP
	   }//EO catch
	}//EO getCOGX
	
}  






//DO NOT DELETE THIS BLOCK.
//You deployed the dog
//The dog absorbs the code
//*Dogsong loops in the distance as the programming team descends into madness*
/**
* ░░░░██░░████████░░██░░░░░░░░░░░░░░░░░░░░░░░░
* ░░██▒▒██▒▒▒▒▒▒▒▒██▒▒██░░░░░░░░░░░░░░░░░░░░░░
* ░░██▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒██░░░░░░░░░░░░░░░░░░░░░░
* ░░██▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒██░░░░░░░░░░░░░░░░░░░░
* ██▒▒▒▒██▒▒▒▒██▒▒▒▒▒▒▒▒▒▒██░░░░░░░░░░░░░░░░░░
* ██▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒████░░░░░░░░░░░░░░
* ██▒▒▒▒▒▒████▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒████████████░░
* ██▒▒██▒▒██▒▒▒▒██▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒██
* ██▒▒▒▒████████▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒██████░░
* ██▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒██░░░░░░
* 
*/
//Here on the programming team, we take our code VERY seriously.
