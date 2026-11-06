package org.firstinspires.ftc.teamcode.commands;

import com.pedropathing.algorithm.Foresight;
import com.pedropathing.algorithm.ForesightConfig;
import com.pedropathing.drivetrain.DrivePowers;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;
import com.pedropathing.paths.PathTracker;
import com.pedropathing.revhub.drivetrains.Mecanum;
import org.firstinspires.ftc.teamcode.subsystems.ButterflyChassis;

public class HoldCommand extends Command{

    private Pose holdPose;
    private long previousNanoTime = 0L;
    private ButterflyChassis chassis;

    private Mecanum drivetrain;
    private Foresight follower;

    public HoldCommand(Pose pose, ButterflyChassis chassis, Mecanum drivetrain, ForesightConfig config) {
        follower = new Foresight(config);
        holdPose = pose;
        this.chassis = chassis;
        addRequirements(chassis);

        this.drivetrain = drivetrain;
    }

    @Override
    public void init() {
        follower.reset();
    }

    @Override
    public void run() {
        long nanoTime = System.nanoTime();
        double deltaTime = 0;
        if (previousNanoTime != 0L) deltaTime = (nanoTime - previousNanoTime) / 1e9;

        DrivePowers powers = follower.calculateHold(drivetrain, holdPose, chassis.motionState, false, deltaTime);
        chassis.mecanumDrive(powers.forward(), -powers.strafe(), powers.turn());

        previousNanoTime = nanoTime;
    }

    @Override
    public boolean isFinished() {
        return false;
    }

}
