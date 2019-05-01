package net.nawaman.util;

import java.lang.reflect.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

import net.nawaman.util.datastructure.FixedLengthDataArray;

/** Array Utilities */
public class UArray {
	
	private UArray() {}
	
	/** Creates a new array of the type */
	@SuppressWarnings("unchecked")
	static public <T> T[] newArray(Class<T> pComponentType, int pLength) {
		return (T[])Array.newInstance(pComponentType, pLength);
	}
	
	/** Checks if the source object is an array or a DataArray. */
	static public boolean isArrayInstance(Object Source) {
		if(Source == null)                 return false;
		if(Source instanceof DataArray<?>) return  true;
		if(Source.getClass().isArray())    return  true;
		return false;
	}
	/** Checks if the source object is an array. */
	static public boolean isPrimitiveArrayInstance(Object Source) {
		if(Source == null)              return false;
		if(Source.getClass().isArray()) return  true;
		return false;
	}
	/** Checks if the source object is an array. */
	static public boolean isDataArrayInstance(Object Source) {
		if(Source == null)                 return false;
		if(Source instanceof DataArray<?>) return  true;
		return false;
	}
	
	/** Checks if the source object is an array or a DataArray. */
	static public boolean isArrayType(Class<?> Cls) {
		if(Cls == null)                           return false;
		if(DataArray.class.isAssignableFrom(Cls)) return  true;
		if(Cls.isArray())                         return  true;
		return false;
	}
	/** Checks if the source object is an array. */
	static public boolean isPrimitiveArrayType(Class<?> Cls) {
		if(Cls == null)   return false;
		if(Cls.isArray()) return  true;
		return false;
	}
	/** Checks if the source object is an array. */
	static public boolean isDataArrayType(Class<?> Cls) {
		if(Cls == null)                           return false;
		if(DataArray.class.isAssignableFrom(Cls)) return  true;
		return false;
	}
	

	/** Checks if the source object is an array of primitive type. */
	static public boolean isArrayInstanceOfPrimitive(Object Source) {
		if(Source == null)                 return false;
		if(Source instanceof DataArray<?>) return false;
		if(Source.getClass().isArray())    return Source.getClass().getComponentType().isPrimitive();
		return false;
	}
	/** Checks if the source object is an array of primitive type. */
	static public boolean isArrayTypeOfPrimitive(Class<?> Cls) {
		if(Cls == null)                           return false;
		if(DataArray.class.isAssignableFrom(Cls)) return false;
		if(Cls.isArray())                         return Cls.getComponentType().isPrimitive();
		return false;
	}

	/** Returns the length of the source array/DataArray or 0 if not an array. */
	static public int getLength(Object Source) {
		if(Source == null)                 return 0;
		if(Source instanceof DataArray<?>) return ((DataArray<?>)Source).getLength();
		if(Source instanceof List<?>)      return ((List<?>)Source).size();
		if(Source.getClass().isArray())    return Array.getLength(Source);
		throw new IllegalArgumentException("The given object is not an array, list or a DataArray.");
	}

	/** Returns the element type of the array. */
	static public Class<?> getComponentType_OfInstance(Object pArray) {
		if(pArray == null)                 return Void.TYPE;
		if(pArray instanceof DataArray<?>) return ((DataArray<?>)pArray).getComponentClass();
		if(pArray.getClass().isArray())    return pArray.getClass().getComponentType();
		throw new IllegalArgumentException("The given object is not an array or a DataArray.");
	}
	/** Returns the element type of the array. */
	static public Class<?> getComponentType_OfType(Class<?> Cls) {
		if(Cls == null)                           return Void.TYPE;
		if(DataArray.class.isAssignableFrom(Cls)) return Object.class;
		if(Cls.isArray())                         return Cls.getComponentType();
		throw new IllegalArgumentException("The given object is not an array or a DataArray.");
	}

	/** Sets the value pValue at the position pPos of the Array pArray. */
	@SuppressWarnings("unchecked")
	static public Object set(Object pArray, int pPos, Object pValue) {
		if(pArray == null) throw new NullPointerException();
		
		if(pArray instanceof List<?>) {
			if((pPos < 0) || (pPos >= ((List)pArray).size())) 
				throw new ArrayIndexOutOfBoundsException(pPos);
			((List)pArray).set(pPos, pValue);
			return pValue;
		}
		if(pArray instanceof DataArray) {
			if(((DataArray)pArray).getComponentClass().isInstance(pValue))
				throw new IllegalArgumentException(
						    "The value " + pValue + " cannot be assigned into the arrat of " +
						    ((DataArray)pArray).getComponentClass() + ".");
			return ((DataArray)pArray).setData(pPos, pValue);
		}
		if(pArray.getClass().isArray()) { Array.set(pArray, pPos, pValue); return true; }
		throw new IllegalArgumentException("The given object is not an array or a DataArray.");
	}
	/** Returns the value at the position pPos of the Array pArray. */
	static public Object get(Object pArray, int pPos) {
		if(pArray == null) return null;
		if(pArray instanceof List<?>) {
			if((pPos < 0) || (pPos >= ((List<?>)pArray).size())) return null;
			return ((List<?>)pArray).get(pPos);
		}
		if(pArray instanceof DataArray<?>) return ((DataArray<?>)pArray).getData(pPos);
		if(pArray.getClass().isArray())    return Array.get(pArray, pPos);
		throw new IllegalArgumentException("The given object is not an array or a DataArray.");
	}
	
	/** Returns string representation of a data array value. */
	static public String toString(DataArray<?> O) { return toString(O, "[", "]", ","); }
	
