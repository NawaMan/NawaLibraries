package net.nawaman.util;

import java.util.*;
  
public class ArrayIterable<T> implements Iterable<T> {
	
	/** Creates a new array iterator wrapping a integer array. */
	@SuppressWarnings("unchecked")
	static public ArrayIterable<Boolean> newArrayIterable(boolean[] pO) {
		return (pO == null)?null:(ArrayIterable<Boolean>)new ArrayIterable(ArrayIterator.newArrayIterator(pO));
	}
	/** Creates a new array iterator wrapping a char array. */
	@SuppressWarnings("unchecked")
	static public ArrayIterable<Character> newArrayIterable(char[] pO) {
		return (pO == null)?null:(ArrayIterable<Character>)new ArrayIterable(ArrayIterator.newArrayIterator(pO));
	}
	/** Creates a new array iterator wrapping a byte array. */
	@SuppressWarnings("unchecked")
	static public ArrayIterable<Byte> newArrayIterable(byte[] pO) {
		return (pO == null)?null:(ArrayIterable<Byte>)new ArrayIterable(ArrayIterator.newArrayIterator(pO));
	}
	/** Creates a new array iterator wrapping a short array. */
	@SuppressWarnings("unchecked")
	static public ArrayIterable<Short> newArrayIterable(short[] pO) {
		return (pO == null)?null:(ArrayIterable<Short>)new ArrayIterable(ArrayIterator.newArrayIterator(pO));
	}
	/** Creates a new array iterator wrapping a int array. */
	@SuppressWarnings("unchecked")
	static public ArrayIterable<Integer> newArrayIterable(int[] pO) {
		return (pO == null)?null:(ArrayIterable<Integer>)new ArrayIterable(ArrayIterator.newArrayIterator(pO));
	}
	/** Creates a new array iterator wrapping a float array. */
	@SuppressWarnings("unchecked")
	static public ArrayIterable<Float> newArrayIterable(float[] pO) {
		return (pO == null)?null:(ArrayIterable<Float>)new ArrayIterable(ArrayIterator.newArrayIterator(pO));
	}
	/** Creates a new array iterator wrapping a double array. */
	@SuppressWarnings("unchecked")
	static public ArrayIterable<Double> newArrayIterable(double[] pO) {
		return (pO == null)?null:(ArrayIterable<Double>)new ArrayIterable(ArrayIterator.newArrayIterator(pO));
	}
	/** Creates a new array iterator wrapping an object array. */
	@SuppressWarnings("unchecked")
	static public ArrayIterable<?> newArrayIterable(Object[] pO) {
		return (pO == null)?null:(ArrayIterable<Object>)new ArrayIterable(ArrayIterator.newArrayIterator(pO));
	}
	/** Creates a new array iterator wrapping a data array. */
	@SuppressWarnings("unchecked")
	static public <E> ArrayIterable<E> newArrayIterable(DataArray<E> pO) {
		return (pO == null)?null:(ArrayIterable<E>)new ArrayIterable(ArrayIterator.newArrayIterator(pO));
	}
	
	Iterator<T> I;
	
	ArrayIterable(Iterator<T> pI) { this.I = pI; }
	
    /** Returns an iterator over a set of elements of type T. */
    public Iterator<T> iterator() { return I;}

}
