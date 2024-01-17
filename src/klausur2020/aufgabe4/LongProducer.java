package klausur2020.aufgabe4;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class LongProducer
{
  private Random rand = new Random();

  public long getNext()
  {
    try
    {
      TimeUnit.SECONDS.sleep( rand.nextInt(4) );
    }
    catch (InterruptedException e)
    {
      e.printStackTrace();
    }

    return System.currentTimeMillis();
  }
}
