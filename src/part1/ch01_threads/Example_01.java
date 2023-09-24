package part1.ch01_threads;

import java.math.BigInteger;

public class Example_01
{
   public static void main(String[] args)
   {
      System.out.println("Num of cores : " + Runtime.getRuntime().availableProcessors());

      int count = 0;

      System.out.println("Start serach");
      long start = System.currentTimeMillis();
      for(long candidate = 1_000_000L; candidate < 2_000_000L; candidate++)
      {
         BigInteger bInt = BigInteger.valueOf(candidate);
         if(bInt.isProbablePrime(1000))
         {
            count++;
         }
      }
      long end = System.currentTimeMillis();

      System.out.println("Elapsed time " + (end - start) + " [ms]");
      System.out.println("Number of prims " + count);
   }
}
