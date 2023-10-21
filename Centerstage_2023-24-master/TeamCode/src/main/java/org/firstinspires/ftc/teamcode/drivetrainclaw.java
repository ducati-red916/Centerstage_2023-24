package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;

public class drivetrainclaw extends drivetrainobject {
    private double clawtargetposout;
    private double clawrangeoutmin;
    private double clawrangeoutmax;

    private double SlidePowerout;
    private DcMotor.RunMode Slidemodeout;
    private int Slidespeedout;
    private int SlideTPosout;


    public void setclawpos(double clawpos){
        clawtargetposout=clawpos;
    }

    public double getClawTargetPos() {return clawtargetposout;}

    public void setclawrange(double clawmin, double clawmax){
        clawrangeoutmin=clawmin;
        clawrangeoutmax=clawmax;
    }

    public double getClawRangemin() {return clawrangeoutmin;}
    public double getClawRangemax() {return clawrangeoutmax;}

    public void setSlidePower(double Slide){
        SlidePowerout=Slide;
    }

    public double getSlidePower() {return SlidePowerout;}

    public void setSlideMode(DcMotor.RunMode mode){
        Slidemodeout=mode;
    }

    public DcMotor.RunMode getSlidemode() {return Slidemodeout;}

    public void setSlidespeed(int speed){
        Slidespeedout=speed;
    }

    public int getSlideSpeed() {return Slidespeedout;}

    public void setSlideTPos(int TPos){
        SlideTPosout=TPos;
    }

    public int getSlideTPos() {return SlideTPosout;}


}
