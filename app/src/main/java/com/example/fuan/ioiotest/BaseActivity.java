package com.example.fuan.ioiotest;

import android.util.Log;

import ioio.lib.api.DigitalInput;
import ioio.lib.api.DigitalOutput;
import ioio.lib.api.PulseInput;
import ioio.lib.api.PwmOutput;
import ioio.lib.api.exception.ConnectionLostException;
import ioio.lib.util.BaseIOIOLooper;
import ioio.lib.util.IOIOLooper;
import ioio.lib.util.android.IOIOActivity;
import ioio.lib.api.IOIO;

public class BaseActivity extends IOIOActivity{
    /**
     * 左右电机的Pwm波和占空比变量设置
     * Pwm_Pin设置Pwm波输出引脚
     */
    private PwmOutput leftMotorPwm1,leftMotorPwm2,rightMotorPwm1,rightMotorPwm2;
    private float leftMoterPwmDuty1,leftMoterPwmDuty2,rightMotorPwmDuty1,rightMotorPwmDuty2;
    private final static int Pwm_Pin1=11;
    private final static int Pwm_Pin2=12;
    private final static int Pwm_Pin3=13;
    private final static int Pwm_Pin4=14;

    /**
     * detectEdge为边缘检测模块输入变量
     * detectEdgeEnable为边缘检测开关，true时为开启量，false时为关闭量
     * DetectEdge_Pin设置边缘检测模块的引脚
     */
    private DigitalInput detectEdge;
    private Boolean detectEdgeEnable=false;
    private final static int DetectEdge_Pin=29;

    /**
     * touchSence为触摸传感器输入变量
     * touchSencePlace全局变量记录发什么触摸的位置
     * TouchSence_Pin设置触摸检测模块的引脚
     */
    private DigitalInput touchSence1;
    private DigitalInput touchSence2;
    private DigitalInput touchSence3;
    private DigitalInput touchSence4;
    private DigitalInput touchSence5;
    private int touchSencePlace;
    private final static int TouchSence_Pin1=22;
    private final static int TouchSence_Pin2=23;
    private final static int TouchSence_Pin3=24;
    private final static int TouchSence_Pin4=25;
    private final static int TouchSence_Pin5=26;

    /**
     * faceLed为头部Led输出变量
     * faceLedFlag为Led使能标志
     * FaceLed_Pin设置头部Led输出引脚
     */
    private DigitalOutput faceLedLeftEye;
    private DigitalOutput faceLedRightEye;
    private DigitalOutput faceLedMouth;
    private Boolean faceLedLeftEyeFlag;
    private Boolean faceLedRightEyeFlag;
    private Boolean faceLedMouthFlag;
    private final static int FaceLed_Pin1=1;
    private final static int FaceLed_Pin2=2;
    private final static int FaceLed_Pin3=3;

    /**
     * servoPwm为舵机Pwm波输出变量
     * servoPwmDuty为舵机Pwm波占空比变量
     * Servo_Pin设置舵机Pwm波输出引脚
     */
    private PwmOutput servoPwm1,servoPwm2;
    private float servoPwmDuty1,servoPwmDuty2;
    private final static int Servo_Pin1=5;
    private final static int Servo_Pin2=10;

    /**
     * ultrasonicSensorTrigger为超生波探测器触发数字输出信号变量
     * ultrasonicSensorEcho为超声波探测器回声检测持续时间输出口
     * ultrasonicSensor_Pin设置超声波探测器的连接引脚
     */
    private DigitalOutput ultrasonicSensorTrigger;
    private PulseInput ultrasonicSensorEcho;
    private final static int ultrasonicSensorTrigger_Pin=7;
    private final static int ultrasonicSensorEcho_Pin=6;
    private IOIO ioio_;
    public int echoSeconds;
    public int echoDistanceCm;

