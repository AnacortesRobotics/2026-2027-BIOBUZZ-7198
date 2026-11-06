package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.hardware.rev.RevColorSensorV3;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.TouchSensor;
import org.firstinspires.ftc.teamcode.commands.CommandScheduler;
import org.firstinspires.ftc.teamcode.commands.FunctionalCommand;
import org.firstinspires.ftc.teamcode.commands.InstantCommand;
import org.firstinspires.ftc.teamcode.commands.Trigger;
import org.firstinspires.ftc.teamcode.subsystems.ButterflyChassis;
import org.firstinspires.ftc.teamcode.subsystems.SubsystemTest;

import java.util.List;

@TeleOp
public class CommandTest extends OpMode {

    private ButterflyChassis butterflyChassis;

    public DcMotor testMotor;
    public CRServo testServo;
    public RevColorSensorV3 colorSensor;
    public TouchSensor touchSensor;

    private Trigger gamepadTest;

    private List<LynxModule> hubs;

    @Override
    public void init() {

        hubs = hardwareMap.getAll(LynxModule.class);
        for (LynxModule module : hubs) {
            module.setBulkCachingMode(LynxModule.BulkCachingMode.MANUAL);
        }

//        testMotor = hardwareMap.get(DcMotor.class, "testMotorLuke");
//        testServo = hardwareMap.get(CRServo.class, "crServo");
//        colorSensor = hardwareMap.get(RevColorSensorV3.class, "colorSens");
//        touchSensor = hardwareMap.get(TouchSensor.class, "touchSens");


        butterflyChassis = new ButterflyChassis(hardwareMap, telemetry);
        CommandScheduler.getInstance().registerSubsystem(butterflyChassis);

        butterflyChassis.setDefaultCommand(new InstantCommand(()->{
            if (butterflyChassis.isTankDrive) {
                butterflyChassis.mecanumDriveFieldCentric(
                    gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.right_stick_x);
            } else {
                butterflyChassis.tankDriveFieldCentric(
                        gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.right_stick_x);
            }}, butterflyChassis));
        new Trigger(()->gamepad1.right_trigger_pressed).whileTrue(new FunctionalCommand(
                ()->{}, ()->{butterflyChassis.setMaxSpeed(1 - gamepad1.right_trigger)
            ;}, (interrupted)->{}, ()->false
        )).onFalse(new InstantCommand(()->{butterflyChassis.setMaxSpeed(1);}));

    }

    @Override
    public void loop() {
        for (LynxModule module : hubs) {
            module.clearBulkCache();
        }

        CommandScheduler.getInstance().run();
    }

    @Override
    public void stop() {
        CommandScheduler.getInstance().endAll();
    }
}
