package com.example.fuan.ioiotest;


import ioio.lib.api.DigitalInput;
import ioio.lib.api.DigitalOutput;
import ioio.lib.api.PwmOutput;
import ioio.lib.api.DigitalOutput.Spec.Mode;
import ioio.lib.api.exception.ConnectionLostException;
import ioio.lib.util.BaseIOIOLooper;
import ioio.lib.util.IOIOLooper;
import ioio.lib.util.android.IOIOActivity;


public class BaseActivity extends IOIOActivity{
    private PwmOutput leftMotorPwm1,leftMotorPwm2,rightMotorPwm1,rightMotorPwm2;
    private float leftMoterPwmDuty1,leftMoterPwmDuty2,rightMotorPwmDuty1,rightMotorPwmDuty2;
    /*
    detectEdge为红外模块输入变量，用于边缘检测
    detectEdgeEnable为边缘检测开关，true时为开启量，false时为关闭量
     */
    private DigitalInput detectEdge;
    private Boolean detectEdgeEnable=false;
    /*
    touchSence为触摸传感器输入变量
    touchSencePlace全局变量记录发什么触摸的位置
     */
    private DigitalInput touchSence1;
    private DigitalInput touchSence2;
    private DigitalInput touchSence3;
    private DigitalInput touchSence4;
    private DigitalInput touchSence5;
    private int touchSencePlace;
    /*
    faceLed为Led输出变量
     */
    private DigitalOutput faceLedLeftEye;
    private DigitalOutput faceLedRightEye;
    private DigitalOutput faceLedMouth;
    private Boolean faceLedLeftEyeFlag;
    private Boolean faceLedRightEyeFlag;
    private Boolean faceLedMouthFlag;
    /*
    servoPwm为舵机Pwm波输出变量
     */
    private PwmOutput servoPwm1,servoPwm2;
    private float servoPwmDuty1,servoPwmDuty2;

    class Looper extends BaseIOIOLooper{
        /*
        * 函数名称：initMotor
        * 功能：电机引脚GPIO口设置
        */
        public void initMotor() throws ConnectionLostException,InterruptedException{
            leftMotorPwm1 = ioio_.openPwmOutput(11, 100);
            leftMotorPwm2 = ioio_.openPwmOutput(12, 100);
            rightMotorPwm1 = ioio_.openPwmOutput(13, 100);
            rightMotorPwm2 = ioio_.openPwmOutput(14, 100);
        }
        /*
        * 函数名称：initDetectEdge()
        * 功能：红外模块引脚GPIO口设置
        */
        public void initDetectEdge() throws ConnectionLostException,InterruptedException{
            detectEdge = ioio_.openDigitalInput(29);

        }
        /*
        * 函数名称：initTouchSence()
        * 功能：触摸模块引脚GPIO口设置
        */
        public void initTouchSence() throws ConnectionLostException,InstantiationError{
            touchSence1 = ioio_.openDigitalInput(22);
            touchSence2 = ioio_.openDigitalInput(23);
            touchSence3 = ioio_.openDigitalInput(24);
            touchSence4 = ioio_.openDigitalInput(25);
            touchSence5 = ioio_.openDigitalInput(26);
        }
        /*
        * 函数名称：initLed()
        * 功能：Led模块引脚GPIO口设置
        */
        public void initLed() throws ConnectionLostException,InterruptedException{
            faceLedLeftEye = ioio_.openDigitalOutput(1, false);
            faceLedRightEye = ioio_.openDigitalOutput(2, false);
            faceLedMouth = ioio_.openDigitalOutput(3, false);
        }
        /*
        * 函数名称：initServo()
        * 功能:舵机模块引脚GPIO口设置
         */
        public void initServo() throws ConnectionLostException,InterruptedException{
            servoPwm1 = ioio_.openPwmOutput(6, 50);
            servoPwm2 = ioio_.openPwmOutput(10, 50);
        }

