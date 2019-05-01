package net.nawaman.util;

import java.util.*;
import java.lang.reflect.*;
import java.math.BigDecimal;
import java.math.BigInteger;

@SuppressWarnings("unchecked")
class TheComparater implements Comparator {
	
	public int compare(Object O1, Object O2) {
		return UObject.compare(O1, O2);
	}
	public boolean equals(Object O1, Object O2) {
		return UObject.equal(O1, O2);
	}
}

/** Object Utilities. */
public class UObject {
	
	/** Object array of 0 member. This is used to reduce unnecessary object creation. */
	static public final Object[]    EmptyObjectArray     = new Object[0];
	static public final Boolean[]   EmptyBooleanArray    = new Boolean[0];
	static public final boolean[]   EmptyBoolArray       = new boolean[0];
	static public final Character[] EmptyCharacterArray = new Character[0];
	static public final char[]      EmptyCharArray       = new char[0];
	
	private UObject() {}
	
	@SuppressWarnings("unchecked")
	static Comparator Comparator = new TheComparater();
	/** Returns the comparator. */
	@SuppressWarnings("unchecked")
	static public Comparator getTheComparator() {
		return UObject.Comparator;
	}

	// Hash ----------------------------------------------------------------------------------------
	/** Returns hash value of a boolean value. */
	static public int hash(boolean B) { return (B?1:0); }
	/** Returns hash value of a Boolean value. */
	static public int hash(Boolean B) {
		if(B == null) return 0;
		return (B.booleanValue()?1:0);
	}
	/** Returns hash value of a byte value. */
	static public int hash(byte B)   { return B; }
	/** Returns hash value of a short value. */
	static public int hash(short S)  { return S; }
	/** Returns hash value of an int value. */
	static public int hash(int I)    { return I; }
	/** Returns hash value of a long value. */
	static public int hash(long L)   { return (int)(((L >> 32) & 0x0000FFFFL) ^ (L & 0x0000FFFFL)); }
	/** Returns hash value of a float value. */
	static public int hash(float F)  { return Float.floatToIntBits(F); }
	/** Returns hash value of a double value. */
	static public int hash(double S) {
		long l = Double.doubleToRawLongBits(S);
		return (int)(((l >> 32) & 0x0000FFFFL) ^ (l & 0x0000FFFFL));
	}
	/** Returns hash value of a number value. */
	static public int hash(Number N) {
		if(N == null) return 0;
		if(N instanceof Long) {
			long l = N.longValue();
			return (int)(((l >> 32) & 0x0000FFFFL) ^ (l & 0x0000FFFFL));
		}
		if(N instanceof Double) {
			long l = Double.doubleToRawLongBits(N.doubleValue());
			return (int)(((l >> 32) & 0x0000FFFFL) ^ (l & 0x0000FFFFL));
		}
		if(N instanceof BigInteger) return N.hashCode();
		if(N instanceof BigDecimal) return N.hashCode();
		return N.intValue();
	}
	/** Returns hash value of a char value. */
	static public int hash(char C) {
		return C;
	}
	/** Returns hash value of a character value. */
	static public int hash(Character C) {
		return (C == null)?0:C.charValue();
	}
	/** Returns hash value of a String value. */
	static public int hash(String S) {
		return UString.hash(S.toString());
	}
	/** Returns hash value of a data-array value. */
	static public int hash(DataArray<?> DA) {
		int H = 0;
		int Offset = 10;
		for(int i = DA.getLength(); --i >= 0; ) H += (i + Offset)*UObject.hash(DA.getData(i));
		return H;
	}
	/** Returns hash value of an iterator value. */
	static public int hash(Iterator<?> I) {
		int H = 0;
		int Offset = 10;
		for(int i = 0; I.hasNext(); i++) H += (i + Offset)*UObject.hash(I.next());
		return H;
	}
	/** Returns hash value of an iterable value. */
	static public int hash(Iterable<?> I) {
		return UObject.hash(I.iterator());
	}
	/** Returns hash value of a map value. */
	@SuppressWarnings("unchecked")
	static public int hash(Map M) {
		int H = 0;
		int Offset = 10;
		List Indexs = new Vector(M.entrySet());
		Collections.sort(Indexs, UObject.getTheComparator());
		for(int i = Indexs.size(); --i >= 0;) {
			H += (i + Offset)*((UObject.hash(Indexs.get(i)) << 3) + UObject.hash(M.get(Indexs.get(i))));
		}
		return H;
	}
	/** Returns hash value of a field-holder value. */
	@SuppressWarnings("unchecked")
	static public int hash(FieldHolder F) {
		int H = 0;
		int Offset = 10;
		List<String> Indexs = new Vector<String>();
		String[] FNs = F.getFieldNames();
		int L = F.getFieldCount();
		for(int i = 0; i < L; i++) Indexs.add(FNs[i]);
		Collections.sort(Indexs, UObject.getTheComparator());
		for(int i = L; --i >= 0;) {
			H += (i + Offset)*((UObject.hash(Indexs.get(i)) << 3) + UObject.hash(F.getData(Indexs.get(i))));
		}
		return H;
	}

