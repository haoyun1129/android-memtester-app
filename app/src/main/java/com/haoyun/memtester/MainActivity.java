package com.haoyun.memtester;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements MemTester.MemTesterListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private MemTester mMemTester;
    private TextView mTv;
    private String mName;
    private int mProgress;
    private String[] mTestNames;
    private int mIndex;
    private RecyclerView mRvTests;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTv = findViewById(R.id.sample_text);
        mRvTests = findViewById(R.id.rvTests);
        mMemTester = new MemTester();
        RecyclerView.Adapter testAdapter = new MemTesterAdapter(this, mMemTester);
        mRvTests.setLayoutManager(new LinearLayoutManager(this));
        mRvTests.setAdapter(testAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        startMemTester();
    }

    private void startMemTester() {
        mMemTester.register(MainActivity.this);
        mTestNames = mMemTester.getTests();
        // new Thread(new Runnable() {
        //     @Override
        //     public void run() {
        //
        //
        //         mMemTester.start();
        //     }
        // }).start();
    }

    @Override
    public void onTestStart(int index, String name) {
        mName = name;
        mProgress = 0;
        runOnUiThread(updateUI);
    }

    @Override
    public void onTestProgress(int index, int progress) {
        mIndex = index;
        mProgress = progress;
        runOnUiThread(updateUI);
    }


    private Runnable updateUI = new Runnable() {
        @Override
        public void run() {
            mTv.setText(mName + ": " + mProgress + ", " + getTestCount());
        }
    };
    private int getTestCount() {
        return mTestNames.length;
    }
}
