package org.firstinspires.ftc.teamcode.opmodes;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;
import org.firstinspires.ftc.teamcode.commands.CommandScheduler;
import org.firstinspires.ftc.teamcode.commands.InstantCommand;
import org.firstinspires.ftc.teamcode.commands.Trigger;
import org.firstinspires.ftc.teamcode.subsystems.ButterflyChassis;
import org.firstinspires.ftc.teamcode.subsystems.Intake;


@TeleOp
public class IntakeTest extends OpMode {

    Intake intake;
    ButterflyChassis chassis;

    @Override
    public void init() {
        intake = new Intake(hardwareMap, telemetry);
        chassis = new ButterflyChassis(hardwareMap, telemetry);
        new Trigger(()->gamepad1.a).onTrue(intake.startIntake());
        new Trigger(()->gamepad1.b).onTrue(intake.stopIntake());
        new Trigger(()->gamepad1.y).onTrue(intake.reverseIntake());

        chassis.setDefaultCommand(new InstantCommand(()->{chassis.mecanumDriveFieldCentric(
                gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.right_stick_x
        );}, chassis));
        CommandScheduler.getInstance().registerSubsystem(intake, chassis);

    }

    @Override
    public void loop() {
        CommandScheduler.getInstance().run();
    }
}
