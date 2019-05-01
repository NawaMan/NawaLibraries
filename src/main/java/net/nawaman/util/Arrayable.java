package net.nawaman.util;

import java.util.Iterator;

/** Objects that can be accessed as an array */
public interface Arrayable<ElementType> extends Iterable<ElementType>, MightBeImmutable {

	/** Changes the element value. */
	public ElementType set(int I, ElementType Element);
	
	/** Returns the element value. */
	public ElementType get(int I);
	
	/** Returns the length of the array. */
	public int length();
	
	/** Checks if this array is immutable. */
	public boolean isImmutable();
	
	/** Returns an iterator over a set of elements. */
	public Iterator<ElementType> iterator();
	
}
