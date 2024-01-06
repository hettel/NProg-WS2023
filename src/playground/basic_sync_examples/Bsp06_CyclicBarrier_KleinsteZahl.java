package playground.basic_sync_examples;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Bsp06_CyclicBarrier_KleinsteZahl
{
    static AtomicInteger scratch = new AtomicInteger(0);

    private static class Task1 implements  Runnable
    {
        private final CyclicBarrier barrier;
        private final int[] liste;

        public Task1(int[] liste, CyclicBarrier barrier)
        {
            this.liste = liste;
            this.barrier = barrier;
        }
        @Override
        public void run()
        {
            int index = 0;
            try {
                scratch.set( liste[index] );

                while(true) {
                    barrier.await();

                    barrier.await();

                    while ( liste[index] < scratch.get() ) {
                        index++;
                    }

                    if ( liste[index] == scratch.get() ) {
                        System.out.println("Found " + liste[index] + " by Task1");
                        break;
                    } else {
                        scratch.set(liste[index]);
                    }

                }

            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }

    private static class Task2 implements  Runnable
    {
        private final int[] liste;
        private final CyclicBarrier barrier;

        public Task2(int[] liste, CyclicBarrier barrier)
        {
            this.liste = liste;
            this.barrier = barrier;
        }

        @Override
        public void run()
        {
            int index = 0;
            try {
                while(true) {
                    barrier.await();

                    while ( liste[index] < scratch.get() ) {
                        index++;
                    }

                    if ( liste[index] == scratch.get() ) {
                        System.out.println("Found " + liste[index] + " Task2");
                        break;
                    } else {
                        scratch.set(liste[index]);
                    }

                    barrier.await();
                }

            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException
    {

        int[] liste1 = {2,23,24,31,37,42,45,60};
        int[] liste2 = {3,17,19,27,39,42,46,70};


        CyclicBarrier barrier = new CyclicBarrier(2);

        new Thread( new Task1( liste1, barrier) ).start();
        new Thread( new Task2( liste2, barrier) ).start();

        System.out.println("Main done");

    }
}
