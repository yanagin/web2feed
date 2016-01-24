package orz.yanagin.web2feed.db;

import java.util.Date;

public class Queue {

	private long id;

	private String url;
	
	private Date createdAt;
	
	private Date updatedAt;

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
	
	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	@Override
	public String toString() {
		return "id->" + id
				+ " url->" + url
				+ " createdAt->" + createdAt
				+ " updatedAt->" + updatedAt;
	}

}
