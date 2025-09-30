package org.firstinspires.ftc.teamcode.testfiles;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "TestFile", group = "Samples")

public class SampleFile extends OpMode {
    private int theNumber = 1;
    @Override
    public void init() {
        telemetry.addData("Status", "Initialized");
    }

    @Override
    public void loop() {
        telemetry.addData("Status", "Running");
        telemetry.addData("The Number Value", theNumber);
        theNumber += 1;
    }
}