        @Override
        public void setup() throws ConnectionLostException,InterruptedException{
            initMotor();
            initDetectEdge();
            initTouchSence();
            initLed();
            initServo();
        }
        /*
        * 函数名称：setMotorDuty
        * 功能：电机pwm波参数设置
        */
        public void setMotorDuty()  throws ConnectionLostException,InterruptedException{
            leftMotorPwm1.setDutyCycle(leftMoterPwmDuty1);
            leftMotorPwm2.setDutyCycle(leftMoterPwmDuty2);
            rightMotorPwm1.setDutyCycle(rightMotorPwmDuty1);
            rightMotorPwm2.setDutyCycle(rightMotorPwmDuty2);
        }
        /*
        * 函数名称：setServoDuty()
        * 功能：舵机pwm波参数设置
        */
        public void setServoDuty() throws ConnectionLostException, InterruptedException {
            servoPwm1.setDutyCycle(servoPwmDuty1);
            servoPwm2.setDutyCycle(servoPwmDuty2);
        }
        /*
        * 函数名称：judgeTouchPlace()
        * 功能：依次读取触摸传感器的各GPIO口电平，然后在触摸事件发生时判断发生触摸的位置，用touchSencePlace来记录
        */
        public void judgeTouchPlace() throws ConnectionLostException,InterruptedException{
            if (touchSence1.read())
                touchSencePlace=1;
            if (touchSence2.read())
                touchSencePlace=2;
            if (touchSence3.read())
                touchSencePlace=3;
            if (touchSence4.read())
                touchSencePlace=4;
            if (touchSence5.read())
                touchSencePlace=5;
        }
        /*
        函数名称：setDetectEdge()
        功能：判断机器人的运动方向上是否存在跌落的危险
         */
        public void setDetectEdge() throws ConnectionLostException,InterruptedException{
            if(detectEdgeEnable){
                if (detectEdge.read()){
                    moveStop();//这里设定检测到边缘的位置时，电机停止运行
                }
            }
        }
        /*
        * 函数名称：setFaceLedPlace(boolean lefteye,boolean rigtheye,boolean mouth)
        * 功能：设置Led灯的位置并且控制灯的亮灭
        */
        public void setFaceLedPlace() throws ConnectionLostException,InterruptedException{
            faceLedLeftEye.write(faceLedLeftEyeFlag);
            faceLedRightEye.write(faceLedRightEyeFlag);
            faceLedMouth.write(faceLedMouthFlag);
        }
        @Override
        public void loop() throws ConnectionLostException,InterruptedException{
            setMotorDuty();
            judgeTouchPlace();
            setDetectEdge();
            setServoDuty();
        }

        @Override
        public void disconnected(){

        }
    }
    /*
    * 函数名称：moveForward
    * 功能：电机正转，可以调节速度，但是超过提前标定的速度阈值则不能转动
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
    *功能：电机停止转动
    */
    public void moveStop(){
        leftMoterPwmDuty1 = 0;
        leftMoterPwmDuty2 = 0;
        rightMotorPwmDuty1 = 0;
        rightMotorPwmDuty2 = 0;
    }

    /*
    * 函数名称：motorLeft
    * 功能：左电机控制，可实现速度和旋转方向的调节，但是速度超过提前设置的速度阈值则不能移动
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
    * 功能：右电机控制，可实现速度和旋转方向的调节，但是速度超过提前设置的速度阈值则不能移动
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
    public void openLed(int ledPlace) {
        if ((ledPlace >= 0 )&&(ledPlace < 8)){
            switch (ledPlace){
                case 0:{
                    faceLedLeftEyeFlag=false;
                    faceLedRightEyeFlag=false;
                    faceLedMouthFlag=false;
                    break;
                }
                case 1:{
                    faceLedLeftEyeFlag=true;
                    faceLedRightEyeFlag=false;
                    faceLedMouthFlag=false;
                    break;
                }
                case 2:{
                    faceLedLeftEyeFlag=false;
                    faceLedRightEyeFlag=true;
                    faceLedMouthFlag=false;
                    break;
                }
                case 3:{
                    faceLedLeftEyeFlag=true;
                    faceLedRightEyeFlag=true;
                    faceLedMouthFlag=false;
                    break;
                }
                case 4:{
                    faceLedLeftEyeFlag=false;
                    faceLedRightEyeFlag=false;
                    faceLedMouthFlag=true;
                    break;
                }
                case 5:{
                    faceLedLeftEyeFlag=true;
                    faceLedRightEyeFlag=false;
                    faceLedMouthFlag=true;
                    break;
                }
                case 6:{
                    faceLedLeftEyeFlag=false;
                    faceLedRightEyeFlag=true;
                    faceLedMouthFlag=true;
                    break;
                }
                case 7:{
                    faceLedLeftEyeFlag=true;
                    faceLedRightEyeFlag=true;
                    faceLedMouthFlag=true;
                    break;
                }
            }
        }
    }
    /*
    * 函数名称：steeringEngine
    * 功能：可以实现舵机180°转动调节，speed=0时，电机占空比设为2.5，此时对应的转动角度为0，发生舵机的抖动，
    * 所以设为2.56，让电机转动角度初值比0大一点
     */
    public void servoControl(float angle){
        servoPwmDuty1=(2.56f+angle/10)/100;
        servoPwmDuty2=(2.56f+angle/10)/100;
    }
    protected IOIOLooper createIOIOLooper(){
        return new Looper();
    }
}
