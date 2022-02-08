package net.nawaman.util;

import java.util.*;

/** Holder of fields. */
public interface FieldHolder extends Iterable<Object> {
	
	/** Returns the number of fields that this object hold. */
	public int      getFieldCount();
	/** Returns an array of field names. */
	public String[] getFieldNames();
	
	/** Returns the data class of the field. */
	public Class<?> getDataClass(String pName);

	/** Set pData into the field named pName and return if that has success. */
	public Object setData(String pName, Object pData);
	/** Returns the value of the field named pName */
	public Object getData(String pName);
	
	/** Returns an iterator that can iterate field value */
	public Iterator<Object> newIterator();

}
