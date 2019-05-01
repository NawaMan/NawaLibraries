package net.nawaman.util.chararray;

import java.io.*;
import java.lang.reflect.Array;
import java.util.*;

import net.nawaman.util.ArrayIterator;
import net.nawaman.util.DataArray;

/** Array of chars */
abstract public class CharArray implements CharSequence, DataArray<Character>, Comparable<CharSequence>, Serializable {
	
	private static final long serialVersionUID = 9172616695869260479L;
	
	/** Constructs a char array. */
	public CharArray() {}
	
	/** Compares this char array with the char sequence CS. */
	public int compareTo(CharSequence CS) {
		if(CS == null) return this.length();
		int len1 = this.length();
		int len2 = CS.length();
		int n = Math.min(len1, len2);

		int i = 0;
		while (i < n) {
			char c1 = this.getData(i);
			char c2 = CS.charAt(i);
			if (c1 != c2) return c1 - c2;
			i++;
		}
		return len1 - len2;
	}
	/** Compares this char array with the char sequence CS without consider case. */
	public int compareToIgnoreCase(CharSequence CS) {
		if(CS == null) return this.length();
		int len1 = this.length();
		int len2 = CS.length();
		int n = Math.min(len1, len2);

		int i = 0;
		while (i < n) {
			char c1 = Character.toLowerCase(this.getData(i));
			char c2 = Character.toLowerCase(CS.charAt(i));
			if (c1 != c2) return c1 - c2;
			i++;
		}
		return len1 - len2;
	}
	
	/** Returns the char value at the position index. */
	public char charAt(int index) {
		return this.getData(index);
	}
	/** Returns char sequence of this char array from start to end. */
	public CharSequence subSequence(int start, int end) {
		if(start >= this.length()) start = this.length() - 1;
		if(end   >= this.length()) end   = this.length() - 1;
		if(start < 0) start = 0;
		if(end   < 0) end   = 0;
		if(start > end) { int temp = start; end = start; start = temp; }
		
		if(this.isStructure())  return new FixedLengthCharArray(this.get(start, end - start));
		if(this.isAppendable()) return new VirtualCharArray(this.asAppendable(), start, end - start);
		if(this.isVirtual())    return new VirtualCharArray(this.asVirtual(), start, end - start);
		return null;
	}
	/** Returns the string representation of this char array which is the same with its string value. */ @Override
	final public String toString() {
		return this.getString();
	}
	
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	
	/** Return the string value of this char array. */
	abstract public String getString();

	// Characteristic +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	/** Checks if this CharArray contains an actual chars */
	public boolean isActual() {
		return false;
	}

	/** Checks if this CharArray is an AppendableCharArray */
	public boolean isAppendable() {
		return false;
	}
	/** Returns this CharArray as an AppendableCharArray */
	public AppendableCharArray asAppendable() {
		return null;
	}

	/** Checks if this CharArray is a VirtualCharArray */
	public boolean isVirtual() {
		return false;
	}
	/** Returns this CharArray as a VirtualCharArray */
	public VirtualCharArray asVirtual() {
		return null;
	}

	/** Checks if this CharArray is a StructureCharArray */
	public boolean isStructure() {
		return false;
	}
	/** Checks if this CharArray is a StructureCharArray */
	public StructureCharArray asStructure() {
		return null;
	}

	// External behavior +++++++++++++++++++++++++++++++++++++++++++++++++++++++

	/** Returns the length of this array */
	abstract public int length();

	/** Returns the char at the position pPos */
	abstract public char get(int pPos);

	/** Sets the char value at the position pPos */
	abstract public char set(int pPos, char pChar);

	/** Returns char[] from pPos to pPos + pLength */
	abstract public char[] get(int pPos, int pLength);

	/** Set pChars from pPos */
	abstract public char[] set(int pPos, char[] pChars);

	/** Returns all chars as char[] */
	abstract public char[] getAll();

	/** Returns the hexadecimal representation of this CharArrays in this array */
	abstract public String toHex();
	
	// To satisfy DataArray ++++++++++++++++++++++++++++++++++++++++++++++++++++

	/** Returns the length of this array */
	public int getLength() {
		return this.length();
	}
	
	/** Returns the class data that this DataArray hold. */
	public Class<Character> getComponentClass() {
		return Character.class;
	}

	/** Returns the char at the position pPos */ @Override
	public Character getData(int pPos) {
		return this.get(pPos);
	}

	/** Sets the char value at the position pPos */ @Override
	public Object setData(int pPos, Character pChar) {
		if(pChar == null) return null;
		if((pPos < 0) || (pPos >= this.length())) return null;
		this.set(pPos, pChar.charValue());
		return pChar;
	}
	
    /** Returns an iterator over a set of elements of type T. */
    public Iterator<Character> iterator() {
    	return ArrayIterator.newArrayIterator(this);
    }
	
	/** Returns the array duplication of this DataArray */
	public Character[] toArray() {
		Character[] New = (Character[])Array.newInstance(Character.class, this.getLength());
		for(int i = New.length; --i >= 0; ) New[i] = this.get(i);
		return New;
	}
	
	/** Returns the array duplication of this DataArray of the type TargetComponentCls */
	@SuppressWarnings("unchecked")
	public <E> E[] toArrayOf(Class<? super Character> TargerComponentCls) {
		E[] New = (E[])Array.newInstance(TargerComponentCls, this.getLength());
		for(int i = New.length; --i >= 0; ) {
			Object C = this.get(i);
			New[i] = (E)C;
		}
		return New;
	}
	
