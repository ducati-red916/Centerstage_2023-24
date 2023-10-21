package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "androidstudiolastminute")
public class androidstudiolastminute extends LinearOpMode {

    private DcMotor back_left_port_3;
    private DcMotor back_right_port_1;
    private DcMotor front_right_port_2;
    private Servo claw;
    private DcMotor slide1;
    private DcMotor front_left_port_0;

    double drive_mode;
    boolean clawclosed;
    int clawstate;
    int Odometry_left;
    int Odometry_right;
    int Odometry_back;
    double lastclawpos;
    boolean toggle;
    double Vertical;
    double Horizontal;
    double Pivot;
    boolean clawflag;
    cOdometry oRobotPosition;
    holonomic drive;
    final double startdrivemode = 0.5;
    final boolean telemtoggle = true;
    final boolean clawstartclosed=true;
    final double closedpos=0.65;
    final double openpos=0.5;
    final int driveheight=-280;
    final int highpole=-11650;
    final int midpole=-7450;
    final int lowpole=-4460;
    final int dot=-475;
    final int bottom=0;
    final int slidespeed=-7500;

    /**
     * Describe this function...
     */
    private void Dynamic_variables() {
        Odometry_left = back_left_port_3.getCurrentPosition();
        Odometry_right = back_right_port_1.getCurrentPosition();
        Odometry_back = front_right_port_2.getCurrentPosition();
    }

    private void autosetvariables(){
        claw.scaleRange(openpos,closedpos);
        if (clawstartclosed){
            clawflag=true;
            clawclosed=false;
        } else{
            clawflag=true;
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
        oRobotPosition = new cOdometry();

        oRobotPosition.Init(back_left_port_3.getCurrentPosition(), back_right_port_1.getCurrentPosition(), front_right_port_2.getCurrentPosition());


        motormodes();
        waitForStart();
        autosetvariables();
        if (opModeIsActive()) {
            while (opModeIsActive()) {
                oRobotPosition.Run(back_left_port_3.getCurrentPosition(), back_right_port_1.getCurrentPosition(), front_right_port_2.getCurrentPosition());
                Dynamic_variables();
                drivemodes(); //sets drivemodes to their correct values
                drive.run(gamepad1.right_stick_x, gamepad1.right_stick_y, gamepad1.left_stick_x, drive_mode);
                front_right_port_2.setPower(drive.FrontRight());
                back_right_port_1.setPower(drive.BackRight());
                front_left_port_0.setPower(drive.FrontLeft());
                back_left_port_3.setPower(drive.BackLeft());
                servo();
                slide();


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
        telemetry.addData("X Pos", oRobotPosition.X());
        telemetry.addData("Y Pos", oRobotPosition.Y());
        telemetry.addData("Theta", oRobotPosition.Theta());
        telemetry.update();
    }

    /**
     * Describe this function...
     */
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
    }

    /**
     * Describe this function...
     */
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
        /*
        if(gamepad1.left_bumper) {
            if (toggle==false){
                toggle=true;
            }
            if (toggle==true){
                toggle=false;
            }
        }
        */

        if (gamepad1.left_bumper) {

                clawclosed = false;
                clawflag = false;
                //         clawstate=1;


        }
        /*
        if (gamepad1.left_bumper && clawstate==1) {
            clawstate=0;
            clawclosed=true;
            clawflag=true;
        }
        */


        if (gamepad1.right_bumper) {
            clawclosed=true;
            clawflag=true;
        }




        //If claw is nearly closed, then raise slide
        if (slide1.getCurrentPosition()==0 && lastclawpos-claw.getPosition()<0.01 && clawflag){
            clawflag=false;
            slide1.setTargetPosition(driveheight);
            slide1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            ((DcMotorEx) slide1).setVelocity(slidespeed);
        }
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