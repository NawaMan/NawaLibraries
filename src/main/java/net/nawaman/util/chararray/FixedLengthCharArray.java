package net.nawaman.util.chararray;

import net.nawaman.util.*;

/** Fixed-Length CharArray */
public class FixedLengthCharArray extends CharArray {
	
	private static final long serialVersionUID = -4716769959619867099L;
	
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

	/**{@inheritDoc}*/ @Override
	public String getString() {
		return new String(this.Data);
	}

	// Constructor +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	/** Constructs a FixLengthCharArray of the length pLength */
	public FixedLengthCharArray(int pLength) {
		if (pLength < 0)
			pLength = 0;
		this.Data = new char[pLength];
	}

	/** Constructs a FixLengthCharArray of from char[] */
	public FixedLengthCharArray(char[] pChars) {
		this.Data = pChars;
		this.DLength = (this.Data == null) ? 0 : this.Data.length;
	}

	/** Constructs a FixLengthCharArray of from char[] */
	public FixedLengthCharArray(CharSequence pCS) { this(pCS.toString().toCharArray()); }

	// Characteristic +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

	/**{@inheritDoc}*/ @Override
	public boolean isActual() {
		return true;
	}

	// Internal Data +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

	char[] Data = null;

	int DLength = 0;

	// External behavior +++++++++++++++++++++++++++++++++++++++++++++++++++++++

	/**{@inheritDoc}*/ @Override
	public int length() {
		return this.DLength;
	}

	/**{@inheritDoc}*/ @Override
	public char get(int pPos) {
		if ((pPos < 0) || (pPos >= this.DLength)) return 0;
		return this.Data[pPos];
	}

	/**{@inheritDoc}*/ @Override
	public char set(int pPos, char pChar) {
		if ((pPos < 0) || (pPos >= this.DLength)) throw new ArrayIndexOutOfBoundsException(pPos);
		this.Data[pPos] = pChar;
		return pChar;
	}

	/**{@inheritDoc}*/ @Override
	public char[] get(int pPos, int pLength) {
		if (this.Data == null) return new char[0];

		if (pPos >= this.DLength) return new char[0];
		if (pLength < 0)          return new char[0];

		// Adjust
		if (pPos < 0) {
			pLength += pPos;
			pPos = 0;
		}
		if ((pPos + pLength) >= this.DLength) {
			pLength = this.DLength - pPos;
		}

		// Duplicate
		char[] Temp = new char[pLength];
		System.arraycopy(this.Data, pPos, Temp, 0, pLength);
		return Temp;
	}

	/**{@inheritDoc}*/ @Override
	public char[] set(int pPos, char[] pChars) {
		if (this.Data == null)    throw new NullPointerException();
		if (pPos >= this.DLength) throw new ArrayIndexOutOfBoundsException(pPos);

		int pLength = pChars.length;
		if (pLength == 0) return pChars;

		// Adjust
		if (pPos < 0) {
			pLength += pPos;
			pPos = 0;
		}
		if ((pPos + pLength) >= this.DLength) {
			pLength = this.DLength - pPos;
		}

		System.arraycopy(pChars, 0, this.Data, pPos, pLength);
		return pChars;
	}

	/**{@inheritDoc}*/ @Override
	public char[] getAll() {
		if (this.Data == null) return new char[0];
		// Duplicate
		char[] Temp = new char[this.DLength];
		System.arraycopy(this.Data, 0, Temp, 0, this.DLength);
		return Temp;
	}

	/**{@inheritDoc}*/ @Override
	public String toHex() {
		return UString.chars2hex(this.Data);
	}

	/** Returns the absolute position of the position pPos */
	public int getAbsolutePosition(int pPos) {
		return pPos;
	}
	
}