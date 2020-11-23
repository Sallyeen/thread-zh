package com.woda.practice.solutions;

import com.woda.practice.Consumer;
import com.woda.practice.Producer;

import java.util.concurrent.BlockingQueue;

public class BlockingQueueSolution {

    public class ProducerImpl extends Producer {

        public ProducerImpl(BlockingQueue<String> queue, int id) {
            super(queue, id);
        }

        public void run() {

        }
    }

    public class ConsumerImpl extends Consumer {

        public ConsumerImpl(BlockingQueue<String> queue, int id) {
            super(queue, id);
        }

        public void run() {

        }
    }
}
