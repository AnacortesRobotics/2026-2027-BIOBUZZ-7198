package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.commands.CommandScheduler;
import org.firstinspires.ftc.teamcode.commands.InstantCommand;
import org.firstinspires.ftc.teamcode.commands.Trigger;
import org.firstinspires.ftc.teamcode.subsystems.ButterflyChassis;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Launcher;
import org.firstinspires.ftc.teamcode.subsystems.TransferSystem;


@TeleOp
public class BiobuzzTeleOp extends OpMode {

    Intake intake;
    Launcher launcher;
    TransferSystem transfer;
    ButterflyChassis chassis;


    @Override
    public void init() {
        intake = new Intake(hardwareMap, telemetry);
        launcher = new Launcher(hardwareMap, telemetry);
        transfer = new TransferSystem(hardwareMap, telemetry);
        chassis = new ButterflyChassis(hardwareMap, telemetry);


        new Trigger(()->gamepad1.a).onTrue(intake.startIntake());
        new Trigger(()->gamepad1.b).onTrue(intake.stopIntake());
        new Trigger(()->gamepad2.a).onTrue(launcher.launch());
        new Trigger(()->gamepad2.b).onTrue(launcher.resetMotor());
        new Trigger(()->gamepad2.y).onTrue(transfer.loadLauncher());
        new Trigger(()->gamepad2.x).onTrue(transfer.stopTransfer());

        chassis.setDefaultCommand( new InstantCommand(()->{chassis.mecanumDriveFieldCentric(
                gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.right_stick_x);}, chassis));


        CommandScheduler.getInstance().registerSubsystem(intake, launcher, transfer, chassis);
    }

    @Override
    public void loop() {
        CommandScheduler.getInstance().run();
    }

    public void stop() {
        CommandScheduler.getInstance().endAll();
    }

}
