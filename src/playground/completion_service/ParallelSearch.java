package playground.completion_service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ParallelSearch {


	static class Task implements  Callable<String>
	{

		@Override
		public String call() throws Exception
		{
			String threadName = Thread.currentThread().getName();

			System.out.println("Start searching " + threadName );
			long startTime = System.currentTimeMillis();

			long counter = 0;
			String str = "";
			while (true)
			{
				str = Util.getRandomString(100);
				int hashValue = str.hashCode();
				counter++;

				if (0 < hashValue && hashValue < 500)
				{
					break;
				}

				if( Thread.currentThread().isInterrupted() )
				{
					return "";
				}

			}

			long endTime = System.currentTimeMillis();
			System.out.println(threadName + " String " + str);
			System.out.println(threadName + " Found hash value : " + str.hashCode() );
			System.out.println(threadName + " Number of tries  : " + counter);

			System.out.println(threadName + " Time elapsed     : " + (endTime - startTime) + " [ms]");

			return str;
		}
	}

	public static void main(String[] args) throws Exception
	{
		int numOfProc = Runtime.getRuntime().availableProcessors();
		ExecutorService executor = Executors.newFixedThreadPool( numOfProc );
		CompletionService<String> completionService = new ExecutorCompletionService(executor);

		List<Task> callables = new ArrayList<>();
		for(int i=0; i<numOfProc; i++)
		{
			callables.add( new Task() );
		}

		for(Task task : callables)
		{
			completionService.submit( task );
		}

		String result = completionService.take().get();
		System.out.println("Main String found " + result );

		executor.shutdownNow();
	}
}
