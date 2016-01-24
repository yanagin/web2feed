package orz.yanagin.web2feed.feed;

import java.util.List;

import orz.yanagin.web2feed.db.Queue;
import orz.yanagin.web2feed.db.QueueDao;

public class QueueFeed {

	public static void main(String[] args) {
		QueueDao queueDao = new QueueDao();
		System.out.println("count->" + queueDao.getCountAll());
		
		List<Queue> queues = queueDao.getQueues(10);
		if (queues == null) {
			return;
		}
		
		for (Queue queue : queues) {
			System.out.println(queue);
		}
	}
	
}
