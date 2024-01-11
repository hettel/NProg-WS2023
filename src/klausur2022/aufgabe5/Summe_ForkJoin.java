package klausur2022.aufgabe5;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ThreadLocalRandom;

public class Summe_ForkJoin
{
  static class Task extends RecursiveTask<Integer>
  {
    private final int[] array;
    private final int start;
    private final int end;

    public Task(int[] array, int start, int end)
    {
      this.array = array;
      this.start = start;
      this.end = end;
    }

    @Override
    protected Integer compute()
    {
      if( (end - start) < 64 )
      {
        int sum = 0;

        for( int i = start; i < end; i++ )
        {
          sum += array[i];
        }

        return sum;
      }

      int mid = (end - start)/2;
      Task leftTask = new Task(array, start, start + mid);
      Task rightTask = new Task(array, start + mid, end);
      invokeAll( leftTask, rightTask );

      return (leftTask.join() + rightTask.join());
    }
  }

  public static void main(String[] args)
  {
    int[] array = getArray(1_000_000);

    Task root = new Task( array, 0, array.length );

    ForkJoinPool.commonPool().execute( root );

    System.out.println("Summe : " + root.join() );
  }




  private static int[] getArray(int len)
  {
    int[] array = new int[len];
    Arrays.setAll(array, i -> ThreadLocalRandom.current().nextInt(len/4) );
    return array;
  }

}
