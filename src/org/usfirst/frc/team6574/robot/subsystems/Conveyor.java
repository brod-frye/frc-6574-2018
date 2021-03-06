package org.usfirst.frc.team6574.robot.subsystems;

import org.usfirst.frc.team6574.robot.RobotMap;

import edu.wpi.first.wpilibj.Spark;

public class Conveyor {

	Spark leftRoller;
	Spark rightRoller;
	
	//public DigitalInput closedLimit;
	//public DigitalInput openLimit;
	//Victor arm;
	Spark leftBooper;
	Spark rightBooper;
	
	/**
	 * Constructs a conveyor subsystem for the robot.
	 */
	public Conveyor() {
		//leftRoller = new Spark(RobotMap.conveyor.LEFT_PWM_NUM);
		//rightRoller = new Spark(RobotMap.conveyor.RIGHT_PWM_NUM);
		
		//closedLimit = new DigitalInput(0);
		//openLimit = new DigitalInput(1);
		//arm = new Victor(8);
		leftBooper = new Spark(RobotMap.conveyor.LEFT_BOOPER_PWM_NUM);
		rightBooper = new Spark(RobotMap.conveyor.RIGHT_BOOPER_PWM_NUM);
		
	}
	
	/**
	 * Spins the conveyor's rollers.
	 * 
	 * @param speed	 a double containing the percent output
	 */
	/*public void spin(double speed) {
		leftRoller.set(-speed);
		rightRoller.set(speed);
	}*/

	/**
	 * Stops all of the intake's motors.
	 */
	/*public void stop() {
		leftRoller.stopMotor();
		rightRoller.stopMotor();
	}*/
	
	public void boop() {
		leftBooper.set(1);
		rightBooper.set(1);
	}
	
	public void stopBoop() {
		leftBooper.stopMotor();
		rightBooper.stopMotor();
	}
	/*
	public double getBoop() {
		return booper.get();
	}*/
	
	/*
	public void closeArm() {
		arm.set(-0.25);
	}
	
	public void openArm() {
		arm.set(0.25);
	}
	
	public void stopArm() {
		arm.stopMotor();
	}
	
	public boolean isArmClosing() {
		return arm.get() == -0.25;
	}
	
	public boolean isArmOpening() {
		return arm.get() == 0.25;
	}
	
	public boolean isArmStopped() {
		return arm.get() == 0;
	}
	*/
}
