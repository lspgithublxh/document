package com.example.demo.artifict.pullStream;

import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

/**
 * Description 每个sink有自己的 cb,
 *
 * @author lishaoping
 * @ClassName Sink
 * @date 2021.04.06 19:02
 */
@Slf4j
public class Sink{

    public static ISinkable<Integer> logSink(){
        return new ISinkable<Integer>() {
            @Override
            public void sink(IReadable<Integer> readable) {
                sink11(readable);
            }
        };
    }

    public static void sink11(IReadable<Integer> readable) {
        log.info("request-stream start!");
        readable.read(null, new Icb() {
            @Override
            public void send(Boolean end, Object o) {
                log.info("response-stream end!");
                log.info("sink:end: {}", end);
                log.info("sink:o:{}", o);
                if(Objects.nonNull(end) && end) return;
                readable.read(null, this);
            }
        });
    }

    /**
     * 接收到的数据输出到控制台 的sink
     * @return sink
     */
    public static ISinkable<Double> consoleSink(){
        return new ISinkable<Double>() {
            @Override
            public void sink(IReadable<Double> readable) {
                log.info("request-stream start!");
                readable.read(null, new Icb<Double>() {
                    @Override
                    public void send(Boolean end, Double o) {
                        log.info("response-stream end!");
                        log.info("sink:end: {}", end);
                        log.info("sink:o:{}", o);
                        if(Objects.nonNull(end) && end) return;
                        readable.read(null, this);
                    }
                });
            }
        };
    }
}
