package edu.edina.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.Telemetry;

import edu.edina.definitions.BotBits;

public class ChassisSubsystem {

    HardwareMap map;
    Telemetry telemetry;

    private DcMotor frontLeftDrive = null;
    private DcMotor backLeftDrive = null;
    private DcMotor frontRightDrive = null;
    private DcMotor backRightDrive = null;

    public ChassisSubsystem(HardwareMap hardwareMapReference, Telemetry telemetryReference) {
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
    }

    public void Drive (double xDriveInput, double yDriveInput, double turnInput, boolean Slowmode) {

        // flip sign because input needs to be the other way for sensible driving
        yDriveInput = -yDriveInput;

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
}