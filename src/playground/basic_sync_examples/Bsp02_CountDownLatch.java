package playground.basic_sync_examples;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class Bsp02_CountDownLatch
{
    private static class Task implements  Runnable
    {
        private final String name;
        private final CountDownLatch latch;

        public Task(String name, CountDownLatch latch)
        {
            this.name = name;
            this.latch = latch;
        }

        @Override
        public void run()
        {
            System.out.println("Bereit: " + name );
            try {
                TimeUnit.MILLISECONDS.sleep( ThreadLocalRandom.current().nextInt(1000));

                latch.countDown();
                latch.await();  // Wartepunkt
                System.out.println("Los: " + name );
                TimeUnit.MILLISECONDS.sleep(ThreadLocalRandom.current().nextInt(1000, 3000));
                System.out.println("Ende " + name);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException
    {
        CountDownLatch latch = new CountDownLatch(3);  // Anzahl der Threads

        new Thread( new Task("Task1", latch)).start();
        new Thread( new Task("Task2", latch)).start();
        new Thread( new Task("Task3", latch)).start();

        System.out.println("Main done");

    }
}
