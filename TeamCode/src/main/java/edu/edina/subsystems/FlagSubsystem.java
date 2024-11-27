package edu.edina.subsystems;

import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.Telemetry;

import edu.edina.definitions.BotBits;

public class FlagSubsystem extends SubsystemBase {
    HardwareMap map;
    Telemetry telemetry;

    // Define class members
    Servo FlagServoRight;
    Servo FlagServoLeft;
    double  positionRight = _minPosition;
    double  positionLeft = _minPosition;

    static final double _increment          = 0.01;     // amount to slew servo each CYCLE_MS cycle
    static final int    _cycleMiliseconds   =   50;     // period of each cycle
    static final double _maxPosition        =  1.0;     // Maximum rotational position
    static final double _minPosition        =  0.0;     // Minimum rotational position

    public FlagSubsystem(HardwareMap hardwareMapReference, Telemetry telemetryReference) {
        map = hardwareMapReference;
        telemetry = telemetryReference;

        FlagServoLeft = map.get(Servo.class, BotBits.FlagServoLeft);
        FlagServoRight = map.get(Servo.class, BotBits.FlagServoRight);

        FlagServoLeft.setDirection(Servo.Direction.FORWARD);
        FlagServoRight.setDirection(Servo.Direction.FORWARD);
    }

    public double RaiseLeft(){
        return Raise(FlagServoLeft);
    }

    public double RaiseRight(){
        return Raise(FlagServoRight);
    }

    public double Raise(Servo servo){
        double position = servo.getPosition();

        position += _increment;

        telemetry.addData("Servo Position", position);

        if (position >= _maxPosition) {
            position = _maxPosition;
        }

        servo.setPosition(position);

        telemetry.addData("FlagServoRight position: ", position);

        return position;
    }

    public void RaiseFully(){
        telemetry.addData("Subsystem method", "RaiseFully");

        double currPositionLeft;
        double currPositionRight;

        do {
            currPositionLeft = Raise(FlagServoLeft);
            sleep(_cycleMiliseconds);
        } while (currPositionLeft < _maxPosition);

        do {
            currPositionRight = Raise(FlagServoRight);
            sleep(_cycleMiliseconds);
        } while (currPositionRight < _maxPosition);
    }

    public double LowerLeft(){
        return Lower(FlagServoLeft);
    }

    public double LowerRight(){
        return Lower(FlagServoRight);
    }

    private double Lower(Servo servo) {
        double position = servo.getPosition();

        position -= _increment;

        telemetry.addData("Servo Position", position);

        if (position <= _minPosition) {
            position = _minPosition;
        }

        servo.setPosition(position);

        telemetry.addData("FlagServoRight position: ", position);

        return position;
    }

    public void LowerFully(){
        telemetry.addData("Subsystem method", "LowerFully");

        double currPositionLeft;
        double currPositionRight;

        do {
            currPositionLeft = Lower(FlagServoLeft);
            sleep(_cycleMiliseconds);
        } while (currPositionLeft > _minPosition);

        do {
            currPositionRight = Lower(FlagServoRight);
            sleep(_cycleMiliseconds);
        } while (currPositionRight > _minPosition);
    }
}