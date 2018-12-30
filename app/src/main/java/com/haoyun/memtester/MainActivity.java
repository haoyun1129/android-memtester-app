package com.haoyun.memtester;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity implements MemTester.MemTesterListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int UPDATE_PROGRESS = 0;
    private MemTester mMemTester;
    private RecyclerView mRvTests;
    private Handler mUiHandler;
    private MemTesterAdapter mTestAdapter;
    private Runnable updateUI = new Runnable() {
        @Override
        public void run() {
            // mTv.setText(mName + ": " + mProgress + ", " + getTestCount());
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRvTests = findViewById(R.id.rvTests);
        mMemTester = MemTester.getInstance();
        mTestAdapter = new MemTesterAdapter(this, mMemTester);
        mRvTests.setLayoutManager(new LinearLayoutManager(this));
        mRvTests.setAdapter(mTestAdapter);
        mUiHandler = new UiHandler();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMemTester.register(MainActivity.this);
    }

    @Override
    public void onTestStart(int index, String name) {
        runOnUiThread(updateUI);
    }

    public void runTest(View v) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                mMemTester.start();
            }
        }).start();
    }

    @Override
    public void onTestProgress(int index, int progress) {
        Message msg = mUiHandler.obtainMessage(UPDATE_PROGRESS, index, progress);
        msg.sendToTarget();
    }

    private class UiHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE_PROGRESS:
                    int index = msg.arg1;
                    Log.v(TAG, "handleMessage: UPDATE_PROGRESS, index = " + msg.arg1 + ", progress=" + msg.arg2);
                    mTestAdapter.notifyItemChanged(index);
                    break;
            }
        }
    }
}
