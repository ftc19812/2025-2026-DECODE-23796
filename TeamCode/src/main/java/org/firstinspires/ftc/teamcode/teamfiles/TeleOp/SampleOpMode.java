package org.firstinspires.ftc.teamcode.teamfiles.TeleOp;

// imports, so you can import the stuff. idk what to tell you, man
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
@Disabled // delete this when copying this file
public class SampleOpMode extends OpMode {
    private DcMotorEx motor1;
    private Servo servo1;
    // make most variables that don't change constantly here
    // private DcMotorEx motorExample; // don't hardwareMap here!!!
    @Override
    public void init() {
        hardwareMap.get(DcMotorEx.class, "motor1");
        motor1.setDirection(DcMotorEx.Direction.REVERSE);
        motor1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        hardwareMap.get(Servo.class, "servo1");
        servo1.setDirection(Servo.Direction.REVERSE);
        // initialize variables (i.e. hardware map, set direction, set mode here!)
        // motorExample = hardwareMap.get(DcMotorEx.class, "motorExampleName");
        // motorExample.setDirection(DcMotorSimple.Direction.REVERSE); // if the motor needs to be reverse
        // motorExample.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER); // use this line if you're using velocity
        telemetry.addData("Status", "Initialized");
        telemetry.update();
    }
    @Override
    public void start() {
        // if you're using limelight, enable it here (you can enable in init, but this is better)


        // if you're using limelight, enable it here (you can enable in init, but this is better)
    }
    @Override
    public void loop() {
        // make things that update constantly here (like checking button presses)
        if(gamepad1.a){
            motor1.setVelocity(1500);
        } else if (gamepad1.b){
            motor1.setVelocity(-1500);
        }else{
            motor1.setVelocity(0);
        }

        if (gamepad1.right_bumper){
            servo1.setPosition(1);
        }else if(gamepad1.left_bumper){
            servo1.setPosition(-1);
        }else{
            servo1.setPosition(0);
        }

    }
}

