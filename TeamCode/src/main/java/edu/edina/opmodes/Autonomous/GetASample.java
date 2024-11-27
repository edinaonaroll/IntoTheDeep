package edu.edina.opmodes.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import edu.edina.definitions.SubsystemInitMode;
import edu.edina.subsystems.ArmSubsystem;
import edu.edina.subsystems.ChassisSubsystem;
import edu.edina.subsystems.FlagSubsystem;

@TeleOp(name= "GetASample", group= "Autonomous")
@Disabled
public class GetASample extends LinearOpMode{

    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() {

        ArmSubsystem armsubsystem = new ArmSubsystem(hardwareMap, telemetry , SubsystemInitMode.Autonomous);
        ChassisSubsystem chassisSubsystem = new ChassisSubsystem(hardwareMap, telemetry, SubsystemInitMode.Autonomous);
        FlagSubsystem flagSubsystem = new FlagSubsystem(hardwareMap, telemetry);

        flagSubsystem.LowerLeft();

        telemetry.addData("Status", "Initialized");

        waitForStart();

        flagSubsystem.RaiseLeft();

        while (opModeIsActive()) {
            // drive forward two inches
            chassisSubsystem.DriveForward_Inches(2, .5);
        }

        flagSubsystem.LowerLeft();

        runtime.reset();
    }
}