package org.firstinspires.ftc.teamcode.tuning;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;

@TeleOp(name = "ForwardPushTestForTicks")
public class ForwardPushTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        // CHANGE THESE NAMES to match your config

        telemetry.addLine("Push the robot straight forward ~24 inches, then stop.");
        telemetry.update();
        GoBildaPinpointDriver driver = hardwareMap.get(GoBildaPinpointDriver.class, "pinpoint");
        driver.resetPosAndIMU();

        sleep(4000);
        driver.setEncoderResolution(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD);
        driver.setOffsets(-3.0, 0, DistanceUnit.INCH);
        driver.setEncoderDirections(GoBildaPinpointDriver.EncoderDirection.FORWARD,
                GoBildaPinpointDriver.EncoderDirection.FORWARD);
        driver.setPosition(new Pose2D(DistanceUnit.MM, 0,0, AngleUnit.DEGREES, 0));

        telemetry.addLine("Help help me");
        telemetry.update();
        waitForStart();


        int offsetX = 0;
        int offsetY = 0;


        if (isStopRequested()) return;

        // Tell driver to physically push the bot forward ~24 inches
        int num = 1;
        while (opModeIsActive()) {
            driver.update();
            if (num == 1) {
                offsetX = driver.getEncoderX() * -1;
                offsetY = driver.getEncoderY() * -1;
            }
            driver.resetPosAndIMU();
            num+=1;
            int YEncoders = driver.getEncoderY();
            int XEncoders = driver.getEncoderX();

            int TrueYEncoders = YEncoders + offsetY;
            int TrueXEncoders = XEncoders + offsetX;
            telemetry.addData("X Offset", offsetX);
            telemetry.addData("Y Offset", offsetY);
            telemetry.addData("Parallel Ticks (X)", TrueXEncoders);
            telemetry.addData("Perpendicular Ticks (Y)", TrueYEncoders);
            telemetry.addData("test loop", num);
            telemetry.update();

            driver.update();

            //12205 per 24 inch
            // 0.00196640721 inch per tick
        }
    }
}