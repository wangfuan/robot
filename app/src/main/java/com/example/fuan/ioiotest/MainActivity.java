package com.example.fuan.ioiotest;

import android.os.Bundle;
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

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView_=(TextView)findViewById(R.id.textView2);
        seekBar_=(SeekBar)findViewById(R.id.seekBar);
        toggleButton_=(ToggleButton)findViewById(R.id.toggleButton);

        seekBar_.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textView_.setText(seekBar_.getProgress()+"%");
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
        private PwmOutput pwmOutput_;
        private DigitalOutput led_;
        private DigitalInput touchSen_;


        @Override
        public void setup() throws ConnectionLostException{
            led_=ioio_.openDigitalOutput(IOIO.LED_PIN,true);
            pwmOutput_=ioio_.openPwmOutput(12, 100);
            touchSen_=ioio_.openDigitalInput(10, DigitalInput.Spec.Mode.PULL_DOWN);
            enableUi(true);
        }

        @Override
        public void loop() throws ConnectionLostException,InterruptedException{
            pwmOutput_.setDutyCycle(seekBar_.getProgress() / 100.0f);
            //pwmOutput_.setDutyCycle(0.5f);
            //textView_.setText(seekBar_.getProgress()+"%");
           // led_.write(!toggleButton_.isChecked());
            if(touchSen_.read())
                led_.write(true);
            else
                led_.write(false);
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
