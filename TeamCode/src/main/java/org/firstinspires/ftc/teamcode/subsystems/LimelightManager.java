package org.firstinspires.ftc.teamcode.subsystems;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.LLStatus;
import com.qualcomm.hardware.limelightvision.Limelight3A;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;

import java.util.List;

/*
Courtesy of Benny Nguyen, Software Lead of 23796, class of OA28
I don't know what I'm doing, if it wasn't obvious already.

And yes, I did make all of this by myself with no help whatsoever.
You can tell by the terrible code scattered across this. Might as well be considered
a war crime to be honest.

*/
public final class LimelightManager {

    private Limelight3A hardware;
    private Telemetry telemetry;

    // Initializes the variables necessary for the lime light camera.

    public LimelightManager(Limelight3A hardware, Telemetry telemetry){
        this.hardware = hardware;
        this.telemetry = telemetry;
    }

    /** starts the detection process. Place this in 'init' or 'start', //
     * During comp, put it in init. Otherwise, put it in start. */
    public void start() {
        hardware.start();
    }

    /** stops the detection process. Place this in 'stop' */
    public void stop() {
        hardware.stop();
    }
    // check your associated pipeline by connecting to the limelight's ip address
    public void pipelineSwitch(int num) {
        hardware.pipelineSwitch(num);
    }

    public Pose3D getBotpose(LLResult result) {
        return result.getBotpose();
    }

    public double getCaptureLatency(LLResult result) {
        return result.getCaptureLatency();
    }
    public double getTargetingLatency(LLResult result) {
        return result.getTargetingLatency();
    }
    public double parseLatency(LLResult result) {
        return result.getParseLatency();
    }
    // gets the latest result. This is a prerequisite to most other methods here
    public LLResult getLatestResult() {
        return hardware.getLatestResult();
    }

    // gets the latest status. This is a prerequisite to some other methods here
    public LLStatus getStatus() {
        return hardware.getStatus();
    }

    @Nullable
    public LLResultTypes.FiducialResult getLargestAprilTagResult(LLResult result) {
        List<LLResultTypes.FiducialResult> fiducialResults = result.getFiducialResults();

        double biggestArea = 0;
        LLResultTypes.FiducialResult fiducialResult = null;
        for (LLResultTypes.FiducialResult fr : fiducialResults) {
            if (fr.getTargetArea() > biggestArea) {
                biggestArea = fr.getTargetArea();
                fiducialResult = fr;
            }
        }

        if (fiducialResult != null) {
            return fiducialResult;
        }

        return null;
    }

    // if this method returns 404, that means it's not working.
    public int getAprilTagID(LLResult result) {
        LLResultTypes.FiducialResult fiducialResult = getLargestAprilTagResult(result);
        if (fiducialResult != null) {
            return fiducialResult.getFiducialId();
        }
        //
        return 404;
    }

    public boolean ScanAndGetAprilTagID(int TagID) {
        LLResult result = getLatestResult();
        if (result.isValid()) {
            int ScannedID = getAprilTagID(result);
            return ScannedID == TagID;
        }
        return false;
    }

    public int ScanAndGetAprilTagID() {
        LLResult result = getLatestResult();
        return getAprilTagID(result);
    }

    public void telemetryStatus(LLStatus status) {
        telemetry.addData("Name", "%s",
                status.getName());
        telemetry.addData("LL", "Temp: %.1fC, CPU: %.1f%%, FPS: %d",
                status.getTemp(), status.getCpu(),(int)status.getFps());
        telemetry.addData("Pipeline", "Index: %d, Type: %s",
                status.getPipelineIndex(), status.getPipelineType());
    }

    // gets the info of the scanned april tags if applicable. pairs well with telemetry version
    public List<LLResultTypes.FiducialResult> getAprilTagInfo(LLResult result) {
        return result.getFiducialResults();
    }

    // below are the telemetry methods, purely for easy telemetry without cluttering code

