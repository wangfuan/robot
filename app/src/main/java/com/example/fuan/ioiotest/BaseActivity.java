package com.example.fuan.ioiotest;

import ioio.lib.api.PwmOutput;
import ioio.lib.api.exception.ConnectionLostException;
import ioio.lib.util.BaseIOIOLooper;
import ioio.lib.util.IOIOLooper;
import ioio.lib.util.android.IOIOActivity;


public class BaseActivity extends IOIOActivity{
    private PwmOutput leftMotorIO1,leftMotorIO2,rightMotorIO1,rightMotorIO2;
    private float leftMoterIO1Duty,leftMotorIO2Duty,rightMotorIO1Duty,rightMotorIO2Duty;

//    @Override
//    public void onCreate(Bundle savedInstanceState){
//        super.onCreate(savedInstanceState);
//    }


    class Looper extends BaseIOIOLooper{
        public void initMotor() throws ConnectionLostException,InterruptedException{
            leftMotorIO1 = ioio_.openPwmOutput(11, 100);
            leftMotorIO2 = ioio_.openPwmOutput(12, 100);
            rightMotorIO1 = ioio_.openPwmOutput(13, 100);
            rightMotorIO2 = ioio_.openPwmOutput(14, 100);
        }

        @Override
        public void setup() throws ConnectionLostException,InterruptedException{
            initMotor();
        }
        public void setMotorDuty()  throws ConnectionLostException,InterruptedException{
            leftMotorIO1.setDutyCycle(leftMoterIO1Duty);
            leftMotorIO2.setDutyCycle(leftMotorIO2Duty);
            rightMotorIO1.setDutyCycle(rightMotorIO1Duty);
            rightMotorIO2.setDutyCycle(rightMotorIO2Duty);
        }

        @Override
        public void loop() throws ConnectionLostException,InterruptedException{
            setMotorDuty();
        }

        @Override
        public void disconnected(){

        }
    }
    /*
    * 函数名称：moveForward
    * 功能    ：电机正转
    *
    */
    public void moveForward(int speed){
        if( (speed >=0) && (speed <= 100)){
            leftMoterIO1Duty = speed/100.0f;
            leftMotorIO2Duty = 0;
            rightMotorIO1Duty = speed/100.0f;
            rightMotorIO2Duty = 0;
        }
        else if( (speed < 0) && (speed >= -100)){
            leftMoterIO1Duty = 0;
            leftMotorIO2Duty = -speed/100.0f;
            rightMotorIO1Duty = 0;
            rightMotorIO2Duty = -speed/100.0f;
        }
    }
    public void Backward(int speed){
//        pwmChange = speed/100.0f;
//        motorRotate_1=false;
//        motorRotate_2=false;
//        PwmOutput_1.setDutyCycle(pwmChange);
//        MotorRefer_1.write(motorRotate_1);
//        PwmOutput_2.setDutyCycle(pwmChange);
//        MotorRefer_2.write(motorRotate_2);
    }//电机反转
    public void TurnCircle(int speed,boolean direction) throws ConnectionLostException,InterruptedException{
//        pwmChange = speed/100.0f;
//        motorRotate_1 = direction;
//        motorRotate_2 = !direction;
//        PwmOutput_1.setDutyCycle(pwmChange);
//        MotorRefer_1.write(!motorRotate_1);
//        PwmOutput_2.setDutyCycle(pwmChange);
//        MotorRefer_2.write(motorRotate_2);
    }//机器人原地旋转，可以控制旋转速度和旋转方向（顺、逆时针），但是不能控制圆周旋转角度
    @Override
    protected IOIOLooper createIOIOLooper(){
        return new Looper();
    }
}
