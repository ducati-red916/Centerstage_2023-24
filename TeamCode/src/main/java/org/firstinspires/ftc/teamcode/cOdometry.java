package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;

import java.lang.Math;

public class cOdometry {

    private int current_Odometry_Left;
    private int current_Odometry_Right;
    private int current_Odometry_Back;
    private int old_Odometry_Left;
    private int old_Odometry_Right;
    private int old_Odometry_Back;

    private double PosX; // in cm
    private double PosY; // in cm
    private double PosTheta; // in radians

    final private double WheelRadius=1.375/2*2.54; //in Inches converted to CM
    final private double LeftRightDist=7.625*2.54; //Distance between the left and right wheels Inches converted to CM
    final private double BackOrigDist=8.25*2.54; //Distance from back odomoetry wheel to robot origin Inches converted to CM

    final private double CMperTick =  2*Math.PI*WheelRadius/8192;

    public void Init (int Left, int Right, int Back) {
        old_Odometry_Left=current_Odometry_Left;
        old_Odometry_Right=current_Odometry_Right;
        old_Odometry_Back=current_Odometry_Back;

        current_Odometry_Left=Left;
        current_Odometry_Right=Right;
        current_Odometry_Back=Back;
        PosX=0;
        PosY=0;
        PosTheta=0;
    }
    public void Run(int Left, int Right, int Back) {
        double DeltaLeft;
        double DeltaRight;
        double DeltaBack;
        double DeltaPosX;
        double DeltaPosY;
        double DeltaPosTheta;

        old_Odometry_Left=current_Odometry_Left;
        old_Odometry_Right=current_Odometry_Right;
        old_Odometry_Back=current_Odometry_Back;

        current_Odometry_Left=Left;
        current_Odometry_Right=Right;
        current_Odometry_Back=Back;

        DeltaLeft=current_Odometry_Left - old_Odometry_Left;
        DeltaRight=current_Odometry_Right - old_Odometry_Right;
        DeltaBack=current_Odometry_Back - old_Odometry_Back;

        DeltaPosTheta=CMperTick*2*(DeltaRight-DeltaLeft)/LeftRightDist;
        DeltaPosX=CMperTick*(DeltaLeft+DeltaRight)/2;
        DeltaPosY=CMperTick*(DeltaBack-DeltaPosTheta*BackOrigDist);

        //double Theta=PosTheta+(DeltaPosTheta/2);
       // PosX+=DeltaPosX*Math.cos(Theta)-DeltaPosY*Math.sin(Theta);
        //PosY+=DeltaPosX*Math.sin(Theta)+DeltaPosY*Math.cos(Theta);
        PosTheta+=DeltaPosTheta;

    }

    public double X() {return PosX*2.54;} //output in inches
    public double Y() {return PosY*2.54;} //output in inches
    public double Theta() {return PosTheta*(180/Math.PI); } //output in degrees
}
