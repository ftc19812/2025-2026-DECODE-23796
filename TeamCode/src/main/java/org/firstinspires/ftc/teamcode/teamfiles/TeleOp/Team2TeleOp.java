package org.firstinspires.ftc.teamcode.teamfiles.TeleOp;

// imports, so you can import the stuff. idk what to tell you, man
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

@TeleOp
@Disabled // delete this when copying this file
public class Team2TeleOp extends OpMode {
    public Servo scoopBaskinRobbins;
    public Servo kickball;
    public DcMotorEx spin2;
    public DcMotor frontLeft;
    public DcMotor frontRight;
    public DcMotor backLeft;
    public DcMotor backRight;
    public IMU imu;
    // make most variables that don't change constantly here
    // private DcMotorEx motorExample; // don't hardwareMap here!!!
    @Override
    public void init() {
        frontLeft = hardwareMap.get(DcMotor.class, "leftFront");
        frontRight = hardwareMap.get(DcMotor.class, "rightFront");
        backLeft = hardwareMap.get(DcMotor.class, "leftBack");
        backRight = hardwareMap.get(DcMotor.class, "rightBack");

        scoopBaskinRobbins = hardwareMap.get(Servo.class, "scoop");
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

        imu = hardwareMap.get(IMU.class, "imu");
        // This needs to be changed to match the orientation on your robot
        RevHubOrientationOnRobot.LogoFacingDirection logoDirection =
                RevHubOrientationOnRobot.LogoFacingDirection.UP;
        RevHubOrientationOnRobot.UsbFacingDirection usbDirection =
                RevHubOrientationOnRobot.UsbFacingDirection.FORWARD;

        RevHubOrientationOnRobot orientationOnRobot = new
                RevHubOrientationOnRobot(logoDirection, usbDirection);
        imu.initialize(new IMU.Parameters(orientationOnRobot));
    }

    @Override
    public void start() {
        scoopBaskinRobbins.setPosition(0);
        spin2.setPower(1);
        // if you're using limelight, enable it here (you can enable in init, but this is better)
    }

    @Override
    public void loop() {
        if (gamepad1.aWasPressed()) {
            if (scoopBaskinRobbins.getPosition() == 1) {
                scoopBaskinRobbins.setPosition(0);
            } else {
                scoopBaskinRobbins.setPosition(1);
            }
        }

//        if (gamepad1.a) {
//            imu.resetYaw();
//        }
        // If you press the left bumper, you get a drive from the point of view of the robot
        // (much like driving an RC vehicle)
//        if (gamepad1.left_bumper) {
        drive(-gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x);
//        } else {
//            driveFieldRelative(-gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x);
//        }
    }

    private void driveFieldRelative(double forward, double right, double rotate) {
        // First, convert direction being asked to drive to polar coordinates
        double theta = Math.atan2(forward, right);
        double r = Math.hypot(right, forward);

        // Second, rotate angle by the angle the robot is pointing
        theta = AngleUnit.normalizeRadians(theta -
                imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS));

        // Third, convert back to cartesian
        double newForward = r * Math.sin(theta);
        double newRight = r * Math.cos(theta);

        // Finally, call the drive method with robot relative forward and right amounts
        drive(newForward, newRight, rotate);
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
}

