package klausur2017.aufgabe6;

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.LongStream;

public class Aufgabe6
{
    record Point(double x, double y){}
    public static void main(String[] args)
    {
        long N = 10_000_000_000L;
        LongStream lStream = LongStream.range(0, N);

        long A = lStream.parallel()
                .mapToObj( l -> new Point( ThreadLocalRandom.current().nextDouble(), ThreadLocalRandom.current().nextDouble()))
                .filter( r -> r.x()*r.x() + r.y()*r.y() < 1.0 )
                .count();

        double pi = (4.0*A)/N;
        System.out.println("Approximation von " + pi );
        System.out.println("Vergleich " + Math.PI );
    }
}
