package net.nawaman.util.chararray;

import net.nawaman.util.*;

/**
 * <CODE>StructureCharArray</CODE> is a <CODE>CharArray</CODE> that is composed of many structures.
 * Each structures can be used as it is a CharArray.<br>
 * The main use of this class is for constructing a CharArray from may smaller ones
 */
public class StructureCharArray extends CharArray {
	
	private static final long serialVersionUID = -4569578825465377192L;
	
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

	/**{@inheritDoc}*/ @Override
	public String getString() {
		return new String(this.getAll());
	}

	/** Constructs an empty StructureCharArray * */
	public StructureCharArray() {
		this(null);
	}

	/** Constructs a StructureCharArray from an array of <CODE>CharArray</CODE>s * */
	public StructureCharArray(CharArray[] pCAs) {
		this.StctArray  = (pCAs == null) ? new CharArray[0] : pCAs;
		this.StctLength = (pCAs == null) ? 0 : this.StctArray.length;
	}

	// Internal Data +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	
	private CharArray[] StctArray  = null;
	private int         StctLength = 0;

	// Characteristic +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	
	/**{@inheritDoc}*/ @Override
	public boolean isStructure() {
		return true;
	}
	/**{@inheritDoc}*/ @Override
	public StructureCharArray asStructure() {
		return  this;
	}
	
	// External behavior +++++++++++++++++++++++++++++++++++++++++++++++++++++++

	// Structure ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	/** Returns the number of structures * */
	public int getStructureCount() {
		return this.StctLength;
	}

	/** Returns the structure at the position */
	public CharArray getStructure(int pPos) {
		if (this.StctArray == null) return null;
		if ((pPos < 0) && (pPos >= this.StctLength)) return null;

		return this.StctArray[pPos];
	}

	/** Sets CharArray as the structure at the position */
	public void setStructure(int pPos, CharArray BA) {
		if (this.StctArray == null) return;
		if ((pPos < 0) && (pPos >= this.StctLength)) return;

		this.StctArray[pPos] = BA;
	}

	/** Sets char[] as the value of the structure at the position */
	public void setStructure(int pPos, char[] cs) {
		this.setStructure(pPos, new FixedLengthCharArray(cs));
	}

	/** Sets char[] as the value of the structure at the position */
	public void setStructure(int pPos, String pStr) {
		this.setStructure(pPos, new FixedLengthCharArray(pStr));
	}

	/** Returns the position of CA */
	public int indexOf(CharArray CA) {
		for (int i = 0; i < this.StctLength; i++) {
			if (CA == this.StctArray[i]) return i;
		}
		return -1;
	}

	/** Appends CharArray as a structure */
	public void append(CharArray CA) {
		if (this.StctLength >= this.StctArray.length) {
			CharArray[] SA = new CharArray[this.StctLength + 5];
			System.arraycopy(this.StctArray, 0, SA, 0, this.StctLength);
			this.StctArray = SA;
		}

		this.StctArray[this.StctLength] = CA;
		this.StctLength++;
	}

	/** Appends char[] as a structure */
	public void append(char[] cs) {
		this.append(new FixedLengthCharArray(cs));
	}

	/** Inserts CharArray at the position of At */
	public void insertAt(CharArray CA, CharArray At) {
		int i = this.indexOf(At);
		if (i == -1) return;

		if (this.StctLength >= this.StctArray.length) {
			CharArray[] SA = new CharArray[this.StctLength + 5];
			System.arraycopy(this.StctArray, 0, SA, 0, this.StctLength);
			this.StctArray = SA;
		}

		System.arraycopy(this.StctArray, i, this.StctArray, i + 1,
				(this.StctLength - i));
		this.StctArray[i] = CA;
		this.StctLength++;
	}

	/** Trim the structures by one */
	public void trim() {
		if ((this.StctArray.length - this.StctLength) > 5) {
			CharArray[] SA = new CharArray[this.StctLength - 5];
			System.arraycopy(this.StctArray, 0, SA, 0, this.StctLength);
			this.StctArray = SA;
		}
		this.StctArray[this.StctLength - 1] = null;
		this.StctLength--;
	}

