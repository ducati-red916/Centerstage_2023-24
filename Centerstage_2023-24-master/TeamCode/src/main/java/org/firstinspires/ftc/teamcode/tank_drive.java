package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name = "tank_drive")
public class tank_drive extends LinearOpMode {

    private DcMotor back_left_port_3;
    private DcMotor back_right_port_1;
    private DcMotor front_right_port_2;
    private DcMotor front_left_port_0;

    @Override
    public void runOpMode() {
        back_left_port_3 = hardwareMap.get(DcMotor.class, "back_left_port_3");
        back_right_port_1 = hardwareMap.get(DcMotor.class, "back_right_port_1");
        front_right_port_2 = hardwareMap.get(DcMotor.class, "front_right_port_2");
        front_left_port_0 = hardwareMap.get(DcMotor.class, "front_left_port_0");

        waitForStart();
        if (opModeIsActive()) {
            while (opModeIsActive()) {
                back_right_port_1.setPower(gamepad1.right_stick_y);
                back_left_port_3.setPower(gamepad1.left_stick_y);
            }
        }
    }




}