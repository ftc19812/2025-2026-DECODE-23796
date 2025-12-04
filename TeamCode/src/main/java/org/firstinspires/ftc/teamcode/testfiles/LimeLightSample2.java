package org.firstinspires.ftc.teamcode.testfiles;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;

@Autonomous(name="Limelight Test 2")
public class  LimeLightSample2 extends LinearOpMode {

    private Limelight3A limelight;

    @Override
    public void runOpMode() throws InterruptedException
    {
        limelight = hardwareMap.get(Limelight3A.class, "limelight");

        telemetry.setMsTransmissionInterval(11);

        limelight.pipelineSwitch(0);

        /*
         * Starts polling for data.
         */
        limelight.start();
        telemetry.addLine("Limelight has started, initiated...");
        telemetry.update();
        waitForStart();
        while (opModeIsActive()) {
            LLResult result = limelight.getLatestResult();
            if (result != null) {
                if (result.isValid()) {
                    Pose3D botpose = result.getBotpose();
                    telemetry.addData("tx", result.getTx());
                    telemetry.addData("ty", result.getTy());
                    telemetry.addData("Botpose", botpose.toString());

                } else {
                    telemetry.addLine("Result exists, just isn't valid");
                }
            } else {
                telemetry.addLine("The result doesn't exist");
            }
            telemetry.addData("Version", limelight.getVersion());
            telemetry.addData("Is Connected", limelight.isConnected());
            telemetry.addData("Is Running", limelight.isRunning());
            telemetry.addData("Connection Info", limelight.getConnectionInfo());
            telemetry.addData("Device Name", limelight.getDeviceName());
            telemetry.update();
        }
    }
}
