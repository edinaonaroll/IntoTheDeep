package edu.edina.opmodes.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import edu.edina.definitions.SubsystemInitMode;
import edu.edina.subsystems.ArmSubsystem;
import edu.edina.subsystems.ChassisSubsystem;
import edu.edina.subsystems.FlagSubsystem;

@TeleOp(name= "IntoTheDeepTeleOp", group= "TeleOp")
public class TeleOpIntoTheDeep extends LinearOpMode
{

    private ElapsedTime runtime = new ElapsedTime();
    private double currTime = 0;

    @Override
    public void runOpMode() {

        ArmSubsystem armSubsystem = new ArmSubsystem (hardwareMap, telemetry, SubsystemInitMode.TeleOp);
        ChassisSubsystem chassisSubsystem = new ChassisSubsystem (hardwareMap, telemetry, SubsystemInitMode.TeleOp);
        FlagSubsystem flagSubsystem = new FlagSubsystem (hardwareMap, telemetry);

        telemetry.addData("Status", "Initialized");

        waitForStart();

        runtime.reset();

        while (opModeIsActive())
        {
            currTime = runtime.time();

            // flag control section
            if (currTime < 1) {
                flagSubsystem.Lower();
            }

            if (currTime > 2 && currTime < 60) {
                flagSubsystem.Raise();
            }

            if (currTime > 60) {
                flagSubsystem.Lower();
            }

            // drive control section
            double xDriveInput = gamepad1.left_stick_x;
            double yDriveInput = gamepad1.left_stick_y;
            double turnInput = gamepad1.right_stick_x;
            boolean driveSlowMode = gamepad1.a;

            double yInput = -gamepad1.left_stick_y; // Remember, Y stick value is reversed

            chassisSubsystem.DriveByController(xDriveInput, yDriveInput, turnInput, driveSlowMode);


            //Arm control section
            double armLiftInput = gamepad2.left_stick_y;
            double armExtendInput = gamepad2.left_stick_x;

            boolean clawOpenInput = gamepad2.dpad_up;
            boolean clawCloseInput = gamepad2.dpad_down;

            boolean armSlowMode = gamepad2.a;
            boolean raiseArmFully = gamepad2.y;


            armSubsystem.ArmRaiseLowerByController(armLiftInput, armSlowMode);
            armSubsystem.ArmExtendRetractByController(armExtendInput, armSlowMode);

            if (clawOpenInput){
                armSubsystem.Release();
            } else if (clawCloseInput){
                armSubsystem.Grab();
            }

            if (raiseArmFully){
                armSubsystem.RaiseFully();
            }

            telemetry.addData("Run Time: ", runtime.time());
            telemetry.addData("status", "Running");
            telemetry.update();



            idle();
        }


        runtime.reset();
    }
}
