package orz.yanagin.web2feed.crawler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Response {
	
	private Map<String, List<String>> headers = new HashMap<>();
	
	private int statusCode;
	
	private String body;

	public Map<String, List<String>> getHeaders() {
		return headers;
	}

	public void setHeaders(Map<String, List<String>> headers) {
		this.headers = headers;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getHeaderValue(String name) {
		List<String> list = getHeaders().get(name);
		if (list == null || list.isEmpty()) {
			return null;
		}
		return list.get(0);
	}

}
