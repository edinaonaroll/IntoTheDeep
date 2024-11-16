package edu.edina.opmodes.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import edu.edina.definitions.MotorSpeed;
import edu.edina.definitions.SubsystemInitMode;
import edu.edina.subsystems.ArmSubsystem;
import edu.edina.subsystems.ChassisSubsystem;
import edu.edina.subsystems.FlagSubsystem;

@TeleOp(name= "DriveAndPark", group= "Autonomous")
public class DriveAndPark extends LinearOpMode{

    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() {

        runtime.reset();

        ArmSubsystem armsubsystem = new ArmSubsystem(hardwareMap, telemetry, SubsystemInitMode.Autonomous);
        ChassisSubsystem chassisSubsystem = new ChassisSubsystem(hardwareMap, telemetry, SubsystemInitMode.Autonomous);
        FlagSubsystem flagSubsystem = new FlagSubsystem(hardwareMap, telemetry);

        flagSubsystem.Lower();

        telemetry.addData("Status", "Initialized");

        waitForStart();

        flagSubsystem.Raise();

        // drive forward 2 feet
        chassisSubsystem.DriveForward_Inches(48, MotorSpeed.Percent_50);

        // lift arm up
        armsubsystem.RaiseFully();
        // hook specimen onto bar
        armsubsystem.ExtendFully();
        // release specimen
        armsubsystem.Release();
        armsubsystem.RetractFully();
        armsubsystem.LowerFully();
        // back up
        chassisSubsystem.DriveBack_Inches(40, MotorSpeed.Percent_50);
        // lower arm to lower bar or all the way

        // strafe to observation zone
        chassisSubsystem.DriveRight_Inches(60, MotorSpeed.Percent_50);


        // drive to submersible and hang starting specimen

        // drive to observation zone or low bar touch



        flagSubsystem.Lower();

        telemetry.addData("Run time: ", runtime.toString());

        runtime.reset();
    }
}