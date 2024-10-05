package edu.edina.subsystems;

import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.Telemetry;

import edu.edina.definitions.BotBits;

public class FlagSubsystem {

    static final double _increment      = 0.01;     // amount to slew servo each CYCLE_MS cycle
    static final int _cycleMiliseconds  =   50;     // period of each cycle
    static final double _maxPosition    =  1.0;     // Maximum rotational position
    static final double _minPosition    =  0.0;     // Minimum rotational position

    // Define class members
    Servo servo;
    double  position = _minPosition;
    boolean rampUp = true;

    HardwareMap map;
    Telemetry telemetry;

    public FlagSubsystem(HardwareMap hardwareMapReference, Telemetry telemetryReference) {
        map = hardwareMapReference;
        telemetry = telemetryReference;
    }

    public void FlagRaise(){
        telemetry.addData("Subsystem method", "FlagRaise");

        servo = map.get(Servo.class, BotBits.FlagServo);

        position = servo.getPosition();
        telemetry.addData("Servo Position", position);

        while (position < _maxPosition){
            position += _increment;

            if (position >= _maxPosition) {
                position = _maxPosition;
            }
        }
    }

    public void FlagLower() {
        telemetry.addData("Subsystem method", "FlagLower");

        // Keep stepping down until we hit the min value.
        servo = map.get(Servo.class, BotBits.FlagServo);

        position = servo.getPosition();
        telemetry.addData("Servo Position", position);

        while (position > _minPosition) {
            position -= _increment;
            if (position <= _minPosition) {
                position = _minPosition;
            }
        }
    }
}