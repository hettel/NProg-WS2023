package part2.o3_mutualExclusion;


public class BlockingDataExchange
{
  private static class SharedVariable<T>
  {
    private T value;
    
    private synchronized void put(T item) throws InterruptedException
    {
      while( value != null )
        wait();
      
      value = item;
      
      notify();
    }
    
    private synchronized T take() throws InterruptedException
    {
      while( value == null )
        wait();
      
      T result = value;
      value = null;
      
      notify();
      
      return result;
    }
  }
  
  public static void main(String[] args)
  {
    SharedVariable<Long> exchangePlace = new SharedVariable<>();
    
    Runnable producer = () -> {
      while( true )
      {
        try
        {
          long time = System.currentTimeMillis();
          exchangePlace.put( time );
          System.out.println("put " + time );
        }
        catch (InterruptedException e)
        {
          e.printStackTrace();
        }
      }
    };
    
    Runnable consumer = () -> {
      while( true )
      {
        try
        {
          long value = exchangePlace.take( );
          System.out.println("take " + value );
        }
        catch (InterruptedException e)
        {
          e.printStackTrace();
        }
      }
    };
    
    // If the example is executed with two or more producers 
    // or two or more consumers, a "deadlock" occurs.
    new Thread( producer ).start();
    new Thread( consumer ).start();
    new Thread( consumer ).start();
  }
}
