package com.haoyun.memtester;

import java.util.ArrayList;
import java.util.List;

class MemTest {
    public String name;
    public float progress;
    public MemTester.Status status;
    private List<String> mLog;


    public MemTest(String name) {
        this.name = name;
        reset();
    }

    public void reset() {
        progress = 0;
        status = MemTester.Status.STOPPED;
        mLog = new ArrayList<>();
    }

    public void log(int priority, String message) {
        mLog.add(message);
    }
}
