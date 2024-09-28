package edu.edina.opmodes.Test;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

import edu.edina.definitions.BotBits;

@TeleOp(name= "TestOpMode", group= "Test")
public class TestOpMode extends LinearOpMode {

    private DcMotor leftFrontDrive = null;
    private DcMotor leftBackDrive = null;
    private DcMotor rightFrontDrive = null;
    private DcMotor rightBackDrive = null;
    private double yDriveInput = gamepad1.left_stick_y;

    private Boolean leftBumperPushed = false;
    private int SelectedItem = 0;

    @Override
    public void runOpMode() {
        leftFrontDrive  = hardwareMap.get(DcMotor.class, BotBits.FrontLeftDriveMotor);
        leftBackDrive  = hardwareMap.get(DcMotor.class, BotBits.BackLeftDriveMotor);
        rightFrontDrive = hardwareMap.get(DcMotor.class, BotBits.FrontRightDriveMotor);
        rightBackDrive = hardwareMap.get(DcMotor.class, BotBits.BackRightDriveMotor);

        leftFrontDrive.setDirection(DcMotorSimple.Direction.FORWARD);
        leftBackDrive.setDirection(DcMotorSimple.Direction.FORWARD);
        rightFrontDrive.setDirection(DcMotorSimple.Direction.FORWARD);
        rightBackDrive.setDirection(DcMotorSimple.Direction.FORWARD);

        leftFrontDrive.setPower(0);
        leftBackDrive.setPower(0);
        rightFrontDrive.setPower(0);
        rightBackDrive.setPower(0);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            yDriveInput = gamepad1.left_stick_y;
            leftBumperPushed =  gamepad1.left_bumper;

            if (leftBumperPushed){
                if(SelectedItem <= 2){
                    SelectedItem++;
                } else {
                    SelectedItem =0;
                }
//                wait(1000);
//                Thread.sleep(1000)throws InterruptedException;
            }

            switch (SelectedItem) {
                case 0:
                    leftFrontDrive.setPower(yDriveInput);
                    telemetry.addData("SelectedItem","leftFrontDrive");
                    telemetry.addData("MotorDirection",leftFrontDrive.getDirection());
                    break;
                case 1:
                    rightFrontDrive.setPower(yDriveInput);
                    telemetry.addData("SelectedItem","rightFrontDrive");
                    break;
                case 2:
                    leftBackDrive.setPower(yDriveInput);
                    telemetry.addData("SelectedItem","leftBackDrive");
                    break;
                case 3:
                    rightBackDrive.setPower(yDriveInput);
                    telemetry.addData("SelectedItem","rightBackDrive");
                    break;
            }

            telemetry.addData("leftStickInput",yDriveInput);
            telemetry.update();
        }
    }
}
