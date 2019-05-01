package net.nawaman.util.chararray;

import net.nawaman.util.*;

/**
 * <CODE>VirtualCharArray</CODE> is a <CODE>CharArray</CODE> that is not real or simulated.
 */
public class VirtualCharArray extends CharArray {

	private static final long serialVersionUID = -2627174641239303671L;
	
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

	/**{@inheritDoc}*/ @Override
	public String getString() {
		return new String(this.getAll());
	}

	// Constructors ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	/** Constructs a VirtualCharArray */
	public VirtualCharArray(char[] cs) {
		this(new FixedLengthCharArray(cs), 0, (cs != null) ? cs.length : 0);
	}
	/** Constructs a VirtualCharArray */
	public VirtualCharArray(FixedLengthCharArray A) {
		this(A, 0, (A != null) ? A.length() : 0);
	}
	/** Constructs a VirtualCharArray */
	public VirtualCharArray(CharArray CA, int P, int L) {
		// Assign the internal data
		this.Parent = CA;
		this.Pos    = P;
		this.Length = L;

		// Ensure non-negative position
		if (this.Pos < 0)
			this.Pos = 0;

		// Ensure not too long length
		if ((this.Pos + this.Length) > CA.length())
			this.Length = CA.length() - this.Pos;
	}
	
	// Characteristic +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	
	/**{@inheritDoc}*/ @Override
	public boolean isVirtual() {
		return  true;
	}
	/**{@inheritDoc}*/ @Override
	public VirtualCharArray asVirtual() {
		return  this;
	}

	// Internal data +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

	private CharArray Parent = null;
	private int       Pos    = 0;
	private int       Length = 0;

	// External behavior +++++++++++++++++++++++++++++++++++++++++++++++++++++++
	/**
     * Generates and returns a sub VirualCharArray that mapped from pPos to the
     * end of this CharArray
     */
	public CharArray generateSub(int pPos) {
		return new VirtualCharArray(this, pPos, this.Length);
	}

	/**
     * Generates and returns a sub VirualCharArray that mapped from pPos to pPos +
     * pLength
     */
	public CharArray generateSub(int pPos, int pLength) {
		return new VirtualCharArray(this, pPos, pLength);
	}

	/**{@inheritDoc}*/ @Override
	public int length() {
		return this.Length;
	}

	/**{@inheritDoc}*/ @Override
	public char get(int pPos) {
		if ((pPos < 0) || (pPos >= this.Length)) return '0';
		if (this.Parent == null)                 return '0';
		return this.Parent.getData(this.Pos + pPos);
	}
	
	/**{@inheritDoc}*/ @Override
	public char set(int pPos, char pChar) {
		if ((pPos < 0) || (pPos >= this.Length)) throw new ArrayIndexOutOfBoundsException(pPos);
		if (this.Parent == null)                 throw new NullPointerException("Null parent for virtual char array.");
		this.Parent.set(this.Pos + pPos, pChar);
		return pChar;
	}

	/**{@inheritDoc}*/ @Override
	public char[] get(int pPos, int pLength) {
		if (this.Parent == null) return new char[0];
		if (pPos >= this.Length) return new char[0];
		if (pLength < 0)         return new char[0];

		// Adjust
		if (pPos < 0) {
			pLength += pPos;
			pPos = 0;
		}
		if ((pPos + pLength) >= this.Length) {
			pLength = this.Length - pPos;
		}

		return this.Parent.get(this.Pos + pPos, pLength);
	}

	/**{@inheritDoc}*/ @Override
	public char[] set(int pPos, char[] pChars) {
		if (this.Parent == null) throw new NullPointerException("Null parent for virtual char array.");
		if (pPos >= this.Length) throw new ArrayIndexOutOfBoundsException(pPos);

		int pLength = pChars.length;
		if (pLength == 0) return pChars;

		// Adjust
		if (pPos < 0) {
			pLength += pPos;
			pPos = 0;
		}
		if ((pPos + pLength) >= this.Length) {
			pLength = this.Length - pPos;
		}

		this.Parent.set(this.Pos + pPos, pChars);
		return pChars;
	}
	
	/**{@inheritDoc}*/ @Override
	public char[] getAll() {
		return this.get(0, this.Length);
	}

	/**{@inheritDoc}*/ @Override
	public String toHex() {
		return UString.chars2hex(this.getAll());
	}

}