package com.example.demo.artifict.pullStream;

/**
 * Description TODO
 *
 * @author lishaoping
 * @ClassName ISinkable
 * @date 2021.04.06 19:47
 */
public interface ISinkable<Data> {

    void sink(IReadable<Data> readable);
}
