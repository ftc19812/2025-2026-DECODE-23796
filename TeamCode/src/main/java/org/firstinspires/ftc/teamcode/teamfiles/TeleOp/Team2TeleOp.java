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
public class Team2TeleOp extends OpMode {
    public Servo scoopBaskinRobbins;
    public Servo kickball;
    public DcMotorEx spin2;


    // make most variables that don't change constantly here
    // private DcMotorEx motorExample; // don't hardwareMap here!!!
    @Override
    public void init() {
        scoopBaskinRobbins = hardwareMap.get(Servo.class, "scoopBaskinRobbins");
        scoopBaskinRobbins.setDirection(Servo.Direction.REVERSE);
        kickball = hardwareMap.get(Servo.class, "kickball");
        kickball.setDirection(Servo.Direction.REVERSE);
        spin2 = hardwareMap.get(DcMotorEx.class, "spin2");
        spin2.setDirection(DcMotor.Direction.REVERSE);
        spin2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        // initialize variables (i.e. hardware map, set direction, set mode here!)
        // motorExample = hardwareMap.get(DcMotorEx.class, "motorExampleName");
        // motorExample.setDirection(DcMotorSimple.Direction.REVERSE); // if the motor needs to be reverse
        // motorExample.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER); // use this line if you're using velocity
        telemetry.addData("Status", "Initialized");
        telemetry.update();
    }

    @Override
    public void start() {
        scoopBaskinRobbins.setPosition(0);

        // if you're using limelight, enable it here (you can enable in init, but this is better)
    }

    @Override
    public void loop() {
        spin2.setPower(1);

        if (gamepad1.a) {
            scoopBaskinRobbins.setPosition(1);
            scoopBaskinRobbins.setPosition(0);
        }
    }
}

