package org.firstinspires.ftc.teamcode.opmodes;

import android.hardware.HardwareBuffer;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.commands.CommandScheduler;
import org.firstinspires.ftc.teamcode.commands.InstantCommand;
import org.firstinspires.ftc.teamcode.commands.Trigger;
import org.firstinspires.ftc.teamcode.subsystems.Limelight;

@TeleOp
public class LimelightTest extends OpMode {
    Limelight limelight;
    CommandScheduler commandScheduler;

    @Override
    public void init() {
        limelight = new Limelight(hardwareMap, telemetry);
        commandScheduler = CommandScheduler.getInstance();
        limelight.update();
        new Trigger(()-> gamepad1.a).onTrue(new InstantCommand(()->limelight.screenshot()));
    }

    @Override
    public void loop() {
        limelight.detectTags();
        commandScheduler.run();
    }


}
