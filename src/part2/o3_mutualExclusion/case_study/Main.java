package part2.o3_mutualExclusion.case_study;

import java.util.concurrent.ThreadLocalRandom;

public class Main
{
  //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
  // WARNING !!!!
  // This example runs into a deadlock if the condition
  // 
  // 2*BufferCapacity < numOf( Consumer ) + numOf( Producer )
  //
  // holds
  //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
  public static void main(String[] args)
  {
    BoundedBuffer<Integer> channel = new BoundedBuffer<Integer>(2);

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

    // start consumer
    new Thread(consumer).start(); 
    new Thread(consumer).start();
    new Thread(consumer).start();
  }
}
