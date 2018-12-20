package org.usfirst.frc.team5683.robot.commands;

import org.usfirst.frc.team5683.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class Piston_Reverse extends Command {

    public Piston_Reverse() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.sol);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.sol.Solenoid_Close();
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
    	Robot.sol.Solenoid_Stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
