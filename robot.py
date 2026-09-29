import wpilib

from subsystems.drive import drive

class ALAZ(wpilib.TimedRobot):

    def robotInit(self):
        print("Robot Başlatıldı")
        
        #Joystick tanımlanır(0. kontrolcü)

        self.driver_controller = wpilib.XboxController(0)

        self.drive = drive()

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

        x_axis = self.driver_controller.getRightX()

        is_a_pressed = self.driver_controller.getAButton()

        #Joystick'in ölü bölgesi sıfırlanır

        if abs(y_axis) < 0.05:
            y_axis == 0.0
        
        if abs(x_axis) < 0.05:
            x_axis == 0.0

        #Temiz veriler drive.py içindeki drive fonksiyonuna yazıdrılır

        self.drive.drive(y_axis,x_axis)
        



    if __name__ == "__main__":
        wpilib.run(ALAZ)
