package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import java.util.List;
import org.firstinspires.ftc.robotcore.external.JavaUtil;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaCurrentGame;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.Tfod;

//@Autonomous(name = "CustomSleeveTest (Blocks to Java)")
public class atoiou extends LinearOpMode {

    private VuforiaCurrentGame vuforiaPOWERPLAY;
    private Tfod tfod;
    private DcMotor OWLeft;
    private DcMotor front_right_port_2;
    private DcMotor back_right_port_1;
    private DcMotor front_left_port_0;
    private DcMotor back_left_port_3;
    private DcMotor OWBack;
    private DcMotor OWRight;
    autonomous_dumb_function gotopos;
    Recognition recognition;

    /**
     * This function is executed when this Op Mode is selected from the Driver Station.
     */
    @Override
    public void runOpMode() {
        List<Recognition> recognitions;
        int index;
        gotopos = new autonomous_dumb_function();

        vuforiaPOWERPLAY = new VuforiaCurrentGame();
        tfod = new Tfod();
        OWLeft = hardwareMap.get(DcMotor.class, "OWLeft");
        front_right_port_2 = hardwareMap.get(DcMotor.class, "front_right_port_2");
        back_right_port_1 = hardwareMap.get(DcMotor.class, "back_right_port_1");
        front_left_port_0 = hardwareMap.get(DcMotor.class, "front_left_port_0");
        back_left_port_3 = hardwareMap.get(DcMotor.class, "back_left_port_3");
        OWBack = hardwareMap.get(DcMotor.class, "OWBack");
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
            // Put run blocks here.
            while (opModeIsActive()) {
                // Put loop blocks here.
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
                        displayInfo(index);
                        // Increment index.
                        index = index + 1;
                    }
                }
                // Verify, looks like movement commands based on detection must be inside the Index repeat while loop
                // Iterate through list and call a function to
                // display info for each recognized object.
                for (Recognition recognition_item2 : recognitions) {
                    recognition = recognition_item2;
                    // Need to reset OW Position to zero
                    // Only need two detections, if not first two then third.
                    if (recognition.getLabel().equals("C")) {

                     //   while (gotopos.atpos()) {
                            gotopos.Run(back_right_port_1.getCurrentPosition(), back_left_port_3.getCurrentPosition(), -24, 24, 0.25, 2);
                            back_left_port_3.setPower(gotopos.BackLeft);
                            back_right_port_1.setPower(gotopos.BackRight);
                            front_left_port_0.setPower(gotopos.FrontLeft);
                            front_right_port_2.setPower(gotopos.FrontRight);
                      //  }

                    } else if (recognition.getLabel().equals("S")) {
                        gotopos.Run(back_right_port_1.getCurrentPosition(), back_left_port_3.getCurrentPosition(), 24, 24, 0.25, 2);
                        back_left_port_3.setPower(gotopos.BackLeft);
                        back_right_port_1.setPower(gotopos.BackRight);
                        front_left_port_0.setPower(gotopos.FrontLeft);
                        front_right_port_2.setPower(gotopos.FrontRight);
                    } else if (recognition.getLabel().equals("T")) {
                        gotopos.Run(back_right_port_1.getCurrentPosition(), back_left_port_3.getCurrentPosition(), 0, 24, 0.25, 2);
                        back_left_port_3.setPower(gotopos.BackLeft);
                        back_right_port_1.setPower(gotopos.BackRight);
                        front_left_port_0.setPower(gotopos.FrontLeft);
                        front_right_port_2.setPower(gotopos.FrontRight);
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

    /**
     * Describe this function...
     */
    private void displayInfo(int i) {
        // Display the location of the top left corner
        // of the detection boundary for the recognition
        telemetry.addData("Label: " + recognition.getLabel() + ", Confidence: " + recognition.getConfidence(), "X: " + Math.round(JavaUtil.averageOfList(JavaUtil.createListWith(Double.parseDouble(JavaUtil.formatNumber(recognition.getLeft(), 0)), Double.parseDouble(JavaUtil.formatNumber(recognition.getRight(), 0))))) + ", Y: " + Math.round(JavaUtil.averageOfList(JavaUtil.createListWith(Double.parseDouble(JavaUtil.formatNumber(recognition.getTop(), 0)), Double.parseDouble(JavaUtil.formatNumber(recognition.getBottom(), 0))))));
        telemetry.addData("OWBack Current POS", OWBack.getCurrentPosition());
        telemetry.addData("OWBack Target POS", OWBack.getTargetPosition());
        telemetry.addData("OWLeft Current POS", OWLeft.getCurrentPosition());
        telemetry.addData("OWLeft target POS", OWLeft.getTargetPosition());
        telemetry.addData("OWRight Current POS", OWRight.getCurrentPosition());
        telemetry.addData("OWRight target POS", OWRight.getTargetPosition());
        // Deactivate TFOD.
        tfod.deactivate();
        telemetry.update();
    }
}

