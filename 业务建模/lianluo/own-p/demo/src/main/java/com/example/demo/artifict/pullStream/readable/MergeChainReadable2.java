package com.example.demo.artifict.pullStream.readable;

import com.example.demo.artifict.pullStream.IReadable;
import com.example.demo.artifict.pullStream.Icb;
import com.example.demo.artifict.pullStream.chain.Chain;
import com.example.demo.artifict.pullStream.fun.DataMapper;

/**
 * Description 合并两个不同数据类型 的 Chain的Readable
 *
 * @author lishaoping
 * @ClassName MergeChainReadable
 * @date 2021.04.08 16:18
 */
public class MergeChainReadable2<T,Re> implements IReadable<T> {

    private DataMapper<Re,T> dataMapper;
    private Chain<T> chain1;
    private Chain<Re> chain2;

    private boolean chain1Finish = false;
    private boolean chain2Finish = false;

    public MergeChainReadable2(Chain<T> chain1, Chain<Re> chain2, DataMapper<Re,T> dataMapper){
        this.chain1 = chain1;
        this.chain2 = chain2;
        this.dataMapper = dataMapper;
    }

    @Override
    public void read(Boolean end, Icb<T> cb) {
        if(!chain1Finish){
            chain1.getIReadable().read(end,cb);
            chain1Finish = true;
        }
        if(!chain2Finish){
            chain2.getIReadable().read(end, new Icb<Re>() {
                @Override
                public void send(Boolean end, Re data) {
                    T t = dataMapper.apply(data);
                    cb.send(end, t);
                }
            });
            chain2Finish = true;
        }

    }

}
