package hone.maintenance.boot;

/**
 * Created by vsoshyn on 09/02/2017.
 */
public class Tester {

    private int counter = 0;

    public static void main(String[] args) throws InterruptedException {

        Tester testee = new Tester();

        Thread t1 = new Thread(() -> {
            int i;
//            try {
                for (i = 0; i <= 1000000; i++) {
                    synchronized (testee) {
//                        Thread.sleep(500);
                        testee.increment();
//                        System.out.println(Thread.currentThread().getName());
                    }
                }
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
        }, "first");

        Thread t2 = new Thread(() -> {
            int i;
//            try {
                for (i = 0; i <= 1000000; i++) {
//                    synchronized (testee) {
//                        Thread.sleep(300);
                        testee.increment();
//                        System.out.println(Thread.currentThread().getName());
//                    }
                }
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
        }, "second");

        Thread t3 = new Thread(() -> {
            int i;
            for (i = 0; i <= 1000000; i++) {
//                synchronized (testee) {
//                    Thread.sleep(500);
                    testee.decrement();
//                    System.out.println(Thread.currentThread().getName());
//                }
            }
        }, "third");

        t1.start();
        t2.start();
        t3.start();


        t1.join();
        t2.join();
        t3.join();

        System.out.println("Result = " + testee.counter);

    }

    private void increment() {
        counter++;
    }

    private void decrement() {
        counter--;
    }

}
