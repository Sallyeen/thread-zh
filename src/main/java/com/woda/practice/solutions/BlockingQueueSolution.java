package com.woda.practice.solutions;

import com.woda.practice.Consumer;
import com.woda.practice.Producer;

import java.security.SecureRandom;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BlockingQueueSolution {

    public static class ProducerConsumerTest3 {
        public static void main(String[] args) throws InterruptedException {
            BlockingQueue<String> queue = new LinkedBlockingDeque<>(5);
            ProducerImpl producerImpl1 = new ProducerImpl(queue, 1);
            ProducerImpl producerImpl2 = new ProducerImpl(queue, 2);
            ConsumerImpl consumerImpl1 = new ConsumerImpl(queue, 1);
            ConsumerImpl consumerImpl2 = new ConsumerImpl(queue, 2);
            //定义service，创建可缓存线程池
            ExecutorService service = Executors.newCachedThreadPool();
            //借助executor控制线程的开启与结束
            service.execute(producerImpl1);
            service.execute(producerImpl2);
            service.execute(consumerImpl1);
            service.execute(consumerImpl2);
            //线程结束
            service.shutdown();
        }
    }

    public static class ProducerImpl extends Producer {

        public ProducerImpl(BlockingQueue<String> queue, int id) {
            super(queue, id);
        }

        public void run() {
            try {
                for (int j = 1; j <= 5; j++) {
                    System.out.println("正在生产数据...");
                    String chars = "abcde";
                    String p = IntStream.range(0, 3)
                            .mapToObj(i -> String.valueOf(chars.charAt(new SecureRandom().nextInt(chars.length()))))
                            .collect(Collectors.joining(""));
                    Thread.sleep(600);
                    produce();
                    System.out.println("将数据：" + p + "放入队列...");
                    if (queue.size() >= 5) {
                        System.out.println("放入数据失败：" + p);
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println("退出生产者线程！");
            }
        }
    }

    public static class ConsumerImpl extends Consumer {

        public ConsumerImpl(BlockingQueue<String> queue, int id) {
            super(queue, id);
        }

        public void run() {
            System.out.println("启动消费者线程");
            try {
                if (queue.isEmpty()) {
                    System.out.println("获取数据失败：");
                } else {
                    System.out.println("正从队列获取数据...");
                    consume();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println("退出消费者线程！");
            }
        }
    }
}
