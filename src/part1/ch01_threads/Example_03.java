package part1.ch01_threads;

public class Example_03
{
   static class MyTask implements Runnable
   {

      @Override
      public void run()
      {
         System.out.println("Hello from " + Thread.currentThread().getName());
      }

   }

   public static void main(String[] args)
   {
      Runnable task = new MyTask();

      for (int i = 0; i < 100; i++)
      {
         Thread th = new Thread(task);
         th.start();
      }

      System.out.println("Hello from " + Thread.currentThread().getName());
   }
}
