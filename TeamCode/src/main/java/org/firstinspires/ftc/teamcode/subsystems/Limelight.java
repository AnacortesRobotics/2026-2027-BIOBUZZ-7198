package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.hardware.limelightvision.LLFieldMap;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.commands.Subsystem;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.*;


import java.util.List;


public class Limelight implements Subsystem {
    private VisionPortal visionPortal;
    Telemetry telemetry;
    Limelight3A limelight;
    private LLResult result;
    private AprilTagProcessor aprilTag;

    double tx;
    double ty;

    public Limelight(HardwareMap hMap, Telemetry telemetry){
        aprilTag = new AprilTagProcessor.Builder().build();
        this.telemetry = telemetry;
        limelight = hMap.get(Limelight3A.class, "limelight");
        limelight.setPollRateHz(100);
        limelight.pipelineSwitch(0);
        limelight.start();
//        VisionPortal.Builder builder = new VisionPortal.Builder();

//        builder.setCamera(hMap.get(Limelight.class, "limelight"));

//
//        builder.addProcessor(aprilTag);
//
//        visionPortal = builder.build();


    }

    public void detectTags(){
//        List<LLResultTypes.FiducialResult> currentDetections = result.getFiducialResults(); //attempt at finding april tag clusters
//        for (LLResultTypes.FiducialResult detection : currentDetections) {
//            if (detection instanceof LLResultTypes.FiducialResult) {
//                AprilTagSingleDetection singleDet = (AprilTagSingleDetection) detection;
//                telemetry.addLine(String.format("\n==== (ID %d) %s", singleDet.id, singleDet.metadata.name));
//                telemetry.addLine(String.format("XYZ %6.1f %6.1f %6.1f  (inch)", detection.ftcPose.x, detection.ftcPose.y, detection.ftcPose.z));
//            } else {
//                AprilTagClusterDetection clusterDet = (AprilTagClusterDetection) detection;
//                telemetry.addLine(String.format("\n==== Tag Cluster (%s)", clusterDet.metadata.name));
//                telemetry.addLine(String.format("XYZ (inch)", detection.ftcPose.x, detection.ftcPose.y, detection.ftcPose.z));
//                }
//        }
        tx = result.getTx();
        ty = result.getTy();
//
//        telemetry.addData("target x", tx);
//        telemetry.addData("target y", ty);
    }

    public void update(){
        result = limelight.getLatestResult();
        tx = result.getTx();
        ty = result.getTy();
        telemetry.addData("x", tx);
        telemetry.addData("y", ty);
    }

    public void screenshot(){
        limelight.captureSnapshot("april_Tag_Testing");
    }

    @Override
    public void periodic(){
        update();
    }

}
