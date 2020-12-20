/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2020 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class DriveSubsystem extends SubsystemBase {

  Spark leftDrive, rightDrive;
  Encoder leftEncoder, rightEncoder;
  DifferentialDrive drive;

  double WHEEL_DIAMETER = 4; // Wheel Diameter is 4 inches
  double PPR = 240; // 240 Pulses per Rotation
  /**
   * Creates a new DriveSubsystem.
   */
  public DriveSubsystem() {
    leftDrive = new Spark(0);
    rightDrive = new Spark(1);

    leftEncoder = new Encoder(0, 1);
    rightEncoder = new Encoder(2, 3);

    leftEncoder.setDistancePerPulse((Math.PI * WHEEL_DIAMETER) / PPR);
    rightEncoder.setDistancePerPulse((Math.PI * WHEEL_DIAMETER) / PPR);
    
    drive = new DifferentialDrive(leftDrive, rightDrive);

  }

  public double getAverageDistance() {
    double leftDistance = leftEncoder.getDistance();
    double rightDistance = rightEncoder.getDistance();
    
    return (leftDistance + rightDistance) / 2;
  }

  public void arcadeDrive(double forward, double rotation) {
    drive.arcadeDrive(forward, rotation);
  }

  public void stop() {
    drive.stopMotor();
  }
  
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}
