package org.usfirst.frc.team5683.robot.commands;

import org.usfirst.frc.team5683.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class Baseline extends Command {

    public Baseline() {
        // Use requires() here to declare subsystem dependencies
       requires(Robot.dt);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	//Drives to Baseline
	    Robot.dt.DriveMecanumGeneric(.70,0.02,0.);
		Timer.delay(.755);
		Robot.dt.DriveMecanumGeneric(0.,0.,0.);
		Timer.delay(0.25);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
