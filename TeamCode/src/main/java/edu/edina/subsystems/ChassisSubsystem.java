package edu.edina.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.Telemetry;

import edu.edina.definitions.BotBits;

public class ChassisSubsystem {

    HardwareMap map;
    Telemetry telemetry;

    private DcMotor leftFrontDrive = null;
    private DcMotor leftBackDrive = null;
    private DcMotor rightFrontDrive = null;
    private DcMotor rightBackDrive = null;

    public ChassisSubsystem(HardwareMap hardwareMapReference, Telemetry telemetryReference) {
        map = hardwareMapReference;
        telemetry = telemetryReference;

        leftFrontDrive =    map.get(DcMotor.class, BotBits.FrontLeftDriveMotor);
        leftBackDrive =     map.get(DcMotor.class, BotBits.BackLeftDriveMotor);
        rightFrontDrive =   map.get(DcMotor.class, BotBits.FrontRightDriveMotor);
        rightBackDrive =    map.get(DcMotor.class, BotBits.BackRightDriveMotor);
    }

}