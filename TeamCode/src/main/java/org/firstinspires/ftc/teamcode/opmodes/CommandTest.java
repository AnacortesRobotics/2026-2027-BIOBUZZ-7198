package org.firstinspires.ftc.teamcode.opmodes;

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

@TeleOp
public class CommandTest extends OpMode {

    private CommandScheduler commandScheduler;
    private ButterflyChassis butterflyChassis;

    public DcMotor testMotor;
    public CRServo testServo;
    public RevColorSensorV3 colorSensor;
    public TouchSensor touchSensor;

    private Trigger gamepadTest;

    @Override
    public void init() {
//        testMotor = hardwareMap.get(DcMotor.class, "testMotorLuke");
//        testServo = hardwareMap.get(CRServo.class, "crServo");
//        colorSensor = hardwareMap.get(RevColorSensorV3.class, "colorSens");
//        touchSensor = hardwareMap.get(TouchSensor.class, "touchSens");

        commandScheduler = CommandScheduler.getInstance();
        butterflyChassis = new ButterflyChassis(hardwareMap, telemetry);
        commandScheduler.registerSubsystem(butterflyChassis);

        butterflyChassis.setDefaultCommand(new InstantCommand(()->{butterflyChassis.mecanumDriveFieldCentric(
                gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.right_stick_x
        );}, butterflyChassis));
        new Trigger(()->gamepad1.right_trigger_pressed).whileTrue(new FunctionalCommand(
                ()->{}, ()->{butterflyChassis.setMaxSpeed(1 - gamepad1.right_trigger)
            ;}, (interrupted)->{}, ()->false
        )).onFalse(new InstantCommand(()->{butterflyChassis.setMaxSpeed(1);}));

    }

    @Override
    public void loop() {

        commandScheduler.run();
    }

    @Override
    public void stop() {
        commandScheduler.endAll();
    }
}
