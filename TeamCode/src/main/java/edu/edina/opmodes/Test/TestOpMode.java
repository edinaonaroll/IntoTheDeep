package edu.edina.opmodes.Test;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import edu.edina.definitions.BotBits;
import edu.edina.subsystems.FlagSubsystem;

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

        FlagSubsystem flag = new FlagSubsystem(hardwareMap, telemetry);

        telemetry.addData("Status", "Initialized");
        waitForStart();

        while (opModeIsActive()) {
            double yInput = -gamepad1.left_stick_y; // Remember, Y stick value is reversed
            Boolean leftBumperPushed =  gamepad1.left_bumper;


            if(leftBumperPushed){
                if(SelectedItem <= 3){
                    SelectedItem++;

                }else{
                    SelectedItem =0;
                }
            }

            switch(SelectedItem){
                case 0:
                    leftFrontDrive.setPower(yInput);
                    telemetry.addData("SelectedItem","leftFrontDrive");
                    break;
                case 1:
                    rightFrontDrive.setPower(yInput);
                    telemetry.addData("SelectedItem","rightFrontDrive");
                    break;
                case 2:
                    leftBackDrive.setPower(yInput);
                    telemetry.addData("SelectedItem","leftBackDrive");
                    break;
                case 3:
                    rightBackDrive.setPower(yInput);
                    telemetry.addData("SelectedItem","rightBackDrive");
                    break;
                case 4:
                    telemetry.addData("SelectedItem","servo");

                    if (yInput > 0){
                        telemetry.addData("Action","Raise flag");
                        flag.Raise();
                    }
                    else if (yInput < 0) {
                        telemetry.addData("Action","Lower flag");
                        flag.Lower();
                    }

                    break;
                default:
                    telemetry.addData("No! Bad!", "SelectedItem not supported: " + SelectedItem);
                    break;
            }

            telemetry.addData("leftStickInput", yInput);
            telemetry.update();
        }
    }
}