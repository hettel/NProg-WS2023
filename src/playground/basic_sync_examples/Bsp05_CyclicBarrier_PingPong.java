package playground.basic_sync_examples;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class Bsp05_CyclicBarrier_PingPong
{
    private static class Ping implements  Runnable
    {
        private final CyclicBarrier barrier;

        public Ping(CyclicBarrier barrier)
        {
            this.barrier = barrier;
        }

        @Override
        public void run()
        {
            try {
                while(true)
                {
                    TimeUnit.MILLISECONDS.sleep( ThreadLocalRandom.current().nextInt(100, 500));
                    System.out.println("ping");
                    barrier.await();
                    // Pong ist dran
                    barrier.await();
                }
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }

    private static class Pong implements  Runnable
    {
        private final CyclicBarrier barrier;

        public Pong(CyclicBarrier barrier)
        {
            this.barrier = barrier;
        }

        @Override
        public void run()
        {
            try {
                while(true)
                {
                    // ping ist dran
                    barrier.await();
                    TimeUnit.MILLISECONDS.sleep( ThreadLocalRandom.current().nextInt(100, 500));
                    System.out.println("pong");
                    barrier.await();
                }
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException
    {

        CyclicBarrier barrier = new CyclicBarrier(2);

        new Thread( new Ping( barrier) ).start();
        new Thread( new Pong( barrier) ).start();

        System.out.println("Main done");

    }
}
