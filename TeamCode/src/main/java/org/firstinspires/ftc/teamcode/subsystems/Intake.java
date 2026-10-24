package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.commands.Command;
import org.firstinspires.ftc.teamcode.commands.InstantCommand;
import org.firstinspires.ftc.teamcode.commands.Subsystem;

public class Intake implements Subsystem {
    private DcMotor intake;
    public Intake(HardwareMap hMap, Telemetry telemetry){
        intake = hMap.get(DcMotor.class, "intake");
        intake.setDirection(DcMotorSimple.Direction.REVERSE);

    }

    public Command startIntake(){
        return new InstantCommand(()->{setIntakePower(.6);}, this);
    }

    public Command reverseIntake(){
        return new InstantCommand(()->{setIntakePower(-.6);}, this);
    }


    public Command stopIntake(){
        return new InstantCommand(()->{setIntakePower(0);});
    }

    private void setIntakePower(double power) {
        intake.setPower(power);
    }

}
