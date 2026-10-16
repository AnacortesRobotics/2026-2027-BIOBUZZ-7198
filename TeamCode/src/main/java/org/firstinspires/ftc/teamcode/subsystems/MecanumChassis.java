package org.firstinspires.ftc.teamcode.subsystems;

import android.view.SubMenu;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PIDCoefficients;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.teamcode.commands.Command;
import org.firstinspires.ftc.teamcode.commands.FunctionalCommand;
import org.firstinspires.ftc.teamcode.commands.Subsystem;
import org.firstinspires.ftc.teamcode.controllers.PIDController;

import java.util.Locale;

public class MecanumChassis implements Subsystem {

    public DcMotor leftFront;
    public DcMotor rightFront;
    public DcMotor leftBack;
    public DcMotor rightBack;
    private Telemetry telemetry;

    private double scaleSpeed = 1;
    private double maxSpeed = 1;
    private GoBildaPinpointDriver odo;

//    public PIDController pidForward = new PIDController(PIDCoefficients.XP, PIDCoefficients.XI, PIDCoefficients.XD, false);
//    public PIDController pidHorizontal = new PIDController(PIDCoefficients.YP, PIDCoefficients.YI, PIDCoefficients.YD, false);
//    public PIDController pidRotate = new PIDController(PIDCoefficients.RP, PIDCoefficients.RI, PIDCoefficients.RD, true);


    public MecanumChassis(HardwareMap hMap, Telemetry telemetry){
        leftFront = hMap.get(DcMotor.class, "frontLeft");
        leftFront.setDirection(DcMotor.Direction.REVERSE);
        rightFront = hMap.get(DcMotor.class, "frontRight");
        rightFront.setDirection(DcMotor.Direction.FORWARD);
        leftBack = hMap.get(DcMotor.class, "backLeft");
        leftBack.setDirection(DcMotor.Direction.REVERSE);
        rightBack = hMap.get(DcMotor.class, "backRight");
        rightBack.setDirection(DcMotor.Direction.FORWARD);

        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        this.telemetry = telemetry;
        odo = hMap.get(GoBildaPinpointDriver.class, "odo");
        odo.setEncoderResolution(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_SWINGARM_POD);
        odo.setEncoderDirections(GoBildaPinpointDriver.EncoderDirection.REVERSED,
                GoBildaPinpointDriver.EncoderDirection.FORWARD);
        odo.setOffsets(3.14961, -5.94488, DistanceUnit.INCH);// needs updating
        odo.resetPosAndIMU();
    }

    public void updateOdo() {
        odo.update();
        String data = String.format(Locale.US, "{X: %.3f, Y: %.3f, H: %.3f}", odo.getPosition().getX(DistanceUnit.INCH), odo.getPosition().getY(DistanceUnit.INCH), odo.getPosition().getHeading(AngleUnit.DEGREES));
        telemetry.addData("True Position (Raw odo values)", data);
    }

    public void setCurrentPose(Pose2D pose) {
        odo.setPosition(pose);
    }

    public void mecanumDrive(double forward, double strafe, double rotate) {
        double lfPower = (forward + strafe - rotate) * scaleSpeed;
        double rfPower = (forward - strafe + rotate) * scaleSpeed;
        double lbPower = (forward - strafe - rotate) * scaleSpeed;
        double rbPower = (forward + strafe + rotate) * scaleSpeed;
        double fastMotor = Math.max(Math.abs(lfPower),
                Math.max(Math.abs(rfPower),
                        Math.max(Math.abs(lbPower),
                                Math.abs(rbPower))));
        double scaleFactor = Math.min(maxSpeed / fastMotor, 1);
        leftFront.setPower(lfPower * scaleFactor);
        rightFront.setPower(rfPower * scaleFactor);
        leftBack.setPower(lbPower * scaleFactor);
        rightBack.setPower(rbPower * scaleFactor);
    }

    public void mecanumDriveFieldCentric(double vertical, double horizontal, double rotate) {
        double heading = -odo.getHeading(AngleUnit.RADIANS);
        double robotVert = Math.sin(heading) * horizontal + Math.cos(heading) * vertical;
        double robotHoriz = Math.cos(heading) * horizontal - Math.sin(heading) * vertical;
        mecanumDrive(robotVert, robotHoriz, rotate);
    }

//    public void updateTelemetry() {
//        telemetry.addData("Target point", pidForward.getTarget() + ", " + pidHorizontal.getTarget() + ", " + pidRotate.getTarget());
//        telemetry.addData("Max speed", maxSpeed);
//        trajectory.updateTelemetry();
//    }
//
//  public void stop() {
//        pidForward.stop();
//        pidHorizontal.stop();
//        pidRotate.stop();
//        mecanumDriveFieldCentric(0, 0, 0);
//}
//
//    public Command driveToPosition(Pose2D target) {
//        return new FunctionalCommand(
//                ()->setTarget(target),
//                this::update,
//                (interrupted)->stop(),
//                this::isAtTarget,
//                this).setName("Drive to position");
//    }
//
//    private void updateTrajectory() {
//        setTarget(trajectory.getLookaheadPoint(odo.getPosition(), 6));
//        mecanumDriveFieldCentric(pidForward.update(odo.getPosition().getX(DistanceUnit.INCH)),
//                pidHorizontal.update(odo.getPosition().getY(DistanceUnit.INCH)),
//                pidRotate.update(odo.getPosition().getHeading(AngleUnit.DEGREES)));
//    }
//
//    private void update() {
//        mecanumDriveFieldCentric(pidForward.update(odo.getPosition().getX(DistanceUnit.INCH)),
//                pidHorizontal.update(odo.getPosition().getY(DistanceUnit.INCH)),
//                pidRotate.update(odo.getPosition().getHeading(AngleUnit.DEGREES))
//        );
//    }
//
//    private void setTarget(Pose2D target) {
//        pidForward.setTarget(target.getX(DistanceUnit.INCH));
//        pidHorizontal.setTarget(target.getY(DistanceUnit.INCH));
//        pidRotate.setTarget(target.getHeading(AngleUnit.DEGREES));
//    }
//
//    private boolean isAtTarget() {
//        return Math.abs(odo.getPosition().getX(DistanceUnit.INCH) - pidForward.getTarget()) < .5 &&
//                Math.abs(odo.getPosition().getY(DistanceUnit.INCH) - pidHorizontal.getTarget()) < .5 &&
//                Math.abs(odo.getPosition().getHeading(AngleUnit.DEGREES) - pidRotate.getTarget()) < 2;
//    }




}
