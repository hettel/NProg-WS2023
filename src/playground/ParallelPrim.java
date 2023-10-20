package playground;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ParallelPrim
{
    private static class Task implements Runnable
    {
        private final int start, end;
        private int result;

        Task(int start, int end)
        {
            this.start = start;
            this.end = end;
        }

        public int getResult()
        {
            return result;
        }

        @Override public void run()
        {

            for (int i = start; i < end; i++)
            {
                if(BigInteger.valueOf(i).isProbablePrime(1000) )
                {
                    result++;
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException
    {
        long startTime = System.currentTimeMillis();

        int numProc = 20; // Runtime.getRuntime().availableProcessors();
        int startRange = 1_000_000;
        int endRange = 2_000_000;

        int chunkSize = (endRange - startRange)/numProc;

        List<Task> tasks = new ArrayList<>();
        for( int i=0; i < numProc; i++)
        {
            int start = startRange + i*chunkSize;
            int end = (i == numProc - 1) ? endRange : startRange + (i+1)*chunkSize;
            System.out.println( start + " -- " + end );
            Task task = new Task(start, end );
            tasks.add( task );
        }

        List<Thread> threads = new ArrayList<>();
        for( Task task : tasks)
        {
            Thread thread = new Thread(task);
            threads.add(thread);
            thread.start();
        }

        TimeUnit.MILLISECONDS.sleep(10);
        for(Thread thread : threads )
        {
            //thread.join();
        }

        int result = 0;
        for( Task task : tasks )
        {
            result += task.getResult();
        }


        long endTime = System.currentTimeMillis();

        System.out.println("Anzahl " + result );
        System.out.println("done " + Thread.currentThread().getName() );
        System.out.println("Dauer " + (endTime - startTime) + "[ms]");
    }
}
