package org.usfirst.frc.team5683.robot.subsystems;

import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Motor_Controller extends Subsystem {
	
	Victor v;
	
	public void Motor_Controller() {
		v = new Victor(0);
	}
	
	public void Motor_Forward() {
		v.setSpeed(1.0);
	}
	
	public void Motor_Reverse() {
		v.setSpeed(-1.0);
	}

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

