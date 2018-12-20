package org.usfirst.frc.team5683.robot.subsystems;

import org.usfirst.frc.team5683.robot.RobotMap;
import org.usfirst.frc.team5683.robot.commands.MecanumDriveCommand;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.MecanumDrive;

/**
 *
 */
public class DriveTrain extends Subsystem {

    Victor frontL, frontR, rearL, rearR;
    public MecanumDrive DoomCube;
    public void initDriveTrain() {
    	frontL = new Victor(RobotMap.frontLeft);
    	frontR = new Victor(RobotMap.frontRight);
    	rearL = new Victor(RobotMap.rearLeft);
    	rearR = new Victor(RobotMap.rearRight);
    	
    	DoomCube = new MecanumDrive(frontR, frontL, rearL, rearR);
    }
    
    public void DriveMecanumGeneric(double y, double x, double z) {
    	DoomCube.driveCartesian(y, x, z, 0);
    }
    
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        setDefaultCommand(new MecanumDriveCommand());
    }
}

