package frc.robot.subsystems.Manipulator;

import static edu.wpi.first.wpilibj2.command.Commands.waitSeconds;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;
import edu.wpi.first.math.filter.Debouncer;
import edu.wpi.first.math.filter.LinearFilter;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Indexer extends SubsystemBase {
  private final CANSparkMax indexMotorLeft;
  private final CANSparkMax indexMotorRight;
  private final LinearFilter currentFilter = LinearFilter.movingAverage(10);
  private double filteredCurrentLeft;
  private double filteredCurrentRight;

  /** Create a new Gripper subsystem. */
  public Indexer() {
    indexMotorLeft = new CANSparkMax(17, MotorType.kBrushless);
    indexMotorRight = new CANSparkMax(18, MotorType.kBrushless);

    indexMotorLeft.enableVoltageCompensation(12.0);
    indexMotorLeft.setSmartCurrentLimit(25);
    indexMotorLeft.burnFlash();
    indexMotorRight.enableVoltageCompensation(12.0);
    indexMotorRight.setSmartCurrentLimit(25);
    indexMotorRight.burnFlash();
  }

  public void setRollers(double outputVolts) {
    indexMotorLeft.setVoltage(outputVolts);
    indexMotorRight.setVoltage(outputVolts);
  }

  public Command stop() {
    return runOnce(() -> setRollers(0));
  }



  public Command drop() {
    return runOnce(() -> setRollers(4));
  }

  public Command launch() {
    return runOnce(() -> setRollers(-2));
  }
  public Command outtake() {
    return runOnce(() -> setRollers(-5));
  }


  

  public double getFilteredCurrentLeft() {
    return filteredCurrentLeft;
  }
public double getFilteredCurrentRight() {
    return filteredCurrentRight;
  }

  public double getCurrentLeft() {
    return indexMotorLeft.getOutputCurrent();
  }
public double getCurrentRight() {
    return indexMotorRight.getOutputCurrent();
  }

  @Override
  public void periodic() {
    filteredCurrentLeft = currentFilter.calculate(getCurrentLeft());
    filteredCurrentRight = currentFilter.calculate(getCurrentRight());
  }
}
