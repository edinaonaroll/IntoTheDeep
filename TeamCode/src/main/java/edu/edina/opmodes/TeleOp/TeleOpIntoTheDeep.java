package edu.edina.opmodes.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import edu.edina.definitions.BotBits;

@TeleOp(name= "IntoTheDeepTeleOp", group= "TeleOp")
public class TeleOpIntoTheDeep extends LinearOpMode
{

    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor leftFrontDrive = null;
    private DcMotor leftBackDrive = null;
    private DcMotor rightFrontDrive = null;
    private DcMotor rightBackDrive = null;

    @Override
    public void runOpMode() {

        leftFrontDrive  = hardwareMap.get(DcMotor.class, BotBits.FrontLeftDriveMotor);
        leftBackDrive  = hardwareMap.get(DcMotor.class, BotBits.BackLeftDriveMotor);
        rightFrontDrive = hardwareMap.get(DcMotor.class, BotBits.FrontRightDriveMotor);
        rightBackDrive = hardwareMap.get(DcMotor.class, BotBits.BackRightDriveMotor);

        leftFrontDrive.setDirection(DcMotor.Direction.REVERSE);
        leftBackDrive.setDirection(DcMotor.Direction.REVERSE);
        rightFrontDrive.setDirection(DcMotor.Direction.FORWARD);
        rightBackDrive.setDirection(DcMotor.Direction.FORWARD);

        telemetry.addData("Status", "Initialized");

        waitForStart();

        while (opModeIsActive())
        {
            double xDriveInput = gamepad1.left_stick_x * 1.1; // Counteract imperfect strafing
            double yDriveInput = -gamepad1.left_stick_y; // Remember, Y stick value is reversed
            double turnInput = gamepad1.right_stick_x;

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

            telemetry.addData("status", "Running");
            telemetry.update();

            idle();
        }

        runtime.reset();
    }
}
