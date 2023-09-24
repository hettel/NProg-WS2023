package part1.ch02_activity;

import java.math.BigInteger;

public class PrimeCount
{

   public static void main(String[] args)
   {
      int count = 0;

      System.out.println("Start serach");
      long startTime = System.currentTimeMillis();
      for (long candidate = 1_000_000L; candidate < 2_000_000L; candidate++)
      {
         BigInteger bInt = BigInteger.valueOf(candidate);
         if (bInt.isProbablePrime(1000))
         {
            count++;
         }
      }
      long endTime = System.currentTimeMillis();

      System.out.println("Elapsed time " + (endTime - startTime) + " [ms]");
      System.out.println("Number of prims " + count);
   }

}
