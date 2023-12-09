package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;

import java.util.concurrent.TimeUnit;

@TeleOp(name = "android studio claws")
public class android_studio_claws extends LinearOpMode {

    private DcMotor back_left_port_3;
    private DcMotor back_right_port_1;
    private DcMotor front_right_port_2;
    private DcMotor front_left_port_0;
    private DcMotor slide;
    double drive_mode = 0.5;
    final double pickup1_close = 0.14;
    final double pickup1_open = 0;
    final double pickup2_up = 0.25;
    final double pickup2_down = 0.93;
    final double dropoff1_close = 0.06;
    final double dropoff1_open = -0.1;
    final double dropoff2_drop = 0.7;
    final double dropoff2_tranfer = 0.89;
    final double dropoff2_clear = 1;
    private TouchSensor limit_switch;
    private int loopcounter = 0;
    private boolean dpad_up_last;
    int pos0;
    holonomic drive;

    @Override
    public void runOpMode() {
        Servo plane;
        Servo pickup1;
        Servo pickup2;
        Servo dropoff1;
        Servo dropoff2;
        back_left_port_3 = hardwareMap.get(DcMotor.class, "back_left_port_3");
        back_right_port_1 = hardwareMap.get(DcMotor.class, "back_right_port_1");
        front_right_port_2 = hardwareMap.get(DcMotor.class, "front_right_port_2");
        front_left_port_0 = hardwareMap.get(DcMotor.class, "front_left_port_0");
        limit_switch = hardwareMap.get(TouchSensor.class, "limit_switch");
        slide = hardwareMap.get(DcMotor.class, "slide");
        plane = hardwareMap.get(Servo.class, "plane");
        pickup1 = hardwareMap.get(Servo.class, "pickup1");
        pickup2 = hardwareMap.get(Servo.class, "pickup2");
        dropoff1 = hardwareMap.get(Servo.class, "dropoff1");
        dropoff2 = hardwareMap.get(Servo.class, "dropoff2");
        drive = new holonomic();
        motormodes();
        waitForStart();
        if (opModeIsActive()) {
            while (opModeIsActive()) {
                if (limit_switch.isPressed())
                    pos0 = slide.getCurrentPosition();
                drivemodes();
                drive.run(gamepad1.right_stick_x, gamepad1.right_stick_y, gamepad1.left_stick_x, drive_mode);
                ((DcMotorEx)front_right_port_2).setVelocity(drive.FrontRight()*2700);
                ((DcMotorEx) back_right_port_1).setVelocity(drive.BackRight()*2700);
                ((DcMotorEx)front_left_port_0).setVelocity(drive.FrontLeft()*2700);
                ((DcMotorEx) back_left_port_3).setVelocity(drive.BackLeft()*2700);

               if (gamepad1.right_bumper)
                    plane.setPosition(0);
               else
                    plane.setPosition(0.65);
               if (gamepad1.left_trigger != 0) {
                   slide.setTargetPosition(slide.getTargetPosition() - Math.round(gamepad1.left_trigger * 50));
                   slide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                   ((DcMotorEx) slide).setVelocity(0.5);
               } else {
                   slide.setTargetPosition(slide.getTargetPosition() + Math.round(gamepad1.left_trigger * 50));
                   slide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                   ((DcMotorEx) slide).setVelocity(0.5);
               }
               if (gamepad2.a) {
                   slide.setTargetPosition(pos0);
                   slide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                   ((DcMotorEx) slide).setVelocity(0.5);
               }
                if (gamepad2.y) {
                    slide.setTargetPosition(pos0+500);
                    slide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    ((DcMotorEx) slide).setVelocity(0.5);
                }

               if (gamepad1.dpad_up) {
                    pickup2.setPosition(pickup2_down);
                   dpad_up_last=true;
                }
                //1=claw, 2=rotater
               if (gamepad1.left_bumper || gamepad1.dpad_up) {
                   sleep(1000);
                   pickup1.setPosition(pickup1_open);
               } else {
                   pickup1.setPosition(pickup1_close);
               }
                if (!(gamepad1.left_bumper || gamepad1.dpad_up))
                    pickup1.setPosition(pickup1_close);
               if (!gamepad1.dpad_up) {
                   if (dpad_up_last != gamepad1.dpad_up)
                       sleep(500);
                   pickup2.setPosition(pickup2_up);
               }
                if (!(gamepad1.left_bumper || gamepad1.dpad_up))
                    pickup1.setPosition(pickup1_close);
               if (gamepad1.right_bumper)
                    dropoff1.setPosition(dropoff1_open);
               else
                    dropoff1.setPosition(dropoff1_close);

               if (gamepad1.dpad_down)
                    dropoff2.setPosition(dropoff2_tranfer);
               else if (gamepad1.dpad_right)
                   dropoff2.setPosition(dropoff2_clear);
               else
                    dropoff2.setPosition(dropoff2_drop);
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
        if (gamepad1.a)
            drive_mode=0.8;
        if (gamepad1.x)
            drive_mode=0.5;
        if (gamepad1.b)
            drive_mode=0.25;
    }
}