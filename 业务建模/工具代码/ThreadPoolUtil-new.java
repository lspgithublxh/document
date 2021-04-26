package com.chehejia.ota.biz.utils;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import rx.functions.Func0;

import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Description 线程池工具
 *
 * @author lishaoping
 * @ClassName ThreadPoolUtil
 * @date 2021.04.15 09:20
 */
@Slf4j
public class ThreadPoolUtil {

    public static Map<UseType,ThreadPoolExecutor> threadPoolUtilMap = Maps.newConcurrentMap();

    /**
     * 根据类型获取一个线程池
     * @param type
     * @return
     */
    public static ThreadPoolExecutor getSingleton(UseType type){
        return threadPoolUtilMap.computeIfAbsent(type, key -> type.func0.call());
    }

    private static ThreadPoolExecutor getTaskJobExecutor() {
        int processorsNum = Runtime.getRuntime().availableProcessors();
        return new ThreadPoolExecutor(processorsNum * 2, processorsNum * 5,
                60, TimeUnit.SECONDS, new ArrayBlockingQueue<>(1),
                r -> {
                    Thread thread = new Thread(r, UseType.TASK_JOB.name());
                    thread.setDaemon(true);
                    thread.setUncaughtExceptionHandler((t, e) -> log.error("Thread-{}: throw exception:{}", t.getName(), e.getMessage()));
                    return thread;
                }, (r, executor) -> {
            r.run();
            log.info("Main Thread run task:");
        });
    }

    public static enum UseType{

        TASK_JOB(1, ThreadPoolUtil::getTaskJobExecutor);

        int v = 0;
        Func0<ThreadPoolExecutor> func0 = null;
        UseType(int v, Func0<ThreadPoolExecutor> func0){
           this.v = v;
           this.func0 = func0;
        }

    }
}
