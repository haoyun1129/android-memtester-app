package com.haoyun.memtester;

class MemTest {
    public String name;
    public float progress;
    public MemTester.Status status = MemTester.Status.STOPPED;

    public MemTest(String name) {
        this.name = name;
        progress = 0;
    }
}
