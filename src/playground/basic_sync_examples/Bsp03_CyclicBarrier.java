package playground.basic_sync_examples;

import java.util.concurrent.*;

public class Bsp03_CyclicBarrier
{
    private static class Task implements  Runnable
    {
        private final String name;
        private final CyclicBarrier barrier;

        public Task(String name, CyclicBarrier barrier)
        {
            this.name = name;
            this.barrier = barrier;
        }

        @Override
        public void run()
        {
            System.out.println("Bereit: " + name );
            try {
                for(int i = 0; i < 5; i++)
                {
                    barrier.await();
                    System.out.println("Beginn " + name);
                    TimeUnit.MILLISECONDS.sleep( ThreadLocalRandom.current().nextInt(500, 2000));
                    System.out.println("Ende " + name );
                }
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException
    {
        CyclicBarrier barrier = new CyclicBarrier(3);

        new Thread( new Task("Task 1" , barrier) ).start();
        new Thread( new Task("Task 2" , barrier) ).start();
        new Thread( new Task("Task 3" , barrier) ).start();

        System.out.println("Main done");

    }
}
