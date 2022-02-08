package net.nawaman.util.db;

abstract public class DatabaseInfo {
	
	static public final String LocalHost = "localhost";
		
	public DatabaseInfo() {}
	
	// Customized

	abstract protected String getDriverClassName();
	
	abstract protected String getDefaultProtocalName();
	abstract protected String getDefaultPort();
	
	protected boolean isUseUnicode() { return true; }
	
	// Service

	public String getDB_URL(String DBName) {
		return this.getDB_URL(null, null, null, DBName, null);
	}
	public String getDB_URL(String HostName, String DBName) {
		return this.getDB_URL(null, HostName, null, DBName, null);
	}
	public String getDB_URL(String HostName, String DBName, String Port) {
		return this.getDB_URL(null, HostName, Port, DBName, null);
	}
	public String getDB_URL(String ProtocalName, String HostName, String DBName, String Port) {
		return this.getDB_URL(ProtocalName, HostName, Port, DBName, null);
	}
	public String getDB_URL(String ProtocalName, String HostName, String Port, String DBName, String Option) {
		if(ProtocalName == null) ProtocalName = this.getDefaultProtocalName();
		if(HostName     == null) HostName     = LocalHost;
		if(Port         == null) Port         = this.getDefaultPort();
		if(Option       == null) Option       = this.isUseUnicode()?"?useUnicode=true&characterEncoding=UTF-8":"";
		
		return ProtocalName + HostName + ":" + Port + "/" + DBName + Option;
	}
	
}