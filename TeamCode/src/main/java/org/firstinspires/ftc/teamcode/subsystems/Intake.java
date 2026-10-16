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
    private CRServo lIntake;
    private CRServo rIntake;

    public Intake(HardwareMap hMap, Telemetry telemetry){
        intake = hMap.get(DcMotor.class, "intake");
        intake.setDirection(DcMotorSimple.Direction.REVERSE);
        lIntake = hMap.get(CRServo.class, "leftServo");
        lIntake.setDirection(DcMotorSimple.Direction.FORWARD);
        rIntake = hMap.get(CRServo.class, "rightServo");
        rIntake.setDirection(DcMotorSimple.Direction.REVERSE);


    }

    public Command startIntake(){
        return new InstantCommand(()->{setServoPower(.75); setIntakePower(.4);}, this);
    }


    public Command stopIntake(){
        return new InstantCommand(()->{setServoPower(0); setIntakePower(0);});
    }

    private void setIntakePower(double power) {
        intake.setPower(power);
    }

    private void setServoPower(double power) {
        lIntake.setPower(power);
        rIntake.setPower(power);
    }
}
