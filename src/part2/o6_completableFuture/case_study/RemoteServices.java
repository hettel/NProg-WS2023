package part2.o6_completableFuture.case_study;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public final class RemoteServices
{
  private RemoteServices() {}
  
  public static OrderState verifyCustomer(String customerId)
  {
    System.out.println("verifyCustomer " + Thread.currentThread().getName() );
    randomDelay();
    int rand = ThreadLocalRandom.current().nextInt(100);
    if( rand < 80 )
      return OrderState.Approved;
    else
      return OrderState.Rejected;
  }
  
  public static OrderState authorizeCard(String cardId)
  {
    System.out.println("authorizeCard " + Thread.currentThread().getName() );
    randomDelay();
    int rand = ThreadLocalRandom.current().nextInt(100);
    if( rand < 80 )
      return OrderState.Approved;
    else
      return OrderState.Rejected;
  }
  
  
  public static OrderState enqueueTicket(String ticketId)
  {
    System.out.println("enqueueTicket " + Thread.currentThread().getName() );
    randomDelay();
    int rand = ThreadLocalRandom.current().nextInt(100);
    if( rand < 80 )
      return OrderState.Approved;
    else
      return OrderState.Rejected;
  }
  
  public static void dequeueTicket(String ticketId)
  {
    randomDelay();
    int rand = ThreadLocalRandom.current().nextInt(100);
    if( rand < 50 )
      throw new RuntimeException("Cannot dequeue the ticket");
  }
  
  private static void randomDelay()
  {
    try
    {
      TimeUnit.MILLISECONDS.sleep( 500 + ThreadLocalRandom.current().nextInt(2000) );
    }
    catch (InterruptedException e)
    {
      e.printStackTrace();
    }
  }
}
