package part2.o5_concurrencyTools.case_study;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


public class ParallelCellularAutomata
{
  public static void main(String[] args)
  {
    ExecutorService executor = Executors.newCachedThreadPool();
    
    final int SIZE = 100;
    FieldValue[] from = new FieldValue[SIZE];
    FieldValue[] to   = new FieldValue[SIZE];
    
    initRandom(from);
    
    // Executed when the barrier is tripped
    Runnable barrierAction = () -> { copy(to, from); print(from); delayMillis(300);};
    CyclicBarrier barrier = new CyclicBarrier(SIZE, barrierAction );
    
    for(int index = 0; index < SIZE; index++)
    {
      Task task = new Task(100, index, from, to, barrier);
      executor.execute(task);
    }
    
    executor.shutdown();
  }
  
  private static void print(FieldValue[] field) 
  {
    for (FieldValue c : field)
    {
      System.out.print(c);
    }
    System.out.println();
  }
  
  private static void copy(FieldValue[] from, FieldValue[] to)
  {
    System.arraycopy(from, 0, to, 0, from.length);
  }
  
  private static void initRandom(FieldValue[] field)
  {
    Arrays.fill(field, FieldValue.W);
    Random rd = new Random();
    for (int i = 0; i < 20; i++)
    {
      field[rd.nextInt(field.length)] = FieldValue.B;
    }
  }

  private static void delayMillis(int time)
  {
    try
    {
      TimeUnit.MILLISECONDS.sleep(time);
    }
    catch (InterruptedException e)
    {
      e.printStackTrace();
    }
  }
}
