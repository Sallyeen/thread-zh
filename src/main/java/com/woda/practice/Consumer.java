package com.woda.practice;

import java.util.Queue;

public abstract class Consumer implements Runnable {

    protected final Queue<String> queue;
    protected final int id;

    protected Consumer(Queue<String> queue, int id) {
        this.queue = queue;
        this.id = id;
    }

    protected void consume() throws InterruptedException {
        System.out.printf("Consumer %d consumes: %s\n", this.id, queue.poll());
    }
}
