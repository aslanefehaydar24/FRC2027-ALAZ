import wpilib

from subsystems.drive import drive

class ALAZ(wpilib.TimedRobot):

    def robotInit(self):
        print("Robot Başlatıldı")
        
        self.drive = drive()

    def robotPeriodic(self):
        pass

    def autonomousInit(self):
        print("Otonom mod başlatıldı")

    def autonomousPeriodic(self):
        pass

    def teleopInit(self):
        print("Manuel mod paşlatıldı")

        self.drive.forward()

    def teleopPeriodic(self):
        pass

    if __name__ == "__main__":
        wpilib.run(ALAZ)
