package edu.edina.opmodes.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import edu.edina.definitions.BotBits;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name="24-25Teleop", group="24-25Teleop")
//@Disabled
public class BasicTeleOp extends LinearOpMode {


    @Override
    public void runOpMode() throws InterruptedException {

        // Declare drive motors
        // Make sure your ID's match your configuration
        DcMotor leftFront = hardwareMap.dcMotor.get(BotBits.FrontLeftDriveMotor);
        DcMotor leftBack = hardwareMap.dcMotor.get(BotBits.BackLeftDriveMotor);
        DcMotor rightFront = hardwareMap.dcMotor.get(BotBits.FrontRightDriveMotor);
        DcMotor rightBack = hardwareMap.dcMotor.get(BotBits.BackRightDriveMotor);


        leftFront.setDirection(DcMotorSimple.Direction.FORWARD);
        leftBack.setDirection(DcMotorSimple.Direction.FORWARD);
        rightFront.setDirection(DcMotorSimple.Direction.FORWARD);
        rightBack.setDirection(DcMotorSimple.Direction.REVERSE);

        waitForStart();

        while (opModeInInit())


        if (isStopRequested()) return;

        while (opModeIsActive()) {

            //////////// Drivetrain code ////////////

            double y = -gamepad1.left_stick_y; // Remember, Y stick value is reversed
            double x = gamepad1.left_stick_x * 1.1; // Counteract imperfect strafing
            double rx = gamepad1.right_stick_x;

            double r = Math.hypot(x, y);
            double robotAngle = Math.atan2(y, x) - Math.PI / 4;
            double rightX = rx;
            final double v1 = r * Math.cos(robotAngle) + rightX;
            final double v2 = r * Math.sin(robotAngle) - rightX;
            final double v3 = r * Math.sin(robotAngle) + rightX;
            final double v4 = r * Math.cos(robotAngle) - rightX;

            leftFront.setPower(v1);
            rightFront.setPower(v2);
            leftBack.setPower(v3);
            rightBack.setPower(v4);


            telemetry.addData("status", "Running");
            telemetry.update();

            idle();

        }
    }
}
