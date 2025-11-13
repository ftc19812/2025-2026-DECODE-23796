package org.firstinspires.ftc.teamcode.teamfiles.Auto;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.drivelocalizers.MecanumDrive;
import org.firstinspires.ftc.teamcode.subsystems.LimelightManager;
import org.firstinspires.ftc.teamcode.testfiles.RoadrunnerSample;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.StandardWatchEventKinds;

@Autonomous
public class Team2Auto extends OpMode {
    private static final Logger log = LoggerFactory.getLogger(RoadrunnerSample.class);
    public Pose2d initialPos = new Pose2d(0,0,Math.toRadians(90));

    //    RRIntakeSample intake = new RRIntakeSample(hardwareMap);
    public TrajectoryActionBuilder trajectory;
    public TrajectoryActionBuilder newTrajectory;

    public TrajectoryActionBuilder newerTrajectory;

    Limelight3A limelightcam;
    LimelightManager limelight;
    MecanumDrive drive;

    public DcMotorEx intake;

    public DcMotorEx outake;

    @Override
    public void init() {
        drive = new MecanumDrive(hardwareMap, initialPos);

        trajectory = drive.actionBuilder(new Pose2d(0,0,Math.toRadians(90)))
                .strafeToLinearHeading(new Vector2d(0,68), Math.toRadians(0));
        newTrajectory = drive.actionBuilder(new Pose2d(0, 68, Math.toRadians(0)))
                .waitSeconds(5)
                .strafeToLinearHeading(new Vector2d(72, 72), Math.toRadians(90));
        newerTrajectory = drive.actionBuilder(new Pose2d(72, 72, Math.toRadians(90)))
                .strafeToLinearHeading(new Vector2d(96,68), Math.toRadians(270));

        intake = hardwareMap.get(DcMotorEx.class, "intake");
        outake = hardwareMap.get(DcMotorEx.class, "outake");


    }

    @Override
    public void start() {
        new Thread(() -> {
            Actions.runBlocking(
                    new SequentialAction(
                            trajectory.build()
                    )
            );
            Actions.runBlocking(
                    new SequentialAction(
                            newTrajectory.build()
                    )
            );
            intake.setPower(1);
            Actions.runBlocking(
                    new SequentialAction(
                            newerTrajectory.build()
                    )
            );
            intake.setPower(0);
            outake.setPower(1);

        }).start();
    }

    @Override
    public void loop() {


    }
}




