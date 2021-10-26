package net.nawaman.util.datastructure;

import java.lang.reflect.Array;
import java.util.Iterator;

import net.nawaman.util.*;

/**
 * DataArray with element no more than 8.<br/><br/>
 * This class has a very specific used to help optimize memory used in some rare situation.
 * This DataArray use overhead of 3 words plus a word every actual data store in the array. That is
 * in contrast with normal array that will use 8 words for whatever the actual data is. Therefore,
 * The DataArray is suitable to be used in the case where there needs for array to store data
 * between 4-8 elements but it is extremely-likely that the actual number of data will be form 0-3
 * elements and the the class that use it will have much enough number of instances to the point
 * where memory usage is really import.<br/>
 **/
public class EightElementDataArray<T> implements DataArray<T> {
	
	static final int Limit     = 8;
	static final int LengthPos = Limit;
	
	/** Creates a new EightElement DataArray */
	static public <E> EightElementDataArray<E> newInstance(Class<E> pCClass, int pLength) {
		if((pCClass == null) || (pLength < -1)) return null;
		return new EightElementDataArray<>(pCClass, pLength);
	}
	
	/** Constructs a new EightElement DataArray */
	protected EightElementDataArray(Class<T> pComponentClass, int pLength) {
		if(pLength <     0) pLength = 0;
		if(pLength > Limit) pLength = Limit;
		this.setIndex(LengthPos, pLength);
		this.CClass = pComponentClass;
	}
	
	// Internal Services ---------------------------------------------------------------------------
	
	private int      Indexes =    0;
	private Object[] Datas   = null;
	private Class<T> CClass  = null;   

	private int getIndex(int pPos) {
		switch(pPos) {
			case(0): return ((this.Indexes & 0x00000007) >>  0) - 1;
			case(1): return ((this.Indexes & 0x00000038) >>  3) - 1;
			case(2): return ((this.Indexes & 0x000001C0) >>  6) - 1;
			case(3): return ((this.Indexes & 0x00000E00) >>  9) - 1;
			case(4): return ((this.Indexes & 0x00007000) >> 12) - 1;
			case(5): return ((this.Indexes & 0x00038000) >> 15) - 1;
			case(6): return ((this.Indexes & 0x001C0000) >> 18) - 1;
			case(7): return ((this.Indexes & 0x00E00000) >> 21) - 1;
			case(8): return ((this.Indexes & 0x0F000000) >> 24);
		}
		return -1;
	}
	
	private void setIndex(int pPos, int pInd) {
		if((pPos < 0) || (pPos > Limit)) return;
		//pInd = (((pInd < -1) || (pInd > 7))?0:(pInd + 1));
		switch(pPos) {
			case (0): this.Indexes = ((this.Indexes & ~0x00000007) | (pInd <<  0)); break;
			case (1): this.Indexes = ((this.Indexes & ~0x00000038) | (pInd <<  3)); break;
			case (2): this.Indexes = ((this.Indexes & ~0x000001C0) | (pInd <<  6)); break;
			case (3): this.Indexes = ((this.Indexes & ~0x00000E00) | (pInd <<  9)); break;
			case (4): this.Indexes = ((this.Indexes & ~0x00007000) | (pInd << 12)); break;
			case (5): this.Indexes = ((this.Indexes & ~0x00038000) | (pInd << 15)); break;
			case (6): this.Indexes = ((this.Indexes & ~0x001C0000) | (pInd << 18)); break;
			case (7): this.Indexes = ((this.Indexes & ~0x00E00000) | (pInd << 21)); break;
			case (8): this.Indexes = ((this.Indexes & ~0x0F000000) | (pInd << 24)); break;
		}
	}

	
	private int extendMacros() {
		if(this.Datas == null) {
			this.Datas = new Object[1];
			return 0;
		} else {
			Object[] Temp = new Object[this.Datas.length + 1];
			System.arraycopy(this.Datas, 0, Temp, 0, this.Datas.length);
			this.Datas = Temp;
			return this.Datas.length - 1;
		}
	}
	
	private Object getDataRaw(int pInd) {
		int Pos = this.getIndex(pInd);
		return (Pos < 0)?null:this.Datas[Pos];
	}
	private void setDataRaw(int pInd, Object pV) {
		int Pos = this.getIndex(pInd); if(Pos < 0) { if(pV == null) return; Pos = this.extendMacros(); }
		this.Datas[Pos] = pV;
		this.setIndex(pInd, Pos);
	}
	
	// Implementing --------------------------------------------------------------------------------
	/** Returns the length of this array. */
	public int getLength() { return this.getIndex(LengthPos); }
	
	/** Returns the class data that this DataArray hold. */
	public Class<T> getComponentClass() { return this.CClass; }
	
	/** Sets the data of this array at the position pPos. */
	public Object setData(int pPos, T pData) {
		if((pPos < 0) || (pPos >= this.getLength())) return null;
		this.setDataRaw(pPos, pData);
		return pData;
	}
	/** Returns the data of the array at the position pPos. */
	@SuppressWarnings("unchecked")
	public T getData(int pPos) {
		if((pPos < 0) || (pPos >= this.getLength())) return null;
		return (T)this.getDataRaw(pPos);
	}
	
	/** Returns an iterator over a set of elements of type T. */
	public Iterator<T> iterator() { return ArrayIterator.newArrayIterator(this); }

	/** Returns the array duplication of thie DataArray */
	@SuppressWarnings("unchecked")
	public T[] toArray() {
		T[] New = (T[])Array.newInstance(this.CClass, this.getLength());
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

	/** For Testing. */
	static public void main(String ... Args) {
		
		EightElementDataArray<Integer> EEDA = EightElementDataArray.newInstance(Integer.class, 19);
		System.out.println(UObject.toString(EEDA));
		System.out.println(UObject.toString(EEDA.Indexes));
		System.out.println(UObject.toString(EEDA.Datas));
		
		EEDA.setData(2, 10);
		System.out.println(UObject.toString(EEDA));
		System.out.println(UObject.toString(EEDA.Indexes));
		System.out.println(UObject.toString(EEDA.Datas));
		
		EEDA.setData(0, 1);
		System.out.println(UObject.toString(EEDA));
		System.out.println(UObject.toString(EEDA.Indexes));
		System.out.println(UObject.toString(EEDA.Datas));
		
	}
	
}
