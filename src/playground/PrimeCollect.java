package playground;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PrimeCollect
{
    public static void main(String[] args)
    {
        List<Integer> primes = collectPrimes( 1_000_000, 2_000_000 );

        System.out.println("Size " + primes.size() );
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
