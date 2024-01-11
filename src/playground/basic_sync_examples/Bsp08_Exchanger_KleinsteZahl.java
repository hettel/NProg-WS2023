package playground.basic_sync_examples;

import java.util.Arrays;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Exchanger;
import java.util.concurrent.ThreadLocalRandom;

public class Bsp08_Exchanger_KleinsteZahl
{
    static class Task implements Runnable
    {
        private final Exchanger<Integer> exchanger;

        private final int[] liste;
        private final String name;

        public Task(int[] liste, Exchanger<Integer> exchanger, String name)
        {
            this.exchanger = exchanger;
            this.liste = liste;
            this.name = name;
        }

        @Override
        public void run()
        {
            try {
                int index = 0;

                while ( true ) {
                    int myValue = liste[index];
                    int otherValue = exchanger.exchange(myValue);

                    if ( myValue == otherValue ) {
                        System.out.println(name + " value found " + myValue);
                        break;
                    }

                    while ( liste[index] < otherValue )
                        index++;

                }
            }
            catch (InterruptedException exce)
            {
                exce.printStackTrace();
            }
        }
    }


    public static void main(String[] args)
    {
        int[] liste1 = new int[100_000];
        int[] liste2 = new int[100_000];

        // Initialisiere Arrays mit Zufallszahlen
        Arrays.parallelSetAll( liste1, i -> ThreadLocalRandom.current().nextInt( ) );
        Arrays.parallelSetAll( liste2, i -> ThreadLocalRandom.current().nextInt( ) );

        // Stelle sicher, dass mindestens eine Zahl in beiden Arrays vorkommt
        liste1[0] = Integer.MAX_VALUE;
        liste2[0] = Integer.MAX_VALUE;

        // Sortiere Arrays
        Arrays.parallelSort( liste1 );
        Arrays.parallelSort( liste2 );

        Exchanger<Integer> exchanger = new Exchanger<>();

        new Thread( new Task(liste1, exchanger, "Task1") ).start();
        new Thread( new Task(liste2, exchanger, "Task2") ).start();

        System.out.println("Main done");
    }
}
