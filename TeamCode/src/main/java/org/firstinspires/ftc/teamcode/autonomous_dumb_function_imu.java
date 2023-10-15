package org.firstinspires.ftc.teamcode;

public class autonomous_dumb_function_imu {

    //constants
    final private double WheelRadius = 1.375 / 2 * 2.54; //in Inches converted to CM
    final private double InchesperTick = 0.0005454545454; //2 * Math.PI * WheelRadius / 8192;
    //final private double NearRadius = 1.5;
    final double Thetanearradius = 5;
    final double thetaspeed = 0.025;
    final double XYnearradius = 2;
    final double XYspeed = 0.25;



    //variables
    public double Xpower;
    public double Ypower;
    public double Thetapower;
    private boolean atpo;
    double DeltaX;
    double Yoff0;
    double Xoff0;
    double DeltaY;
    double DeltaTheta;
    public double PosX;
    public double PosY;
    boolean onY;
    private double PosTheta; // in radians
//    final private double LeftRightDist=7.625*2.54; //Distance between the left and right wheels Inches converted to CM

    public void init (){
        //IMU stuffs

    }

    //main program flow
    public void Run(double Left, double Back, float heading, double TPosX, double TPosY, double speed, double margin, double NearRadius) {


        PosX = InchesperTick * Back;
        PosY = InchesperTick * -Left;

        thetato0(speed/4);


        DeltaX = TPosX - PosX;
        DeltaY = TPosY - PosY;
        Yoff0 = PosY;
        Xoff0 = PosX;
        DeltaTheta = heading;
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
            atpo=false;
            Yto0();
            if (Math.abs(DeltaX) < Math.abs(NearRadius)) {
                Xpower = speed * Math.sin(DeltaX * Math.PI * 1/2 * 1/NearRadius); // start slowing when inside 24 inches
            } else {
                Xpower = speed * (Math.abs(DeltaX) / DeltaX);

            }
        } else {
            Xpower=0;
            Ypower=0;
            Thetapower=0;
            if (Math.abs(DeltaY) > margin) {
                atpo=false;
                Xto0();
                if (DeltaY < Math.abs(NearRadius)) {
                    Ypower = -speed * Math.sin(DeltaY * Math.PI * 1/2 * 1/NearRadius); // start slowing when inside 24 inches
                } else {
                    Ypower = -speed * (Math.abs(DeltaY) / DeltaY);
                }
            } else {
                Xpower = 0;
                Ypower = 0;
                Thetapower = 0;
                atpo=true;
            }

        }
    }
    private void thetato0(double speed) {
        if (Math.abs(DeltaTheta) > 0.0001) {
            if (DeltaTheta < Thetanearradius) {
                Thetapower = -thetaspeed * -Math.sin(DeltaTheta * Math.PI * 1 / 2 * 1 / Thetanearradius); // start slowing when inside 24 inches

            } else {
                Thetapower = -thetaspeed * -(Math.abs(DeltaTheta) / DeltaTheta);
            }
        }
    }
    private void Yto0() {
        if (Math.abs(Yoff0) > 0.01) {
            if (Yoff0 < XYnearradius) {
                Ypower = -XYspeed * -Math.sin(Yoff0) * Math.PI * 1 / 2 * 1 / XYnearradius; // start slowing when inside 24 inches

            } else {
                Ypower = -XYspeed * -(Math.abs(Yoff0) / Yoff0);
            }
        }
    }
    private void Xto0() {
        if (Math.abs(DeltaX) > 0.01) {
            if (DeltaX < XYnearradius) {
                Xpower = -XYspeed * -Math.sin(DeltaX) * Math.PI * 1 / 2 * 1 / XYnearradius; // start slowing when inside 24 inches

            } else {
                Xpower = -XYspeed * -(Math.abs(DeltaX) / DeltaX);
            }
        }
    }

    public boolean atpos (){
        if (atpo) {
            return true;
        } else {
            return false;
        }
    }
}



