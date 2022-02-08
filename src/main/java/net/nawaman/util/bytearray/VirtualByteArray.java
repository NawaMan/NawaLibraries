package net.nawaman.util.bytearray;

import java.lang.reflect.*;
import java.util.*;

import net.nawaman.util.*;

/**
 * <CODE>VirtualByteArray</CODE> is an unreal <CODE>ByteArray</CODE> or
 * ByteArray that do not actually contain an array of bytes but is virtually a
 * part of other ByteArray.
 */
final public class VirtualByteArray implements ByteArray {

	// Constructor ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	/** Constructs a VirtualByteArray */
	public VirtualByteArray(byte[] bs) {
		this(new FixedLengthByteArray(bs), 0, (bs != null) ? bs.length : 0, 0);
	}

	/** Constructs a VirtualByteArray */
	public VirtualByteArray(FixedLengthByteArray A) {
		this(A, 0, (A != null) ? A.length() : 0, 0);
	}

	/** Constructs a VirtualByteArray */
	public VirtualByteArray(AppendableByteArray A) {
		this(A, 0, (A != null) ? A.length() : 0, 0);
	}

	/** Constructs a VirtualByteArray */
	public VirtualByteArray(VirtualByteArray A) {
		this(A, 0, (A != null) ? A.length() : 0, 0);
	}

	/** Constructs a VirtualByteArray */
	public VirtualByteArray(FixedLengthByteArray A, int P) {
		this(A, P, (A != null) ? A.length() : 0, 0);
	}

	/** Constructs a VirtualByteArray */
	public VirtualByteArray(AppendableByteArray A, int P) {
		this(A, P, (A != null) ? A.length() : 0, 0);
	}

	/** Constructs a VirtualByteArray */
	public VirtualByteArray(VirtualByteArray A, int P) {
		this(A, P, (A != null) ? A.length() : 0, 0);
	}

	/** Constructs a VirtualByteArray */
	public VirtualByteArray(FixedLengthByteArray A, int P, int L) {
		this(A, P, L, 0);
	}

	/** Constructs a VirtualByteArray */
	public VirtualByteArray(AppendableByteArray A, int P, int L) {
		this(A, P, L, 0);
	}

	/** Constructs a VirtualByteArray */
	public VirtualByteArray(VirtualByteArray A, int P, int L) {
		this(A, P, L, 0);
	}

	/** Constructs a VirtualByteArray */
	protected VirtualByteArray(ByteArray BA, int P, int L, int dummy) {
		// Assign the internal data
		this.Parent = BA;
		this.Pos = P;
		this.Length = L;

		// Ensure non-negative position
		if (this.Pos < 0)
			this.Pos = 0;

		// Ensure not too long length
		if ((this.Pos + this.Length) > BA.length())
			this.Length = BA.length() - this.Pos;
	}

	// Characteristic ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	
	/** Checks if this ByteArray contains an actual bytes*/
	public boolean isActual()     { return false; }
	
	/** Checks if this ByteArray is an AppendableByteArray */
	public boolean isAppendable() { return false; }
	
	/** Returns this ByteArray as an AppendableByteArray */
	public AppendableByteArray asAppendable() { return null; }
	
	/** Checks if this ByteArray is a VirtualByteArray */
	public boolean isVirtual() { return true; }
	
	/** Returns this ByteArray as a VirtualByteArray */
	public VirtualByteArray asVirtual() { return this; }
	
	/** Checks if this ByteArray is a StructureByteArray */
	public boolean isStructure() { return false; }
	
	/** Checks if this ByteArray is a StructureByteArray */
	public StructureByteArray asStructure() { return null; }

	// Internal data +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

	private ByteArray Parent = null;

	private int Pos    = 0;
	private int Length = 0;

	// External behavior +++++++++++++++++++++++++++++++++++++++++++++++++++++++
	/**
     * Generates and returns a sub VirualByteArray that mapped from pPos to the
     * end of this ByteArray
     */
	public ByteArray generateSub(int pPos) { return new VirtualByteArray(this, pPos, this.Length); }

	/** Generates and returns a sub VirualByteArray that mapped from pPos to pPos + pLength */
	public ByteArray generateSub(int pPos, int pLength) { return new VirtualByteArray(this, pPos, pLength); }

	/** Returns the length of this array */
	public int length() { return this.Length; }

	/** Returns the byte at the position pPos */
	public byte get(int pPos) {
		if ((pPos < 0) || (pPos >= this.Length)) return 0;
		if (this.Parent == null)                 return 0;
		return this.Parent.get(this.Pos + pPos);
	}
	/** Sets the byte value at the position pPos */
	public byte set(int pPos, byte pByte) {
		if ((pPos < 0) || (pPos >= this.Length)) throw new ArrayIndexOutOfBoundsException(pPos);
		if (this.Parent == null)                 throw new NullPointerException("Null parent for virtual byte array.");
		this.Parent.set(this.Pos + pPos, pByte);
		return pByte;
	}
	/** Returns byte[] from pPos to pPos + pLength */
	public byte[] get(int pPos, int pLength) {
		if (this.Parent == null) return new byte[0];

		if (pPos >= this.Length) return new byte[0];
		if (pLength < 0)         return new byte[0];

		// Adjust
		if (pPos < 0)                        { pLength += pPos; pPos = 0; }
		if ((pPos + pLength) >= this.Length) { pLength = this.Length - pPos; }

		return this.Parent.get(this.Pos + pPos, pLength);
	}
	/** Set pBytes from pPos */
	public byte[] set(int pPos, byte[] pBytes) {
		if (this.Parent == null) throw new NullPointerException("Null parent for virtual byte array.");

		if (pPos >= this.Length) throw new ArrayIndexOutOfBoundsException(pPos);

		int pLength = pBytes.length;
		if (pLength == 0) return pBytes;

		// Adjust
		if (pPos < 0)                        { pLength += pPos; pPos = 0; }
		if ((pPos + pLength) >= this.Length) { pLength = this.Length - pPos; }

		this.Parent.set(this.Pos + pPos, pBytes);
		return pBytes;
	}
	/** Returns all bytes as byte[] */
	public byte[] getAll() { return this.get(0, this.Length); }

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
		if(pChar == null) return null;
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