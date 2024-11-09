package edu.edina.opmodes.TeleOp;

import static edu.edina.definitions.BotBits.ArmExtendMotor;
import edu.edina.definitions.BotBits;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

import edu.edina.definitions.SubsystemInitMode;
import edu.edina.subsystems.ArmSubsystem;
import edu.edina.subsystems.ChassisSubsystem;
import edu.edina.subsystems.FlagSubsystem;

@TeleOp(name= "IntoTheDeepTeleOp", group= "TeleOp")
public class TeleOpIntoTheDeep extends LinearOpMode
{

    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() {

        ArmSubsystem armsubsystem = new ArmSubsystem (hardwareMap, telemetry);
        ChassisSubsystem chassisSubsystem = new ChassisSubsystem (hardwareMap, telemetry, SubsystemInitMode.TeleOp);
        FlagSubsystem flagSubsystem = new FlagSubsystem (hardwareMap, telemetry);

        flagSubsystem.Lower();

        telemetry.addData("Status", "Initialized");

        waitForStart();

        flagSubsystem.Raise();

        while (opModeIsActive())
        {
            double xDriveInput = gamepad1.left_stick_x;
            double yDriveInput = gamepad1.left_stick_y;
            double turnInput = gamepad1.right_stick_x;
            boolean driveSlowMode = gamepad1.a;

            double yInput = -gamepad1.left_stick_y; // Remember, Y stick value is reversed
            boolean buttonPushed = (gamepad2.y);

//            if (buttonPushed) {
//                ArmExtendMotor.setPower(1);
//                ArmExtendMotor.
            double armLiftInput = gamepad2.left_stick_y;
            boolean armSlowMode = gamepad2.a;

            armsubsystem.MoveArm(armLiftInput, armSlowMode);
            chassisSubsystem.DriveByController(xDriveInput, yDriveInput, turnInput, driveSlowMode);

            telemetry.addData("status", "Running");
            telemetry.update();

            idle();
        }

        flagSubsystem.Lower();

        runtime.reset();
    }
}
