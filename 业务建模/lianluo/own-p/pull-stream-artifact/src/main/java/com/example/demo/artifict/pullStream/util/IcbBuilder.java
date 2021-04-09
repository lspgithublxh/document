package com.example.demo.artifict.pullStream.util;

import com.baomidou.mybatisplus.extension.api.R;
import com.example.demo.artifict.pullStream.Icb;
import com.example.demo.artifict.pullStream.fun.DataMapper;

import java.util.Objects;

/**
 * Description TODO
 *
 * @author lishaoping
 * @ClassName IcbUtil
 * @date 2021.04.07 16:57
 */
public class IcbBuilder<T,R> {

    Icb<T> origin;

    public static <T,R> IcbBuilder<T,R> builder(){
        return new IcbBuilder<T,R>();
    }

    public IcbBuilder<T,R> wrapper(Icb<T> icb){
        this.origin = icb;
        return this;
    }

    public Icb<R> build(){
        throw new RuntimeException("使用子类");
    }

    public static class DoubleIcbBuilder extends IcbBuilder<Integer, Integer>{

        @Override
        public Icb<Integer> build() {
            return new Icb<Integer>() {
                @Override
                public void send(Boolean end, Integer data) {
                    DoubleIcbBuilder.super.origin.send(end, Objects.isNull(data) ? null: data * 2);
                }
            };
        }
    }

    public static class MapIcbBuilder<T, R> extends IcbBuilder<T, R>{

        private DataMapper<R,T> dataMapper;

        public MapIcbBuilder(DataMapper<R,T> dataMapper){
            this.dataMapper = dataMapper;
        }

        @Override
        public Icb<R> build() {
            return new Icb<R>() {
                @Override
                public void send(Boolean end, R data) {
                     T apply = dataMapper.apply(data);
                    MapIcbBuilder.super.origin.send(end, apply);
                }
            };
        }
    }
}
