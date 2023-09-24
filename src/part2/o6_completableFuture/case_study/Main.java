package part2.o6_completableFuture.case_study;

public class Main
{
  public static void main(String[] args)
  {
    // Simulates the creation of 20 orders
    System.out.println("create orders");
    
    Runnable task = () -> {
      OrderState state = OrderService.createOrder("abc", "hans", "007");
      System.out.println( state );
    };

    for(int i=0; i <20; i++)
    {
      new Thread( task ).start();
    }
  }
}
