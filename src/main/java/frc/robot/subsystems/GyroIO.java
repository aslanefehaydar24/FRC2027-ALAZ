// GyroIO.java
package frc.robot.subsystems;

import org.wpilib.math.geometry.Rotation2d;

public interface GyroIO {
    Rotation2d getRotation();

    void zero();
}