	// Simulate String Functions ++++++++++++++++++++++++++++++++++++++++++++++
	/** Check if the char sequence CS equals to this char array from the position pPos. */
	protected boolean subEquals(int pPos, CharSequence CS, boolean isIgnoreCase) {
		if(pPos < 0)   return false;
		if(CS == null) return false;
		int this_length = this.length();
		int CS_length   = CS.length();
		if(CS_length + pPos > this_length) return false;
		if(isIgnoreCase) {
			for(int i = 0; i < CS_length; i++) {
				if(Character.toLowerCase(this.getData(pPos + i))
				   != Character.toLowerCase(CS.charAt(i))) return false;
			}
		} else {
			for(int i = 0; i < CS_length; i++) {
				if(this.getData(pPos + i) != CS.charAt(i)) return false;
			}
		}
		return true;
	}

	/** Check if this char array equals to the input object. */ @Override
	public boolean equals(Object anObject) {
		if(anObject == null) return false;
		if(!(anObject instanceof CharSequence)) return false;
		CharSequence CS = (CharSequence)anObject;
		int length = this.length();
		if(length != CS.length()) return false;
		return this.subEquals(0, CS, false);
	}
	/** Check if this char array equals to the input object without consider case. */
	public boolean equalsIgnoreCase(CharSequence CS) {
		if(CS == null) return false;
		int length = this.length();
		if(length != CS.length()) return false;
		return this.subEquals(0, CS, true);
	}
	
	/** Returns hash code of this char array. */ @Override
	public int hashCode() {
		int length = this.length();
		int _hashCode = "CharArray".hashCode();
		for(int i = 0; i < length; i++) _hashCode += (i + 5)*(i + 5)*this.getData(i);
		return _hashCode;
	}
	
	/** Returns the first index in the char array of the character ch. */
	public int indexOf(int ch) {
		return this.indexOf(ch, 0);
	}
	/** Returns the first index in the char array of the character ch from the index. */
	public int indexOf(int ch, int fromIndex) {
		int length = this.length();
		if(fromIndex >= length) return -1;
		for(int i = fromIndex; i < length; i++) if(this.getData(i) == ch) return i;
		return -1;
	}
	/** Returns the first index in the char array of the char sequence CS. */
	public int indexOf(CharSequence CS) {
		return this.indexOf(CS, 0);
	}
	/** Returns the first index in the char array of the char sequence CS from the index. */
	public int indexOf(CharSequence CS, int fromIndex) {
		if(CS == null) return -1;
		int pPos = this.indexOf(CS.charAt(0), fromIndex);
		if(pPos == -1) return -1;
		if(this.subEquals(pPos, CS, false)) return pPos;
		return -1;
	}
	
	/** Returns the last index in the char array of the character ch. */
	public int lastindexOf(int ch) {
		return this.lastindexOf(ch, 0);
	}
	/** Returns the last index in the char array of the character ch from the index. */
	public int lastindexOf(int ch, int fromIndex) {
		int length = this.length();
		if(fromIndex >= length) return -1;
		for(int i = fromIndex; i >= 0; i--) if(this.getData(i) == ch) return i;
		return -1;
	}
	/** Returns the last index in the char array of the char sequence CS. */
	public int lastindexOf(CharSequence CS) {
		return this.lastindexOf(CS, 0);
	}
	/** Returns the last index in the char array of the char sequence CS from the index. */
	public int lastindexOf(CharSequence CS, int fromIndex) {
		if(CS == null) return -1;
		int pPos = this.indexOf(CS.charAt(0), fromIndex);
		if(pPos == -1) return -1;
		if(this.subEquals(pPos, CS, false)) return pPos;
		return -1;
	}
	
	/** Check if the char array is empty. */
	public boolean isEmpty() {
		return (this.length() == 0);
	}
	
	/** Splits this char array with the char sequence 'Cut'. */
	public String[] split(CharSequence Cut) {
		return this.split(Cut, -1);
	}
	/** Splits this char array with the char sequence 'Cut' by the limit. */
	public String[] split(CharSequence Cut, int limit) {
		String St = this.getString();
		Vector<String> S = new Vector<String>(); 
		int l = 0;
		int p = 0;
		int o = 0;
		while(l < limit) {
			p = this.indexOf(Cut, o);
			if(p == -1) break;
			S.add(St.substring(o, p - o));
			o += Cut.length();
		}
		String[] Sts = new String[S.size()];
		S.toArray(Sts);
		return Sts;
	}
	
	/** Checks if this char array starts with the char sequence. */
	public boolean startsWith(CharSequence CS) {
		return (CS != null) && this.subEquals(0, CS, false);
	}
	/** Checks if this char array ends with the char sequence. */
	public boolean endsWith  (CharSequence CS) {
		return (CS != null) && this.subEquals(CS.length(), CS, false);
	}
	
	/** Returns sub string from the beginIndex to the end of the array. */
	public String substring(int beginIndex) {
		return this.substring(beginIndex, this.length() - 1);
	}
	/** Returns sub string from the beginIndex to the endIndex of the array. */
	public String substring(int beginIndex, int endIndex) {
		if(beginIndex >= this.length()) beginIndex = this.length() - 1;
		if(endIndex   >= this.length()) endIndex   = this.length() - 1;
		if(beginIndex <              0) beginIndex = 0;
		if(endIndex   <              0) endIndex   = 0;
		if(beginIndex > endIndex) { int temp = beginIndex; beginIndex = endIndex; endIndex = temp; }
		return new String(this.get(beginIndex, endIndex - beginIndex));
	}
	
	/** Returns array of char represented by this char array. */
	public char[] toCharArray() {
		return this.getAll();
	}

	/** Returns the lower case of this char array. */
	public String toLowerCase() {
		return (new String(this.getAll())).toLowerCase();
	}
	/** Returns the upper case of this char array. */
	public String toUpperCase() {
		return (new String(this.getAll())).toUpperCase();
	}
}