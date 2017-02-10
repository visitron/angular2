package com.tech.task;

import org.junit.Test;

import java.util.Iterator;
import java.util.Random;

/**
 * Created by vsoshyn on 10/02/2017.
 */
public class CombinerTest {

    @Test
    public void test() throws Exception {

        Iterator<Integer> random = new Random().ints(0, 100).iterator();
        for (int i = 0; i < 1000; i++) {
            System.out.println(random.next());
        }

//        Combiner<Integer> combiner = new CombinerImpl<>(new SynchronousQueue<>());

    }

}
