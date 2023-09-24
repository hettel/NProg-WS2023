package part2.o4_synchronization;

import java.util.concurrent.ThreadLocalRandom;

public class Demo08
{
  public static void main(String[] args)
  {
    LockFreeStack<Integer> stack = new LockFreeStackLinkedList<>();

    Runnable producer = () -> {
      for(int i=0; i < 1_000; i++ )
      {
        int rd = ThreadLocalRandom.current().nextInt();
        stack.push(rd);
      }
      System.out.println("producer done");
    };
    
    Runnable consumer = () -> {
      for(int i=0; i < 1_000; i++ )
      {
        System.out.println( stack.pop() );
      }
      System.out.println("consumer done");
    };
    
    new Thread( producer ).start();
    new Thread( consumer ).start();
    
    // Note: the stack has not be empty because 
    // pop is not a blocking method
    System.out.println("main done");
  }

}
