package playground;

import java.math.BigInteger;

public class ParallelPrim
{
    private static class Task implements Runnable
    {
        private int start, end;

        Task(int start, int end)
        {
            this.start = start;
            this.end = end;
        }
        @Override public void run() {
            int count = 0;
            for (int i = start; i < end; i++)
            {
                if(BigInteger.valueOf(i).isProbablePrime(1000) )
                {
                    count++;
                }
            }

            System.out.println("Anzahl " + count + " von  " + Thread.currentThread().getName() );
        }
    }

    public static void main(String[] args) throws InterruptedException
    {
        long startTime = System.currentTimeMillis();
        Task task1 = new Task(1_000_000, 1_250_000);
        Task task2 = new Task(1_250_000, 1_500_000);
        Task task3 = new Task(1_500_000, 1_750_000);
        Task task4 = new Task(1_750_000, 2_000_000);

        Thread thread1 = new Thread( task1, "Worker1");
        Thread thread2 = new Thread( task2, "Worker2");
        Thread thread3 = new Thread( task3, "Worker3");
        Thread thread4 = new Thread( task4, "Worker4");

        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();

        thread1.join();
        thread2.join();
        thread3.join();
        thread4.join();

        long endTime = System.currentTimeMillis();

        System.out.println("done " + Thread.currentThread().getName() );
        System.out.println("Dauer " + (endTime - startTime) + "[ms]");
    }
}
