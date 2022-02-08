package net.nawaman.util;

import java.lang.reflect.*;
import java.util.*;

/** Iterator of an array. */
public class ArrayIterator<T> implements Iterator<T> {
	
	/** Creates a new array iterator wrapping a integer array. */
	static public ArrayIterator<Boolean> newArrayIterator(boolean[] pO) {
		return (pO == null)?null:new ArrayIterator<Boolean>(pO);
	}
	/** Creates a new array iterator wrapping a char array. */
	static public ArrayIterator<Character> newArrayIterator(char[] pO) {
		return (pO == null)?null:new ArrayIterator<Character>(pO);
	}
	/** Creates a new array iterator wrapping a byte array. */
	static public ArrayIterator<Byte> newArrayIterator(byte[] pO) {
		return (pO == null)?null:new ArrayIterator<Byte>(pO);
	}
	/** Creates a new array iterator wrapping a short array. */
	static public ArrayIterator<Short> newArrayIterator(short[] pO) {
		return (pO == null)?null:new ArrayIterator<Short>(pO);
	}
	/** Creates a new array iterator wrapping a int array. */
	static public ArrayIterator<Integer> newArrayIterator(int[] pO) {
		return (pO == null)?null:new ArrayIterator<Integer>(pO);
	}
	/** Creates a new array iterator wrapping a float array. */
	static public ArrayIterator<Float> newArrayIterator(float[] pO) {
		return (pO == null)?null:new ArrayIterator<Float>(pO);
	}
	/** Creates a new array iterator wrapping a double array. */
	static public ArrayIterator<Double> newArrayIterator(double[] pO) {
		return (pO == null)?null:new ArrayIterator<Double>(pO);
	}
	/** Creates a new array iterator wrapping an object array. */
	static public ArrayIterator<?> newArrayIterator(Object[] pO) {
		return (pO == null)?null:new ArrayIterator<Object>(pO);
	}
	/** Creates a new array iterator wrapping a data array. */
	static public <E> ArrayIterator<E> newArrayIterator(DataArray<E> pO) {
		return (pO == null)?null:new ArrayIterator<E>(pO);
	}
	
	int    C =    0;
	Object O = null;
	
	ArrayIterator(Object pO) { this.O = pO; }
	
	/** Returns <tt>true</tt> if the iteration has more elements. */
    public boolean hasNext() {
    	if(this.O instanceof DataArray<?>) return (this.C < ((DataArray<?>)this.O).getLength());
    	else                               return (this.C < Array.getLength(this.O));
    }

    /** Returns the next element in the iteration. */
    @SuppressWarnings("unchecked")
    public T next() {
    	if(!this.hasNext()) return null;
    	if(this.O instanceof DataArray) return (T)(((DataArray<T>)this.O).getData(this.C++));
    	else                            return (T)(Array.get(this.O, this.C++));
    }
    
    /** Removes from the underlying collection the last element returned by the iterator (not support).*/
    public void remove() {}

}
