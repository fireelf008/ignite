package com.test.ignite.config;

public enum IgniteType {
    /**
     * 单机模式
     */
    NONE(0),

    /**
     * JDBC发现
     */
    JDBC(1);

    private int key;

    IgniteType(int key) {
        this.key = key;
    }

    public int getKey() {
        return this.key;
    }
}
