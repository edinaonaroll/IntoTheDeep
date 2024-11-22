package edu.edina.subsystems;

import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.Telemetry;

import edu.edina.definitions.BotBits;

public class FlagSubsystem extends SubsystemBase {
    HardwareMap map;
    Telemetry telemetry;

    // Define class members
    Servo FlagServo;
    double  position = _minPosition;

    static final double _increment          = 0.01;     // amount to slew servo each CYCLE_MS cycle
    static final int    _cycleMiliseconds   =   50;     // period of each cycle
    static final double _maxPosition        =  1.0;     // Maximum rotational position
    static final double _minPosition        =  0.0;     // Minimum rotational position

    public FlagSubsystem(HardwareMap hardwareMapReference, Telemetry telemetryReference) {
        map = hardwareMapReference;
        telemetry = telemetryReference;

        FlagServo = map.get(Servo.class, BotBits.FlagServo);
    }

    public double Raise(){
        position = FlagServo.getPosition();
        position += _increment;

        telemetry.addData("Servo Position", position);

        if (position >= _maxPosition) {
            position = _maxPosition;
        }

        FlagServo.setPosition(position);

        return position;
    }

    public void RaiseFully(){
        telemetry.addData("Subsystem method", "RaiseFully");

        double currPosition;

        do {
            currPosition = Raise();
            sleep(_cycleMiliseconds);
        } while (currPosition < _maxPosition);
    }

    public double Lower() {
        position = FlagServo.getPosition();
        position -= _increment;

        telemetry.addData("Servo Position", position);

        if (position <= _minPosition) {
            position = _minPosition;
        }

        FlagServo.setPosition(position);

        return position;
    }

    public void LowerFully(){
        telemetry.addData("Subsystem method", "LowerFully");

        double currPosition;

        do {
            currPosition = Lower();
            sleep(_cycleMiliseconds);
        } while (currPosition > _minPosition);
    }
}