	/** Returns string representation of a data array value. */
	static public String toString(DataArray<?> O, String Open, String Close, String Separator) {
		if(O == null) return "null";
		StringBuffer SB = new StringBuffer();
		SB.append(Open);
		for(int i = 0; i < O.getLength(); i++) {
			if(i != 0) SB.append(Separator);
			Object Oi = O.getData(i);
			if(Oi instanceof String) SB.append(Oi);
			else                     SB.append(UObject.toString(Oi));
		}
		SB.append(Close);
		return SB.toString();
	}
	
	/** Returns string representation of an object value. */
	static public <T> String toString(T[] O) {
		return toString(O, "[", "]", ",");
	}
	/** Returns string representation of an object value with customized Open, Close and Separator. */
	static public <T> String toString(T[] O, String Open, String Close, String Separator) {
		StringBuffer SB = new StringBuffer();
		SB.append(Open);
		if(O != null) {
			for(int i = 0; i < Array.getLength(O); i++) {
				if(i != 0) SB.append(Separator);
				Object Oi = Array.get(O,i);
				if(Oi instanceof String) SB.append(Oi);
				else                     SB.append(UObject.toString(Oi));
			}
		}
		SB.append(Close);
		return SB.toString();
	}
	/** Returns string representation of an object value. */
	static public String toString(Object O) {
		if(O == null) return "null";
		if(O instanceof DataArray<?>) return toString(O);
		if(O.getClass().isArray())    return toString(getObjectArray(O));
		throw new IllegalArgumentException("The given object is not an array or a DataArray.");
	}
	/** Returns string representation of an object value. */
	static public String toString(Object O, String Open, String Close, String Separator) {
		if(O == null) return "null";
		if(O instanceof DataArray<?>) return toString((DataArray<?>)O, Open, Close, Separator);
		if(O.getClass().isArray()) {
			StringBuffer SB = new StringBuffer();
			SB.append(Open);
			for(int i = 0; i < Array.getLength(O); i++) {
				if(i != 0) SB.append(Separator);
				Object Oi = Array.get(O,i);
				if(Oi instanceof String) SB.append(Oi);
				else                     SB.append(UObject.toString(Oi));
			}
			SB.append(Close);
			return SB.toString();
		}
		throw new IllegalArgumentException("The given object is not an array or a DataArray.");
	}
	/** Returns string representation of an object value. */
	static public String toString(String Open, String Close, String Separator, Object ... pValues) {
		return toString(pValues, Open, Close, Separator);
	}

	/** Returns string representation of a data array value. */
	static public String toDetail(DataArray<?> O, String Open, String Close, String Separator) {
		if(O == null) return "null";
		StringBuffer SB = new StringBuffer();
		SB.append(Open);
		for(int i = 0; i < O.getLength(); i++) {
			if(i != 0) SB.append(Separator);
			Object Oi = O.getData(i);
			if(Oi instanceof String) SB.append("\"").append(Oi).append("\"");
			else                     SB.append(UObject.toString(Oi) + ":" + ((Oi==null)?"":Oi.getClass().toString()));
		}
		SB.append(Close);
		return SB.toString();
	}
	/** Returns string representation of an object value. */
	static public String toDetail(Object O, String Open, String Close, String Separator) {
		if(O == null) return "null";
		if(O instanceof DataArray<?>) return toString((DataArray<?>)O, Open, Close, Separator);
		if(O.getClass().isArray()) {
			StringBuffer SB = new StringBuffer();
			SB.append(Open);
			for(int i = 0; i < Array.getLength(O); i++) {
				if(i != 0) SB.append(Separator);
				Object Oi = Array.get(O,i);
				if(Oi instanceof String) SB.append("\"").append(Oi).append("\"");
				else                     SB.append(UObject.toString(Oi) + ":" + ((Oi==null)?"":Oi.getClass().toString()));
			}
			SB.append(Close);
			return SB.toString();
		}
		throw new IllegalArgumentException("The given object is not an array or a DataArray.");
	}
	/** Returns string representation of a data array value. */
	static public <T> String toDetail(T[] O, String Open, String Close, String Separator) {
		StringBuffer SB = new StringBuffer();
		SB.append(Open);
		if(O != null) {
			for(int i = 0; i < Array.getLength(O); i++) {
				if(i != 0) SB.append(Separator);
				Object Oi = Array.get(O,i);
				if(Oi instanceof String) SB.append("\"").append(Oi).append("\"");
				else                     SB.append(UObject.toDetail(Oi) + ":" + ((Oi==null)?"":Oi.getClass().toString()));
			}
		}
		SB.append(Close);
		return SB.toString();
	}
	/** Returns string representation of an object value. */
	static public String toDetail(String Open, String Close, String Separator, Object ... pValues) {
		return toDetail(pValues, Open, Close, Separator);
	}

	/**
	 * Returns the new array object (as an array object) from an array. This means that if the 
	 * source array is an array of the primitive type, a new array of the wrapped type will be
	 * created. If the original type is not primitive, then simple casting will be used.
	 **/
	static public Object[] getObjectArray(Object Source) {
		if(Source == null) return null;
		
		if(!Source.getClass().isArray()) {
			// DataArray
			if(Source instanceof DataArray<?>) return ((DataArray<?>)Source).toArray();
			throw new IllegalArgumentException("The given object is not an array or a DataArray.");
		}
		if(!Source.getClass().getComponentType().isPrimitive()) {
			if(!isArrayType(Source.getClass().getComponentType())) return (Object[])Source;
		}

		Object[] New = new Object[Array.getLength(Source)];
		for(int i = Array.getLength(Source); --i >= 0; ) {
			Object O = Array.get(Source,i);
			if(O == null) continue;
			if(isArrayInstance(O)) O = getObjectArray(O);
			Array.set(New, i, O);
		}
		return New;
	}

