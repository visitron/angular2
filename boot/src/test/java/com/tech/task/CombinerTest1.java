package com.tech.task;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

/**
 * Created by Buibi on 12.02.2017.
 */
public class CombinerTest1 {

    private ExecutorService service = Executors.newCachedThreadPool();

    @Test
    public void test() throws Exception {
        SynchronousQueue<String> listener = new SynchronousQueue<>();
        Combiner<String> combiner = new CombinerImpl1<>(listener);
        Map<String, Integer> statistic = new HashMap<>();
        statistic.put("E1", 0);
        statistic.put("E2", 0);
        statistic.put("E3", 0);
        statistic.put("E4", 0);

        Future listenerFuture = service.submit(() -> {
            while (!Thread.interrupted()) {
                try {
                    String val = listener.take();
                    String[] parts = val.split(" ");
                    statistic.put(parts[0], Integer.valueOf(parts[1]));
//                    System.out.println("Got <- " + val);
                } catch (InterruptedException e) {
//                    System.out.println("Listener's take has been interrupted");
                    statistic.entrySet().forEach(entry -> {
                        System.out.println(entry.getKey() + " " + entry.getValue());
                    });
                    break;
                }
            }
            System.out.println("Listener has been interrupted");
        });

        Future emitterFuture1 = service.submit(() -> {
            BlockingQueue<String> emitter = new ArrayBlockingQueue<String>(100) {
                @Override
                public String toString() {
                    return "Emitter 1";
                }
            };

            try {
                combiner.addInputQueue(emitter, 1, 2, TimeUnit.SECONDS);
            } catch (Combiner.CombinerException e) {
                e.printStackTrace();
            }

            int i = 0;
            while (!Thread.interrupted()) {
                try {
//                    Thread.sleep(100);
                    emitter.put("E1 " + (++i));
//                    System.out.println("Emitter 1 -> " + i);
                } catch (InterruptedException e) {
//                    System.out.println("Emitter's 1 put has been interrupted");
                    break;
                }
            }

            System.out.println("Emitter 1 has been interrupted");
        });

        Future emitterFuture2 = service.submit(() -> {
            BlockingQueue<String> emitter = new ArrayBlockingQueue<String>(100) {
                @Override
                public String toString() {
                    return "Emitter 2";
                }
            };

            try {
                combiner.addInputQueue(emitter, 0.5, 2, TimeUnit.SECONDS);
            } catch (Combiner.CombinerException e) {
                e.printStackTrace();
            }

            int i = 0;
            while (!Thread.interrupted()) {
                try {
//                    Thread.sleep(300);
                    emitter.put("E2 " + (++i));
//                    if (i == 206) {
                    if (false) {
                        combiner.removeInputQueue(emitter);
//                        break;
                    }
//                    System.out.println("Emitter 2 -> " + i);
                } catch (InterruptedException | Combiner.CombinerException e) {
//                    System.out.println("Emitter's 2 put has been interrupted");
                    break;
                }
            }

            System.out.println("Emitter 2 has been interrupted");
        });

        service.submit(() -> {
            try {
                emitterFuture1.get(8, TimeUnit.SECONDS);
            } catch (TimeoutException | InterruptedException | ExecutionException e) {
                emitterFuture1.cancel(true);
            }
        });

        service.submit(() -> {
            try {
                emitterFuture2.get(8, TimeUnit.SECONDS);
            } catch (TimeoutException | InterruptedException | ExecutionException e) {
                emitterFuture2.cancel(true);
            }
        });


        Future emitterFuture3 = service.submit(() -> {
            BlockingQueue<String> emitter = new ArrayBlockingQueue<String>(100) {
                @Override
                public String toString() {
                    return "Emitter 3";
                }
            };

            try {
                combiner.addInputQueue(emitter, 4, 2, TimeUnit.SECONDS);
            } catch (Combiner.CombinerException e) {
                e.printStackTrace();
            }
            int i = 0;
            while (!Thread.interrupted()) {
                try {
//                    Thread.sleep(300);
                    emitter.put("E3 " + (++i));
//                    System.out.println("Emitter 3 -> " + i);
                } catch (InterruptedException e) {
//                    System.out.println("Emitter's 3 put has been interrupted");
                    break;
                }
            }

            System.out.println("Emitter 3 has been interrupted");
        });

        Future emitterFuture4 = service.submit(() -> {
            BlockingQueue<String> emitter = new ArrayBlockingQueue<String>(100) {
                @Override
                public String toString() {
                    return "Emitter 4";
                }
            };

            try {
                combiner.addInputQueue(emitter, 10, 2, TimeUnit.SECONDS);
            } catch (Combiner.CombinerException e) {
                e.printStackTrace();
            }
            int i = 0;
            while (!Thread.interrupted()) {
                try {
                    emitter.put("E4 " + (++i));
//                    System.out.println("Emitter 4 -> " + i);
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
//                    System.out.println("Emitter's 4 put has been interrupted");
                    break;
                }
            }

            System.out.println("Emitter 4 has been interrupted");
        });

        try {
            emitterFuture3.get(3, TimeUnit.SECONDS);
        } catch (TimeoutException e) {
            emitterFuture3.cancel(true);
        }

        try {
            emitterFuture4.get(8, TimeUnit.SECONDS);
        } catch (TimeoutException e) {
            emitterFuture4.cancel(true);
        }

        if (!listenerFuture.isDone()) {
            listenerFuture.cancel(true);
        }

    }

}