	/** Returns hash value of an object value. */
	static public int hash(Object O) {
		if(O == null) return 0;
		
		if(O instanceof Objectable) return ((Objectable)O).hash();

		// Primitive ---------------------------------------------------------
		if(O instanceof    Class<?>) return UObject.hash(((Class<?>)  O).getCanonicalName() + ":Class");
		if(O instanceof     Boolean) return UObject.hash((Boolean)    O);
		if(O instanceof      Number) return UObject.hash((Number)     O);
		if(O instanceof   Character) return UObject.hash((Character)  O);
		if(O instanceof      String) return UObject.hash((String)     O);
		if(O instanceof Iterator<?>) return UObject.hash((Iterator<?>)O);
		if(O instanceof Iterable<?>) return UObject.hash((Iterable<?>)O);
		if(O instanceof   Map<?, ?>) return UObject.hash((Map<?, ?>)  O);
		
		if(O instanceof FieldHolder) return UObject.hash((FieldHolder)O);

		if(O.getClass().isArray()) {
			int H = 0;
			int L = Array.getLength(O);
			int Offset = 10;
			for(int i = L; --i >= 0;) H += (i + Offset)*UObject.hash(Array.get(O, i));
			return H;
		}
		
		return O.hashCode();
	}
	
	// Is ------------------------------------------------------------------------------------------
	/** Checks if object O1 and O2 are the same value. */
	static public boolean is(Object O1, Object O2) {
		if(O1 == O2) return true;
		
		// Primitive ---------------------------------------------------------		
		if((O1 instanceof    Number) && (O2 instanceof    Number)) return UNumber.is((Number)O1, (Number)O2);
		if((O1 instanceof Character) && (O2 instanceof Character)) return O1.equals(O2);
		if((O1 instanceof   Boolean) && (O2 instanceof   Boolean)) return O1.equals(O2);
		if((O1 instanceof    String) && (O2 instanceof    String)) return O1.equals(O2);
		if( O1 instanceof Objectable)                              return ((Objectable)O1).is(O2);
		
		return false;
	}
	