	/** Returns the new array with out any null element. */
	@SuppressWarnings("unchecked")
	static public <T> T[] getLeanArray(T[] Source) {
		if(Source == null) return null;

		T[]    New = null;
		int    TempSize = 0;
		Object Temp = Array.newInstance(Source.getClass().getComponentType(), Array.getLength(Source));
		int    Count = Array.getLength(Source);
		for(int i = 0; i < Count; i++ ) {
			if(Array.get(Source, i) == null) continue;
			Array.set(Temp, TempSize, Array.get(Source, i));
			TempSize++;
		}
		New = (T[])Array.newInstance(Source.getClass().getComponentType(), TempSize);
		System.arraycopy(Temp, 0, New, 0, TempSize);
		return New;
	}
	
	/** Returns the new array with out any null element without specifying type. */
	static public <E> DataArray<E> getLeanArray(DataArray<E> Source) {
		if(Source == null) return null;

		int    TempSize = 0;
		Object Temp = Array.newInstance(Source.getClass().getComponentType(), Array.getLength(Source));
		for(int i = Array.getLength(Source); --i >= 0; ) {
			if(Array.get(Source, i) == null) continue;
			Array.set(Temp, TempSize, Array.get(Source, i));
			TempSize++;
		}
		DataArray<E> New = FixedLengthDataArray.newInstance(Source.getComponentClass(), TempSize);
		for(int i = Source.getLength(); --i >= 0; ) New.setData(i, Source.getData(i));
		return New;
	}
	/** Returns the new array with out any null element without specifying type. */
	static public Object getLeanArray(Object Source) {
		if(Source == null) return null;
		if(Source instanceof DataArray<?>) return getLeanArray((DataArray<?>)Source);
		
		Class<?> CSource = Source.getClass();
		if(!CSource.isArray()) throw new IllegalArgumentException("The given object is not an array or a DataArray.");
		
		if(CSource.getComponentType().isPrimitive()) return Source; // It's not possible to be null
		return getLeanArray((Object[])Source);
	}

	/**
	 * Converts array to array of the class ACls (ACls is a class of an array). This function will
	 * not convert between number type.
	 **/
	static public Object convertArray(Object Source, Class<?> ACls) {
		return convertArray(Source, ACls, false);
	}
	/**
	 * Converts array to array class ACls (ACls is a class of an array).<br />
	 * The input array can be an DataArray.
	 **/
	static public Object convertArray(Object Source, Class<?> ACls, boolean pIsConvertNumberal) {
		if(!ACls.isArray()) throw new IllegalArgumentException("The given target class is not an array class.");
		if(Source == null)  return null;
		if(!Source.getClass().isArray()) {
			// DataArray
			if(Source instanceof DataArray<?>) {
				// It already the same type
				if(((DataArray<?>)Source).getComponentClass() == ACls) return ((DataArray<?>)Source).toArray();
				Source = ((DataArray<?>)Source).toArray();
			} else throw new IllegalArgumentException("The given object is not an array or a DataArray.");
		}
		
		return convertArrayToArrayOf(Source, ACls.getComponentType(), pIsConvertNumberal);
	}
	/** Converts array to array of of the class Cls (Cls is the class of the element of the output array). */
	@SuppressWarnings("unchecked")
	static public <T> DataArray<T> convertArrayToArrayOf(DataArray<?> Source, Class<T> Cls, boolean pIsConvertNumberal) {
		if(Cls == null) throw new NullPointerException();
		if(Source == null) return null;
		
		if(Source.getComponentClass() == Cls) return (DataArray<T>)Source;	// It already the same type
		Object O = convertArrayToArrayOf(Source.toArray(), Cls, pIsConvertNumberal);
		DataArray<T> DA = FixedLengthDataArray.newInstance(Cls, Source.getLength());
		for(int i = DA.getLength(); --i >= 0; ) DA.setData(i, (T)Array.get(O, i));
		return DA;
	}
	/**
	 * Converts array to array of the class Cls (Cls is the class of the element of the output array).<br />
	 * The input array can be an DataArray.
	 **/
	static public Object convertArrayToArrayOf(Object Source, Class<?> Cls, boolean pIsConvertNumberal) {
		if(Cls == null) throw new NullPointerException();
		if(Source == null) return null;
		
		if(Source instanceof DataArray<?>) return convertArrayToArrayOf((DataArray<?>)Source, Cls, pIsConvertNumberal);
		
		if(!Source.getClass().isArray()) throw new IllegalArgumentException("The given object is not an array or a DataArray.");
		
		if(Source.getClass().getComponentType() == Cls) return Source;	// It already the same type
		
		// In case that both are object, there is no need to change
		boolean isOriginalObject = (UClass.getCLASS(Source.getClass()) == Source.getClass());
		boolean isTargetObject   = (UClass.getCLASS(Cls) == Cls);
		if(isOriginalObject && isTargetObject && Cls.isAssignableFrom(Source.getClass())) return Source;

		Class<?> CCls = UClass.getCLASS(Cls);

		Object New = Array.newInstance(Cls, Array.getLength(Source));
		for(int i = Array.getLength(Source); --i >= 0; ) { 
			Object O = Array.get(Source, i);
			
			if(Number.class.isAssignableFrom(CCls)) {
				if((O != null) && CCls.isInstance(O)) {
					if(pIsConvertNumberal && !Number.class.isInstance(O)) {
						throw new ClassCastException(
								"Cannot cast from " + UObject.toString(Source) + 
								":"+Source.getClass().getComponentType()+"[] " +
								"to an array of " + UObject.toString(CCls) + ".");
					}
				}
				
				if(    !Cls.isPrimitive())   {
					if(     Cls ==  Number.class) Array.set(New, i,                      O               );
					else if(Cls == Integer.class) Array.set(New, i, (O==null)?0:((Number)O).intValue()   );
					else if(Cls ==  Double.class) Array.set(New, i, (O==null)?0:((Number)O).doubleValue());
					else if(Cls ==    Long.class) Array.set(New, i, (O==null)?0:((Number)O).longValue()  );
					else if(Cls ==    Byte.class) Array.set(New, i, (O==null)?0:((Number)O).byteValue()  );
					else if(Cls ==   Float.class) Array.set(New, i, (O==null)?0:((Number)O).floatValue() );
					else if(Cls ==   Short.class) Array.set(New, i, (O==null)?0:((Number)O).shortValue() );
				}
				else if(Cls ==    int.class) Array.setInt(   New, i, (O==null)?0:((Number)O).intValue()   );
				else if(Cls == double.class) Array.setDouble(New, i, (O==null)?0:((Number)O).doubleValue());
				else if(Cls ==   long.class) Array.setLong(  New, i, (O==null)?0:((Number)O).longValue()  );
				else if(Cls ==   byte.class) Array.setByte(  New, i, (O==null)?0:((Number)O).byteValue()  );
				else if(Cls ==  float.class) Array.setFloat( New, i, (O==null)?0:((Number)O).floatValue() );
				else if(Cls ==  short.class) Array.setShort( New, i, (O==null)?0:((Number)O).shortValue() );
				continue;
			}

			if(isArrayType(Cls)) {
				O = convertArray(O, Cls, pIsConvertNumberal);
				Array.set(New, i, O);
				continue;
			}
			
			if((O != null) && !CCls.isInstance(O)) {
				throw new ClassCastException(
						"Cannot cast from " + UObject.toString(Source) + 
						":"+Source.getClass().getComponentType()+"[] " +
						"to an array of " + UObject.toString(CCls) + ".");
			}
			
			if(Cls == char.class)    { Array.setChar(   New, i, (O==null)?0    :((Character)O).charValue()   ); continue; }
			if(Cls == boolean.class) { Array.setBoolean(New, i, (O==null)?false:((Boolean)  O).booleanValue()); continue; }
			
			Array.set(New, i, O);
		}
		return New;
	}
	
