/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2020 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ShooterSubsystem extends SubsystemBase {

  Spark shooter;

  Encoder shooterEncoder;
  double ENCODER_PPR = 12;
  /**
   * Creates a new ShooterSubsystem.
   */
  public ShooterSubsystem() {
    shooter = new Spark(2);

    shooterEncoder = new Encoder(4, 5);
    shooterEncoder.setDistancePerPulse(1 / ENCODER_PPR);
  }

  public void shoot(double speed) {
    shooter.set(speed);
  }

  public void stopShooting() {
    shooter.stopMotor();
  }

  public double getShooterRPM() {
    return shooterEncoder.getRate();
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
