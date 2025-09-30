package org.firstinspires.ftc.teamcode.teamfiles.Test;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;

@TeleOp
public class TestMotor extends OpMode {

    DcMotorEx testMotor;
    int slowMode = 1;
    @Override
    public void init() {
        telemetry.addData("Status", "Initialzied");
        telemetry.update();
        testMotor = hardwareMap.get(DcMotorEx.class, "testMotor");
    }
    @Override
    public void loop() {
        if (gamepad1.right_bumper) {
            slowMode = 2;
        } else {
            slowMode = 1;
        }

        if (gamepad1.a) {
            testMotor.setPower(1/slowMode);
        } else if (gamepad1.b) {
            testMotor.setPower(-1/slowMode);
        } else {
            testMotor.setPower(0);
        }
    }
}