	/**
	 * Check if the source array can be converted to an array of the class ACls (ACls is a class of
	 * an array). This function will not convert between number type.
	 **/
	static public boolean canArrayBeConvertTo(Object Source, Class<?> ACls) {
		return canArrayBeConvertTo(Source, ACls, false);
	}
	/**
	 * Checks if the source array can be convert to array class ACls (ACls is a class of an array).<br />
	 * The input array can be an DataArray.
	 **/
	static public boolean canArrayBeConvertTo(Object Source, Class<?> ACls, boolean pIsConvertNumberal) {
		if(ACls   == null)  return false;
		if(Source == null)  return false;
		if(!ACls.isArray()) return false;
		if(!Source.getClass().isArray()) {
			// DataArray
			if(Source instanceof DataArray<?>) {
				// It already the same type
				if(((DataArray<?>)Source).getComponentClass() == ACls) return true;
				Source = ((DataArray<?>)Source).toArray();
			} else return false;
		}
		
		return canArrayBeConvertToArrayOf(Source, ACls.getComponentType(), pIsConvertNumberal);
	}
	/**
	 * Check if the source array can be converted to an array of the class Cls (Cls is the class of
	 * the element of the output array).<br /> The input array can be an DataArray.
	 **/
	static public boolean canArrayBeConvertToArrayOf(Object Source, Class<?> Cls, boolean pIsConvertNumberal) {
		if(Cls    == null) return false;
		if(Source == null) return false;
		
		if(Source.getClass().getComponentType() == Cls) return true;	// It already the same type
		
		// In case that both are object, there is no need to change
		boolean isOriginalObject = (UClass.getCLASS(Source.getClass()) == Source.getClass());
		boolean isTargetObject   = (UClass.getCLASS(Cls) == Cls);
		if(isOriginalObject && isTargetObject && Cls.isAssignableFrom(Source.getClass())) return true;
		
		Class<?> CCls = UClass.getCLASS(Cls);

		for(int i = Array.getLength(Source); --i >= 0; ) { 
			Object O = Array.get(Source, i);
			
			if(pIsConvertNumberal && Number.class.isAssignableFrom(CCls)) {
				if((O != null) && (CCls != UClass.getCLASS(O.getClass()))) return false;
				continue;
			}
			
			if((O != null) && (CCls != UClass.getCLASS(O.getClass()))) return false;
		}
		return true;
	}

