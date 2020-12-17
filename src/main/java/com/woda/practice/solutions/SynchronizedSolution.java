package com.woda.practice.solutions;

import com.woda.practice.Consumer;
import com.woda.practice.Producer;

import java.util.Queue;
import java.util.concurrent.TimeUnit;

public class SynchronizedSolution {

    public class ProducerImpl extends Producer {
        public ProducerImpl(Queue<String> queue, int id) {
            super(queue, id);
        }

        public void run() {
            try {
                while (true) {
                    synchronized (queue) {
                        produce();
                        queue.notifyAll();
                    }
                    TimeUnit.MILLISECONDS.sleep(600);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public class ConsumerImpl extends Consumer {
        public ConsumerImpl(Queue<String> queue, int id) {
            super(queue, id);
        }

        public void run() {
            try {
                while (true) {
                    synchronized (queue) {
                        while (queue.isEmpty()) {
                            queue.wait();
                        }
                        consume();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}