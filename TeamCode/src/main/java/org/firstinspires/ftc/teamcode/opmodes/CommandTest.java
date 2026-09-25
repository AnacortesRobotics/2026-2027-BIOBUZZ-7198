package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.hardware.rev.RevColorSensorV3;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.TouchSensor;
import org.firstinspires.ftc.teamcode.commands.CommandScheduler;
import org.firstinspires.ftc.teamcode.commands.FunctionalCommand;
import org.firstinspires.ftc.teamcode.subsystems.SubsystemTest;

@TeleOp
public class CommandTest extends OpMode {

    private CommandScheduler commandScheduler;
    private SubsystemTest subsystemTest;

    public DcMotor testMotor;
    public CRServo testServo;
    public RevColorSensorV3 colorSensor;
    public TouchSensor touchSensor;

    private long time;

    @Override
    public void init() {
        testMotor = hardwareMap.get(DcMotor.class, "testMotorLuke");
        testServo = hardwareMap.get(CRServo.class, "crServo");
        colorSensor = hardwareMap.get(RevColorSensorV3.class, "colorSens");
        touchSensor = hardwareMap.get(TouchSensor.class, "touchSens");

        commandScheduler = CommandScheduler.getInstance();
        subsystemTest = new SubsystemTest();

        commandScheduler.schedule(new FunctionalCommand(()->{testMotor.setPower(.3);},
                ()->{}, (interrupted)->{}, ()->true, subsystemTest));

        time = System.currentTimeMillis();

    }

    @Override
    public void loop() {
        if (System.currentTimeMillis() > time + 10000) {
            commandScheduler.schedule(new FunctionalCommand(()->{testMotor.setPower(0);}, ()->{if (touchSensor.isPressed()) {testMotor.setPower(-.3);}}, (interrupted)->{}, ()->false));
        }

        commandScheduler.run();
    }

    @Override
    public void stop() {
        commandScheduler.endAll();
    }
}
