package org.firstinspires.ftc.teamcode.testfiles;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.util.ElapsedTime;


@TeleOp
public class SpindexerTest extends OpMode {
    CRServo spinMotor;
    ElapsedTime timeSinceLastSpin = new ElapsedTime();

    @Override
    public void init() {
        spinMotor = hardwareMap.get(CRServo.class, "spinMotor");

    }
    @Override
    public void loop() {
        if (gamepad1.a) {
            spinMotor(0.3, 1);
        } else if (gamepad1.b) {

        } else if (gamepad1.right_bumper) {
            spinMotor.setPower(0.3);
        }
    }

    public void spinMotor(double power, double waitTime) {
        spinMotor.setPower(power);
        
        spinMotor.setPower(0);
    }
}
