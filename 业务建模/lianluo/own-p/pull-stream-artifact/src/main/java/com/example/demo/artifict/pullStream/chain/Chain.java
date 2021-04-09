package com.example.demo.artifict.pullStream.chain;

import com.example.demo.artifict.pullStream.IReadable;
import com.example.demo.artifict.pullStream.ISinkable;
import com.example.demo.artifict.pullStream.IThroughable;
import com.example.demo.artifict.pullStream.fun.DataMapper;
import com.example.demo.artifict.pullStream.readable.*;
import lombok.Data;

import java.util.concurrent.Executor;

/**
 * Description 调用链
 *
 * @author lishaoping
 * @ClassName Chain
 * @date 2021.04.07 18:38
 */
@Data
public class Chain<T> {

    private IReadable<T> iReadable;

    public Chain(IReadable<T> iReadable){
        this.iReadable = iReadable;
    }

    /**
     * 创建初始的 Chain ，比如 利用Source来创建
     * @param iReadable
     * @param <R>
     * @return
     */
    public static <R> Chain<R> create(IReadable<R> iReadable){
        return new Chain<R>(iReadable);
    }

    /**
     * 链式 添加 IThroughable
     * @param iThroughable
     * @param <Re>
     * @return
     */
    public <Re> Chain<Re> appendThrough(IThroughable<T, Re> iThroughable){
        IReadable<Re> readable = iThroughable.through(iReadable);
        return new Chain<Re>(readable);
    }

    /**
     * 启动 sink过程：即执行request过程再response过程。
     * 请求数据由主线程调用，但产生数据和消费数据 可以是 和 请求数据 同步或者异步
     * @param iSinkable
     */
    public void startSink(ISinkable<T> iSinkable){
        iSinkable.sink(iReadable);
    }

    /**
     * 异步数据请求
     * @param executor 请求执行线程池
     * @return chain
     */
    public  Chain<T> asyncReadable(Executor executor){
        return new Chain<T>(new AsyncReadable<T>(executor, iReadable));
    }

    /**
     * 异步发送数据
     * @param executor
     * @return
     */
    public Chain<T> asyncRead(Executor executor){
        return new Chain<T>(new AsyncIcbReadable<>(executor, iReadable));
    }

    /**
     * 合并 另一个Chain;两个Chain 依次 输出到 cb里。只支持同步模式的Chain
     * @param chain2
     * @return
     */
    public Chain<T> syncMerge(Chain<T> chain2){
        return new Chain<>(new MergeChainReadable<>(this, chain2));
    }

    /**
     * 合并 另一个Chain;两个Chain 依次 输出到 cb里。支持异步模式的Chain
     * @param chain2
     * @return
     */
    public Chain<T> merge(Chain<T> chain2){
        return new Chain<>(new CommonMergeChainReadable<>(this, chain2));
    }

    public Chain<T> asyncMerge(Chain<T> chain2, Executor executor){
        return new Chain<>(new AsyncCommonMergeChainReadable<>(this, chain2, executor));
    }

    /**
     * 合并 另一个Chain;两个Chain 同步先后 将读取到的数据 输出到 cb里。
     * @param chain2 另一个 chain
     * @param dataMapper 数据转换器
     * @param <Re> 另一个 chain的数据类型
     * @return 合并后的新的Chain
     */
    protected  <Re> Chain<T> merge(Chain<Re> chain2, DataMapper<Re, T> dataMapper){
        return new Chain<>(new MergeChainReadable2<T, Re>(this, chain2, dataMapper));
    }
}
