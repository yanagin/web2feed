package orz.yanagin.web2feed.feed;

import java.util.List;

import orz.yanagin.web2feed.db.Resource;
import orz.yanagin.web2feed.db.ResourceDao;

public class ResourceFeed {
	
	public static void main(String[] args) {
		ResourceDao resourceDao = new ResourceDao();
		System.out.println("count->" + resourceDao.getCountAll());
		
		
		List<Resource> resources = resourceDao.getResources(10);
		if (resources == null) {
			return;
		}
		
		for (Resource resourece : resources) {
			System.out.println(resourece);
		}
	}

}
