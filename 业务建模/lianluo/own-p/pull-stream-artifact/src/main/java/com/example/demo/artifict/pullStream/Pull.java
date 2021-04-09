package com.example.demo.artifict.pullStream;

import com.example.demo.artifict.pullStream.chain.Chain;
import com.example.demo.artifict.pullStream.fun.FunBox;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executors;

/**
 * Description 组装Source/Through/Sink
 *
 * @author lishaoping
 * @ClassName Pull
 * @date 2021.04.07 15:20
 */
@Slf4j
public class Pull {

    /**
     *  封装过程，先一步步变换 IReadable, 最后sink
     * @param source
     * @param iThroughables
     * @param sinkable
     */
    public static  <Data> void build(IReadable<Data> source, List<IThroughable<Data, Data>> iThroughables, ISinkable<Data> sinkable){
        IReadable<Data> iReadable = source;
        if(!CollectionUtils.isEmpty(iThroughables)){
            for (IThroughable<Data, Data> iThroughable : iThroughables){
                iReadable = iThroughable.through(iReadable);
            }
        }
        sinkable.sink(iReadable);
    }

    public static  <Data> void build2(IReadable<Data> source, ISinkable<Data> sinkable, IThroughable<Data,Data>... iThroughables){
        IReadable<Data> iReadable = source;
        if(Objects.nonNull(iThroughables)){
            for (IThroughable<Data,Data> iThroughable : iThroughables){
                iReadable = iThroughable.through(iReadable);
            }
        }
        sinkable.sink(iReadable);
    }

    public static void main(String[] args) {
        //demo1: Source + Through + Sink
//        demo1();
        //demo2: Source + Sink
//        demo2();
        //列表方式 传递 Ithrought 只能相同类型。必须按方法的逐个逐个的套接IThrought才是最灵活的办法。
        //demo3: 链式调用
//        demo3();
        //demo4: 多个不同映射类型的 Through调用;;由于是小数，所以会 继续多请求一次
//        demo4();
        //demo5:异步 请求和消费数据， 多Through调用；
//        demo5();
        //demo6: 合并流：
//        demo6();
        //demo7:异步请求响应的情况下合并流
//        demo7();
        //demo8: 异步请求响应 且 异步 合并流
        demo8();
        log.info("[main-thread] end.");
    }


    private static void demo1() {
        Pull.build(Source.random(5), Lists.newArrayList(Through.doubleDataThrough()), Sink.logSink());
    }

    private static void demo2() {
        Pull.build2(Source.random(5), Sink.logSink());
//        Pull.build2(Source.random(5), Sink.logSink(), Through.doubleDataThrough());
    }

    private static void demo3() {
        Chain.create(Source.random(5))
                .appendThrough(Through.doubleDataThrough())
                .startSink(Sink.logSink());
    }

    private static void demo4() {
        Chain.create(Source.random(1))
                .appendThrough(Through.doubleData())
                .appendThrough(Through.map3(FunBox::intToDouble))
                .startSink(Sink.consoleSink());
    }

    private static void demo5() {
        Chain.create(Source.random(1))
                .asyncRead(Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()))
                .asyncReadable(Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()))
                .appendThrough(Through.doubleData())
                .appendThrough(Through.map3(FunBox::intToDouble))
                .startSink(Sink.consoleSink());
    }

    private static void demo6() {
        Chain.create(Source.random(1))
                .appendThrough(Through.doubleData())
                .appendThrough(Through.map3(FunBox::intToDouble))
                .merge(Chain.create(Source.timestamp(1))
                                .appendThrough(Through.commonMap(FunBox::longToDouble)))
                .startSink(Sink.consoleSink());
    }

    private static void demo7() {
        Chain.create(Source.random(2))
                .asyncReadable(Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()))
                .asyncRead(Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()))
                .appendThrough(Through.doubleData())
                .appendThrough(Through.map3(FunBox::intToDouble))
                .merge(Chain.create(Source.timestamp(2))
                        .appendThrough(Through.commonMap(FunBox::longToDouble)))
                .merge(Chain.create(Source.timestamp(2))
                        .appendThrough(Through.commonMap(FunBox::longToDouble)))
                .startSink(Sink.consoleSink());
    }

    private static void demo8() {
        Chain.create(Source.random(2))
                .asyncReadable(Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()))
                .asyncRead(Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()))
                .appendThrough(Through.doubleData())
                .appendThrough(Through.map3(FunBox::intToDouble))
                .asyncMerge(Chain.create(Source.timestamp(2))
                        .appendThrough(Through.commonMap(FunBox::longToDouble)),
                        Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()))
                .asyncMerge(Chain.create(Source.timestamp(2))
                                .appendThrough(Through.commonMap(FunBox::longToDouble)),
                        Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()))
                .startSink(Sink.consoleSink());
    }
}
