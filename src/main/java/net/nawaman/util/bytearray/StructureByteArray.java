package net.nawaman.util.bytearray;

import java.lang.reflect.*;
import java.util.*;

import net.nawaman.util.*;

/**
 * <CODE>StructureByteArray</CODE> is a <CODE>ByteArray</CODE> that are
 * composed of many structures. Each structures can be used as it is a
 * ByteArray.<br>
 * The main use of this class is for constructing a ByteArray from may smaller
 * ones
 */
final public class StructureByteArray implements ByteArray {

	/** Constructors an empty StructureByteArray */
	public StructureByteArray() {
		this(null);
	}

	/** Constructors a StructureByteArray from an array of <CODE>ByteArray</CODE>s */
	public StructureByteArray(ByteArray[] pBAs) {
		this.StctArray = (pBAs == null) ? new ByteArray[0] : pBAs;
		this.StctLength = (pBAs == null) ? 0 : this.StctArray.length;
	}

	// Internal Data +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	private ByteArray[] StctArray = null;

	private int StctLength = 0;

	// Characteristic +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	
	/** Checks if this ByteArray contains an actual bytes*/
	public boolean isActual()     { return false; }
	
	/** Checks if this ByteArray is an AppendableByteArray */
	public boolean isAppendable() { return false; }
	
	/** Returns this ByteArray as an AppendableByteArray */
	public AppendableByteArray asAppendable() { return null; }
	
	/** Checks if this ByteArray is a VirtualByteArray */
	public boolean isVirtual() { return false; }
	
	/** Returns this ByteArray as a VirtualByteArray */
	public VirtualByteArray asVirtual() { return null; }
	
	/** Checks if this ByteArray is a StructureByteArray */
	public boolean isStructure() { return true; }
	
	/** Checks if this ByteArray is a StructureByteArray */
	public StructureByteArray asStructure() { return this; }

	// External behavior +++++++++++++++++++++++++++++++++++++++++++++++++++++++

	// Structure ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	/** Returns the number of structures */
	public int getStructureCount() {
		return this.StctLength;
	}

	/** Returns the structure at the position */
	public ByteArray getStructure(int pPos) {
		if (this.StctArray == null)
			return null;
		if ((pPos < 0) && (pPos >= this.StctLength))
			return null;

		return this.StctArray[pPos];
	}

	/** Sets ByteArray as the structure at the position */
	public void setStructure(int pPos, ByteArray BA) {
		if (this.StctArray == null)
			return;
		if ((pPos < 0) && (pPos >= this.StctLength))
			return;

		this.StctArray[pPos] = BA;
	}

	/** Sets byte[] as the value of the structure at the position */
	public void setStructure(int pPos, byte[] bs) {
		this.setStructure(pPos, new FixedLengthByteArray(bs));
	}

	/** Returns the position of BA */
	public int indexOf(ByteArray BA) {
		for (int i = 0; i < this.StctLength; i++) {
			if (BA == this.StctArray[i])
				return i;
		}
		return -1;
	}

	/** Appends ByteArray as a structure */
	public void append(ByteArray BA) {
		if (this.StctLength >= this.StctArray.length) {
			ByteArray[] SA = new ByteArray[this.StctLength + 5];
			System.arraycopy(this.StctArray, 0, SA, 0, this.StctLength);
			this.StctArray = SA;
		}

		this.StctArray[this.StctLength] = BA;
		this.StctLength++;
	}

	/** Appends byte[] as a structure */
	public void append(byte[] bs) {
		this.append(new FixedLengthByteArray(bs));
	}

	/** Inserts ByteArray at the position of At */
	public void insertAt(ByteArray BA, ByteArray At) {
		int i = this.indexOf(At);
		if (i < 0)
			return;

		if (this.StctLength >= this.StctArray.length) {
			ByteArray[] SA = new ByteArray[this.StctLength + 5];
			System.arraycopy(this.StctArray, 0, SA, 0, this.StctLength);
			this.StctArray = SA;
		}

		System.arraycopy(this.StctArray, i, this.StctArray, i + 1,
				(this.StctLength - i));
		this.StctArray[i] = BA;
		this.StctLength++;
	}

	/** Trim the structures by one */
	public void trim() {
		if ((this.StctArray.length - this.StctLength) > 5) {
			ByteArray[] SA = new ByteArray[this.StctLength - 5];
			System.arraycopy(this.StctArray, 0, SA, 0, this.StctLength);
			this.StctArray = SA;
		}
		this.StctArray[this.StctLength - 1] = null;
		this.StctLength--;
	}

	/** Deletes ByteArray out of the structure */
	public void delete(ByteArray BA) {
		int i = this.indexOf(BA);
		if (i < 0)
			return;

		if ((this.StctArray.length - this.StctLength) > 5) {
			ByteArray[] SA = new ByteArray[this.StctLength - 5];
			System.arraycopy(this.StctArray, 0, SA, 0, this.StctLength);
			this.StctArray = SA;
		}

		System.arraycopy(this.StctArray, i + 1, this.StctArray, i,
				(this.StctLength - i));

		this.StctArray[this.StctLength - 1] = null;
		this.StctLength--;
	}

	/** Clears all the structure */
	public void clear() {
		if (this.StctArray.length > 5)
			this.StctArray = new ByteArray[5];
		for (int i = 0; i < this.StctArray.length; i++)
			this.StctArray[i] = null;
		this.StctLength = 0;
	}

