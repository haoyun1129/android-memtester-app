package com.haoyun.memtester;

class MemTest {
    public String name;
    public float progress;
    public MemTester.Status status;

    public MemTest(String name) {
        this.name = name;
        reset();
    }

    public void reset() {
        progress = 0;
        status = MemTester.Status.STOPPED;
    }
}
