package net.nawaman.util.datastructure;

import java.lang.reflect.Array;
import java.util.Iterator;

import net.nawaman.util.ArrayIterator;
import net.nawaman.util.DataArray;
import net.nawaman.util.UObject;

/**
 * Eight or more element DataArray utilizing EightElementDataArray. <br/>
 * This class expands the limit of EightElementDataArray. Since EightElementDataArray is suitable
 * for every specific use, great attention must be taken to ensure that it is appropriated to be
 * used in each case. See <code>EightElementDataArray</code> for more detail.
 *  
 * @see EightElementDataArray
 **/
public class EightElementOrMoreDataArray<T> implements DataArray<T> {

	static final int Limit = EightElementDataArray.Limit;
	
	/** Creates a new EightElementOrMoreDataArray. */
	static public <E> EightElementOrMoreDataArray<E> newInstance(Class<E> pCClass, int pLength) {
		if((pCClass == null) || (pLength < -1)) return null;
		return new EightElementOrMoreDataArray<>(pCClass, pLength);
	}
	
	/** Constructs a new EightElementOrMoreDataArray DataArray */
	@SuppressWarnings("unchecked")
	protected EightElementOrMoreDataArray(Class<T> pCClass, int pLength) {
		if(pLength < 0) pLength = 0;
		this.Datas = new DataArray[(pLength / Limit) + 1];
		// Set all except the last.
		for(int i = (this.Datas.length - 1); --i >= 0;) 
			this.Datas[i] = EightElementDataArray.newInstance(pCClass, Limit);
		// Set the last one
		this.Datas[this.Datas.length - 1] = EightElementDataArray.newInstance(pCClass, pLength % Limit);
	}
	
	// Internal Services ---------------------------------------------------------------------------
	
	DataArray<T>[] Datas = null;
	
	// Implementing --------------------------------------------------------------------------------
	
	/** Returns the length of this array. */
	public int getLength() { return (this.Datas.length - 1)*Limit + this.Datas[this.Datas.length - 1].getLength(); }
	
	/** Returns the class data that this DataArray hold. */
	public Class<T> getComponentClass() { return this.Datas[0].getComponentClass(); }
	
	/** Sets the data of this array at the position pPos. */
	public Object setData(int pPos, T pData) {
		if((pPos < 0) || (pPos >= this.getLength())) return null;
		int Slot = pPos / Limit;
		int Pos  = pPos % Limit;
		this.Datas[Slot].setData(Pos, pData);
		return pData;
	}
	/** Returns the data of the array at the position pPos. */
	public T getData(int pPos) {
		if((pPos < 0) || (pPos >= this.getLength())) return null;
		int Slot = pPos / Limit;
		int Pos  = pPos % Limit;
		return this.Datas[Slot].getData(Pos);
	}

	/** Returns an iterator over a set of elements of type T. */
	public Iterator<T> iterator() { return ArrayIterator.newArrayIterator(this); }
	
	/** Returns the array duplication of this DataArray */
	@SuppressWarnings("unchecked")
	public T[] toArray() {
		T[] New = (T[])Array.newInstance(this.getComponentClass(), this.getLength());
		for(int i = New.length; --i >= 0; ) New[i] = this.getData(i);
		return New;
	}
	
	/** Returns the array duplication of this DataArray of the type TargetComponentCls */
	@SuppressWarnings("unchecked")
	public <E> E[] toArrayOf(Class<? super T> TargerComponentCls) {
		if(TargerComponentCls == null) return null;
		Object New = Array.newInstance(TargerComponentCls, this.getLength());
		if(this.Datas != null) {
			for(int i = this.getLength(); --i >= 0; ) Array.set(New, i, this.getData(i));
		}
		return (E[])New;
	}

	static public void main(String ... Args) {
		
		EightElementOrMoreDataArray<Integer> FLDA = EightElementOrMoreDataArray.newInstance(Integer.class, 19);
		System.out.println(UObject.toString(FLDA));
		System.out.println(UObject.toString(FLDA.Datas));
		System.out.println();
		
		FLDA.setData(2, 10);
		System.out.println(UObject.toString(FLDA));
		System.out.println(UObject.toString(FLDA.Datas));
		System.out.println();
		
		FLDA.setData(0, 1);
		System.out.println(UObject.toString(FLDA));
		System.out.println(UObject.toString(FLDA.Datas));
		System.out.println();
	}
	
}
