package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.*;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.commands.*;

import static android.service.autofill.Validators.or;
import static com.sun.tools.doclint.Entity.or;

public class Launcher implements Subsystem {

    private CRServo turret;
    private Telemetry telemetry;
    private DcMotorEx turretPosition;
    private double turretPos;
    private DcMotor launcher;

    public Launcher(HardwareMap hMap, Telemetry telemetry){
        turret = hMap.get(CRServo.class, "turret");
        turretPosition = hMap.get(DcMotorEx.class, "turretIncoder");
        launcher = hMap.get(DcMotor.class, "launcher");
        launcher.setDirection(DcMotorSimple.Direction.FORWARD);
        this.telemetry = telemetry;
    }

    public Command zeroTurret(){
        return new InstantCommand(()->{turretPosition.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            turretPosition.setTargetPosition(0);
            turretPosition.setMode(DcMotor.RunMode.RUN_TO_POSITION);});
    }

    public Command setTarget(double aim) {

        return new FunctionalCommand(()->{}, ()->{
            if (turretPos + 20 > aim && turretPos - 20 < aim){turretPower(0);}
            else {turretPower(-(aim - turretPos)*.004);}}, (interrupted)->{},
            ()->false, this);

    }

    private void turretPower(double target){
        turret.setPower(target);
    }

    public void update(){
        double turretPOS = turretPosition.getCurrentPosition();
        turretPos = (turretPOS/8192)*360;

    }


    public void updateTelemetry(){
        telemetry.addData("turret angle", turretPos);

    }

    public Command fire(){
        return new SequentialCommandGroup(new InstantCommand(()->launcher.setPower(1)), new WaitCommand(500), new InstantCommand(()->launcher.setPower(0)));
    }




    @Override
    public void periodic() {
        update();
        updateTelemetry();
    }



}
