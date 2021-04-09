package com.example.demo.artifict.pullStream;

/**
 * 可读性，(所有可读对象 需要实现的接口)
 */
public interface IReadable<Data> {

    /**
     * 数据读取到 cb里
     * @param end 是否结束读取
     * @param cb 数据读取到的目的地
     */
    void read(Boolean end, Icb<Data> cb);
}
