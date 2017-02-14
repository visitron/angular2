package com.tech.task;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * Created by Buibi on 12.02.2017.
 */
public class CombinerTest1 {

    private ExecutorService service = Executors.newCachedThreadPool();
    private SynchronousQueue<String> listener = new SynchronousQueue<>();
    private Combiner<String> combiner = new CombinerImpl1<>(listener);

    @Test
    public void test() throws Exception {
        Map<String, Integer> statistic = new HashMap<>();
        Future listenerFuture = service.submit(() -> {
            while (!Thread.interrupted()) {
                try {
                    String val = listener.take();
                    Integer hits = statistic.get(val);
                    if (hits == null) hits = 0;
                    statistic.put(val, hits + 1);
//                    System.out.println("Got <- " + val);
                } catch (InterruptedException e) {
//                    System.out.println("Listener's take has been interrupted");
                    int total = statistic.values().stream().reduce((i1, i2) -> i1 + i2).get();
                    statistic.entrySet().forEach(entry -> {
                        System.out.println(entry.getKey() + " " + entry.getValue() + "; " + entry.getValue() * 100 / total + "%");
                    });
                    System.out.println("Total = " + total);

                    break;
                }
            }
            System.out.println("Listener has been interrupted");
        });

        CountDownLatch latch = new CountDownLatch(8);

        List<Future<?>> futures = new ArrayList<>();
        futures.add(submitTask("Emitter 1", 0.5, latch));
        futures.add(submitTask("Emitter 2", 1, latch));
        futures.add(submitTask("Emitter 3", 2, latch));
        futures.add(submitTask("Emitter 4", 3, latch));
        futures.add(submitTask("Emitter 5", 4, latch));
        futures.add(submitTask("Emitter 6", 5, latch));
        futures.add(submitTask("Emitter 7", 0.5, latch));
        futures.add(submitTask("Emitter 8", 1, latch));

        for (Future<?> future : futures) {
            service.submit(() -> {
                try {
                    future.get(8, TimeUnit.SECONDS);
                } catch (TimeoutException | InterruptedException | ExecutionException e) {
                    future.cancel(true);
                }
            });
        }

        try {
            listenerFuture.get(9, TimeUnit.SECONDS);
        } catch (TimeoutException e) {
            listenerFuture.cancel(true);
        }


        Thread.sleep(1000);


//        Future emitterFuture1 = service.submit(() -> {
//            BlockingQueue<String> emitter = new ArrayBlockingQueue<String>(100) {
//                @Override
//                public String toString() {
//                    return "Emitter 1";
//                }
//            };
//
//            try {
//                combiner.addInputQueue(emitter, 1, 2, TimeUnit.SECONDS);
//            } catch (Combiner.CombinerException e) {
//                e.printStackTrace();
//            }
//
//            int i = 0;
//            while (!Thread.interrupted()) {
//                try {
//                    emitter.put("E1 " + (++i));
//                } catch (InterruptedException e) {
//                    break;
//                }
//            }
//
//            System.out.println("Emitter 1 has been interrupted");
//        });
//
//        Future emitterFuture2 = service.submit(() -> {
//            BlockingQueue<String> emitter = new ArrayBlockingQueue<String>(100) {
//                @Override
//                public String toString() {
//                    return "Emitter 2";
//                }
//            };
//
//            try {
//                combiner.addInputQueue(emitter, 0.5, 2, TimeUnit.SECONDS);
//            } catch (Combiner.CombinerException e) {
//                e.printStackTrace();
//            }
//
//            int i = 0;
//            while (!Thread.interrupted()) {
//                try {
//                    emitter.put("E2 " + (++i));
//                } catch (InterruptedException e) {
//                    break;
//                }
//            }
//
//            System.out.println("Emitter 2 has been interrupted");
//        });
//
//        service.submit(() -> {
//            try {
//                emitterFuture1.get(8, TimeUnit.SECONDS);
//            } catch (TimeoutException | InterruptedException | ExecutionException e) {
//                emitterFuture1.cancel(true);
//            }
//        });
//
//        service.submit(() -> {
//            try {
//                emitterFuture2.get(8, TimeUnit.SECONDS);
//            } catch (TimeoutException | InterruptedException | ExecutionException e) {
//                emitterFuture2.cancel(true);
//            }
//        });


//        Future emitterFuture3 = service.submit(() -> {
//            BlockingQueue<String> emitter = new ArrayBlockingQueue<String>(100) {
//                @Override
//                public String toString() {
//                    return "Emitter 3";
//                }
//            };
//
//            try {
//                combiner.addInputQueue(emitter, 4, 2, TimeUnit.SECONDS);
//            } catch (Combiner.CombinerException e) {
//                e.printStackTrace();
//            }
//            int i = 0;
//            while (!Thread.interrupted()) {
//                try {
////                    Thread.sleep(300);
//                    emitter.put("E3 " + (++i));
////                    System.out.println("Emitter 3 -> " + i);
//                } catch (InterruptedException e) {
////                    System.out.println("Emitter's 3 put has been interrupted");
//                    break;
//                }
//            }
//
//            System.out.println("Emitter 3 has been interrupted");
//        });
//
//        Future emitterFuture4 = service.submit(() -> {
//            BlockingQueue<String> emitter = new ArrayBlockingQueue<String>(100) {
//                @Override
//                public String toString() {
//                    return "Emitter 4";
//                }
//            };
//
//            try {
//                combiner.addInputQueue(emitter, 10, 2, TimeUnit.SECONDS);
//            } catch (Combiner.CombinerException e) {
//                e.printStackTrace();
//            }
//            int i = 0;
//            while (!Thread.interrupted()) {
//                try {
//                    emitter.put("E4 " + (++i));
////                    System.out.println("Emitter 4 -> " + i);
//                    Thread.sleep(3000);
//                } catch (InterruptedException e) {
////                    System.out.println("Emitter's 4 put has been interrupted");
//                    break;
//                }
//            }
//
//            System.out.println("Emitter 4 has been interrupted");
//        });
//
//        try {
//            emitterFuture3.get(3, TimeUnit.SECONDS);
//        } catch (TimeoutException e) {
//            emitterFuture3.cancel(true);
//        }
//
//        try {
//            emitterFuture4.get(8, TimeUnit.SECONDS);
//        } catch (TimeoutException e) {
//            emitterFuture4.cancel(true);
//        }


    }

    private Future<?> submitTask(String name, double priority, CountDownLatch latch) {
        return service.submit(() -> {
            String name0 = name + "; priority = " + priority;
            BlockingQueue<String> emitter = new ArrayBlockingQueue<String>(100) {
                @Override
                public String toString() {
                    return name0;
                }
            };

            try {
                combiner.addInputQueue(emitter, priority, 2, TimeUnit.SECONDS);
            } catch (Combiner.CombinerException e) {
                e.printStackTrace();
            }

            latch.countDown();

            while (!Thread.interrupted()) {
                try {
                    emitter.put(name0);
                } catch (InterruptedException e) {
                    break;
                }
            }

//            System.out.println(name0 + " has been interrupted");
        });
    }

}
