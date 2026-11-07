package org.firstinspires.ftc.teamcode;

import com.pedropathing.math.Pose;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;

public class ValueTurnover {

    public static ValueTurnover instance;

    private static Pose currentPos = new Pose(0, 0, 0);

    public static synchronized ValueTurnover getInstance() {
        if (instance == null) {instance = new ValueTurnover();}
        return instance;
    }

    public void setCurrentPos(Pose currentPose) {
        currentPos = currentPose;
    }

    public void setCurrentPos(Pose2D currentPose) {
        currentPos = new Pose(currentPose.getX(DistanceUnit.INCH),
                currentPose.getY(DistanceUnit.INCH),
                currentPose.getHeading(AngleUnit.RADIANS));
    }

    public Pose getCurrentPos() {
        return currentPos;
    }
    
}
