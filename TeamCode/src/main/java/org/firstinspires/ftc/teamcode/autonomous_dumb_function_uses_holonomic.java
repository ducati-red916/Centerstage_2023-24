package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;

import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

public class autonomous_dumb_function_uses_holonomic {

    //constants
    final private double WheelRadius = 1.375 / 2 * 2.54; //in Inches converted to CM
    final private double InchesperTick = 0.0005454545454; //2 * Math.PI * WheelRadius / 8192;
    final private double NearRadius = 1.5;

    //IMU stuffs
    //   BNO055IMU imu;
    //   Orientation angles = new Orientation();

    //variables
    public double Xpower;
    public double Ypower;
    public double Thetapower;
    //   private boolean atpo;
    double DeltaX;
    double Yoff0;
    double DeltaY;
    //    private double DeltaTheta;
    double PosX;
    double PosY;
    boolean onY;
    //   private double PosTheta; // in radians
//    final private double LeftRightDist=7.625*2.54; //Distance between the left and right wheels Inches converted to CM


    //main program flow
    public void Run(double Left, double Back, double TPosX, double TPosY, double speed, double margin) {


        PosX = InchesperTick * -Back;
        PosY = InchesperTick * Left;
        // PosTheta = angles.

        DeltaX = TPosX - PosX;
        DeltaY = TPosY - PosY;
        Yoff0 = 0 - PosY;

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
            Xpower=0;
            Ypower=0;
            Thetapower=0;
            //           atpo=false;
            if (Math.abs(DeltaX) < Math.abs(NearRadius)) {
                Xpower = speed * Math.sin(DeltaX * Math.PI * 1/2 * 1/NearRadius); // start slowing when inside 24 inches
                /*
                if (Math.abs(Yoff0)>0.1){
                    if (Yoff0 < 2) {
                        Thetapower = speed * -Math.sin(Yoff0 * Math.PI * 1/2 * 1/2); // start slowing when inside 24 inches
                    } else {
                        Thetapower = -speed * -(Math.abs(Yoff0) / Yoff0);
                    }
                }

                 */
            } else {
                Xpower = speed * (Math.abs(DeltaX) / DeltaX);
                /*
                if (Math.abs(Yoff0)>0.1){
                    if (Yoff0 < 2) {
                        Thetapower = speed * -Math.sin(Yoff0 * Math.PI * 1/2 * 1/2); // start slowing when inside 24 inches
                    } else {
                        Thetapower = -speed * -(Math.abs(Yoff0) / Yoff0);
                    }
                }

                 */
            }
        } else {
            Xpower=0;
            Ypower=0;
            Thetapower=0;
            if (Math.abs(DeltaY) > margin) {

                if (DeltaY < Math.abs(NearRadius)) {
                    Ypower = speed * Math.sin(DeltaY * Math.PI * 1/2 * 1/NearRadius); // start slowing when inside 24 inches
/*                    if (Math.abs(DeltaX)>0.1){
                        if (DeltaX < 2) {
                            Thetapower = speed * -Math.sin(DeltaX * Math.PI * 1/2 * 1/2); // start slowing when inside 24 inches
                        } else {
                            Thetapower = -speed * -(Math.abs(DeltaX) / DeltaX);
                        }
                    }
 */
                } else {
                    Ypower = speed * -(Math.abs(DeltaY) / DeltaY);
                    /*
                    if (Math.abs(DeltaX)>0.1){
                        if (DeltaX < 2) {
                            Thetapower = speed * -Math.sin(DeltaX * Math.PI * 1/2 * 1/2); // start slowing when inside 24 inches
                        } else {
                            Thetapower = -speed * -(Math.abs(DeltaX) / DeltaX);
                        }
                    }
                     */
                }
            } else {
                Xpower = 0;
                Ypower = 0;
                Thetapower = 0;
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



