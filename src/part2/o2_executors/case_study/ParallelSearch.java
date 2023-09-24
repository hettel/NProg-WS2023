package part2.o2_executors.case_study;

import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ParallelSearch
{

  public static void main(String[] args) throws Exception
  {
    System.out.println("Start searching");
    long startTime = System.currentTimeMillis();
    
    int nThreads = Runtime.getRuntime().availableProcessors();
    
    ExecutorService executor = Executors.newFixedThreadPool( nThreads );

    CompletionService<String> completionService 
                             = new ExecutorCompletionService< >( executor );

    for(int i=0; i < nThreads; i++)
    {
      completionService.submit( new SearchTask() );
    }
    
    String result = completionService.take().get();
    // Interrupt all worker in the pool
    executor.shutdownNow();
    
    System.out.println("Found " + result.hashCode() );

    long endTime = System.currentTimeMillis();
    System.out.println("Elapsed Time " + (endTime - startTime) + " [ms]");
  }
}
