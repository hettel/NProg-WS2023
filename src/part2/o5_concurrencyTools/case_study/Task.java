package part2.o5_concurrencyTools.case_study;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;


public class Task implements Runnable
{
  private final int maxLifeTime;
  private final CyclicBarrier barrier;
  private final FieldValue[] from;
  private final FieldValue[] to;
  private final int fieldIndex;

  public Task(int maxLifeTime,  int fieldIndex, FieldValue[] from, FieldValue[] to, CyclicBarrier barrier)
  {
    this.maxLifeTime = maxLifeTime;
    this.barrier = barrier;
    this.from = from;
    this.to = to;
    this.fieldIndex = fieldIndex;
  }

  @Override
  public void run()
  {
    try
    {
      int fieldLen = this.from.length;
      
      for (int i = 0; i < maxLifeTime; i++)
      {
        int leftIndex = (this.fieldIndex - 1 + fieldLen)%fieldLen;
        int rightIndex = (this.fieldIndex + 1)%fieldLen;
        
        FieldValue value = Rule.get(this.from[leftIndex], 
                                    this.from[fieldIndex], 
                                    this.from[rightIndex]);
        
        this.to[fieldIndex] = value;
        this.barrier.await();
      }
    }
    catch (InterruptedException | BrokenBarrierException exce)
    {
      exce.printStackTrace();
    }
  }
}
