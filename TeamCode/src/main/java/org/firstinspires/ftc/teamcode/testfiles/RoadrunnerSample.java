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
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.drivelocalizers.MecanumDrive;
import org.firstinspires.ftc.teamcode.subsystems.LimelightManager;
import org.firstinspires.ftc.teamcode.subsystems.RRIntakeSample;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Vector;

@Autonomous(name = "Roadrunner Test")
public class RoadrunnerSample extends OpMode {

    private static final Logger log = LoggerFactory.getLogger(RoadrunnerSample.class);
    Pose2d initialPos = new Pose2d(12,12,Math.toRadians(0));

//    RRIntakeSample intake = new RRIntakeSample(hardwareMap);
    Limelight3A limelightcam;
    LimelightManager limelight;
    MecanumDrive drive;
    TrajectoryActionBuilder trajectory;
    @Override
    public void init() {
        limelightcam = hardwareMap.get(Limelight3A.class, "limelight");
        limelight = new LimelightManager(limelightcam, telemetry);
        drive = new MecanumDrive(hardwareMap, initialPos);
        trajectory = drive.actionBuilder(new Pose2d(24,12,Math.toRadians(90)))
                .strafeToLinearHeading(new Vector2d(36,36), Math.toRadians(0))
                .strafeTo(new Vector2d(25,25))
                .strafeToLinearHeading(new Vector2d(36,38), Math.toRadians(45));
    }

    @Override
    public void start() {
        new Thread(() -> {

        }).start();
    }

    @Override
    public void loop() {

    }


}
