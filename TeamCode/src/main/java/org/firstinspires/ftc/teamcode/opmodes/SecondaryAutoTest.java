package org.firstinspires.ftc.teamcode.opmodes;


import com.pedropathing.api.PoseFactory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.pedropathing.follower.Follower;
import org.firstinspires.ftc.teamcode.pedro.Constants;

import static com.pedropathing.api.Paths.line;

@Disabled
@Autonomous
public class SecondaryAutoTest extends OpMode {

    private Follower follower;
    private final PoseFactory p = PoseFactory.degrees();

    @Override
    public void init() {
        follower = Constants.create(hardwareMap);
        follower.setPose(p.of(-24, -60, 0));
        follower.follow(line(p.of(-24, -60, 0), p.of(-36, -48, 0)).constant(60));
    }

    @Override
    public void loop() {
        follower.update();
    }
}