	// Equal ---------------------------------------------------------------------------------------
	/** Checks if object O1 and O2 equals. */
	@SuppressWarnings("unchecked")
	static public boolean equal(Object O1, Object O2) {
		if(O1 == O2) return true;

		boolean IsNull1 = (O1 == null);
		boolean IsNull2 = (O2 == null);
		
		if(IsNull1 || IsNull2) {		
			if((O1 instanceof Number) || (O2 instanceof Number))
				return UNumber.equal((Number)O1, (Number)O2);
			
			if((O1 instanceof Character) || (O2 instanceof Character)) {
				if(     IsNull1) O1 = '\0';
				else if(IsNull2) O1 = '\0';
				return O1.equals(O2);
			}
			
			if((O1 instanceof Boolean) || (O2 instanceof Boolean)) {
				if(     IsNull1) O1 = false;
				else if(IsNull2) O1 = false;
				return O1.equals(O2);
			}
		
			// Some case, object simulate null
			if(IsNull2) return O1.equals(O2);
			else        return O2.equals(O1);
		}
		
		// Numbering
		if((O1 instanceof Number) && (O2 instanceof Number))
			return UNumber.equal((Number)O1, (Number)O2);
		
		if(O1 instanceof String)     return O1.equals(O2);
		if(O1 instanceof Objectable) return O1.equals(O2);
		if(is(O1, O2))               return true;
		
		if(!O1.getClass().isAssignableFrom(O2.getClass()) && !O2.getClass().isAssignableFrom(O1.getClass())) return false;

		// Collection ---------------------------------------------------------
		if((O1.getClass().isArray() || (O1 instanceof DataArray)) && (O2.getClass().isArray() || (O2 instanceof DataArray))) {
			boolean O1DA = (O1 instanceof DataArray);
			boolean O2DA = (O2 instanceof DataArray);
			int L  = O1DA?((DataArray)O1).getLength():Array.getLength(O1);
			int L2 = O2DA?((DataArray)O2).getLength():Array.getLength(O2);

			if(L != L2) return false;
			for(int i = L; --i >= 0;) {
				Object OO1 = O1DA?((DataArray)O1).getData(i):Array.get(O1, i);
				Object OO2 = O2DA?((DataArray)O2).getData(i):Array.get(O2, i);
				if(!UObject.equal(OO1, OO2)) return false;
			}
			return true;
		}
		if(((O1 instanceof Iterator) || (O1 instanceof Iterable)) &&
		   ((O2 instanceof Iterator) || (O2 instanceof Iterable))) {
			Iterator I1 = (O1 instanceof Iterable)?((Iterable)O1).iterator():(Iterator)O1;
			Iterator I2 = (O2 instanceof Iterable)?((Iterable)O2).iterator():(Iterator)O2;
			for(int i = 0; I1.hasNext(); i++) {
				if(I2.hasNext()) return false;
				if(!UObject.equal(I1.next(), I2.next())) return false;
			}
			return true;
		}
		if((O1 instanceof Map) && (O2 instanceof Map)) {
			Map M1 = (Map)O1;
			Map M2 = (Map)O2;
			if(M1.size() != M2.size()) return false;
			List Indexs1 = new Vector(M1.entrySet());
			List Indexs2 = new Vector(M2.entrySet());
			Collections.sort(Indexs1, UObject.getTheComparator());
			Collections.sort(Indexs2, UObject.getTheComparator());
			if(!UObject.equal(Indexs1, Indexs2)) return false;
			for(int i = Indexs1.size(); --i >= 0;) {
				if(!UObject.equal(       Indexs1.get(i) ,        Indexs2.get(i) )) return false;
				if(!UObject.equal(M1.get(Indexs1.get(i)), M2.get(Indexs2.get(i)))) return false;
			}
			return true;
		}
		if((O1 instanceof FieldHolder) && (O2 instanceof FieldHolder)) {
			FieldHolder F1 = (FieldHolder)O1;
			FieldHolder F2 = (FieldHolder)O2;
			if(F1.getFieldCount() != F2.getFieldCount()) return false;
			List<String> Indexs1 = new Vector<String>();
			List<String> Indexs2 = new Vector<String>();
			Collections.sort(Indexs1, UObject.getTheComparator());
			Collections.sort(Indexs2, UObject.getTheComparator());
			if(!UObject.equal(Indexs1, Indexs2)) return false;
			for(int i = Indexs1.size(); --i >= 0;) {
				if(!UObject.equal(           Indexs1.get(i) ,            Indexs2.get(i) )) return false;
				if(!UObject.equal(F1.getData(Indexs1.get(i)), F2.getData(Indexs2.get(i)))) return false;
			}
			return true;
		}

		return O1.equals(O2);
	}

	// Compare -------------------------------------------------------------------------------------
	/** Compares object O1 and O2. */
	@SuppressWarnings("unchecked")
	static public int compare(Object O1, Object O2) {
		if(O1 == O2) return 0;

		boolean IsNull1 = (O1 == null);
		boolean IsNull2 = (O2 == null);
		
		if(IsNull1 || IsNull2) {		
			if((O1 instanceof Number) || (O2 instanceof Number))
				return UNumber.compare((Number)O1, (Number)O2);
			
			else if((O1 instanceof Character) || (O2 instanceof Character)) {
				if(     IsNull1) O1 = '\0';
				else if(IsNull2) O1 = '\0';
				
			} else if((O1 instanceof Boolean) || (O2 instanceof Boolean)) {
				if(     IsNull1) O1 = false;
				else if(IsNull2) O1 = false;
			}
		}
		
		if((O1 instanceof Number) && (O2 instanceof Number))
			return UNumber.compare((Number)O1, (Number)O2);
		
		if((O1 instanceof String) && (O2 instanceof String))
			return (UObject.hash((String)O1) - UObject.hash((String)O2));
		
		if((O1 instanceof Comparable) && (O2 instanceof Comparable) &&
		   (O1.getClass().isAssignableFrom(O2.getClass()) || O2.getClass().isAssignableFrom(O1.getClass()))) {
			return ((Comparable)O1).compareTo(O2);
		}
		return UObject.hash(O1) - UObject.hash(O2);
	}
	
