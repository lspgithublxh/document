package com.example.demo.artifict.pullStream.cbable;

import com.example.demo.artifict.pullStream.Icb;

import java.util.Objects;

/**
 * Description 泛化
 *
 * @author lishaoping
 * @ClassName DoubleCb
 * @date 2021.04.07 16:51
 */
public class DoubleCb implements Icb<Integer> {

    private Icb<Integer> icb;

    public DoubleCb(Icb<Integer> origin){
        this.icb = origin;
    }

    @Override
    public void send(Boolean end, Integer data) {
        icb.send(end, Objects.isNull(data) ? null : data * 2);
    }
}
