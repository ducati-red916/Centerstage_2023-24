package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "android studio claws")
public class android_studio_claws extends LinearOpMode {

    private DcMotor back_left_port_3;
    private DcMotor back_right_port_1;
    private DcMotor front_right_port_2;
    private DcMotor front_left_port_0;
    double drive_mode=0.5;
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

               if (gamepad1.dpad_left) {
                   pickup1.setPosition(0.5);
                   pickup2.setPosition(0);
               } else if (gamepad1.dpad_down){
                   pickup1.setPosition(0);
                   pickup2.setPosition(1);
               }

                if (gamepad1.dpad_right) {
                    dropoff1.setPosition(1);
                    dropoff2.setPosition(1);
                } else if (gamepad1.dpad_up) {
                    dropoff1.setPosition(0.5);
                    dropoff2.setPosition(0);
                }
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