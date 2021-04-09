package com.example.demo.artifict.pullStream;

/**
 *
 */
public interface Icb<Data> {

    void send(Boolean end, Data data);

}
