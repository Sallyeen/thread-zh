package com.woda.practice.solutions;

import com.woda.practice.Consumer;
import com.woda.practice.Producer;

import java.util.Queue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockSolution {

    private final Lock lock = new ReentrantLock(true);
    private final Condition unEmpty = lock.newCondition();

    public class ProducerImpl extends Producer {
        public ProducerImpl(Queue<String> queue, int id) {
            super(queue, id);
        }

        public void run() {
            try {
                while (true) {
                    lock.lock();
                    try {
                        produce();
                        unEmpty.signalAll();

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        System.out.println("produce error");
                    } finally {
                        lock.unlock();
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
            while (true) {
                lock.lock();
                try {
                    while (queue.isEmpty()) {
                        unEmpty.await();
                    }
                    consume();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        }
    }
}
