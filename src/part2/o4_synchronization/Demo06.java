package part2.o4_synchronization;

public class Demo06
{

  public static void main(String[] args)
  {
    int[] array = new int[42];
    boolean[] ready = new boolean[1];
   
    // Caution: lambda with side effects
    Runnable writer = () -> {
      for(int i=0; i < array.length; i++)
      {
        array[i] = i;
      }
      
      ready[0] = true;
    };
    
    // Caution: lambda with side effects
    Runnable reader = () -> {
      
      while( ready[0] == false ) {;}
      
      for(int i=0; i < array.length; i++)
      {
        System.out.println( array[i] );
      }
    };
    
    new Thread( writer ).start();
    new Thread( reader ).start();
  }

}
