package campsite;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.Test;

public class ConcurrentBookingsTest {

	@Test
	public void testConcurrentBookings() throws InterruptedException {
		ExecutorService executor = Executors.newFixedThreadPool(5);
		
		List<BookingTask> tasks = new ArrayList<>();
		Date today = new Date();
		
		tasks.add(new BookingTask(today, addDays(today, 1)));
		tasks.add(new BookingTask(addDays(today, 2), addDays(today, 3)));
		tasks.add(new BookingTask(addDays(today, 3), addDays(today, 4)));
		tasks.add(new BookingTask(addDays(today, 4), addDays(today, 5)));
		tasks.add(new BookingTask(addDays(today, 6), addDays(today, 7)));

		List<Future<String>> future = executor.invokeAll(tasks);
		
		future.stream().forEach(futValue -> {
			try {
				System.out.println(futValue.get());
			} catch (InterruptedException | ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		
		
	}
	
	private Date addDays(Date date, int i) {
		Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DAY_OF_MONTH, i);
        return c.getTime();
	}
	
}
