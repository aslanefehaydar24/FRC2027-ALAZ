// EncoderIOSim.java
package frc.robot.subsystems;

/** Donanım gerektirmeyen sanal encoder. */
public class EncoderIOSim implements EncoderIO {
    private double distanceMeters = 0.0;
    private double velocityMetersPerSec = 0.0;

    /** Her döngüde çağrılır: tekerlek hızı (m/s) ve döngü süresi (s). */
    public void update(double wheelSpeedMetersPerSec, double dtSeconds) {
        velocityMetersPerSec = wheelSpeedMetersPerSec;
        distanceMeters += wheelSpeedMetersPerSec * dtSeconds;
    }

    @Override
    public double getDistanceMeters() {
        return distanceMeters;
    }

    @Override
    public double getVelocityMetersPerSec() {
        return velocityMetersPerSec;
    }

    @Override
    public void reset() {
        distanceMeters = 0.0;
        velocityMetersPerSec = 0.0;
    }
}
