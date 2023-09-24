package part2.o1_threads;

import java.util.concurrent.TimeUnit;

public class Demo03
{
  // shared data (the two threads can access this variable)
  static boolean isRunning = true;

  public static void main(String[] args) throws InterruptedException
  {
    Runnable reader = () -> {
      int count = 0;
      while (isRunning)
        count++;
      System.out.println("Value: " + count);
    };

    Runnable writer = () -> isRunning = false;

    Thread th1 = new Thread(reader);
    Thread th2 = new Thread(writer);

    System.out.println("Start threads");
    th1.start();
    TimeUnit.MILLISECONDS.sleep(500);
    th2.start();

    TimeUnit.MILLISECONDS.sleep(100);
    System.out.println("isRunning " + isRunning );
    
    th1.join();
    th2.join();
    System.out.println("done");
  }
}
