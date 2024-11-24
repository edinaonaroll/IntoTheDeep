package edu.edina.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
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
    boolean rampUp = true;
    static final int _cycleMiliseconds  =   50;     // period of each cycle

    // arm raise limits
    static final int _armRaiseMaxClicks = 350;
    static final int _armRaiseMinClicks =  0;

    // arm extend limits
    static final int _armExtendMaxClicks = 200;

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
                else {
                    ArmLiftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                }
            } else if (yInput < 0) {
                if (!ArmTouchSensor.isPressed()){
                    //Power = .02;
                    if (currentPosition > 300) {
                        ArmLiftMotor.setPower(yInput);
                    }
                    else if (currentPosition > 50) {
                        ArmLiftMotor.setPower(.02);
                    }
                    else if (currentPosition > 20) {
                        ArmLiftMotor.setPower(.15);
                    }
                    ArmLiftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
                }
            }

            ArmLiftMotor.setPower(Power);

        } else if (yInput == 0) {
            // if the arm is coming down, brake will stop it from going *UP*.
            // set the power to a low 'going up' value, so that brake will keep it from going down.
            ArmLiftMotor.setPower(.05);
            ArmLiftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        }

        telemetry.addData("ArmLiftMotor Power", ArmLiftMotor.getPower());
        telemetry.addData("ArmLiftMotor Start Position", currentPosition);
        telemetry.addData("ArmLiftMotor ZeroPowerBehavior",ArmLiftMotor.getZeroPowerBehavior());
    }

    public void ArmExtendRetractByController(double Input, boolean SlowMode) {

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



    // TODO:  take in paramater of how many clicks to extend (see Raise)
    public void Extend () {
        telemetry.addData("Arm subsystem method", "Extend");
    }

    // TODO:  take in paramater of how many clicks to extend (see Raise)
    public void ExtendFully () {
        telemetry.addData("Arm subsystem method", "ExtendFully");
    }

    // TODO:  take in paramater of how many clicks to retract (see Raise)
    public void Retract () {
        telemetry.addData("Arm subsystem method", "Retract");
    }

    // TODO:  take in paramater of how many clicks to retract (see Raise)
    public void RetractFully () {
        telemetry.addData("Arm subsystem method", "RetractFully");
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
            sleep(_cycleMiliseconds);
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
            sleep(_cycleMiliseconds);
        } while(currPosition > _armRaiseMinClicks);

        return currPosition;
    }
}