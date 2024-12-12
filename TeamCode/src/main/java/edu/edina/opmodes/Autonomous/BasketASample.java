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

@TeleOp(name= "BasketASample", group= "Autonomous")
//@Disabled
public class BasketASample extends LinearOpMode{

    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() {
        ArmSubsystem armSubsystem = new ArmSubsystem(hardwareMap, telemetry, SubsystemInitMode.Autonomous);
        ChassisSubsystem chassisSubsystem = new ChassisSubsystem(hardwareMap, telemetry, SubsystemInitMode.Autonomous);
        FlagSubsystem flagSubsystem = new FlagSubsystem(hardwareMap, telemetry);
        GrabberSubsystem grabberSubsystem = new GrabberSubsystem(hardwareMap, telemetry);

        flagSubsystem.Lower();
        grabberSubsystem.GrabFully();

        telemetry.addData("Status", "Initialized");

        waitForStart();

        runtime.reset();
        flagSubsystem.Raise();

        while (opModeIsActive()) {
            armSubsystem.RaiseFully(0.5);
            chassisSubsystem.DriveRight_Inches(40,.6);
            chassisSubsystem.TurnLeft(15,0.5);
            chassisSubsystem.DriveForward_Inches(95,.5);
            chassisSubsystem.TurnLeft(15,.5);
            chassisSubsystem.DriveForward_Inches(3,.5);

            armSubsystem.ExtendByNumbers(.5,500);
            grabberSubsystem.ReleaseFully();
            armSubsystem.RetractByNumbers(.8,15);
            armSubsystem.LowerFully(0.7);
            chassisSubsystem.TurnRight(23, 0.5);
            chassisSubsystem.DriveBack_Inches(100, .6);

            break;
        }

        flagSubsystem.Lower();

        runtime.reset();
    }
}