package com.haoyun.memtester;

import android.util.Log;

class MemTester {

    private static final String TAG = MemTester.class.getSimpleName();

    private native void native_start();
    private native String[] native_get_tests();

    private MemTesterListener mListener;
    static {
        System.loadLibrary("native-lib");
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
        if (mListener != null) {
            mListener.onTestProgress(index, progress);
        }
    }
}
