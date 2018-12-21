package org.usfirst.frc.team5683.robot.commands;

import org.usfirst.frc.team5683.robot.OI;
import org.usfirst.frc.team5683.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class Motor_Drive_Forward extends Command {

    public Motor_Drive_Forward() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.mc);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.mc.Motor_Forward();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return OI.j.getX() == 0;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
