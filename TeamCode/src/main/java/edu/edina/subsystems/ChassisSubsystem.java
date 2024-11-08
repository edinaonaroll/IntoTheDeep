package edu.edina.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.Telemetry;

import edu.edina.definitions.BotBits;
import edu.edina.definitions.MotorSpeed;
import edu.edina.definitions.SubsystemInitMode;

public class ChassisSubsystem {

    HardwareMap map;
    Telemetry telemetry;

    private DcMotor frontLeftDrive = null;
    private DcMotor backLeftDrive = null;
    private DcMotor frontRightDrive = null;
    private DcMotor backRightDrive = null;

    private int frontLeftDriveMotorPosition = 0;
    private int backLeftDriveMotorPosition = 0;
    private int frontRightDriveMotorPosition = 0;
    private int backRightDriveMotorPosition = 0;

    private double clicksPerInch = 52; // 54 best for slow speed
    private double clicksPerDeg = 14; // empirically measured

    public ChassisSubsystem(HardwareMap hardwareMapReference, Telemetry telemetryReference, SubsystemInitMode initMode) {
        map = hardwareMapReference;
        telemetry = telemetryReference;

        frontLeftDrive =    map.get(DcMotor.class, BotBits.FrontLeftDriveMotor);
        backLeftDrive =     map.get(DcMotor.class, BotBits.BackLeftDriveMotor);
        frontRightDrive =   map.get(DcMotor.class, BotBits.FrontRightDriveMotor);
        backRightDrive =    map.get(DcMotor.class, BotBits.BackRightDriveMotor);

        frontLeftDrive.setDirection(DcMotor.Direction.FORWARD);
        backLeftDrive.setDirection(DcMotor.Direction.FORWARD);
        frontRightDrive.setDirection(DcMotor.Direction.FORWARD);
        backRightDrive.setDirection(DcMotor.Direction.REVERSE);

        SetMotorDirection(DcMotor.Direction.FORWARD);
        
        if (initMode == SubsystemInitMode.Autonomous){
            InitAutonomous();
        }
    }

    public void SetMotorDirection(DcMotor.Direction direction) {
       if(direction==DcMotor.Direction.FORWARD){
           frontLeftDrive.setDirection(DcMotor.Direction.FORWARD);
           backLeftDrive.setDirection(DcMotor.Direction.FORWARD);
           frontRightDrive.setDirection(DcMotor.Direction.FORWARD);
           backRightDrive.setDirection(DcMotor.Direction.REVERSE);
       } else {
           frontLeftDrive.setDirection(DcMotor.Direction.REVERSE);
           backLeftDrive.setDirection(DcMotor.Direction.REVERSE);
           frontRightDrive.setDirection(DcMotor.Direction.REVERSE);
           backRightDrive.setDirection(DcMotor.Direction.FORWARD);
       }
    }

    public void DriveByController(double xDriveInput, double yDriveInput, double turnInput, boolean Slowmode) {

        // flip sign because input needs to be the other way for sensible driving
        if (Slowmode){
            yDriveInput = -yDriveInput / 2;
        } else {
            yDriveInput = -yDriveInput;
        }

        // Counteract imperfect strafing
        xDriveInput = xDriveInput * 1.1;

        double driveInputAngle = Math.hypot(xDriveInput, yDriveInput);
        double robotAngle = Math.atan2(yDriveInput, xDriveInput) - Math.PI / 4;

        final double frontLeftMotorPower = driveInputAngle * Math.cos(robotAngle) + turnInput;
        final double backLeftMotorPower = driveInputAngle * Math.sin(robotAngle) + turnInput;
        final double frontRightMotorPower = driveInputAngle * Math.sin(robotAngle) - turnInput;
        final double backRightMotorPower = driveInputAngle * Math.cos(robotAngle) - turnInput;

        frontLeftDrive.setPower(frontLeftMotorPower);
        backLeftDrive.setPower(backLeftMotorPower);
        frontRightDrive.setPower(frontRightMotorPower);
        backRightDrive.setPower(backRightMotorPower);

        telemetry.addData("xDriveInput", xDriveInput);
        telemetry.addData("yDriveInput", yDriveInput);
        telemetry.addData("turnInput", turnInput);
    }

