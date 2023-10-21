package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;
import java.util.Timer;

@TeleOp(name = "android studio maybeworking")
public class android_studio_maybeworking extends LinearOpMode {

    private DcMotor back_left_port_3;
    private DcMotor back_right_port_1;
    private DcMotor front_right_port_2;
    private Servo claw;
    private DcMotor slide1;
    private DcMotor front_left_port_0;
 //   private Gamepad gamepad1;
 //   private Gamepad gamepad2;

    double drive_mode;
    boolean clawclosed;
    int Odometry_left;
    int Odometry_right;
    int Odometry_back;
    int maingamepad;
    boolean gamepad2backispressed;
    boolean leftbumperispressed;
    double lastclawpos;
    boolean toggle;
    double Vertical;
    double Horizontal;
    double Pivot;
//    boolean clawflag;


    holonomic drive;
    Timer T = new Timer();
    final double startdrivemode = 0.5;
    final boolean telemtoggle = true;
    final boolean clawstartclosed=true;
    final double closedpos=0.65;
    final double openpos=0.5;
    final int driveheightstart=-280;
    final int highpolestart=-11650;
    final int midpolestart=-7450;
    final int lowpolestart=-4460;
    final int dotstart=-475;
    final int bottomstart=0;

    int driveheight=driveheightstart;
    int highpole=highpolestart;
    int midpole=midpolestart;
    int lowpole=lowpolestart;
    int dot=dotstart;
    int bottom=bottomstart;

    final int slidespeed=-7500;

    /**
     * Describe this function...
     */

