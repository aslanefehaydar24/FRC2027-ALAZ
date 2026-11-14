// GyroIOSim.java
package frc.robot.subsystems;

import org.wpilib.math.geometry.Rotation2d;

/** Donanım ya da vendor kütüphanesi gerektirmeyen sanal gyro. */
public class GyroIOSim implements GyroIO {
    private double headingRad = 0.0;

    /** Her döngüde çağrılır: dönüş hızı (rad/s) ve döngü süresi (s). */
    public void update(double angularVelocityRadPerSec, double dtSeconds) {
        headingRad += angularVelocityRadPerSec * dtSeconds;
    }

    @Override
    public Rotation2d getRotation() {
        return new Rotation2d(headingRad);
    }

    @Override
    public void zero() {
        headingRad = 0.0;
    }
}
