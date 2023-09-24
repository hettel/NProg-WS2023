package part1.ch05_streams_reduction;

import java.util.Arrays;

public class Example_03
{

   public static void main(String[] args)
   {
      double values[] = new double[1_000_001];

      double start = -4;
      double step = 8.0 / (values.length - 1);
      for (int i = 0; i < values.length; i++)
      {
         values[i] = 1_000*Math.atan(start + i * step);
      }

      double sum1 = Arrays.stream(values).sum();
      System.out.println("Sequential Stream: " + sum1);

      double sum2 = Arrays.stream(values).parallel().sum();
      System.out.println("Parallel Stream: " + sum2);
      
      System.out.println("Difference : " + Math.abs(sum1 - sum2) );
   }
}
