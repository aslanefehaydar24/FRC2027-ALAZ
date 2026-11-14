// EncoderIOHardware.java
package frc.robot.subsystems;

import org.wpilib.hardware.rotation.Encoder;

/** Gerçek encoder. Robot gelince Robot.java'da EncoderIOSim yerine bu kullanılacak. */
public class EncoderIOHardware implements EncoderIO {
    private final Encoder encoder;

    /**
     * @param channelA DIO portu A
     * @param channelB DIO portu B
     * @param reversed ters monte edildiyse true
     * @param distancePerPulse bir darbede gidilen mesafe (metre)
     */
    public EncoderIOHardware(int channelA, int channelB, boolean reversed, double distancePerPulse) {
        encoder = new Encoder(channelA, channelB, reversed);
        encoder.setDistancePerPulse(distancePerPulse);
        encoder.reset();
    }

    @Override
    public double getDistanceMeters() {
        return encoder.getDistance();
    }

    @Override
    public double getVelocityMetersPerSec() {
        return encoder.getRate();
    }

    @Override
    public void reset() {
        encoder.reset();
    }
}
