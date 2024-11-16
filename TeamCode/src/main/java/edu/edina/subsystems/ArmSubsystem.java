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
    static final double _minPosition    =  0.7;     // Minimum rotational position

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

        //TODO:  Update telemetry text so that it makes sense
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
        //TODO:  Update telemetry text so that it makes sense
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

        //TODO:  Update telemetry text so that it makes sense
        telemetry.addData("SelectedItem","Arm Extend Motor");
        telemetry.addData("MotorZeroPowerBehavior",ArmExtendMotor.getZeroPowerBehavior());
        telemetry.addData("Power", ArmExtendMotor.getPower());
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





    public void Raise (int clicks) {
        telemetry.addData("Arm subsystem method", "Extend");
// TODO:  Make arm raise
    }

    public void RaiseFully () {
        telemetry.addData("Arm subsystem method", "ExtendFully");

        int armLiftMotorStartPosition = ArmLiftMotor.getCurrentPosition();
        int armLiftMotorTargetPosition = armLiftMotorStartPosition + 200;

        if (armLiftMotorTargetPosition > _armRaiseMaxClicks) {
            armLiftMotorTargetPosition = _armRaiseMaxClicks;
        }

        if (armLiftMotorTargetPosition < _armRaiseMaxClicks)
        {
            ArmLiftMotor.setPower(MotorSpeed.Percent_10);
            ArmLiftMotor.setTargetPosition(armLiftMotorTargetPosition);
            ArmLiftMotor.setPower(MotorSpeed.Percent_0);
        }
    }

    public void Lower () {
        telemetry.addData("Arm subsystem method", "Retract");
// TODO:  Make arm retract
    }

    public void LowerFully () {
        telemetry.addData("Arm subsystem method", "RetractFully");
// TODO:  Make arm retract
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