package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.teamcode.commands.Command;
import org.firstinspires.ftc.teamcode.commands.InstantCommand;
import org.firstinspires.ftc.teamcode.commands.Subsystem;

import java.util.Locale;

public class ButterflyChassis implements Subsystem {

    public final double ROBOT_WIDTH = 15.625;
    public final double ROBOT_LENGTH = 15.25;

    public DcMotor leftFront;
    public DcMotor rightFront;
    public DcMotor leftBack;
    public DcMotor rightBack;

    public Servo leftServo;
    public Servo rightServo;

    private double scaleSpeed = 1;
    private double maxSpeed = 1;

    private GoBildaPinpointDriver odo;
    private Telemetry telemetry;

    public ButterflyChassis(HardwareMap hMap, Telemetry telemetry) {

        leftFront = hMap.get(DcMotor.class, "frontLeft");
        leftFront.setDirection(DcMotor.Direction.REVERSE);
        rightFront = hMap.get(DcMotor.class, "frontRight");
        rightFront.setDirection(DcMotor.Direction.FORWARD);
        leftBack = hMap.get(DcMotor.class, "backLeft");
        leftBack.setDirection(DcMotor.Direction.REVERSE);
        rightBack = hMap.get(DcMotor.class, "backRight");
        rightBack.setDirection(DcMotor.Direction.FORWARD);

//        leftServo = hMap.get(Servo.class, "leftServo");
//        rightServo = hMap.get(Servo.class, "rightServo");

        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


        odo = hMap.get(GoBildaPinpointDriver.class, "odo");
        odo.setEncoderResolution(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_SWINGARM_POD);
        odo.setEncoderDirections(GoBildaPinpointDriver.EncoderDirection.REVERSED,
                GoBildaPinpointDriver.EncoderDirection.FORWARD);
        odo.setOffsets(3.14961, -5.94488, DistanceUnit.INCH);  // Offsets for test chassis are -4.5, -7.5

        odo.resetPosAndIMU();

        this.telemetry = telemetry;

//        updateOdo();
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

    public void mecanumDriveFieldCentric(double horizontal, double vertical, double rotate) {
        double heading = odo.getHeading(AngleUnit.RADIANS);
        double robotVert = Math.sin(heading) * horizontal + Math.cos(heading) * vertical;
        double robotHoriz = Math.cos(heading) * horizontal - Math.sin(heading) * vertical;
        mecanumDrive(-robotVert, robotHoriz, -rotate);
    }

    public void tankDrive(double right, double left) {
        double lfPower = left * scaleSpeed;
        double rfPower = right * scaleSpeed;
        double lbPower = left * scaleSpeed;
        double rbPower = right * scaleSpeed;
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

    public void tankDriveFieldCentric(double horizontal, double vertical, double rotate) {
        double magnitude = Math.sqrt(horizontal * horizontal + vertical * vertical) / 1.3;
        double heading = -odo.getHeading(AngleUnit.RADIANS);
        double targetHeading = Math.atan2(-horizontal, vertical);  // could be +h, -v -> needs to have direction reversed (1,-1)
        double headingDifference = magnitude == 0 ? 0 : heading - targetHeading;
        int direction = -Math.PI/2 < headingDifference && headingDifference < Math.PI/2 ? -1 : 1;
        tankDrive(direction * magnitude - direction * Math.sin(headingDifference) + rotate * direction,
                direction * magnitude + direction * Math.sin(headingDifference) - rotate * direction);
        telemetry.addData("heading", heading);
        telemetry.addData("target heading", targetHeading);
    }

    public void setServoPosition(double position) {
        leftServo.setPosition(position);
        rightServo.setPosition(position);
    }

    public Command dropWheels() {
        return new InstantCommand(()->setServoPosition(1), this);
    }

    public Command raiseWheels() {
        return new InstantCommand(()->setServoPosition(0), this);
    }

    public Pose2D getPose() {
        return odo.getPosition();
    }

    public void setMaxSpeed(double speed) {
        maxSpeed = speed;
    }

    public double getMaxSpeed() {
        return maxSpeed;
    }

    @Override
    public void periodic() {
        odo.update();
        String data = String.format(Locale.US, "{X: %.3f, Y: %.3f, H: %.3f}", odo.getPosition().getX(DistanceUnit.INCH), odo.getPosition().getY(DistanceUnit.INCH), odo.getPosition().getHeading(AngleUnit.DEGREES));
        telemetry.addData("True Position (Raw odo values)", data);
//        telemetry.addData("Target point", pidForward.getTarget() + ", " + pidHorizontal.getTarget() + ", " + pidRotate.getTarget());
        telemetry.addData("Max speed", maxSpeed);
    }

}
