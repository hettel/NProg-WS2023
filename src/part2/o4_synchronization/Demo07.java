package part2.o4_synchronization;

import java.util.concurrent.atomic.AtomicBoolean;

public class Demo07
{

  public static void main(String[] args)
  {
    int[] array = new int[42];
    AtomicBoolean ready = new AtomicBoolean(false);

    // Caution: lambda with side effects
    Runnable writer = () -> {
      for ( int i = 0; i < array.length; i++ ) {
        array[i] = i;
      }

      ready.set(true);
    };

    // Caution: lambda with side effects
    Runnable reader = () -> {

      while ( ready.get() == false ) {
        ;
      }

      for ( int i = 0; i < array.length; i++ ) {
        System.out.println(array[i]);
      }
    };

    new Thread(writer).start();
    new Thread(reader).start();
  }
}
