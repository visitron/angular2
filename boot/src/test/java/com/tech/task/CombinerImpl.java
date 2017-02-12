package com.tech.task;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * Created by vsoshyn on 10/02/2017.
 */
public class CombinerImpl<T> extends Combiner<T> {

    private List<InputKey> predictability = new ArrayList<>();
    private PriorityBlockingQueue<T> internalBus = new PriorityBlockingQueue<T>();
    private final List<Input<T>> queues = new ArrayList<>();
    private Random random = new Random(System.currentTimeMillis());
    private Iterator<Double> generator;
    private ExecutorService worker = Executors.newCachedThreadPool();
    private List<Future> futures = new ArrayList<>();

    public CombinerImpl(SynchronousQueue<T> outputQueue) {
        super(outputQueue);
    }

    @Override
    public void addInputQueue(BlockingQueue<T> queue, double priority, long isEmptyTimeout, TimeUnit timeUnit) throws CombinerException {
        queues.add(new Input<>(priority, isEmptyTimeout, timeUnit, queue));
        rebuildPredictability();

        Future f = worker.submit(() -> {
            while (!queues.isEmpty()) {
                try {
                    T data = queue.poll(isEmptyTimeout, timeUnit);
                    if (data == null) {
                        removeInputQueue(queue);
                        return;
                    } else {
                        outputQueue.put(data);
                    }
                } catch (InterruptedException | CombinerException e) {
                    System.out.println("Future has been cancelled");
                }
            }

            System.out.println("Worker has stopped");
        });

        futures.add(f);

    }

    @Override
    public void removeInputQueue(BlockingQueue<T> queue) throws CombinerException {
        synchronized (queues) {
            for (int i = 0; i < queues.size(); i++) {
                if (queues.get(i).queue == queue) {
                    futures.get(i).cancel(true);
                    queues.remove(i);
                    rebuildPredictability();
                    System.out.println(Thread.currentThread().getId() + " ;Queue removed = " + queue);
                    break;
                }
            }
        }
    }

    @Override
    public boolean hasInputQueue(BlockingQueue<T> queue) {
        return queues.stream().anyMatch(input -> input.queue == queue);
    }

    private void rebuildPredictability() {

        synchronized (queues) {

            if (queues.isEmpty()) {
                predictability.clear();
                return;
            }

            if (predictability.isEmpty()) {
                predictability.add(new InputKey(1, 2));
                generator = random.doubles(1, 2).iterator();
                return;
            }

            Double min = queues.stream()
                    .map(input -> input.priority)
                    .min(Double::compareTo)
                    .get();
            Double max = queues.stream()
                    .map(input -> input.priority)
                    .reduce((d1, d2) -> d1 + d2).get();
            generator = random.doubles(min, max + min).iterator();

            predictability.clear();
            Double current = min;
            for (int i = 0; i < queues.size(); i++) {
                predictability.add(new InputKey(current, queues.get(i).priority + current));
                current += queues.get(i).priority;
            }
        }

    }

    private synchronized Integer nextIndex() {
        Double next = generator.next();
        for (int j = 0; j < predictability.size(); j++) {
            if (match(next, predictability.get(j))) {
                return j;
            }
        }
        return -1;
    }

    public void test() {
        Map<Double, Statistic> statistics = queues.stream()
                .collect(Collectors.toMap(o -> o.priority, o -> new Statistic(o.priority)));

        for (int i = 0; i < 10000000; i++) {
            Double next = generator.next();
            for (int j = 0; j < predictability.size(); j++) {
                if (match(next, predictability.get(j))) {
                    statistics.get(queues.get(j).priority).hists++;
                    break;
                }
            }
        }

        int sum = statistics.values().stream().map(s -> s.hists).reduce((s1, s2) -> s1 + s2).get();
        double orig = statistics.values().stream().map(s -> s.priority).reduce((s1, s2) -> s1 + s2).get();
        statistics.values().forEach(statistic -> System.out.println("Priority = "  + statistic.priority
                + "; % = " + statistic.hists * 100 / sum + "; orig % = " + (int) (statistic.priority * 100 / orig)));
    }

    private boolean match(Double value, InputKey key) {
        return value >= key.min && value < key.max;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        predictability.forEach(k -> {
            result.append("min = " + k.min + "; max = " + k.max).append("\r\n");
        });
        return result.toString();
    }

    private static class InputKey {
        double min;
        double max;

        public InputKey(double min, double max) {
            this.min = min;
            this.max = max;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) return false;
            if (!(obj instanceof InputKey)) return false;
            InputKey that = (InputKey) obj;
            System.out.println("Comparator");
            return that.min >= this.min && that.max < this.max;
        }
    }

    private static class Event<T> {
        T event;
        Double priority;

        public Event(T event, Double priority) {
            this.event = event;
            this.priority = priority;
        }
    }

    private static class Input<T> {
        double priority;
        long timeout;
        TimeUnit timeUnit;
        BlockingQueue<T> queue;

        public Input(double priority, long timeout, TimeUnit timeUnit, BlockingQueue<T> queue) {
            this.priority = priority;
            this.timeout = timeout;
            this.timeUnit = timeUnit;
            this.queue = queue;
        }
    }

    private static class Statistic {
        double priority;
        int hists;

        public Statistic(double priority) {
            this.priority = priority;
        }

        @Override
        public String toString() {
            return "Priority: " + priority + "; Hits: " + hists;
        }
    }

}
