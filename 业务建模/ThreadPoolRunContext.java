package com.chehejia.ota.biz.utils;

import com.chehejia.ota.biz.utils.action.Action1;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Description 执行框架; 方便 使用线程池。
 * example: new ThreadPoolRunContext<>(...).awaitTermination()
 * @author lishaoping
 * @ClassName ThreadPoolRunContext
 * @date 2021.04.15 16:02
 */
@Slf4j
public class ThreadPoolRunContext<T> {

    private ThreadPoolExecutor executor;
    private Set<T> finishHandleItemSet;
    private final AtomicBoolean stopSubmit;
    private CountDownLatch latch;
    private Action1<T> action1;
    private List<T> itemList;
    private long maxParallelRunTime;

    public ThreadPoolRunContext(ThreadPoolUtil.UseType useType, Action1<T> action1, List<T> itemList, long maxParallelRunTime){
        executor = ThreadPoolUtil.getSingleton(useType);
        finishHandleItemSet = Sets.newConcurrentHashSet();
        stopSubmit = new AtomicBoolean(false);
        latch = new CountDownLatch(itemList.size());
        this.action1 = action1;
        this.itemList = itemList;
        this.maxParallelRunTime = maxParallelRunTime;
    }

    private void start(){
        itemList.forEach(item ->{
            executor.execute(() -> {
                synchronized (stopSubmit){
                    if(stopSubmit.get()) return;
                    finishHandleItemSet.add(item);
                }
                action1.call(item);
                latch.countDown();
            });
        });
    }

    public void awaitTermination(){
        start();
        try {
            boolean toZero = latch.await(itemList.size() >
                    maxParallelRunTime ? maxParallelRunTime : itemList.size(), TimeUnit.SECONDS);
            if(!toZero){
                synchronized (stopSubmit){
                    stopSubmit.set(true);
                }
                log.info("threadPool's worker time over, rest work count :{}, main thread run them now.", itemList.size() - finishHandleItemSet.size() );
                AtomicInteger mainRunCount = new AtomicInteger(0);
                itemList.stream().filter(item -> !finishHandleItemSet.contains(item)).forEach(item ->{
                    log.info("main thread run work now: start:{}", mainRunCount.incrementAndGet());
                    action1.call(item);
                });
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
