package com.example.demo.artifict.pullStream;

import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

/**
 * 负责产生一个个的IReadable对象
 *
 * @author lishaoping
 * @ClassName Source
 * @date 2021.04.06 18:59
 */
@Slf4j
public class Source{

    /**
     * 产生随机数的IReadable
     * @param n 个数
     * @return IReadable
     */
    public static IReadable<Integer> random(final int n){
        return new IReadable<Integer>() {
            private int nn = n;
            @Override
            public void read(Boolean end, Icb<Integer> cb) {
                log.info("source:request-stream end!!");
                log.info("source:response-stream start!,cb：{}", cb.hashCode());
                if(Objects.nonNull(end) && end){
                     cb.send(true, null);
                     return;
                }
                if(0 > --nn){
                    cb.send(true, null);
                    return;
                }
                cb.send(null, (int)Math.random());//
            }
        };
    }

    /**
     * 产生时间戳
     * @param n 个数
     * @return
     */
    public static IReadable<Long> timestamp(final int n){
        return new IReadable<Long>() {
            private int nn = n;
            @Override
            public void read(Boolean end, Icb<Long> cb) {
                log.info("source:request-stream end!!");
                log.info("source:response-stream start!,cb：{}", cb.hashCode());
                if(Objects.nonNull(end) && end){
                    cb.send(true, null);
                    return;
                }
                if(0 > --nn){
                    cb.send(true, null);
                    return;
                }
                cb.send(null, System.currentTimeMillis());//
            }
        };
    }


}
