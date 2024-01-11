// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.*;;

/** This is a demo program showing how to use Mecanum control with the MecanumDrive class. */
public class Robot extends TimedRobot {
  private static final int kFrontLeftChannel = 2;
  private static final int kRearLeftChannel = 1;
  private static final int kFrontRightChannel = 3;
  private static final int kRearRightChannel = 0;
  private final double speedMultiplier = 0.25; // this limits the speed for the motors
  private double deadband = 0.1; // Adjust this value to set the deadband size

  private final XboxController m_driverController = new XboxController(0);

  private MecanumDrive m_robotDrive;

  @Override
  public void robotInit() {
    PWMSparkMax frontLeft = new PWMSparkMax(kFrontLeftChannel);
    PWMSparkMax rearLeft = new PWMSparkMax(kRearLeftChannel);
    PWMSparkMax frontRight = new PWMSparkMax(kFrontRightChannel);
    PWMSparkMax rearRight = new PWMSparkMax(kRearRightChannel);

    SmartDashboard.putString("Code Version", "10-27 2:44pm");
   

    // Invert the right side motors.
    // You may need to change or remove this to match your robot.
    frontRight.setInverted(false);
    rearRight.setInverted(false);
    rearLeft.setInverted(false);

    m_robotDrive = new MecanumDrive(frontLeft, rearLeft, frontRight, rearRight);

  }

  @Override
  public void teleopPeriodic() {

    double xSpeed = -m_driverController.getLeftX();
    double ySpeed = -m_driverController.getLeftY();
    double zRotation = -m_driverController.getRightX();

    if (Math.abs(xSpeed) < deadband) {
      xSpeed = 0;
    } else {
      xSpeed = xSpeed * speedMultiplier;
    }
    if (Math.abs(ySpeed) < deadband) {
      ySpeed = 0;
    } else {
      ySpeed = ySpeed * speedMultiplier;
    }
    if (Math.abs(zRotation) < deadband) {
      zRotation = 0;
    }else {
      zRotation = zRotation * speedMultiplier;
    }

    SmartDashboard.putNumber("Get Left Y", m_driverController.getLeftY());
    SmartDashboard.putNumber("Get Left X", m_driverController.getLeftX());
    SmartDashboard.putNumber("Get Right X", m_driverController.getRightX());
    SmartDashboard.putNumber("Get Raw Axis 0", m_driverController.getRawAxis(0));
    SmartDashboard.putNumber("Get Raw Axis 1", m_driverController.getRawAxis(1));
   
    // Use the joystick X axis for forward movement, Y axis for lateral
    // movement, and Z axis for rotation.
    m_robotDrive.driveCartesian(xSpeed, ySpeed, zRotation);

  }
}
