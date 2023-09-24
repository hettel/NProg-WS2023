package part2.o2_executors.case_study;

import java.util.concurrent.Callable;

public class SearchTask implements Callable<String>
{
  @Override
  public String call() throws Exception
  {
    // Gets the executing thread
    Thread thread = Thread.currentThread();
    System.out.println("Start searching: " + thread.getName());

    // Check if the work should proceed
    while (thread.isInterrupted() == false)
    {
      String candidate = Util.getRandomString(100);
      int hashValue = candidate.hashCode();

      if (0 < hashValue && hashValue < 500)
      {
        return candidate;
      }
    }

    System.out.println("Thread aborted " + thread.getName());
    throw new InterruptedException();
  }

}
