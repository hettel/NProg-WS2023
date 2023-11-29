package playground.producer_consumer;

import java.math.BigInteger;
import java.util.OptionalLong;
import java.util.concurrent.*;

public class ProdConsumer
{


    static class Producer implements Runnable
    {
        private final BlockingQueue<OptionalLong> outQueue;
        private  final int poissonPilsToSend;

        public Producer(BlockingQueue<OptionalLong> outQueue, int poissonPilsToSend)
        {
            this.outQueue = outQueue;
            this.poissonPilsToSend = poissonPilsToSend;
        }

        @Override
        public void run()
        {
            try {
                for ( int i = 0; i < 1_000; i++ ) {
                    long randNum = ThreadLocalRandom.current().nextLong();
                    outQueue.put( OptionalLong.of(randNum) );
                }

                for( int i=0; i< poissonPilsToSend;  i++)
                   outQueue.put( OptionalLong.empty() );

            }catch (InterruptedException exce)
            {
                exce.printStackTrace();
            }
        }
    }


    static class Filter implements Runnable
    {
        private final BlockingQueue<OptionalLong> inQueue;
        private final BlockingQueue<OptionalLong> outQueue;

        private final int poissonPilsToConsume;

        private final int poissonPilsToSend;

        public Filter(BlockingQueue<OptionalLong> inQueue, int poissonPilsToConsume, BlockingQueue<OptionalLong> outQueue,  int poissonPilsToSend)
        {
            this.inQueue = inQueue;
            this.outQueue = outQueue;
            this.poissonPilsToConsume = poissonPilsToConsume;
            this.poissonPilsToSend = poissonPilsToSend;
        }

        @Override
        public void run()
        {
            try
            {
                int poisonPils = 0;

                while ( true ) {

                    OptionalLong optValue = inQueue.take();

                    if( optValue.isEmpty() ) {
                        poisonPils++;

                        if( poisonPils == poissonPilsToConsume  )
                        {
                            for( int i=0; i<poissonPilsToSend; i++)
                                outQueue.put(OptionalLong.empty());

                            break;
                        }
                    }

                    if ( BigInteger.valueOf(optValue.getAsLong()).isProbablePrime(1000) ) {
                        outQueue.put(optValue);
                    }
                }
            }
            catch (InterruptedException exce)
            {
                exce.printStackTrace();
            }
        }
    }

    static class Consumer implements Runnable
    {
        private final BlockingQueue<OptionalLong> inQueue;
        private final int poissonPilsToConsume;

        public Consumer(BlockingQueue<OptionalLong> inQueue, int poissonPilsToConsume)
        {
            this.inQueue = inQueue;
            this.poissonPilsToConsume = poissonPilsToConsume;
        }

        @Override
        public void run()
        {
            try{
                 int poisonPils = 0;

                while ( true )
                {
                    OptionalLong optValue = inQueue.take();

                    if( optValue.isEmpty() ) {
                        poisonPils++;
                        if ( poisonPils == poissonPilsToConsume )
                            break;
                    }
                    else
                    {
                        System.out.println("Found " + optValue.getAsLong() );
                    }

                }
            }
            catch (InterruptedException exce)
            {
                exce.printStackTrace();
            }
        }
    }

    public static void main(String[] args)
    {
        BlockingQueue<OptionalLong> queue1 = new ArrayBlockingQueue<>(50);
        BlockingQueue<OptionalLong> queue2 = new ArrayBlockingQueue<>(50);

        ExecutorService executor = Executors.newFixedThreadPool(3);

        executor.execute( new Producer(queue1,2) );
        executor.execute( new Producer(queue1,1) );
        executor.execute( new Filter(queue1, 1, queue2, 1) );
        executor.execute( new Filter(queue1, 1,queue2,1) );
        executor.execute( new Filter(queue1, 1, queue2,1) );
        executor.execute( new Consumer( queue2, 3) );

        executor.shutdown();
    }
}
