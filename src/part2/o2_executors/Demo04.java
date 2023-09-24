package part2.o2_executors;

import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

public class Demo04
{
  public static void main(String[] args) throws Exception
  {
    System.out.println("Start");
    int parallel = Runtime.getRuntime().availableProcessors();
    int bitLen = 4048;

    List<Callable<BigInteger>> tasks = new ArrayList<>();
    for (int i = 0; i < parallel; i++)
    {
      tasks.add( () -> BigInteger.probablePrime(bitLen, ThreadLocalRandom.current()) );
    }
    
    ExecutorService executor = Executors.newFixedThreadPool(parallel);
    CompletionService<BigInteger> completionService = 
                            new ExecutorCompletionService<>(executor);
    
    tasks.forEach(completionService::submit);

    NumberFormat formatter = new DecimalFormat("0.###E0", 
                       DecimalFormatSymbols.getInstance(Locale.ROOT));
    for (int i = 0; i < 2; i++)
    {
      BigInteger result = completionService.take().get();
      System.out.println( formatter.format(result) );
    }

    executor.shutdownNow();
    System.out.println("done");
  }
}
