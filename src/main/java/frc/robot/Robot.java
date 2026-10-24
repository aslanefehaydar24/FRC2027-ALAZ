package frc.robot;
//Paketler import edilir
import org.wpilib.math.util.MathUtil;
import org.wpilib.math.geometry.Pose2d;
import org.wpilib.math.geometry.Rotation2d;
import org.wpilib.framework.TimedRobot;
import org.wpilib.driverstation.XboxController;
import org.wpilib.smartdashboard.Field2d;
import org.wpilib.telemetry.Telemetry;

import frc.robot.subsystems.Drive;

public class Robot extends TimedRobot {

  // Sabitler (Python'daki "max_linear_speed" vb. burada tanımlanır)
  private static final double kDeadband = 0.05;
  private static final double kDt = 0.02;
  private static final double kMaxLinearSpeed = 3.0; // m/s
  private static final double kMaxAngularSpeed = Math.toRadians(180); // rad/s

  private final XboxController driverController = new XboxController(0);
  private final Drive drive = new Drive();
  private final Field2d field = new Field2d();

  // Sanal saha konumu
  private double simX = 8.0;
  private double simY = 4.0;
  private double simHeading = 0.0;

  public Robot() {
    System.out.println("Robot Başlatıldı");
  }

  private void updateSimField(double speed, double rotation) {
    double v = speed * kMaxLinearSpeed;
    double w = rotation * kMaxAngularSpeed;

    simHeading += w * kDt;
    simX += v * Math.cos(simHeading) * kDt;
    simY += v * Math.sin(simHeading) * kDt;

    field.setRobotPose(new Pose2d(simX, simY, new Rotation2d(simHeading)));
  }

  @Override
  public void robotPeriodic() {
    Telemetry.log("Field", field);
  }

  @Override
  public void autonomousInit() {
    System.out.println("Otonom mod başlatıldı");
  }

  @Override
  public void autonomousPeriodic() {}

  @Override
  public void teleopInit() {
    System.out.println("Manuel mod başlatıldı");
  }

  @Override
  public void teleopPeriodic() {
    // Ölü bölge hem uygulanır hem de değer yeniden ölçeklenir
    double yAxis = MathUtil.applyDeadband(-driverController.getLeftY(), kDeadband);
    double xAxis = MathUtil.applyDeadband(-driverController.getRightX(), kDeadband);

    boolean isAPressed = driverController.getAButton(); // şimdilik kullanılmıyor

    drive.drive(yAxis, xAxis);

    // Sadece simülasyonda sanal sahayı güncelle
    if (isSimulation()) {
      updateSimField(yAxis, xAxis);
    }
  }
}