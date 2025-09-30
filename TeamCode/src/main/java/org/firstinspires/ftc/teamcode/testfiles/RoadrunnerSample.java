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

@Autonomous(name = "Roadrunner Test")
public class RoadrunnerSample extends LinearOpMode {

    Pose2d initialPos = new Pose2d(0,0,Math.toRadians(90));

//    RRIntakeSample intake = new RRIntakeSample(hardwareMap);





    @Override
    public void runOpMode() {
        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPos);
        // this is where the init goes
        TrajectoryActionBuilder tab1 = drive.actionBuilder(initialPos)
                .lineToX(24)
                .waitSeconds(1)
                .lineToY(24)
                .waitSeconds(1)
                .lineToX(-24)
                .waitSeconds(1)
                .lineToY(-24);
        TrajectoryActionBuilder tab2 = drive.actionBuilder(new Pose2d(0,0,Math.toRadians(90)))
                .strafeToConstantHeading(new Vector2d(48,48));
        TrajectoryActionBuilder tab3 = drive.actionBuilder(new Pose2d(48,48,Math.toRadians(90)))
                .splineTo(new Vector2d(-48,-48), Math.toRadians(180))
                .waitSeconds(1);
        telemetry.addData("Status", "initialized");
        telemetry.addData("Moment of Truth", "ARE YOU READY???");
        boolean looped = false;
        while (opModeIsActive() && !looped) {
            looped = true;
            Actions.runBlocking(
                    new SequentialAction(
                            new ParallelAction(
                                    tab1.build()
//                                    intake.setPowerRR(1.0)
                            ),
                            tab2.build(),
                            tab3.build()
                    )
            );
        }
    }
}
