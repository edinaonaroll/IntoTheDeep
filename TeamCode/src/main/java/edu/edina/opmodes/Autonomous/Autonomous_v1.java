package edu.edina.opmodes.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import edu.edina.definitions.SubsystemInitMode;
import edu.edina.subsystems.ArmSubsystem;
import edu.edina.subsystems.GrabberSubsystem;
import edu.edina.subsystems.ChassisSubsystem;
import edu.edina.subsystems.FlagSubsystem;

@TeleOp(name= "Autonomous_V1", group= "Autonomous")
public class Autonomous_v1 extends LinearOpMode{

    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() {

        runtime.reset();

        ArmSubsystem armsubsystem = new ArmSubsystem(hardwareMap, telemetry, SubsystemInitMode.Autonomous);
        ChassisSubsystem chassisSubsystem = new ChassisSubsystem(hardwareMap, telemetry, SubsystemInitMode.Autonomous);
        FlagSubsystem flagSubsystem = new FlagSubsystem(hardwareMap, telemetry);
        GrabberSubsystem grabberSubsystem = new GrabberSubsystem(hardwareMap, telemetry);

        flagSubsystem.Lower();
        grabberSubsystem.GrabFully();
        armsubsystem.RetractByNumbers(.9,0);

        telemetry.addData("Status", "Initialized");

        waitForStart();

        while (opModeIsActive()) {
            flagSubsystem.Raise();

            // drive forward 2 feet
            chassisSubsystem.DriveForward_Inches(48, .9);

            // lift arm up
            armsubsystem.RaiseFully(.9);

            // hook specimen onto bar
            armsubsystem.ExtendByNumbers(.9, 300);

//            retract a little to hook specimen onto bar
            armsubsystem.RetractByNumbers(.9, 280);

            // release specimen
            grabberSubsystem.ReleaseFully();

            // back up
            chassisSubsystem.DriveBack_Inches(3, .9);

            armsubsystem.RetractByNumbers(.9, 46);
            armsubsystem.LowerFully(.2);

            // back up
            chassisSubsystem.DriveBack_Inches(40, .9);

            // lower arm to lower bar or all the way

            // strafe to observation zone
            chassisSubsystem.DriveRight_Inches(60, .9);


            // drive to submersible and hang starting specimen

            // drive to observation zone or low bar touch


            flagSubsystem.Lower();

            telemetry.addData("Run time: ", runtime.toString());

            runtime.reset();

            break;
        }
    }
}