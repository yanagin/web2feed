package orz.yanagin.web2feed.crawler;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class ResponseParserTest {
	
	static class MockResponseParser extends ResponseParser {
		
		List<String> links;

		public MockResponseParser(String url, List<String> links) {
			super(url, null);
			this.links = links;
		}
		
		@Override
		public List<String> getLinks() {
			return links;
		}
		
	}

	@Test
	public void testGetInternalLinks() {
		List<String> internalLinks = new MockResponseParser(
				"http://www.abc.com/", 
				Arrays.asList(
						"http://www.abc.com/1.html",
						"https://www.abc.com/2.html",
						"3.html",
						"/4.html",
						"http://www.def.com/5.html",
						"javascript:void(0);"
						)).getInternalLinks();

		assertThat(internalLinks.size(), is(4));
		assertThat(internalLinks.get(0), is("http://www.abc.com/1.html"));
		assertThat(internalLinks.get(1), is("https://www.abc.com/2.html"));
		assertThat(internalLinks.get(2), is("http://www.abc.com/3.html"));
		assertThat(internalLinks.get(3), is("http://www.abc.com/4.html"));
	}

	@Test
	public void testGetInternalLinks2() {
		List<String> internalLinks = new MockResponseParser(
				"https://www.abc.com", 
				Arrays.asList(
						"http://www.abc.com/1.html",
						"https://www.abc.com/2.html",
						"3.html",
						"/4.html",
						"http://www.def.com/5.html",
						"javascript:void(0);"
						)).getInternalLinks();

		assertThat(internalLinks.size(), is(4));
		assertThat(internalLinks.get(0), is("http://www.abc.com/1.html"));
		assertThat(internalLinks.get(1), is("https://www.abc.com/2.html"));
		assertThat(internalLinks.get(2), is("https://www.abc.com/3.html"));
		assertThat(internalLinks.get(3), is("https://www.abc.com/4.html"));
	}

	@Test
	public void testGetInternalLinks3() {
		List<String> internalLinks = new MockResponseParser(
				"https://www.abc.com/index.html", 
				Arrays.asList(
						"http://www.abc.com/1.html",
						"https://www.abc.com/2.html",
						"3.html",
						"/4.html",
						"http://www.def.com/5.html",
						"javascript:void(0);"
						)).getInternalLinks();

		assertThat(internalLinks.size(), is(4));
		assertThat(internalLinks.get(0), is("http://www.abc.com/1.html"));
		assertThat(internalLinks.get(1), is("https://www.abc.com/2.html"));
		assertThat(internalLinks.get(2), is("https://www.abc.com/3.html"));
		assertThat(internalLinks.get(3), is("https://www.abc.com/4.html"));
	}

}
