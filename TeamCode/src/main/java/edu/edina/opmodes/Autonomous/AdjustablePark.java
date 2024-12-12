package edu.edina.opmodes.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import edu.edina.definitions.SubsystemInitMode;
import edu.edina.subsystems.ArmSubsystem;
import edu.edina.subsystems.ChassisSubsystem;
import edu.edina.subsystems.FlagSubsystem;
import edu.edina.subsystems.GrabberSubsystem;

@TeleOp(name= "AdjustablePark", group= "Autonomous")
//@Disabled
public class AdjustablePark extends LinearOpMode{

    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() {

        ChassisSubsystem chassisSubsystem = new ChassisSubsystem(hardwareMap, telemetry, SubsystemInitMode.Autonomous);
        FlagSubsystem flagSubsystem = new FlagSubsystem(hardwareMap, telemetry);
        GrabberSubsystem grabberSubsystem = new GrabberSubsystem(hardwareMap, telemetry);

        flagSubsystem.Lower();
        grabberSubsystem.GrabFully();

        telemetry.addData("Status", "Initialized");

        waitForStart();

        flagSubsystem.Raise();

        while (opModeIsActive()) {
            // drive forward two inches
            chassisSubsystem.DriveForward_Inches(70, .5);
            break;
        }

        flagSubsystem.Lower();

        runtime.reset();
    }
}