package net.nawaman.util;

/** DataHolder holds data - think of it as a variable. */
public interface DataHolder {
	
	/** Sets the value to the holder and return true if success. */
	public Object setData(Object pValue);
	
	/** Returns the value that this data holder holds. */
	public Object getData();

}
