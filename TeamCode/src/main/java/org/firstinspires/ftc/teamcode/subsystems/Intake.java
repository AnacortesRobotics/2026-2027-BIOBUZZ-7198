package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.*;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.commands.Command;
import org.firstinspires.ftc.teamcode.commands.InstantCommand;
import org.firstinspires.ftc.teamcode.commands.Subsystem;

public class Intake implements Subsystem {

    private DcMotor intake;
    private Servo flipper;

    private Telemetry telemetry;

    public boolean intakeUp = true;

    public Intake(HardwareMap hMap, Telemetry telemetry) {
        intake = hMap.get(DcMotor.class, "intake");
        intake.setDirection(DcMotorSimple.Direction.REVERSE);
        flipper = hMap.get(Servo.class, "intakeServo");
        this.telemetry = telemetry;
    }

    public Command startIntake() {
        return new InstantCommand(()->setIntakePower(.7), this);
    }

    public Command stopIntake() {
        return new InstantCommand(()->setIntakePower(0), this);
    }

    private void setIntakePower(double power) {
        intake.setPower(power);
    }

    public Command intakeDown() {
        return new InstantCommand(()->{flipper.setPosition(1); intakeUp = false;}, this);
    }

    public Command intakeUp() {
        return new InstantCommand(()->{flipper.setPosition(.6); intakeUp = true;}, this);
    }

    @Override
    public void periodic() {
        telemetry.addData("Intake Up", intakeUp);
    }
}
