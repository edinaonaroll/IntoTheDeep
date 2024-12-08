package edu.edina.opmodes.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import edu.edina.definitions.SubsystemInitMode;
import edu.edina.subsystems.ArmSubsystem;
import edu.edina.subsystems.ChassisSubsystem;
import edu.edina.subsystems.FlagSubsystem;

@TeleOp(name= "ParkFar", group= "Autonomous")
//@Disabled
public class ParkFar extends LinearOpMode{

    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() {

        ChassisSubsystem chassisSubsystem = new ChassisSubsystem(hardwareMap, telemetry, SubsystemInitMode.Autonomous);
        FlagSubsystem flagSubsystem = new FlagSubsystem(hardwareMap, telemetry);

        flagSubsystem.Lower();

        telemetry.addData("Status", "Initialized");

        waitForStart();

        flagSubsystem.Raise();

        while (opModeIsActive()) {
            chassisSubsystem.DriveLeft_Inches(50,.5);
            chassisSubsystem.DriveForward_Inches(140, .5);
            chassisSubsystem.DriveRight_Inches(50,.5);
            break;
        }

        flagSubsystem.Lower();

        runtime.reset();
    }
}