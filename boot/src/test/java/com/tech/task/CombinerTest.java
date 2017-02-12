package com.tech.task;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by vsoshyn on 10/02/2017.
 */
public class CombinerTest {

    @Test
    public void test() throws Exception {

//        Iterator<Integer> random = new Random().ints(0, 100).iterator();
//        for (int i = 0; i < 1000; i++) {
//            System.out.println(random.next());
//        }
//

        List<BlockingQueue<Integer>> queues = Arrays.asList(
                new ArrayBlockingQueue<Integer>(100),
                new ArrayBlockingQueue<Integer>(50),
                new ArrayBlockingQueue<Integer>(200));

        SynchronousQueue<Integer> listener = new SynchronousQueue<>();
        Combiner<Integer> combiner = new CombinerImpl<>(listener);
        combiner.addInputQueue(queues.get(0), 1.5, 5, TimeUnit.SECONDS);
        combiner.addInputQueue(queues.get(1), 1, 5, TimeUnit.SECONDS);
        combiner.addInputQueue(queues.get(2), 5, 100, TimeUnit.SECONDS);

        new Thread(() -> {
            BlockingQueue<Integer> q = queues.get(0);
            for (int i = 0; i < 100; i++) {
                try {
                    q.put(i);
                    System.out.println("Put " + i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(() -> {
            while (true) {
                try {
                    System.out.println(listener.take());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();


//        System.out.println(combiner);
//        ((CombinerImpl) combiner).test();


        assertTrue(combiner.hasInputQueue(queues.get(1)));
        assertFalse(combiner.hasInputQueue(new ArrayBlockingQueue<Integer>(100)));

        combiner.removeInputQueue(queues.get(1));

        assertFalse(combiner.hasInputQueue(queues.get(1)));

        Thread t = new Thread(() -> {
            try {
                Thread.sleep(15000);
                assertFalse(combiner.hasInputQueue(queues.get(0)));
                assertFalse(combiner.hasInputQueue(queues.get(1)));
                assertTrue(combiner.hasInputQueue(queues.get(2)));
                combiner.removeInputQueue(queues.get(2));
                assertFalse(combiner.hasInputQueue(queues.get(2)));
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (Combiner.CombinerException e) {
                e.printStackTrace();
            }
        });

        t.start();
        t.join();

        Thread.sleep(5000);

//        System.out.println(combiner);

    }

    @Test
    public void test2() throws Exception {
        SynchronousQueue<Integer> listener = new SynchronousQueue<>();
        Combiner<Integer> combiner = new CombinerImpl<>(listener);

        Thread tListener = new Thread(() -> {
            try {
                Integer payload = listener.take();
                System.out.println("Payload " + payload);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        tListener.start();

        Thread tSender1 = new Thread(() -> {
            BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(100);
            try {
                combiner.addInputQueue(queue, 1, 2, TimeUnit.SECONDS);
                Thread.sleep(1000);
            } catch (Combiner.CombinerException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (int i = 0; i < 100; i++) {
                try {
                    queue.put(i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        tSender1.start();

        Thread tSender2 = new Thread(() -> {
            ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(100);
            try {
                combiner.addInputQueue(queue, 2, 2, TimeUnit.SECONDS);
            } catch (Combiner.CombinerException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (int i = 100; i < 200; i++) {
                try {
                    queue.put(i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        tSender2.start();

        Thread tSender3 = new Thread(() -> {
            BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(100);
            try {
                combiner.addInputQueue(queue, 3, 2, TimeUnit.SECONDS);
            } catch (Combiner.CombinerException e) {
                e.printStackTrace();
            }

            for (int i = 200; i < 300; i++) {
                try {
                    Thread.sleep(1000);
                    queue.put(i);
                    if (i == 110) {
                        combiner.removeInputQueue(queue);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (Combiner.CombinerException e) {
                    e.printStackTrace();
                }
            }
        });
        tSender3.start();

        tSender1.join();
        tSender2.join();
        tSender3.join();

    }

}
