package com.woda.practice;

import java.util.Queue;

public abstract class Consumer implements Runnable {

    private final Queue<String> queue;
    private final int id;

    protected Consumer(Queue<String> queue, int id) {
        this.queue = queue;
        this.id = id;
    }

    private void consume() {
        System.out.printf("Consumer %d consumes: %s\n", this.id, queue.poll());
    }
}
