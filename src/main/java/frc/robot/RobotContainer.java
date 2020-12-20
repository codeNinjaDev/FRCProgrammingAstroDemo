/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.XboxController.Button;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

/**
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {

  XboxController gamepad = new XboxController(0);

  // The robot's subsystems and commands are defined here...
  DriveSubsystem drive = new DriveSubsystem();
  ShooterSubsystem shooter = new ShooterSubsystem();
  IntakeSubsystem intake = new IntakeSubsystem();

  /**
   * The container for the robot.  Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Configure the button bindings

    drive.setDefaultCommand(new RunCommand(() -> {
      drive.arcadeDrive(gamepad.getY(Hand.kLeft), gamepad.getX(Hand.kRight));
    }, drive));

    intake.setDefaultCommand(new InstantCommand(() -> {
      intake.stopIntake();
      intake.raiseIntake();
    }, intake));

    shooter.setDefaultCommand(new InstantCommand(() -> shooter.stopShooting(), shooter));

    configureButtonBindings();
  }

  /**
   * Use this method to define your button->command mappings.  Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a
   * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {


    new JoystickButton(gamepad, Button.kBumperLeft.value).whileHeld(new InstantCommand(() -> intake.lowerIntake(), intake));
    new JoystickButton(gamepad, Button.kBumperRight.value).whenHeld(
      new SequentialCommandGroup(
        new InstantCommand(() -> shooter.shoot(1), shooter), // Start shooting
        new WaitUntilCommand(() -> shooter.getShooterRPM() >= 3000), // Spin up shooter until at desired speed
        new InstantCommand(() -> intake.intakeBalls(), intake), // Intake the balls into shooter
        new WaitCommand(2) // Shoot for two seconds
      )
    );
  }


  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return new SequentialCommandGroup(
      new InstantCommand(() -> drive.arcadeDrive(0.5, 0), drive), // Start driving at 50% speed
      new WaitUntilCommand(() -> drive.getAverageDistance() >= 60), // Wait until we hit 60 inches or 5 feet
      new InstantCommand(() -> drive.stop(), drive), // Stop driving
      new InstantCommand(() -> intake.lowerIntake(), intake), // Lower intake
      new InstantCommand(() -> shooter.shoot(1), shooter), // Start shooting full speed
      new WaitUntilCommand(() -> shooter.getShooterRPM() >= 3000), // Spin up shooter until at desired speed
      new InstantCommand(() -> intake.intakeBalls(), intake), // Intake Balls into shooter
      new WaitCommand(2), // Shoot for two seconds
      new InstantCommand(() -> intake.stopIntake(), intake), // Stop Intaking
      new InstantCommand(() -> shooter.stopShooting(), shooter), // Stop Shooting
      new InstantCommand(() -> intake.raiseIntake(), intake) // Raise Intake
    );
  }
}
