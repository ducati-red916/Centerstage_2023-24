package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;

@TeleOp(name = "android studio claws simple")
public class android_studio_claws_simple extends LinearOpMode {

    private DcMotor back_left_port_3;
    private DcMotor back_right_port_1;
    private DcMotor front_right_port_2;
    private DcMotor front_left_port_0;
    private DcMotor slide;
    private DcMotor hanger;
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
    boolean bottom_claw_toggle = false;
    private TouchSensor limit_switch;
    private boolean flag;
    boolean dpad_down_last = true;
    boolean dpad_up_last = true;
    boolean top_claw_toggle = true;
    boolean dpad_right_last = true;
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
        hanger = hardwareMap.get(DcMotor.class, "hanger");
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
                if (gamepad2.right_trigger != 0)
                    slide.setPower(gamepad2.right_trigger);
                else
                    slide.setPower(-gamepad2.left_trigger);
                if (gamepad1.right_trigger != 0)
                    hanger.setPower(gamepad1.right_trigger);
                else
                    hanger.setPower(-gamepad1.left_trigger);

                if (gamepad2.dpad_up){
                    dropoff2.setPosition(dropoff2_drop);
                } else if (gamepad2.dpad_right)
                    dropoff2.setPosition(dropoff2_clear);
                else
                    dropoff2.setPosition(dropoff2_tranfer);

                if (gamepad2.dpad_down)
                    pickup2.setPosition(pickup2_down);
                else
                    pickup2.setPosition(pickup2_up);

                if (gamepad2.y)
                    dropoff1.setPosition(dropoff1_open);
                else
                    dropoff1.setPosition(dropoff1_close);

                if (gamepad2.a)
                    pickup1.setPosition(pickup1_open);
                else
                    pickup1.setPosition(pickup1_close);

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