package com.example.demo.artifict.pullStream.readable;

import com.example.demo.artifict.pullStream.IReadable;
import com.example.demo.artifict.pullStream.Icb;
import com.example.demo.artifict.pullStream.cbable.AsyncCb;

import java.util.concurrent.Executor;

/**
 * Description 配置 异步响应 cb 的 readable
 *
 * @author lishaoping
 * @ClassName AcceptIcbReadable
 * @date 2021.04.08 16:07
 */
public class AsyncIcbReadable<T> implements IReadable<T> {

    private Executor executor;
    private IReadable<T> origin;

    public AsyncIcbReadable(Executor executor, IReadable<T> origin){
        this.executor = executor;
        this.origin = origin;
    }

    @Override
    public void read(Boolean end, Icb<T> cb) {
        origin.read(end, new AsyncCb<>(executor, cb));
    }
}
