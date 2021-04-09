package com.example.demo.artifict.pullStream.readable;

import com.example.demo.artifict.pullStream.IReadable;
import com.example.demo.artifict.pullStream.Icb;
import com.example.demo.artifict.pullStream.chain.Chain;
import com.example.demo.artifict.pullStream.util.OperateCountQueue;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
import java.util.concurrent.Executor;

/**
 * Description 合并两个Chain的Readable; 支持异步模式
 *
 * @author lishaoping
 * @ClassName AsyncCommonMergeChainReadable
 * @date 2021.04.08 16:18
 */
@Slf4j
public class AsyncCommonMergeChainReadable<T> implements IReadable<T> {

    private Executor mergeExecutor;
    private Chain<T> chain1;
    private Chain<T> chain2;
    private OperateCountQueue chain1Finish = new OperateCountQueue(1);
    private OperateCountQueue chain2Finish = new OperateCountQueue(1);

    public AsyncCommonMergeChainReadable(Chain<T> chain1, Chain<T> chain2, Executor executor){
        this.chain1 = chain1;
        this.chain2 = chain2;
        this.mergeExecutor = executor;
    }

    @Override
    public void read(Boolean end, Icb<T> cb) {
        mergeExecutor.execute(()->{
            if(chain1Finish.pullOne()){
                chain1.getIReadable().read(end, new Icb<T>() {
                    @Override
                    public void send(Boolean end, T t) {
                        if (Objects.isNull(end) || !end){
                            chain1Finish.pushOne();
                        }
                        cb.send(end, t);
                    }
                });
            }
        });
        mergeExecutor.execute(()->{
            if(chain2Finish.pullOne()){
                chain2.getIReadable().read(end, new Icb<T>() {
                    @Override
                    public void send(Boolean end, T t) {
                        if(Objects.isNull(end) || !end){
                            chain2Finish.pushOne();
                        }
                        cb.send(end, t);
                    }
                });
            }
        });
    }

}