    /**
     * 这个线程在所有的IOIO Activity中都会出现，一旦应用启动它会重新开始运行，并且在应用暂停的时候
     * 会终止此线程。
     *
     * initMotor()方法用于配置电机模块
     * initDetectEdge()方法用于配置边缘检测模块
     * initTouchSence()方法用于配置触摸模块
     * initLed()方法用于配置Led模块
     * initServo()方法用于配置舵机模块
     * initUltrasonicSensor()方法用于配置超声波传感器模块
     */
    class Looper extends BaseIOIOLooper{
        /**
        * 方法名称：initMotor（）
        * 功能：电机引脚GPIO口设置
        */
        public void initMotor() throws ConnectionLostException,InterruptedException{
            leftMotorPwm1 = ioio_.openPwmOutput(Pwm_Pin1, 100);
            leftMotorPwm2 = ioio_.openPwmOutput(Pwm_Pin2, 100);
            rightMotorPwm1 = ioio_.openPwmOutput(Pwm_Pin3, 100);
            rightMotorPwm2 = ioio_.openPwmOutput(Pwm_Pin4, 100);
        }

        /**
        * 方法名称：initDetectEdge()
        * 功能：红外模块引脚GPIO口设置
        */
        public void initDetectEdge() throws ConnectionLostException,InterruptedException{
            detectEdge = ioio_.openDigitalInput(DetectEdge_Pin);

        }

        /**
        * 方法名称：initTouchSence()
        * 功能：触摸模块引脚GPIO口设置
        */
        public void initTouchSence() throws ConnectionLostException,InterruptedException{
            touchSence1 = ioio_.openDigitalInput(TouchSence_Pin1);
            touchSence2 = ioio_.openDigitalInput(TouchSence_Pin2);
            touchSence3 = ioio_.openDigitalInput(TouchSence_Pin3);
            touchSence4 = ioio_.openDigitalInput(TouchSence_Pin4);
            touchSence5 = ioio_.openDigitalInput(TouchSence_Pin5);
        }

        /**
        * 方法名称：initLed()
        * 功能：Led模块引脚GPIO口设置
        */
        public void initLed() throws ConnectionLostException,InterruptedException{
            faceLedLeftEye = ioio_.openDigitalOutput(FaceLed_Pin1, false);
            faceLedRightEye = ioio_.openDigitalOutput(FaceLed_Pin2, false);
            faceLedMouth = ioio_.openDigitalOutput(FaceLed_Pin3, false);
        }

        /**
        * 方法名称：initServo()
        * 功能:舵机模块引脚GPIO口设置
         */
        public void initServo() throws ConnectionLostException,InterruptedException{
            servoPwm1 = ioio_.openPwmOutput(Servo_Pin1, 50);
            servoPwm2 = ioio_.openPwmOutput(Servo_Pin2, 50);
        }

        /**
        * 方法名称：initUltrasonicSensor()
        * 功能：超声波传感器引脚的GPIO口设置
         */
        public void initUltrasonicSensor() throws ConnectionLostException{
           try{
               ultrasonicSensorEcho=ioio_.openPulseInput(ultrasonicSensorEcho_Pin, PulseInput.PulseMode.POSITIVE);
               ultrasonicSensorTrigger=ioio_.openDigitalOutput(ultrasonicSensorTrigger_Pin);
           }catch (ConnectionLostException e){
               throw e;
           }
        }

        /**
         * 方法名称：setup()
         * 功能：在当前线程中对各个模块进行初始化
         */
        @Override
        public void setup() throws ConnectionLostException,InterruptedException{
            initMotor();
            initDetectEdge();
            initTouchSence();
            initLed();
            initServo();
            initUltrasonicSensor();
            UltrasonicsSensorOutput us =new UltrasonicsSensorOutput();
            new Thread(us).start(); //超声波测距传感器的线程实例化后放在这里启动
        }

        /**
        * 方法名称：setMotorDuty
        * 功能：电机pwm波参数设置
        */
        public void setMotorDuty()  throws ConnectionLostException,InterruptedException{
            leftMotorPwm1.setDutyCycle(leftMoterPwmDuty1);
            leftMotorPwm2.setDutyCycle(leftMoterPwmDuty2);
            rightMotorPwm1.setDutyCycle(rightMotorPwmDuty1);
            rightMotorPwm2.setDutyCycle(rightMotorPwmDuty2);
        }

        /**
        * 方法名称：setServoDuty()
        * 功能：舵机pwm波参数设置
        */
        public void setServoDuty() throws ConnectionLostException, InterruptedException {
            servoPwm1.setDutyCycle(servoPwmDuty1);
            servoPwm2.setDutyCycle(servoPwmDuty2);
        }

