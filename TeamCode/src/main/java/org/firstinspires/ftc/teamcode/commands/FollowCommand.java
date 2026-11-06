package org.firstinspires.ftc.teamcode.commands;

import com.pedropathing.algorithm.Algorithm;
import com.pedropathing.algorithm.Foresight;
import com.pedropathing.algorithm.ForesightConfig;
import com.pedropathing.drivetrain.DrivePowers;
import com.pedropathing.drivetrain.Drivetrain;
import com.pedropathing.follower.Follower;
import com.pedropathing.paths.Path;
import com.pedropathing.paths.PathTracker;
import com.pedropathing.revhub.drivetrains.Mecanum;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.subsystems.ButterflyChassis;

public class FollowCommand extends Command {

    private Path path;
    private PathTracker pathTracker;
    private long previousNanoTime = 0L;
    private ButterflyChassis chassis;

    private Mecanum drivetrain;
    private Foresight follower;

    public FollowCommand(Path path, ButterflyChassis chassis, Mecanum drivetrain, ForesightConfig config) {
        follower = new Foresight(config);
        this.path = path;
        this.chassis = chassis;
        addRequirements(chassis);

        this.drivetrain = drivetrain;
    }

    @Override
    public void init() {
        pathTracker = new PathTracker(path);
        follower.reset();
    }

    @Override
    public void run() {
        long nanoTime = System.nanoTime();
        double deltaTime = 0;
        if (previousNanoTime != 0L) deltaTime = (nanoTime - previousNanoTime) / 1e9;

        DrivePowers powers = follower.calculatePath(drivetrain, pathTracker, chassis.motionState, deltaTime);
        chassis.mecanumDrive(powers.forward(), -powers.strafe(), powers.turn());

        previousNanoTime = nanoTime;
    }

    @Override
    public boolean isFinished() {
        return pathTracker.done();
    }

}
