package org.firstinspires.ftc.teamcode.opmodes;

import com.pedropathing.api.PoseFactory;
import com.pedropathing.revhub.drivetrains.Mecanum;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import org.firstinspires.ftc.teamcode.ValueTurnover;
import org.firstinspires.ftc.teamcode.commands.*;
import org.firstinspires.ftc.teamcode.pedro.Constants;
import org.firstinspires.ftc.teamcode.subsystems.ButterflyChassis;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Launcher;
import org.firstinspires.ftc.teamcode.subsystems.TransferSystem;

import java.util.List;

import static com.pedropathing.api.Paths.curve;

public class BiobuzzAutoOne extends OpMode {

    ButterflyChassis chassis;
    Intake intake;
    Launcher launcher;
    TransferSystem transfer;

    private final PoseFactory p = PoseFactory.degrees();

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

        CommandScheduler.getInstance().schedule(
            new SequentialCommandGroup(
                new RepeatCommand(
                    new SequentialCommandGroup(
                        launcher.launch(),
                        transfer.loadLauncher(),
                        new WaitCommand(1500),
                        transfer.stopTransfer()
                ), 3),
                launcher.launch(),
                new WaitCommand(500),
                new FollowCommand(curve(p.of(-24 + chassis.ROBOT_WIDTH, -72 + chassis.ROBOT_LENGTH/2, 90),
                    p.of(-48, -48 ,45), p.of(-60, 24, 0)).linear(Math.PI/2, 0),
                    chassis, new Mecanum(hardwareMap, Constants.drivetrainConfig), Constants.foresightConfig),
                new HoldCommand(p.of(-60, 24, 0),
                    chassis, new Mecanum(hardwareMap, Constants.drivetrainConfig), Constants.foresightConfig)
            )
        );

    }

    @Override
    public void start() {
        chassis.setCurrentPose(p.of(-24 + chassis.ROBOT_WIDTH, -72 + chassis.ROBOT_LENGTH/2, 90));
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
        ValueTurnover.getInstance().setCurrentPos(chassis.getPose());
        CommandScheduler.getInstance().endAll();
    }

}
