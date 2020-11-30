package com.woda.practice.solutions;

import com.woda.practice.Consumer;
import com.woda.practice.Producer;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockSolution {

    private static final Lock lock = new ReentrantLock(true);
    //静态上下文调用非静态方法问题
    private static Condition condition = lock.newCondition();

    public static class ProducerConsumerTest2 {
        public static void main(String[] args) {
            Queue<String> queue = new LinkedList<>();
            //定义了四个线程：2producer，2customer
            new Thread(new ProducerImpl(queue, 1)).start();
            new Thread(new ProducerImpl(queue, 2)).start();
            new Thread(new ProducerImpl(queue, 3)).start();
            new Thread(new ProducerImpl(queue, 4)).start();
            new Thread(new ConsumerImpl(queue, 1)).start();
            new Thread(new ConsumerImpl(queue, 2)).start();
            new Thread(new ConsumerImpl(queue, 3)).start();
            new Thread(new ConsumerImpl(queue, 4)).start();
        }
    }

    public static class ProducerImpl extends Producer {

        public ProducerImpl(Queue<String> queue, int id) {
            super(queue, id);
        }

        public void prd() {
            int capacity = 5;
            lock.lock();
            try {
                while (queue.size() >= capacity) {
                    System.out.println("The warehouse is full and production is suspended");
                    try {
                        condition.await();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                /*String chars = "abcde";
                String p = IntStream.range(0, 3)
                        .mapToObj(i -> String.valueOf(chars.charAt(new SecureRandom().nextInt(chars.length()))))
                        .collect(Collectors.joining(""));
                TimeUnit.MILLISECONDS.sleep(600);
                produce();
                System.out.println("Product: " + p);
                queue.offer(p);*/
                produce();
                condition.signalAll();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }

        public void run() {
            while (true) {
                try {
                    prd();
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("product error");
                }
            }

        }
    }

    public static class ConsumerImpl extends Consumer {

        public ConsumerImpl(Queue<String> queue, int id) {
            super(queue, id);
        }

        public void csm() throws InterruptedException {
            lock.lock();
            try {
                while (queue.isEmpty()) {
                    System.out.println("The warehouse is empty and consumption is suspended");
                    try {
                        condition.await();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                consume();
                condition.signalAll();
            } finally {
                lock.unlock();
            }
        }

        public void run() {
            while (true) {
                try {
                    csm();
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("consume error");
                }
            }
        }
    }

}
