package com.haoyun.memtester;

import android.util.Log;

import java.util.ArrayList;

class MemTester {

    private static final String TAG = MemTester.class.getSimpleName();
    static private MemTester sInstance;
    private native void native_start();
    private native String[] native_get_tests();

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

    public void start() {
        native_start();
    }
    public String[] getTests() {
        return native_get_tests();
    }

    public ArrayList<MemTest> getMemTests() {
        return mMemTests;
    }

    public interface MemTesterListener {
        void onTestStart(int index, String name);
        void onTestProgress(int index, int progress);
    }

    // Callback from native
    private void onTestStart(int index, String name) {
        Log.v(TAG, "onTestStart: " + index + ", " +  name);
        if (mListener != null) {
            mListener.onTestStart(index, name);
        }
    }
    private void onTestProgress(int index, int progress) {
        Log.v(TAG, "onTestStart: " + index + ", " +  progress);
        mMemTests.get(index).progress = progress;
        if (mListener != null) {
            mListener.onTestProgress(index, progress);
        }
    }
}
