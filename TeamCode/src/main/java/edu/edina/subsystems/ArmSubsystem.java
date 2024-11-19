package edu.edina.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import edu.edina.definitions.BotBits;
import edu.edina.definitions.SubsystemInitMode;

public class ArmSubsystem extends SubsystemBase{

    Telemetry telemetry;
    HardwareMap map;

    private DcMotor ArmLiftMotor = null;
    private DcMotor ArmExtendMotor = null;
    private TouchSensor ArmTouchSensor = null;

    private Servo GrabServo = null;
    double  position = _minPosition;
    int armRaiseIncrement = 10;
    int armExtendIncrement = 10;

    // grabber limits
    static final double _increment      = 0.01;     // amount to slew servo each CYCLE_MS cycle
    static final int _cycleMiliseconds  =   50;     // period of each cycle
    static final double _maxPosition    =  1.0;     // Maximum rotational position
    static final double _minPosition    =  0.42;     // Minimum rotational position

    // arm raise limits
    static final int _armRaiseMaxClicks = 360;

    // arm extend limits
    static final int _armExtendMaxClicks = 200;

    public ArmSubsystem(HardwareMap hardwareMapReference, Telemetry telemetryReference, SubsystemInitMode initMode) {
        map = hardwareMapReference;
        telemetry = telemetryReference;

        ArmLiftMotor = map.get(DcMotor.class, BotBits.ArmLiftMotor);
        ArmExtendMotor = map.get(DcMotor.class, BotBits.ArmExtendMotor);
        ArmTouchSensor = map.get(TouchSensor.class, BotBits.ArmTouchSensor);
        GrabServo = map.get(Servo.class, BotBits.GrabServo);

        if (initMode == SubsystemInitMode.Autonomous){
            InitAutonomous();
        }
    }

