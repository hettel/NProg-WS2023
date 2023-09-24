package part2.o5_concurrencyTools;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadLocalRandom;


public class Demo00_BoundedBuffer
{

  public static void main(String[] args)
  {
    BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(2);

    Runnable producer = () -> {
      try {
        while (true)
        {
          queue.put(ThreadLocalRandom.current().nextInt());
        }
      } catch (InterruptedException exce) {  }
    };

    Runnable consumer = () -> {
      try{
        while (true)
        {
          System.out.println( queue.take() );
        }
      } catch (InterruptedException exce) {  }
    };

    // start producer
    new Thread(producer).start(); 
    new Thread(producer).start();
    new Thread(producer).start();

    // start consumer
    new Thread(consumer).start(); 
    new Thread(consumer).start();
    new Thread(consumer).start();
  }

}
