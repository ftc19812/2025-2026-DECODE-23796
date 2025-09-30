package org.firstinspires.ftc.teamcode.subsystems;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class RRIntakeSample {
    private DcMotorEx intake1;
    private DcMotorEx intake2;

    public RRIntakeSample(HardwareMap hardwareMap) {
        intake1 = hardwareMap.get(DcMotorEx.class, "intake1");
        intake2 = hardwareMap.get(DcMotorEx.class, "intake2");
//        reverse things here as necessary
//        intake1.setDirection(DcMotorSimple.Direction.REVERSE);
//        intake1.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    /** assuming that both intakes work at 1 power. May need to reverse*/
    public void setPower(double power){
        intake1.setPower(power);
        intake2.setPower(power);
    }

    private class SetPowerRR implements Action {
        private double power = 0;
        public SetPowerRR(double power) {
            this.power = power;
        }
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            setPower(power);
            return false;
        }
    }

    public Action setPowerRR(double power) {
        return new SetPowerRR(power);
    }


}