    // telemetry to print out various stats
    public void telemetryStats(LLResult result) {
        if (result.isValid()) {
            telemetry.addData("tx", result.getTx());
            telemetry.addData("txnc", result.getTxNC());
            telemetry.addData("ty", result.getTy());
            telemetry.addData("tync", result.getTyNC());
        } else {
            telemetry.addData("Stats", "not found");
        }
    }

    // telemetry to print out barcodes if applicable
    public void telemetryBarcode(LLResult result) {
        if (result.isValid()) {
            List<LLResultTypes.BarcodeResult> barcodeResults = result.getBarcodeResults();
            for (LLResultTypes.BarcodeResult br : barcodeResults) {
                telemetry.addData("Barcode", "Data: %s", br.getData());
            }
        } else {
            telemetry.addData("Barcodes", "not found");
        }
    }
    public void telemetryLatency(LLResult result){
        if (result.isValid()) {
            double captureLatency = result.getCaptureLatency();
            double targetingLatency = result.getTargetingLatency();
            double parseLatency = result.getParseLatency();
            telemetry.addData("LL Latency", captureLatency + targetingLatency);
            telemetry.addData("Parse Latency", parseLatency);
            telemetry.addData("PythonOutput", java.util.Arrays.toString(result.getPythonOutput()));
        } else {
            telemetry.addData("Latency", "not found");
        }
    }

    // telemetry to print out classifiers if applicable
    public void telemetryClassifier(LLResult result) {
        if (result.isValid()) {
            List<LLResultTypes.ClassifierResult> classifierResults = result.getClassifierResults();
            for (LLResultTypes.ClassifierResult cr : classifierResults) {
                telemetry.addData("Classifier", "Class: %s, Confidence: %.2f", cr.getClassName(), cr.getConfidence());
            }
        } else {
            telemetry.addData("Classifer", "not found");
        }
    }

    // telemetry to print out april tags. Do note that these are technically 'fudicial'
    public void telemetryAprilTags(LLResult result) {
        if (result.isValid()) {
            List<LLResultTypes.FiducialResult> fiducialResults = result.getFiducialResults();
            for (LLResultTypes.FiducialResult fr : fiducialResults) {
                telemetry.addData("April Tags", "ID: %d, Family: %s, X: %.2f, Y: %.2f", fr.getFiducialId(), fr.getFamily(), fr.getTargetXDegrees(), fr.getTargetYDegrees());
            }
        } else {
            telemetry.addData("April Tags", "not found");
        }
    }
    public void telemetryColor(LLResult result) {
        if (result.isValid()) {
            List<LLResultTypes.ColorResult> colorResults = result.getColorResults();
            for (LLResultTypes.ColorResult cr : colorResults) {
                telemetry.addData("Color", "X: %.2f, Y: %.2f", cr.getTargetXDegrees(), cr.getTargetYDegrees());
            }
        } else {
            telemetry.addData("Color", "not found");
        }
    }
    public void telemetryDetector(LLResult result) {
        if (result.isValid()){
            List<LLResultTypes.DetectorResult> detectorResults = result.getDetectorResults();
            for (LLResultTypes.DetectorResult dr : detectorResults) {
                telemetry.addData("Detector", "Class: %s, Area: %.2f", dr.getClassName(), dr.getTargetArea());
            }
        } else {
            telemetry.addData("Detector", "not found");
        }
    }

}

/*
Fly, broken wings
I know you are still with me
All I need is a nudge to get me started
Fly, broken wings
To somewhere we can be free
Closer to our ideal
Teary-eyed
Once gentle soul
I watched as you rotted away
The mirror says that I still remember hope
You're doing what you love
Isn't that enough? Isn't that enough?
A genius, perfect job
Isn't that enough? Isn't that enough?
Again and again
You locked me down, I locked me down
We staked me to the ground
The soil gave me warmth
Please die, little dreams
Kill the camellias in me
Wouldn't it be easier to give in?
Why are these hands chasing dreams out of my reach?
Is my thirst for normalcy odd?
Fly, perfect wings
Where have you been hiding?
Bring me to the mind that got us started
Fly, perfect wings
Show them who I can be
For the one last time, if you will
That's all
*/
