package klausur2017.aufgabe3;

import java.util.concurrent.ThreadLocalRandom;

public class Aufgabe3
{
    public static void main(String[] args)
    {
        long max = 100_000_000L;
        long sum = 0;
        for(long l=0; l < max; l++)
        {
            double x = ThreadLocalRandom.current().nextDouble();
            double y = ThreadLocalRandom.current().nextDouble();

            if( x*x + y*y < 1 )
            {
                sum++;
            }
        }

        System.out.println("Result " + ((double) sum)/max);
        System.out.println("Vergleich " + Math.PI/4 );
    }
}
