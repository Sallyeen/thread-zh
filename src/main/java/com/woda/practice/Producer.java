package com.woda.practice;

import java.util.Queue;

public abstract class Producer implements Runnable {

    private final Queue<String> queue;
    private final int id;

    protected Producer(Queue<String> queue, int id) {
        this.queue = queue;
        this.id = id;
    }

    private void produce() {
        String msg = String.format("Message from Producer %d", this.id);
        System.out.printf("Producer %d produces: %s\n", this.id, msg);
        queue.offer(msg);
    }
}
