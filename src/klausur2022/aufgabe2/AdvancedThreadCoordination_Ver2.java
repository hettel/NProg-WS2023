package klausur2022.aufgabe2;

import java.util.concurrent.*;

public class AdvancedThreadCoordination_Ver2
{
	static class Task implements Runnable
	{
		private final String name;

		private final CountDownLatch latch;

		private final CyclicBarrier barrier;

		private final CyclicBarrier endBarrier;

		public Task(String name, CountDownLatch latch, CyclicBarrier barrier, CyclicBarrier endBarrier)
		{
			this.name = name;
            this.latch = latch;
            this.barrier = barrier;
            this.endBarrier = endBarrier;
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

				endBarrier.await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (BrokenBarrierException e) {
                throw new RuntimeException(e);
            }
        }
	}

	public static void main(String[] args) throws Exception
	{
		CountDownLatch latch = new CountDownLatch(1);
		CyclicBarrier barrier = new CyclicBarrier(3);
		CyclicBarrier endBarrier = new CyclicBarrier(4);

		Thread th1 = new Thread( new Task("Task 1", latch, barrier, endBarrier ) );
		Thread th2 = new Thread( new Task("Task 2", latch, barrier, endBarrier) );
		Thread th3 = new Thread( new Task("Task 3", latch, barrier, endBarrier) );

		System.out.println("Start");

		th1.start(); th2.start(); th3.start();

		TimeUnit.SECONDS.sleep(1);

		latch.countDown();

		endBarrier.await();

		System.out.println("main done");

	}

}
