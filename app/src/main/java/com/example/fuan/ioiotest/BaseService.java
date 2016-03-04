package com.example.fuan.ioiotest;
import ioio.lib.api.DigitalInput;
import ioio.lib.api.DigitalOutput;
import ioio.lib.api.IOIO;
import ioio.lib.api.PulseInput;
import ioio.lib.api.PwmOutput;
import ioio.lib.api.exception.ConnectionLostException;
import ioio.lib.util.BaseIOIOLooper;
import ioio.lib.util.IOIOLooper;
import ioio.lib.util.android.IOIOService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

/**
 * Created by jiangly on 2016/2/26.
 */
public class BaseService extends IOIOService {
    private PwmOutput leftMotorPwm1,leftMotorPwm2,rightMotorPwm1,rightMotorPwm2;
    private static float leftMoterPwmDuty1,leftMoterPwmDuty2,rightMotorPwmDuty1,rightMotorPwmDuty2;
    private final static int PwmPin1=11;
    private final static int PwmPin2=12;
    private final static int PwmPin3=13;
    private final static int PwmPin4=14;
    /*
    detectEdge为红外模块输入变量，用于边缘检测
    detectEdgeEnable为边缘检测开关，true时为开启量，false时为关闭量
     */
    private static DigitalInput detectEdge;
    private static Boolean detectEdgeEnable=false;
    private final static int DetectEdgePin=29;
    /*
    touchSence为触摸传感器输入变量
    touchSencePlace全局变量记录发什么触摸的位置
     */
    private DigitalInput touchSence1;
    private DigitalInput touchSence2;
    private DigitalInput touchSence3;
    private DigitalInput touchSence4;
    private DigitalInput touchSence5;
    private static int touchSencePlace;
    private final static int TouchSencePin1=22;
    private final static int TouchSencePin2=23;
    private final static int TouchSencePin3=24;
    private final static int TouchSencePin4=25;
    private final static int TouchSencePin5=26;
    /*
    faceLed为Led输出变量
     */
    private DigitalOutput faceLedLeftEye;
    private DigitalOutput faceLedRightEye;
    private DigitalOutput faceLedMouth;
    private static Boolean faceLedLeftEyeFlag;
    private static Boolean faceLedRightEyeFlag;
    private static Boolean faceLedMouthFlag;
    private final static int FaceLedPin1=1;
    private final static int FaceLedPin2=2;
    private final static int FaceLedPin3=3;
    /*
    servoPwm为舵机Pwm波输出变量
     */
    private PwmOutput servoPwm1,servoPwm2;
    private static float servoPwmDuty1,servoPwmDuty2;
    private final static int ServoPin1=5;
    private final static int ServoPin2=10;

    /**
     * ultrasonicSensorTrigger为超生波探测器触发数字输出信号变量
     * ultrasonicSensorEcho为超声波探测器回声检测持续时间输出口
     * ultrasonicSensor_Pin设置超声波探测器的连接引脚
     */
    public   static  DigitalOutput ultrasonicSensorTrigger;
    public static PulseInput ultrasonicSensorEcho;
    private final static int ultrasonicSensorTrigger_Pin=7;
    private final static int ultrasonicSensorEcho_Pin=6;
    public   static  IOIO ioio_;
    public static int echoSeconds;
    public static int echoDistanceCm;

