package org.usfirst.frc.team5683.robot.commands;

import org.usfirst.frc.team5683.robot.OI;
import org.usfirst.frc.team5683.robot.Robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.drive.MecanumDrive;

/**
 *
 */
public class MDFlipped extends Command {

    public MDFlipped() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.dt);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Joystick stick = OI.stick;
    	MecanumDrive DoomCube = Robot.dt.DoomCube;
    	
    	DoomCube.driveCartesian(((-stick.getX() * (-stick.getThrottle() + 1)/2)), 
				((stick.getTwist() * (-stick.getThrottle() + 1)/2)),
				((stick.getY()*(-stick.getThrottle()+ 1)/2)),0);
		Timer.delay(0.004);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
