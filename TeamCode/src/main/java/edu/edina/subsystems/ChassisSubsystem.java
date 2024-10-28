package edu.edina.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.Telemetry;

import edu.edina.definitions.BotBits;

public class ChassisSubsystem {

    HardwareMap map;
    Telemetry telemetry;

    private DcMotor leftFrontDrive = null;
    private DcMotor leftBackDrive = null;
    private DcMotor rightFrontDrive = null;
    private DcMotor rightBackDrive = null;

    public ChassisSubsystem(HardwareMap hardwareMapReference, Telemetry telemetryReference) {
        map = hardwareMapReference;
        telemetry = telemetryReference;

        leftFrontDrive =    map.get(DcMotor.class, BotBits.FrontLeftDriveMotor);
        leftBackDrive =     map.get(DcMotor.class, BotBits.BackLeftDriveMotor);
        rightFrontDrive =   map.get(DcMotor.class, BotBits.FrontRightDriveMotor);
        rightBackDrive =    map.get(DcMotor.class, BotBits.BackRightDriveMotor);

        leftFrontDrive.setDirection(DcMotor.Direction.FORWARD);
        leftBackDrive.setDirection(DcMotor.Direction.FORWARD);
        rightFrontDrive.setDirection(DcMotor.Direction.FORWARD);
        rightBackDrive.setDirection(DcMotor.Direction.REVERSE);

    }

    public void Drive (double xDriveInput, double yDriveInput, double turnInput, boolean Slowmode) {

        double driveInputAngle = Math.hypot(xDriveInput, yDriveInput);
        double robotAngle = Math.atan2(yDriveInput, xDriveInput) - Math.PI / 4;

        final double frontLeftMotorPower = driveInputAngle * Math.cos(robotAngle) + turnInput;
        final double backLeftMotorPower = driveInputAngle * Math.sin(robotAngle) - turnInput;
        final double frontRightMotorPower = driveInputAngle * Math.sin(robotAngle) + turnInput;
        final double backRightMotorPower = driveInputAngle * Math.cos(robotAngle) - turnInput;

        leftFrontDrive.setPower(frontLeftMotorPower);
        leftBackDrive.setPower(backLeftMotorPower);
        rightFrontDrive.setPower(frontRightMotorPower);
        rightBackDrive.setPower(backRightMotorPower);

        telemetry.addData("xDriveInput", xDriveInput);
        telemetry.addData("yDriveInput", yDriveInput);
        telemetry.addData("turnInput", turnInput);

    }

}