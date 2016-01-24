package orz.yanagin.web2feed.crawler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import orz.yanagin.web2feed.db.DatabaseManager;
import orz.yanagin.web2feed.db.Queue;
import orz.yanagin.web2feed.db.QueueDao;
import orz.yanagin.web2feed.db.Resource;
import orz.yanagin.web2feed.db.ResourceDao;

public class Crawler {
	
	final Logger logger = LoggerFactory.getLogger(this.getClass());

	public static void main(String[] args) {
		new Crawler().start();
	}
	
	public void start() {
		QueueDao queueDao = new QueueDao();
		List<Queue> queues = queueDao.getQueues(3);
		if (queues == null || queues.isEmpty()) {
			return;
		}
		
		for (Queue queue : queues) {
			try {
				ResponseParser respone = crawl(queue.getUrl());
				List<String> internalLinks = respone.getInternalLinks();
				if (internalLinks == null) {
					continue;
				}
				
				for (String internalLink : internalLinks) {
					if (queueDao.getCount(internalLink) > 0) {
						logger.info("キューに既に登録されているためスキップします。internalLink->" + internalLink);
						continue;
					}
					
					queueDao.insert(internalLink);
					logger.info("キューに内部リンクを登録しました。internalLink->" + internalLink);
				}
				
				queueDao.updateStatus(queue.getId(), "success");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
				queueDao.updateStatus(queue.getId(), "error");
			} finally {
				DatabaseManager.commit();
			}
		}
		
		DatabaseManager.finalyze();
	}
	
	ResponseParser crawl(String url) throws MalformedURLException, IOException {
		try {
			logger.info("クロールを開始します。url->" + url);
			
			ResourceDao resourceDao = new ResourceDao();

			Request request = new Request();
			request.setUrl(url);
			request.setDate(resourceDao.getLatestDate(url));
			
			Response response = getResponse(request);
			ResponseParser responseParser = new ResponseParser(request.getUrl(), response);
			Resource resource = responseParser.getResource();
			
			if (resource.getDate() != null 
					&& resourceDao.getByDate(request.getUrl(), resource.getDate()) > 0) {
				logger.info("Dateヘッダが更新されていないのでリソースの登録を行いません。url->" + request.getUrl() + " Date->" + resource.getDate());
				return responseParser;
			}
			if (resource.getLastModified() != null 
					&& resourceDao.getByLastModified(request.getUrl(), resource.getLastModified()) > 0) {
				logger.info("Dateヘッダが更新されていないのでリソースの登録を行いません。url->" + request.getUrl() + " Last-Modified->" + resource.getLastModified());
				return responseParser;
			}
			if (resource.getBody() != null
					&& resourceDao.getByHashCode(request.getUrl(), resource.getBody().hashCode()) > 0) {
				logger.info("hashCodeが変わらないのでリソースの登録を行いません。url->" + request.getUrl() + " Last-Modified->" + resource.getLastModified());
				return responseParser;
			}
			
			resourceDao.insert(resource);
			logger.info("リソースを登録しました。url->" + request.getUrl());
						
			return responseParser;
		} finally {
			logger.info("クロールを終了します。url->" + url);
		}
	}
	
	Response getResponse(Request request) throws MalformedURLException, IOException {
		HttpURLConnection connection = null;
		BufferedReader reader = null;
		try {
			connection = (HttpURLConnection)new URL(request.getUrl()).openConnection();
			connection.setRequestMethod("GET");
			connection.setInstanceFollowRedirects(true);
			if (request.getDate() != null) {
				connection.setRequestProperty("If-Modified-Since", request.getDate());
			}
			connection.setReadTimeout(15 * 1000);
			connection.setReadTimeout(30 * 1000);
			connection.connect();

			Response response = new Response();
			
			response.getHeaders().putAll(connection.getHeaderFields());
			
			connection.getInputStream();
			reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			StringBuilder body = new StringBuilder();
			String line;
			while((line = reader.readLine()) != null) {
				body.append(line);
			}
			response.setBody(body.toString());
			
			return response;
		} finally {
			if (reader != null) {
				reader.close();
			}
			if (connection != null) {
				connection.disconnect();
			}
		}
	}
	
}