    @Override
    protected IOIOLooper createIOIOLooper() {
        return new BaseIOIOLooper() {
            /*
     * 函数名称：initMotor
     * 功能：电机引脚GPIO口设置
     */
            public void initMotor() throws ConnectionLostException,InterruptedException{
                leftMotorPwm1 = ioio_.openPwmOutput(PwmPin1, 100);
                leftMotorPwm2 = ioio_.openPwmOutput(PwmPin2, 100);
                rightMotorPwm1 = ioio_.openPwmOutput(PwmPin3, 100);
                rightMotorPwm2 = ioio_.openPwmOutput(PwmPin4, 100);
            }
            /*
            * 函数名称：initDetectEdge()
            * 功能：红外模块引脚GPIO口设置
            */
            public void initDetectEdge() throws ConnectionLostException,InterruptedException{
                detectEdge = ioio_.openDigitalInput(DetectEdgePin);

            }
            /*
            * 函数名称：initTouchSence()
            * 功能：触摸模块引脚GPIO口设置
            */
            public void initTouchSence() throws ConnectionLostException,InterruptedException{
                touchSence1 = ioio_.openDigitalInput(TouchSencePin1);
                touchSence2 = ioio_.openDigitalInput(TouchSencePin2);
                touchSence3 = ioio_.openDigitalInput(TouchSencePin3);
                touchSence4 = ioio_.openDigitalInput(TouchSencePin4);
                touchSence5 = ioio_.openDigitalInput(TouchSencePin5);
            }
            /*
            * 函数名称：initLed()
            * 功能：Led模块引脚GPIO口设置
            */
            public void initLed() throws ConnectionLostException,InterruptedException{
                faceLedLeftEye = ioio_.openDigitalOutput(FaceLedPin1, false);
                faceLedRightEye = ioio_.openDigitalOutput(FaceLedPin2, false);
                faceLedMouth = ioio_.openDigitalOutput(FaceLedPin3, false);
            }
            /*
            * 函数名称：initServo()
            * 功能:舵机模块引脚GPIO口设置
             */
            public void initServo() throws ConnectionLostException,InterruptedException{
                servoPwm1 = ioio_.openPwmOutput(ServoPin1, 50);
                servoPwm2 = ioio_.openPwmOutput(ServoPin2, 50);
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

            @Override
            protected void setup() throws ConnectionLostException,
                    InterruptedException {
//                leftMotorPwm1 = ioio_.openPwmOutput(11, 100);
//                leftMotorPwm2 = ioio_.openPwmOutput(12, 100);
//                rightMotorPwm1 = ioio_.openPwmOutput(13, 100);
//                rightMotorPwm2 = ioio_.openPwmOutput(14, 100);

                initMotor();
                initDetectEdge();
                initTouchSence();
                initLed();
                initServo();
                initUltrasonicSensor();
                UltrasonicsSensorOutput us =new UltrasonicsSensorOutput();
               // new Thread(us).start(); //超声波测距传感器的线程实例化后放在这里启动
                us.start();
                Log.v("ServiceDemo", "已经初始化");
            }
            /*
                   * 函数名称：setMotorDuty
                   * 功能：电机pwm波参数设置
                   */
            public  void setMotorDuty()  throws ConnectionLostException,InterruptedException{
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
            public void loop() throws ConnectionLostException,
                    InterruptedException {
//                leftMotorPwm1.setDutyCycle( 20/100.0f);
//                leftMotorPwm2.setDutyCycle(0);
//                rightMotorPwm1.setDutyCycle(0);
//                rightMotorPwm2.setDutyCycle( 20/100.0f);
                setMotorDuty();
                judgeTouchPlace();
                setDetectEdge();
                setServoDuty();
                Log.v("ServiceDemo", "正在循坏");
                Log.v("ServiceDemo","leftMoterPwmDuty1    "+ leftMoterPwmDuty1);
                Log.v("ServiceDemo","leftMoterPwmDuty2    "+ leftMoterPwmDuty2);
                Log.v("ServiceDemo","rightMotorPwmDuty1   "+ rightMotorPwmDuty1);
                Log.v("ServiceDemo","rightMotorPwmDuty2   "+ rightMotorPwmDuty2);
            }
        };
    }
        /*
        * 函数名称：moveForward
        * 功能：电机正转，可以调节速度，但是超过提前标定的速度阈值则不能转动
        */
        public static void moveForward(int speed){
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
        public static void moveBackward(int speed){
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
        public static void turnCircle(int speed,boolean direction) {
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
        public static void moveStop(){
            leftMoterPwmDuty1 = 0;
            leftMoterPwmDuty2 = 0;
            rightMotorPwmDuty1 = 0;
            rightMotorPwmDuty2 = 0;
        }

        /*
        * 函数名称：motorLeft
        * 功能：左电机控制，可实现速度和旋转方向的调节，但是速度超过提前设置的速度阈值则不能移动
         */
        public static void motorLeft(int speed,boolean direction){
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
        public static void motorRight(int speed,boolean direction){
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
        public static void openLed(int ledPlace) {
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
        public static void servoControl(float angle){
            servoPwmDuty1=(2.56f+angle/10)/100;
            servoPwmDuty2=(2.56f+angle/10)/100;
        }
//        protected IOIOLooper createIOIOLooper(){
//            return new Looper();
//        }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int result = super.onStartCommand(intent, flags, startId);
        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (intent != null && intent.getAction() != null
                && intent.getAction().equals("stop")) {
            // User clicked the notification. Need to stop the service.
            nm.cancel(0);
            stopSelf();
        } else {
            // Service starting. Create a notification.
            Notification notification = new Notification(
                    R.drawable.abc_ic_menu_copy_mtrl_am_alpha, "IOIO service running",
                    System.currentTimeMillis());
            notification
                    .setLatestEventInfo(this, "IOIO Service", "Click to stop",
                            PendingIntent.getService(this, 0, new Intent(
                                    "stop", null, this, this.getClass()), 0));
            notification.flags |= Notification.FLAG_ONGOING_EVENT;
            nm.notify(0, notification);
        }
        return result;
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

}

/**
 * 此方法创建了超声波测距模块单独运行的线程
 *
 * run()内部为线程运行时的执行体
 */
class UltrasonicsSensorOutput extends Thread{
    @Override
    public void run() {
        while(true)
        {
            try {
                BaseService.ultrasonicSensorTrigger.write(false);
                Thread.sleep(5);
                BaseService.ultrasonicSensorTrigger.write(true);
                Thread.sleep(1);
                BaseService.ultrasonicSensorTrigger.write(false);
                BaseService.echoSeconds=(int)(BaseService.ultrasonicSensorEcho.getDuration()*1000*1000);
                BaseService.echoDistanceCm=BaseService.echoSeconds/29/2;
                Thread.sleep(20);
           //   Log.d("Thread", "get the data");//超生波测距线程循环执行的时候可以打印此日志
            }catch (InterruptedException e){
                BaseService.ioio_.disconnect();
            }catch (ConnectionLostException e){
            }
        }
    }
}