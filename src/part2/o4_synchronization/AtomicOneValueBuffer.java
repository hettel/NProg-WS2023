package part2.o4_synchronization;

import java.util.concurrent.atomic.AtomicMarkableReference;

// Simulating a lock free (blocking) Queue of size one
public class AtomicOneValueBuffer<V>
{
  private final AtomicMarkableReference<V> buffer = new AtomicMarkableReference<>(null, false);
  
  // set the value and mark the setting
  // null values are allowed
  public void put(V value)
  {
    do
    {
      // no content
    }while( !buffer.compareAndSet(null, value, false, true) );
  }
  
  // read the value und mark the reading
  // the value can also be null
  public V take()
  {
    V value = null;
    
    do
    {
      value = buffer.getReference();
    }while( !buffer.compareAndSet(value, null, true, false) );
    
    return value;
  }
}
