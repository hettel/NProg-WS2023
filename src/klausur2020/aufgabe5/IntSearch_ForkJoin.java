package klausur2020.aufgabe5;

import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class IntSearch_ForkJoin
{
  static class CountTask extends RecursiveTask<Integer>
  {
    private final int search;
    private final int[] array;
    private final int start;
    private final int end;

    public CountTask(int search, int[] array, int start, int end)
    {
      this.search = search;
      this.array = array;
      this.start = start;
      this.end = end;
    }

    @Override
    protected Integer compute()
    {
      int count = 0;

      if( (end - start) < 64 )
      {
        for( int i = start; i < end; i++ )
        {
          if( array[i] == search )
            count++;
        }

        return count;
      }

      int mid = (end - start)/2;

      CountTask leftTask = new CountTask(search, array, start, start + mid);
      CountTask rightTask = new CountTask(search, array, start + mid, end);
      invokeAll( leftTask, rightTask );

      return leftTask.join() + rightTask.join();
    }
  }


  public static void main(String[] args)
  {
    int[] array = getArray(1024*1024);

    CountTask root = new CountTask(42, array, 0, array.length);

    ForkJoinPool.commonPool().execute( root );

    System.out.println("Anzahl : " + root.join() );
  }



  public static int[] getArray(int len)
  {
    int[] array = new int[len];

    Random rand = new Random();
    for(int i = 0; i  < len; i++)
    {
      array[i] = rand.nextInt(len/4);
    }

    return array;
  }
}
