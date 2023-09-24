package part2.o4_synchronization.case_study;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class FairBoundedBuffer<T>
{
  private Lock lock = new ReentrantLock(true); // fair lock
  private Condition isFull = lock.newCondition();
  private Condition isEmpty = lock.newCondition();

  private T[] array;
  private int tailIndex = 0;
  private int headIndex = 0;
  private int size = 0;
  private final int capacity;

  @SuppressWarnings("unchecked")
  public FairBoundedBuffer(int capacity)
  {
    this.capacity = capacity;
    this.array = (T[]) new Object[capacity];
  }

  public T take() throws InterruptedException
  {
    lock.lock();
    try
    {
      // conditional wait
      while (size == 0)
        isEmpty.await();

      T item = this.array[this.tailIndex];
      this.tailIndex = (this.tailIndex + 1) % this.capacity;
      size--;

      isFull.signal();

      return item;
    }
    finally
    {
      lock.unlock();
    }
  }

  public void put(T item) throws InterruptedException
  {
    lock.lock();
    try
    {
      // conditional wait
      while (size == capacity)
        isFull.await();

      this.array[this.headIndex] = item;
      this.headIndex = (this.headIndex + 1) % this.capacity;
      size++;

      isEmpty.signal();
    }
    finally
    {
      lock.unlock();
    }
  }
}