    private void InitAutonomous(){
        ArmLiftMotor.setTargetPosition(0);
        ArmExtendMotor.setTargetPosition(0);

        ArmLiftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        ArmExtendMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        ArmLiftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        ArmExtendMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public void ArmRaiseLowerByController(double yInput, boolean SlowMode){

        //TODO:  Update telemetry text so that shows what motor is in slow mode
        telemetry.addData("SlowMode", SlowMode);

        double DefaultPowerFactor = 3.5;
        double PowerFactor = 0;
        double Power = 0;

        // Flip value so that the arm moves in the expected direction
        yInput = -yInput;
        int currentPosition = ArmLiftMotor.getCurrentPosition();

        if (SlowMode) {
            PowerFactor = DefaultPowerFactor * 8;
        } else {
            PowerFactor = DefaultPowerFactor;
        }

        if (currentPosition < 150){
            PowerFactor = PowerFactor / 2;
        }

        if (yInput != 0) {
            if (yInput > 0){
                if (currentPosition < _armRaiseMaxClicks) {
                    Power = yInput / PowerFactor;
                }
            } else if (yInput < 0) {
                if (!ArmTouchSensor.isPressed()){
                    Power = .02;
                }
            }

            ArmLiftMotor.setPower(Power);

        } else if (yInput == 0) {
            // if the arm is coming down, brake will stop it from going *UP*.
            // set the power to a low 'going up' value, so that brake will keep it from going down.
            ArmLiftMotor.setPower(.05);
        }

        ArmLiftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        telemetry.addData("ArmLiftMotor Power", ArmLiftMotor.getPower());
        telemetry.addData("ArmLiftMotor Start Position", currentPosition);
        telemetry.addData("ArmLiftMotor ZeroPowerBehavior",ArmLiftMotor.getZeroPowerBehavior());
    }

    public void ArmExtendRetractByController(double Input, boolean SlowMode) {

        //TODO:  Update telemetry text so that shows what motor is in slow mode
        telemetry.addData("SlowMode",SlowMode);

        double DefaultPowerFactor = 1.5;
        double PowerFactor = 0;

        if (SlowMode) {
            PowerFactor = DefaultPowerFactor * 2;
        } else {
            PowerFactor = DefaultPowerFactor;
        }

        ArmExtendMotor.setPower(Input/PowerFactor);
        ArmExtendMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        //TODO:  Update telemetry text so that it makes sense.  See the method above this one.
        telemetry.addData("SelectedItem","Arm Extend Motor");
        telemetry.addData("MotorZeroPowerBehavior",ArmExtendMotor.getZeroPowerBehavior());
        telemetry.addData("Power", ArmExtendMotor.getPower());
    }



    // TODO:  take in paramater of how many clicks to raise (see Raise)
    public double Extend () {
        telemetry.addData("Arm subsystem method", "Extend");
// TODO:  Make arm extend
        return 0;
    }

    // TODO:  take in paramater of how many clicks to raise (see Raise)
    public double ExtendFully () {
        telemetry.addData("Arm subsystem method", "ExtendFully");
// TODO:  Make arm extend
        return 0;
    }

    // TODO:  take in paramater of how many clicks to raise (see Raise)
    public double Retract () {
        telemetry.addData("Arm subsystem method", "Retract");
// TODO:  Make arm retract
        return 0;
    }

    // TODO:  take in paramater of how many clicks to raise (see Raise)
    public double RetractFully () {
        telemetry.addData("Arm subsystem method", "RetractFully");
// TODO:  Make arm retract
        return 0;
    }





    public double Raise (int clicks, double motorPower) {
        telemetry.addData("Arm subsystem method", "Extend");

        int currPosition = ArmLiftMotor.getCurrentPosition();
        int targetPosition = currPosition + armRaiseIncrement;

        if (targetPosition >= _armRaiseMaxClicks) {
            targetPosition = _armRaiseMaxClicks;
        }

        ArmLiftMotor.setPower(.2);
        ArmLiftMotor.setTargetPosition(targetPosition);

        telemetry.addData("Arm raise position:     ", targetPosition);
        telemetry.update();

        return targetPosition;
    }

    public double RaiseFully (double motorPower) {
        telemetry.addData("Arm subsystem method", "RaiseFully");

        double currPosition;

        do {
            currPosition = Raise(armRaiseIncrement, motorPower);
            sleep(_cycleMiliseconds);
        } while (currPosition < _armRaiseMaxClicks);

        return position;
    }

    // TODO:  take in paramater of how many clicks to raise (see Raise)
    public double Lower (int clicks, double motorPower) {
        telemetry.addData("Arm subsystem method", "Retract");
        // TODO:  Make arm retract the specified amount
        return 0;
    }

    // TODO:  take in motor speed as a parameter to pass to the motor
    public double LowerFully (double motorPower) {
        telemetry.addData("Arm subsystem method", "LowerFully");
        // TODO:  Make arm lower all the way
        ArmLiftMotor.setPower(motorPower);
        ArmLiftMotor.setPower(-.05);
        ArmLiftMotor.setTargetPosition(0);
        return 0;
    }



    public double Release () {
        telemetry.addData("Grabber subsystem method", "Release");

        double currPosition = GrabServo.getPosition();
        double targetPosition = currPosition + _increment;

        if (targetPosition >= _maxPosition) {
            targetPosition = _maxPosition;
        }

        GrabServo.setPosition(targetPosition);

        telemetry.addData("Servo Position", position);
        telemetry.update();

        return targetPosition;
    }

    public void ReleaseFully() {
        double currPosition;

        do {
            currPosition = Release();
            sleep(_cycleMiliseconds);
        } while (currPosition < _maxPosition);
    }

    public void Grab(){
        telemetry.addData("Grabber subsystem method", "Grab");

        double currPosition = GrabServo.getPosition();
        double targetPosition = currPosition - _increment;

        if (targetPosition <= _minPosition) {
            targetPosition = _minPosition;
        }

        GrabServo.setPosition(targetPosition);
    }

    public void GrabFully() {
        telemetry.addData("Subsystem method", "Grab");

        double currPosition;

        do {
            currPosition = Release();
            sleep(_cycleMiliseconds);
        } while (currPosition > _minPosition);
    }
}