	// Array +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

	/** Returns the length of this array */
	public int length() {
		if(this.StctArray == null) return 0;

		int l = 0;
		for(int i = 0; i < this.StctLength; i++)
			l += (this.StctArray[i] == null) ? 0 : this.StctArray[i].length();
		return l;
	}

	/** Returns the byte at the position pPos */
	public byte get(int pPos) {
		if(this.StctArray == null) return 0;

		if(pPos >= this.length()) return 0;
		if(pPos < 0)              return 0;

		// Find the first position
		int p = 0;
		int l = 0;
		for (int i = 0; i < this.StctLength; i++) {
			l += (this.StctArray[i] == null) ? 0 : this.StctArray[i].length();
			if (pPos < l) {
				p = i;
				l -= (this.StctArray[i] == null) ? 0 : this.StctArray[i]
						.length();
				break;
			}
		}
		return this.StctArray[p].get(pPos - l);
	}
	
	/** Sets the byte value at the position pPos */
	public byte set(int pPos, byte pByte) {
		if (this.StctArray == null) throw new NullPointerException();
		if (pPos >= this.length())  throw new ArrayIndexOutOfBoundsException(pPos);
		if (pPos < 0)               throw new ArrayIndexOutOfBoundsException(pPos);

		// Find the first position
		int p = 0;
		int l = 0;
		for (int i = 0; i < this.StctLength; i++) {
			l += (this.StctArray[i] == null) ? 0 : this.StctArray[i].length();
			if (pPos < l) {
				p = i;
				l -= (this.StctArray[i] == null) ? 0 : this.StctArray[i]
						.length();
				break;
			}
		}
		this.StctArray[p].set(pPos - l, pByte);
		return pByte;
	}

	/** Returns byte[] from pPos to pPos + pLength */
	public byte[] get(int pPos, int pLength) {
		if (this.StctArray == null)
			return new byte[0];

		int length = this.length();

		if (pPos >= length)
			return new byte[0];
		if (pLength < 0)
			return new byte[0];

		// Adjust
		if (pPos < 0) {
			pLength += pPos;
			pPos = 0;
		}
		if ((pPos + pLength) >= length) {
			pLength = length - pPos;
		}

		// Find the start position
		int p = 0;
		for (int i = 0; i < this.StctLength; i++) {
			int l = (this.StctArray[i] == null) ? 0 : this.StctArray[i]
					.length();
			if (pPos >= l) {
				pPos -= l;
				continue;
			}

			p = i;
			break;
		}

		// Copy the data
		byte[] bs = new byte[pLength];
		int EachPos = pPos;
		int ArrayPos = 0;
		for (int i = p; i < this.StctLength; i++) {
			if (this.StctArray[i] == null)
				continue;
			int len = this.StctArray[i].length();

			// For the fist time, the postion of this line may not 0
			if (i == p)
				len -= EachPos;

			// trim the length for the last line
			if ((pLength - ArrayPos) < len)
				len = (pLength - ArrayPos);

			System.arraycopy(this.StctArray[i].get(EachPos, len), 0, bs,
					ArrayPos, len);

			// Add the position
			ArrayPos += len;

			// For the fist time, set this to be 0 (the rest of the line will
			// start at 0)
			if (i == p)
				EachPos = 0;
		}

		return bs;
	}

	/** Set pBytes from pPos */
	public byte[] set(int pPos, byte[] pBytes) {
		if (this.StctArray == null) throw new NullPointerException();

		int length = this.length();
		int pLength = pBytes.length;

		if (pPos >= length) throw new ArrayIndexOutOfBoundsException(pPos);
		if (pLength < 0)    return pBytes;

		// Adjust
		if (pPos < 0)                   { pLength += pPos; pPos = 0; }
		if ((pPos + pLength) >= length) { pLength = length - pPos;   }

		// Find the start position
		int p = 0;
		for (int i = 0; i < this.StctLength; i++) {
			int l = (this.StctArray[i] == null) ? 0 : this.StctArray[i]
					.length();
			if (pPos >= l) {
				pPos -= l;
				continue;
			}

			p = i;
			break;
		}

		// Copy the data
		int EachPos = pPos;
		int ArrayPos = 0;
		byte[] bs = new byte[0];
		for (int i = p; i < this.StctLength; i++) {
			if (this.StctArray[i] == null) continue;
			int len = this.StctArray[i].length();

			// For the fist time, the position of this line may not 0
			if (i == p) len -= EachPos;

			// trim the length for the last line
			if ((pLength - ArrayPos) < len) len = (pLength - ArrayPos);

			if (bs.length != len) bs = new byte[len];
			System.arraycopy(pBytes, ArrayPos, bs, 0, len);
			this.StctArray[i].set(EachPos, bs);

			// Add the position
			ArrayPos += len;

			// For the fist time, set this to be 0 (the rest of the line will start at 0)
			if (i == p) EachPos = 0;
		}
		return pBytes;
	}

	/** Returns all bytes as byte[] */
	public byte[] getAll() { return this.get(0, this.length()); }

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
	@Override public Byte setData(int pPos, Byte pByte) {
		if(pByte == null)                         return null;
		if((pPos < 0) || (pPos >= this.length())) throw new ArrayIndexOutOfBoundsException(pPos);
		this.get(pPos, pByte.byteValue());
		return pByte;
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