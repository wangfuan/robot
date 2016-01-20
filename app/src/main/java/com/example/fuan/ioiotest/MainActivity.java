package com.example.fuan.ioiotest;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import ioio.lib.api.DigitalInput;
import ioio.lib.api.DigitalOutput;
import ioio.lib.api.IOIO;
import ioio.lib.api.PwmOutput;
import ioio.lib.api.exception.ConnectionLostException;
import ioio.lib.util.BaseIOIOLooper;
import ioio.lib.util.IOIOLooper;
import ioio.lib.util.android.IOIOActivity;


public class MainActivity extends IOIOActivity{
    private TextView textView_;
    private SeekBar seekBar_;
    private ToggleButton toggleButton_;


    private TextView DetectEdge;


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView_=(TextView)findViewById(R.id.textView2);
        seekBar_=(SeekBar)findViewById(R.id.seekBar);
        toggleButton_=(ToggleButton)findViewById(R.id.toggleButton);



        DetectEdge=(TextView)findViewById(R.id.detectedge);//红外检测变化时文字框发生改变

        seekBar_.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textView_.setText(seekBar_.getProgress() + "%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        enableUi(false);
    }
    class Looper extends BaseIOIOLooper{
        private PwmOutput PwmOutput_1;
        private DigitalOutput MotorRefer_1;//1号电机
        private PwmOutput PwmOutput_2;
        private DigitalOutput MotorRefer_2;//2号电机
        private float pwmChange;
        private Boolean motorRotate_1=true;
        private Boolean motorRotate_2=true;

//      private DigitalOutput led_;

        private DigitalOutput LedLefteye;
        private DigitalOutput LedRighteye;
        private DigitalOutput LedMouth;//嘴巴、眼睛处的led灯
        private Boolean LedLefteyeswitch=true;
        private Boolean LedRighteyeswitch=true;
        private Boolean LedMouthswitch=true;

        private DigitalInput DetectEdge;//边缘检测输入引脚变量设置
        private Boolean DetectEdgeflag=true;//边缘检测标志位

        private DigitalInput TouchHead;
        private DigitalInput TouchShlf;
        private DigitalInput TouchShrg;
        private DigitalInput TouchBottom;//触摸部位引脚变量设置
        private Boolean TouchHeadflag=true;
        private Boolean TouchShlfflag=true;
        private Boolean TouchShrgflag=true;
        private Boolean TouchBottomflag=true;

        @Override
        public void setup() throws ConnectionLostException{
            //led_=ioio_.openDigitalOutput(IOIO.LED_PIN, true);

            PwmOutput_1=ioio_.openPwmOutput(11, 100);
            MotorRefer_1=ioio_.openDigitalOutput(12, true);
            PwmOutput_2=ioio_.openPwmOutput(13,100);
            MotorRefer_2=ioio_.openDigitalOutput(14,true);//电机引脚设置

            LedLefteye=ioio_.openDigitalOutput(16, true);
            LedRighteye=ioio_.openDigitalOutput(17,true);
            LedMouth=ioio_.openDigitalOutput(18, true);//左右眼、嘴巴处Led灯的引脚设置


            //touchSen_=ioio_.openDigitalInput(10, DigitalInput.Spec.Mode.PULL_DOWN);

            DetectEdge=ioio_.openDigitalInput(20);//边缘检测输入管脚设置

            TouchHead=ioio_.openDigitalInput(25);
            TouchShlf=ioio_.openDigitalInput(26);
            TouchShrg=ioio_.openDigitalInput(27);
            TouchBottom=ioio_.openDigitalInput(28);//触摸部位输入管脚设置

            enableUi(true);

        }

        public void MoveForward(int speed) throws ConnectionLostException,InterruptedException{
            pwmChange = speed/100.0f;
            motorRotate_1=true;
            motorRotate_2=true;
            PwmOutput_1.setDutyCycle(pwmChange);
            MotorRefer_1.write(motorRotate_1);
            PwmOutput_2.setDutyCycle(pwmChange);
            MotorRefer_2.write(motorRotate_2);
        }//电机正转

        public void Backward(int speed) throws ConnectionLostException,InterruptedException{
            pwmChange = speed/100.0f;
            motorRotate_1=false;
            motorRotate_2=false;
            PwmOutput_1.setDutyCycle(pwmChange);
            MotorRefer_1.write(motorRotate_1);
            PwmOutput_2.setDutyCycle(pwmChange);
            MotorRefer_2.write(motorRotate_2);
        }//电机反转

        public void TurnCircle(int speed,boolean direction) throws ConnectionLostException,InterruptedException{
            pwmChange = speed/100.0f;
            motorRotate_1 = direction;
            motorRotate_2 = !direction;
            PwmOutput_1.setDutyCycle(pwmChange);
            MotorRefer_1.write(!motorRotate_1);
            PwmOutput_2.setDutyCycle(pwmChange);
            MotorRefer_2.write(motorRotate_2);
        }//机器人原地旋转，可以控制旋转速度和旋转方向（顺、逆时针），但是不能控制圆周旋转角度

        public void Stop() throws ConnectionLostException,InterruptedException{
            if (motorRotate_1){
                pwmChange=1.0f;
            }
            else {
                pwmChange=0.0f;
            }
            PwmOutput_1.setDutyCycle(pwmChange);
            if (motorRotate_2){
                pwmChange=1.0f;
            }
            else {
                pwmChange=0.0f;
            }
            PwmOutput_2.setDutyCycle(pwmChange);
        }//不管机器人在哪个运动状态，都可以使机器人停止

        public void Led(boolean lefteye,boolean righteye,boolean mouth)throws  ConnectionLostException,InterruptedException{
            LedLefteyeswitch=lefteye;
            LedLefteye.write(LedLefteyeswitch);

            LedRighteyeswitch=righteye;
            LedRighteye.write(LedRighteyeswitch);

            LedMouthswitch=mouth;
            LedMouth.write(LedMouthswitch);
        }//眼睛和嘴巴的Led开关控制函数，顺序为：左眼，右眼，嘴巴

        public void Detect() throws ConnectionLostException,InterruptedException{
            if (DetectEdge.read())
                DetectEdgeflag=true;
            else
                DetectEdgeflag=false;

        }//检测到边缘的时候（即无障碍物），红外管输出高电平信号

        public void Touch() throws ConnectionLostException,InterruptedException{
            if (TouchHead.read())
                TouchHeadflag = true;
            else
                TouchHeadflag = false;

            if (TouchShlf.read())
                TouchShlfflag = true;
            else
                TouchShlfflag = false;

            if (TouchShrg.read())
                TouchShrgflag = true;
            else
                TouchShrgflag = false;

            if (TouchBottom.read())
                TouchBottomflag = true;
            else
                TouchBottomflag = false;
        }//如果头部、左右耳朵及底部触摸检测成功，相应标志位置高

        @Override
        public void loop() throws ConnectionLostException,InterruptedException{

            //pwmOutput_.setDutyCycle(seekBar_.getProgress() / 100.0f);
            //pwmOutput_.setDutyCycle(0.5f);
            //textView_.setText(seekBar_.getProgress()+"%");
            // led_.write(!toggleButton_.isChecked());
            /**if(touchSen_.read())
             led_.write(true);
             else
             led_.write(false);*/

        }

        @Override
        public void disconnected(){
            enableUi(false);
        }

    }

    @Override
    protected IOIOLooper createIOIOLooper(){
        return new Looper();
    }

    private void enableUi(final boolean enable){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                seekBar_.setEnabled(enable);
                toggleButton_.setEnabled(enable);
            }
        });
    }
}

/*public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}*/
