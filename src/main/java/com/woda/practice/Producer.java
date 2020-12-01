package com.woda.practice;

import java.util.Queue;

public abstract class Producer implements Runnable {

    protected final Queue<String> queue;
    protected final int id;

    protected Producer(Queue<String> queue, int id) {
        this.queue = queue;
        this.id = id;
    }

    protected void produce() throws InterruptedException {
        String msg = String.format("Message from Producer %d : ", this.id);
        System.out.printf("Producer %d produces: %s\n", this.id, msg);
        queue.offer(msg);
    }

    /*public void stop() {
        boolean isRunning = false;
    }*/

}
