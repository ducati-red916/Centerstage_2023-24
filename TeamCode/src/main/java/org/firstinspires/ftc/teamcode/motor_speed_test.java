package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "motor speed test")
public class motor_speed_test extends LinearOpMode {

    private DcMotor back_left_port_3;
    private DcMotor back_right_port_1;
    private DcMotor front_right_port_2;
    private Servo claw;
    private DcMotor slide1;
    private DcMotor front_left_port_0;
    private Gamepad gamepad3;
    private Gamepad gamepad4;



    /**
     * Describe this function...
     */



    /**
     * This function is executed when this Op Mode is selected from the Driver Station.
     */
    @Override
    public void runOpMode() {
        back_left_port_3 = hardwareMap.get(DcMotor.class, "back_left_port_3");
        back_right_port_1 = hardwareMap.get(DcMotor.class, "back_right_port_1");
        front_right_port_2 = hardwareMap.get(DcMotor.class, "front_right_port_2");
        claw = hardwareMap.get(Servo.class, "claw");
        slide1 = hardwareMap.get(DcMotor.class, "slide1");
        front_left_port_0 = hardwareMap.get(DcMotor.class, "front_left_port_0");
        back_right_port_1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        waitForStart();
        if (opModeIsActive()) {
            while (opModeIsActive()) {
                ((DcMotorEx) back_right_port_1).setVelocity(99999999);
                telemetry.addData("speed", ((DcMotorEx) back_right_port_1).getVelocity());
                telemetry.update();
            }
        }
    }

}