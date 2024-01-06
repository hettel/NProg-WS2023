package playground.example;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

public class MaxValue
{
    static AtomicInteger atomicCounter = new AtomicInteger();

    private static class FindMaxTask extends RecursiveTask<Integer>
    {
        private final int[] array;
        private final int start;
        private final int end;

        FindMaxTask(int[] array, int start, int end)
        {
            this.array = array;
            this.start = start;
            this.end   = end;

            atomicCounter.incrementAndGet();
        }

        @Override
        protected Integer compute()
        {
            // Abbruchbedingung
            if( end - start < 10 )
            {
                int max = array[start];
                for(int i = start+1; i < end; i++ )
                    if( array[i] > max )
                        max = array[i];

                return max;
            }

            // Teilung
            int mid = (start + end)/2;
            FindMaxTask left  = new FindMaxTask( array, start, mid );
            FindMaxTask right = new FindMaxTask( array, mid,  end );
            invokeAll( left, right );
            return Math.max(left.join(),right.join());
        }
    }

    public static void main(String[] args)
    {
        int[] array = new int[1_000_000];
        Arrays.parallelSetAll( array, (i) -> ThreadLocalRandom.current().nextInt() );

        long startTime = System.currentTimeMillis();
        int max = array[0];
        for ( int i=1; i < array.length; i++ )
        {
            if( array[i] > max )
                max = array[i];
        }
        System.out.println("Dauer " + (System.currentTimeMillis() - startTime ) + "[ms]" );

        System.out.println("max " + max );

        startTime = System.currentTimeMillis();
        int max2 = max_recursive(array,0, array.length);
        System.out.println("Dauer " + (System.currentTimeMillis() - startTime ) + "[ms]" );
        System.out.println("max2 " + max2 );
        System.out.println("count " + count );


        startTime = System.currentTimeMillis();
        FindMaxTask root = new FindMaxTask(array,0, array.length );
        ForkJoinPool.commonPool().execute(root);

        System.out.println( "max3 " + root.join() );
        System.out.println("Dauer " + (System.currentTimeMillis() - startTime ) + "[ms]" );

        startTime = System.currentTimeMillis();

        System.out.println( "max4 " + Arrays.stream(array).parallel().max() );
        System.out.println("Dauer " + (System.currentTimeMillis() - startTime ) + "[ms]" );
        System.out.println("Anzahl " + atomicCounter.get() );
    }

    private static int count = 0;

    public static int max_recursive( int[] array, int start, int end )
    {
        count++;

        // Abbruchbedingung
        if( end - start < 10 )
        {
            int max = array[start];
            for(int i = start+1; i < end; i++ )
                if( array[i] > max )
                    max = array[i];

            return max;
        }


        // Teilung
        int mid = (start + end)/2;
        int left  = max_recursive( array, start, mid );
        int right = max_recursive( array, mid,  end );
        return Math.max(left,right);

    }
}
