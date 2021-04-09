package com.example.demo.artifict.pullStream.readable;

import com.example.demo.artifict.pullStream.IReadable;
import com.example.demo.artifict.pullStream.Icb;
import com.example.demo.artifict.pullStream.chain.Chain;
import lombok.extern.slf4j.Slf4j;

/**
 * Description 合并两个Chain的Readable
 *
 * @author lishaoping
 * @ClassName MergeChainReadable
 * @date 2021.04.08 16:18
 */
@Slf4j
public class MergeChainReadable<T> implements IReadable<T> {

    private Chain<T> chain1;
    private Chain<T> chain2;
    private boolean chain1Finish = false;
    private boolean chain2Finish = false;

    public MergeChainReadable(Chain<T> chain1, Chain<T> chain2){
        this.chain1 = chain1;
        this.chain2 = chain2;
    }

    @Override
    public void read(Boolean end, Icb<T> cb) {
        log.info("------------------------------");
        log.info("start chain1 request:");
        log.info("------------------------------");
        if(!chain1Finish){
            chain1.getIReadable().read(end,cb);
            chain1Finish = true;
        }
        log.info("------------------------------");
        log.info("start chain2 request:");
        log.info("------------------------------");
        //方法1：先获取数据缓存起来，后一并发送给下游的cb---必须是特殊的方式发送
        //方法2：增加请求标记，当已经请求过一次了，则第一个chain不再调用(即便调用也只是获取到end结果)。
        if(!chain2Finish){
            chain2.getIReadable().read(end, cb);
            chain2Finish = true;
        }
        log.info("------------------------------");
        log.info("end chain2 request:");
        log.info("------------------------------");
    }

}
