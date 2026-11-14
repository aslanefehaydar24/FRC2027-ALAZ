// EncoderIO.java
package frc.robot.subsystems;

public interface EncoderIO {
    /** Toplam gidilen mesafe, metre. */
    double getDistanceMeters();

    /** Anlık hız, m/s. */
    double getVelocityMetersPerSec();

    void reset();
}
