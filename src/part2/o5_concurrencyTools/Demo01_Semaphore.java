package part2.o5_concurrencyTools;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.stream.IntStream;

public class Demo01_Semaphore
{
  static class SimpleUninterruptableBoundedBuffer<E>
  {
    private final List<E> buffer;
    private final Semaphore availableSpace;
    private final Semaphore availableItems;
    
    public SimpleUninterruptableBoundedBuffer(int capacity)
    {
      this.buffer = Collections.synchronizedList(new  LinkedList<E>());
      this.availableSpace = new Semaphore(capacity);
      this.availableItems = new Semaphore(0);
    }
    
    public void put(E element)
    {
      this.availableSpace.acquireUninterruptibly();
      this.buffer.add(element);
      this.availableItems.release();
    }
    
    public E take()
    {
      this.availableItems.acquireUninterruptibly();
      E element = this.buffer.remove(0);
      this.availableSpace.release();
      return element;
    }
  }
  
  
  
  public static void main(String[] args)
  {
    // IMPORTANT: The program does not terminate!!!
    // The consumer thread is always waiting for items on the buffer 
    
    SimpleUninterruptableBoundedBuffer<Integer> buffer = new SimpleUninterruptableBoundedBuffer<>(10);
    
    Runnable producer = () -> {
      IntStream.range(0, 100).forEach( i -> buffer.put(i) );
    };
    
    Runnable consumer = () -> {
      while(true)
        System.out.println( buffer.take() );
    };

    new Thread(producer).start();
    new Thread(consumer).start();
  }

}
