package orz.yanagin.web2feed.crawler;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import orz.yanagin.web2feed.crawler.Crawler;
import orz.yanagin.web2feed.crawler.Request;

public class CrawlerTest {

	@Test
	public void testGetResponse() throws MalformedURLException, IOException {
		Request request = new Request();
		request.setUrl("http://www.lawson.co.jp/");
		Response response = new Crawler().getResponse(request);
		System.out.println("------------------------------------");
		Map<String, List<String>> headers = response.getHeaders();
		for (String key : headers.keySet()) {
			System.out.println(key + "->" + headers.get(key));
		}
		System.out.println("------------------------------------");
		System.out.println(response.getBody().hashCode());
	}
	
}
