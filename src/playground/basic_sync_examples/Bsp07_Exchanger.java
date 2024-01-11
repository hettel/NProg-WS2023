package playground.basic_sync_examples;

import java.util.concurrent.Exchanger;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class Bsp07_Exchanger
{

    static class Task implements Runnable
    {
        private final Exchanger<Integer> exchanger;
        private final String name;

        public Task(String name, Exchanger<Integer> exchanger)
        {
            this.exchanger = exchanger;
            this.name = name;
        }

        @Override
        public void run()
        {
            try{
                int rd1  = ThreadLocalRandom.current().nextInt();
                TimeUnit.MILLISECONDS.sleep( ThreadLocalRandom.current().nextInt(1000) );

                int rd2 = exchanger.exchange( rd1 );

                System.out.println( name + ": Meine Variable " + rd1  );
                System.out.println( name + ": Erhaltene Variable " + rd2 );

            }
            catch (InterruptedException exce)
            {
                exce.printStackTrace();
            }

        }
    }

    public static void main(String[] args)
    {
        Exchanger<Integer> exchanger = new Exchanger<>();
        new Thread( new Task("Task1", exchanger) ).start();
        new Thread( new Task("Task2", exchanger) ).start();

        System.out.println("Main done");
    }
}
