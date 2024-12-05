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
    double  positionRight = _minPositionRight;
    double  positionLeft = _minPositionLeft;

    static final double _increment          = 0.01;     // amount to slew servo each CYCLE_MS cycle
    static final int    _cycleMiliseconds   =   50;     // period of each cycle
    static final double _maxPositionRight   =  1.0;
    static final double _maxPositionLeft    =  0.0;// Maximum rotational position
    static final double _minPositionRight   =  0.0;     // Minimum rotational position
    static final double _minPositionLeft    =  0.4;

    public FlagSubsystem(HardwareMap hardwareMapReference, Telemetry telemetryReference) {
        map = hardwareMapReference;
        telemetry = telemetryReference;

        FlagServoLeft = map.get(Servo.class, BotBits.FlagServoLeft);
        FlagServoRight = map.get(Servo.class, BotBits.FlagServoRight);

        FlagServoLeft.setDirection(Servo.Direction.FORWARD);
        FlagServoRight.setDirection(Servo.Direction.FORWARD);
    }

    public double Raise(){
        positionRight = FlagServoRight.getPosition();
        positionLeft = FlagServoLeft.getPosition();

        positionRight += _increment;
        positionLeft -= _increment;

        telemetry.addData("Servo Position", positionLeft);

        if (positionRight >= _maxPositionRight) {
            positionRight = _maxPositionRight;
        }

        if (positionLeft <= _maxPositionLeft) {
            positionLeft = _maxPositionLeft;
        }

        FlagServoRight.setPosition(positionRight);
        FlagServoLeft.setPosition(positionLeft);

        telemetry.addData("FlagServoRight position: ", positionRight);
        telemetry.addData("FlagServoLeft position: ", positionLeft);

        return positionLeft;
    }

    public void RaiseFully(){
        telemetry.addData("Subsystem method", "RaiseFully");

        double currPosition;

        do {
            currPosition = Raise();
            sleep(_cycleMiliseconds);
        } while (currPosition < _maxPositionRight);
    }

    public double Lower() {
        positionRight = FlagServoRight.getPosition();
        positionLeft = FlagServoLeft.getPosition();

        positionRight -= _increment;
        positionLeft += _increment;

        telemetry.addData("Servo Position", positionLeft);

        if (positionLeft >= _minPositionLeft) {
            positionLeft = _minPositionLeft;
        }

        if (positionRight <= _minPositionRight) {
            positionRight = _minPositionRight;
        }

        FlagServoLeft.setPosition(positionLeft);
        FlagServoRight.setPosition(positionRight);

        telemetry.addData("FlagServoRight position: ", positionRight);
        telemetry.addData("FlagServoLeft position: ", positionLeft);

        return positionLeft;
    }

    public void LowerFully(){
        telemetry.addData("Subsystem method", "LowerFully");

        double currPosition;

        do {
            currPosition = Lower();
            sleep(_cycleMiliseconds);
        } while (currPosition > _minPositionLeft);
    }
}