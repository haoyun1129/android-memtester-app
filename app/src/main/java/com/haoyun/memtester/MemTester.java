package com.haoyun.memtester;

import android.util.Log;

import java.util.ArrayList;

class MemTester {

    private static final String TAG = MemTester.class.getSimpleName();
    static private MemTester sInstance;

    private native void native_start(int size, int loop);
    private native String[] native_get_tests();

    private void onTestProgress(int index, float progress) {
        Log.v(TAG, "onTestProgress: " + index + ", " + progress);
        mMemTests.get(index).progress = progress;
        if (mListener != null) {
            mListener.onTestProgress(index, progress);
        }
    }

    private ArrayList<MemTest> mMemTests;
    private MemTesterListener mListener;
    static {
        System.loadLibrary("native-lib");
    }

    private MemTester() {
        if (sInstance != null) {
            throw new RuntimeException("Unexpected singleton instances");
        }
        String[] mNames = native_get_tests();
        mMemTests = new ArrayList<>();
        for (String name : mNames) {
            mMemTests.add(new MemTest(name));
        }
    }

    public void reset() {
        for (MemTest test : mMemTests) {
            test.reset();
        }
    }

    static public MemTester getInstance() {
        if (sInstance == null) {
            synchronized (MemTester.class) {
                if (sInstance == null) {
                    sInstance = new MemTester();
                }
            }
        }
        return sInstance;
    }

    public void register(MemTesterListener listener) {
        mListener = listener;
    }

    public void start(int size, int loop) {
        native_start(size, loop);
    }
    public String[] getTests() {
        return native_get_tests();
    }

    public ArrayList<MemTest> getMemTests() {
        return mMemTests;
    }

    private void onTestCompleted(int index, int status) {
        Log.v(TAG, "onTestCompleted: " + index + ", " + status);
        if (index != -1) {
            mMemTests.get(index).status = Status.fromInt(status);
        }
        if (mListener != null) {
            mListener.onTestCompleted(index, Status.fromInt(status));
        }
    }

    // Callback from native
    private void onTestStart(int index, String name) {
        Log.v(TAG, "onTestStart: " + index + ", " +  name);
        mMemTests.get(index).status = Status.RUNNING;
        if (mListener != null) {
            mListener.onTestStart(index, name);
        }
    }

    public enum Status {
        STOPPED, RUNNING, PASS, NG;

        public static Status fromInt(int status) {
            return Status.values()[status];
        }
    }

    public interface MemTesterListener {
        void onTestStart(int index, String name);

        void onTestProgress(int index, float progress);

        void onTestCompleted(int index, Status result);
    }
}
