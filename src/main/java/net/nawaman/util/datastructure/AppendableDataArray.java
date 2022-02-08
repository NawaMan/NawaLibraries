package net.nawaman.util.datastructure;

import java.lang.reflect.Array;
import java.util.Iterator;

import net.nawaman.util.ArrayIterator;
import net.nawaman.util.DataArray;
import net.nawaman.util.UArray;
import net.nawaman.util.UClass;
import net.nawaman.util.UObject;

public class AppendableDataArray<T> implements DataArray<T> {
	
	static final int DefaultInitial = 0;
	static final int DefaultStep    = 5;
	
	/** Creates a new appendable data array. */
	static public <E> AppendableDataArray<E> newInstance(Class<E> pCClass, int pLength) {
		if(pLength < 0)     throw new NegativeArraySizeException("Unable to create an apeendable data array with the size of " + pLength);
		if(pCClass == null) throw new IllegalArgumentException("Unable to create an apeendable data array of null or void type.");
		
		return new AppendableDataArray<>(pCClass, pLength);
	}
	/** Creates a new appendable data array. */
	static public <E> AppendableDataArray<E> newInstance(Class<E> pCClass) {
		if(pCClass == null) throw new IllegalArgumentException("Unable to create an apeendable data array of null or void type.");
		
		return new AppendableDataArray<>(pCClass, DefaultInitial);
	}
	/** Creates a new appendable data array. */
	@SuppressWarnings("unchecked")
	static public <E> AppendableDataArray<E> newInstance(E[] pArray) {
		if(pArray == null) throw new NullPointerException();
		
		Class<?> Cls = UArray.getComponentType_OfInstance(pArray);
		if(!Cls.isArray()) Cls = UClass.getCLASS(Cls);
		
		@SuppressWarnings("rawtypes")
        AppendableDataArray<E> ADA = new AppendableDataArray(Cls, pArray.length);
		for(int i = pArray.length; --i >= 0; ) ADA.setData(i, pArray[i]);
		return ADA;
	}
	/** Creates a new appendable data array. */
	static public <E> AppendableDataArray<E> newInstance(DataArray<E> pArray) {
		if(pArray == null) throw new NullPointerException();
		
		AppendableDataArray<E> ADA = new AppendableDataArray<>(pArray.getComponentClass(), pArray.getLength());
		for(int i = pArray.getLength(); --i >= 0; ) ADA.setData(i, pArray.getData(i));
		return ADA;
	}
	/** Creates a new appendable data array. */
	@SuppressWarnings("unchecked")
	static public <E> AppendableDataArray<E> newInstance(Object pArray) {
		if(pArray == null)          throw new NullPointerException();
		if(!UArray.isArrayInstance(pArray)) throw new IllegalArgumentException("The input is not an array.");

		Class<?> Cls = UArray.getComponentType_OfInstance(pArray);
		if(!Cls.isArray()) Cls = UClass.getCLASS(Cls);
		
		@SuppressWarnings("rawtypes")
        AppendableDataArray<E> ADA = new AppendableDataArray(Cls, UArray.getLength(pArray));
		for(int i = ADA.getLength(); --i >= 0;) ADA.setData(i, (E)UArray.get(pArray, i));
		return ADA;
	}

	/** Constructs a new AppendableDataArray. */
	protected AppendableDataArray(Class<T> pCClass) { this(pCClass, DefaultInitial); }
	
	/** Constructs a new AppendableDataArray. */
	protected AppendableDataArray(Class<T> pCClass, int pLength) {
		this.CClass = pCClass;
		this.Length = pLength;
		this.Datas  = (pLength == 0)?null:Array.newInstance(pCClass, pLength);
	}
	
	int      Length =    0;
	Object   Datas  = null;
	Class<T> CClass = null;
	
	/** Returns the length of this array. */
	public int getLength() { return this.Length; }
	
	/** Returns the class data that this DataArray hold. */
	public Class<T> getComponentClass() { return this.CClass; }

	/** Append the data of this array . */
	public boolean append(T pData) {
		if((this.Datas == null) || (this.Length >= Array.getLength(this.Datas))) {
			Object New = Array.newInstance(this.CClass, this.Length + DefaultStep);
			System.arraycopy(this.Datas, 0, New, 0, (this.Datas == null)?0:Array.getLength(this.Datas));
			this.Datas = New;
		}
		Array.set(this.Datas, this.Length, pData);
		this.Length++;
		return true;
	}
	/** Insert the data of this array at the position pPos. */
	@SuppressWarnings("unchecked")
	public boolean insert(int pPos, T pData) {
		if((pPos < 0)||(pPos > Array.getLength(this.Datas))) throw new ArrayIndexOutOfBoundsException(pPos);
		
		if(pPos == ((this.Datas == null)?0:Array.getLength(this.Datas))) return this.append(pData);
		
		if((this.Datas == null) || (this.Length >= Array.getLength(this.Datas))) {
			T[] New = (T[])Array.newInstance(this.CClass, this.Length + DefaultStep);
			System.arraycopy(this.Datas, 0, New, 0, (this.Datas == null)?0:Array.getLength(this.Datas));
			this.Datas = New;
		}
		System.arraycopy(this.Datas, pPos, this.Datas, pPos + 1, this.getLength() - pPos);
		Array.set(this.Datas, pPos, pData);
		this.Length++;
		return true;
	}
	
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
		
		AppendableDataArray<Integer> ADA = AppendableDataArray.newInstance(Integer.class, 5);
		System.out.println(UObject.toString(ADA));
		System.out.println(UObject.toString(ADA.Datas));
		
		ADA.setData(2, 10);
		System.out.println(UObject.toString(ADA));
		System.out.println(UObject.toString(ADA.Datas));
		
		ADA.setData(0, 1);
		System.out.println(UObject.toString(ADA));
		System.out.println(UObject.toString(ADA.Datas));

		ADA.append(5);
		System.out.println(UObject.toString(ADA));
		System.out.println(UObject.toString(ADA.Datas));

		ADA.append(20);
		System.out.println(UObject.toString(ADA));
		System.out.println(UObject.toString(ADA.Datas));

		ADA.insert(3, 40);
		System.out.println(UObject.toString(ADA));
		System.out.println(UObject.toString(ADA.Datas));

		ADA.insert(0, 50);
		System.out.println(UObject.toString(ADA));
		System.out.println(UObject.toString(ADA.Datas));
		
		ADA = AppendableDataArray.newInstance(new Integer[] {1, 2, 3, 4, 5});
		System.out.println(UObject.toString(ADA));
		System.out.println(UObject.toString(ADA.Datas));
		
		ADA = AppendableDataArray.newInstance((Integer[])UArray.getObjectArray(new int[] {5, 4, 3, 2, 1}));
		System.out.println(UObject.toString(ADA));
		System.out.println(UObject.toString(ADA.Datas));

		Object O = AppendableDataArray.newInstance(new int[][] { {1, 3}, {5, 7}, {8, 3}, {4, 6} });
		System.out.println(UObject.toString(O));
	}
	
}
