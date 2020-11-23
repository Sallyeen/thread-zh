# Phase 2

## Goal
- 熟料Java多线程模型
- 了解Java并发、锁机制
- 了解synchronized关键字、wait、notify、notifyAll，ReentrantLock、Condition、await、signal，Concurrent包

## Description
- 使用wait、notify实现生产者、消费者模型
- 使用ReentrantLock实现生产者、消费者模型
- 使用BlockingQueue实现生产者、消费者模型
- 生产者每隔600毫秒往队列里写一条消息(produce)，消费者只要队列里有消息就消费(consume)
- 注意每个消费者能等概率的消费，避免死锁产生
- 确保每条消息只被消费一次

## Concept
- 线程状态
- 锁与可重入锁，读写锁
- 常见的线程安全的容器与非线程安全的容器
