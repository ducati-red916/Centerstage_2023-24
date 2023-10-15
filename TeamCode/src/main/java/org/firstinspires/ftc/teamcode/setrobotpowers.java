package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

public class setrobotpowers {

    drivetrainclaw robot;

    private DcMotor front_left_port_0;
    private DcMotor back_right_port_1;
    private DcMotor front_right_port_2;
    private DcMotor back_left_port_3;
    private Servo claw;
    private DcMotor slide1;

    public void init() {

        robot = new drivetrainclaw();

        front_right_port_2.setDirection(DcMotorSimple.Direction.REVERSE);
        back_right_port_1.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    public void setdrivepower() {
        front_left_port_0.setPower(robot.getFrontLeftpower());
        back_right_port_1.setPower(robot.getBackRightpower());
        front_right_port_2.setPower(robot.getFrontRightpower());
        back_left_port_3.setPower(robot.getBackLeftpower());
    }

    public void setmotormode() {
        front_left_port_0.setMode(robot.getFrontLeftmode());
        back_right_port_1.setMode(robot.getBackRightmode());
        front_right_port_2.setMode(robot.getFrontRightmode());
        back_left_port_3.setMode(robot.getBackLeftmode());
        slide1.setMode(robot.getSlidemode());
    }

    public void setslide() {
    slide1.setTargetPosition(robot.getSlideTPos());
    slide1.setPower(robot.getSlidePower());
    ((DcMotorEx) slide1).setVelocity(robot.getSlideSpeed());
    }

    public void setclaw() {
        claw.setPosition(robot.getClawTargetPos());
        claw.scaleRange(robot.getClawRangemin(), robot.getClawRangemax());
    }

}