	// To String (RAW) -----------------------------------------------------------------------------
	/** Returns string representation of a Boolean value. */
	static public String toString(Boolean   B) { return (B == null)?"null":B.toString(); }
	/** Returns string representation of a boolean value. */
	static public String toString(boolean   B) { return B   ?"true":"false"; }
	/** Returns string representation of a byte value. */
	static public String toString(byte      B) { return    Byte.toString(B); }
	/** Returns string representation of a short value. */
	static public String toString(short     S) { return   Short.toString(S); }
	/** Returns string representation of a int value. */
	static public String toString(int       I) { return Integer.toString(I); }
	/** Returns string representation of a long value. */
	static public String toString(long      L) { return    Long.toString(L); }
	/** Returns string representation of a float value. */
	static public String toString(float     F) { return   Float.toString(F); }
	/** Returns string representation of a double value. */
	static public String toString(double    D) { return  Double.toString(D); }
	/** Returns string representation of a Number value. */
	static public String toString(Number    N) { return (N == null)?"null":N.toString(); }
	/** Returns string representation of a char value. */
	static public String toString(char      C) { return Character.toString(C); }
	/** Returns string representation of a Character value. */
	static public String toString(Character C) { return (C == null)?"null":C.toString(); }
	/** Returns string representation of a number value. */
	static public String toString(String    S) { return (S == null)?"NULL":S; }
	/** Returns string representation of an iterator value. */
	
	static public String toString(Iterator<?> I, String pOpen, String pClose, String pSeparator) {
		if(I == null) return "null:" + pOpen + pClose;
		StringBuffer SB = new StringBuffer();
		SB.append(pOpen);
		for(int i = 0; I.hasNext(); i++) {
			if(i != 0) SB.append(pSeparator);
			Object N = I.next();
			SB.append(UObject.toString(N));
		}
		SB.append(pClose);
		return SB.toString();
	}
	/** Returns string representation of an iterator value. */
	static public String toString(Iterator<?> I) { return toString(I, "{", "}", ", "); }
	
	/** Returns string representation of an iterable value. */
	static public String toString(Iterable<?> I, String pOpen, String pClose, String pSeparator) {
		return UObject.toString(I.iterator(), pOpen, pClose, pSeparator);
	}
	/** Returns string representation of an iterable value. */
	static public String toString(Iterable<?> I) {
	    return (I == null)?"null":UObject.toString(I.iterator());
	}
	/** Returns string representation of a map value. */
	@SuppressWarnings("unchecked")
	static public String toString(Map M, String pOpen, String pClose, String pAssociate, String pSeparator) {
		if(M        == null) return "null:" + pOpen + pAssociate + pClose;
		if(M.size() ==    0) return pOpen + pAssociate + pClose;
		StringBuffer SB = new StringBuffer();
		SB.append(pOpen);
		List Keys = new Vector(M.keySet());
		Collections.sort(Keys, UObject.getTheComparator());
		for(int i = 0; i < Keys.size(); i++) {
			if(i != 0) SB.append(pSeparator);
			Object K = Keys.get(i);
			SB.append(UObject.toString(K));
			SB.append(pAssociate);
			SB.append(UObject.toString(M.get(K)));
		}
		SB.append(pClose);
		return SB.toString();
	}
	/** Returns string representation of a map value. */
	@SuppressWarnings("unchecked")
	static public String toString(Map M, boolean isLeading, int pWidth, String pOpen, String pClose,
			                String pAssociate, String pSeparator, String pPrefix) {
		if(M        == null) return "null:" + pOpen + pAssociate + pClose;
		if(M.size() ==    0) return pOpen + pAssociate + pClose;

		List Keys = new Vector(M.keySet());
		if(pWidth < 0) {
			for(int i = 0; i < Keys.size(); i++) {
				Object K = Keys.get(i);
				int L = UObject.toString(K).length();
				if(L > pWidth) pWidth = L;
			}
			pWidth++;
		}
		String NewSeparator = pSeparator + "\n\t" + pPrefix;
		
		StringBuffer SB = new StringBuffer();
		SB.append(pOpen + "\n\t");
		Collections.sort(Keys, UObject.getTheComparator());
		for(int i = 0; i < Keys.size(); i++) {
			if(i != 0) SB.append(NewSeparator);
			Object K = Keys.get(i);
			SB.append(isLeading?UString.ls(UObject.toString(K), pWidth):UString.ts(UObject.toString(K), pWidth));
			SB.append(pAssociate);
			SB.append(UObject.toString(M.get(K)));
		}
		SB.append("\n" + pClose);
		return SB.toString();
	}
	/** Returns string representation of a map value. */
	@SuppressWarnings("unchecked")
	static public String toString(Map M) { return toString(M, "{", "}", "->", ", "); }
	
