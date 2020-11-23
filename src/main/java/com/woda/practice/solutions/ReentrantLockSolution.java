package com.woda.practice.solutions;

import com.woda.practice.Consumer;
import com.woda.practice.Producer;

import java.util.Queue;

public class ReentrantLockSolution {

    public class ProducerImpl extends Producer {

        public ProducerImpl(Queue<String> queue, int id) {
            super(queue, id);
        }

        public void run() {

        }
    }

    public class ConsumerImpl extends Consumer {

        public ConsumerImpl(Queue<String> queue, int id) {
            super(queue, id);
        }

        public void run() {

        }
    }
}
