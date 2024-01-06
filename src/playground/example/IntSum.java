package playground.example;

import java.util.OptionalInt;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

public class IntSum
{
    public static void main(String[] args)
    {
        long max =
        IntStream.range(0, ThreadLocalRandom.current().nextInt(1000) )
                 .count();

        System.out.println("Max " + max );
    }
}
