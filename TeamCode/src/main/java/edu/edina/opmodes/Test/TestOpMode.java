package edu.edina.opmodes.Test;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

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


        telemetry.addData("Status", "Initialized");
        waitForStart();

        while (opModeIsActive()) {
            double yInput = -gamepad1.right_stick_y; // Remember, Y stick value is reversed
            Boolean leftBumperPushed =  gamepad1.left_bumper;


            if(leftBumperPushed){
                if(SelectedItem <= 3){
                    SelectedItem++;

                }else{
                    SelectedItem = 0;
                }


                sleep(1000);

            }

            switch(SelectedItem) {
                case 0:
                    leftFrontDrive.setPower(yInput);
                    telemetry.addData("SelectedItem", "leftFrontDrive");
                    telemetry.addData("MotorDirection", leftFrontDrive.getDirection());
                    break;
                case 1:
                    rightFrontDrive.setPower(yInput);
                    telemetry.addData("SelectedItem", "rightFrontDrive");
                    telemetry.addData("MotorDirection", rightFrontDrive.getDirection());
                    break;
                case 2:
                    leftBackDrive.setPower(yInput);
                    telemetry.addData("SelectedItem", "leftBackDrive");
                    telemetry.addData("MotorDirection", leftBackDrive.getDirection());
                    break;
                case 3:
                    rightBackDrive.setDirection(DcMotorSimple.Direction.REVERSE);
                    rightBackDrive.setPower(yInput);
                    telemetry.addData("SelectedItem", "rightBackDrive");
                    telemetry.addData("MotorDirection", rightBackDrive.getDirection());
                    break;
                case 4:
                    telemetry.addData("SelectedItem", "servo");

                    FlagSubsystem Flag = new FlagSubsystem(hardwareMap,telemetry);

                    if (yInput > 0){
                        telemetry.addData("Direction", "Raise");
                        Flag.FlagRaise();
                    } else {
                        telemetry.addData("Direction", "Lower");
                        Flag.FlagLower();
                    }
                    break;
                default:
                    telemetry.addData("No!  Bad!", "Selected case: " + SelectedItem);
                    break;
            }

            telemetry.addData("right Stick Input", yInput);
            telemetry.update();
        }
    }
}
