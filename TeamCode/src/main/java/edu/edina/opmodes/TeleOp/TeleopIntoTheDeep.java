//package edu.edina.opmodes.TeleOp;
//
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
//import com.qualcomm.robotcore.hardware.DcMotor;
//import com.qualcomm.robotcore.util.ElapsedTime;
//import edu.edina.definitions.BotBits;
//
//
//@Teleop(name= "IntoTheDeepTeleop", group= "Teleop");
//public class TeleopIntoTheDeep extends LinearOpMode {
//
//
//
//    private ElapsedTime runtime = new ElapsedTime();
//    private DcMotor leftFrontDrive = null;
//    private DcMotor leftBackDrive = null;
//    private DcMotor rightFrontDrive = null;
//    private DcMotor rightBackDrive = null;
//
//    @Override
//    public void runOpMode() {
//
//        leftFrontDrive  = hardwareMap.get(DcMotor.class, BotBits.FrontLeftDriveMotor);
//        leftBackDrive  = hardwareMap.get(DcMotor.class, BotBits.BackLeftDriveMotor);
//        rightFrontDrive = hardwareMap.get(DcMotor.class, BotBits.FrontRightDriveMotor);
//        rightBackDrive = hardwareMap.get(DcMotor.class, BotBits.BackRightDriveMotor);
//
//        leftFrontDrive.setDirection(DcMotor.Direction.REVERSE);
//        leftBackDrive.setDirection(DcMotor.Direction.REVERSE);
//        rightFrontDrive.setDirection(DcMotor.Direction.FORWARD);
//        rightBackDrive.setDirection(DcMotor.Direction.FORWARD);
//
//        telemetry.addData("Status", "Initilized");
//
//        waitforstart();
//        runtime.reset();
//
//
//
//    }
//}
