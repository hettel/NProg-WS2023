package klausur2020.aufgabe2;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

public class Aufgabe2
{
    static class Task implements Runnable
    {
        private final String name;
        private final int sleepMilleSec;

        private final CountDownLatch latch;

        private final CyclicBarrier barrier;


        public Task(String name, int sleepMilleSec, CountDownLatch latch, CyclicBarrier barrier)
        {
            this.name = name;
            this.sleepMilleSec = sleepMilleSec;
            this.latch = latch;
            this.barrier = barrier;
        }

        @Override
        public void run()
        {
            try{
                latch.await();
                System.out.println("Start " + name);

                for(int i=0; i < 5; i++)
                {
                    TimeUnit.MILLISECONDS.sleep( sleepMilleSec);
                    System.out.println(" -> " + name );
                    barrier.await();
                }

                System.out.println("Bye from " + name);
                barrier.await();
            }
            catch (Exception exce)
            {
                exce.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException
    {
        CountDownLatch latch = new CountDownLatch(1);
        CyclicBarrier barrier = new CyclicBarrier(2);

        new Thread( new Task("Task1", 300, latch, barrier) ).start();
        new Thread( new Task("Task2", 500, latch, barrier) ).start();

        System.out.println("main waiting");
        TimeUnit.SECONDS.sleep(1);
        latch.countDown();
        System.out.println("main done");
    }
}
