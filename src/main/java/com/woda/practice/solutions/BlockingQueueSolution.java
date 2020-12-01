package com.woda.practice.solutions;

import com.woda.practice.Consumer;
import com.woda.practice.Producer;

import java.security.SecureRandom;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BlockingQueueSolution {
static BlockingQueue<String> queue = new LinkedBlockingQueue<>(5);
public static BlockingQueue<String> blockQueue = queue;

    public static class ProducerConsumerTest3 {
        public static void main(String[] args) throws InterruptedException {
            new Thread(new ProducerImpl(queue, 1)).start();
            new Thread(new ProducerImpl(queue, 2)).start();
            new Thread(new ConsumerImpl(queue, 1)).start();
            new Thread(new ConsumerImpl(queue, 2)).start();
        }
    }

    public static class ProducerImpl extends Producer {
        public ProducerImpl(BlockingQueue<String> queue, int id) {
            super(queue, id);
            blockQueue = queue;
        }

        public void run() {
            while (true) {
                try {
                    while (queue.size() <= 5) {
                        String chars = "abcde";
                        String p = IntStream.range(0, 3)
                                .mapToObj(i -> String.valueOf(chars.charAt(new SecureRandom().nextInt(chars.length()))))
                                .collect(Collectors.joining(""));
                        try {
                            TimeUnit.MILLISECONDS.sleep(600);
                            String msg = String.format("Message from Producer %d : ", this.id);
                            System.out.printf("Producer %d produces: %s\n", this.id, msg);
                            blockQueue.put(msg);
                            System.out.println("Product: " + p);
                            blockQueue.put(p);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println("放入数据失败");
                }finally {
                    System.out.println("退出生产者线程！");
                }
            }
        }
    }

    public static class ConsumerImpl extends Consumer {

        public ConsumerImpl(BlockingQueue<String> queue, int id) {
            super(queue, id);
            blockQueue = queue;
        }

        public void run() {
            while (true) {
                try {
                    while (queue != null) {
                        try {
                            System.out.printf("Consumer %d consumes: %s\n", super.id, blockQueue.take());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }
                    System.out.println("消费数据失败");
                }finally {
                    System.out.println("退出消费者线程！");
                }
            }
        }
    }
}
