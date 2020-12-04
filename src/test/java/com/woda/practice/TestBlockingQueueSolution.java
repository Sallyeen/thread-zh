package com.woda.practice;

import com.woda.practice.solutions.BlockingQueueSolution;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TestBlockingQueueSolution {

    BlockingQueueSolution solution = new BlockingQueueSolution();
    private BlockingQueue<String> queue;
    private List<Producer> producers;
    private List<Consumer> consumers;

    @Before
    public void init() throws Exception {
        this.queue = new LinkedBlockingQueue<>();
        this.producers = IntStream.range(0, 4).mapToObj(id -> solution.new ProducerImpl(queue, id))
                .collect(Collectors.toList());
        this.consumers = IntStream.range(0, 5).mapToObj(id -> solution.new ConsumerImpl(queue, id))
                .collect(Collectors.toList());
    }

    @Test
    public void test() throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(producers.size() + consumers.size());
        producers.forEach(executor::submit);
        consumers.forEach(executor::submit);
        Thread.sleep(30000);
    }
}
