package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.commands.*;

public class TransferSystem implements Subsystem {
    private Servo door;
    private CommandScheduler commandScheduler;

    public TransferSystem(HardwareMap hMap, Telemetry telemetry){
        door = hMap.get(Servo.class, "transferDoor");
    }

//    public Command loadChamber(){
//        return new SequentialCommandGroup(new InstantCommand(()->openDoor()), new WaitCommand(1000)/*not the right number*/, new InstantCommand(()->closeDoor()));
//    }

    private void openDoor(){
        door.setPosition(1);//not the right number but whatever the door fully open is
    }

    private void closeDoor(){
        door.setPosition(0);// not right number, whatever fully closed is
    }

}
