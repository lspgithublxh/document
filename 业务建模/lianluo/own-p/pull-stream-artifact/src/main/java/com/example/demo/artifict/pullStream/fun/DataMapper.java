package com.example.demo.artifict.pullStream.fun;

/**
 * Description TODO
 *
 * @author lishaoping
 * @ClassName DataMapper
 * @date 2021.04.07 16:30
 */
@FunctionalInterface
public interface DataMapper<T,R> {

    R apply(T data);
}
