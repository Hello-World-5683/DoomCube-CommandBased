/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team5683.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	// For example to map the left and right motors, you could define the
	// following variables to use with your drivetrain subsystem.
	// public static int leftMotor = 1;
	// public static int rightMotor = 2;

	// If you are using multiple modules, make sure to define both the port
	// number and the module. For example you with a rangefinder:
	// public static int rangefinderPort = 1;
	// public static int rangefinderModule = 1;
	
	public static final int frontLeft = 0;
	public static final int frontRight = 1;
	public static final int rearRight = 2;
	public static final int rearLeft = 3;
	
	public static final int wMotor = 4;
	
	public static final int AutoR = 0;
	public static final int AutoL = 1;
	
	public static final int Encoder1ChA = 2;
	public static final int Encoder1Chb = 3;
	public static final int Encoder2ChA = 4;
	public static final int Encoder2ChB = 5;
	public static final int Encoder3ChA = 6;
	public static final int Encoder3ChB = 7;
	public static final int Encoder4ChA = 8;
	public static final int Encoder4ChB = 9;
	
	public static final int Ultra = 0;
	public static final int Ultra2 = 1;
}
