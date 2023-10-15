package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;

public class drivetrainobject {

    private double FrontLeftpowerout;
    private double BackRightpowerout;
    private double FrontRightpowerout;
    private double BackLeftpowerout;

    private DcMotor.RunMode FrontLeftmodeout;
    private DcMotor.RunMode BackRightmodeout;
    private DcMotor.RunMode FrontRightmodeout;
    private DcMotor.RunMode BackLeftmodeout;

    public void setFrontLeftpower(double FrontLeft){
        FrontLeftpowerout=FrontLeft;
    }

    public double getFrontLeftpower() {return FrontLeftpowerout;}

    public void setFrontLeftmode(DcMotor.RunMode mode){

        FrontLeftmodeout=mode;
    }

    public DcMotor.RunMode getFrontLeftmode() {return FrontLeftmodeout;}





    public void setBackRightpower(double BackRight){

        BackRightpowerout=BackRight;
    }

    public double getBackRightpower() {return BackRightpowerout;}

    public void setBackRightmode(DcMotor.RunMode mode){

        BackRightmodeout=mode;
    }

    public DcMotor.RunMode getBackRightmode() {return BackRightmodeout;}




    public void setFrontRightmode(DcMotor.RunMode mode){
        FrontRightmodeout=mode;
    }

    public DcMotor.RunMode getFrontRightmode() {return FrontRightmodeout;}

    public void setFrontRightpower(double FrontRight){

        FrontRightpowerout=FrontRight;
    }

    public double getFrontRightpower() {return FrontRightpowerout;}




    public void setBackLeftpower(double BackLeft){

        BackLeftpowerout=BackLeft;
    }

    public double getBackLeftpower() {return BackLeftpowerout;}

    public void setBackLeftmode(DcMotor.RunMode mode){

        BackLeftmodeout=mode;
    }

    public DcMotor.RunMode getBackLeftmode() {return BackLeftmodeout;}
}
