package org.firstinspires.ftc.teamcode.pedro;

import com.pedropathing.follower.Follower;
import com.pedropathing.revhub.drivetrains.MecanumConfig;
import com.pedropathing.revhub.localizers.PinpointConfig;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import com.pedropathing.algorithm.Foresight;
import com.pedropathing.algorithm.ForesightConfig;
import com.pedropathing.controllers.Controller;
import com.pedropathing.math.Matrix;
import com.pedropathing.math.Vector2D;
import com.pedropathing.revhub.drivetrains.Mecanum;
import com.pedropathing.revhub.localizers.PinpointLocalizer;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;

public class Constants {
    public static MecanumConfig drivetrainConfig = new MecanumConfig(c -> {
        c.frontLeftName.set("frontLeft");
        c.frontRightName.set("frontRight");
        c.backLeftName.set("backLeft");
        c.backRightName.set("backRight");
        c.frontLeftDirection.set(DcMotorSimple.Direction.REVERSE);
        c.frontRightDirection.set(DcMotorSimple.Direction.FORWARD);
        c.backLeftDirection.set(DcMotorSimple.Direction.REVERSE);
        c.backRightDirection.set(DcMotorSimple.Direction.FORWARD);
    });

    public static PinpointConfig localizerConfig = new PinpointConfig(c -> {
        c.name.set("odo");
        c.podType.set(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_SWINGARM_POD);
        c.xPodOffset.set(7.1929390974870815);
        c.yPodOffset.set(4.568034795325572);
        c.xPodDirection.set(GoBildaPinpointDriver.EncoderDirection.FORWARD);
        c.yPodDirection.set(GoBildaPinpointDriver.EncoderDirection.REVERSED);
        c.globalDistanceUnit.set(DistanceUnit.INCH);
        c.offsetUnits.set(DistanceUnit.INCH);
        c.resetMode.set(PinpointLocalizer.ResetMode.RESET_AND_RECALIBRATE_IMU);
    });

    public static ForesightConfig foresightConfig = new ForesightConfig(
            c -> {
                Controller primaryTranslationalForward = Controller.proportional(0.23739130471951922);
                Controller secondaryTranslationalForward = Controller.proportional(0.08770974388137565);
                Controller primaryTranslationalLateral = Controller.proportional(0.2788015937343759);
                Controller secondaryTranslationalLateral = Controller.proportional(0.10300973916906393);

                c.forwardTranslational.set(Controller.piecewise(secondaryTranslationalForward).put(2.5, primaryTranslationalForward));
                c.strafeTranslational.set(Controller.piecewise(secondaryTranslationalLateral).put(2.5, primaryTranslationalLateral));

                c.coast.set(Controller.proportionalFeedforward(0.012860443968060704));
                c.brake.set(Controller.proportionalFeedforward(0.010931377372851598));

                c.headingFeedback.set(Controller.proportional(3.8355118755619007));
                c.headingBrakeCoefficients.set(Vector2D.cartesian(0.04735434801294019, 0.009542656412912272));

                c.linearBrakeCoefficients.set(Matrix.diag(0.05116167382093011, 0.05073912156983206));
                c.quadraticBrakeCoefficients.set(Matrix.diag(0.0022151056174584353, 0.0022537181713303852));

                c.maxAchievableForwardVelocity.set(79.58995389688114);
                c.maxAchievableStrafeVelocity.set(66.22522043424877);
                c.naturalForwardDeceleration.set(36.63409484158515);
                c.naturalStrafeDeceleration.set(51.61683900585899);
            }
    );

    public static ForesightConfig slowForesightConfig = new ForesightConfig(
            c -> {
                Controller primaryTranslationalForward = Controller.proportional(0.23739130471951922);
                Controller secondaryTranslationalForward = Controller.proportional(0.08770974388137565);
                Controller primaryTranslationalLateral = Controller.proportional(0.2788015937343759);
                Controller secondaryTranslationalLateral = Controller.proportional(0.10300973916906393);

                c.forwardTranslational.set(Controller.piecewise(secondaryTranslationalForward).put(2.5, primaryTranslationalForward));
                c.strafeTranslational.set(Controller.piecewise(secondaryTranslationalLateral).put(2.5, primaryTranslationalLateral));

                c.coast.set(Controller.proportionalFeedforward(0.012860443968060704));
                c.brake.set(Controller.proportionalFeedforward(0.010931377372851598));

                c.headingFeedback.set(Controller.proportional(3.8355118755619007));
                c.headingBrakeCoefficients.set(Vector2D.cartesian(0.04735434801294019, 0.009542656412912272));

                c.linearBrakeCoefficients.set(Matrix.diag(0.05116167382093011, 0.05073912156983206));
                c.quadraticBrakeCoefficients.set(Matrix.diag(0.0022151056174584353, 0.0022537181713303852));

                c.maxAchievableForwardVelocity.set(79.58995389688114);
                c.maxAchievableStrafeVelocity.set(66.22522043424877);
                c.naturalForwardDeceleration.set(36.63409484158515);
                c.naturalStrafeDeceleration.set(51.61683900585899);

                c.maxPathSpeed.set(.3);
            }
    );

    public static Follower create(HardwareMap h) {
        return new Follower(
                new PinpointLocalizer(h, localizerConfig),
                new Mecanum(h, drivetrainConfig),
                new Foresight(foresightConfig)
        );
    }

    public static Follower createNormal(HardwareMap h, CustomLocalizer localizer) {
        return new Follower(
                localizer,
                new Mecanum(h, drivetrainConfig),
                new Foresight(foresightConfig)
        );
    }
}