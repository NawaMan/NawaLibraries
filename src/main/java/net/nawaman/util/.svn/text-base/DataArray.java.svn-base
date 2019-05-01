package net.nawaman.util;

/** Object that simulate an array */
public interface DataArray<T> extends Iterable<T> {
	
	/** Returns the length of this array. */
	public int getLength();
	
	/** Returns the class data that this DataArray hold. */
	public Class<T> getComponentClass();
	
	/** Sets the data of this array at the position pPos. */
	public Object setData(int pPos, T pData);
	/** Returns the data of the array at the position pPos. */
	public T      getData(int pPos);
	
	/** Returns the array duplication of this DataArray */
	public T[] toArray();
	
	/** Returns the array duplication of this DataArray of the type TargetComponentCls */
	public <E> E[] toArrayOf(Class<? super T> TargerComponentCls);

}