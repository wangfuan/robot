package com.example.fuan.ioiotest;

import ioio.lib.api.DigitalInput;
import ioio.lib.api.DigitalOutput;
import ioio.lib.api.IOIO;
import ioio.lib.api.PwmOutput;
import ioio.lib.api.exception.ConnectionLostException;
import ioio.lib.util.BaseIOIOLooper;
import ioio.lib.util.IOIOLooper;
import ioio.lib.util.android.IOIOActivity;


public class BaseActivity extends IOIOActivity{
    private PwmOutput leftMotorPwm1,leftMotorPwm2,rightMotorPwm1,rightMotorPwm2;
    private float leftMoterPwmDuty1,leftMoterPwmDuty2,rightMotorPwmDuty1,rightMotorPwmDuty2;

    class Looper extends BaseIOIOLooper{
        /*
        * 函数名称：initMotor
        * 函数功能：电机引脚GPIO口设置
        */
        public void initMotor() throws ConnectionLostException,InterruptedException{
            leftMotorPwm1 = ioio_.openPwmOutput(11, 100);
            leftMotorPwm2 = ioio_.openPwmOutput(12, 100);
            rightMotorPwm1 = ioio_.openPwmOutput(13, 100);
            rightMotorPwm2 = ioio_.openPwmOutput(14, 100);
        }

        @Override
        public void setup() throws ConnectionLostException,InterruptedException{
            initMotor();
        }
        /*
        * 函数名称：setMotorDuty
        * 功能:    电机pwm波和数字电平参数设置
        */
        public void setMotorDuty()  throws ConnectionLostException,InterruptedException{
            leftMotorPwm1.setDutyCycle(leftMoterPwmDuty1);
            leftMotorPwm2.setDutyCycle(leftMoterPwmDuty2);
            rightMotorPwm1.setDutyCycle(rightMotorPwmDuty1);
            rightMotorPwm2.setDutyCycle(rightMotorPwmDuty2);
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
    * 功能    ：电机正转，可以调节速度，但是超过提前标定的速度阈值则不能转动
    */
    public void moveForward(int speed){
        if( (speed >=0) && (speed <= 100)){
            leftMoterPwmDuty1 = speed/100.0f;
            leftMoterPwmDuty2 = 0;
            rightMotorPwmDuty1= speed/100.0f;
            rightMotorPwmDuty2 = 0;
        }
        else if( (speed < 0) && (speed > 100)){
            leftMoterPwmDuty1 = 0;
            leftMoterPwmDuty2 = 0;
            rightMotorPwmDuty1 = 0;
            rightMotorPwmDuty2 = 0;
        }
    }
    /*
    * 函数名称：moveBackward
    * 功能    ：电机反转，可以调节速度，但是超过提前标定的速度阈值则不能转动
    */
    public void moveBackward(int speed){
        if( (speed >=0) && (speed <= 100)){
            leftMoterPwmDuty1 = 0;
            leftMoterPwmDuty2 = speed/100.0f;
            rightMotorPwmDuty1 = 0;
            rightMotorPwmDuty2 = speed/100.0f;
        }
        else if( (speed < 0) && (speed > 100)){
            leftMoterPwmDuty1 = 0;
            leftMoterPwmDuty2 = 0;
            rightMotorPwmDuty1 = 0;
            rightMotorPwmDuty2 = 0;
        }
    }
    /*
    *函数名称：turnCircle
    *功能：原地转圈,可以实现正反转及转动速度的调节,但是速度超过提前设置的速度阈值则不能移动
    */
    public void turnCircle(int speed,boolean direction) {
        if (direction){
            if( (speed >= 0) && (speed <= 100)){
                leftMoterPwmDuty1 = speed/100.0f;
                leftMoterPwmDuty2 = 0;
                rightMotorPwmDuty1 = 0;
                rightMotorPwmDuty2 = speed/100.0f;
            }
            else if( (speed < 0) && (speed > 100)){
                leftMoterPwmDuty1 = 0;
                leftMoterPwmDuty2 = 0;
                rightMotorPwmDuty1 = 0;
                rightMotorPwmDuty2 = 0;
            }
        }
        else {
            if( (speed >= 0) && (speed <= 100)){
                leftMoterPwmDuty1 = 0;
                leftMoterPwmDuty2 = speed/100.0f;
                rightMotorPwmDuty1 = speed/100.0f;
                rightMotorPwmDuty2 = 0;
            }
            else if( (speed <= 0) && (speed >= 100)){
                leftMoterPwmDuty1 = 0;
                leftMoterPwmDuty2 = 0;
                rightMotorPwmDuty1 = 0;
                rightMotorPwmDuty2 = 0;
            }

        }
    }
    /*
    *函数名称：moveStop
    *函数功能：电机停止转动
    */
    public void moveStop(){
        leftMoterPwmDuty1 = 0;
        leftMoterPwmDuty2 = 0;
        rightMotorPwmDuty1 = 0;
        rightMotorPwmDuty2 = 0;
    }

    /*
    * 函数名称：motorLeft
    * 函数功能：左电机控制，可实现速度和旋转方向的调节，但是速度超过提前设置的速度阈值则不能移动
     */
    public void motorLeft(int speed,boolean direction){
        if(direction){
            if ((speed >= 0) && (speed <= 100)){
                leftMoterPwmDuty1 = speed/100.0f;
                leftMoterPwmDuty2 = 0;
            }
            else if ((speed < 0) && (speed > 100)) {
                leftMoterPwmDuty1 = 0;
                leftMoterPwmDuty2 = 0;
            }
        }
        else {
            if ((speed >= 0) && (speed <= 100)){
                leftMoterPwmDuty1 = 0;
                leftMoterPwmDuty2 = speed/100.0f;
            }
            else if ((speed < 0) && (speed > 100)) {
                leftMoterPwmDuty1 = 0;
                leftMoterPwmDuty2 = 0;
            }
        }
    }
    /*
    * 函数名称：motorRight
    * 函数功能：右电机控制，可实现速度和旋转方向的调节，但是速度超过提前设置的速度阈值则不能移动
     */
    public void motorRight(int speed,boolean direction){
        if(direction){
            if ((speed >= 0) && (speed <= 100)){
                rightMotorPwmDuty1 = speed/100.0f;
                rightMotorPwmDuty2 = 0;
            }
            else if ((speed < 0) && (speed > 100)) {
                rightMotorPwmDuty1 = 0;
                rightMotorPwmDuty2 = 0;
            }
        }
        else {
            if ((speed >= 0) && (speed <= 100)){
                rightMotorPwmDuty1 = 0;
                rightMotorPwmDuty2 = speed/100.0f;
            }
            else if ((speed < 0) && (speed > 100)) {
                rightMotorPwmDuty1 = 0;
                rightMotorPwmDuty2 = 0;
            }
        }
    }
    protected IOIOLooper createIOIOLooper(){
        return new Looper();
    }
}
