package part2.o3_mutualExclusion.case_study;

public class BoundedBuffer<T>
{
  private T[] array;
  private int tailIndex = 0;
  private int headIndex = 0;
  private int size = 0;
  private final int capacity;
  
  @SuppressWarnings("unchecked")
  public BoundedBuffer(int capacity)
  {
    this.capacity = capacity;
    this.array = (T[]) new Object[capacity];
  }
  
  public synchronized T take() throws InterruptedException
  {
    // conditional wait
    while( size == 0 )
      wait();
    
    T item = this.array[this.tailIndex];
    this.tailIndex = (this.tailIndex+1)%this.capacity;
    size--;
    
    notify();
    
    return item;
  }
  
  public synchronized void put(T item) throws InterruptedException
  {
    // conditional wait
    while( size == capacity )
      wait();
    
    this.array[this.headIndex] = item;
    this.headIndex = (this.headIndex+1)%this.capacity;
    size++;
    
    notify();
  }
}
