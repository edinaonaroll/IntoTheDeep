package edu.edina.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import edu.edina.definitions.BotBits;
import edu.edina.definitions.SubsystemInitMode;

public class ArmSubsystem extends SubsystemBase {

    Telemetry telemetry;
    HardwareMap map;

    private DcMotor ArmLiftMotor = null;
    private DcMotor ArmExtendMotor = null;
    private TouchSensor ArmTouchSensor = null;

    int armRaiseIncrement = 10;
    int armExtendIncrement = 10;
    int armRetractIncrement = 10;
    boolean rampUp = true;
    static final int _raiseCycleMiliseconds  =   30;     // period of each cycle
    static final int _extendCycleMiliseconds  =   30;     // period of each cycle

    // arm raise limits
    static final int _armRaiseMaxClicks = 350;
    static final int _armRaiseMinClicks =  0;

    // arm extend limits
    static final int _armExtendMaxClicks = 570;
    static final int _armExtendMinClicks = 46;
    public ArmSubsystem(HardwareMap hardwareMapReference, Telemetry telemetryReference, SubsystemInitMode initMode) {
        map = hardwareMapReference;
        telemetry = telemetryReference;

        ArmLiftMotor = map.get(DcMotor.class, BotBits.ArmLiftMotor);
        ArmExtendMotor = map.get(DcMotor.class, BotBits.ArmExtendMotor);
        ArmTouchSensor = map.get(TouchSensor.class, BotBits.ArmTouchSensor);

        if (initMode == SubsystemInitMode.Autonomous){
            InitAutonomous();
        }
    }

    private void InitAutonomous(){

        ArmLiftMotor.setTargetPosition(0);
        ArmExtendMotor.setTargetPosition(0);

        ArmExtendMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        ArmLiftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        ArmExtendMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        ArmLiftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        ArmExtendMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public double ArmRaiseLowerByNumbers(double yInput, boolean SlowMode) {

        //TODO:  Update telemetry text so that shows what motor is in slow mode
        telemetry.addData("SlowMode", SlowMode);

        double DefaultPowerFactor = 3.5;
        double PowerFactor = 0;
        double Power = 0;
        double position=ArmLiftMotor.getCurrentPosition();

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
                else {
                    ArmLiftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                }

                ArmLiftMotor.setPower(Power);
            } else if (yInput < 0) {
                if (!ArmTouchSensor.isPressed()){
                    //Power = .02;
                    if (currentPosition > 170) {
                        ArmLiftMotor.setPower(yInput *.8);
                    }
                    else if (currentPosition > 60) {
                        ArmLiftMotor.setPower(.001);
                    }
                    else if (currentPosition > 30) {
                        ArmLiftMotor.setPower(.01);
                    }
                    ArmLiftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
                }
            }

        } else if (yInput == 0) {
            // if the arm is coming down, brake will stop it from going *UP*.
            // set the power to a low 'going up' value, so that brake will keep it from going down.
            ArmLiftMotor.setPower(.05);
            ArmLiftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        }

        telemetry.addData("ArmLiftMotor Power", ArmLiftMotor.getPower());
        telemetry.addData("ArmLiftMotor current Position", currentPosition);
        telemetry.addData("ArmLiftMotor ZeroPowerBehavior",ArmLiftMotor.getZeroPowerBehavior());

        return position;
    }

    public double ArmExtendRetractByNumbers(double Input, boolean SlowMode) {

        double DefaultPowerFactor = 1.5;
        double PowerFactor = 0;
        double currentPosition = ArmExtendMotor.getCurrentPosition();

        if (SlowMode) {
            PowerFactor = DefaultPowerFactor * 2;
        } else {
            PowerFactor = DefaultPowerFactor;
        }

        ArmExtendMotor.setPower(Input/PowerFactor);
        ArmExtendMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        //TODO:  Update telemetry text so that it makes sense.  See the method above this one.
        telemetry.addData("ArmExtendMotor Position: ", currentPosition);
        telemetry.addData("ArmExtendMotor Power: ", ArmExtendMotor.getPower());

        return currentPosition;
    }


    // TODO:  take in paramater of how many clicks to extend (see Raise)
    public double ExtendByNumbers(double motorPower,int targetMaxPosition) {

        int currPosition;
        int targetPosition;

        do {
            currPosition = ArmExtendMotor.getCurrentPosition();
            targetPosition = currPosition + armExtendIncrement;

            ArmExtendMotor.setTargetPosition(targetPosition);
            ArmExtendMotor.setPower(motorPower);

            telemetry.addData("ArmExtendMotor Position", currPosition);
            telemetry.update();

            sleep(_extendCycleMiliseconds);

        } while (currPosition < _armExtendMaxClicks && currPosition < targetMaxPosition);

        return currPosition;
    }

    // TODO:  take in paramater of how many clicks to retract (see Raise)
    public double RetractByNumbers(double motorPower,int targetMaxPosition) {
        telemetry.addData("Arm subsystem method", "RetractFully");

        int currPosition;
        int targetPosition;

        do {
            currPosition = ArmExtendMotor.getCurrentPosition();
            targetPosition = currPosition - armRetractIncrement;

            ArmExtendMotor.setTargetPosition(targetPosition);
            ArmExtendMotor.setPower(motorPower);

            telemetry.addData("ArmExtendMotor Position", currPosition);
            telemetry.update();

            sleep(_extendCycleMiliseconds);

        } while (currPosition > _armExtendMinClicks && currPosition > targetMaxPosition);

        return currPosition;
    }

    public double Raise (double motorPower) {
        telemetry.addData("Arm subsystem method", "Extend");

        int currPosition = ArmLiftMotor.getCurrentPosition();
        int targetPosition = currPosition + armRaiseIncrement;

        if (targetPosition >= _armRaiseMaxClicks) {
            targetPosition = _armRaiseMaxClicks;
        }

        ArmLiftMotor.setPower(motorPower);
        ArmLiftMotor.setTargetPosition(targetPosition);

        return targetPosition;
    }

    public double RaiseFully(double motorPower) {
        telemetry.addData("Arm subsystem method", "ExtendFully");

        double currPosition;

        do {
            currPosition = Raise(motorPower);
            sleep(_raiseCycleMiliseconds);
        } while (currPosition < _armRaiseMaxClicks);

        return currPosition;
    }


    public double Lower (double motorPower) {
        int currPosition = ArmLiftMotor.getCurrentPosition();
        int targetPosition = currPosition - armRaiseIncrement;

        if (targetPosition < _armRaiseMinClicks){
            targetPosition = _armRaiseMinClicks;
        }

        ArmLiftMotor.setPower(motorPower);
        ArmLiftMotor.setTargetPosition(targetPosition);

        return targetPosition;
    }

    public double LowerFully (double motorPower) {
        telemetry.addData("Arm subsystem method", "LowerFully");

        double currPosition;

        do {
            currPosition = Lower(motorPower);
            sleep(_raiseCycleMiliseconds);
        } while(currPosition > _armRaiseMinClicks);

        return currPosition;
    }
}