package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.commands.*;

public class TransferSystem implements Subsystem {

    CRServo transfer;

    public TransferSystem(HardwareMap hMap, Telemetry telemetry){
        transfer = hMap.get(CRServo.class, "transfer");
    }

    public Command loadLauncher(){
    return new InstantCommand(()->powerTransfer(1));
    }

    public Command reverseTransfer(){
        return new InstantCommand(()->powerTransfer(-1));
    }

    public Command stopTransfer(){
        return new InstantCommand(()->powerTransfer(0));
    }

    private void powerTransfer(double power){
        transfer.setPower(power);
    }

}
