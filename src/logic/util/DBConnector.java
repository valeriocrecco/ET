package logic.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import logic.exceptions.SystemException;

public class DBConnector {
	
	private static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/mydb_progettoISPW?useUnicode=true&characterEncoding=utf8&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=UTC";
	private static final String USER = "root";
	private static final String PASS = "valerio23";
    private static final String DRIVER_CLASS_NAME = "com.mysql.cj.jdbc.Driver";
    private static DBConnector dbConnectorInstance = null;
    private Connection connection;
    
	protected DBConnector() throws SystemException {
		try {
			Class.forName(DRIVER_CLASS_NAME);
			connection = DriverManager.getConnection(DB_URL,USER,PASS);
		} catch (ClassNotFoundException e) {
			Logger logger = Logger.getLogger(DBConnector.class.getName());
			logger.log(Level.WARNING, "DB opening error");
	    } catch (SQLException e) {
			throw new SystemException("Unexpected error occured, please try later!");
		}
	}
    
	public static synchronized DBConnector getDBConnectorInstance() throws SystemException {
		if(dbConnectorInstance == null) {
			dbConnectorInstance = new DBConnector();
		}
		return dbConnectorInstance;
	}
	
	public Connection getConnection() {
		return this.connection;
	}
	
	public void releaseConnection() {
		try {
			connection.close();
		} catch (SQLException e) {
			Logger logger = Logger.getLogger(DBConnector.class.getName());
			logger.log(Level.WARNING, "DB close error");
		}
	}
	
	
	
}
