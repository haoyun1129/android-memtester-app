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
    private static final int TEST_COMPLETED = 1;
    private MemTester mMemTester;
    private RecyclerView mRvTests;
    private Handler mUiHandler;
    private MemTesterAdapter mTestAdapter;

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
    public void onTestProgress(int index, float progress) {
        Message msg = mUiHandler.obtainMessage(UPDATE_PROGRESS, index, 0, progress);
        msg.sendToTarget();
    }

    @Override
    public void onTestCompleted(int index, MemTester.Status result) {
        Message msg = mUiHandler.obtainMessage(TEST_COMPLETED, index, 0, result);
        msg.sendToTarget();
    }

    private class UiHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            int index;
            switch (msg.what) {
                case UPDATE_PROGRESS:
                    index = msg.arg1;
                    float progress = (float) msg.obj;
                    Log.v(TAG, "handleMessage: UPDATE_PROGRESS, index=" + index + ", progress=" + progress);
                    mTestAdapter.notifyItemChanged(index);
                    break;
                case TEST_COMPLETED:
                    index = msg.arg1;
                    mTestAdapter.notifyItemChanged(index);
                    break;
            }
        }
    }
}