    private void autosetvariables(){

        claw.scaleRange(openpos,closedpos);
        if (clawstartclosed){
//            clawflag=true;
            clawclosed=false;
        } else{
 //           clawflag=true;
            clawclosed=true;
        }
        if(clawstartclosed){
            lastclawpos=closedpos;
        } else{
            lastclawpos=openpos;
        }

        drive_mode=startdrivemode;
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
        drive = new holonomic();
  //      gamepad1=gamepad1;
  //      gamepad2=gamepad2;
  //      maingamepad=1;

        motormodes();
        waitForStart();
        autosetvariables();
        if (opModeIsActive()) {
            while (opModeIsActive()) {
 //               gamepads();
                drivemodes(); //sets drivemodes to their correct values
                drive.run(gamepad1.right_stick_x, gamepad1.right_stick_y, gamepad1.left_stick_x, drive_mode);
                front_right_port_2.setPower(drive.FrontRight());
                back_right_port_1.setPower(drive.BackRight());
                front_left_port_0.setPower(drive.FrontLeft());
                back_left_port_3.setPower(drive.BackLeft());

                slide();
                servo();
                slide_positions_adjustment();

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
        telemetry.addData("Lbumper", gamepad1.left_bumper);
        telemetry.addData("Rbumper", gamepad1.right_bumper);
        telemetry.addData("clawpos", claw.getPosition());
        telemetry.addData("X", gamepad1.x);
        telemetry.addData("slidepower", slide1.getPower());
        telemetry.addData("Slide1POS", slide1.getCurrentPosition());
        // Configuration file needs to be updated to match where the OW are plugged into the robot.  Now plugged into motor encoder positions.
        telemetry.addData("OW left", Odometry_left);
        telemetry.addData("OW right", Odometry_right);
        telemetry.addData("Y", gamepad1.y);
        telemetry.addData("OW back", Odometry_back);
        telemetry.update();
    }

    private void gamepads(){
        if (gamepad2.back) {
            if (maingamepad == 1 & gamepad2backispressed) {
                gamepad1 = gamepad2;
                gamepad2 = gamepad1;
                maingamepad = 2;
                gamepad2backispressed = false;
            }
            if (maingamepad == 2 & gamepad2backispressed) {
                gamepad1 = gamepad1;
                gamepad2 = gamepad2;
                maingamepad = 1;
                gamepad2backispressed = false;
            }
        } else {
            gamepad2backispressed = true;
        }
    }

    private void motormodes() {
        slide1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        // The next block resets the slide encoder to zero.  Make sure the slide is fully down before initializing the program.
        slide1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        slide1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
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

    /**
     * Describe this function...
     */
    private void slide() {
        if (gamepad1.dpad_up) {
            // -3500 appears to be full height extension
            slide1.setTargetPosition(highpole);
            slide1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            ((DcMotorEx) slide1).setVelocity(slidespeed);
        }
        if(gamepad1.left_trigger>0){
            slide1.setTargetPosition(slide1.getTargetPosition()-Math.round(gamepad1.left_trigger*50));
            slide1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            ((DcMotorEx) slide1).setVelocity(slidespeed);
        }
        if(gamepad1.right_trigger>0){
            slide1.setTargetPosition(slide1.getTargetPosition()+Math.round(gamepad1.right_trigger*50));
            slide1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            ((DcMotorEx) slide1).setVelocity(slidespeed);
        }
        if (gamepad1.dpad_left) {
            // -3500 appears to be full height extension
            slide1.setTargetPosition(midpole);
            slide1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            ((DcMotorEx) slide1).setVelocity(slidespeed);
        }
        if (gamepad1.dpad_right) {
            // -3500 appears to be full height extension
            slide1.setTargetPosition(lowpole);
            slide1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            ((DcMotorEx) slide1).setVelocity(slidespeed);
        }
        if (gamepad1.dpad_down) {
            // -3500 appears to be full height extension
            slide1.setTargetPosition(dot);
            slide1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            ((DcMotorEx) slide1).setVelocity(slidespeed);
        }
        if (gamepad1.y) {
            slide1.setTargetPosition(bottom);
            slide1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            ((DcMotorEx) slide1).setVelocity(slidespeed);
        }
        /*
        //If claw is nearly closed, then raise slide
        if (slide1.getCurrentPosition()==0 && lastclawpos-claw.getPosition()<0.01 && clawflag){
            clawflag=false;
            slide1.setTargetPosition(driveheight);
            slide1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            ((DcMotorEx) slide1).setVelocity(slidespeed);
        }

         */
    }

    private void slide_positions_adjustment(){
        //if (gamepad2.dpad_left || gamepad2.dpad_up || gamepad2.dpad_down || gamepad2.dpad_right || gamepad2.y) {
            if (gamepad2.dpad_left && gamepad2.left_trigger > 0){
                midpole -= Math.round(gamepad1.left_trigger * 50);
            }
            if (gamepad2.dpad_left && gamepad2.right_trigger > 0){
                midpole += Math.round(gamepad1.right_trigger * 50);
            }
            if (gamepad2.dpad_down && gamepad2.left_trigger > 0){
                dot -= Math.round(gamepad1.left_trigger * 50);
            }
            if (gamepad2.dpad_down && gamepad2.right_trigger > 0){
                dot += Math.round(gamepad1.right_trigger * 50);
            }
            if (gamepad2.dpad_up && gamepad2.left_trigger > 0){
                highpole -= Math.round(gamepad1.left_trigger * 50);
            }
            if (gamepad2.dpad_up && gamepad2.right_trigger > 0){
                highpole += Math.round(gamepad1.right_trigger * 50);
            }
            if (gamepad2.dpad_right && gamepad2.left_trigger > 0){
                lowpole -= Math.round(gamepad1.left_trigger * 50);
            }
            if (gamepad2.dpad_right && gamepad2.right_trigger > 0){
                lowpole += Math.round(gamepad1.right_trigger * 50);
            }
            if (gamepad2.y && gamepad2.left_trigger > 0){
                bottom -= Math.round(gamepad1.left_trigger * 50);
            }
            if (gamepad2.y && gamepad2.right_trigger > 0){
                bottom += Math.round(gamepad1.right_trigger * 50);
            }


        //} else {

            if (gamepad2.left_trigger > 0) {
                slide1.setTargetPosition(slide1.getTargetPosition() - Math.round(gamepad1.left_trigger * 50));
                slide1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                ((DcMotorEx) slide1).setVelocity(slidespeed);
            }
            if (gamepad2.right_trigger > 0) {
                slide1.setTargetPosition(slide1.getTargetPosition() + Math.round(gamepad1.right_trigger * 50));
                slide1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                ((DcMotorEx) slide1).setVelocity(slidespeed);
            }
        //}
        if (gamepad2.left_bumper && gamepad2.right_bumper){
            int driveheight=driveheightstart;
            int highpole=highpolestart;
            int midpole=midpolestart;
            int lowpole=lowpolestart;
            int dot=dotstart;
            int bottom=bottomstart;
        }
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

    /**
     * Describe this function...
     */
    private void servo() {

        if (gamepad1.left_bumper & leftbumperispressed) {

            clawclosed = ! clawclosed;
//            clawflag = ! clawflag;
            leftbumperispressed = false;


        } else {
            leftbumperispressed=true;
        }
/*
        if (gamepad1.right_bumper) {
            clawclosed=true;
            clawflag=true;
        }
*/
        lastclawpos=claw.getPosition();
        if (clawclosed){
            if(claw.getPosition()!=closedpos){
                claw.setPosition(lastclawpos+0.1);
            }
        } else{
            if(claw.getPosition()!=openpos){
                claw.setPosition(lastclawpos-0.1);
            }
        }
    }
}