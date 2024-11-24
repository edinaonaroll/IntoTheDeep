package edu.edina.subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.lang.annotation.Target;

import edu.edina.definitions.BotBits;

public class GrabberSubsystem extends SubsystemBase {

    Telemetry telemetry;
    HardwareMap map;

    private Servo GrabServo = null;
    double  position = _minPosition;

    // grabber limits
    static final double _increment      = 0.01;     // amount to slew servo each CYCLE_MS cycle
    static final double _maxPosition    =  1.0;     // Maximum rotational position
    static final double _minPosition    =  0.432;    // larger number is more open

    static final int _cycleMiliseconds  =   50;

    public GrabberSubsystem(HardwareMap hardwareMapReference, Telemetry telemetryReference) {
        map = hardwareMapReference;
        telemetry = telemetryReference;
        GrabServo = map.get(Servo.class, BotBits.GrabServo);
    }



    public double Release () {
        telemetry.addData("Arm subsystem method", "Grab");

        double position = GrabServo.getPosition();
        telemetry.addData("Servo Position", position);
        double targetposition = position + _increment;

        if (targetposition >= _maxPosition) {
            targetposition = _maxPosition;
        }

        GrabServo.setPosition(targetposition);
        return targetposition;
    }

    public double ReleaseFully() {
        telemetry.addData("Grabber subsystem method", "ReleaseFully");

        double currPosition;

        do {
            currPosition = Release();
            sleep(_cycleMiliseconds);
        } while (currPosition < _maxPosition);

        return currPosition;
    }

    public double Grab() {
        telemetry.addData("Grabber subsystem method", "Grab");

        double position = GrabServo.getPosition();
        telemetry.addData("Servo Position", position);
        double targetposition = position - _increment;

        if (targetposition <= _minPosition) {
            targetposition = _minPosition;
        }

        GrabServo.setPosition(targetposition);
        
        return targetposition;
    }

    public double GrabFully () {
        telemetry.addData("Arm subsystem method", "GrabFully");

        double currPosition;

        do {
            currPosition = Grab();
            sleep(_cycleMiliseconds);
        } while(currPosition > _minPosition);

        return currPosition;
    }
}
