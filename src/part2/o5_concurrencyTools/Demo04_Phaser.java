package part2.o5_concurrencyTools;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class Demo04_Phaser
{
  static class GamePlayerTask implements Runnable
  {
    private final String name;
    private final Phaser phaser;
    
    public GamePlayerTask(String name, Phaser phaser)
    {
      this.name = name;
      this.phaser = phaser;
    }
   
    @Override
    public void run()
    {
      phaser.register();
      
      while(true)
      {
        // throw the dice (a dice with 10 possibilities)
        int num = ThreadLocalRandom.current().nextInt(10);
        
        if( num == 3 )
        {
          System.out.println( name + " leaves the game");
          phaser.arriveAndDeregister();
          break;
        }
        else
        {
          phaser.arriveAndAwaitAdvance();
        }
        
        if( phaser.getRegisteredParties() == 1 )
        {
          // Only this player is left
          System.out.println( name + " has won the game");
          phaser.arriveAndDeregister();
          System.out.println("game over");
          break;
        }
      }
    }
  }
  
  public static void main(String[] args) throws InterruptedException
  {
    Phaser phaser = new Phaser(){
      protected boolean onAdvance(int phase, int registeredParties) 
      {
        if( registeredParties <= 1 )
        {
          return true;
        }
        else
        {
          sleep( 1500, TimeUnit.MILLISECONDS );
          System.out.println("Next round");
          return false;
        }
      }
    };
    
    ExecutorService executor = Executors.newFixedThreadPool(4);
    executor.execute( new GamePlayerTask("A", phaser) );
    executor.execute( new GamePlayerTask("B", phaser) );
    executor.execute( new GamePlayerTask("C", phaser) );
    executor.execute( new GamePlayerTask("D", phaser) );
    
    System.out.println("Game started");
    executor.shutdown();
  }
  
  private static void sleep(int time, TimeUnit unit) 
  {
    try
    {
      unit.sleep(time);
    }
    catch (InterruptedException e)
    {
      e.printStackTrace();
    }
  }
}
