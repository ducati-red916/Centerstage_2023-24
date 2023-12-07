package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "android studio claws values")
public class android_studio_claws_values extends LinearOpMode {

    private DcMotor back_left_port_3;
    private DcMotor back_right_port_1;
    private DcMotor front_right_port_2;
    private DcMotor front_left_port_0;
    private DcMotor slide;
    double drive_mode = 0.5;
    double pickup1_close =0.14;
    double pickup2_up =0.25;
    double pickup2_down =0.93;
    double dropoff1_close = 0.06;
    double dropoff2_up = 0.7;
    double dropoff2_down = 0.89;
    holonomic drive;

    int loopcounter;

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
               slide.setPower(gamepad1.right_trigger);
               if (gamepad1.left_trigger != 0)
                   slide.setPower(-gamepad1.left_trigger);



                //1=claw, 2=rotater
                if (gamepad2.dpad_left || gamepad2.dpad_down)
                   pickup1.setPosition(pickup1_close);
               else
                   pickup1.setPosition(0);

               if (gamepad2.dpad_down) {
                   pickup2.setPosition(pickup2_up);
               } else
                   pickup2.setPosition(pickup2_down);


               if (gamepad2.dpad_right)
                    dropoff1.setPosition(dropoff1_close);
               else
                    dropoff1.setPosition(0);

                if (gamepad2.dpad_up) {
                    dropoff2.setPosition(dropoff2_up);
                }else
                    dropoff2.setPosition(dropoff2_down);
                if (loopcounter++%100==0){
                    dropoff2_up+=gamepad2.left_stick_y*0.1;
                    dropoff2_down+= gamepad2.left_stick_x*0.1;
                    pickup2_up+=gamepad2.right_stick_y*0.1;
                    pickup2_down+= gamepad2.right_stick_x*0.1;
                }
                telemetry.addData("dropoff2_up", dropoff2_up);
                telemetry.addData("dropoff2_down", dropoff2_down);
                telemetry.addData("pickup2_up", pickup2_up);
                telemetry.addData("pickup2_down", pickup2_down);
                telemetry.update();
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