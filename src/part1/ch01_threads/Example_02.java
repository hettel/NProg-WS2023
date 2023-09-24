package part1.ch01_threads;

public class Example_02
{
   public static void main(String[] args)
   {
      Runnable task = () -> System.out.println("Hello from " + Thread.currentThread().getName());

      Thread th = new Thread(task, "MyThread");
      th.start();

      System.out.println("Hello from " + Thread.currentThread().getName());
   }
}
