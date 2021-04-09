package com.example.demo.artifict.pullStream.executor;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Description 限制外部使用 线程池，
 *
 * @author lishaoping
 * @ClassName Schedulers
 * @date 2021.04.09 11:07
 */
public class Schedulers {

    public static Scheduler readable(){
        return new Scheduler() {
            @Override
            public Executor getExecutor() {
                return Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
            }
        };
    }

    public static Scheduler read(){
        return new Scheduler() {
            @Override
            public Executor getExecutor() {
                return Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
            }
        };
    }

    public static Scheduler merge(){
        return new Scheduler() {
            @Override
            public Executor getExecutor() {
                return Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
            }
        };
    }

    public static interface Scheduler{

        public Executor getExecutor();
    }
}
