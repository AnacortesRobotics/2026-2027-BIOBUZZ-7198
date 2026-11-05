package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.*;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.commands.Command;
import org.firstinspires.ftc.teamcode.commands.InstantCommand;
import org.firstinspires.ftc.teamcode.commands.Subsystem;

public class Intake implements Subsystem {
    private DcMotor intake;
    private Servo fliper;

    public Intake(HardwareMap hMap, Telemetry telemetry){
        intake = hMap.get(DcMotor.class, "intake");
        intake.setDirection(DcMotorSimple.Direction.REVERSE);
        fliper = hMap.get(Servo.class, "flipIntake");

    }

    public Command startIntake(){
        return new InstantCommand(()->{/*setServoPower(.75);*/ setIntakePower(.8);}, this);
    }


    public Command stopIntake(){
        return new InstantCommand(()->{ setIntakePower(0);});
    }

    private void setIntakePower(double power) {
        intake.setPower(power);
    }

    public Command intakeDown(){
        return new InstantCommand(()-> fliper.setPosition(1));//probably wrong position
    }

    public Command intakeup(){
        return new InstantCommand(()-> fliper.setPosition(0));//probably wrong position
    }
}