        /**
        * 方法名称：judgeTouchPlace()
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

        /**
        * 方法名称：setDetectEdge()
        * 功能：判断机器人的运动方向上是否存在跌落的危险
         */
        public void setDetectEdge() throws ConnectionLostException,InterruptedException{
            if(detectEdgeEnable){
                if (detectEdge.read()){
                    moveStop();//这里设定检测到边缘的位置时，电机停止运行
                }
            }
        }

        /**
        * 方法名称：setFaceLedPlace()
        * 功能：设置Led灯的位置并且控制灯的亮灭
        */
        public void setFaceLedPlace() throws ConnectionLostException,InterruptedException{
            faceLedLeftEye.write(faceLedLeftEyeFlag);
            faceLedRightEye.write(faceLedRightEyeFlag);
            faceLedMouth.write(faceLedMouthFlag);
        }

        /**
         * 当IOIO连接建立的时候反复调用loop（）方法
         *
         * @throws ConnectionLostException
         *         当IOIO连接丢失的时候
         * @throws InterruptedException
         *         当IOIO线程中断的时候
         */
        @Override
        public void loop() throws ConnectionLostException,InterruptedException {
            setMotorDuty();
            judgeTouchPlace();
            setDetectEdge();
            setServoDuty();
            Log.d("Thread","motor run");
        }

        /**
         * 当和IOIO失去连接的时候调用此方法
         */
        @Override
        public void disconnected(){

        }
    }

    /**
     * 此方法创建了超声波测距模块单独运行的线程
     *
     * run()内部为线程运行时的执行体
     */
    class UltrasonicsSensorOutput implements Runnable{
        @Override
        public void run() {
            while(true)
            {
                try {
                    ultrasonicSensorTrigger.write(false);
                    Thread.sleep(5);
                    ultrasonicSensorTrigger.write(true);
                    Thread.sleep(1);
                    ultrasonicSensorTrigger.write(false);
                    echoSeconds=(int)(ultrasonicSensorEcho.getDuration()*1000*1000);
                    echoDistanceCm=echoSeconds/29/2;
                    Thread.sleep(20);
                    Log.d("Thread", "get the data");//超生波测距线程循环执行的时候可以打印此日志
                }catch (InterruptedException e){
                    ioio_.disconnect();
                }catch (ConnectionLostException e){
                }
            }
        }

    }

    /**
     * 方法名称：moveForward(int speed)
     * 功能：电机正转
     *
     * @param speed 调节速度
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

    /**
     * 方法名称：moveBackward（int speed）
     * 功能：电机反转
     *
     * @param speed 调节速度
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

    /**
     * 方法名称：turnCircle(int speed,boolean direction)
     * 功能：原地转圈
     *
     * @param speed 调节速度
     * @param direction 调节转动方向
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

    /**
    * 方法名称：moveStop（）
    * 功能：电机停止转动
    */
    public void moveStop(){
        leftMoterPwmDuty1 = 0;
        leftMoterPwmDuty2 = 0;
        rightMotorPwmDuty1 = 0;
        rightMotorPwmDuty2 = 0;
    }

    /**
     * 方法名称：motorLeft(int speed,boolean direction)
     * 功能：左电机控制
     * @param speed 调节速度
     * @param direction 调节转动方向
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

    /**
     * 方法名称：motorRight(int speed,boolean direction)
     * 功能：右电机控制
     *
     * @param speed 调节速度
     * @param direction 调节转动方向
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

    /**
     * 方法名称：openLed(int ledPlace)
     * 功能：开启头部Led
     * @param ledPlace Led点亮的位置
     */
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

    /**
     * 方法名称：servoControl(float angle)
     * 功能：可以实现舵机180°转动调节，speed=0时，电机占空比设为2.5，此时对应的转动角度为0，发生舵机的抖动，
     * 所以设为2.56，让电机转动角度初值比0大一点
     *
     * @param angle 调整舵机转动到达的角度
     */
    public void servoControl(float angle){
        servoPwmDuty1=(2.56f+angle/10)/100;
        servoPwmDuty2=(2.56f+angle/10)/100;
    }

    /**
     * 创建自己的IOIO线程的方法
     */
    protected IOIOLooper createIOIOLooper(){
        return new Looper();
    }
}