	/** Trim the source array from the front at the pPos position. */
	@SuppressWarnings("unchecked")
	static public <T> T[] getArrayTrimFront(T[] Source, int pPos) {
		if(Source == null) return null;
		
		if(pPos < 0)                       return (T[])Array.newInstance(Source.getClass().getComponentType(), 0);
		if(pPos > Array.getLength(Source)) return Source;
		
		T[] New = (T[])Array.newInstance(Object.class, Array.getLength(Source) - pPos);
		System.arraycopy(Source, pPos, New, 0, Array.getLength(New));
		return New;
	}
	/** Trim the source array from the front at the pPos position. */
	@SuppressWarnings("unchecked")
	static public Object getArrayTrimFront(Object Source, int pPos) {
		if(Source == null)              return null;
		if(Source instanceof DataArray) return getArrayTrimFront((DataArray)Source, pPos);
		
		if(!Source.getClass().isArray()) throw new IllegalArgumentException("The given object is not an array or a DataArray.");
		
		Object New = Array.newInstance(Source.getClass().getComponentType(), Array.getLength(Source) - pPos);
		System.arraycopy(Source, pPos, New, 0, Array.getLength(New));
		
		return New;
	}
	/** Trim the source array from the front at the pPos position. */
	static public <E> DataArray<E> getArrayTrimFront(DataArray<E> Source, int pPos) {
		if(Source == null) return null;
		
		if(pPos < 0)                  return FixedLengthDataArray.newInstance(Source.getComponentClass(), 0);
		if(pPos > Source.getLength()) return Source;

		DataArray<E> New = FixedLengthDataArray.newInstance(Source.getComponentClass(), Source.getLength() - pPos);
		for(int i = New.getLength(); --i >= 0; ) New.setData(i, Source.getData(pPos + i));
		return New;
	}
	
	/** Trim the source array from the back at the pPos position. */
	@SuppressWarnings("unchecked")
	static public <T> T[] getArrayTrimBack(T[] Source, int pPos) {
		if(Source == null) return null;
		
		if(pPos < 0)                       return Source;
		if(pPos > Array.getLength(Source)) return (T[])Array.newInstance(Source.getClass().getComponentType(), 0);
		
		T[] New = (T[])Array.newInstance(Object.class, Array.getLength(Source) - pPos);
		
		System.arraycopy(Source, 0, New, 0, Array.getLength(New));
		return New;
	}
	/** Trim the source array from the back at the pPos position. */
	@SuppressWarnings("unchecked")
	static public Object getArrayTrimBack(Object Source, int pPos) {
		if(Source == null)              return null;
		if(Source instanceof DataArray) return getArrayTrimBack((DataArray)Source, pPos);
		
		if(!Source.getClass().isArray()) throw new IllegalArgumentException("The given object is not an array or a DataArray.");
		
		Object New = Array.newInstance(Source.getClass().getComponentType(), Array.getLength(Source) - pPos);
		System.arraycopy(Source, 0, New, 0, Array.getLength(New));
		
		return New;
	}
	/** Trim the source array from the front at the pPos position. */
	static public <E> DataArray<E> getArrayTrimBack(DataArray<E> Source, int pPos) {
		if(Source == null) return null;
		
		if(pPos < 0)                  return Source;
		if(pPos > Source.getLength()) return FixedLengthDataArray.newInstance(Source.getComponentClass(), 0);

		DataArray<E> New = FixedLengthDataArray.newInstance(Source.getComponentClass(), Source.getLength() - pPos);
		for(int i = New.getLength(); --i >= 0; ) New.setData(i, Source.getData(i));
		return New;
	}
	
	// Resize Array --------------------------------------------------------------------------------
	/**
	 * Resize the source array to the ToSize length. In case of extending, fill the empty space with
	 *    the default value of that type (see UClass.getDefaultValue(Class)).
	 **/
	@SuppressWarnings("unchecked")
	static public <T> T[] resizeArray(T[] Source, int pToSize) {
		return resizeArray(Source, pToSize, (T)((Source == null)?null:UClass.getDefaultValue(Source.getClass().getComponentType())));
	}
	/** Resize the source array to the ToSize length. */
	@SuppressWarnings("unchecked")
	static public <T> T[] resizeArray(T[] Source, int pToSize, T O) {
		if(Source  == null) return null;
		if(pToSize <    0)  return null;
		
		if(pToSize == Array.getLength(Source)) return Source;
		
		if(pToSize < Array.getLength(Source)) return getArrayTrimBack(Source, pToSize);
		
		T[] New = (T[])Array.newInstance(Source.getClass().getComponentType(), pToSize);
		System.arraycopy(Source, 0, New, 0, Array.getLength(Source));
		if(!Source.getClass().getComponentType().isInstance(O)) return New;
		for(int i = Array.getLength(Source); i < pToSize; i++) Array.set(New, i, O);
		return New;
	}
	/**
	 * Resize the source array to the ToSize length. In case of extending, fill the empty space with O.
	 **/
	@SuppressWarnings("unchecked")
	static public Object resizeArray(Object Source, int pToSize, Object O) {
		if(Source == null)           return null;
		if(pToSize <  0)             return null;
		if(!isArrayInstance(Source)) throw new IllegalArgumentException("The given object is not an array or a DataArray.");

		Class<?> C = Source.getClass().getComponentType();
		if((!C.isPrimitive() && C.isInstance(O)) || (C.isPrimitive() && !UClass.getCLASS(C).isInstance(O))) {
			throw new ClassCastException("Cannot cast " + UObject.toString(O) + "as an instance of " + UObject.toString(C) + "[].");
		}
		
		int L = getLength(Source);
		if(pToSize == L) return Source;
		if(pToSize <  L) return getArrayTrimBack(Source, pToSize);
		
		if(Source instanceof DataArray) return resizeArray((DataArray)Source, pToSize, O);
		
		Object New = Array.newInstance(Source.getClass().getComponentType(), pToSize);
		System.arraycopy(Source, 0, New, 0, L);
		if(!C.isPrimitive())        for(int i = pToSize; --i >= L; ) Array.set(New, i, O);
		else if(C ==     int.class) for(int i = pToSize; --i >= L; ) ((int[]    )New)[i] =   ((Integer)O).intValue();
		else if(C == boolean.class) for(int i = pToSize; --i >= L; ) ((boolean[])New)[i] =   ((Boolean)O).booleanValue();
		else if(C ==  double.class) for(int i = pToSize; --i >= L; ) ((double[] )New)[i] =    ((Double)O).doubleValue();
		else if(C ==    char.class) for(int i = pToSize; --i >= L; ) ((char[]   )New)[i] = ((Character)O).charValue();
		else if(C ==    long.class) for(int i = pToSize; --i >= L; ) ((long[]   )New)[i] =      ((Long)O).longValue();
		else if(C ==    byte.class) for(int i = pToSize; --i >= L; ) ((byte[]   )New)[i] =      ((Byte)O).byteValue();
		else if(C ==   float.class) for(int i = pToSize; --i >= L; ) ((float[]  )New)[i] =     ((Float)O).floatValue();
		else if(C ==   short.class) for(int i = pToSize; --i >= L; ) ((short[]  )New)[i] =     ((Short)O).shortValue();
		return New;
	}
	/** Resize the source array to the ToSize length. */
	@SuppressWarnings("unchecked")
	static public <E> DataArray<E> resizeArray(DataArray<E> Source, int pToSize, Object O) {
		if(Source  == null) return null;
		if(pToSize <     0) return null;
		
		// No Change
		if(pToSize == Source.getLength()) return Source;
		
		// Shrink
		if(pToSize < Source.getLength()) return getArrayTrimBack(Source, pToSize);
		
		// Expands
		int FromSize = Source.getLength();
		DataArray<E> New = FixedLengthDataArray.newInstance(Source.getComponentClass(), pToSize);
		for(int i = FromSize; --i >= 0; )        New.setData(i, Source.getData(i));
		for(int i =  pToSize; --i >= FromSize; ) New.setData(i, (E)O);
		return New;
	}
	
