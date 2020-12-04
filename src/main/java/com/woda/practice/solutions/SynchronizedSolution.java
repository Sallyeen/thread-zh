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
            while (true) {
                try {
                    synchronized (queue) {
                        produce();
                        TimeUnit.MILLISECONDS.sleep(600);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    System.out.println("produce error");
                } finally {
                    System.out.println("Exit production process");
                }
            }
        }
    }


    public class ConsumerImpl extends Consumer {
        public ConsumerImpl(Queue<String> queue, int id) {
            super(queue, id);
        }

        public void run() {
            while (true) {
                try {
                    synchronized (queue) {
                        while (queue.isEmpty()) {
                            System.out.println("The queue is empty and consumption is suspended");
                            queue.wait();
                        }
                        consume();
                        queue.notifyAll();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("consume error");
                } finally {
                    System.out.println("Exit consumption process");
                }
            }
        }
    }
}