package playground;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class PrimeCollect
{
    public static class CollectPrimesTask extends RecursiveTask< List<Integer> >
    {
        private final int start;
        private final int end;

        CollectPrimesTask(int start, int end)
        {
            this.start = start;
            this.end   = end;
        }

        @Override
        protected List<Integer> compute()
        {
            if(  (end-start) < 100 )
            {
                List<Integer> primes = new ArrayList<>();
                for(int i = start; i < end; i++)
                {
                    if( BigInteger.valueOf(i).isProbablePrime(1000) )
                        primes.add(i);
                }

                return primes;
            }
            else
            {
                int mid = (start + end)/2;
                CollectPrimesTask leftTask = new CollectPrimesTask(start, mid);
                CollectPrimesTask rightTask = new CollectPrimesTask( mid, end );
                invokeAll( leftTask, rightTask );

                List<Integer> result = leftTask.join();
                result.addAll(rightTask.join());

                return result;
            }
        }
    }


    public static void main(String[] args)
    {
        long startTime = System.currentTimeMillis();

        List<Integer> primes = collectPrimes( 1_000_000, 2_000_000 );

        System.out.println("Dauer " + (System.currentTimeMillis() - startTime) + "[ms]");

        System.out.println("Size " + primes.size() );

        startTime = System.currentTimeMillis();
        CollectPrimesTask root = new CollectPrimesTask(1_000_000, 2_000_000);
        ForkJoinPool.commonPool().execute( root );

        System.out.println("Size " + root.join().size() );
        System.out.println("Dauer " + (System.currentTimeMillis() - startTime) + "[ms]");
    }




    public static List<Integer>  collectPrimes(int start, int end)
    {
        if(  (end-start) < 100 )
        {
            List<Integer> primes = new ArrayList<>();
            for(int i = start; i < end; i++)
            {
                if( BigInteger.valueOf(i).isProbablePrime(1000) )
                    primes.add(i);
            }

            return primes;
        }
        else
        {
            int mid = (start + end)/2;
            List<Integer> left = collectPrimes(start, mid);
            List<Integer>  right = collectPrimes(mid,end);

            left.addAll(right);

            return left;
        }
    }
}
