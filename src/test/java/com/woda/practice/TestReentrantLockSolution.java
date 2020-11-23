package com.woda.practice;

import com.woda.practice.solutions.ReentrantLockSolution;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TestReentrantLockSolution {

    private final ReentrantLockSolution solution = new ReentrantLockSolution();
    private Queue<String> queue;
    private List<Producer> producers;
    private List<Consumer> consumers;

    @Before
    public void init() throws Exception {
        this.queue = new LinkedList<>();
        this.producers = IntStream.range(0, 3).mapToObj(id -> solution.new ProducerImpl(queue, id))
                .collect(Collectors.toList());
        this.consumers = IntStream.range(0, 5).mapToObj(id -> solution.new ConsumerImpl(queue, id))
                .collect(Collectors.toList());
    }

    @Ignore
    @Test
    public void test() throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(producers.size() + consumers.size());
        consumers.forEach(executor::submit);
        producers.forEach(executor::submit);
        Thread.sleep(30000);
    }
}