    private void InitAutonomous(){
        frontLeftDrive.setTargetPosition(0);
        backLeftDrive.setTargetPosition(0);
        frontRightDrive.setTargetPosition(0);
        backRightDrive.setTargetPosition(0);

        frontLeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        frontLeftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public void DriveForward(int Inches, double speed){
        GetMotorPositions();
        SetMotorDirection(DcMotor.Direction.FORWARD);

        // calculate new targets
        frontLeftDriveMotorPosition += Inches * clicksPerInch;
        frontRightDriveMotorPosition += Inches * clicksPerInch;
        backLeftDriveMotorPosition += Inches * clicksPerInch;
        backRightDriveMotorPosition += Inches * clicksPerInch;

        SetMotorSpeed(speed);
        SetMotorPositions();
        WriteTelemetry("Move Forward");
        SetMotorSpeed(MotorSpeed.Percent_0);
    }

    public void DriveBack(int Inches, double speed){
        GetMotorPositions();
        SetMotorDirection(DcMotor.Direction.REVERSE);

        // calculate new targets
        frontLeftDriveMotorPosition += Inches * clicksPerInch;
        frontRightDriveMotorPosition += Inches * clicksPerInch;
        backLeftDriveMotorPosition += Inches * clicksPerInch;
        backRightDriveMotorPosition += Inches * clicksPerInch;

        SetMotorSpeed(speed);
        SetMotorPositions();
        WriteTelemetry("Move Forward");
        SetMotorSpeed(MotorSpeed.Percent_0);
    }

    public void DriveLeft(int Inches, double speed){
        GetMotorPositions();
        SetMotorDirection(DcMotorSimple.Direction.FORWARD);

        // calculate new targets
        frontLeftDriveMotorPosition -= Inches * clicksPerInch;
        frontRightDriveMotorPosition += Inches * clicksPerInch;
        backLeftDriveMotorPosition += Inches * clicksPerInch;
        backRightDriveMotorPosition -= Inches * clicksPerInch;

        SetMotorSpeed(speed);
        SetMotorPositions();
        WriteTelemetry("Move Forward");
        SetMotorSpeed(MotorSpeed.Percent_0);
    }

    public void DriveRight(int Inches, double speed){
        GetMotorPositions();
        SetMotorDirection(DcMotorSimple.Direction.FORWARD);
        
        // calculate new targets
        frontLeftDriveMotorPosition += Inches * clicksPerInch;
        frontRightDriveMotorPosition -= Inches * clicksPerInch;
        backLeftDriveMotorPosition -= Inches * clicksPerInch;
        backRightDriveMotorPosition+= Inches * clicksPerInch;

        SetMotorSpeed(speed);
        SetMotorPositions();
        WriteTelemetry("Move Forward");
        SetMotorSpeed(MotorSpeed.Percent_0);
    }

    private void GetMotorPositions(){
        frontLeftDriveMotorPosition = frontLeftDrive.getCurrentPosition();
        backLeftDriveMotorPosition = backLeftDrive.getCurrentPosition();
        frontRightDriveMotorPosition = frontRightDrive.getCurrentPosition();
        backRightDriveMotorPosition = backRightDrive.getCurrentPosition();
    }

    private void SetMotorPositions() {
        frontLeftDrive.setTargetPosition(frontLeftDriveMotorPosition);
        backLeftDrive.setTargetPosition(backLeftDriveMotorPosition);
        frontRightDrive.setTargetPosition(frontRightDriveMotorPosition);
        backRightDrive.setTargetPosition(backRightDriveMotorPosition);
    }

    private void SetMotorSpeed(double speed) {
        frontLeftDrive.setPower(speed);
        backLeftDrive.setPower(speed);
        frontRightDrive.setPower(speed);
        backRightDrive.setPower(speed);
    }

    private void WriteTelemetry(String action){

        while (frontLeftDrive.isBusy() && backLeftDrive.isBusy() &&
                frontRightDrive.isBusy() && backRightDrive.isBusy()) {
            telemetry.addLine(action);
            telemetry.addData("Target", "%7d :%7d",
                    frontLeftDriveMotorPosition,
                    backLeftDriveMotorPosition,
                    frontRightDriveMotorPosition,
                    backRightDriveMotorPosition);
            telemetry.addData("Actual", "%7d :%7d",
                    frontLeftDrive.getCurrentPosition(),
                    backLeftDrive.getCurrentPosition(),
                    frontRightDrive.getCurrentPosition(),
                    backRightDrive.getCurrentPosition());
            telemetry.update();
        }
    }
}