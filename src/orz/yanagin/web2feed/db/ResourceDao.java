package orz.yanagin.web2feed.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ResourceDao extends DaoBase {

	static class ResourceResultSetMapper implements ResultSetMapper<Resource> {

		@Override
		public Resource getResult(ResultSet resultSet) throws SQLException {
			Resource resource = new Resource();
			resource.setId(resultSet.getLong("id"));
			resource.setUrl(resultSet.getString("url"));
			resource.setDate(resultSet.getString("date"));
			resource.setLastModified(resultSet.getString("last_modified"));
			return resource;
		}
		
	}
	
	public String getLatestDate(String url) {
		return getString(
				"SELECT date FROM resource WHERE url = ? ORDER BY created_at DESC LIMIT 1", 
				url);
	}
	
	public long getByDate(String url, String date) {
		return getLong(
				"SELECT COUNT(*) FROM resource WHERE url = ? AND date = ?", 
				url, date);
	}
	
	public long getByLastModified(String url, String lastModified) {
		return getLong(
				"SELECT COUNT(*) FROM resource WHERE url = ? AND last_modified = ?", 
				url, lastModified);
	}
	
	public long getByHashCode(String url, int hashCode) {
		return getLong(
				"SELECT COUNT(*) FROM resource WHERE url = ? AND hash_code = ?", 
				url, hashCode);
	}
	
	public List<Resource> getResources(int limit) {
		return getResults(
				"SELECT id, url, date, last_modified FROM resource ORDER BY id DESC LIMIT ?", 
				new ResourceResultSetMapper(), 
				limit);
	}
	
	public long getCountAll() {
		return getLong(
				"SELECT COUNT(*) FROM resource");
	}
	
	public void insert(Resource resource) {
		execute(
				"INSERT INTO resource (id, url, date, last_modified, title, description, body, hash_code, created_at) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, NOW())", 
				getSequence("resource"), resource.getUrl(), resource.getDate(), resource.getLastModified(), resource.getTitle(), 
				resource.getDescription(), resource.getBody(), resource.getHashCode());
	}

}
