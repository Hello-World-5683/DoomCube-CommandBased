package org.usfirst.frc.team5683.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Solenoid extends Subsystem {
	
	DoubleSolenoid s;
	
	public void Solenoid() {
		s = new DoubleSolenoid(0,1);
	}
	
	public void Solenoid_Open() {
		s.set(DoubleSolenoid.Value.kForward);
	}
	
	public void Solenoid_Close() {
		s.set(DoubleSolenoid.Value.kReverse);
	}
	
	public void Solenoid_Stop() {
		s.set(DoubleSolenoid.Value.kOff);
	}

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

