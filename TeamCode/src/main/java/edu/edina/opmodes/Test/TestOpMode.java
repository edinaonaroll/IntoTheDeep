package edu.edina.opmodes.Test;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import edu.edina.definitions.BotBits;

@TeleOp(name= "TestOpMode", group= "Test")
public class TestOpMode extends LinearOpMode {

    private DcMotor leftFrontDrive = null;
    private DcMotor leftBackDrive = null;
    private DcMotor rightFrontDrive = null;
    private DcMotor rightBackDrive = null;
    private int SelectedItem = 0;

    @Override
    public void runOpMode() {
        leftFrontDrive  = hardwareMap.get(DcMotor.class, BotBits.FrontLeftDriveMotor);
        leftBackDrive  = hardwareMap.get(DcMotor.class, BotBits.BackLeftDriveMotor);
        rightFrontDrive = hardwareMap.get(DcMotor.class, BotBits.FrontRightDriveMotor);
        rightBackDrive = hardwareMap.get(DcMotor.class, BotBits.BackRightDriveMotor);


        telemetry.addData("Status", "Initialized");
        waitForStart();

        while (opModeIsActive()) {
            double yDriveInput = -gamepad1.left_stick_y; // Remember, Y stick value is reversed
            Boolean leftBumperPushed =  gamepad1.left_bumper;


            if(leftBumperPushed){
                if(SelectedItem <= 2){
                    SelectedItem++;

                }else{
                    SelectedItem =0;
                }
            }

            switch(SelectedItem){
                case 0:
                    leftFrontDrive.setPower(yDriveInput);
                    telemetry.addData("SelectedItem","leftFrontDrive");
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
