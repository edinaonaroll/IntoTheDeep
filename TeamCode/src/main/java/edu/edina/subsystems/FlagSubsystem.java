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

    static final double _increment      = 0.01;     // amount to slew servo each CYCLE_MS cycle
    static final int _cycleMiliseconds  =   50;     // period of each cycle
    static final double _maxPosition    =  1.0;     // Maximum rotational position
    static final double _minPosition    =  0.0;     // Minimum rotational position

    public FlagSubsystem(HardwareMap hardwareMapReference, Telemetry telemetryReference) {
        map = hardwareMapReference;
        telemetry = telemetryReference;

        FlagServo = map.get(Servo.class, BotBits.FlagServo);
    }

    public double Raise() {
        telemetry.addData("Subsystem method", "Raise");

        double currPosition = FlagServo.getPosition();
        double targetPosition = currPosition + _increment;

        if (targetPosition >= _maxPosition) {
            targetPosition = _maxPosition;
        }

        FlagServo.setPosition(targetPosition);

        telemetry.addData("Servo Position:     ", targetPosition);
        telemetry.update();

        return targetPosition;
    }

    public void RaiseFully() {
        telemetry.addData("Subsystem method", "Raise");

        double currPosition;

        do {
            currPosition = Raise();
            sleep(_cycleMiliseconds);
        } while (currPosition < _maxPosition);
    }

    public double Lower() {
        telemetry.addData("Subsystem method", "Lower");

        double currPosition = FlagServo.getPosition();
        double targetPosition = currPosition + _increment;

        while (currPosition > _minPosition) {

            currPosition -= _increment;

            if (currPosition <= _minPosition) {
                currPosition = _minPosition;
            }

            FlagServo.setPosition(currPosition);

            sleep(50);
        }

        return currPosition;
    }
}