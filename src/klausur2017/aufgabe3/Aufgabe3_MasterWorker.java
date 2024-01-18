package klausur2017.aufgabe3;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Aufgabe3_MasterWorker
{
    static class Task implements Callable<Long>
    {
        private final long max;

        public Task(long max)
        {
            this.max = max;
        }

        @Override
        public Long call() throws Exception
        {
            long sum = 0;
            for(long l=0; l < max; l++)
            {
                double x = ThreadLocalRandom.current().nextDouble();
                double y = ThreadLocalRandom.current().nextDouble();

                if( x*x + y*y < 1 )
                {
                    sum++;
                }
            }
            return sum;
        }
    }

    public static void main(String[] args) throws Exception
    {
        long max = 100_000_000L;

        int numOfProc = Runtime.getRuntime().availableProcessors();
        ExecutorService executor = Executors.newFixedThreadPool(numOfProc);

        List< Future<Long> > futureList = new ArrayList<>();
        for( int i=0; i < numOfProc; i++)
        {
            Future<Long> future = executor.submit( new Task(max) );
            futureList.add( future );
        }

        long sum = 0;
        for( Future<Long> future : futureList )
        {
             sum += future.get();
        }

        executor.shutdown();

        System.out.println("Result " + ((double) sum)/(numOfProc*max));
        System.out.println("Vergleich " + Math.PI/4 );
    }
}
