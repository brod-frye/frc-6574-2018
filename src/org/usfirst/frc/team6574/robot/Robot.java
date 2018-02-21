/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team6574.robot;

import org.usfirst.frc.team6574.robot.subsystems.Conveyor;
import org.usfirst.frc.team6574.robot.subsystems.DriveTrain;
import org.usfirst.frc.team6574.robot.subsystems.Intake;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends TimedRobot {
	
	//public static final ExampleSubsystem kExampleSubsystem = new ExampleSubsystem();
	
	public static final DriveTrain drive = new DriveTrain(0, 0, 0);
	public static final Intake intake = new Intake();
	public static final Conveyor conveyor = new Conveyor();
	//public static final Shooter shooter = new Shooter();
	
	public static OI m_oi;
	
	//boolean leftOwnSwitch;
	//boolean leftScale;
	//boolean leftOppositeSwitch;
	
	//Command m_autonomousCommand;
	//SendableChooser<Command> m_chooser = new SendableChooser<Command>();
	//SendableChooser<Boolean> control_chooser;
	
	boolean usingJoystick = true;
	
	boolean pressingShifter = false;
	boolean pressingIntake = false;
	boolean pressingShooter = false;
	boolean pressingSpin = false;
	boolean pressingJoystick = false;
	
	boolean oneJoystick = false;
	
	double intakeSpin = 0.0;
	double distanceMoved = 0;
	int autoStage = 0;
	Timer t = new Timer();
	int pos = 0;
	boolean switchOwnership = false;
	
	
	double getLeftY() {
		if (usingJoystick)
			return -m_oi.leftJoystick.getY();
		if (!usingJoystick)
			return -m_oi.controller.getRawAxis(1);
		return 0;
	}
	
	double getRightY() {
		if (usingJoystick)
			return -m_oi.rightJoystick.getY();
		if (!usingJoystick)
			return -m_oi.controller.getRawAxis(3);	
		return 0;
	}
	
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		m_oi = new OI();
		//m_chooser.addDefault("Default Auto", new AutoDefault());
		//m_chooser.addObject("Switch Auto", new AutoSwitch());
		//m_chooser.addObject("Scale Auto", new AutoScale());
		
		//control_chooser = new SendableChooser<Boolean>();
		//control_chooser.addObject("Use Controller", new Boolean(true));
		//control_chooser.addObject("Use Joystick", new Boolean(false));
		//SmartDashboard.putData("Control chooser", control_chooser);
		
		drive.calibrateGyro();
		
		//leftOwnSwitch = false;
		//leftScale = false;
		//leftOppositeSwitch = false;
		
	}
	
	//public double mapNumber(double num, double min1, double max1, double min2, double max2) {
	//	return (num - min1) * (max2 - min2) / (max1 - min1) + min2;
	//}

	/** 
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {
		autoStage = 0;
		distanceMoved = 0;
		//drive.calibrateGyro();
		drive.clearEncoders();
	}

	@Override
	public void disabledPeriodic() {
		//Scheduler.getInstance().run();
	}
	
	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * <p>You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {
		//m_autonomousCommand = m_chooser.getSelected();
		
		// GET FMS POSITION
		
		//t.reset();
		//t.start();
		
		pos = DriverStation.getInstance().getLocation();
		
		String positions = DriverStation.getInstance().getGameSpecificMessage();
		switchOwnership = (positions.charAt(0) == 'L' && pos == 1) || (positions.charAt(0) == 'L' && pos == 3);
		//leftOwnSwitch = positions.charAt(0) == 'L' ? true : false;
		//leftScale = positions.charAt(1) == 'L' ? true : false;
		//leftOppositeSwitch = positions.charAt(2) == 'L' ? true : false;
		
		/*
		 * String autoSelected = SmartDashboard.getString("Auto Selector",
		 * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
		 * = new MyAutoCommand(); break; case "Default Auto": default:
		 * autonomousCommand = new ExampleCommand(); break; }
		 */

		// schedule the autonomous command (example)
		//if (m_autonomousCommand != null) {
		//	m_autonomousCommand.start();
		//}
		
		drive.clearEncoders();

		distanceMoved = 0;
		//drive.calibrateGyro();
		drive.resetGyro();
		
		//if (!drive.isShifted()) {
		//	drive.engageShifter();
		//}
		
		autoStage = 0;
		
		oneJoystick = false;
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		//Scheduler.getInstance().run();
		
		//SmartDashboard.putNumber("Encoder distance", drive.getEncoderDist());
		//SmartDashboard.putNumber("Left encoder distance", drive.getLeftDistance());
		//SmartDashboard.putNumber("Right encoder distance", drive.getRightDistance());
		//SmartDashboard.putNumber("Left encoder velocity", drive.getLeftVelocity());
		//SmartDashboard.putNumber("Right encoder velocity", drive.getRightVelocity());
		
		//SmartDashboard.putNumber("Gyro angle", drive.getGyroAngle());
		
		/*if (t.get() < 3) {
			drive.set(-0.4);
		} else {
			drive.stop();
		}*/
		
		//(120.0 * (9.96/13.0)) * 12/10 = 12 feet 5 inches
		//when distanceMoved = 0.0736 * Math.abs(drive.getEncoderDist()) / 34
		
		
		if (!switchOwnership) {
			if (autoStage == 0) {
				if (distanceMoved < (120.0 * (9.96/13.0)) * 12/10) {
					distanceMoved = 0.0736 * Math.abs(drive.getEncoderDist()) / 34;
					SmartDashboard.putNumber("Encoder dist", distanceMoved);
					drive.set(0.5);
				} else {
					drive.stop();
					drive.resetGyro();
					drive.clearEncoders();
					autoStage = 1;
				}
			}
		} else {
			if (pos == 1) {
				if (autoStage == 0) {
					if (distanceMoved < (120.0 * (9.96/13.0)) * 12/10) {
						distanceMoved = 0.0736 * Math.abs(drive.getEncoderDist()) / 34;
						SmartDashboard.putNumber("Encoder dist", distanceMoved);
						drive.set(0.5);
					} else {
						drive.stop();
						drive.resetGyro();
						drive.clearEncoders();
						autoStage = 1;
					}
				} else if (autoStage == 1){
					if (drive.getGyroAngle() > -90) {
						drive.rotate(-0.3);
					} else {
						drive.stop();
						drive.resetGyro();
						autoStage = 2;
						t.reset();
					}
				} else if (autoStage == 2) {
					if (t.get() < 2) {
						drive.set(-0.4);
					} else {
						drive.stop();
					}
				} else if (autoStage == 3) {
					drive.stop();
					drive.clearEncoders();
				}
			} else if (pos == 3) {
				if (autoStage == 0) {
					if (distanceMoved < (120.0 * (9.96/13.0)) * 12/10) {
						distanceMoved = 0.0736 * Math.abs(drive.getEncoderDist()) / 34;
						SmartDashboard.putNumber("Encoder dist", distanceMoved);
						drive.set(0.5);
					} else {
						drive.stop();
						drive.resetGyro();
						drive.clearEncoders();
						autoStage = 1;
					}
				} else if (autoStage == 1){
					if (drive.getGyroAngle() < 90) {
						drive.rotate(0.3);
					} else {
						drive.stop();
						drive.resetGyro();
						autoStage = 2;
						t.reset();
					}
				} else if (autoStage == 2) {
					if (t.get() < 2) {
						drive.set(-0.4);
					} else {
						drive.stop();
					}
				} else if (autoStage == 3) {
					drive.stop();
					drive.clearEncoders();
				}
			}
		}
		
		// WORKING RIGHT SWITCH AUTO
		
		/*
		 * RIGHT SWITCH MID AUTO
		 */
		/*if (autoStage == 0) {
			if (distanceMoved < (120.0 * (9.96/13.0)) * 12/10 * 1/2) {
				distanceMoved = 0.0736 * Math.abs(drive.getEncoderDist()) / 34;
				SmartDashboard.putNumber("Encoder dist", distanceMoved);
				drive.set(0.5);
			} else {
				drive.stop();
				drive.resetGyro();
				autoStage = 1;
			}
		} else if (autoStage == 1) {
			
		}*/
		
		/*if (drive.getEncoderDist() < Constants.dist.AUTO_TEST) {
			drive.set(Constants.AUTO_SPEED);
		}*/
	}

	
	String controlSelected = "";
	@Override
	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		//if (m_autonomousCommand != null) {
		//	m_autonomousCommand.cancel();
		//}
		
		distanceMoved = 0;
		drive.clearEncoders();
		pressingShifter = false;
		pressingIntake = false;
		pressingShooter = false;
		pressingSpin = false;
		intakeSpin = 0.0;
		
		//drive.set(0);
		
		//controlSelected = SmartDashboard.getString("Controls", "Joystick");
		//usingJoystick = (Boolean)control_chooser.getSelected();
	}
   
	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
		//Scheduler.getInstance().run();
		

		SmartDashboard.putString("Value", controlSelected);

		
		//7900 "POSITION" per revolution
		//if (distanceMoved < )
		//if (intake.getDeployed()) {
			//intake.spin(intakeSpin);
			//conveyor.spin(intakeSpin);
		//}
		
		if (usingJoystick) {
			/*if (m_oi.leftJoystick.getRawButton(Controls.joystick.SHOOTER_SLOW_REVERSE)) {
				shooter.spin(-Constants.SHOOTER_SPEED_SLOW);
			} else if (m_oi.leftJoystick.getRawButton(Controls.joystick.SHOOTER_FAST_REVERSE)) {
				shooter.spin(-Constants.SHOOTER_SPEED_FAST);
			} else if (m_oi.leftJoystick.getRawButton(Controls.joystick.SHOOTER_SLOW_FORWARD)) {
				shooter.spin(Constants.SHOOTER_SPEED_SLOW);
			} else if (m_oi.leftJoystick.getRawButton(Controls.joystick.SHOOTER_FAST_FORWARD)) {
				shooter.spin(Constants.SHOOTER_SPEED_FAST);
			} else {
				shooter.stop();
			}*/
			
			if (m_oi.leftJoystick.getRawButton(Controls.joystick.CLEAR_ENCODERS)) {
				drive.clearEncoders();
			}
			if (m_oi.leftJoystick.getRawButton(Controls.joystick.RESET_GYRO)) {
				drive.resetGyro();
			}
			/*
			 * 
			 * SHOOTER
			if (Controls.USE_TOGGLE) {
				if (m_oi.leftJoystick.getRawButton(Controls.joystick.TOGGLE_SHOOTER)) {
					if (!pressingShooter) {
						if (shooter.getRaised()) {
							shooter.lower();
							intake.retract();
						} else {
							intake.deploy();
							shooter.raise();
						}
						pressingShooter = true;
					}
				} else {
					pressingShooter = false;
				}
			} else {
				if (m_oi.leftJoystick.getRawButton(Controls.joystick.RAISE_SHOOTER)) {
					intake.deploy();
					shooter.raise();
				} else if (m_oi.rightJoystick.getRawButton(Controls.joystick.LOWER_SHOOTER)) {
					shooter.lower();
					intake.retract();
				}
			}*/
			
			//if (!shooter.getRaised()) {
				if (Controls.USE_TOGGLE) {
					if (m_oi.leftJoystick.getRawButton(Controls.joystick.TOGGLE_INTAKE)) {
						if (!pressingIntake) {
							intake.toggleDeploy();
							pressingIntake = true;
						}
					} else {
						pressingIntake = false;
					}
				} else {
					if (m_oi.leftJoystick.getRawButton(Controls.joystick.DEPLOY_ARM)) {
						intake.deploy();
					} else if (m_oi.leftJoystick.getRawButton(Controls.joystick.RETRACT_ARM)) {
						intake.retract();
					}
				}
			//}
			
			if (Controls.USE_TOGGLE) {
				if (m_oi.leftJoystick.getRawButton(Controls.joystick.SHIFT)) {
					if (!pressingShifter) {
						drive.toggleShifter();
						pressingShifter = true;
					}
				} else {
					pressingShifter = false;
				}
			} else {
				if (m_oi.leftJoystick.getRawButton(Controls.joystick.ENGAGE_SHIFTER)) {
					drive.engageShifter();
				} else if (m_oi.leftJoystick.getRawButton(Controls.joystick.DISENGAGE_SHIFTER)) {
					drive.disengageShifter();
				}
			}
			
			if (m_oi.leftJoystick.getTrigger()) {
				if (!pressingJoystick) {
					oneJoystick = !oneJoystick;
					pressingJoystick = true;
				}
			} else {
				pressingJoystick = false;
			}
			
			//if (intake.getDeployed()) {
				if (m_oi.leftJoystick.getRawButton(Controls.joystick.ARM_FORWARD)) {
					intake.spin(0.5);
					conveyor.spin(1);
				} else if (m_oi.leftJoystick.getRawButton(Controls.joystick.ARM_BACKWARD)) {
					intake.spin(-0.5);
					conveyor.spin(-1);
				} else {
					intake.stop();
					conveyor.stop();
				}
			//}
			
			/*if (m_oi.leftJoystick.getRawButton(Controls.joystick.ARM_FORWARD)) {
				if (!pressingSpin) {
					if (intakeSpin == 0 || intakeSpin == -Constants.INTAKE_SPEED) {
						intakeSpin = Constants.INTAKE_SPEED;
					} else if (intakeSpin == Constants.INTAKE_SPEED) {
						intakeSpin = 0;
					}
					pressingSpin = true;
				}
			} else if (m_oi.leftJoystick.getRawButton(Controls.joystick.ARM_BACKWARD)) {
				if (!pressingSpin) {
					if (intakeSpin == 0 || intakeSpin == Constants.INTAKE_SPEED) {
						intakeSpin = -Constants.INTAKE_SPEED;
					} else if (intakeSpin == -Constants.INTAKE_SPEED) {
						intakeSpin = 0;
					}
					pressingSpin = true;
				}
			} else {
				pressingSpin = false;
			}*/
		} else {
			/*if (m_oi.controller.getRawButton(Controls.controller.SHOOTER_SLOW_REVERSE)) {
				shooter.spin(-Constants.SHOOTER_SPEED_SLOW);
			} else if (m_oi.controller.getRawButton(Controls.controller.SHOOTER_FAST_REVERSE)) {
				shooter.spin(-Constants.SHOOTER_SPEED_FAST);
			} else if (m_oi.controller.getRawButton(Controls.controller.SHOOTER_SLOW_FORWARD)) {
				shooter.spin(Constants.SHOOTER_SPEED_SLOW);
			} else if (m_oi.controller.getRawButton(Controls.controller.SHOOTER_FAST_FORWARD)) {
				shooter.spin(Constants.SHOOTER_SPEED_FAST);
			} else {
				shooter.stop();
			}*/
			
			if (m_oi.controller.getRawButton(Controls.controller.RESET_GYRO)) {
				drive.resetGyro();
			}
			
			if (m_oi.controller.getRawButton(Controls.controller.CLEAR_ENCODERS)) {
				drive.clearEncoders();
			}
			if (Controls.USE_TOGGLE) {
				if (m_oi.controller.getRawButton(Controls.controller.SHIFT)) {
					if (!pressingShifter) {
						drive.toggleShifter();
						pressingShifter = true;
					}
				} else {
					pressingShifter = false;
				}
			} else {
				if (m_oi.controller.getRawButton(Controls.controller.ENGAGE_SHIFTER)) {
					drive.engageShifter();
				} else if (m_oi.controller.getRawButton(Controls.controller.DISENGAGE_SHIFTER)) {
					drive.disengageShifter();
				}
			}
		}
		
		SmartDashboard.putNumber("Encoder distance", drive.getEncoderDist());
		SmartDashboard.putNumber("Left encoder distance", drive.getLeftDistance());
		SmartDashboard.putNumber("Right encoder distance", drive.getRightDistance());
		SmartDashboard.putNumber("Left encoder velocity", drive.getLeftVelocity());
		SmartDashboard.putNumber("Right encoder velocity", drive.getRightVelocity());
		
		SmartDashboard.putNumber("PID error", drive.getPIDController().getError());
		SmartDashboard.putNumber("PID Setpoint", drive.getPIDController().getSetpoint());
		
		SmartDashboard.putNumber("Gyro angle", drive.getGyroAngle());
		
		SmartDashboard.putBoolean("Left Trigger", m_oi.leftJoystick.getTrigger());
		
		//
		// Tank Drive
		//
		if (oneJoystick) {
			if (getLeftY() > Controls.joystick.DEAD_PERCENT) {
				drive.set(getLeftY() + Controls.joystick.DEAD_PERCENT);
			} else if (getLeftY() < -Controls.joystick.DEAD_PERCENT) {
				drive.set(getLeftY() + Controls.joystick.DEAD_PERCENT);
			} else if (m_oi.leftJoystick.getTwist() > Controls.joystick.DEAD_PERCENT) {
				drive.rotate(m_oi.leftJoystick.getTwist() - Controls.joystick.DEAD_PERCENT);
			} else if (m_oi.leftJoystick.getTwist() < -Controls.joystick.DEAD_PERCENT) {
				drive.rotate(m_oi.leftJoystick.getTwist() + Controls.joystick.DEAD_PERCENT);
			} else {
				drive.stop();
			}
		} else {
			double driveValue = 0;
			if (getLeftY() > Controls.joystick.DEAD_PERCENT) {
				driveValue = getLeftY() - Controls.joystick.DEAD_PERCENT;
				if (m_oi.leftJoystick.getRawButton(2) || m_oi.rightJoystick.getRawButton(2)) {
					driveValue = 1;
				}
				drive.frontLeft(driveValue);
				//drive.frontLeft(mapNumber(getLeftY() - Controls.joystick.DEAD_PERCENT, Controls.joystick.DEAD_PERCENT, 1.0 - Controls.joystick.DEAD_PERCENT, 0.0, 1.0));
				drive.backLeft(driveValue);
				//drive.backLeft(mapNumber(getLeftY() - Controls.joystick.DEAD_PERCENT, Controls.joystick.DEAD_PERCENT, 1.0 - Controls.joystick.DEAD_PERCENT, 0.0, 1.0));
			} else if (getLeftY() < -Controls.joystick.DEAD_PERCENT) {
				driveValue = getLeftY() + Controls.joystick.DEAD_PERCENT;
				if (m_oi.leftJoystick.getRawButton(2) || m_oi.rightJoystick.getRawButton(2)) {
					driveValue = -1;
				}
				drive.frontLeft(driveValue);
				//drive.frontLeft(mapNumber(getLeftY() + Controls.joystick.DEAD_PERCENT, -Controls.joystick.DEAD_PERCENT, -1.0 + Controls.joystick.DEAD_PERCENT, 0.0, -1.0));
				drive.backLeft(driveValue);
				//drive.backLeft(mapNumber(getLeftY() + Controls.joystick.DEAD_PERCENT, -Controls.joystick.DEAD_PERCENT, -1.0 + Controls.joystick.DEAD_PERCENT, 0.0, -1.0));
			} else {
				drive.stopLeft();
			}
			driveValue = 0;
			if (getRightY() > Controls.joystick.DEAD_PERCENT) {
				driveValue = getRightY() - Controls.joystick.DEAD_PERCENT;
				if (m_oi.leftJoystick.getRawButton(2) || m_oi.rightJoystick.getRawButton(2)) {
					driveValue = 1;
				}
				drive.frontRight(driveValue);
				//drive.frontRight(mapNumber(getRightY() - Controls.joystick.DEAD_PERCENT, Controls.joystick.DEAD_PERCENT, 1.0 - Controls.joystick.DEAD_PERCENT, 0.0, 1.0));
				drive.backRight(driveValue);
				//drive.backRight(mapNumber(getRightY() - Controls.joystick.DEAD_PERCENT, Controls.joystick.DEAD_PERCENT, 1.0 - Controls.joystick.DEAD_PERCENT, 0.0, 1.0));
			} else if (getRightY() < -Controls.joystick.DEAD_PERCENT) {
				driveValue = getRightY() + Controls.joystick.DEAD_PERCENT;
				if (m_oi.leftJoystick.getRawButton(2) || m_oi.rightJoystick.getRawButton(2)) {
					driveValue = -1;
				}
				drive.frontRight(driveValue);
				//drive.frontRight(mapNumber(getRightY() + Controls.joystick.DEAD_PERCENT, -Controls.joystick.DEAD_PERCENT, -1.0 + Controls.joystick.DEAD_PERCENT, 0.0, -1.0));
				drive.backRight(driveValue);
				//drive.backRight(mapNumber(getRightY() + Controls.joystick.DEAD_PERCENT, -Controls.joystick.DEAD_PERCENT, -1.0 + Controls.joystick.DEAD_PERCENT, 0.0, -1.0));
			} else {
				drive.stopRight();
			}
		}
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
		
	}
	
}
