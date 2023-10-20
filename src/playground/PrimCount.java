package playground;

import java.math.BigInteger;
import java.util.stream.LongStream;

public class PrimCount
{
    public static void main(String[] args)
    {

        System.out.println("Start serach with ");
        long start = System.currentTimeMillis();

        long count =
        LongStream.range(1_000_000L, 2_000_000L)
                  .parallel()
                  .mapToObj( BigInteger::valueOf )
                  .filter( bi -> bi.isProbablePrime(1000))
                  .count();

        long end = System.currentTimeMillis();

        System.out.println("Elapsed time " + (end - start) + " [ms]");
        System.out.println("Number of prims " + count);
    }

}
