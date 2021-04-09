package com.example.demo.artifict.pullStream;

/**
 * Description TODO
 *
 * @author lishaoping
 * @ClassName IThroughable
 * @date 2021.04.07 15:23
 */
public interface IThroughable<T,R> {

    IReadable<R> through(IReadable<T> readable);
}
