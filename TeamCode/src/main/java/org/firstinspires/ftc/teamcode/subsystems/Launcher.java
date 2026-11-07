package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.hardware.rev.RevColorSensorV3;
import com.qualcomm.robotcore.hardware.*;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import org.firstinspires.ftc.teamcode.commands.*;

import java.util.function.IntSupplier;

public class Launcher implements Subsystem {

    DcMotorEx launcher;
    double position;
    Telemetry telemetry;
//    RevColorSensorV3 color;

    public Launcher(HardwareMap hMap, Telemetry telemetry){
        launcher = hMap.get(DcMotorEx.class, "launcher");
        launcher.setDirection(DcMotorSimple.Direction.FORWARD);
        launcher.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        launcher.setTargetPosition(0);
        launcher.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        launcher.setVelocityPIDFCoefficients(2.1, .104, 0, 8.6);// origional numbers P: 1.0176 I: 0.1076 D:0 F: 10.176
        launcher.setPositionPIDFCoefficients(31);
        this.telemetry = telemetry;
    }

    public Command goToPoint(IntSupplier point){
        return new FunctionalCommand(
                ()->{launcher.setTargetPosition(point.getAsInt());
                    launcher.setPower(0.9);},
                ()-> {},
                (interrupted)->{/*launcher.setPower(0);*/},
                ()-> launcher.isBusy(),
                this).setInterruptable(false);
    }

    public Command fixLauncher() {
        return goToPoint(()->launcher.getCurrentPosition() + 110);
    }

    public Command launch(){
        return goToPoint(()->
            launcher.getCurrentPosition() + (int)(103.4*2)
        );
    }

//    public void getColor() {
//        if (color.blue() >= color.red() && color.blue() >= color.green()){
//            foundColor = "blue";
//        }
//        color.green();
//        color.red();
//    }

    public Command positionOff(){
        return new InstantCommand(()->{launcher.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        launcher.setPower(0);});
    }

    public Command positionOn(){
        return new InstantCommand(()->{launcher.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            launcher.setPower(0);});
    }

    public Command resetMotor(){
        return new InstantCommand( ()-> {
            launcher.setPower(0);
            launcher.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            launcher.setTargetPosition(0);
            launcher.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        } );
    }

    public void telemetryData(){
        telemetry.addData("launcher pos", launcher.getCurrentPosition());
        telemetry.addData("launcher degrees", launcher.getCurrentPosition() + (int)(103.8*2) % 360);
    }

//    public Command fire(){
//        return new SequentialCommandGroup(new InstantCommand(()->launcher.setPower(1), this),/*probably will need changing*/
//                new InstantCommand(()->new Trigger(()->getDegrees() < 200 && getDegrees() > 180).
//                        onTrue(new InstantCommand(()->{launcher.setPower(.3); })), this));
//    }

    public Command stopMotor(){
        return new InstantCommand(()->launcher.setPower(0));
    }

//    private double getDegrees(){
//        return (360/145.1*launcher.getCurrentPosition());
//    }


    @Override
    public void periodic(){
        telemetryData();
    }
}
