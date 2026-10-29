package org.firstinspires.ftc.teamcode.opmodes;


import com.pedropathing.revhub.drivetrains.Mecanum;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import org.firstinspires.ftc.teamcode.commands.*;
import org.firstinspires.ftc.teamcode.pedro.Constants;
import com.pedropathing.api.PoseFactory;
import com.pedropathing.math.Pose;
import static com.pedropathing.api.Paths.*;

import org.firstinspires.ftc.teamcode.subsystems.ButterflyChassis;

import java.util.List;

@Autonomous
public class AutoTest extends OpMode {

    ButterflyChassis chassis;

    private final PoseFactory p = PoseFactory.degrees();

    private List<LynxModule> hubs;

    @Override
    public void init() {
        hubs = hardwareMap.getAll(LynxModule.class);
        for (LynxModule module : hubs) {
            module.setBulkCachingMode(LynxModule.BulkCachingMode.MANUAL);
        }

        chassis = new ButterflyChassis(hardwareMap, telemetry);
        CommandScheduler.getInstance().registerSubsystem(chassis);

        CommandScheduler.getInstance().schedule(
            new SequentialCommandGroup(
                new FollowCommand(line(p.of(-24 + chassis.ROBOT_WIDTH, -72 + chassis.ROBOT_LENGTH/2, 90), p.of(-24 + chassis.ROBOT_WIDTH, -48, 90)).constant(Math.PI/2),
                    chassis, new Mecanum(hardwareMap, Constants.drivetrainConfig), Constants.foresightConfig),
                new ParallelRaceCommandGroup(
                    new HoldCommand(p.of(-24 + chassis.ROBOT_WIDTH, -48, 90), chassis, new Mecanum(hardwareMap, Constants.drivetrainConfig), Constants.foresightConfig),
                    new WaitCommand(2000)),
                new FollowCommand(line(p.of(-24 + chassis.ROBOT_WIDTH, -48, 90), p.of(-48, -56, 240)).linear(p.of(-36, 48, 90), p.of(-48, -56, 240)),
                    chassis, new Mecanum(hardwareMap, Constants.drivetrainConfig), Constants.foresightConfig),
                new FollowCommand(path(line(p.of(-48, -56, 240), p.of(-62, -56, 240)).constant((4*Math.PI)/3),
                        line(p.of(-62, -56, 240), p.of(-72+ chassis.ROBOT_WIDTH/2, -58, 270)).linear((4*Math.PI)/3, (3*Math.PI/2))),
                        chassis, new Mecanum(hardwareMap, Constants.drivetrainConfig), Constants.slowForesightConfig)
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
        CommandScheduler.getInstance().endAll();
    }
}
