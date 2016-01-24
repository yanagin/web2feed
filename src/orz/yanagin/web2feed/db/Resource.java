package orz.yanagin.web2feed.db;

public class Resource {

	private long id;
	
	private String url;
	
	private String date;
	
	private String lastModified;
	
	private String title;
	
	private String description;
	
	private String body;
	
	private int hashCode;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getLastModified() {
		return lastModified;
	}

	public void setLastModified(String lastModified) {
		this.lastModified = lastModified;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public int getHashCode() {
		return hashCode;
	}

	public void setHashCode(int hashCode) {
		this.hashCode = hashCode;
	}
	
	@Override
	public String toString() {
		return "id->" + id
				+ " url->" + url
				+ " date->" + date
				+ " lastModified->" + lastModified
				+ " title->" + title
				+ " description->" + description
				+ " body->" + body
				+ " hashCode->" + hashCode;
	}

}
