package part1.ch10_fork_join;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class Example_01
{
   private static final int THRESHOLD = 10;
   
   public static int max(int[] array, int start, int end) {   
     // work phase
     if( end - start < THRESHOLD ) {
        int maxValue = array[start];
        for(int i=start+1; i<end; i++) {
          if( array[i] > maxValue ) {
             maxValue = array[i];
          }
        }
        return maxValue;
     }
     else {
       // split phase
       int mid = (start + end)/2;
       int leftValue = max(array, start, mid);
       int rightValue = max(array, mid, end);
       
       // combine phase
       return Math.max(leftValue, rightValue);
     }
   }
   
   public static void main(String[] args)
   {
      int[] array = new int[1_000_000];
      Arrays.parallelSetAll(array, i -> ThreadLocalRandom.current().nextInt(50_000_000));
      
      int max = max(array, 0, array.length);
      System.out.println("Max value : " + max );
   }
}
