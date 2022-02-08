package net.nawaman.util.db;

abstract public class ConnectionInfo {
	
	protected String getHostName() { return null; }
	
	abstract protected String getUserName();
	abstract protected String getPassword();
	
}