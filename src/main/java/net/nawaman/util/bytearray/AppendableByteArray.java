package net.nawaman.util.bytearray;

import java.lang.reflect.*;
import java.util.*;

import net.nawaman.util.ArrayIterator;

/** <CODE>AppendableByteArray</CODE> is a <CODE>ByteArray</CODE> that can be appended. */
final public class AppendableByteArray implements ByteArray {

	// Constructor +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	/** Constructs an AppendableByteArray */
	public AppendableByteArray() {
	}

	/** Constructs an AppendableByteArray with the initial length pLength */
	public AppendableByteArray(int pLength) {
		if (pLength < 0)
			pLength = 0;
		this.Data = new byte[pLength];
	}

	/** Constructs an AppendableByteArray from the intal pBytes */
	public AppendableByteArray(byte[] pBytes) {
		this.Data = pBytes;
		this.DLength = (this.Data == null) ? 0 : this.Data.length;
	}

	// Characteristic ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	/** Checks if this ByteArray contains an actual bytes*/
	public boolean isActual()     { return true; }
	
	/** Checks if this ByteArray is an AppendableByteArray */
	public boolean isAppendable() { return true; }
	
	/** Returns this ByteArray as an AppendableByteArray */
	public AppendableByteArray asAppendable() { return this; }
	
	/** Checks if this ByteArray is a VirtualByteArray */
	public boolean isVirtual() { return false; }
	
	/** Returns this ByteArray as a VirtualByteArray */
	public VirtualByteArray asVirtual() { return null; }
	
	/** Checks if this ByteArray is a StructureByteArray */
	public boolean isStructure() { return false; }
	
	/** Checks if this ByteArray is a StructureByteArray */
	public StructureByteArray asStructure() { return null; }

	// Internal Data +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

	private byte[] Data = null;

	private int DLength = 0;

	// External behavior +++++++++++++++++++++++++++++++++++++++++++++++++++++++

	/** Returns the length of this array */
	public int length() { return this.DLength; }

	/** Returns the byte at the position pPos */
	public byte get(int pPos) { return ((pPos < 0) || (pPos >= this.DLength)) ? 0 : this.Data[pPos]; }

	/** Sets the byte value at the position pPos */
	public byte set(int pPos, byte pByte) {
		if ((pPos < 0) || (pPos >= this.DLength)) throw new ArrayIndexOutOfBoundsException(pPos);
		this.Data[pPos] = pByte;
		return pByte;
	}

	/** Returns byte[] from pPos to pPos + pLength */
	public byte[] get(int pPos, int pLength) {
		if(this.Data == null) return new byte[0];

		if(pPos >= this.DLength) return new byte[0];
		if(pLength < 0)          return new byte[0];

		// Adjust
		if(pPos < 0)                         { pLength += pPos; pPos = 0; }
		if((pPos + pLength) >= this.DLength) { pLength = this.DLength - pPos; }

		// Duplicate
		byte[] Temp = new byte[pLength];
		System.arraycopy(this.Data, pPos, Temp, 0, pLength);
		return Temp;
	}

	/** Set pBytes from pPos */
	public byte[] set(int pPos, byte[] pBytes) {
		if(this.Data == null)    throw new NullPointerException();
		if(pPos >= this.DLength) throw new ArrayIndexOutOfBoundsException(pPos);

		int pLength = pBytes.length;
		if (pLength == 0) return pBytes;

		// Adjust
		if (pPos < 0)                         { pLength += pPos; pPos = 0; }
		if ((pPos + pLength) >= this.DLength) { pLength = this.DLength - pPos; }

		System.arraycopy(pBytes, 0, this.Data, pPos, pLength);
		return pBytes;
	}
	
	/** Returns all bytes as byte[] */
	public byte[] getAll() {
		if (this.Data == null)
			return new byte[0];
		// Duplicate
		byte[] Temp = new byte[this.DLength];
		System.arraycopy(this.Data, 0, Temp, 0, this.DLength);
		return Temp;
	}

	/** Sets the capacity of this appendable array ()extend only */
	public void setCapacity(int capacity) {
		if (capacity < this.DLength)
			return;

		byte[] Temp = new byte[capacity];
		System.arraycopy(this.Data, 0, Temp, 0, this.DLength);
		this.Data = Temp;
	}

	/** Appends pByte to this ByteArray */
	public void append(byte pByte) {
		// Increase capacity
		if (this.DLength >= this.Data.length) {
			byte[] Temp = new byte[this.DLength + 10];
			System.arraycopy(this.Data, 0, Temp, 0, this.DLength);
			this.Data = Temp;
		}

		// Assign data
		this.Data[this.DLength] = pByte;
		this.DLength++;
	}

	/** Appends pBytes to this ByteArray */
	public void append(byte[] pBytes) {
		if (pBytes == null)
			return;
		if (this.Data == null)
			this.Data = new byte[5];

		// Increase capacity
		if ((this.DLength + pBytes.length) >= this.Data.length) {
			byte[] Temp = new byte[this.DLength + pBytes.length];
			System.arraycopy(this.Data, 0, Temp, 0, this.DLength);
			this.Data = Temp;
		}

		// Assign data
		System.arraycopy(pBytes, 0, this.Data, this.DLength, pBytes.length);
		this.DLength += pBytes.length;
	}

	/** Returns the hexadecimal representation of this bytes in this array */
	public String toHex() { return UByte.bc2hex(this); }
	
	// To satisfy DataArray ++++++++++++++++++++++++++++++++++++++++++++++++++++

	/** Returns the length of this array */
	public int getLength() { return this.length(); }
	
	/** Returns the class data that this DataArray hold. */
	public Class<Byte> getComponentClass() { return Byte.class; }

	/** Returns the byte at the position pPos */
	@Override public Byte getData(int pPos) { return this.get(pPos); }

	/** Sets the byte value at the position pPos */
	@Override public Object setData(int pPos, Byte pChar) {
		if(pChar == null)                         return null;
		if((pPos < 0) || (pPos >= this.length())) throw new ArrayIndexOutOfBoundsException(pPos);
		this.get(pPos, pChar.byteValue());
		return pChar;
	}
	
    /** Returns an iterator over a set of elements of type T. */
    public Iterator<Byte> iterator() { return ArrayIterator.newArrayIterator(this); }
	
	/** Returns the array duplication of this DataArray */
	public Byte[] toArray() {
		Byte[] New = (Byte[])Array.newInstance(Byte.class, this.getLength());
		for(int i = New.length; --i >= 0; ) New[i] = this.get(i);
		return New;
	}
	
	/** Returns the array duplication of this DataArray of the type TargetComponentCls */
	@SuppressWarnings("unchecked")
	public <E> E[] toArrayOf(Class<? super Byte> TargerComponentCls) {
		E[] New = (E[])Array.newInstance(TargerComponentCls, this.getLength());
		for(int i = New.length; --i >= 0; ) {
			Object C = this.get(i);
			New[i] = (E)C;
		}
		return New;
	}
}