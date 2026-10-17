package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.commands.Command;
import org.firstinspires.ftc.teamcode.commands.CommandScheduler;
import org.firstinspires.ftc.teamcode.subsystems.Intake;

@TeleOp
public class IntakeTest extends OpMode {
    CommandScheduler commandScheduler;
    Intake intake;

    @Override
    public void init() {
        intake = new Intake(hardwareMap, telemetry);
        commandScheduler = CommandScheduler.getInstance();
    }

    @Override
    public void loop() {
        if (gamepad1.a){
            commandScheduler.schedule(intake.startIntake());


        }
        if (gamepad1.b){
            commandScheduler.schedule(intake.stopIntake());
        }

        commandScheduler.run();
    }
}
