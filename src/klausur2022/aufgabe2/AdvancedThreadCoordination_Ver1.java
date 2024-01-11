package klausur2022.aufgabe2;

import java.util.concurrent.*;

public class AdvancedThreadCoordination_Ver1
{
	static class Task implements Runnable
	{
		private final String name;

		private final CountDownLatch latch;

		private final CyclicBarrier barrier;

		public Task(String name, CountDownLatch latch, CyclicBarrier barrier)
		{
			this.name = name;
            this.latch = latch;
            this.barrier = barrier;
        }

		@Override
		public void run()
		{
            try {
				latch.await();
                TimeUnit.MILLISECONDS.sleep(ThreadLocalRandom.current().nextInt(3000));
				System.out.println( name );
				barrier.await();
				System.out.println("Bye " + name );
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (BrokenBarrierException e) {
                throw new RuntimeException(e);
            }
        }
	}

	public static void main(String[] args) throws InterruptedException
	{
		CountDownLatch latch = new CountDownLatch(1);
		CyclicBarrier barrier = new CyclicBarrier(3);

		Thread th1 = new Thread( new Task("Task 1", latch, barrier) );
		Thread th2 = new Thread( new Task("Task 2", latch, barrier) );
		Thread th3 = new Thread( new Task("Task 3", latch, barrier) );

		System.out.println("Start");

		th1.start(); th2.start(); th3.start();

		TimeUnit.SECONDS.sleep(1);

		latch.countDown();

		th1.join(); th2.join(); th3.join();

		System.out.println("main done");

	}

}
