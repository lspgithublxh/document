package com.example.demo.artifict.pullStream.cbable;

import com.example.demo.artifict.pullStream.Icb;

import java.util.concurrent.Executor;

/**
 * Description 异步发送数据
 *
 * @author lishaoping
 * @ClassName AsyncCb
 * @date 2021.04.08 16:04
 */
public class AsyncCb<T> implements Icb<T> {

    private Icb<T> origin;
    private Executor executor;

    public AsyncCb(Executor executor, Icb<T> origin){
        this.executor = executor;
        this.origin = origin;
    }

    @Override
    public void send(Boolean end, T t) {
        executor.execute(()->{
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            origin.send(end, t);
        });
    }
}
