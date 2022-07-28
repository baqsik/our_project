package com.load.filter.load_filter_platform.task;

public final class Pair {
    private final Integer a;
    private final String b;

    Pair(Integer a, String b) {
        this.a = a;
        this.b = b;
    }

    Integer getA() {
        return this.a;
    }

    String getB() {
        return this.b;
    }

    @Override
    public String toString() {
        return "int " + this.a + " str " + this.b;
    }
}