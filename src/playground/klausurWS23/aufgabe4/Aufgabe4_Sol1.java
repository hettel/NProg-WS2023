package playground.klausurWS23.aufgabe4;


import java.text.DateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.OptionalLong;
import java.util.Random;
import java.util.concurrent.*;

public class Aufgabe4_Sol1
{

  static class LongProducer implements Runnable
  {
    private final BlockingQueue<OptionalLong> outQueue;

    public LongProducer(BlockingQueue<OptionalLong> outQueue)
    {
      this.outQueue = outQueue;
    }

    @Override
    public void run()
    {
      Random rand = new Random();

      try
      {
        for(int i=0; i<10; i++)
        {
          TimeUnit.SECONDS.sleep(rand.nextInt(4));
          outQueue.put( OptionalLong.of( System.currentTimeMillis() ) );
        }

        outQueue.put( OptionalLong.empty() );
      }
      catch(InterruptedException e)
      {
        e.printStackTrace();
      }
    }
  }

  static class ConvertLoong2Date implements Runnable
  {

    private final BlockingQueue<OptionalLong> inQueue;
    private final BlockingQueue<Optional<Date>> outQueue;

    public ConvertLoong2Date(BlockingQueue<OptionalLong> inQueue, BlockingQueue<Optional<Date>> outQueue)
    {
      this.inQueue = inQueue;
      this.outQueue = outQueue;
    }

    @Override
    public void run()
    {

      try
      {
        while( true )
        {
          OptionalLong optValue = inQueue.take();

          if( optValue.isEmpty() )
          {
            outQueue.put( Optional.empty() );
            break;
          }

          long value = optValue.getAsLong();
          Date date = new Date(value);
          outQueue.put( Optional.of(date) );
        }
      }
      catch(InterruptedException e)
      {
        e.printStackTrace();
      }
    }
  }

  static class DateConsumer implements Runnable
  {
    private final BlockingQueue<Optional<Date>> inQueue;

    public DateConsumer(BlockingQueue<Optional<Date>> inQueue)
    {
      this.inQueue = inQueue;
    }

    @Override
    public void run()
    {
      DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM);


      try
      {
        while ( true )
        {
          Optional<Date>  optDate = inQueue.take();

          if( optDate.isEmpty() )
            break;

          Date date = optDate.get();
          System.out.println( formatter.format(date));
        }
      }
      catch(InterruptedException e)
      {
        e.printStackTrace();
      }
    }
  }


  public static void main(String[] args)
  {
    ExecutorService executor = Executors.newCachedThreadPool();

    BlockingQueue<OptionalLong> queue1 = new ArrayBlockingQueue<>(5);
    BlockingQueue<Optional<Date>> queue2 = new ArrayBlockingQueue<>( 5);

    executor.execute( new LongProducer(queue1));
    executor.execute( new ConvertLoong2Date(queue1,queue2));
    executor.execute( new DateConsumer( queue2));

    executor.shutdown();
  }
  


}
