package edu.edina.opmodes.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import edu.edina.definitions.SubsystemInitMode;
import edu.edina.subsystems.ArmSubsystem;
import edu.edina.subsystems.GrabberSubsystem;
import edu.edina.subsystems.ChassisSubsystem;
import edu.edina.subsystems.FlagSubsystem;

@TeleOp(name= "DriveAndPark", group= "Autonomous")
public class Autonomolus_v1 extends LinearOpMode{

    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() {

        runtime.reset();

        ArmSubsystem armsubsystem = new ArmSubsystem(hardwareMap, telemetry, SubsystemInitMode.Autonomous);
        ChassisSubsystem chassisSubsystem = new ChassisSubsystem(hardwareMap, telemetry, SubsystemInitMode.Autonomous);
        FlagSubsystem flagSubsystem = new FlagSubsystem(hardwareMap, telemetry);
        GrabberSubsystem grabberSubsystem = new GrabberSubsystem(hardwareMap, telemetry);

        flagSubsystem.LowerLeft();

        telemetry.addData("Status", "Initialized");

        waitForStart();

        flagSubsystem.RaiseLeft();

        // drive forward 2 feet
        chassisSubsystem.DriveForward_Inches(48, .5);

        // lift arm up
        armsubsystem.RaiseFully(.5);
        // hook specimen onto bar
        armsubsystem.ExtendFully();
        // release specimen
        grabberSubsystem.Release();
        armsubsystem.RetractFully();
        armsubsystem.LowerFully(.5);
        // back up
        chassisSubsystem.DriveBack_Inches(40, .5);
        // lower arm to lower bar or all the way

        // strafe to observation zone
        chassisSubsystem.DriveRight_Inches(60, .5);


        // drive to submersible and hang starting specimen

        // drive to observation zone or low bar touch



        flagSubsystem.LowerLeft();

        telemetry.addData("Run time: ", runtime.toString());

        runtime.reset();
    }
}