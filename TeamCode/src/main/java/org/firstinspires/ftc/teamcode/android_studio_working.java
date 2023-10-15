package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.Velocity;

@TeleOp(name = "android studio working")
public class android_studio_working extends LinearOpMode {

    private DcMotor back_left_port_3;
    private DcMotor back_right_port_1;
    private DcMotor front_right_port_2;
    private Servo claw;
    private DcMotor slide1;
    private DcMotor front_left_port_0;
    private Gamepad gamepad3;
    private Gamepad gamepad4;

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


    holonomic drive;
    final double startdrivemode = 0.5;
    final boolean telemtoggle = true;
    final boolean clawstartclosed=true;
    final double closedpos=0.65;
    final double openpos=0.5;
    /*
    int driveheight=-280;
    int highpole=-10193;
    int midpole=-7450;
    int lowpole=-4500;
    int dot=-475;
    int bottom=0;

     */
    int driveheight=-75;
    int highpole=-2743;
    int midpole=-2005;
    int lowpole=-1211;
    int dot=-128;
    int bottom=0;

    BNO055IMU imu;
    double DeltaTheta;
    final double Thetanearradius=10;
    final double thetaspeed=0.6;
    double Thetapower;

    // State used for updating telemetry
    //Orientation angles;
    //Acceleration gravity;

    final int slidespeed=-30000;

    /**
     * Describe this function...
     */

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
        imustuff();
        motormodes();
        waitForStart();
        //imu.startAccelerationIntegration(new Position(), new Velocity(), 1000);
        autosetvariables();
        if (opModeIsActive()) {
            //angles   = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
            //gravity  = imu.getGravity();
            while (opModeIsActive()) {
                drivemodes(); //sets drivemodes to their correct values
                drive.run(gamepad1.right_stick_x, gamepad1.right_stick_y, Thetapower, drive_mode);
               front_right_port_2.setPower(drive.FrontRight());
               back_right_port_1.setPower(drive.BackRight());
               front_left_port_0.setPower(drive.FrontLeft());
               back_left_port_3.setPower(drive.BackLeft());

                slide();
                servo();
                //slide_positions_adjustment();

                if (telemtoggle) {
                    telem();
                }
            }
        }
    }

    private void imustuff() {
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit           = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit           = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json"; // see the calibration sample opmode
        parameters.loggingEnabled      = true;
        parameters.loggingTag          = "IMU";
        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();
        imu = hardwareMap.get(BNO055IMU.class, "imu");
        imu.initialize(parameters);
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
       // telemetry.addData("heading", angles.firstAngle);
        telemetry.update();
    }
/*
    private void to90() {
        if (gamepad1.left_stick_button) {
            DeltaTheta = -90 - angles.firstAngle;
            angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
            gravity = imu.getGravity();
            if (Math.abs(DeltaTheta) > 1) {
                if (DeltaTheta < Thetanearradius) {
                    Thetapower = -thetaspeed * -Math.sin(DeltaTheta * Math.PI * 1 / 2 * 1 / Thetanearradius); // start slowing when inside 24 inches

                } else {
                    Thetapower = -thetaspeed * -(Math.abs(DeltaTheta) / DeltaTheta);
                }
            }
        }
    }
    */


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
        //If claw is nearly closed, then raise slide
        if (slide1.getCurrentPosition()==0 && lastclawpos-claw.getPosition()<0.01 && clawflag){
            clawflag=false;
            slide1.setTargetPosition(driveheight);
            slide1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            ((DcMotorEx) slide1).setVelocity(slidespeed);
        }
    }
/*
    private void slide_positions_adjustment(){
        if (gamepad2.dpad_left || gamepad2.dpad_up || gamepad2.dpad_down || gamepad2.dpad_right || gamepad2.y) {
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


        } else {

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
        }
    }*/


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
        /*
        if (!gamepad1.left_stick_button){
            Thetapower = gamepad1.left_stick_x;
        }

         */
    }

    /**
     * Describe this function...
     */
    private void servo() {

        if (gamepad1.left_bumper) {

            clawclosed = false;
            clawflag = false;
            //         clawstate=1;


        }

        if (gamepad1.right_bumper) {
            clawclosed=true;
            clawflag=true;
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