	// Deep clone ---------------------------------------------------------------------------------
	
	/** Create a deep clone (deep clone of Array and Vector for all its elements) */
	@SuppressWarnings("unchecked")
	static public Object deepClone(Object pArray) {
		if((pArray == null) || pArray.getClass().isPrimitive()) return pArray;
		
		Object NewArray = null;
		if(pArray.getClass().isArray()) {
			int Length = Array.getLength(pArray);
			NewArray = Array.newInstance(pArray.getClass().getComponentType(), Length);
			
			for(int i = Length; --i >= 0; )
				Array.set(NewArray, i, deepClone(Array.get(pArray, i)));
			
		} else if(pArray instanceof Vector) {
			NewArray   = ((Vector)pArray).clone();
			int Length = ((Vector)pArray).size();
			
			for(int i = Length; --i >= 0; ) {
				((Vector)NewArray).set(i, deepClone(((Vector)NewArray).get(i)));
			}
			
		} else {
			NewArray = pArray;
			try { NewArray = UClass.invokeObjectMethod(pArray, "clone", (Object[])null); }
			catch (InvocationTargetException e) {}
			catch (IllegalAccessException    e) {}
			catch (NoSuchMethodException     e) {}
		}
		
		return NewArray;
	}

	
	static private HashMap<Class<?>, Object> EmptyArrays = new HashMap<Class<?>, Object>();

	static public <T> Object getEmptyArrayOf(Class<? extends T> Cls) {
		if(Cls == null) return null;
		Object O = EmptyArrays.get(Cls);
		if(O != null) return O;
		
		O = Array.newInstance(Cls, 0);
		EmptyArrays.put(Cls, O);
		return O;
	}

