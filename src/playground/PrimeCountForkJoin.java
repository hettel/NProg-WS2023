package playground;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class PrimeCountForkJoin
{
    public static void main(String[] args)
    {
        long startTime = System.currentTimeMillis();

        int primes = primeCount(1_000_000, 2_000_000);

        System.out.println("Dauer " + ( System.currentTimeMillis() - startTime ) + "[ms]");
        System.out.println("Anzahl " + primes);

    }


    public static int primeCount(int start, int end)
    {
        if ( ( end - start ) < 100 ) {
            int count = 0;
            for ( int i = start; i < end; i++ ) {
                if ( BigInteger.valueOf(i).isProbablePrime(1000) )
                    count++;
            }

            return count;
        } else {
            int mid = ( start + end ) / 2;
            int left = primeCount(start, mid);
            int right = primeCount(mid, end);

            return left + right;
        }
    }
}
