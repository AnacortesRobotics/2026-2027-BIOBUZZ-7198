package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.ValueTurnover;
import org.firstinspires.ftc.teamcode.commands.CommandScheduler;
import org.firstinspires.ftc.teamcode.commands.FunctionalCommand;
import org.firstinspires.ftc.teamcode.commands.InstantCommand;
import org.firstinspires.ftc.teamcode.commands.Trigger;
import org.firstinspires.ftc.teamcode.subsystems.ButterflyChassis;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Launcher;
import org.firstinspires.ftc.teamcode.subsystems.TransferSystem;

import java.util.List;


@TeleOp
public class BiobuzzTeleOp extends OpMode {

    Intake intake;
    Launcher launcher;
    TransferSystem transfer;
    ButterflyChassis chassis;

    private List<LynxModule> hubs;

    @Override
    public void init() {
        hubs = hardwareMap.getAll(LynxModule.class);
        for (LynxModule module : hubs) {
            module.setBulkCachingMode(LynxModule.BulkCachingMode.MANUAL);
        }

        intake = new Intake(hardwareMap, telemetry);
        launcher = new Launcher(hardwareMap, telemetry);
        transfer = new TransferSystem(hardwareMap, telemetry);
        chassis = new ButterflyChassis(hardwareMap, telemetry);

        CommandScheduler.getInstance().registerSubsystem(intake, launcher, transfer, chassis);

        new Trigger(()->gamepad1.a && !intake.intakeUp).onTrue(intake.startIntake());
        new Trigger(()->gamepad1.b).onTrue(intake.stopIntake());
        new Trigger(()->gamepad1.dpad_down).onTrue(intake.intakeDown());
        new Trigger(()->gamepad1.dpad_up).onTrue(intake.intakeUp());
        new Trigger(()->gamepad1.right_trigger_pressed).whileTrue(new FunctionalCommand(
                ()->{}, ()->chassis.setMaxSpeed(1 - gamepad1.right_trigger),
        (interrupted)->{}, ()->false
        )).onFalse(new InstantCommand(()->chassis.setMaxSpeed(1)));

        new Trigger(()->gamepad2.a).onTrue(launcher.launch());
        new Trigger(()->gamepad2.b).onTrue(launcher.resetMotor());
        new Trigger(()->gamepad2.x).onTrue(launcher.fixLauncher());
        new Trigger(()->gamepad2.dpad_up && !intake.intakeUp).onTrue(transfer.loadLauncher());
        new Trigger(()->gamepad2.dpad_down && !intake.intakeUp).onTrue(transfer.reverseTransfer());
        new Trigger(()->gamepad2.y).onTrue(transfer.stopTransfer());

        chassis.setDefaultCommand(new InstantCommand(()->{chassis.mecanumDriveFieldCentric(
                gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.right_stick_x);}, chassis));
    }

    @Override
    public void start() {
        chassis.setCurrentPose(ValueTurnover.getInstance().getCurrentPos());
    }

    @Override
    public void loop() {
        for (LynxModule module : hubs) {
            module.clearBulkCache();
        }
        CommandScheduler.getInstance().run();
    }

    public void stop() {
        CommandScheduler.getInstance().endAll();
    }

}
