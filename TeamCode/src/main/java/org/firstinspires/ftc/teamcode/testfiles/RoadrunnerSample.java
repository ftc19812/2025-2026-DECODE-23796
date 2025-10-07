package org.firstinspires.ftc.teamcode.testfiles;

import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;

import org.firstinspires.ftc.teamcode.drivelocalizers.MecanumDrive;
import org.firstinspires.ftc.teamcode.subsystems.RRIntakeSample;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Autonomous(name = "Roadrunner Test")
public class RoadrunnerSample extends LinearOpMode {

    private static final Logger log = LoggerFactory.getLogger(RoadrunnerSample.class);
    Pose2d initialPos = new Pose2d(0,0,Math.toRadians(90));

//    RRIntakeSample intake = new RRIntakeSample(hardwareMap);





    @Override
    public void runOpMode() {
        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPos);


        TrajectoryActionBuilder trajectory = drive.actionBuilder(new Pose2d(24,12,Math.toRadians(90)))
                .strafeToLinearHeading(new Vector2d(36,36), Math.toRadians(0))
                .strafeTo(new Vector2d(25,25))
                .strafeToLinearHeading(new Vector2d(36,38), Math.toRadians(45));

        TrajectoryActionBuilder myTrajectory = drive.actionBuilder(new Pose2d(36,12,Math.toRadians(90)))
                .strafeToLinearHeading(new Vector2d(36,84), Math.toRadians(75));





        telemetry.addData("Status", "initialized");
        telemetry.addData("Moment of Truth", "ARE YOU READY???");
        boolean looped = false;
        waitForStart();

        while (opModeIsActive() && !looped) {
            looped = true;
            Actions.runBlocking(
                    new SequentialAction(

                            myTrajectory.build()
//
                    )
            );

        }
    }
}
