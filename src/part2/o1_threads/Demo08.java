package part2.o1_threads;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ThreadLocalRandom;

// declare X and Y as volatile prevent the
// reordering effect.
class GobalData
{
  static int X;
  static int rx;
  static int Y;
  static int ry;
}

class Task1 implements Runnable
{
  private final CyclicBarrier startBarrier;
  private final CyclicBarrier syncBarrier;

  Task1(CyclicBarrier startBarrier, CyclicBarrier syncBarrier)
  {
    this.startBarrier = startBarrier;
    this.syncBarrier  = syncBarrier;
  }

  public void run()
  {
    System.out.println("Task 1 started");
   
    try
    {
      long count = 0;
      
      for (int i = 0; i < Integer.MAX_VALUE; i++)
      {
        // sync both threads
        startBarrier.await();
        
        // active wait (burn cpu)
        count += activeWait();
        
        GobalData.X  = 1;
        GobalData.rx = GobalData.Y;
        
        // sync both threads
        // and reset global variables
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


class Task2 implements Runnable
{
  private final CyclicBarrier startBarrier;
  private final CyclicBarrier syncBarrier;

  Task2(CyclicBarrier startBarrier, CyclicBarrier syncBarrier)
  {
    this.startBarrier = startBarrier;
    this.syncBarrier  = syncBarrier;
  }

  public void run()
  {
    System.out.println("Task 2 started");
   
    try
    {
      long count = 0;
      
      for (int i = 0; i < Integer.MAX_VALUE; i++)
      {
        // sync both threads
        startBarrier.await();
        
        // active wait (burn cpu)
        count += activeWait();
        
        GobalData.Y  = 1;
        GobalData.ry = GobalData.X;
        
        // sync both threads
        // and reset global variables
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

public class Demo08
{
  public static void main(String[] args)
  {
    CyclicBarrier startBarrier = new CyclicBarrier(2);
    CyclicBarrier syncBarrier = new CyclicBarrier(2, new Runnable()
    {
      private int count = 0;
      
      @Override
      public void run()
      {
        // Check if reordering has occurred
        if (GobalData.rx == 0 && GobalData.ry == 0)
        {
          // Violation of sequential condition
          System.out.println(" rx == ry == 0  at run " + count + " (" + GobalData.rx + "," + GobalData.ry + ")");
        }
        
        // Reset variable (init values)
        GobalData.X = GobalData.Y = 0;
        GobalData.rx = GobalData.ry = -1;
        count++;
      }
    } ); 

    Task1 task1 = new Task1(startBarrier, syncBarrier);
    Task2 task2 = new Task2(startBarrier, syncBarrier);
    
    Thread th1 = new Thread( task1 );
    Thread th2 = new Thread( task2 );
    
    th1.start(); 
    th2.start(); 
  }
}
