package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.RobotLog;

import java.util.List;
import org.firstinspires.ftc.robotcore.external.JavaUtil;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaCurrentGame;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.Tfod;

@Autonomous(name = "autonomous2023_no_correction")
@Disabled
public class autonomous2023_no_correction extends LinearOpMode {


    private VuforiaCurrentGame vuforiaPOWERPLAY;
    private autonomous_dumb_function_uses_holonomic goto2424;
    final private double WheelRadius = 1.375 / 2 * 2.54; //in Inches converted to CM
    final private double CMperTick = 2 * Math.PI * WheelRadius / 8192;
    private holonomic drive;
    private Tfod tfod;
    private DcMotor back_right_port_1;
    private DcMotor back_left_port_3;
    private DcMotor front_left_port_0;
    private DcMotor front_right_port_2;
    private DcMotor OWBack;
    private Servo claw;
    private DcMotor slide1;
    private DcMotor OWRight;
    double brr;
    double frr;
    double flr;
    double blr;
    int index;

    Recognition recognition;

    /**
     * This function is executed when this Op Mode is selected from the Driver Station.
     */
    @Override
    public void runOpMode() {



        List<Recognition> recognitions;

        drive = new holonomic();
        vuforiaPOWERPLAY = new VuforiaCurrentGame();
        goto2424 = new autonomous_dumb_function_uses_holonomic();
        tfod = new Tfod();
        back_right_port_1 = hardwareMap.get(DcMotor.class, "back_right_port_1");
        back_left_port_3 = hardwareMap.get(DcMotor.class, "back_left_port_3");
        front_left_port_0 = hardwareMap.get(DcMotor.class, "front_left_port_0");
        front_right_port_2 = hardwareMap.get(DcMotor.class, "front_right_port_2");
        OWBack = hardwareMap.get(DcMotor.class, "OWBack");
        claw = hardwareMap.get(Servo.class, "claw");
        slide1 = hardwareMap.get(DcMotor.class, "slide1");
        OWRight = hardwareMap.get(DcMotor.class, "OWRight");

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
        // Wait for start command from Driver Station.
        waitForStart();
        if (opModeIsActive()) {
            back_right_port_1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            back_left_port_3.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            front_right_port_2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            front_left_port_0.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            front_left_port_0.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            front_right_port_2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            back_right_port_1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            back_left_port_3.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            front_right_port_2.setDirection(DcMotorSimple.Direction.REVERSE);
            back_right_port_1.setDirection(DcMotorSimple.Direction.REVERSE);
            // Put loop blocks here.
            while (opModeIsActive()) {
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
                // Iterate through list and call a function to
                // display info for each recognized object.
                for (Recognition recognition_item2 : recognitions) {
                    recognition = recognition_item2;
                    // Need to reset OW Position to zero
                    // back port 3, Left Port 1
                    if (recognition.getLabel().equals("C")) {
                       goto2424.Run(-back_right_port_1.getCurrentPosition(), back_left_port_3.getCurrentPosition(), -20, 32, 0.25, 1);
                       drive.run(goto2424.Xpower, goto2424.Ypower, goto2424.Thetapower, 1);
                        front_right_port_2.setPower(drive.FrontRight);
                        back_right_port_1.setPower(drive.BackRight);
                        front_left_port_0.setPower(drive.FrontLeft);
                        back_left_port_3.setPower(drive.BackLeft);
                        displayInfo();
                    }
                    if (recognition.getLabel().equals("S")) {
                        goto2424.Run(-back_right_port_1.getCurrentPosition(), back_left_port_3.getCurrentPosition(), 20, 32, 0.25, 1);
                        drive.run(goto2424.Xpower, goto2424.Ypower, goto2424.Thetapower, 1);
                        front_right_port_2.setPower(drive.FrontRight);
                        back_right_port_1.setPower(drive.BackRight);
                        front_left_port_0.setPower(drive.FrontLeft);
                        back_left_port_3.setPower(drive.BackLeft);
                        displayInfo();
                    }
                    if (recognition.getLabel().equals("T")) {
                        goto2424.Run(-back_right_port_1.getCurrentPosition(), back_left_port_3.getCurrentPosition(), 0, 32, 0.25, 1);
                        drive.run(goto2424.Xpower, goto2424.Ypower, goto2424.Thetapower, 1);
                        front_right_port_2.setPower(drive.FrontRight);
                        back_right_port_1.setPower(drive.BackRight);
                        front_left_port_0.setPower(drive.FrontLeft);
                        back_left_port_3.setPower(drive.BackLeft);
                        displayInfo();
                    }
                }
            }
        }

        vuforiaPOWERPLAY.close();
        tfod.close();
    }

    /**
     * Describe this function...
     */

    /**
     * Describe this function...
     */
    private void clawcloseslidep() {
        claw.setPosition(0.7);
        slide1.setTargetPosition(-100);
        ((DcMotorEx) slide1).setVelocity(-2000);
        slide1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    /**
     * Describe this function...
     */
    private void powerto0() {
        front_left_port_0.setPower(0);
        back_left_port_3.setPower(0);
        back_right_port_1.setPower(0);
        front_right_port_2.setPower(0);
    }

    /**
     * Describe this function...
     */
    private void holonomic() {
    }

    /**
     * Describe this function...
     */
    private void slide_down() {
        while (opModeIsActive()) {
        }
    }

    /**
     * Describe this function...
     */
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
        telemetry.addData("leftOW(ticks)", -back_right_port_1.getCurrentPosition());
        telemetry.addData("backOW(ticks)", back_left_port_3.getCurrentPosition());
        telemetry.addData("leftOW(inches)", -back_right_port_1.getCurrentPosition()*CMperTick);
        telemetry.addData("backOW(inches)", back_left_port_3.getCurrentPosition()*CMperTick);
        RobotLog.aa("frontrightpower", ""+front_right_port_2.getPower());
        RobotLog.aa("backrighpower", ""+back_right_port_1.getPower());
        RobotLog.aa("frontleftpower", ""+front_left_port_0.getPower());
        RobotLog.aa("backleftpower", ""+back_left_port_3.getPower());
        RobotLog.aa("Xpower", ""+goto2424.Xpower);
        RobotLog.aa("Ypower", ""+goto2424.Ypower);
        RobotLog.aa("Thetapower", ""+goto2424.Thetapower);
        RobotLog.aa("leftOW(ticks)", ""+-back_right_port_1.getCurrentPosition());
        RobotLog.aa("backOW(ticks)", ""+back_left_port_3.getCurrentPosition());
        RobotLog.aa("leftOW(inches)", ""+-back_right_port_1.getCurrentPosition()*CMperTick);
        RobotLog.aa("backOW(inches)", ""+back_left_port_3.getCurrentPosition()*CMperTick);
        telemetry.log();
        tfod.deactivate();
        telemetry.update();
    }
}