package aufgabe4;


import java.text.DateFormat;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Aufgabe4
{
  private static DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM);
  private static Random rand = new Random(); 

  public static void main(String[] args)
  {
    for(int i=0; i<10; i++)
    {
      print( getDate( getLong() ) );
    }
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
