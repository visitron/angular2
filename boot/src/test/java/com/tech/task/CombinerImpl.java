package com.tech.task;

import java.util.*;
import java.util.concurrent.*;

/**
 * Created by vsoshyn on 10/02/2017.
 */
public class CombinerImpl<T> extends Combiner<T> {

    private ScheduledExecutorService schedule = Executors.newScheduledThreadPool(5);
    private ExecutorService executor = Executors.newSingleThreadExecutor();
    private double total;
    private Iterator<Integer> random = new Random(System.currentTimeMillis()).ints(1, 101).iterator();
    private Map<Double, BlockingQueue<T>> queues = new HashMap<>();
    private Map<Double, ArrayList<BlockingQueue<T>>> orderedQueues = new HashMap<>(100);
    private LinkedHashMap<Integer, Double> orderHelper = new LinkedHashMap<>(100);

    public CombinerImpl(SynchronousQueue<T> outputQueue) {
        super(outputQueue);
        executor.execute(() -> {

            Double place0 = orderHelper.get(random.next());
            List<BlockingQueue<T>> froms = orderedQueues.get(place0);

            froms.forEach(from -> {
                try {
                    transfer(from);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });

        });
    }

    @Override
    public void addInputQueue(BlockingQueue<T> queue, double priority, long isEmptyTimeout, TimeUnit timeUnit) throws CombinerException {
        schedule.schedule(() -> {
            if (queue.isEmpty()) {
                try {
                    removeInputQueue(queue);
                } catch (CombinerException e) {
                    e.printStackTrace();
                }
            }
        }, isEmptyTimeout, timeUnit);

        total += priority;
        queues.put(priority, queue);
        rebuildIndexes();

//        Integer place = (int) (priority / total);

//        orderedQueues.put(place, Collections.singletonList(queue));



    }

    @Override
    public void removeInputQueue(BlockingQueue<T> queue) throws CombinerException {

    }

    @Override
    public boolean hasInputQueue(BlockingQueue<T> queue) {
        return false;
    }

    private void rebuildIndexes() {
        orderHelper.values().clear();

        orderedQueues.forEach((key, value) -> {
            orderHelper.put((int) (key / total), key);
        });

        orderHelper.values().

        return orderedQueues.get(place);
    }

    private void transfer(BlockingQueue<T> from) throws InterruptedException {
        outputQueue.offer(from.take());
    }
}
