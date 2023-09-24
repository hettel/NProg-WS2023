package part2.o1_threads;

import java.math.BigInteger;

public class Demo01
{
  public static void main(String[] args) 
  {   
    int count = 0;
    
    System.out.println("Start serach");
    long start = System.currentTimeMillis();
    for( long candidate = 1_000_000L; candidate < 2_000_000L; candidate++ )
    {
      BigInteger bInt = BigInteger.valueOf(candidate);
      if( bInt.isProbablePrime(1000) )
      {
        count++;
      }
    }
    long end = System.currentTimeMillis();
    
    System.out.println("Elapsed Time " + (end - start) + " [ms]");
    System.out.println("Number of primes " + count );
  }
}
