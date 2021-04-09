package com.example.demo.artifict.pullStream.readable;

import com.example.demo.artifict.pullStream.IReadable;
import com.example.demo.artifict.pullStream.Icb;

import java.util.concurrent.Executor;

/**
 * Description 异步执行Readable
 *
 * @author lishaoping
 * @ClassName AsyncReadable
 * @date 2021.04.08 15:58
 */
public class AsyncReadable<T> implements IReadable<T> {

    private IReadable iReadable;
    private Executor executor;

    public AsyncReadable(Executor executor, IReadable iReadable){
        this.executor = executor;
        this.iReadable = iReadable;
    }

    @Override
    public void read(Boolean end, Icb cb) {
        executor.execute(()->{
            iReadable.read(end, cb);
        });
    }
}
