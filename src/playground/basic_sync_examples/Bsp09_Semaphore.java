package playground.basic_sync_examples;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class Bsp09_Semaphore
{
    static class  Task implements Runnable
    {
        private final String name;
        private final List<Integer> list;

        private final Semaphore semaphore;

        public Task(String name, List<Integer> list, Semaphore semaphore)
        {
            this.name = name;
            this.list = list;
            this.semaphore = semaphore;
        }

        @Override
        public void run()
        {
            while (  true )
            {
                try {
                    sleepRandom();

                    semaphore.acquire();
                    int rd = list.removeFirst();
                    System.out.println(name + " get " + rd);
                    sleepRandom();
                    list.add(rd);
                    System.out.println(name + " add " + rd);
                    semaphore.release();
                }
                catch (InterruptedException exce)
                {
                    System.out.println( name + " interrupted");
                    break;
                }
            }
        }

        private void sleepRandom() throws InterruptedException
        {
            TimeUnit.MILLISECONDS.sleep(ThreadLocalRandom.current().nextInt(1000));
        }
    }



    public static void main(String[] args) throws  InterruptedException
    {
        List<Integer> primeList = Collections.synchronizedList( new LinkedList<>() );
        primeList.add(2);
        primeList.add(3);
        primeList.add(5);
        primeList.add(7);
        primeList.add(11);

        Semaphore semaphore = new Semaphore( primeList.size() );

        List<Thread> threads = new ArrayList<>();
        for( int i=0; i < 20; i++)
        {
            Thread th = new Thread( new Task("Task_" + i, primeList, semaphore) );
            threads.add(th);
        }

        threads.forEach(Thread::start);

        TimeUnit.SECONDS.sleep(5);

        threads.forEach(Thread::interrupt);

        System.out.println("Main done");
    }
}
