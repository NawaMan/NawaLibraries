package net.nawaman.util.datastructure;

import java.lang.reflect.Array;
import java.util.Iterator;

import net.nawaman.util.*;


/**
 * Fixed-length DataArray<br/>
 * This type is very similar to the bare object array but it use more memory (1 word more) than
 * normal and it takes longer than than normal array. This type will be useful when there is a need 
 * for the data to be seen as DataArray or generic support is needed.
 **/
public class FixedLengthDataArray<T> implements DataArray<T> {
	
	/** Creates a new fixed-length data array. */
	@SuppressWarnings("unchecked")
	static public <E> FixedLengthDataArray<E> newInstance(Class<E> pCClass, int pLength) {
		if(pLength < 0)     throw new NegativeArraySizeException("Unable to create an apeendable data array with the size of " + pLength);
		if(pCClass == null) throw new IllegalArgumentException("Unable to create an apeendable data array of null or void type.");
		
		return new FixedLengthDataArray(pCClass, pLength);
	}
	/** Creates a new fixed-length data array. */
	@SuppressWarnings("unchecked")
	static public <E> FixedLengthDataArray<E> newInstance(E[] pArray) {
		if(pArray == null) throw new NullPointerException();

		Class<?> Cls = UArray.getComponentType_OfInstance(pArray);
		if(!Cls.isArray()) Cls = UClass.getCLASS(Cls);
		
		FixedLengthDataArray<E> FLDA = new FixedLengthDataArray(Cls, pArray.length);
		for(int i = pArray.length; --i >= 0;) FLDA.setData(i, pArray[i]);
		return FLDA;
	}
	/** Creates a new fixed-length data array. */
	@SuppressWarnings("unchecked")
	static public <E> FixedLengthDataArray<E> newInstance(DataArray<E> pArray) {
		if(pArray == null) throw new NullPointerException();
		FixedLengthDataArray<E> FLDA = new FixedLengthDataArray(pArray.getComponentClass(), pArray.getLength());
		for(int i = pArray.getLength(); --i >= 0; ) FLDA.setData(i, pArray.getData(i));
		return FLDA;
	}
	/** Creates a new fixed-length data array. */
	@SuppressWarnings("unchecked")
	static public <E> FixedLengthDataArray<E> newInstance(Object pArray) {
		if(pArray == null)          throw new NullPointerException();
		if(!UArray.isArrayInstance(pArray)) throw new IllegalArgumentException("The input is not an array.");
		
		Class<?> Cls = UArray.getComponentType_OfInstance(pArray);
		if(!Cls.isArray()) Cls = UClass.getCLASS(Cls);
		
		FixedLengthDataArray<E> FLDA = new FixedLengthDataArray(Cls, UArray.getLength(pArray));
		for(int i = FLDA.getLength(); --i >= 0;) FLDA.setData(i, (E)UArray.get(pArray, i));
		return FLDA;
	}
	
	/** Constructs a new FixedLengthDataArray. */
	protected FixedLengthDataArray(Class<T> pCClass, int pLength) {
		this.CClass = pCClass;
		this.Datas = Array.newInstance(pCClass, pLength);
	}
	
	Object   Datas  = null;
	Class<T> CClass = null;
	
	/** Returns the length of this array. */
	public int getLength() { return (this.Datas == null)?0:Array.getLength(this.Datas); }
	
	/** Returns the class data that this DataArray hold. */
	public Class<T> getComponentClass() { return this.CClass; }
	
	/** Sets the data of this array at the position pPos. */
	public Object setData(int pPos, T pData) {
		if((pPos < 0)||(this.Datas == null)||(pPos > Array.getLength(this.Datas))) return null;
		Array.set(this.Datas, pPos, pData);
		return pData;
	}
	/** Returns the data of the array at the position pPos. */
	@SuppressWarnings("unchecked")
	public T getData(int pPos) {
		if((pPos < 0)||(this.Datas == null)||(pPos > Array.getLength(this.Datas))) throw new ArrayIndexOutOfBoundsException(pPos);
		return (T)Array.get(this.Datas, pPos);
	}

	/** Returns an iterator over a set of elements of type T. */
	public Iterator<T> iterator() { return ArrayIterator.newArrayIterator(this); }

	/** Returns the array duplication of this DataArray */
	@SuppressWarnings("unchecked")
	public T[] toArray() {
		Object New = Array.newInstance(this.CClass, this.getLength());
		if(this.Datas != null)  System.arraycopy(this.Datas, 0, New, 0, Array.getLength(this.Datas));
		return (T[])New;
	}
	
	/** Returns the array duplication of this DataArray of the type TargetComponentCls */
	@SuppressWarnings("unchecked")
	public <E> E[] toArrayOf(Class<? super T> TargerComponentCls) {
		if(TargerComponentCls == null) return null;
		Object New = Array.newInstance(TargerComponentCls, this.getLength());
		if(this.Datas != null) {
			for(int i = this.getLength(); --i >= 0; ) Array.set(New, i, Array.get(this.Datas, i));
		}
		return (E[])New;
	}

	/** For Testing */
	static public void main(String ... Args) {
		Object O = FixedLengthDataArray.newInstance(new int[][] { {1, 3}, {5, 7} });
		System.out.println(UObject.toString(O));
	}
}
