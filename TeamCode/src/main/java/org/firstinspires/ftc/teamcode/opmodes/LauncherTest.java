package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.commands.CommandScheduler;
import org.firstinspires.ftc.teamcode.commands.Trigger;
import org.firstinspires.ftc.teamcode.subsystems.Launcher;


@TeleOp
public class LauncherTest extends OpMode {
    Launcher launcher;

    @Override
    public void init() {
        launcher = new Launcher(hardwareMap, telemetry);

        new Trigger(()->gamepad1.a).onTrue(launcher.fire());
        new Trigger(()->gamepad1.b).onTrue(launcher.stopMotor());

        CommandScheduler.getInstance().registerSubsystem(launcher);
    }

    @Override
    public void loop() {
        CommandScheduler.getInstance().run();
    }
}
