package com.example.demo.artifict.pullStream;

import com.example.demo.artifict.pullStream.cbable.DoubleCb;
import com.example.demo.artifict.pullStream.fun.DataMapper;
import com.example.demo.artifict.pullStream.throughable.BaseThrough;
import com.example.demo.artifict.pullStream.throughable.GeneralThrough;
import com.example.demo.artifict.pullStream.util.IcbBuilder;
import lombok.extern.slf4j.Slf4j;
import rx.functions.Action1;
import rx.functions.Func1;

import java.util.Objects;

/**
 * Description
 *
 * @author lishaoping
 * @ClassName Through
 * @date 2021.04.06 19:08
 */
@Slf4j
public class Through{

    public static IThroughable<Integer,Integer> throught1(){
        return new IThroughable<Integer,Integer>() {
            @Override
            public IReadable<Integer> through(IReadable<Integer> readable) {
                return doubleData(readable);
            }
        };
    }

    public static IReadable<Integer> doubleData(IReadable<Integer> readable){
        return new IReadable<Integer>(){
            @Override
            public void read(Boolean end, Icb<Integer> cb) {
                log.info("request-stream:");
               readable.read(end, new Icb<Integer>() {
                   @Override
                   public void send(Boolean end, Integer data) {
                       log.info("response-stream: " + data);
                       cb.send(end, Objects.isNull(data) ? null: data * 2);
                   }
               });
            }
        };
    }

    public static IThroughable<Integer,Integer> map2(DataMapper<Integer,Integer> dataMapper){
        return new IThroughable<Integer,Integer>() {
            @Override
            public IReadable<Integer> through(IReadable<Integer> readable) {
                return new IReadable<Integer>(){
                    @Override
                    public void read(Boolean end, Icb<Integer> cb) {
                        log.info("request-stream:");
                        readable.read(end, new Icb<Integer>() {
                            @Override
                            public void send(Boolean end, Integer data) {
                                log.info("response-stream: " + data);
                                cb.send(end, dataMapper.apply(data));
                            }
                        });
                    }
                };
            }
        };
    }

    public static IThroughable<Integer,Integer> doubleData(){
        return new BaseThrough<>(new IcbBuilder.DoubleIcbBuilder());
    }

    public static IThroughable<Integer,Integer> map(DataMapper<Integer,Integer> dataMapper){
        return new BaseThrough<>(new IcbBuilder.DoubleIcbBuilder());
    }

    public static IThroughable<Integer,Double> map3(DataMapper<Integer,Double> dataMapper){
        return new GeneralThrough<Integer,Double>(new IcbBuilder.MapIcbBuilder<Double, Integer>(dataMapper));
    }

    public static <T, Re> IThroughable<T, Re> commonMap(DataMapper<T, Re> dataMapper){
        return new GeneralThrough<T, Re>(new IcbBuilder.MapIcbBuilder<Re, T>(dataMapper));
    }
}