	/** Deletes CharArray out of the structure */
	public void delete(CharArray BA) {
		int i = this.indexOf(BA);
		if (i < 0)
			return;

		if ((this.StctArray.length - this.StctLength) > 5) {
			CharArray[] SA = new CharArray[this.StctLength - 5];
			System.arraycopy(this.StctArray, 0, SA, 0, this.StctLength);
			this.StctArray = SA;
		}

		System.arraycopy(this.StctArray, i + 1, this.StctArray, i,
				(this.StctLength - i));

		this.StctArray[this.StctLength - 1] = null;
		this.StctLength--;
	}

	/** Clears all the structure * */
	public void clear() {
		if (this.StctArray.length > 5)
			this.StctArray = new CharArray[5];
		for (int i = 0; i < this.StctArray.length; i++)
			this.StctArray[i] = null;
		this.StctLength = 0;
	}

	// Array +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

	/**{@inheritDoc}*/ @Override
	public int length() {
		if (this.StctArray == null) return 0;

		int l = 0;
		for (int i = 0; i < this.StctLength; i++)
			l += (this.StctArray[i] == null) ? 0 : this.StctArray[i].length();
		return l;
	}

	/**{@inheritDoc}*/ @Override
	public char get(int pPos) {
		if (this.StctArray == null) return 0;

		if (pPos >= this.length()) return 0;
		if (pPos < 0)              return 0;

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
		return this.StctArray[p].getData(pPos - l);
	}

	/**{@inheritDoc}*/ @Override
	public char set(int pPos, char pChar) {
		if (this.StctArray == null) throw new NullPointerException();

		if (pPos >= this.length()) throw new ArrayIndexOutOfBoundsException(pPos);
		if (pPos < 0)              throw new ArrayIndexOutOfBoundsException(pPos);

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
		this.StctArray[p].set(pPos - l, pChar);
		return pChar;
	}

	/**{@inheritDoc}*/ @Override
	public char[] get(int pPos, int pLength) {
		if (this.StctArray == null) return new char[0];

		int length = this.length();

		if (pPos >= length) return new char[0];
		if (pLength < 0)    return new char[0];

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
		char[] cs = new char[pLength];
		int EachPos = pPos;
		int ArrayPos = 0;
		for (int i = p; i < this.StctLength; i++) {
			if (this.StctArray[i] == null)
				continue;
			int len = this.StctArray[i].length();

			// For the fist time, the position of this line may not 0
			if (i == p)
				len -= EachPos;

			// trim the length for the last line
			if ((pLength - ArrayPos) < len)
				len = (pLength - ArrayPos);

			System.arraycopy(this.StctArray[i].get(EachPos, len), 0, cs,
					ArrayPos, len);

			// Add the position
			ArrayPos += len;

			// For the fist time, set this to be 0 (the rest of the line will
			// start at 0)
			if (i == p)
				EachPos = 0;
		}

		return cs;
	}

	/**{@inheritDoc}*/ @Override
	public char[] set(int pPos, char[] pChars) {
		if (this.StctArray == null) throw new NullPointerException();

		int length = this.length();
		int pLength = pChars.length;

		if (pPos >= length) throw new ArrayIndexOutOfBoundsException(pPos);
		if (pLength < 0)    throw new ArrayIndexOutOfBoundsException(pPos);

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
		int EachPos = pPos;
		int ArrayPos = 0;
		char[] cs = new char[0];
		for (int i = p; i < this.StctLength; i++) {
			if (this.StctArray[i] == null)
				continue;
			int len = this.StctArray[i].length();

			// For the fist time, the position of this line may not 0
			if (i == p)
				len -= EachPos;

			// trim the length for the last line
			if ((pLength - ArrayPos) < len)
				len = (pLength - ArrayPos);

			if (cs.length != len) cs = new char[len];
			System.arraycopy(pChars, ArrayPos, cs, 0, len);
			this.StctArray[i].set(EachPos, cs);

			// Add the position
			ArrayPos += len;

			// For the fist time, set this to be 0 (the rest of the line will
			// start at 0)
			if (i == p)
				EachPos = 0;
		}
		return pChars;
	}

	/**{@inheritDoc}*/ @Override
	public char[] getAll() {
		return this.get(0, this.length());
	}

	/**{@inheritDoc}*/ @Override
	public String toHex() {
		return UString.chars2hex(this.getAll());
	}

	/** Returns the absolute position of the position pPos */
	public int getAbsolutePosition(int pPos) {
		return pPos;
	}
}