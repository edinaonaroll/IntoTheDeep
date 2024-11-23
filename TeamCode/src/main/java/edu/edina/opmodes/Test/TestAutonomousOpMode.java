package edu.edina.opmodes.Test;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import edu.edina.definitions.SubsystemInitMode;
import edu.edina.subsystems.ArmSubsystem;
import edu.edina.subsystems.ChassisSubsystem;
import edu.edina.subsystems.FlagSubsystem;

@TeleOp(name= "Test Autonomous", group= "Test")
public class TestAutonomousOpMode extends LinearOpMode{

    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() {

        runtime.reset();

        ArmSubsystem armsubsystem = new ArmSubsystem(hardwareMap, telemetry, SubsystemInitMode.Autonomous);
        ChassisSubsystem chassisSubsystem = new ChassisSubsystem(hardwareMap, telemetry, SubsystemInitMode.Autonomous);
        FlagSubsystem flagSubsystem = new FlagSubsystem(hardwareMap, telemetry);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();

        sleep(3000);
        telemetry.addData("Step: ", "Started");
        telemetry.update();

        sleep(3000);
        telemetry.addData("Next Action: ", "Lower flag");
        telemetry.update();

        flagSubsystem.Lower();

        sleep(3000);
        telemetry.addData("Next Action: ", "Raise flag");
        telemetry.update();

        flagSubsystem.Raise();

        sleep(3000);
        telemetry.addData("Next Action: ", "Drive forward");
        telemetry.update();

//        // drive forward 2 feet
//        chassisSubsystem.DriveForward_Inches(48, MotorSpeed.Percent_50);
//
//        sleep(3000);
//        telemetry.addData("Next Action: ", "armsubsystem.RaiseFully");
//        telemetry.update();
//
//        // lift arm up
//        armsubsystem.RaiseFully();
//
//        sleep(3000);
//        telemetry.addData("Next Action: ", "armsubsystem.ExtendFully");
//        telemetry.update();
//
//        // hook specimen onto bar
//        armsubsystem.ExtendFully();
//
//        sleep(3000);
//        telemetry.addData("Next Action: ", "armsubsystem.Release");
//        telemetry.update();
//
//        // release specimen
//        armsubsystem.Release();
//
//        sleep(3000);
//        telemetry.addData("Next Action: ", "armsubsystem.RetractFully");
//        telemetry.update();
//
//        armsubsystem.RetractFully();
//
//        sleep(3000);
//        telemetry.addData("Next Action: ", "armsubsystem.LowerFully");
//        telemetry.update();
//
//        armsubsystem.LowerFully();
//
//        sleep(3000);
//        telemetry.addData("Next Action: ", "DriveBack_Inches");
//        telemetry.update();
//
//
//        // back up
//        chassisSubsystem.DriveBack_Inches(40, MotorSpeed.Percent_50);
//        // lower arm to lower bar or all the way
//
//        // strafe to observation zone
//        chassisSubsystem.DriveRight_Inches(60, MotorSpeed.Percent_50);
//
//
//        // drive to submersible and hang starting specimen
//
//        // drive to observation zone or low bar touch
//
//
//
//        flagSubsystem.Lower();
//
//        telemetry.addData("Run time: ", runtime.toString());

        runtime.reset();
    }
}