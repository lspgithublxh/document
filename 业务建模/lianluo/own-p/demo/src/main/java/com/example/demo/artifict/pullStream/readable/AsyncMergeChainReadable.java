package com.example.demo.artifict.pullStream.readable;

import com.example.demo.artifict.pullStream.IReadable;
import com.example.demo.artifict.pullStream.Icb;
import com.example.demo.artifict.pullStream.chain.Chain;
import com.example.demo.artifict.pullStream.util.OperateCountQueue;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Description 合并两个Chain的Readable; 支持异步模式
 *
 * @author lishaoping
 * @ClassName MergeChainReadable
 * @date 2021.04.08 16:18
 */
@Slf4j
public class AsyncMergeChainReadable<T> implements IReadable<T> {

    private Chain<T> chain1;
    private Chain<T> chain2;
    private OperateCountQueue chain1Finish = new OperateCountQueue(1);
    private OperateCountQueue chain2Finish = new OperateCountQueue(1);

    public AsyncMergeChainReadable(Chain<T> chain1, Chain<T> chain2){
        this.chain1 = chain1;
        this.chain2 = chain2;
    }

    @Override
    public void read(Boolean end, Icb<T> cb) {
        log.info("------------------------------");
        log.info("start chain1 request:");
        log.info("------------------------------");
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
        log.info("------------------------------");
        log.info("start chain2 request:");
        log.info("------------------------------");
        //方法1：先获取数据缓存起来，后一并发送给下游的cb---必须是特殊的方式发送
        //方法2：增加请求标记，当已经请求过一次了，则第一个chain不再调用(即便调用也只是获取到end结果)。
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
        log.info("------------------------------");
        log.info("end chain2 request:");
        log.info("------------------------------");
    }

}
