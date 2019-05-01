package net.nawaman.util.db;

public class DBInfo_MySQL extends DatabaseInfo {
	
	static public final DBInfo_MySQL Instance = new DBInfo_MySQL();
	
	static public String MySQL_DriverName   = "com.mysql.jdbc.Driver";
	static public String MySQL_ProtocalName = "jdbc:mysql://";
	static public String MySQL_DefaultPort  = "3306";
	
	public DBInfo_MySQL() { super(); }
	
	@Override protected String getDriverClassName()     { return MySQL_DriverName;   }
	@Override protected String getDefaultProtocalName() { return MySQL_ProtocalName; }
	@Override protected String getDefaultPort()         { return MySQL_DefaultPort;  }
	
}