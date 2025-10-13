package org.firstinspires.ftc.teamcode.testfiles;

import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;

import org.firstinspires.ftc.teamcode.drivelocalizers.MecanumDrive;
import org.firstinspires.ftc.teamcode.subsystems.LimelightManager;
import org.firstinspires.ftc.teamcode.subsystems.RRIntakeSample;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Vector;

@Autonomous(name = "Roadrunner Test")
public class RoadrunnerSample extends LinearOpMode {

    private static final Logger log = LoggerFactory.getLogger(RoadrunnerSample.class);
    Pose2d initialPos = new Pose2d(12,12,Math.toRadians(0));

//    RRIntakeSample intake = new RRIntakeSample(hardwareMap);
    Limelight3A limelightcam;
    LimelightManager limelight;




    @Override
    public void runOpMode() {
        limelightcam = hardwareMap.get(Limelight3A.class, "limelight");
        limelight = new LimelightManager(limelightcam, telemetry);
        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPos);


        TrajectoryActionBuilder trajectory = drive.actionBuilder(new Pose2d(24,12,Math.toRadians(90)))
                .strafeToLinearHeading(new Vector2d(36,36), Math.toRadians(0))
                .strafeTo(new Vector2d(25,25))
                .strafeToLinearHeading(new Vector2d(36,38), Math.toRadians(45));
        // initial pos is 12,12,0
        TrajectoryActionBuilder initialTrajectory = drive.actionBuilder(new Pose2d(12,12,Math.toRadians(0)))
                .strafeToLinearHeading(new Vector2d(36,12), Math.toRadians(90))
                .waitSeconds(1)
                ;

        TrajectoryActionBuilder trajectoryIfAprilTag = drive.actionBuilder(new Pose2d(36,12,Math.toRadians(90)))
                .strafeToLinearHeading(new Vector2d(12,60), Math.toRadians(180))
                .turn(Math.toRadians(1080))
                .strafeTo(new Vector2d(12, 50))
                .splineTo(new Vector2d(30,60), Math.toRadians(75))
                ;
        TrajectoryActionBuilder trajectoryIfNotAprilTag = drive.actionBuilder(new Pose2d(36,12,Math.toRadians(90)))
                .strafeToLinearHeading(new Vector2d(60,60), Math.toRadians(270))
                .turn(Math.toRadians(1080))
                .strafeTo(new Vector2d(30, 50))
                .splineTo(new Vector2d(12,60), Math.toRadians(120))
                ;


        TrajectoryActionBuilder amazingTrajectory = drive.actionBuilder(new Pose2d(24,10, Math.toRadians(90)))
                .strafeToLinearHeading(new Vector2d(24, 42), Math.toRadians(180))
                .strafeTo(new Vector2d(12, 42))
                .turn(Math.toRadians(1080))
                .waitSeconds(2)
                .splineTo(new Vector2d(36, 48), Math.toRadians(0))
                .strafeToLinearHeading(new Vector2d(12, 60 ), Math.toRadians(135));


        TrajectoryActionBuilder coolTrajectory = drive.actionBuilder(initialPos)

                .strafeToLinearHeading(new Vector2d(60, 36), Math.toRadians(270))
                .strafeToLinearHeading(new Vector2d( 10, 60), Math.toRadians(90))
                .strafeToLinearHeading(new Vector2d( 60, 84), Math.toRadians(270))
                .strafeTo(new Vector2d(36, 84))
                .strafeTo(new Vector2d(36, 10))
                .strafeToLinearHeading(new Vector2d(36, 48), Math.toRadians(90))
                ;



        telemetry.addData("Status", "initialized");
        telemetry.addData("Moment of Truth", "ARE YOU READY???");
        telemetry.update();
        int loop = 1;
        waitForStart();

        if (isStopRequested()) return;

        Actions.runBlocking(
                new SequentialAction(
                        initialTrajectory.build()
                )
        );
        LLResult results = limelight.getLatestResult();
        if (results.isValid()) {
            int AprilTag = limelight.getAprilTagID(results);
            telemetry.addData("Result", "Valid");

            if (AprilTag != 404) {
                Actions.runBlocking(
                        new SequentialAction(
                                trajectoryIfAprilTag.build() // brings it to the center of the field
                                // facing 90 deg.
//
                        )
                );
            }
        } else {
            telemetry.addData("Result", "Invalid");
            Actions.runBlocking(
                    new SequentialAction(
                            trajectoryIfNotAprilTag.build() // brings it to the center of the field
                            // facing 90 deg.
//
                    )
            );
            telemetry.addData("Limelight", "was not found");
            telemetry.update();
        }
//        while (opModeIsActive() && loop == 1) {
//            loop+=1;
//            telemetry.addLine("Update 1");
//            telemetry.update();
//            Actions.runBlocking(
//                    new SequentialAction(
//                            coolTrajectory.build()
//                    )
//            );
//            telemetry.addLine("Update 2");
//            telemetry.update();
//
            /*
            LLResult results = limelight.getLatestResult();
            if (results.isValid()) {
                int AprilTag = limelight.getAprilTagID(results);
                if (AprilTag != 404) {
                    Actions.runBlocking(
                            new SequentialAction(
                                    trajectoryIfAprilTag.build() // brings it to the center of the field
                                    // facing 90 deg.
//
                            )
                    );
                }
            } else {
                Actions.runBlocking(
                        new SequentialAction(
                                trajectoryIfNotAprilTag.build() // brings it to the center of the field
                                // facing 90 deg.
//
                        )
                );
                telemetry.addData("Limelight", "was not found");
                telemetry.update();
            }
//            */
//
//
//        }
    }
}
