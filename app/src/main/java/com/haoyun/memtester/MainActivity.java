package com.haoyun.memtester;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements MemTester.MemTesterListener {

    private MemTester mMemTester;
    private TextView mTv;
    private String mName;
    private int mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTv = (TextView) findViewById(R.id.sample_text);
    }

    @Override
    protected void onResume() {
        super.onResume();
        startMemTester();
    }

    private void startMemTester() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                mMemTester = new MemTester();
                mMemTester.register(MainActivity.this);
                mMemTester.start();
            }
        }).start();
    }

    @Override
    public void onTestStart(int index, String name) {
        mName = name;
        mProgress = 0;
        runOnUiThread(updateUI);
    }

    @Override
    public void onTestProgress(int index, int progress) {
        mProgress = progress;
        runOnUiThread(updateUI);
    }


    private Runnable updateUI = new Runnable() {
        @Override
        public void run() {
            mTv.setText(mName + ": " + mProgress);
        }
    };

}
