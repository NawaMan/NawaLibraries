package net.nawaman.util.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

abstract public class Database {

	DatabaseInfo DBInfo = null;
	Connection   Conn   = null;
	String       DBName = null;
	
	public Database(DatabaseInfo pDBInfo, ConnectionInfo pConnInfo, String pDBName) {
		this.DBInfo = pDBInfo;
		this.DBName = pDBName;
		try {
			Class.forName(this.DBInfo.getDriverClassName());
			String URL = this.DBInfo.getDB_URL(pConnInfo.getHostName(), pDBName);
			this.Conn = DriverManager.getConnection(URL, pConnInfo.getUserName(), pConnInfo.getPassword());
		} catch (ClassNotFoundException E) {
			throw new RuntimeException("MySQL driver is not found.", E);
		} catch (SQLException E) {
			throw new RuntimeException("Unable to connect to the database.", E);
		}
	}
	
	public Statement createStatement() { try { return this.Conn.createStatement(); } catch(Exception E) { return null; } }
	
	public void disconnect() { try {  this.Conn.close(); } catch(Exception E) {} }
}