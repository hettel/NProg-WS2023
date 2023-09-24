package part2.o1_threads;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ThreadLocalRandom;


/*
 * A second example for the reordering problem
 * 
 * This example uses global arrays for the shared variables
 */
class Task implements Runnable
{
  private final int me;
  private final CyclicBarrier startBarrier;
  private final CyclicBarrier syncBarrier;

  private final int[] X;
  private final int[] r;

  Task(int me, CyclicBarrier startBarrier, CyclicBarrier syncBarrier, int[] X, int r[])
  {
    this.me = me;
    this.startBarrier = startBarrier;
    this.syncBarrier  = syncBarrier;
    this.X = X;
    this.r = r;
  }

  public void run()
  {
    int other = (me == 1) ? 0 : 1;
    System.out.println("Task " + me + " started (other = " + other + ")");
   
    try
    {
      long count = 0;
      
      for (int i = 0; i < Integer.MAX_VALUE; i++)
      {
        // sync both threads
        startBarrier.await();
        
        // active wait (burn cpu)
        count += activeWait();
        
        X[me]=1;
        r[me]=X[other];
        
        // sync both threads
        syncBarrier.await();
      }
      
      System.out.println("Count " + count);
    }
    catch (Exception exce)
    {
      exce.printStackTrace();
    }
  }

  private int activeWait()
  {
    int sum = 0;
    int runs = ThreadLocalRandom.current().nextInt(100);
    for (int i = 0; i < runs; i++)
    {
      sum += i;
    }
    return sum;
  }
}

public class Demo09
{
  public static void main(String[] args)
  {
    final int X[] = new int[2];
    final int r[] = new int[2];

    CyclicBarrier startBarrier = new CyclicBarrier(2);

    CyclicBarrier syncBarrier = new CyclicBarrier(2, new Runnable()
    {
      private int count = 0;
      
      @Override
      public void run()
      {
        // Check if reordering has occurred
        if (r[0] == 0 && r[1] == 0)
        {
          // Violation of sequential condition
          System.out.println(" r[0] == r[1] == 0  at run " + count + " (" + r[0] + "," + r[1] + ")");
        }
        
        // Reset variable (init values)
        X[0] = X[1] = 0;
        r[0] = r[1] = -1;
        count++;
      }
    } ); 

    Task task0 = new Task(0, startBarrier, syncBarrier, X, r);
    Task task1 = new Task(1, startBarrier, syncBarrier, X, r);
    
    Thread th0 = new Thread( task0 );
    Thread th1 = new Thread( task1 );
    
    th0.start(); 
    th1.start(); 
  }
}
