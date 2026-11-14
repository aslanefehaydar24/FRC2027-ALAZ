package frc.robot;
//Paketler import edilir
import org.wpilib.math.util.MathUtil;
import org.wpilib.math.filter.SlewRateLimiter;
import org.wpilib.math.geometry.Pose2d;
import org.wpilib.math.geometry.Rotation2d;
import org.wpilib.framework.TimedRobot;
import org.wpilib.driverstation.XboxController;
import org.wpilib.smartdashboard.Field2d;
import org.wpilib.telemetry.Telemetry;

import frc.robot.subsystems.Drive;
import frc.robot.subsystems.EncoderIOSim;
import frc.robot.subsystems.GyroIOSim;

public class Robot extends TimedRobot {

  // Sabitler (Python'daki "max_linear_speed" vb. burada tanımlanır)
  private static final double kDeadband = 0.05;
  private static final double kDt = 0.02;
  private static final double kMaxLinearSpeed = 3.0; // m/s
  private static final double kMaxAngularSpeed = Math.toRadians(180); // rad/s

  // Slew rate limiter sabitleri (küçük sayı = daha yumuşak, büyük sayı = daha çevik)
  private static final double kSpeedSlewRate = 1.5; // 1/sn
  private static final double kRotSlewRate = 2.0; // 1/sn

  // Sadece simülasyon: joystick 1.0 iken tekerlek hızı (m/s)
  private static final double kSimMaxWheelSpeed = 3.0;

  private final XboxController driverController = new XboxController(0);
  private final Drive drive = new Drive();
  private final GyroIOSim gyro = new GyroIOSim();
  // Robot gelince EncoderIOHardware kullanılacak
  private final EncoderIOSim leftEncoder = new EncoderIOSim();
  private final EncoderIOSim rightEncoder = new EncoderIOSim();
  private final Field2d field = new Field2d();

  // Ani hız değişimlerini sınırlayan yumuşatıcılar
  private final SlewRateLimiter speedLimiter = new SlewRateLimiter(kSpeedSlewRate);
  private final SlewRateLimiter rotLimiter = new SlewRateLimiter(kRotSlewRate);

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
    gyro.update(w, kDt);

    // Arcade sürüş: dönüş iki tarafa ters işaretle eklenir, 1'i aşarsa oran bozulmadan sıkıştırılır
    double left = speed - rotation;
    double right = speed + rotation;
    double max = Math.max(1.0, Math.max(Math.abs(left), Math.abs(right)));
    leftEncoder.update(left / max * kSimMaxWheelSpeed, kDt);
    rightEncoder.update(right / max * kSimMaxWheelSpeed, kDt);

    simHeading += w * kDt;
    simX += v * Math.cos(simHeading) * kDt;
    simY += v * Math.sin(simHeading) * kDt;

    field.setRobotPose(new Pose2d(simX, simY, new Rotation2d(simHeading)));
  }

  @Override
  public void robotPeriodic() {
    Telemetry.log("Field", field);
    // Encoder değerlerini (mesafe, hız) panele gönderir
    Telemetry.log("Encoders/LeftDistanceM", leftEncoder.getDistanceMeters());
    Telemetry.log("Encoders/RightDistanceM", rightEncoder.getDistanceMeters());
    Telemetry.log("Encoders/LeftVelocityMps", leftEncoder.getVelocityMetersPerSec());
    Telemetry.log("Encoders/RightVelocityMps", rightEncoder.getVelocityMetersPerSec());
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
    // Mod başlarken limiter'ları sıfırla, robot sıçramasın
    speedLimiter.reset(0);
    rotLimiter.reset(0);
    // Her teleop başında encoder'ları sıfırla
    leftEncoder.reset();
    rightEncoder.reset();
  }

  @Override
  public void teleopPeriodic() {
    // Ölü bölge hem uygulanır hem de değer yeniden ölçeklenir
    double yAxis = MathUtil.applyDeadband(-driverController.getLeftY(), kDeadband);
    double xAxis = MathUtil.applyDeadband(-driverController.getRightX(), kDeadband);

    // Ani hız değişimlerini yumuşat (slew rate limiter)
    yAxis = speedLimiter.calculate(yAxis);
    xAxis = rotLimiter.calculate(xAxis);

    boolean isAPressed = driverController.getAButton();
    if (isAPressed) {
      System.out.println("Gyro: " + gyro.getRotation().getDegrees() + " derece");
    }
    if (driverController.getBButton()) {
      gyro.zero();
    }

    drive.drive(yAxis, xAxis);

    // Sadece simülasyonda sanal sahayı, gyro'yu ve encoder'ları güncelle
    if (isSimulation()) {
      updateSimField(yAxis, xAxis);
    }
  }
}
