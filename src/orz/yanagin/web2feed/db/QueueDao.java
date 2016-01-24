package orz.yanagin.web2feed.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class QueueDao extends DaoBase {
	
	static class QueueResultSetMapper implements ResultSetMapper<Queue> {

		@Override
		public Queue getResult(ResultSet resultSet) throws SQLException {
			Queue queue = new Queue();
			queue.setId(resultSet.getLong("id"));
			queue.setUrl(resultSet.getString("url"));
			queue.setCreatedAt(resultSet.getDate("created_at"));
			queue.setUpdatedAt(resultSet.getDate("updated_at"));
			return queue;
		}
		
	}
	
	public List<Queue> getQueues(int limit) {
		return getResults(
				"SELECT id, url, created_at, updated_at FROM queue WHERE updated_at IS NULL ORDER BY created_at LIMIT ?", 
				new QueueResultSetMapper(), 
				limit);
	}
	
	public long getCount(String url) {
		return getLong(
				"SELECT COUNT(*) FROM queue WHERE url = ? AND updated_at IS NULL",
				url);
	}
	
	public long getCountAll() {
		return getLong(
				"SELECT COUNT(*) FROM queue WHERE updated_at IS NULL");
	}
	
	public void insert(String url) {
		execute(
				"INSERT INTO queue (id, url) VALUES (?, ?)",
				getSequence("queue"), url);
	}
	
	public void updateStatus(long id, String status) {
		execute(
				"UPDATE queue SET status = ?, updated_at = NOW() WHERE id = ?", 
				status, id);
	}

}
