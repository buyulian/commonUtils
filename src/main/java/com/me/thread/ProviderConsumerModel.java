package com.me.thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;

public class ProviderConsumerModel<T> {
    private int threadNum = 4;
    private int queueSize = 10;
    private BlockingQueue<T> blockingQueue;
    private Consumer<T> consumer;
    /**
     * -9 强制停止 0 等待对列清空停止 1 工作
     */
    private volatile int sign = 0;
    private Thread[] threads;

    private AtomicLong dealNum = new AtomicLong(0);

    public void start() {
        blockingQueue = new ArrayBlockingQueue<>(queueSize);
        this.sign = 1;
        threads = new Thread[threadNum];
        for (int i = 0; i < threadNum; i++) {
            threads[i] = new Thread(()->{
                while (true) {
                    if (sign == -9) {
                        return;
                    }
                    if (sign == 0 && blockingQueue.peek() == null) {
                        return;
                    }
                    T take = null;
                    String id = null;
                    try {
                        take = blockingQueue.take();
                        id = take.toString();
                        System.out.println("已取出 " + id);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    consumer.accept(take);
                    long andAdd = dealNum.getAndAdd(1);
                    System.out.println("已处理 " + id + " num " + andAdd);
                }
            });
        }

        for (int i = 0; i < threadNum; i++) {
            threads[i].start();
        }
    }

    public void stop() {
        sign = -9;
        joins();
    }

    public void waitComplete() {
        sign = 0;
        joins();
    }

    private void joins() {
        for (int i = 0; i < threadNum; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("共处理 " + dealNum.get());
    }

    public void addTask(T t) {
        try {
            blockingQueue.put(t);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public ProviderConsumerModel(int threadNum, int queueSize, Consumer<T> consumer) {
        this.threadNum = threadNum;
        this.queueSize = queueSize;
        this.consumer = consumer;
    }

    public void setThreadNum(int threadNum) {
        this.threadNum = threadNum;
    }

    public void setConsumer(Consumer<T> consumer) {
        this.consumer = consumer;
    }
}
