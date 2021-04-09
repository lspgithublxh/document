package com.example.demo.artifict.pullStream.throughable;

import com.baomidou.mybatisplus.extension.api.R;
import com.example.demo.artifict.pullStream.IReadable;
import com.example.demo.artifict.pullStream.IThroughable;
import com.example.demo.artifict.pullStream.Icb;
import com.example.demo.artifict.pullStream.util.IcbBuilder;
import lombok.extern.slf4j.Slf4j;

/**
 * Description TODO
 *
 * @author lishaoping
 * @ClassName BaseThrough
 * @date 2021.04.07 16:37
 */
@Slf4j
public class BaseThrough<T> implements IThroughable<T, T>{

    private IcbBuilder<T, T> icb;

    public BaseThrough(IcbBuilder<T, T> icb){
        this.icb = icb;
    }

    @Override
    public IReadable<T> through(IReadable<T> readable) {
        return new IReadable<T>(){
            @Override
            public void read(Boolean end, Icb<T> cb) {
                log.info("request-stream:");
                readable.read(end, icb.wrapper(cb).build());
            }
        };
    }
}
