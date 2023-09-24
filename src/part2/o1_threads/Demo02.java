package part2.o1_threads;

public class Demo02
{
  public static void main(String[] args)
  {
    Runnable task = () ->  
      System.out.println("Hello from " + Thread.currentThread().getName());

    Thread th = new Thread( task, "MyThread" );
    th.start();

    System.out.println("Hello from " + Thread.currentThread().getName());
  }
}
