package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.commands.Subsystem;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.List;

public class Limelight implements Subsystem {
    Telemetry telemetry;
    Limelight3A limelight;
    private LLResult result;
    public Limelight(HardwareMap hMap, Telemetry telemetry){
        this.telemetry = telemetry;
        limelight = hMap.get(Limelight3A.class, "limelight");
        limelight.setPollRateHz(100);
        limelight.pipelineSwitch(1);
        limelight.start();


    }

    private LLResultTypes.FiducialResult getAprilTag(int aprilTag) {
        List<LLResultTypes.FiducialResult> lLresult = result.getFiducialResults();
        if(lLresult == null) return null;
        for (LLResultTypes.FiducialResult tag : lLresult ) {
            if (tag.getFiducialId() == aprilTag) {
                return tag;
            } else if (tag.getFiducialId() == aprilTag) {
                return tag;
            }
        }
        return null;
    }

    private void detectClusters(){

    }

}
