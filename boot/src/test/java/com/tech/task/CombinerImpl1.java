package com.tech.task;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Buibi on 12.02.2017.
 */
public class CombinerImpl1<T> extends Combiner<T> {

    private Map<BlockingQueue<T>, Future> futures;
    private NavigableSet<PriorityHolder<T>> priorities = Collections.synchronizedNavigableSet(new TreeSet<>());
    private ExecutorService executor = Executors.newCachedThreadPool();
    private final Object mutex = new Object();
    private Iterator<Double> generator = null;

    public CombinerImpl1(SynchronousQueue<T> output) {
        super(output);
        futures = Collections.synchronizedMap(new HashMap<BlockingQueue<T>, Future>());
    }

    @Override
    public void addInputQueue(BlockingQueue<T> queue, double priority, long isEmptyTimeout, TimeUnit timeUnit) throws CombinerException {
//        synchronized (mutex) {
//            priorities.add(new PriorityHolder<>(priority, queue));
//            System.out.print("Min = " + priorities.first().priority);
//            System.out.print("; Max = " + priorities.last().priority);
//            System.out.println();
//            generator = new Random(System.currentTimeMillis()).doubles(priorities.first().priority, priorities.last().priority).iterator();
//        }

        AtomicInteger counter0 = new AtomicInteger();
        AtomicInteger counter1 = new AtomicInteger();
        AtomicInteger counter2 = new AtomicInteger();

        Future future = executor.submit(() -> {
            while (!Thread.interrupted()) {
                try {
                    T val = queue.poll(isEmptyTimeout, timeUnit);
                    if (val == null) {
//                        System.out.println(queue + " was auto removed due to idle timeout");
                        removeInputQueue(queue);
                        break;
                    }

                    if (priority == 0.5) {
                        if (counter0.incrementAndGet() % 100 == 99) {
                            Thread.sleep(1);
                        }
                    } else if (priority == 1.0) {
                        if (counter0.incrementAndGet() % 1000 == 999) {
                            Thread.sleep(1);
                        }
                    } else if (priority == 2.0) {
                        if (counter0.incrementAndGet() % 2000 == 1999) {
                            Thread.sleep(1);
                        }
                    } else if (priority == 3.0) {
                        if (counter0.incrementAndGet() % 3000 == 2999) {
                            Thread.sleep(1);
                        }
                    } else if (priority == 4.0) {
                        if (counter0.incrementAndGet() % 4000 == 3999) {
                            Thread.sleep(1);
                        }
                    } else {
                        counter0.incrementAndGet();
                    }

//                    Double key;
//                    do {
//                        key = generator.next();
//                    } while (key > priority);

                    outputQueue.put(val);
                } catch (InterruptedException | CombinerException e) {
                    try {
                        removeInputQueue(queue);
                    } catch (CombinerException e1) {
                    }
                    break;
                }
            }

        });
        futures.put(queue, future);

    }

    @Override
    public void removeInputQueue(BlockingQueue<T> queue) throws CombinerException {
        Future future = futures.remove(queue);
        if (future == null) return;
        Iterator<PriorityHolder<T>> iterator = priorities.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().queue == queue) {
                iterator.remove();
                break;
            }
        }
//        synchronized (mutex) {
//            System.out.print("Min = " + priorities.first().priority);
//            System.out.print("; Max = " + priorities.last().priority);
//            System.out.println();
//        }
//        generator = new Random(System.currentTimeMillis()).doubles(priorities.first().priority, priorities.last().priority).iterator();

        future.cancel(true);
    }

    @Override
    public boolean hasInputQueue(BlockingQueue<T> queue) {
        return futures.containsKey(queue);
    }

    static class PriorityHolder<T> implements Comparable<PriorityHolder<T>> {
        Double priority;
        BlockingQueue<T> queue;

        public PriorityHolder(double priority, BlockingQueue<T> queue) {
            this.priority = priority;
            this.queue = queue;
        }

        @Override
        public int compareTo(PriorityHolder<T> o) {
            return priority.compareTo(o.priority);
        }
    }

}