package com.adam.elevatormanagementsystem.models;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.*;

@Component
public class ThreadPoolExecutorUtil {

    private final Logger logger = LoggerFactory.getLogger(ThreadPoolExecutorUtil.class);

    private final ThreadPoolExecutor threadPoolExecutor;

    public ThreadPoolExecutorUtil() {
        BlockingQueue<Runnable> blockingQueue = new ArrayBlockingQueue(10000);
        threadPoolExecutor = new ThreadPoolExecutor(16, 16, 20, TimeUnit.SECONDS, blockingQueue);
        threadPoolExecutor.setRejectedExecutionHandler((r, executor) ->
        {
            try {
                Thread.sleep(1000);
                logger.error("Exception occurred while adding task, Waiting for some time");
            } catch (InterruptedException e) {
                logger.error("Thread interrupted:  ()", e.getCause());
                Thread.currentThread().interrupt();
            }
            threadPoolExecutor.execute(r);
        });
    }

    public void executeTask(TaskThread taskThread) {
        Future<?> future = threadPoolExecutor.submit(taskThread);

        System.out.println("Number of Active Threads: " + threadPoolExecutor.getActiveCount());
    }

}
