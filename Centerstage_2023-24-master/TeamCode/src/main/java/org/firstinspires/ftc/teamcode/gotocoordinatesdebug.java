package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "gotocoordinatesdebug")

public class gotocoordinatesdebug extends LinearOpMode {
    private DcMotor back_left_port_3;
    private DcMotor back_right_port_1;
    private DcMotor front_right_port_2;
    private Servo claw;
    private DcMotor slide1;
    private DcMotor front_left_port_0;

    double drive_mode;
    double Odometry_left;
    double Odometry_right;
    double Odometry_back;
    boolean telemtoggle;
    autonomous_dumb_function gotopos;

    /**
     * Describe this function...
     */
    private void Dynamic_variables() {
        Odometry_back = back_left_port_3.getCurrentPosition();
        Odometry_left = back_right_port_1.getCurrentPosition();


    }

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

        gotopos = new autonomous_dumb_function();

        initialization();
        waitForStart();
        if (opModeIsActive()) {
            while (opModeIsActive()) {
                gotopos.Run(back_right_port_1.getCurrentPosition(), back_left_port_3.getCurrentPosition(), 24, 24, 1, 2);
                Dynamic_variables();

                if (telemtoggle) {
                    telem();
                }
            }
        }
    }

    /**
     * Describe this function...
     */
    private void telem() {
        telemetry.addData("power should be(BL)", gotopos.BackLeft);
        telemetry.addData("power should be(BR)", gotopos.BackRight);
        telemetry.addData("power should be(FL)", gotopos.FrontLeft);
        telemetry.addData("power should be(FR)", gotopos.FrontRight);
        telemetry.addData("power is(BL)", back_left_port_3.getPower());
        telemetry.addData("power is(BR)", back_right_port_1.getPower());
        telemetry.addData("power is(FL)", front_left_port_0.getPower());
        telemetry.addData("power is(FR)", front_right_port_2.getPower());
        telemetry.addData("OW left", Odometry_left);
        telemetry.addData("OW back", Odometry_back);
        telemetry.addData("deltax", gotopos.DeltaX);
        telemetry.addData("deltay", gotopos.DeltaY);
        telemetry.addData("posx",gotopos.PosX);
        telemetry.addData("posy",gotopos.PosY);
        telemetry.update();
    }

    /**
     * Describe this function...
     */
    private void initialization() {
        front_right_port_2.setDirection(DcMotorSimple.Direction.REVERSE);
        back_right_port_1.setDirection(DcMotorSimple.Direction.REVERSE);
        back_left_port_3.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        back_right_port_1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        front_left_port_0.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        front_right_port_2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        back_left_port_3.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        back_right_port_1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        front_left_port_0.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        front_right_port_2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        drive_mode = 1;
        telemtoggle = true;
    }
}



