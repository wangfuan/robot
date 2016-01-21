package com.example.fuan.ioiotest;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ToggleButton;

/**
 * Created by Brick on 1/21/2016.
 */
public class MainActivity extends BaseActivity {
    private TextView textView_;
    private SeekBar seekBar_;
    private ToggleButton toggleButton_;
    private Button forwardButton;
    private Button backwardButton;
    private Button stopButton;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView_=(TextView)findViewById(R.id.textView2);
        seekBar_=(SeekBar)findViewById(R.id.seekBar);
        toggleButton_=(ToggleButton)findViewById(R.id.toggleButton);

        forwardButton=(Button)findViewById(R.id.button2);
        backwardButton=(Button)findViewById(R.id.button);
        stopButton=(Button)findViewById(R.id.button3);

        forwardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveForward(-50);
            }
        });
        backwardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if(motorRotate){
//                    pwmChange=1.0f;
//                }
//                else{
//                    pwmChange=0.0f;
//                }

            }
        });
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
