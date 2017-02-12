package com.tech.task;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Buibi on 12.02.2017.
 */
public class CombinerImpl1<T> extends Combiner<T> {

    private Map<BlockingQueue<T>, Future> futures;
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    private ExecutorService executor = Executors.newCachedThreadPool();

    public CombinerImpl1(SynchronousQueue<T> output) {
        super(output);
        futures = Collections.synchronizedMap(new HashMap<BlockingQueue<T>, Future>());
    }

    @Override
    public void addInputQueue(BlockingQueue<T> queue, double priority, long isEmptyTimeout, TimeUnit timeUnit) throws CombinerException {
        Future future = executor.submit(() -> {
            while (!Thread.interrupted()) {
                try {
                    T val = queue.poll(isEmptyTimeout, timeUnit);
                    if (val == null) {
                        System.out.println("Emitter was auto removed due to idle timeout");
                        removeInputQueue(queue);
                        break;
                    }
                    outputQueue.put(val);
                } catch (InterruptedException | CombinerException e) {
//                    e.printStackTrace();
                    try {
                        removeInputQueue(queue);
                    } catch (CombinerException e1) {
//                        e1.printStackTrace();
                    }
                    break;
                }
            }
        });
        futures.put(queue, future);

    }

    @Override
    public void removeInputQueue(BlockingQueue<T> queue) throws CombinerException {
        Future future = futures.get(queue);
        if (future == null) return;
        future.cancel(true);
        futures.remove(queue);
    }

    @Override
    public boolean hasInputQueue(BlockingQueue<T> queue) {
        return futures.containsKey(queue);
    }
}
