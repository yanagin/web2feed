package orz.yanagin.web2feed.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {
	
	private static Connection connection;

	public static synchronized void initialize() {
		if (connection != null) {
			return;
		}
		try {
			connection = DriverManager.getConnection("jdbc:hsqldb:file:db/web2feed;shutdown=true;hsqldb.write_delay=false;", "SA", "");
		} catch (SQLException e) {
			throw new RuntimeSQLException(e);
		}
	}

	static Connection getConnection() throws SQLException {
		initialize();
		return connection;
	}

	public static void commit() {
		if (connection == null) {
			return;
		}
		try {
			connection.commit();
		} catch (SQLException e) {
			throw new RuntimeSQLException(e);
		}
	}
	
	public static void finalyze() {
		if (connection == null) {
			return;
		}
		try {
			connection.close();
		} catch (SQLException e) {
			throw new RuntimeSQLException(e);
		}
	}

}
