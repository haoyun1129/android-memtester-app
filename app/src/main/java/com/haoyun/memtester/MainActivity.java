package com.haoyun.memtester;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.haoyun.memtester.ui.SizeDialogFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MemTester.MemTesterListener, SizeDialogFragment.SizeResultListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int UPDATE_PROGRESS = 0;
    private static final int TEST_COMPLETED = 1;
    private MemTester mMemTester;
    private RecyclerView mRvTests;
    private Handler mUiHandler;
    private MemTesterAdapter mTestAdapter;
    private Button mBtnTestSize;
    private int mTestSize;
    private Button mBtnTestLoop;
    private Button mBtnEnter;
    private Button mBtnMemtester;
    private List<Button> mButtons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRvTests = findViewById(R.id.rvTests);
        mMemTester = MemTester.getInstance();
        mTestAdapter = new MemTesterAdapter(this, mMemTester);
        mRvTests.setLayoutManager(new GridLayoutManager(this, 1));
        mRvTests.setAdapter(mTestAdapter);
        mBtnMemtester = findViewById(R.id.btnMemtester);
        mBtnTestSize = findViewById(R.id.btnTestSize);
        mBtnTestLoop = findViewById(R.id.btnTestLoop);
        mBtnEnter = findViewById(R.id.btnEnter);
        mButtons = new ArrayList<>();
        mButtons.add(mBtnMemtester);
        mButtons.add(mBtnTestSize);
        mButtons.add(mBtnTestLoop);
        mButtons.add(mBtnEnter);
        mUiHandler = new UiHandler();
        onSizeResult(1);
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
        mMemTester.reset();
        mTestAdapter.notifyDataSetChanged();
        setViewsEnabled(false);
        new Thread(new Runnable() {
            @Override
            public void run() {
                mMemTester.start(mTestSize);
            }
        }).start();
    }

    private void setViewsEnabled(boolean enabled) {
        for (Button b: mButtons) {
            b.setEnabled(enabled);
        }
    }

    public void setSize(View v) {
        SizeDialogFragment sizeDialog = new SizeDialogFragment();
        sizeDialog.setResultListener(this);
        sizeDialog.show(getSupportFragmentManager(), "size_dialog");
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

    @Override
    public void onSizeResult(int size) {
        mTestSize = size;
        mBtnTestSize.setText(size + " MB");
    }

    public void linkToMemtester(View view) {
        String url = "http://pyropus.ca/software/memtester/";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    private class UiHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            int index;
            switch (msg.what) {
                case UPDATE_PROGRESS:
                    index = msg.arg1;
                    float progress = (float) msg.obj;
                    // Log.v(TAG, "handleMessage: UPDATE_PROGRESS, index=" + index + ", progress=" + progress);
                    mTestAdapter.notifyItemChanged(index);
                    break;
                case TEST_COMPLETED:
                    index = msg.arg1;
                    if (index == -1) {
                        setViewsEnabled(true);
                    } else {
                        mTestAdapter.notifyItemChanged(index);
                    }
                    break;
            }
        }
    }
}
