package net.nawaman.util;

/** Object that is wrapped a non-Java object and need but sometime need to be appear as Native (Java Object) */
public interface NonNative {
	
	/** Returns the object as Naitve */
	public Object getAsNative();

}
