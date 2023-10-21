package org.firstinspires.ftc.teamcode;


import static com.sun.tools.doclint.Entity.or;

public class cOdometry_waypoints {

    public double BackLeft;
    public double BackRight;
    public double FrontLeft;
    public double FrontRight;
    private double DeltaX;
    private double DeltaY;
    private double DeltaTheta;
    final private double NearRadius = 24;
    private int finxy;

    public void init() {
        finxy = 0;
    }

    public void Run(double PosX, double PosY, double PosTheta, double TPosX, double TPosY, double TPosTheta, double speed, double margin) {
        DeltaX = TPosX - PosX;
        DeltaY = TPosY - PosY;
        DeltaTheta = TPosTheta - PosTheta;
        if (PosTheta > speed * 20 && finxy==0) {
            BackLeft = -speed;
            BackRight = speed;
            FrontLeft = -speed;
            FrontRight = speed;
        } else {
            if (Math.abs(DeltaX) > margin) {
                if (DeltaX > Math.abs(24)) {
                    BackLeft = -speed * -Math.sin(DeltaX * Math.PI * 1 / 2 * NearRadius); // start slowing when inside 24 inches
                    BackRight = -speed * -Math.sin(DeltaX * Math.PI * 1 / 2 * NearRadius); // start slowing when inside 24 inches
                    FrontLeft = speed * -Math.sin(DeltaX * Math.PI * 1 / 2 * NearRadius); // start slowing when inside 24 inches
                    FrontRight = speed * -Math.sin(DeltaX * Math.PI * 1 / 2 * NearRadius); // start slowing when inside 24 inches
                } else {
                    BackLeft = -speed * -(Math.abs(DeltaX) / DeltaX);
                    BackRight = -speed * -(Math.abs(DeltaX) / DeltaX);
                    FrontLeft = speed * -(Math.abs(DeltaX) / DeltaX);
                    FrontRight = speed * -(Math.abs(DeltaX) / DeltaX);
                }
            } else {
                if (Math.abs(DeltaY) > margin) {
                    if (DeltaY > Math.abs(24)) {
                        BackLeft = speed * -Math.sin(DeltaY * Math.PI * 1 / 2 * NearRadius); // start slowing when inside 24 inches
                        BackRight = speed * -Math.sin(DeltaY * Math.PI * 1 / 2 * NearRadius); // start slowing when inside 24 inches
                        FrontLeft = speed * -Math.sin(DeltaY * Math.PI * 1 / 2 * NearRadius); // start slowing when inside 24 inches
                        FrontRight = speed * -Math.sin(DeltaY * Math.PI * 1 / 2 * NearRadius); // start slowing when inside 24 inches
                    } else {
                        BackLeft = speed * -(Math.abs(DeltaY) / DeltaY);
                        BackRight = speed * -(Math.abs(DeltaY) / DeltaY);
                        FrontLeft = speed * -(Math.abs(DeltaY) / DeltaY);
                        FrontRight = speed * -(Math.abs(DeltaY) / DeltaY);
                    }
                } else {
                    if (Math.abs(DeltaTheta) > margin) {
                        finxy=1;
                        if (DeltaTheta > Math.abs(24)) {
                            BackLeft = -speed * -Math.sin(DeltaTheta * Math.PI * 1 / 2 * NearRadius); // start slowing when inside 24 inches
                            BackRight = speed * -Math.sin(DeltaTheta * Math.PI * 1 / 2 * NearRadius); // start slowing when inside 24 inches
                            FrontLeft = -speed * -Math.sin(DeltaTheta * Math.PI * 1 / 2 * NearRadius); // start slowing when inside 24 inches
                            FrontRight = speed * -Math.sin(DeltaTheta * Math.PI * 1 / 2 * NearRadius); // start slowing when inside 24 inches
                        } else {
                            BackLeft = -speed * -(Math.abs(DeltaTheta) / DeltaTheta);
                            BackRight = speed * -(Math.abs(DeltaTheta) / DeltaTheta);
                            FrontLeft = -speed * -(Math.abs(DeltaTheta) / DeltaTheta);
                            FrontRight = speed * -(Math.abs(DeltaTheta) / DeltaTheta);
                        }
                    } else {
                        BackLeft = 0;
                        BackRight = 0;
                        FrontLeft = 0;
                        FrontRight = 0;
                    }
                }
            }
        }
    }
}


// double Vertical;
//        double Horizontal;
//        double Pivot;
//
//        if (gamepad1.a) {
//            drive_mode = 1;
//        }
//        if (gamepad1.b) {
//            drive_mode = 0.25;
//        }
//        Vertical = drive_mode * -gamepad1.right_stick_y;
//        Horizontal = drive_mode * gamepad1.right_stick_x;
//        Pivot = drive_mode * gamepad1.left_stick_x;
//        front_right_port_2.setPower(-Pivot + (Vertical - Horizontal));
//        back_right_port_1.setPower(-Pivot + Vertical + Horizontal);
//        front_left_port_0.setPower(Pivot + Vertical + Horizontal);
//        back_left_port_3.setPower(Pivot + (Vertical - Horizontal));