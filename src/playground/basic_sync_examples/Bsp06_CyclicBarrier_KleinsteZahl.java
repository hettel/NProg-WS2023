package playground.basic_sync_examples;

import java.util.Arrays;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class Bsp06_CyclicBarrier_KleinsteZahl
{
    static AtomicInteger scratch = new AtomicInteger(0);

    static AtomicBoolean found = new AtomicBoolean(false);

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

                    // Task 2 sucht nächste Zahl

                    barrier.await();

                    while ( liste[index] < scratch.get() ) {
                        index++;
                    }

                    if ( liste[index] == scratch.get() ) {
                        found.set(true);
                    } else {
                        scratch.set(liste[index]);
                    }

                    barrier.await();
                    if( found.get() )
                    {
                        System.out.println("Task 1 => " + liste[index]  +  " at " + index );
                        break;
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
                        found.set(true);
                    } else {
                        scratch.set(liste[index]);
                    }

                    barrier.await();

                    // Task 1 sucht nächste Zahl

                    barrier.await();
                    if( found.get() )
                    {
                        System.out.println("Task 2 => " + liste[index] +  " at " + index );
                        break;
                    }
                }

            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException
    {

        int[] liste1 = new int[100_000];
        int[] liste2 = new int[100_000];

        // Initialisiere Arrays mit Zufallszahlen
        Arrays.parallelSetAll( liste1, i -> ThreadLocalRandom.current().nextInt( ) );
        Arrays.parallelSetAll( liste2, i -> ThreadLocalRandom.current().nextInt( ) );

        // Stelle sicher, dass mindestens eine gemeinsame Zahl existiert
        liste1[0] = Integer.MAX_VALUE;
        liste2[0] = Integer.MAX_VALUE;

        // Sortiere Arrays
        Arrays.parallelSort( liste1 );
        Arrays.parallelSort( liste2 );

        CyclicBarrier barrier = new CyclicBarrier(2);

        new Thread( new Task1( liste1, barrier) ).start();
        new Thread( new Task2( liste2, barrier) ).start();

        System.out.println("Main done");
    }
}
