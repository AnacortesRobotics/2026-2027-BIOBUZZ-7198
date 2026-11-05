package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.commands.CommandScheduler;
import org.firstinspires.ftc.teamcode.commands.Trigger;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.TransferSystem;

@TeleOp
public class TransferTest extends OpMode {
    TransferSystem transfer;

    Intake intake;

    @Override
    public void init() {
        transfer = new TransferSystem(hardwareMap, telemetry);
        intake = new Intake(hardwareMap, telemetry);
        new Trigger(()->gamepad1.a).onTrue(transfer.loadLauncher());
        new Trigger(()->gamepad1.b).onTrue(transfer.stopTransfer());
        new Trigger(()->gamepad1.y).onTrue(intake.startIntake());
        new Trigger(()->gamepad1.x).onTrue(intake.stopIntake());


        CommandScheduler.getInstance().registerSubsystem(transfer);

    }

    @Override
    public void loop() {
        CommandScheduler.getInstance().run();
    }
}