	/** Returns string representation of an iterator value. */
	@SuppressWarnings("unchecked")
	static public String toString(FieldHolder F, String pOpen, String pClose, String pSeparator) {
		if(F                 == null) return "null" + pOpen + pSeparator + pClose;
		if(F.getFieldCount() ==    0) return pOpen + pSeparator + pClose;
		StringBuffer SB = new StringBuffer();
		SB.append(pOpen);
		List<String> Keys = new Vector();
		String[] FNs = F.getFieldNames();
		int L = F.getFieldCount();
		for(int i = 0; i < L; i++) Keys.add(FNs[i]);
		Collections.sort(Keys, UObject.getTheComparator());
		for(int i = 0; i < Keys.size(); i++) {
			if(i != 0) SB.append(pSeparator);
			String K = Keys.get(i);
			SB.append(UObject.toString(K));
			SB.append(": ");
			SB.append(UObject.toString(F.getData(K)));
		}
		SB.append(pClose);
		return SB.toString();
	}
	/** Returns string representation of a field-holder value. */
	static public String toString(FieldHolder F) { return toString(F, "{", "}", "; "); }
	
	/** Returns string representation of a data array value. */
	static public String toString(DataArray<?> O) {
		if(O == null) return "null";
		StringBuffer SB = new StringBuffer();
		SB.append("[");
		for(int i = 0; i < O.getLength(); i++) {
			if(i != 0) SB.append(", ");
			Object Oi = O.getData(i);
			if(Oi instanceof String) SB.append("\"").append(Oi).append("\"");
			else                     SB.append(UObject.toString(Oi));
		}
		SB.append("]");
		return SB.toString();
	}
	/** Returns string representation of an object value. */
	static public String toString(Object O) {
		// Mull
		if(O == null) return "null";

		if(O instanceof Objectable) return O.toString();
		
		// Array
		if(O.getClass().isArray())    return UArray.toString(O);
		if(O instanceof DataArray<?>) return toString((DataArray<?>)O);
		
		if(O instanceof     Boolean) return UObject.toString((Boolean)    O);
		if(O instanceof      Number) return UObject.toString((Number)     O);
		if(O instanceof   Character) return UObject.toString((Character)  O);
		if(O instanceof      String) return UObject.toString((String)     O);
		if(O instanceof FieldHolder) return UObject.toString((FieldHolder)O);
		if(O instanceof Iterator<?>) return UObject.toString((Iterator<?>)O);
		if(O instanceof Iterable<?>) return UObject.toString((Iterable<?>)O);
		if(O instanceof    Map<?,?>) return UObject.toString((Map<?, ?>)  O);
		
		if(O instanceof Class<?>) {
			if(UArray.isArrayType((Class<?>)O)) {
				if(DataArray.class.isAssignableFrom((Class<?>)O)) return "[]";
				Class<?> CC = UArray.getComponentType_OfType((Class<?>)O);
				if(CC.isPrimitive()) {
					if(CC ==     int.class) return     "int[]";
					if(CC == boolean.class) return "boolean[]";
					if(CC ==  double.class) return  "double[]";
					if(CC ==    char.class) return    "char[]";
					if(CC ==    byte.class) return    "byte[]";
					if(CC ==    long.class) return    "long[]";
					if(CC ==   float.class) return   "float[]";
					if(CC ==   short.class) return   "short[]";
				}
				String CName = CC.getCanonicalName();
				if(CName.startsWith("java.")) {
					int Index = CName.lastIndexOf(".");
					CName = CName.substring(Index + 1, CName.length());
				}
				return CName + "[]";
			}
			String CName = ((Class<?>)O).getCanonicalName();
			int Index = CName.lastIndexOf(".");
			CName = CName.substring(Index + 1, CName.length());
			return CName;
		}
		
		return O.toString();
	}
	
