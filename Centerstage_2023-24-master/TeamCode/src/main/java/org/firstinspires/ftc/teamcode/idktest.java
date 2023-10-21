package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;

import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

public class idktest {

    //constants
    final private double WheelRadius = 1.375 / 2 * 2.54; //in Inches converted to CM
    final private double CMperTick = 2 * Math.PI * WheelRadius / 8192;
    final private double NearRadius = 1/12;

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
    public void Run(double Back, double TPosX, double TPosY, double speed, double margin) {

        PosX = CMperTick * -Back;

        // PosTheta = angles.

        DeltaX = TPosX - PosX;


        //       atpo=false;
/*
        while(Math.abs(PosTheta) != 0+5){
            BackLeft = -0.1;
            BackRight = 0.1;
            FrontLeft = -0.1;
            FrontRight = 0.1;
        }
*/

        if (Math.abs(DeltaX) > margin) {
            //           atpo=false;

                BackLeft = -speed * -(Math.abs(DeltaX) / DeltaX);
                BackRight = -speed * -(Math.abs(DeltaX) / DeltaX);
                FrontLeft = speed * -(Math.abs(DeltaX) / DeltaX);
                FrontRight = speed * -(Math.abs(DeltaX) / DeltaX);
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




