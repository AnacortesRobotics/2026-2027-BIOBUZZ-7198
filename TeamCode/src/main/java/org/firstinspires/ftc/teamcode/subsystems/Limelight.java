package org.firstinspires.ftc.teamcode.subsystems;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.LLStatus;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.vision.apriltag.AprilTagClusterDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

public class Limelight {
    private Telemetry telemetry;
    private ElapsedTime runtime;
    private Limelight3A limelight;
    private MecanumChassis chassis;
    private LLResult result;
    
    public Limelight(HardwareMap hMap, Telemetry telemetry, MecanumChassis chassis){
        runtime = new ElapsedTime();
        runtime.reset();
        this.telemetry = telemetry;
        this.chassis = chassis;
        limelight = hMap.get(Limelight3A.class, "limelight");
        limelight.setPollRateHz(100);
        limelight.pipelineSwitch(1);
        limelight.start();
    }

    public void update(){
        LLResult result = limelight.getLatestResult();
        if (result != null && result.isValid()) {
            double tx = result.getTx(); // How far left or right the target is (degrees)
            double ty = result.getTy(); // How far up or down the target is (degrees)
            double ta = result.getTa(); // How big the target looks (0%-100% of the image)

            telemetry.addData("Target X", tx);
            telemetry.addData("Target Y", ty);
            telemetry.addData("Target Area", ta);
        } else {
            telemetry.addData("Limelight", "No Targets");
        }
//        limelight.getLatestResult().getFiducialResults().

    }

//    private LLResultTypes.FiducialResult getAprilTag(){
//
//    }





}