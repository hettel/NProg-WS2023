package klausur2022.aufgabe4;


import java.text.DateFormat;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class Aufgabe4_ProducerConsumer
{
  private static DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM);
  private static Random rand = new Random(); 

  public static void main(String[] args)
  {
    BlockingQueue<Long> queue1 = new ArrayBlockingQueue<>(5);
    BlockingQueue<Date> queue2 = new ArrayBlockingQueue<>(5);

    Runnable producer = () -> {
      try {
        for(int i= 0; i<10; i++)
        {
          queue1.put( getLong() );
        }
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
    };

    Runnable filter = () -> {
      try {
        for ( int i = 0; i < 10; i++ ) {
          long l = queue1.take();
          queue2.put(getDate(l));
        }
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
    };

    Runnable consumer = () -> {
      try {
        for(int i= 0; i<10; i++)
        {
          Date date = queue2.take();
          print(date);
        }
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
    };

    new Thread( consumer ).start();
    new Thread( filter ).start();
    new Thread( producer ).start();
  }
  
  // consumer
  public static void print(Date date)
  {
    System.out.println( formatter.format(date));
  }
  
  // filter
  public static Date getDate(long time)
  {
    return new Date(time);
  }
  
  // producer
  public static long getLong()
  {
    try
    {
      TimeUnit.SECONDS.sleep(rand.nextInt(4));
    }
    catch (InterruptedException e)
    {
      e.printStackTrace();
    }

    return System.currentTimeMillis();
  }

}
