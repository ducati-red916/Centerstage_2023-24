package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;

import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

public class RunToPosition {

    //constants
    final private double WheelRadius = 1.89 / 2 * 2.54; //in Inches converted to CM
    final private double CMperTick = 2 * Math.PI * WheelRadius / 2000;
    final private double NearRadius = 6;

    //IMU stuffs
    //   BNO055IMU imu;
    //   Orientation angles = new Orientation();

    //variables
    public double BackLeft;
    public double BackRight;
    public double FrontLeft;
    public double FrontRight;
    //   private boolean atpo;
    double DeltaX;
    double DeltaY;
    //    private double DeltaTheta;
    double PosX;
    double PosY;
    //   private double PosTheta; // in radians
//    final private double LeftRightDist=7.625*2.54; //Distance between the left and right wheels Inches converted to CM


    //main program flow
    public void Run(double Left, double Back, double TPosX, double TPosY, double speed, double margin) {

        PosX = CMperTick * -Back;
        PosY = CMperTick * Left;
        // PosTheta = angles.

        DeltaX = TPosX - PosX;
        DeltaY = TPosY - PosY;

        //       atpo=false;
/*
        while(Math.abs(PosTheta) != 0+5){
            BackLeft = -0.1;
            BackRight = 0.1;
            FrontLeft = -0.1;
            FrontRight = 0.1;
        }
*/
        // below is desmos function
// f\left(x\right)=\left\{\operatorname{abs}\left(x\right)<6:0.25\cdot-\sin\left(x\cdot\pi\cdot\frac{1}{2}\cdot\frac{1}{6}\right),\operatorname{abs}\left(x\right)>6:0.25\cdot-\left(\frac{\operatorname{abs}\left(x\right)}{x}\right)\right\}
        if (Math.abs(DeltaX) > margin) {
            //           atpo=false;
            if (Math.abs(DeltaX) < Math.abs(NearRadius)) {
                BackLeft = -speed * -Math.sin(DeltaX * Math.PI * 1/2 * 1/NearRadius); // start slowing when inside 24 inches
                BackRight = -speed * -Math.sin(DeltaX * Math.PI * 1/2 * 1/NearRadius); // start slowing when inside 24 inches
                FrontLeft = speed * -Math.sin(DeltaX * Math.PI * 1/2 * 1/NearRadius); // start slowing when inside 24 inches
                FrontRight = speed * -Math.sin(DeltaX * Math.PI * 1/2 * 1/NearRadius); // start slowing when inside 24 inches
            } else {
                BackLeft = -speed * -(Math.abs(DeltaX) / DeltaX);
                BackRight = -speed * -(Math.abs(DeltaX) / DeltaX);
                FrontLeft = speed * -(Math.abs(DeltaX) / DeltaX);
                FrontRight = speed * -(Math.abs(DeltaX) / DeltaX);
            }
        } else {
            if (Math.abs(DeltaY) > margin) {
                if (DeltaY < Math.abs(NearRadius)) {
                    BackLeft = -speed * -Math.sin(DeltaY * Math.PI * 1/2 * 1/NearRadius); // start slowing when inside 24 inches
                    BackRight = speed * -Math.sin(DeltaY * Math.PI * 1/2 * 1/NearRadius); // start slowing when inside 24 inches
                    FrontLeft = -speed * -Math.sin(DeltaY * Math.PI * 1/2 * 1/NearRadius); // start slowing when inside 24 inches
                    FrontRight = speed * -Math.sin(DeltaY * Math.PI * 1/2 * 1/NearRadius); // start slowing when inside 24 inches
                } else {
                    BackLeft = -speed * -(Math.abs(DeltaY) / DeltaY);
                    BackRight = speed * -(Math.abs(DeltaY) / DeltaY);
                    FrontLeft = -speed * -(Math.abs(DeltaY) / DeltaY);
                    FrontRight = speed * -(Math.abs(DeltaY) / DeltaY);
                }
            } else {
                BackLeft = 0;
                BackRight = 0;
                FrontLeft = 0;
                FrontRight = 0;
//                atpo=true;
            }

        }
    }

   /* public boolean atpos (){
        if (atpo) {
            return true;
        } else {
            return false;
        }
    } */
}



