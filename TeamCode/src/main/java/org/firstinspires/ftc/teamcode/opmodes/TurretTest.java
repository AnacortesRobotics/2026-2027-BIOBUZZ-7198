package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.commands.Command;
import org.firstinspires.ftc.teamcode.commands.CommandScheduler;
import org.firstinspires.ftc.teamcode.commands.Trigger;
import org.firstinspires.ftc.teamcode.subsystems.Launcher;

@TeleOp
public class TurretTest extends OpMode {
    CommandScheduler commandScheduler;
    Launcher launcher;


    @Override
    public void init() {
        launcher = new Launcher(hardwareMap, telemetry);

        commandScheduler = CommandScheduler.getInstance();

        commandScheduler.registerSubsystem(launcher);

        new Trigger(()->gamepad1.a).onTrue(launcher.setTarget(-90));
        new Trigger(()->gamepad1.b).onTrue(launcher.setTarget(90));
        new Trigger(()->gamepad1.x).onTrue(launcher.setTarget(45));
        new Trigger(()->gamepad1.y).onTrue(launcher.setTarget(0));
        commandScheduler.schedule(launcher.zeroTurret());
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

