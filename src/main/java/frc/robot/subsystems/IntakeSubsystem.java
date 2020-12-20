/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2020 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class IntakeSubsystem extends SubsystemBase {


  Spark intake;
  Solenoid intakePiston;

  /**
   * Creates a new IntakeSubsystem.
   */
  public IntakeSubsystem() {
    intake = new Spark(3);
    intakePiston = new Solenoid(0);
  }

  public void intakeBalls() {
    intake.set(0.5);
  }

  public void ejectBalls() {
    intake.set(-0.5);
  }

  public void raiseIntake() {
    intakePiston.set(false);
  }

  public void lowerIntake() {
    intakePiston.set(true);
  }

  public void stopIntake() {
    intake.stopMotor();
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
