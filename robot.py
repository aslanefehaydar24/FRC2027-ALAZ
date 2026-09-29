import math
import wpilib

from wpimath.geometry import Pose2d, Rotation2d
from subsystems.drive import drive

class ALAZ(wpilib.TimedRobot):

    def robotInit(self):
        print("Robot Başlatıldı")
        
        #Joystick tanımlanır(0. kontrolcü)

        self.driver_controller = wpilib.XboxController(0)
        self.drive = drive()

        #Robot sanal saha oluşturma
        self.field = wpilib.Field2d()
        wpilib.SmartDashboard.putData("Field", self.field)

        #robot sanal saha konumlandırma
        self.sim_x = 8.0
        self.sim_y = 4.0 
        self.sim_heading = 0.0


    #Robot sanal saha hareket ettirme
    def update_sim_field(self, speed: float, rotation: float):

        dt = 0.02

        max_linear_speed = 3.0
        max_angular_speed = math.radians(180)

        v = speed * max_linear_speed
        w = rotation * max_angular_speed

        self.sim_heading += w * dt
        self.sim_x += v * math.cos(self.sim_heading) * dt
        self.sim_y += v * math.sin(self.sim_heading) * dt

        self.field.setRobotPose(
            Pose2d(self.sim_x, self.sim_y, Rotation2d(self.sim_heading))
        )

    def robotPeriodic(self):
        pass

    def autonomousInit(self):
        print("Otonom mod başlatıldı")

    def autonomousPeriodic(self):
        pass

    def teleopInit(self):
        print("Manuel mod paşlatıldı")


    def teleopPeriodic(self):
        
        # Joysstickden genel girdiler y_axis ve x_axise yazırılır

        y_axis = -self.driver_controller.getLeftY()

        x_axis = -self.driver_controller.getRightX()

        is_a_pressed = self.driver_controller.getAButton()

        #Joystick'in ölü bölgesi sıfırlanır

        if abs(y_axis) < 0.05:
            y_axis == 0.0
        
        if abs(x_axis) < 0.05:
            x_axis == 0.0

        #Temiz veriler drive.py içindeki drive fonksiyonuna yazıdrılır

        self.drive.drive(y_axis,x_axis)

        #Verileri simulatöre aktarıyor
        self.update_sim_field(y_axis,x_axis)
        



    if __name__ == "__main__":
        wpilib.run(ALAZ)
