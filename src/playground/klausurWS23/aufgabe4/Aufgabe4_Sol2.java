package playground.klausurWS23.aufgabe4;


import java.text.DateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.OptionalLong;
import java.util.Random;
import java.util.concurrent.*;

public class Aufgabe4_Sol2
{
  public static void main(String[] args)
  {
    try(ExecutorService executor = Executors.newFixedThreadPool(3) ) {

      BlockingQueue<OptionalLong> queue1 = new ArrayBlockingQueue<>(10);
      BlockingQueue<Optional<Date>> queue2 = new ArrayBlockingQueue<>(10);

      executor.execute(() -> produceLong(queue1));
      executor.execute(() -> convertLongToDate(queue1, queue2));
      executor.execute(() -> consume(queue2));
    }
    //executor.shutdown();
  }
  

  public static void consume( BlockingQueue<Optional<Date>> inQueue )
  {
    DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM);

    try
    {
      while( true )
      {
        Optional<Date> optDate = inQueue.take();
        if( optDate.isEmpty() )
        {
          break;
        }

        System.out.println( formatter.format(optDate.get()));
      }
    }catch (InterruptedException e)
    {
      e.printStackTrace();
    }
  }

  public static void convertLongToDate(BlockingQueue<OptionalLong> inQueue, BlockingQueue<Optional<Date>> outQueue)
  {
    try
    {
      while( true )
      {
        OptionalLong optVal = inQueue.take();
        if( optVal.isEmpty() )
        {
            outQueue.put(Optional.empty() );
            break;
        }

        Date date = new Date(optVal.getAsLong() );
        outQueue.put( Optional.of(date) );
      }
    }catch (InterruptedException e)
    {
      e.printStackTrace();
    }
  }
  
  // producer
  public static void produceLong(BlockingQueue<OptionalLong> outQueue)
  {
    Random rand = new Random();
    try
    {
      for(int i=0; i<10; i++)
      {
        TimeUnit.SECONDS.sleep(rand.nextInt(4));
        outQueue.put(OptionalLong.of( System.currentTimeMillis() ) );
      }

      outQueue.put(OptionalLong.empty());
    }
    catch (InterruptedException e)
    {
      e.printStackTrace();
    }
  }
}
