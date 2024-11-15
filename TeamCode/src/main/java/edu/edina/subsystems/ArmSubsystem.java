package edu.edina.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
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

    public void MoveArm(double yInput, boolean SlowMode){

        telemetry.addData("Arm subsystem method", "Moving");
        telemetry.addData("SlowMode",SlowMode);

        double DefaultPowerFactor = 12;
        double PowerFactor = 0;
        // Flip value so that the arm moves in the expected direction
        yInput = -yInput;

        if (SlowMode) {
            PowerFactor = DefaultPowerFactor * 6;
        } else {
            PowerFactor = DefaultPowerFactor;
        }

        if (yInput != 0) {
            ArmLiftMotor.setPower(yInput/PowerFactor);
            ArmLiftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        } else if (yInput == 0) {
//TODO:  only activate this block if the middle button is NOT pressed

            // if the arm is coming down, brake will stop it from going *UP*.
            // briefly, set the power to 'going up', so that brake will keep it from going down.
            ArmLiftMotor.setPower(.01);
            ArmLiftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        }
// TODO:  Add another 'else if' for if the middle button is pressed.  This tells us that
//  the arm is going down and we need to stop it.
//  In this case, we need to stop and ignore 'down' input from the controller.
//  If the button is pressed, what should the behavior be if the input is 'up' vs. 'down'?



        telemetry.addData("SelectedItem","Arm Lift Motor");
        telemetry.addData("MotorZeroPowerBehavior",ArmLiftMotor.getZeroPowerBehavior());
        telemetry.addData("Power", ArmLiftMotor.getPower());
    }

    public void ArmExtendRetract (double Input, boolean SlowMode) {
        telemetry.addData("Arm subsystem method", "Extend");
        telemetry.addData("SlowMode",SlowMode);

        double DefaultPowerFactor = 12;
        double PowerFactor = 0;
        // Flip value so that the arm moves in the expected direction
        Input = -Input;

        if (SlowMode) {
            PowerFactor = DefaultPowerFactor * 6;
        } else {
            PowerFactor = DefaultPowerFactor;
        }

        ArmExtendMotor.setPower(Input/PowerFactor);
        ArmExtendMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        telemetry.addData("SelectedItem","Arm Extend Motor");
        telemetry.addData("MotorZeroPowerBehavior",ArmLiftMotor.getZeroPowerBehavior());
        telemetry.addData("Power", ArmLiftMotor.getPower());
    }



    public void Extend () {
        telemetry.addData("Arm subsystem method", "Extend");
// TODO:  Make arm extend
    }

    public void ExtendFully () {
        telemetry.addData("Arm subsystem method", "ExtendFully");
// TODO:  Make arm extend
    }

    public void Retract () {
        telemetry.addData("Arm subsystem method", "Retract");
// TODO:  Make arm retract
    }

    public void RetractFully () {
        telemetry.addData("Arm subsystem method", "RetractFully");
// TODO:  Make arm retract
    }

    public void Grab () {
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