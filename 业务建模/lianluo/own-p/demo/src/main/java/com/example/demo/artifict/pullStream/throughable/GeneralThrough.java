package com.example.demo.artifict.pullStream.throughable;

import com.example.demo.artifict.pullStream.IReadable;
import com.example.demo.artifict.pullStream.IThroughable;
import com.example.demo.artifict.pullStream.Icb;
import com.example.demo.artifict.pullStream.util.IcbBuilder;

/**
 * Description TODO
 *
 * @author lishaoping
 * @ClassName GeneralThrough
 * @date 2021.04.07 19:03
 */
public class GeneralThrough <T,R> implements IThroughable<T, R> {


    private IcbBuilder<R,T> builder;

    public GeneralThrough(IcbBuilder<R,T> builder) {
        this.builder = builder;
    }

    @Override
    public IReadable<R> through(IReadable<T> readable) {
        return new IReadable<R>() {
            @Override
            public void read(Boolean end, Icb<R> cb) {
                Icb<T> newCb = builder.wrapper(cb).build();
                readable.read(end, newCb);
            }
        };
    }
}
