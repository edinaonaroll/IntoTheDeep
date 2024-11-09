package edu.edina.opmodes.Test;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.TouchSensor;

import edu.edina.definitions.BotBits;
import edu.edina.subsystems.ArmSubsystem;
import edu.edina.subsystems.FlagSubsystem;

@TeleOp(name= "TestOpMode", group= "Test")
public class TestOpMode extends LinearOpMode {

    private DcMotor leftFrontDrive = null;
    private DcMotor leftBackDrive = null;
    private DcMotor rightFrontDrive = null;
    private DcMotor rightBackDrive = null;
    private TouchSensor TouchSensorRight = null;
    private TouchSensor TouchSensorLeft = null;
    private TouchSensor TouchSensorMiddle = null;

    private DcMotor ArmLiftMotor = null;
    private DcMotor ArmExtendMotor = null;

    private int SelectedItem = 1;
    private int NumHardwareElements = 0;

    @Override
    public void runOpMode() {
        leftFrontDrive  = hardwareMap.get(DcMotor.class, BotBits.FrontLeftDriveMotor);
        leftBackDrive  = hardwareMap.get(DcMotor.class, BotBits.BackLeftDriveMotor);
        rightFrontDrive = hardwareMap.get(DcMotor.class, BotBits.FrontRightDriveMotor);
        rightBackDrive = hardwareMap.get(DcMotor.class, BotBits.BackRightDriveMotor);

//TODO:  Replace literal text with reference to BotBits class
        TouchSensorRight = hardwareMap.get(TouchSensor.class,  "Touch Sensor Right");
        TouchSensorLeft = hardwareMap.get(TouchSensor.class, "Touch Sensor Left");
        TouchSensorMiddle = hardwareMap.get(TouchSensor.class, "Touch Sensor Middle");

        leftFrontDrive.setDirection(DcMotorSimple.Direction.FORWARD);
        leftBackDrive.setDirection(DcMotorSimple.Direction.FORWARD);
        rightFrontDrive.setDirection(DcMotorSimple.Direction.FORWARD);
        rightBackDrive.setDirection(DcMotorSimple.Direction.REVERSE);

        ArmLiftMotor = hardwareMap.get(DcMotor.class, BotBits.ArmLiftMotor);
        ArmExtendMotor = hardwareMap.get(DcMotor.class, BotBits.ArmExtendMotor);

        FlagSubsystem flag = new FlagSubsystem(hardwareMap, telemetry);
        ArmSubsystem Arm = new ArmSubsystem(hardwareMap, telemetry);

        NumHardwareElements = 8;

        telemetry.addData("Status", "Initialized");
        waitForStart();

        while (opModeIsActive()) {
            double yInput = -gamepad1.left_stick_y; // Remember, Y stick value is reversed
            Boolean buttonPushed = (gamepad1.left_trigger > 0);

            if (buttonPushed) {
                if (SelectedItem < NumHardwareElements) {
                    SelectedItem++;

                } else {
                    SelectedItem = 1;
                }

                sleep(300);
            }

            telemetry.addData("leftStickInput", yInput);

            switch (SelectedItem){
                case 1:
                    if(yInput>0){
                        ArmLiftMotor.setPower(yInput/2);
                        ArmLiftMotor.setDirection(DcMotorSimple.Direction.FORWARD);
                    }else if(yInput<0){
                        ArmLiftMotor.setPower(yInput/4);
                        ArmLiftMotor.setDirection(DcMotorSimple.Direction.FORWARD);
                    }else if(yInput==0){
                        ArmLiftMotor.setPower(.01);
                        ArmLiftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                    }
                    telemetry.addData("SelectedItem","Arm Lift Motor");
                    telemetry.addData("MotorZeroPowerBehavior",ArmLiftMotor.getZeroPowerBehavior());
                    telemetry.addData("Power", ArmLiftMotor.getPower());
                    break;
                case 2:
                    ArmExtendMotor.setPower(yInput);
                    telemetry.addData("SelectedItem","Arm Extend Motor");
                    telemetry.addData("Power", ArmExtendMotor.getPower());
                    break;
                case 3:
                    telemetry.addData("SelectedItem","Grab Servo");

                     if (yInput > 0){
                        telemetry.addData("Action","Grab");
                        Arm.Grab();
                    }
                    else if (yInput < 0) {
                        telemetry.addData("Action","Release");
                        Arm.Release();
                    }

                    break;
                case 4:
                    telemetry.addData("SelectedItem","Flag Servo");

                    if (yInput > 0){
                        telemetry.addData("Action","Raise flag");
                        flag.Raise();
                    }
                    else if (yInput < 0) {
                        telemetry.addData("Action","Lower flag");
                        flag.Lower();
                    }

                    break;
                case 5:
                    leftFrontDrive.setPower(yInput);
                    telemetry.addData("SelectedItem","leftFrontDrive");
                    break;
                case 6:
                    rightFrontDrive.setPower(yInput);
                    telemetry.addData("SelectedItem","rightFrontDrive");
                    break;
                case 7:
                    leftBackDrive.setPower(yInput);
                    telemetry.addData("SelectedItem","leftBackDrive");
                    break;
                case 8:
                    rightBackDrive.setPower(yInput);
                    telemetry.addData("SelectedItem","rightBackDrive");
                    break;
                default:
                    telemetry.addData("No! Bad!", "SelectedItem not supported: " + SelectedItem);
                    break;
            }

            if(TouchSensorRight.isPressed()){
                telemetry.clear();
                telemetry.addData("Touch sensor", "You touched the right");
            }

            if(TouchSensorMiddle.isPressed()){
                //TODO:  Clear telemetry (see above)
                telemetry.addData("Touch sensor","You touched the middle");
            }

            if(TouchSensorLeft.isPressed()){
                //TODO:  Clear telemetry (see above)
                telemetry.addData("Touch sensor","You touched the left");
            }

            telemetry.update();
        }
    }
}