	// To Detail (Decorate) ------------------------------------------------------------------------
	/** Returns string representation of a Boolean value. */
	static public String toDetail(Boolean   B) { return (B == null)?"null":B.toString(); }
	/** Returns string detail representation of a boolean value. */
	static public String toDetail(boolean   B) { return B   ?"true":"false"; }
	/** Returns string detail representation of a byte value. */
	static public String toDetail(byte      B) { return    Byte.toString(B); }
	/** Returns string detail representation of a short value. */
	static public String toDetail(short     S) { return   Short.toString(S); }
	/** Returns string detail representation of a int value. */
	static public String toDetail(int       I) { return Integer.toString(I); }
	/** Returns string detail representation of a long value. */
	static public String toDetail(long      L) { return    Long.toString(L); }
	/** Returns string detail representation of a float value. */
	static public String toDetail(float     F) { return   Float.toString(F); }
	/** Returns string detail representation of a double value. */
	static public String toDetail(double    D) { return  Double.toString(D); }
	/** Returns string detail representation of a Number value. */
	static public String toDetail(Number    N) { return (N == null)?"null":N.toString(); }
	/** Returns string detail representation of a char value. */
	static public String toDetail(char      C) { return Character.toString(C); }
	/** Returns string detail representation of a Character value. */
	static public String toDetail(Character C) { return (C == null)?"null":C.toString(); }
	/** Returns string detail representation of a String value. */
	static public String toDetail(String S) {
		return (S == null)?"NULL":("\"" + UString.escapeText(S) + "\"");
	}
	/** Returns string detail representation of an iterator value. */
	static public String toDetail(Iterator<?> I) {
		if(I == null) return "null:{}";
		StringBuffer SB = new StringBuffer();
		SB.append("{");
		if(I instanceof Resetable) ((Resetable)I).reset();
		for(int i = 0; I.hasNext(); i++) {
			if(i != 0) SB.append(", ");
			Object N = I.next();
			SB.append(UObject.toDetail(N));
		}
		SB.append("}");
		return SB.toString();
	}
	/** Returns string detail representation of an iterable value. */
	static public String toDetail(Iterable<?> I) {
		return UObject.toDetail(I.iterator());
	}
	/** Returns string detail representation of a map value. */
	@SuppressWarnings("unchecked")
	static public String toDetail(Map M) {
		if(M        == null) return "null:{->}";
		if(M.size() ==    0) return "{->}";
		StringBuffer SB = new StringBuffer();
		SB.append("{ ");
		List Keys = new Vector(M.keySet());
		Collections.sort(Keys, UObject.getTheComparator());
		for(int i = 0; i < Keys.size(); i++) {
			if(i != 0) SB.append(", ");
			Object K = Keys.get(i);
			SB.append(UObject.toDetail(K));
			SB.append("->");
			SB.append(UObject.toDetail(M.get(K)));
		}
		SB.append(" }");
		return SB.toString();
	}
	/** Returns string detail representation of a fieldholder value. */
	@SuppressWarnings("unchecked")
	static public String toDetail(FieldHolder F) {
		if(F                 == null) return "null:{;}";
		if(F.getFieldCount() ==    0) return "{;}";
		StringBuffer SB = new StringBuffer();
		SB.append("{ ");
		List<String> Keys = new Vector();
		String[] FNs = F.getFieldNames();
		int L = F.getFieldCount();
		for(int i = 0; i < L; i++) Keys.add(FNs[i]);
		Collections.sort(Keys, UObject.getTheComparator());
		for(int i = 0; i < Keys.size(); i++) {
			if(i != 0) SB.append("; ");
			String K = Keys.get(i);
			SB.append(UObject.toString(K));
			SB.append(": ");
			SB.append(UObject.toString(F.getData(K)));
		}
		SB.append(" }");
		return SB.toString();
	}
	/** Returns string detail representation of a data array value. */
	static public String toDetail(DataArray<?> O) {
		StringBuffer SB = new StringBuffer();
		SB.append("[");
		for(int i = 0; i < O.getLength(); i++) {
			if(i != 0) SB.append(", ");
			Object Oi = O.getData(i);
			if(Oi instanceof String) SB.append("\"").append(Oi).append("\"");
			else                     SB.append(UObject.toDetail(Oi));
		}
		SB.append("]");
		return SB.toString();
	}
	/** Returns string detail representation of an object value. */
	static public String toDetail(Object O) {
		if(O == null) return "null";
		
		if(O instanceof Objectable)
			return ((Objectable)O).toDetail();
		
		if(O.getClass().isArray()) {
			StringBuffer SB = new StringBuffer();
			SB.append("[");
			for(int i = 0; i < Array.getLength(O); i++) {
				if(i != 0) SB.append(", ");
				Object Oi = Array.get(O,i);
				if(Oi instanceof String) SB.append("\"").append(Oi).append("\"");
				else                     SB.append(UObject.toDetail(Oi));
			}
			SB.append("]");
			return SB.toString();
		}
		
		if(O instanceof DataArray<?>) {
			StringBuffer SB = new StringBuffer();
			SB.append("[");
			for(int i = 0; i < ((DataArray<?>)O).getLength(); i++) {
				if(i != 0) SB.append(", ");
				Object Oi = ((DataArray<?>)O).getData(i);
				if(Oi instanceof String) SB.append("\"").append(Oi).append("\"");
				else                     SB.append(UObject.toDetail(Oi));
			}
			SB.append("]");
			return SB.toString();
		}
		
		if(O instanceof     Boolean) return UObject.toDetail((Boolean)    O);
		if(O instanceof      Number) return UObject.toDetail((Number)     O);
		if(O instanceof   Character) return UObject.toDetail((Character)  O);
		if(O instanceof      String) return UObject.toDetail((String)     O);
		if(O instanceof FieldHolder) return UObject.toDetail((FieldHolder)O);
		if(O instanceof Iterator<?>) return UObject.toDetail((Iterator<?>)O);
		if(O instanceof Iterable<?>) return UObject.toDetail((Iterable<?>)O);
		if(O instanceof   Map<?, ?>) return UObject.toDetail((Map<?, ?>)  O);

		if(O instanceof Class<?>) {
			if(UArray.isArrayType((Class<?>)O)) {
				if(DataArray.class.isAssignableFrom((Class<?>)O)) return "[]";
				Class<?> CC = ((Class<?>)O).getComponentType();
				if(CC.isPrimitive()) {
					if(CC ==     int.class) return     "int[]";
					if(CC == boolean.class) return "boolean[]";
					if(CC ==  double.class) return  "double[]";
					if(CC ==    char.class) return    "char[]";
					if(CC ==    byte.class) return    "byte[]";
					if(CC ==    long.class) return    "long[]";
					if(CC ==   float.class) return   "float[]";
					if(CC ==   short.class) return   "short[]";
				}
				String CName = CC.getCanonicalName();
				if(CName.startsWith("java.")) {
					int Index = CName.lastIndexOf(".");
					CName = CName.substring(Index + 1, CName.length());
				}
				return CName + "[]";
			}
			String CName = ((Class<?>)O).getCanonicalName();
			if(CName.startsWith("java.")) {
				int Index = CName.lastIndexOf(".");
				CName = CName.substring(Index + 1, CName.length());
			}
			return CName;
		}
		
		FieldHolder FH = new FieldHolderObject(O);
		return UObject.toDetail(FH);
	}
	
	/** For Testing. */
	static public void main(String ... args) {
		System.out.println(is(5, 3+2));
		System.out.println(toString(5));
		System.out.println(toDetail(5));
		System.out.println(toString(new int[] {5,10}));
		System.out.println(toDetail(new int[] {5,10}));
		
		List<Object> L = new Vector<Object>();
		L.add(6);
		L.add("NawaMan");
		L.add('N');
		L.add(true);
		L.add(1.578);
		System.out.println(toString(L));
		System.out.println(toDetail(L));
		
		Map<Object, Object> M = new Hashtable<Object, Object>();
		M.put(1, 6);
		M.put(2, "NawaMan");
		M.put(5,'N');
		M.put(7, true);
		M.put(3, 1.578);
		M.put(6, new StringBuffer());
		M.put(4, new java.io.File("."));
		System.out.println(toString(M));
		System.out.println(toDetail(M));
	}
}
