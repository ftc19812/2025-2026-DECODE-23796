package org.firstinspires.ftc.teamcode.teamfiles.TeleOp;

// imports, so you can import the stuff. idk what to tell you, man
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.teamcode.drivelocalizers.MecanumDrive;

@TeleOp
public class Team2TeleOp extends OpMode {
    public DcMotor frontLeft;
    public DcMotor frontRight;
    public DcMotor backLeft;
    public DcMotor backRight;
    public Servo kickball;
    public DcMotorEx spin2;
    public DcMotorEx intake;
    public MecanumDrive drive;
    public PoseVelocity2d poseEstimate;
    public Pose2d initialPos = new Pose2d(0,0,90);
    // make most variables that don't change constantly here
    // private DcMotorEx motorExample; // don't hardwareMap here!!!
    @Override
    public void init() {
        drive = new MecanumDrive(hardwareMap, initialPos);

        frontLeft = hardwareMap.get(DcMotor.class, "leftFront");
        frontRight = hardwareMap.get(DcMotor.class, "rightFront");
        frontRight.setDirection(DcMotor.Direction.REVERSE); // if the motor needs to be reverse
        backLeft = hardwareMap.get(DcMotor.class, "leftBack");
        backRight = hardwareMap.get(DcMotor.class, "rightBack");
        backRight.setDirection(DcMotor.Direction.REVERSE);
        // intake = hardwareMap.get(DcMotor.class, "intake");
        // chain = hardwareMap.get(DcMotor.class, "chain");
        kickball = hardwareMap.get(Servo.class, "scoop");
        spin2 = hardwareMap.get(DcMotorEx.class, "spin2");
        spin2.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        intake = hardwareMap.get(DcMotorEx.class, "intake");
        // initialize variables (i.e. hardware map, set direction, set mode here!)
        // motorExample = hardwareMap.get(DcMotorEx.class, "motorExampleName");
        // motorExample.setDirection(DcMotorSimple.Direction.REVERSE); // if the motor needs to be reverse
        // motorExample.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER); // use this line if you're using velocity
        telemetry.addData("Status", "Initialized");
        telemetry.update();

    }

    @Override
    public void loop() {
        poseEstimate = drive.updatePoseEstimate();

        if (gamepad1.x) {
            kickball.setPosition(kickball.getPosition()+0.1);
        }
        if (gamepad1.x) {
            kickball.setPosition(kickball.getPosition()-0.1);
        }

        if (gamepad1.a) {
            kickball.setPosition(0);
        } else {
            kickball.setPosition(1);
        }


        if (gamepad1.left_trigger>0) {
            intake.setPower(1);
        } else {
            intake.setPower(0);
        }

        if (gamepad1.rightBumperWasPressed()) {
            if (spin2.getPower() != 0) {
                spin2.setVelocity(0);
            } else {
                spin2.setVelocity(-2000);
            }
        }

        //        if (gamepad1.a) {
//            imu.resetYaw();
//        }
        // If you press the left bumper, you get a drive from the point of view of the robot
        // (much like driving an RC vehicle)
//        if (gamepad1.left_bumper) {
        drive(-gamepad1.left_stick_y, -gamepad1.left_stick_x, -gamepad1.right_stick_x);


        telemetry.addData("kickball", kickball.getPosition());
        telemetry.addData("outtake velocity", spin2.getPower());
        telemetry.addData("intake", intake.getPower());
        telemetry.addData("Pose Estimate Linear", poseEstimate.linearVel);
        telemetry.addData("Pose Estimate Angular", poseEstimate.angVel);
        telemetry.update();


    }
    // Thanks to FTC16072 for sharing this code!!
    public void drive(double forward, double right, double rotate) {
        // This calculates the power needed for each wheel based on the amount of forward,
        // strafe right, and rotate
        double frontLeftPower = forward + right + rotate;
        double frontRightPower = forward - right - rotate;
        double backRightPower = forward + right - rotate;
        double backLeftPower = forward - right + rotate;

        double maxPower = 1.0;
        double maxSpeed = 1.0;  // make this slower for outreaches

        // This is needed to make sure we don't pass > 1.0 to any wheel
        // It allows us to keep all of the motors in proportion to what they should
        // be and not get clipped
        maxPower = Math.max(maxPower, Math.abs(frontLeftPower));
        maxPower = Math.max(maxPower, Math.abs(frontRightPower));
        maxPower = Math.max(maxPower, Math.abs(backRightPower));
        maxPower = Math.max(maxPower, Math.abs(backLeftPower));

        // We multiply by maxSpeed so that it can be set lower for outreaches
        // When a young child is driving the robot, we may not want to allow full
        // speed.
        frontLeft.setPower(maxSpeed * (frontLeftPower / maxPower));
        frontRight.setPower(maxSpeed * (frontRightPower / maxPower));
        backLeft.setPower(maxSpeed * (backLeftPower / maxPower));
        backRight.setPower(maxSpeed * (backRightPower / maxPower));


    }

    public void funcName() {
        telemetry.addData("dfjsj", 1);
        telemetry.addData("ddc", 1);
        telemetry.addData("dfjwedswssj", 1);
        telemetry.addData("dffdsdsjsj", 1);
        telemetry.addData("dfjsbfdj", 1);
        telemetry.addData("dfjddsj", 1);
    }
    public float add(float num1, float num2) {
        return num1 + num2;
    }
    public float multiply(float num33, float num999999) {
        return num33 * num999999;
    }

    public float justinsfunction(float num1, float num2, float num3) {
        return (num1+num2) * num3;
    }

    public boolean isValid(String name, float age, float height ){
        if(name == "operator"){
            return true;
        }else {
            if(age > 18 && height >= 50)
            {
                return true;
            }else{
                return false;
            }
        }
    }




}

