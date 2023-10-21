package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "gotocoordinates")

public class gotocoordinates extends LinearOpMode {

    private DcMotor back_left_port_3;
    private DcMotor back_right_port_1;
    private DcMotor front_right_port_2;
    private DcMotor front_left_port_0;
    boolean telemtoggle;
    autonomous_dumb_function gotopos;





    @Override
    public void runOpMode() {
        back_left_port_3 = hardwareMap.get(DcMotor.class, "back_left_port_3");
        back_right_port_1 = hardwareMap.get(DcMotor.class, "back_right_port_1");
        front_right_port_2 = hardwareMap.get(DcMotor.class, "front_right_port_2");
        front_left_port_0 = hardwareMap.get(DcMotor.class, "front_left_port_0");

        gotopos = new autonomous_dumb_function();

        initialization();
        waitForStart();
        if (opModeIsActive()) {
            while (opModeIsActive()) {
                gotopos.Run(back_right_port_1.getCurrentPosition(), back_left_port_3.getCurrentPosition(), 0, -24, 0.25, 0.5);
                back_left_port_3.setPower(gotopos.BackLeft);
                back_right_port_1.setPower(gotopos.BackRight);
                front_left_port_0.setPower(gotopos.FrontLeft);
                front_right_port_2.setPower(gotopos.FrontRight);
                if (telemtoggle) {
                    telem();
                }
            }
        }
    }


    private void telem() {
        telemetry.addData("power should be(BL)", gotopos.BackLeft);
        telemetry.addData("power should be(BR)", gotopos.BackRight);
        telemetry.addData("power should be(FL)", gotopos.FrontLeft);
        telemetry.addData("power should be(FR)", gotopos.FrontRight);
        telemetry.addData("power is(BL)", back_left_port_3.getPower());
        telemetry.addData("power is(BR)", back_right_port_1.getPower());
        telemetry.addData("power is(FL)", front_left_port_0.getPower());
        telemetry.addData("power is(FR)", front_right_port_2.getPower());
        telemetry.addData("OW left", back_right_port_1.getCurrentPosition());
        telemetry.addData("OW back", back_left_port_3.getCurrentPosition());
        telemetry.addData("deltax", gotopos.DeltaX);
        telemetry.addData("deltay", gotopos.DeltaY);
        telemetry.addData("posx",gotopos.PosX);
        telemetry.addData("posy",gotopos.PosY);
        telemetry.update();
    }


    private void initialization() {
        back_left_port_3.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        back_right_port_1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        front_left_port_0.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        front_right_port_2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        back_left_port_3.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        back_right_port_1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        front_left_port_0.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        front_right_port_2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        telemtoggle = true;
    }
}




