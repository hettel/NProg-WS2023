package klausur2022.aufgabe1;

import java.util.concurrent.TimeUnit;

public class SimpleThreadCoordination
{
	static class Task1 implements Runnable
	{
		@Override
		public void run()
		{
			try {
				for( int i=0; i<2; i++)
				{

						TimeUnit.SECONDS.sleep(1);

					System.out.println("Thread 1");
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}


	static class Task2 implements Runnable
	{
		private final Thread th;
		private final String name;

		public Task2(Thread th, String name)
		{
			this.th = th;
			this.name = name;
		}

		@Override
		public void run()
		{
            try {
                th.join();
				System.out.println( name );
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
	}

	public static void main(String[] args)  throws InterruptedException
	{
		Thread th1 = new Thread( new Task1() );

		Thread th2 = new Thread( new Task2(th1, "Task2") ) ;
		Thread th3 = new Thread( new Task2(th1, "Task3") ) ;

		th1.start();
		th2.start();
		th3.start();

		th1.join();
		th2.join();
		th3.join();

		System.out.println("done");

	}

}
