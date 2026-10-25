package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.*;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.commands.*;

public class Launcher implements Subsystem {

    DcMotorEx launcher;
    double position;
    public Launcher(HardwareMap hMap, Telemetry telemetry){
        launcher = hMap.get(DcMotorEx.class, "launcher");
        launcher.setDirection(DcMotorSimple.Direction.FORWARD);//could be wrong
    }

    public Command fire(){
        return new SequentialCommandGroup(new InstantCommand(()->launcher.setPower(1), this),/*probably will need changing*/
                new InstantCommand(()->new Trigger(()->getDegrees() < 200 && getDegrees() > 180).
                        onTrue(new InstantCommand(()->{launcher.setPower(.3); })), this));
    }

    public Command stopMotor(){
        return new InstantCommand(()->launcher.setPower(0));
    }

    private double getDegrees(){
        return (360/145.1*launcher.getCurrentPosition()) % 360;
    }

}
