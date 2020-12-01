package com.woda.practice.solutions;

import com.woda.practice.Consumer;
import com.woda.practice.Producer;

import java.security.SecureRandom;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SynchronizedSolution {

    public static class ProducerConsumerTest1 {
        public static void main(String[] args) {
            Queue<String> queue = new LinkedList<>();
            new Thread(new ProducerImpl(queue, 1)).start();
            new Thread(new ProducerImpl(queue, 2)).start();
            new Thread(new ConsumerImpl(queue, 1)).start();
            new Thread(new ConsumerImpl(queue, 2)).start();
        }
    }

    public static class ProducerImpl extends Producer {
        public ProducerImpl(Queue<String> queue, int id) {
            super(queue, id);
        }

        public void run() {
            while (true) {
                try {
                    int capacity = 3;
                    synchronized (queue) {
                        while (queue.size() >= capacity) {
                            System.out.println("仓库已满"/*"The warehouse is full and production is suspended"*/);
                            queue.wait();
                        }
                        String chars = "abcde";
                        String p = IntStream.range(0, 3)
                                .mapToObj(i -> String.valueOf(chars.charAt(new SecureRandom().nextInt(chars.length()))))
                                .collect(Collectors.joining(""));
                        TimeUnit.MILLISECONDS.sleep(600);
                        produce();
                        System.out.println("Product: " + p);
                        queue.offer(p);//queue这样存了msg和产品的名字，这样可以吗？不合理的话该怎么改
                        queue.notifyAll();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                    System.out.println("produce error");
                }
            }
        }
    }

    public static class ConsumerImpl extends Consumer {
        public ConsumerImpl(Queue<String> queue, int id) {
            super(queue, id);
        }

        public void run() {
            while (true) {
                try {
                    synchronized (queue) {
                        while (queue.isEmpty()) {
                            System.out.println("仓库已空"/*"The warehouse is empty and consumption is suspended"*/);
                            queue.wait();
                        }
                        consume();
                        queue.notifyAll();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                    System.out.println("consume error");
                }
            }
        }
    }
}