package net.nawaman.util;

import java.util.*;

public class DataArray_Proxy<T> implements DataArray<T> {
	
	public DataArray_Proxy(T[] pSource, boolean pIsWritable) {
		this((Object)pSource, pIsWritable);
	}
	public DataArray_Proxy(DataArray<T> pSource, boolean pIsWritable) {
		this((Object)pSource, pIsWritable);
	}
	public DataArray_Proxy(List<T> pSource, Class<T> pCls, boolean pIsWritable) {
		this(pSource, pIsWritable);
		this.Cls = pCls;
		if(this.Cls == null) throw new NullPointerException();
	}
	
	DataArray_Proxy(Object pSource, boolean pIsWritable) {
		this.Source = pSource;
		this.IsWriable = pIsWritable;
	}

	protected Object   Source    = null;
	protected Class<T> Cls       = null;
	protected boolean  IsWriable = false;
	
	/** Checks if this DataArray is writable */
	public boolean isWritable() { return this.IsWriable; }
	
	/** Returns the length of this array. */
	public int getLength() { return UArray.getLength(this.Source); }
	
	/** Returns the class data that this DataArray hold. */
	@SuppressWarnings("unchecked")
	public Class<T> getComponentClass() {
		if(this.Cls != null) return this.Cls;
		return (Class<T>)UArray.getComponentType_OfInstance(this.Source);
	}
	
	/** Sets the data of this array at the position pPos. */
	public Object setData(int pPos, T pData) {
		if(!this.IsWriable) return null;
		return UArray.set(this.Source, pPos, pData);
	}
	/** Returns the data of the array at the position pPos. */
	@SuppressWarnings("unchecked")
	public T getData(int pPos) { return (T)UArray.get(this.Source, pPos); }
	
	/** Returns the array duplication of this DataArray */
	@SuppressWarnings("unchecked")
	public T[] toArray() {
		if(this.Source instanceof DataArray) return (T[])((DataArray)this.Source).toArray();
		T[] Ts = UArray.newArray(this.getComponentClass(), this.getLength());
		for(int i = Ts.length; --i >= 0; ) Ts[i] = (T)UArray.get(this.Source, i);
		return Ts;
	}
	
	/** Returns the array duplication of thie DataArray of the type TargetComponentCls */
	@SuppressWarnings("unchecked")
	public <E> E[] toArrayOf(Class<? super T> TargerComponentCls) {
		if(TargerComponentCls == this.getComponentClass()) return (E[])this.toArray();
		return (E[])UArray.convertArrayToArrayOf(this.Source, TargerComponentCls, false);
	}
	
	/** Returns an iterator over a set of elements of type T. */
	@SuppressWarnings("unchecked")
	public Iterator<T> iterator() {
		if(this.Source instanceof DataArray) return              ArrayIterator.newArrayIterator((DataArray<T>)this.Source);
		else                                 return (Iterator<T>)ArrayIterator.newArrayIterator((T[])this.Source);
	}
}
