package part2.o4_synchronization.case_study;

import java.util.concurrent.ThreadLocalRandom;


public class Main
{

  public static void main(String[] args)
  {
    FairBoundedBuffer<Integer> channel = new FairBoundedBuffer<Integer>(2);

    Runnable producer = () -> {
      try {
        while (true)
        {
          channel.put(ThreadLocalRandom.current().nextInt());
        }
      } catch (InterruptedException exce) {  }
    };

    Runnable consumer = () -> {
      try{
        while (true)
        {
          System.out.println( channel.take() );
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
