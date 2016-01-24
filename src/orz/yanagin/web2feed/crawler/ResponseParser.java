package orz.yanagin.web2feed.crawler;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import orz.yanagin.web2feed.db.Resource;

public class ResponseParser {

	private final String url;

	private final Response response;

	public ResponseParser(String url, Response response) {
		this.url = url;
		this.response = response;
	}

	public Resource getResource() {
		Resource resource = new Resource();
		resource.setUrl(url);
		resource.setDate(response.getHeaderValue("Date"));
		resource.setLastModified(response.getHeaderValue("Last-Modified"));
		resource.setBody(response.getBody());
		if (response.getBody() != null) {
			resource.setHashCode(response.getBody().hashCode());
		}
		return resource;
	}

	public List<String> getLinks() {
		List<String> links = new ArrayList<>();
		if (response == null || response.getBody() == null) {
			return links;
		}

		Pattern pattern = Pattern.compile("<a.*?href=\"(.*?)\".*?>(.*?)</a>", Pattern.DOTALL);
		Matcher matcher = pattern.matcher(response.getBody());
		while (matcher.find()) {
			String href = matcher.group(1).replaceAll("¥¥s", "");
			links.add(href);
		}
		return links;
	}

	public List<String> getInternalLinks() {
		List<String> internalLinks = new ArrayList<>();
		
		List<String> links = getLinks();
		if (url == null || links == null || links.isEmpty()) {
			return internalLinks;
		}

		int from = url.indexOf("//") + 2;
		int to = url.indexOf("/", from);
		if (to < 0) {
			to = url.length();
		}
		String domain = url.substring(from, to);
		String baseUrl = url.substring(0, to);

		for (String link : links) {
			if (link == null) {
				continue;
			}
			if (link.toLowerCase().startsWith("javascript")) {
				continue;
			}
			if (link.startsWith("http://" + domain)
					|| link.startsWith("https://" + domain)) {
				internalLinks.add(link);
				continue;
			}
			if (link.startsWith("/")) {
				internalLinks.add(baseUrl + link);
				continue;
			}
			if (!link.startsWith("http")) {
				internalLinks.add(baseUrl + "/" + link);
				continue;
			}
		}
		return internalLinks;
	}

}
