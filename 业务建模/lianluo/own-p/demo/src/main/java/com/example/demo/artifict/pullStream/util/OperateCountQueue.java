package com.example.demo.artifict.pullStream.util;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Description 整数模拟队列
 *
 * @author lishaoping
 * @ClassName IntegerQueue
 * @date 2021.04.09 09:45
 */
public class OperateCountQueue {

    public AtomicInteger count = new AtomicInteger(0);

    public OperateCountQueue(int initCount){
        count.set(initCount);
    }

    public boolean pullOne(){
        if(count.get() > 0){
            synchronized (this){
                if(count.get() > 0){
                    count.getAndDecrement();
                    return true;
                }
            }
        }
        return false;
    }

    public void pushOne(){
        count.getAndIncrement();
    }
}
