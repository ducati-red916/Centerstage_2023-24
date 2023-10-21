package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;


@TeleOp(name = "android studio working")
public class android_studio_working extends LinearOpMode {

    private DcMotor back_left_port_3;
    private DcMotor back_right_port_1;
    private DcMotor front_right_port_2;
    private DcMotor front_left_port_0;
    private Servo plane;
    double drive_mode;

    holonomic drive;
    final double startdrivemode = 0.5;


    @Override
    public void runOpMode() {
        back_left_port_3 = hardwareMap.get(DcMotor.class, "back_left_port_3");
        back_right_port_1 = hardwareMap.get(DcMotor.class, "back_right_port_1");
        front_right_port_2 = hardwareMap.get(DcMotor.class, "front_right_port_2");
        front_left_port_0 = hardwareMap.get(DcMotor.class, "front_left_port_0");
        plane = hardwareMap.get(Servo.class, "plane");
        drive = new holonomic();
        motormodes();
        waitForStart();
        drive_mode=startdrivemode;
        if (opModeIsActive()) {
            while (opModeIsActive()) {
                drivemodes(); //sets drivemodes to their correct values
                drive.run(gamepad1.right_stick_x, gamepad1.right_stick_y, gamepad1.left_stick_x, drive_mode);
               front_right_port_2.setPower(drive.FrontRight());
               back_right_port_1.setPower(drive.BackRight());
               front_left_port_0.setPower(drive.FrontLeft());
               back_left_port_3.setPower(drive.BackLeft());
               if( gamepad1.right_bumper )
                   plane.setPosition(1);
               else
                   plane.setPosition(0);
            }
        }
    }

    private void motormodes() {
        back_left_port_3.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        back_right_port_1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        front_left_port_0.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        front_right_port_2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        back_left_port_3.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        back_right_port_1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        front_left_port_0.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        front_right_port_2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        front_right_port_2.setDirection(DcMotorSimple.Direction.REVERSE);
        back_right_port_1.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    private void drivemodes() {
        if (gamepad1.a) {
            drive_mode = 0.8;
        }
        if (gamepad1.x){
            drive_mode=0.5;
        }
        if (gamepad1.b) {
            drive_mode = 0.25;
        }
    }

}