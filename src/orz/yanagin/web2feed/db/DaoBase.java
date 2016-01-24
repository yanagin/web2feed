package orz.yanagin.web2feed.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class DaoBase {
	
	public static interface ResultSetMapper<T> {
		
		T getResult(ResultSet resultSet) throws SQLException;
		
	}
	
	PreparedStatement getPreparedStatement(Connection connection, String sql, Object...values) throws SQLException {
		PreparedStatement prepareStatement = connection.prepareStatement(sql);
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				Object value = values[i];
				if (value == null) {
					prepareStatement.setNull(i + 1, java.sql.Types.NULL);
					continue;
				}
				if (value instanceof String) {
					prepareStatement.setString(i + 1, (String)value);
					continue;
				}
				if (value instanceof Integer) {
					prepareStatement.setInt(i + 1, (Integer)value);
					continue;
				}
				if (value instanceof Long) {
					prepareStatement.setLong(i + 1, (Long)value);
					continue;
				}
				if (value instanceof Date) {
					prepareStatement.setDate(i + 1, new java.sql.Date(((Date)value).getTime()));
					continue;
				}
				
				
				throw new IllegalArgumentException("type:" + value.getClass() + " is not supported");
			}
		}
		return prepareStatement;
	}

	protected int execute(String sql, Object... values) {
		Connection connection = null;
		try {
			connection = DatabaseManager.getConnection();
			PreparedStatement prepareStatement = getPreparedStatement(connection, sql, values);
			return prepareStatement.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeSQLException(e);
		}
	}
	
	protected <E> List<E> getResults(String sql, ResultSetMapper<E> mapper, Object... values) {
		Connection connection = null;
		try {
			connection = DatabaseManager.getConnection();
			PreparedStatement prepareStatement = getPreparedStatement(connection, sql, values);
			ResultSet resultSet = prepareStatement.executeQuery();
			List<E> result = new ArrayList<>();
			while (resultSet.next()) {
				result.add(mapper.getResult(resultSet));
			}
			return result;
		} catch (SQLException e) {
			throw new RuntimeSQLException(e);
		}
	}
	
	protected <E> E getSingleResult(String sql, ResultSetMapper<E> mapper, Object... values) {
		List<E> results = getResults(sql, mapper, values);
		return (results != null && !results.isEmpty()) ? results.get(0) : null;
	}
	
	protected long getLong(String sql, Object... values) {
		ResultSetMapper<Long> longMapper = new ResultSetMapper<Long>() {
			@Override
			public Long getResult(ResultSet resultSet) throws SQLException {
				return resultSet.getLong(1);
			}
		};
		return getSingleResult(
				sql, 
				longMapper,
				values);
	}
	
	protected String getString(String sql, Object... values) {
		ResultSetMapper<String> stringMapper = new ResultSetMapper<String>() {
			@Override
			public String getResult(ResultSet resultSet) throws SQLException {
				return resultSet.getString(1);
			}
		};
		return getSingleResult(
				sql, 
				stringMapper,
				values);
	}
	
	protected Date getDate(String sql, Object... values) {
		ResultSetMapper<Date> dateMapper = new ResultSetMapper<Date>() {
			@Override
			public Date getResult(ResultSet resultSet) throws SQLException {
				java.sql.Date date = resultSet.getDate(1);
				return new Date(date.getTime());
			}
		};
		return getSingleResult(
				sql, 
				dateMapper,
				values);
	}
	
	protected long getSequence(String tableName) {
		return getLong("VALUES NEXT VALUE FOR seq_" + tableName);
	}

}
