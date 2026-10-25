package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.commands.*;

public class TransferSystem implements Subsystem {
    DcMotor transfer;

    public TransferSystem(HardwareMap hMap, Telemetry telemetry){
        transfer = hMap.get(DcMotor.class, "transfer");

    }

    public Command loadLauncher(){
        return new SequentialCommandGroup(
                new InstantCommand(()->transfer.setPower(.5)),
                new WaitCommand(250),
                new InstantCommand(()-> transfer.setPower(0))
        );
    }


}
