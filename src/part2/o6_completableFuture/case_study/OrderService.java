package part2.o6_completableFuture.case_study;

import java.util.concurrent.CompletableFuture;

public final class OrderService
{
  private OrderService() {}
  
  public static OrderState createOrder(String ticketId, String userId, String cardId )
  {
    CompletableFuture<OrderState> customerVerfification; 
    CompletableFuture<OrderState> cardAuthorization;
    CompletableFuture<OrderState> ticketCreation;
    
    // For an order, several things must be done:
    // - verify customer
    // - check card
    // - create a process ticket
    CompletableFuture.allOf( 
        customerVerfification = CompletableFuture.supplyAsync( () -> RemoteServices.verifyCustomer(userId) ),
        cardAuthorization = CompletableFuture.supplyAsync( () -> RemoteServices.authorizeCard(cardId) ),
        ticketCreation = CompletableFuture.supplyAsync( () -> RemoteServices.enqueueTicket(ticketId) )
        ).join();
    
    OrderState globalState = OrderState.check( customerVerfification.join(), cardAuthorization.join(), ticketCreation.join() );
    
    if( globalState == OrderState.Rejected )
    {
      CompletableFuture.runAsync( () ->RemoteServices.dequeueTicket(ticketId) )
                       .exceptionally( e -> { System.err.println( e.getMessage()); return (Void) null;} );
    }
    
    return globalState;
  }
}
