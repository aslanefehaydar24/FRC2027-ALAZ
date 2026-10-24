package frc.robot.subsystems;

public class Drive {

  public void drive(double speed, double rotation) {
    // Joystick'ten gelen girdiler yüzdesel olarak terminale yazdırılır
    if (speed == 0 && rotation == 0) {
      System.out.println("Durdu");
    } else {
      System.out.printf("Hız: %%%+.0f | Dönüş: %%%+.0f%n", speed * 100, rotation * 100);
    }
  }
}