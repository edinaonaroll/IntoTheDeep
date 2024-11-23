package edu.edina.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import edu.edina.definitions.BotBits;
import edu.edina.definitions.SubsystemInitMode;

public class GrabberSubsystem extends SubsystemBase {

    Telemetry telemetry;
    HardwareMap map;

    private Servo GrabServo = null;
    double  position = _minPosition;

    // grabber limits
    static final double _increment      = 0.01;     // amount to slew servo each CYCLE_MS cycle
    static final double _maxPosition    =  1.0;     // Maximum rotational position
    static final double _minPosition    =  0.432;    // larger number is more open

    public GrabberSubsystem(HardwareMap hardwareMapReference, Telemetry telemetryReference) {
        map = hardwareMapReference;
        telemetry = telemetryReference;
        GrabServo = map.get(Servo.class, BotBits.GrabServo);
    }



    public void Release () {
        telemetry.addData("Arm subsystem method", "Grab");

        position = GrabServo.getPosition();
        telemetry.addData("Servo Position", position);

        while (position < _maxPosition) {
            position += _increment;

            if (position >= _maxPosition) {
                position = _maxPosition;
            }
        }

        GrabServo.setPosition(position);
    }

    public void Grab() {
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