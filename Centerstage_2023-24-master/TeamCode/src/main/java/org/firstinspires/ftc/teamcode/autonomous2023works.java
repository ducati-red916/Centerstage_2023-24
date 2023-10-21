package org.firstinspires.ftc.teamcode;
/*
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.RobotLog;

import java.util.List;
import org.firstinspires.ftc.robotcore.external.JavaUtil;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.Velocity;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaCurrentGame;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.Tfod;

@Autonomous(name = "autonomous2023")
public class autonomous2023works extends LinearOpMode {


    private VuforiaCurrentGame vuforiaPOWERPLAY;
    private autonomous_dumb_function_imu goto2424;
    private autonomous_dumb_function_imu coneplacement;
    //final private double WheelRadius = 1.375 / 2 * 2.54; //in Inches converted to CM
    final private double CMperTick = 0.0005454545454; //2 * Math.PI * WheelRadius / 8192;
    final private double openpos = 0.5;
    final private double closedpos = 0.65;
    final int slidespeed=-30000;
    int dot=-475;
    int bottom=0;
    private holonomic drive;
    private Tfod tfod;
    private DcMotor back_right_port_1;
    private DcMotor back_left_port_3;
    private DcMotor front_left_port_0;
    private DcMotor front_right_port_2;
    private DcMotor OWLeft;
    private DcMotor OWBack;
    private Servo claw;
    private DcMotor slide1;
    double lastclawpos;
    boolean clawclosed;
    double brr;
    double frr;
    double flr;
    double blr;
    int index;
    int loopcounter;
    BNO055IMU imu;

    // State used for updating telemetry
    Orientation angles;
    Acceleration gravity;

    Recognition recognition;

    @Override
    public void runOpMode() {



        List<Recognition> recognitions;

        drive = new holonomic();
        vuforiaPOWERPLAY = new VuforiaCurrentGame();
        goto2424 = new autonomous_dumb_function_imu();
        coneplacement = new autonomous_dumb_function_imu();
        tfod = new Tfod();
        back_right_port_1 = hardwareMap.get(DcMotor.class, "back_right_port_1");
        back_left_port_3 = hardwareMap.get(DcMotor.class, "back_left_port_3");
        front_left_port_0 = hardwareMap.get(DcMotor.class, "front_left_port_0");
        front_right_port_2 = hardwareMap.get(DcMotor.class, "front_right_port_2");
        OWBack = hardwareMap.get(DcMotor.class, "OWBack");
        claw = hardwareMap.get(Servo.class, "claw");
        slide1 = hardwareMap.get(DcMotor.class, "slide1");
        OWLeft = hardwareMap.get(DcMotor.class, "OWLeft");

        // Sample TFOD Op Mode using a Custom Model
        // Initialize Vuforia to provide TFOD with camera
        // images.
        // The following block uses the device's back camera.
        // The following block uses a webcam.
        vuforiaPOWERPLAY.initialize(
                "", // vuforiaLicenseKey
                hardwareMap.get(WebcamName.class, "Webcam 1"), // cameraName
                "", // webcamCalibrationFilename
                false, // useExtendedTracking
                false, // enableCameraMonitoring
                VuforiaLocalizer.Parameters.CameraMonitorFeedback.AXES, // cameraMonitorFeedback
                0, // dx
                0, // dy
                0, // dz
                AxesOrder.XZY, // axesOrder
                90, // firstAngle
                90, // secondAngle
                0, // thirdAngle
                true); // useCompetitionFieldTargetLocations
        // Initialize TFOD before waitForStart.
        // Use the Manage page to upload your custom model.
        // In the next block, replace
        // YourCustomModel.tflite with the name of your
        // custom model.
        // Set isModelTensorFlow2 to true if you used a TensorFlow
        // 2 tool, such as ftc-ml, to create the model.
        //
        // Set isModelQuantized to true if the model is
        // quantized. Models created with ftc-ml are quantized.
        //
        // Set inputSize to the image size corresponding to the model.
        // If your model is based on SSD MobileNet v2
        // 320x320, the image size is 300 (srsly!).
        // If your model is based on SSD MobileNet V2 FPNLite 320x320, the image size is 320.
        // If your model is based on SSD MobileNet V1 FPN 640x640 or
        // SSD MobileNet V2 FPNLite 640x640, the image size is 640.
        tfod.useModelFromFile("LauraCustImgModel (1).tflite", JavaUtil.createListWith("C", "S", "T"), true, true, 320);
        tfod.initialize(vuforiaPOWERPLAY, (float) 0.7, true, true);
        tfod.setClippingMargins(0, 80, 0, 0);
        tfod.activate();
        // Enable following block to zoom in on target.
        tfod.setZoom(1, 16 / 9);
        telemetry.addData("DS preview on/off", "3 dots, Camera Stream");
        telemetry.addData(">", "Press Play to start");
        telemetry.update();
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit           = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit           = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json"; // see the calibration sample opmode
        parameters.loggingEnabled      = true;
        parameters.loggingTag          = "IMU";
        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();
        imu = hardwareMap.get(BNO055IMU.class, "imu");
        imu.initialize(parameters);
        // Wait for start command from Driver Station.
        waitForStart();

        imu.startAccelerationIntegration(new Position(), new Velocity(), 1000);

        if (opModeIsActive()) {
            clawclosed = true;
            angles   = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
            gravity  = imu.getGravity();
            back_right_port_1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            back_left_port_3.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            front_right_port_2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            front_left_port_0.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            front_left_port_0.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            front_right_port_2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            back_right_port_1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            back_left_port_3.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            front_right_port_2.setDirection(DcMotorSimple.Direction.REVERSE);
            back_right_port_1.setDirection(DcMotorSimple.Direction.REVERSE);
            OWLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            OWBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            slide1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            loopcounter = 0;
            // Put loop blocks here.
            while (opModeIsActive()) {
                claw.setPosition(closedpos);
                angles   = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
                gravity  = imu.getGravity();
                // Get a list of recognitions from TFOD.
                recognitions = tfod.getRecognitions();
                // Changing game pad input to variables
                // If list is empty, inform the user. Otherwise, go
                // through list and display info for each recognition.
                if (JavaUtil.listLength(recognitions) == 0) {
                    telemetry.addData("TFOD", "No items detected.");
                } else {
                    index = 0;
                    // Iterate through list and call a function to
                    // display info for each recognized object.
                    for (Recognition recognition_item : recognitions) {
                        recognition = recognition_item;
                        // Display info.
                        displayInfo();
                        // Increment index.
                        index = index + 1;
                    }
                }
                // Iterate through list and6         call a function to
                // display info for each recognized object.
                if (lastclawpos-claw.getPosition()<0.01) {
                    if(loopcounter==50) {
                        slide1.setTargetPosition(dot);
                        slide1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                        ((DcMotorEx) slide1).setVelocity(slidespeed);
                    }
                    loopcounter+=1;
                    //coneplacement.Run(OWLeft.getCurrentPosition(), OWBack.getCurrentPosition(), angles.firstAngle, 6, 15, 0.25, 0.1, 2);
                    //if (coneplacement.atpos()) {
                        for (Recognition recognition_item2 : recognitions) {
                            recognition = recognition_item2;
                            // Need to reset OW Position to zero
                            // back port 3, Left Port 1
                            if (recognition.getLabel().equals("C")) { //speed 0.35 with near radius 12 also works much smoother
                                goto2424.Run(OWLeft.getCurrentPosition(), OWBack.getCurrentPosition(), angles.firstAngle, -25, 27, 0.5, 0.5, 25);
                            }
                            if (recognition.getLabel().equals("S")) {
                                goto2424.Run(OWLeft.getCurrentPosition(), OWBack.getCurrentPosition(), angles.firstAngle, 25, 27, 0.5, 0.5, 25);
                            }
                            if (recognition.getLabel().equals("T")) {
                                goto2424.Run(OWLeft.getCurrentPosition(), OWBack.getCurrentPosition(), angles.firstAngle, 0, 27, 0.5, 0.5, 25);
                            }
                            displayInfo();
                        }
                    //}
                    drive.run(goto2424.Xpower, goto2424.Ypower, goto2424.Thetapower, 1);
                    ((DcMotorEx)front_right_port_2).setVelocity(drive.FrontRight()*2700);
                    ((DcMotorEx) back_right_port_1).setVelocity(drive.BackRight()*2700);
                    ((DcMotorEx)front_left_port_0).setVelocity(drive.FrontLeft()*2700);
                    ((DcMotorEx) back_left_port_3).setVelocity(drive.BackLeft()*2700);
                    if(goto2424.atpos()){
                        slide1.setTargetPosition(bottom);
                        slide1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                        ((DcMotorEx) slide1).setVelocity(slidespeed);
                    }
                }
            }
        }

        vuforiaPOWERPLAY.close();
        tfod.close();
    }

    private void clawcloseslidep() {
        claw.setPosition(0.7);
        slide1.setTargetPosition(-100);
        ((DcMotorEx) slide1).setVelocity(-2000);
        slide1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    private void powerto0() {
        front_left_port_0.setPower(0);
        back_left_port_3.setPower(0);
        back_right_port_1.setPower(0);
        front_right_port_2.setPower(0);
    }

    private void holonomic() {
    }

    private void slide_down() {
        while (opModeIsActive()) {
        }
    }

    private void displayInfo() {
        // Display the location of the top left corner
        // of the detection boundary for the recognition
        telemetry.addData("Label: " + recognition.getLabel() + ", Confidence: " + recognition.getConfidence(), "X: " + Math.round(JavaUtil.averageOfList(JavaUtil.createListWith(Double.parseDouble(JavaUtil.formatNumber(recognition.getLeft(), 0)), Double.parseDouble(JavaUtil.formatNumber(recognition.getRight(), 0))))) + ", Y: " + Math.round(JavaUtil.averageOfList(JavaUtil.createListWith(Double.parseDouble(JavaUtil.formatNumber(recognition.getTop(), 0)), Double.parseDouble(JavaUtil.formatNumber(recognition.getBottom(), 0))))));
        telemetry.addData("frontrightpower", front_right_port_2.getPower());
        telemetry.addData("backrighpower", back_right_port_1.getPower());
        telemetry.addData("frontleftpower", front_left_port_0.getPower());
        telemetry.addData("backleftpower", back_left_port_3.getPower());
        telemetry.addData("Xpower", goto2424.Xpower);
        telemetry.addData("Ypower", goto2424.Ypower);
        telemetry.addData("Thetapower", goto2424.Thetapower);
        telemetry.addData("leftOW(ticks)", OWLeft.getCurrentPosition());
        telemetry.addData("backOW(ticks)", OWBack.getCurrentPosition());
        telemetry.addData("leftOW(inches)", OWLeft.getCurrentPosition()*CMperTick);
        telemetry.addData("backOW(inches)", OWBack.getCurrentPosition()*CMperTick);
        RobotLog.aa("frontrightpower", ""+front_right_port_2.getPower());
        RobotLog.aa("backrighpower", ""+back_right_port_1.getPower());
        RobotLog.aa("frontleftpower", ""+front_left_port_0.getPower());
        RobotLog.aa("backleftpower", ""+back_left_port_3.getPower());
        RobotLog.aa("Xpower", ""+goto2424.Xpower);
        RobotLog.aa("Ypower", ""+goto2424.Ypower);
        RobotLog.aa("Thetapower", ""+goto2424.Thetapower);
        RobotLog.aa("heading", ""+goto2424.DeltaTheta);
        RobotLog.aa("leftOW(ticks)", ""+OWLeft.getCurrentPosition());
        RobotLog.aa("backOW(ticks)", ""+OWBack.getCurrentPosition());
        RobotLog.aa("leftOW(inches)", ""+goto2424.PosY);
        RobotLog.aa("backOW(inches)", ""+goto2424.PosX);
        telemetry.log();
        tfod.deactivate();
        telemetry.update();
    }
}
*/