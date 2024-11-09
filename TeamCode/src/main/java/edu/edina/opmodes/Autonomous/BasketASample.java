package edu.edina.opmodes.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import edu.edina.definitions.MotorSpeed;
import edu.edina.definitions.SubsystemInitMode;
import edu.edina.subsystems.ArmSubsystem;
import edu.edina.subsystems.ChassisSubsystem;
import edu.edina.subsystems.FlagSubsystem;

@TeleOp(name= "BasketASample", group= "Autonomous")
public class BasketASample extends LinearOpMode{

    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() {

        ArmSubsystem armsubsystem = new ArmSubsystem(hardwareMap, telemetry);
        ChassisSubsystem chassisSubsystem = new ChassisSubsystem(hardwareMap, telemetry, SubsystemInitMode.Autonomous);
        FlagSubsystem flagSubsystem = new FlagSubsystem(hardwareMap, telemetry);

        flagSubsystem.Lower();

        telemetry.addData("Status", "Initialized");

        waitForStart();

        flagSubsystem.Raise();

        while (opModeIsActive()) {
            // drive forward two inches
            chassisSubsystem.DriveForward(2, MotorSpeed.Percent_50);
        }

        flagSubsystem.Lower();

        runtime.reset();
    }
}