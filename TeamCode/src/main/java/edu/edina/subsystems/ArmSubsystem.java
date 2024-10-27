package edu.edina.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.Telemetry;

import edu.edina.definitions.BotBits;

public class ArmSubsystem {

    Telemetry telemetry;
    HardwareMap map;

    private DcMotor ArmLiftMotor = null;
    private DcMotor ArmExtendMotor = null;

    private Servo GrabServo = null;
    double  position = _minPosition;
    boolean rampUp = true;

    static final double _increment      = 0.01;     // amount to slew servo each CYCLE_MS cycle
    static final int _cycleMiliseconds  =   50;     // period of each cycle
    static final double _maxPosition    =  1.0;     // Maximum rotational position
    static final double _minPosition    =  0.0;     // Minimum rotational position

    public ArmSubsystem(HardwareMap hardwareMapReference, Telemetry telemetryReference) {
        map = hardwareMapReference;
        telemetry = telemetryReference;

        ArmLiftMotor = map.get(DcMotor.class, BotBits.ArmLiftMotor);
        ArmExtendMotor = map.get(DcMotor.class, BotBits.ArmExtendMotor);

        GrabServo = map.get(Servo.class, BotBits.GrabServo);
    }

    public void Raise(){
        telemetry.addData("Arm subsystem method", "Raise");

    }

    public void Lower(){
        telemetry.addData("Arm subsystem method", "Lower");

    }

    public void Extend(){
        telemetry.addData("Arm subsystem method", "Extend");

    }

    public void Retract(){
        telemetry.addData("Arm subsystem method", "Retract");

    }

    public void Grab(){
        telemetry.addData("Arm subsystem method", "Grab");

        position = GrabServo.getPosition();
        telemetry.addData("Servo Position", position);

        while (position < _maxPosition){
            position += _increment;

            if (position >= _maxPosition) {
                position = _maxPosition;
            }
        }

        GrabServo.setPosition(position);
    }

    public void Release(){
        telemetry.addData("Arm subsystem method", "Release");
        telemetry.addData("Subsystem method", "Lower");

        position = GrabServo.getPosition();
        telemetry.addData("Servo Position", position);

        while (position > _minPosition) {
            position -= _increment;
            if (position <= _minPosition) {
                position = _minPosition;
            }
        }

        GrabServo.setPosition(position);
    }
}