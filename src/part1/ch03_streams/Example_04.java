package part1.ch03_streams;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;

public class Example_04
{

   public static void main(String[] args)
   {
      final int LEN = 1_000;
      
      // Create two int-arrays with random values between 0 and 42
      Random rand = new Random();     
      int[] vec1 = rand.ints(LEN, 0, 42).toArray();
      int[] vec2 = rand.ints(LEN, 0, 42).toArray();
      
      // Add the two vectors
      int[] vecSum = add( vec1, vec2);

      // Print the first 10 elements of the result
      Arrays.stream( vecSum).limit(10).forEach( System.out::println );
   }

   public static int[] add(int[] a, int b[])
   {
       assert a.length == b.length;

       int[] c = new int[a.length];

       IntStream.range(0, c.length).parallel().forEach( i -> 
            {
               c[i] = a[i] + b[i];
            } 
       );

       return c;
   }

}
