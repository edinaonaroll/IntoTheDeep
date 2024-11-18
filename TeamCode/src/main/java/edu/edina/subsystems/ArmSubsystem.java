package edu.edina.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import edu.edina.definitions.BotBits;
import edu.edina.definitions.MotorSpeed;
import edu.edina.definitions.SubsystemInitMode;

public class ArmSubsystem {

    Telemetry telemetry;
    HardwareMap map;

    private DcMotor ArmLiftMotor = null;
    private DcMotor ArmExtendMotor = null;
    private TouchSensor ArmTouchSensor = null;

    private Servo GrabServo = null;
    double  position = _minPosition;
    boolean rampUp = true;

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
    public void Extend () {
        telemetry.addData("Arm subsystem method", "Extend");
// TODO:  Make arm extend
    }

    // TODO:  take in paramater of how many clicks to raise (see Raise)
    public void ExtendFully () {
        telemetry.addData("Arm subsystem method", "ExtendFully");
// TODO:  Make arm extend
    }

    // TODO:  take in paramater of how many clicks to raise (see Raise)
    public void Retract () {
        telemetry.addData("Arm subsystem method", "Retract");
// TODO:  Make arm retract
    }

    // TODO:  take in paramater of how many clicks to raise (see Raise)
    public void RetractFully () {
        telemetry.addData("Arm subsystem method", "RetractFully");
// TODO:  Make arm retract
    }





    public void Raise (int clicks) {
        telemetry.addData("Arm subsystem method", "Extend");
        // TODO:  Get current arm position

        // TODO:  Get target position by adding how much to raise and current positions

        // TODO:  If target position is too high, then set a lower value

        // TODO:  Set motor power

        // TODO:  Set target position
    }

    public void RaiseFully () {
        telemetry.addData("Arm subsystem method", "ExtendFully");

//        ArmLiftMotor.setPower(MotorSpeed.Percent_10);
        ArmLiftMotor.setPower(.4);
        ArmLiftMotor.setTargetPosition(_armRaiseMaxClicks);
    }

    // TODO:  take in paramater of how many clicks to raise (see Raise)
    public void Lower () {
        telemetry.addData("Arm subsystem method", "Retract");
        // TODO:  Make arm retract the specified amount
    }

    // TODO:  take in motor speed as a parameter to pass to the motor
    public void LowerFully () {
        telemetry.addData("Arm subsystem method", "LowerFully");
        // TODO:  Make arm lower all the way
//        ArmLiftMotor.setPower(MotorSpeed.Percent_10);
//        ArmLiftMotor.setPower(.4);
//        ArmLiftMotor.setTargetPosition(0);
    }



    public void Release () {
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

    public void Grab(){
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