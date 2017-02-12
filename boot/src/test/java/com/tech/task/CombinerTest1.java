package com.tech.task;

import org.junit.Test;

import java.util.concurrent.*;

/**
 * Created by Buibi on 12.02.2017.
 */
public class CombinerTest1 {

    private ExecutorService service = Executors.newCachedThreadPool();

    @Test
    public void test() throws Exception {
        SynchronousQueue<Integer> listener = new SynchronousQueue<>();
        Combiner<Integer> combiner = new CombinerImpl1<>(listener);

        Future listenerFuture = service.submit(() -> {
            while (!Thread.interrupted()) {
                try {
                    Integer val = listener.take();
                    System.out.println("Got <- " + val);
                } catch (InterruptedException e) {
                    System.out.println("Listener's take has been interrupted");
                    break;
                }
            }
            System.out.println("Listener has been interrupted");
        });

        Future emitterFuture1 = service.submit(() -> {
            BlockingQueue<Integer> emitter = new ArrayBlockingQueue<Integer>(100);
            try {
                combiner.addInputQueue(emitter, 1, 2, TimeUnit.SECONDS);
            } catch (Combiner.CombinerException e) {
                e.printStackTrace();
            }
            int i = 0;
            while (!Thread.interrupted()) {
                try {
                    Thread.sleep(100);
                    emitter.put(++i);
                    System.out.println("Emitter 1 -> " + i);
                } catch (InterruptedException e) {
                    System.out.println("Emitter's 1 put has been interrupted");
                    break;
                }
            }

            System.out.println("Emitter 1 has been interrupted");
        });

        Future emitterFuture2 = service.submit(() -> {
            BlockingQueue<Integer> emitter = new ArrayBlockingQueue<Integer>(100);
            try {
                combiner.addInputQueue(emitter, 1, 2, TimeUnit.SECONDS);
            } catch (Combiner.CombinerException e) {
                e.printStackTrace();
            }
            int i = 200;
            while (!Thread.interrupted()) {
                try {
                    Thread.sleep(300);
                    emitter.put(++i);
                    if (i == 206) {
                        combiner.removeInputQueue(emitter);
//                        break;
                    }
                    System.out.println("Emitter 2 -> " + i);
                } catch (InterruptedException | Combiner.CombinerException e) {
                    System.out.println("Emitter's 1 put has been interrupted");
                    break;
                }
            }

            System.out.println("Emitter 1 has been interrupted");
        });

        try {
            emitterFuture1.get(8, TimeUnit.SECONDS);
        } catch (TimeoutException e) {
            emitterFuture1.cancel(true);
        }

        try {
            emitterFuture2.get(8, TimeUnit.SECONDS);
        } catch (TimeoutException e) {
            emitterFuture2.cancel(true);
        }

        Future emitterFuture3 = service.submit(() -> {
            BlockingQueue<Integer> emitter = new ArrayBlockingQueue<Integer>(100);
            try {
                combiner.addInputQueue(emitter, 1, 2, TimeUnit.SECONDS);
            } catch (Combiner.CombinerException e) {
                e.printStackTrace();
            }
            int i = 200;
            while (!Thread.interrupted()) {
                try {
                    Thread.sleep(300);
                    emitter.put(++i);
                    System.out.println("Emitter 3 -> " + i);
                } catch (InterruptedException e) {
                    System.out.println("Emitter's 3 put has been interrupted");
                    break;
                }
            }

            System.out.println("Emitter 3 has been interrupted");
        });

        Future emitterFuture4 = service.submit(() -> {
            BlockingQueue<Integer> emitter = new ArrayBlockingQueue<Integer>(100);
            try {
                combiner.addInputQueue(emitter, 1, 2, TimeUnit.SECONDS);
            } catch (Combiner.CombinerException e) {
                e.printStackTrace();
            }
            int i = 400;
            while (!Thread.interrupted()) {
                try {
                    emitter.put(++i);
                    System.out.println("Emitter 4 -> " + i);
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    System.out.println("Emitter's 4 put has been interrupted");
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
