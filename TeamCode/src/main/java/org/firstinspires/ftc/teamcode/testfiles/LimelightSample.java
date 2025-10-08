package org.firstinspires.ftc.teamcode.testfiles;/*
Copyright (c) 2024 Limelight Vision

All rights reserved.

Redistribution and use in source and binary forms, with or without modification,
are permitted (subject to the limitations in the disclaimer below) provided that
the following conditions are met:

Redistributions of source code must retain the above copyright notice, this list
of conditions and the following disclaimer.

Redistributions in binary form must reproduce the above copyright notice, this
list of conditions and the following disclaimer in the documentation and/or
other materials provided with the distribution.

Neither the name of FIRST nor the names of its contributors may be used to
endorse or promote products derived from this software without specific prior
written permission.

NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR
TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.LLStatus;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.teamcode.subsystems.LimelightManager;

import java.util.List;

/*
 * This OpMode illustrates how to use the Limelight3A Vision Sensor.
 *
 * @see <a href="https://limelightvision.io/">Limelight</a>
 *
 * Notes on configuration:
 *
 *   The device presents itself, when plugged into a USB port on a Control Hub as an ethernet
 *   interface.  A DHCP server running on the Limelight automatically assigns the Control Hub an
 *   ip address for the new ethernet interface.
 *
 *   Since the Limelight is plugged into a USB port, it will be listed on the top level configuration
 *   activity along with the Control Hub Portal and other USB devices such as webcams.  Typically
 *   serial numbers are displayed below the device's names.  In the case of the Limelight device, the
 *   Control Hub's assigned ip address for that ethernet interface is used as the "serial number".
 *
 *   Tapping the Limelight's name, transitions to a new screen where the user can rename the Limelight
 *   and specify the Limelight's ip address.  Users should take care not to confuse the ip address of
 *   the Limelight itself, which can be configured through the Limelight settings page via a web browser,
 *   and the ip address the Limelight device assigned the Control Hub and which is displayed in small text
 *   below the name of the Limelight on the top level configuration screen.
 */
@TeleOp(name = "Limelight Test", group = "Sensor")
public class LimelightSample extends OpMode {

    private LimelightManager limelight;
    private Limelight3A limelightcam;
    @Override
    public void init() {
        limelight = new LimelightManager(hardwareMap.get(Limelight3A.class, "limelight"), telemetry);
        limelightcam = hardwareMap.get(Limelight3A.class, "limelight");
        telemetry.setMsTransmissionInterval(11);
        // i think 8 is April Tags? We won't really know until we check, anyway
        limelight.pipelineSwitch(0);

        telemetry.addData(">", "Robot Ready.  Press Play.");
        telemetry.update();
    }
    @Override
    public void start() {
        // put this in init during competition so auto doesn't die
        limelight.start();
    }
    @Override
    public void loop() {
        LLResult result = limelight.getLatestResult();
        LLStatus status = limelight.getStatus();
//        limelight.telemetryStatus(status);

        if (result.isValid()) {
            // Access general information
            int AprilTagID = limelight.getAprilTagID(result);
            if (AprilTagID == 404) {
                telemetry.addData("April Tags", "returned nothing");
            } else if (AprilTagID == 20) {
                // do whatever 20 does
                telemetry.addData("April Tag Detected", 20);
            } else if (AprilTagID == 21) {
                // do whatever 21 does
                telemetry.addData("April Tag Detected", 21);
            } else if (AprilTagID == 22) {
                // do whatever 22 does
                telemetry.addData("April Tag Detected", 22);
            } else if (AprilTagID == 23) {
                // do whatever 23 does
                telemetry.addData("April Tag Detected", 23);
            } else if (AprilTagID == 24) {
                // do whatever 24 does
                telemetry.addData("April Tag Detected", 24);
            } else if (AprilTagID == 25) {
                // do whatever 25 does
                telemetry.addData("April Tag Detected", 25);
            } else {
                // if anything else, lol
            }

        } else {
            telemetry.addData("Limelight", "No data available");
            telemetry.addData("Version", limelightcam.getVersion());
            telemetry.addData("Is Connected", limelightcam.isConnected());
            telemetry.addData("Is Running", limelightcam.isRunning());
            telemetry.addData("Connection Info", limelightcam.getConnectionInfo());
            telemetry.addData("Device Name", limelightcam.getDeviceName());
        }
    }
    @Override
    public void stop() {
        limelight.stop();
    }

}