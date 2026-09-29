class drive:

    def drive(self, speed: float, rotation:float):

        #Joystickden gelen girdiler yüzdesel olarak terminale yazdırılır
        
        if(speed == 0 and rotation == 0):
            print("Durdu")
        else:
            print(f"Hız: %{speed*100:+.0f} | Dönüş: %{rotation*100:+.0f}")
    