package com.woda.practice.solutions;

import com.woda.practice.Consumer;
import com.woda.practice.Producer;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class BlockingQueueSolution {

    public class ProducerImpl extends Producer {

        public ProducerImpl(BlockingQueue<String> queue, int id) {
            super(queue, id);
        }

        @Override
        protected void produce() throws InterruptedException {
            String msg = String.format("Message from Producer %d", this.id);
            System.out.printf("Producer %d produces: %s\n", this.id, msg);
            BlockingQueue<String> blockingQueue = (BlockingQueue<String>) queue;
            blockingQueue.put(msg);
        }

        public void run() {
            while (true) {
                try {
                    produce();
                    TimeUnit.MILLISECONDS.sleep(600);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public class ConsumerImpl extends Consumer {
        public ConsumerImpl(BlockingQueue<String> queue, int id) {
            super(queue, id);
        }

        @Override
        protected void consume() throws InterruptedException {
            BlockingQueue<String> blockingQueue = (BlockingQueue<String>) queue;
            System.out.printf("Consumer %d consumes: %s\n", this.id, blockingQueue.take());
        }

        public void run() {
            while (true) {
                try {
                    consume();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
