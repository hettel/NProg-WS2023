package klausur2017.aufgabe1;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class Aufgabe1
{
    static class Task12 implements Runnable
    {
        private final String name;
        private final CountDownLatch latch;
        private final CyclicBarrier barrier;

        public Task12(String name, CountDownLatch latch, CyclicBarrier barrier)
        {
            this.name = name;
            this.latch = latch;
            this.barrier = barrier;
        }

        @Override
        public void run()
        {
            try {
                latch.await();

                for ( int i=0; i<3; i++ )
                {
                    long sleepTime = 100 + ThreadLocalRandom.current().nextInt(1000);
                    TimeUnit.MILLISECONDS.sleep( sleepTime );
                    System.out.println(" -> " + name );
                    barrier.await();
                }

                System.out.println("Bye bye from " + name );
            }
            catch (Exception exce)
            {
                exce.printStackTrace();
            }
        }
    }

    static class Task3 implements Runnable
    {
        private final String name;
        private final CountDownLatch latch;

        public Task3(String name, CountDownLatch latch)
        {
            this.name = name;
            this.latch = latch;
        }

        @Override
        public void run()
        {
            try {
                latch.await();
                System.out.println(" -> " + name );
            }
            catch (Exception exce)
            {
                exce.printStackTrace();;
            }
        }
    }

    public static void main(String[] args) throws InterruptedException
    {
        CountDownLatch latchForTask_1_and_2 = new CountDownLatch(7);
        CountDownLatch latchForTask_3 = new CountDownLatch(3);
        CyclicBarrier  barrier = new CyclicBarrier(2, () -> latchForTask_3.countDown() );

        Thread th1 = new Thread( new Task12("Task1", latchForTask_1_and_2, barrier ));
        Thread th2 = new Thread( new Task12("Task2", latchForTask_1_and_2, barrier ));
        Thread th3 = new Thread( new Task3("Task3", latchForTask_3));

        th1.start(); th2.start(); th3.start();

        System.out.println("Countdown");
        for(int i = 7; i > 0; i--)
        {
            System.out.println( i );
            latchForTask_1_and_2.countDown();
        }

        th1.join(); th2.join(); th3.join();
        System.out.println("main done");
    }
}
