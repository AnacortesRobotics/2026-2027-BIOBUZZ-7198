package org.firstinspires.ftc.teamcode.controllers;

public class PIDController {


    private double kP = 0;
    private double kI = 0;
    private double kD = 0;
    private double target = 0;

    private double lastError = 0;
    private long lastTime = 0;
    private double integral = 0;

    private boolean isInverted = false;
    private boolean isRotate = false;

    public PIDController(double kP, double kI, double kD, boolean isRotate) {
        this.kP = kP;
        this.kI = kI;
        this.kD = kD;
        this.isRotate = isRotate;
    }

    public void setTarget(double target) {
        this.target = target;
    }

    public double getTarget() {
        return target;
    }

    public double update(double current) {
        double error = target - current;
        double deltaTime = System.currentTimeMillis() - lastTime;
        double derivative;

        if (lastTime != 0) {
            derivative = (error - lastError) / deltaTime;
            integral += error / deltaTime;
        } else {
            derivative = 0;
        }

        lastError = error;
        lastTime = System.currentTimeMillis();

        double rotateInvert = 1;

        if (isRotate) {
            if (target - current > 180 || target - current < -180) {
                rotateInvert = -1;
            }
        }

        return (isInverted ? -1 : 1) * rotateInvert * ((error * kP) + (integral * kI) + (derivative * kD));
    }

    public void stop() {
        lastError = 0;
        lastTime = 0;
        integral = 0;
    }

    public void setInverted(boolean invert) {
        isInverted = invert;
    }

    public void updatePIDCoefficients(double kP, double kI, double kD) {
        this.kP = kP;
        this.kI = kI;
        this.kD = kD;
    }
}