	/**
	 * Converts array to array of the class Cls (Cls is the class of the element of the output array).<br />
	 * The input array can be an DataArray.
	 **/
	static public Object extractArray(Object Source, int FromOffset) {
		return extractArray(Source, null, FromOffset, false, true);
	}
	/**
	 * Converts array to array of the class Cls (Cls is the class of the element of the output array).<br />
	 * The input array can be an DataArray.
	 **/
	static public Object extractArray(Object Source, int FromOffset, boolean pIsToClone) {
		return extractArray(Source, null, FromOffset, pIsToClone, true);
	}
	/**
	 * Converts array to array of the class Cls (Cls is the class of the element of the output array).<br />
	 * The input array can be an DataArray.
	 **/
	static public Object extractArray(Object Source, Class<?> Cls, int FromOffset, boolean pIsToClone, boolean pIsConvertNumberal) {
		if(Source == null) return null;
		
		if(!Source.getClass().isArray()) throw new IllegalArgumentException("The given object is not an array or a DataArray.");

		if(Cls == null) Cls = Source.getClass().getComponentType();
		
		if(FromOffset < 0) FromOffset = 0;
		if(FromOffset >= Array.getLength(Source)) return getEmptyArrayOf(Cls);
		
		if((Source.getClass().getComponentType() == Cls) && (FromOffset == 0)) {
			// It already the same type
			if(!pIsToClone) return Source;
			
			int    Length = Array.getLength(Source);
			Object Target = Array.newInstance(Cls, Length);
			System.arraycopy(Source, 0, Target, 0, Length);
			return Target;
		}
		
		int      Length = Array.getLength(Source);
		Class<?> CCls   = UClass.getCLASS(Cls);
		boolean  IsNumb = Number.class.isAssignableFrom(CCls);

		Object New = Array.newInstance(Cls, Length - FromOffset);
		for(int i = FromOffset; i < Length; i++) { 
			Object O = Array.get(Source, i);
			
			if(IsNumb) {
				if((O != null) && CCls.isInstance(O)) {
					if(pIsConvertNumberal && !Number.class.isInstance(O)) {
						throw new ClassCastException(
								"Cannot cast from " + UObject.toString(Source) + 
								":"+Source.getClass().getComponentType()+"[] " +
								"to an array of " + UObject.toString(CCls) + ".");
					}
				}
				
				if(!Cls.isPrimitive())   {
					if(     Cls ==     Number.class) Array.set(New, i - FromOffset,                      O               );
					else if(Cls ==    Integer.class) Array.set(New, i - FromOffset, (O==null)?0:((Number)O).intValue()   );
					else if(Cls ==     Double.class) Array.set(New, i - FromOffset, (O==null)?0:((Number)O).doubleValue());
					else if(Cls ==       Long.class) Array.set(New, i - FromOffset, (O==null)?0:((Number)O).longValue()  );
					else if(Cls ==       Byte.class) Array.set(New, i - FromOffset, (O==null)?0:((Number)O).byteValue()  );
					else if(Cls ==      Float.class) Array.set(New, i - FromOffset, (O==null)?0:((Number)O).floatValue() );
					else if(Cls ==      Short.class) Array.set(New, i - FromOffset, (O==null)?0:((Number)O).shortValue() );
					else if(Cls == BigInteger.class) Array.set(New, i - FromOffset, (O==null)?0:((O instanceof BigInteger)?(BigInteger)O:new BigInteger(((Number)O).toString())));
					else if(Cls == BigInteger.class) Array.set(New, i - FromOffset, (O==null)?0:((O instanceof BigDecimal)?(BigDecimal)O:new BigDecimal(((Number)O).toString())));
				}
				else if(Cls ==    int.class) Array.setInt(   New, i - FromOffset, (O==null)?0:((Number)O).intValue()   );
				else if(Cls == double.class) Array.setDouble(New, i - FromOffset, (O==null)?0:((Number)O).doubleValue());
				else if(Cls ==   long.class) Array.setLong(  New, i - FromOffset, (O==null)?0:((Number)O).longValue()  );
				else if(Cls ==   byte.class) Array.setByte(  New, i - FromOffset, (O==null)?0:((Number)O).byteValue()  );
				else if(Cls ==  float.class) Array.setFloat( New, i - FromOffset, (O==null)?0:((Number)O).floatValue() );
				else if(Cls ==  short.class) Array.setShort( New, i - FromOffset, (O==null)?0:((Number)O).shortValue() );
				continue;
			}
			
			if((O != null) && CCls.isInstance(O)) {
				throw new ClassCastException(
						"Cannot cast from " + UObject.toString(Source) + 
						":"+Source.getClass().getComponentType()+"[] " +
						"to an array of " + UObject.toString(CCls) + ".");
			}
			
			if(Cls == char.class)    { Array.setChar(   New, i - FromOffset, (O==null)?0    :((Character)O).charValue()   ); continue; }
			if(Cls == boolean.class) { Array.setBoolean(New, i - FromOffset, (O==null)?false:((Boolean)  O).booleanValue()); continue; }
			
			Array.set(New, i - FromOffset, O);
		}
		return New;
	}
	
	
	// Main ----------------------------------------------------------------------------------------
	/** For Testing. */
	static public void main(String ... args) {
		Object I = new Integer[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
		System.out.println(UObject.toDetail(I));
		/*
		Object O = null;
		O = UArray.getArrayTrimFront(I, 1);
		System.out.println(UObject.toDetail(O));
		O = UArray.getArrayTrimBack(I, 1);
		System.out.println(UObject.toDetail(O));

		O = UArray.resizeArray(I, 4);
		System.out.println(UObject.toDetail(O));
		O = UArray.resizeArray(I, 14, 0);
		System.out.println(UObject.toDetail(O));
		*/
		
		int    i = 0;
		Object S = null;
		Object R = null;
		
		System.out.println();
		System.out.println("Test#" + i++ + " --------------- ");
		
		S = I;
		R = getArrayTrimFront(S, 5);
		System.out.println(UObject.toDetail(S)+":"+UObject.toString(S.getClass())+" => "+UObject.toDetail(R)+":"+UObject.toString(R.getClass()));
		S = I;
		R = getArrayTrimBack(S, 5);
		System.out.println(UObject.toDetail(S)+":"+UObject.toString(S.getClass())+" => "+UObject.toDetail(R)+":"+UObject.toString(R.getClass()));

		System.out.println();
		System.out.println("Test#" + i++ + " --------------- ");
		
		S = I;
		R = convertArrayToArrayOf(S, int.class, false);
		System.out.println(UObject.toDetail(S)+":"+UObject.toString(S.getClass())+" => "+UObject.toDetail(R)+":"+UObject.toString(R.getClass()));
		S = R;
		R = convertArrayToArrayOf(S, Integer.class, false);
		System.out.println(UObject.toDetail(S)+":"+UObject.toString(S.getClass())+" => "+UObject.toDetail(R)+":"+UObject.toString(R.getClass()));

		System.out.println();
		System.out.println("Test#" + i++ + " --------------- ");
		
		S = I;
		R = convertArrayToArrayOf(S, byte.class, true);
		System.out.println(UObject.toDetail(S)+":"+UObject.toString(S.getClass())+" => "+UObject.toDetail(R)+":"+UObject.toString(R.getClass()));
		S = R;
		R = convertArrayToArrayOf(S, Byte.class, true);
		System.out.println(UObject.toDetail(S)+":"+UObject.toString(S.getClass())+" => "+UObject.toDetail(R)+":"+UObject.toString(R.getClass()));
		S = R;
		R = convertArrayToArrayOf(S, byte.class, true);
		System.out.println(UObject.toDetail(S)+":"+UObject.toString(S.getClass())+" => "+UObject.toDetail(R)+":"+UObject.toString(R.getClass()));

		System.out.println();
		System.out.println("Test#" + i++ + " --------------- ");
		
		S = I;
		R = convertArrayToArrayOf(S, long.class, true);
		System.out.println(UObject.toDetail(S)+":"+UObject.toString(S.getClass())+" => "+UObject.toDetail(R)+":"+UObject.toString(R.getClass()));
		S = R;
		R = convertArrayToArrayOf(S, Long.class, true);
		System.out.println(UObject.toDetail(S)+":"+UObject.toString(S.getClass())+" => "+UObject.toDetail(R)+":"+UObject.toString(R.getClass()));
		S = R;
		R = convertArrayToArrayOf(S, long.class, true);
		System.out.println(UObject.toDetail(S)+":"+UObject.toString(S.getClass())+" => "+UObject.toDetail(R)+":"+UObject.toString(R.getClass()));

		System.out.println();
		System.out.println("Test#" + i++ + " --------------- ");
		
		S = I;
		R = convertArrayToArrayOf(S, double.class, true);
		System.out.println(UObject.toDetail(S)+":"+UObject.toString(S.getClass())+" => "+UObject.toDetail(R)+":"+UObject.toString(R.getClass()));
		S = R;
		R = convertArrayToArrayOf(S, Double.class, true);
		System.out.println(UObject.toDetail(S)+":"+UObject.toString(S.getClass())+" => "+UObject.toDetail(R)+":"+UObject.toString(R.getClass()));
		S = R;
		R = convertArrayToArrayOf(S, double.class, true);
		System.out.println(UObject.toDetail(S)+":"+UObject.toString(S.getClass())+" => "+UObject.toDetail(R)+":"+UObject.toString(R.getClass()));

		System.out.println();
		System.out.println("Test#" + i++ + " --------------- ");
		
		UArray.set(I, 5, null);
		S = I;
		R = getLeanArray(S);
		System.out.println(UObject.toDetail(S)+":"+UObject.toString(S.getClass())+" => "+UObject.toDetail(R)+":"+UObject.toString(R.getClass()));
		S = I;
		R = convertArrayToArrayOf(S, int.class, false);
		System.out.println(UObject.toDetail(S)+":"+UObject.toString(S.getClass())+" => "+UObject.toDetail(R)+":"+UObject.toString(R.getClass()));

		System.out.println();
		System.out.println("Test#" + i++ + " --------------- ");

		I = new int[][] { { 1, 2}, {3, 4}, {5, 6}, {7, 8}, {9, 10} };
		S = I;
		R = getObjectArray(I);
		System.out.println(UObject.toDetail(S)+":"+UObject.toString(S.getClass())+" => "+UObject.toDetail(R)+":"+UObject.toString(R.getClass()));
		S = R;
		R = convertArray(S, int[][].class);
		System.out.println(UObject.toDetail(S)+":"+UObject.toString(S.getClass())+" => "+UObject.toDetail(R)+":"+UObject.toString(R.getClass()));
		S = R;
		R = convertArray(S, Integer[][].class);
		System.out.println(UObject.toDetail(S)+":"+UObject.toString(S.getClass())+" => "+UObject.toDetail(R)+":"+UObject.toString(R.getClass()));

		System.out.println();
		System.out.println("Test#" + i++ + " --------------- ");

		S = I;
		R = convertArray(S, byte[][].class, true);
		System.out.println(UObject.toDetail(S)+":"+UObject.toString(S.getClass())+" => "+UObject.toDetail(R)+":"+UObject.toString(R.getClass()));
		S = R;
		R = convertArray(S, Long[][].class, true);
		System.out.println(UObject.toDetail(S)+":"+UObject.toString(S.getClass())+" => "+UObject.toDetail(R)+":"+UObject.toString(R.getClass()));
		S = R;
		R = convertArray(S, double[][].class, true);
		System.out.println(UObject.toDetail(S)+":"+UObject.toString(S.getClass())+" => "+UObject.toDetail(R)+":"+UObject.toString(R.getClass()));

		System.out.println();
		System.out.println("Test#" + i++ + " --------------- ");
		
		I = FixedLengthDataArray.newInstance(new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10});
		S = I;
		R = convertArrayToArrayOf((DataArray<?>)S, Integer.class, false);
		System.out.println(UObject.toDetail(S)+":"+UObject.toString(S.getClass())+" => "+UObject.toDetail(R)+":"+UObject.toString(R.getClass()));
		S = ((DataArray<?>)R).toArray();
		R = convertArrayToArrayOf(S, int.class, false);
		System.out.println(UObject.toDetail(S)+":"+UObject.toString(S.getClass())+" => "+UObject.toDetail(R)+":"+UObject.toString(R.getClass()));

		System.out.println();
		System.out.println("END ------------------------------------------------------------------");
		
		System.out.println(Integer.class.isAssignableFrom(int.class));
		System.out.println(Integer.class.isAssignableFrom(Integer.TYPE));
		System.out.println(int.class.isAssignableFrom(Integer.class));
		System.out.println(int.class.isAssignableFrom(Integer.TYPE));
		System.out.println(int.class == Integer.TYPE);
		
		System.out.println(Object.class.isAssignableFrom(int.class));
		System.out.println(Object.class.isAssignableFrom(Integer.class));
		
		int[] NewI = new int[] { 1, 2, 3, 4, 5 };
		System.out.println(UObject.toDetail(NewI));
		set(NewI, 2, 30);
		System.out.println(UObject.toDetail(NewI));
		Array.set(NewI, 4, 50);
		System.out.println(UObject.toDetail(NewI));
		
		System.out.println();
		System.out.println(UObject.hash(Object.class));
	}
}
