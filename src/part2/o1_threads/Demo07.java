package part2.o1_threads;

public class Demo07
{
  static String result = null;
  static volatile boolean ready = false;
  
  public static void main(String[] args) throws Exception
  {
    Runnable task1 = () -> { 
        while( ready == false ) { ; } 
        result += "consistency"; 
    }; 
     
    Runnable task2 = () ->  { 
        result = "sequential "; 
        ready=true; 
    };

    Thread th1 = new Thread( task1 );
    Thread th2 = new Thread( task2 );
    
    th1.start();
    th2.start();
    
    th1.join(); 
    th2.join(); 
     
    System.out.println("Result: " + result );
  }
}
