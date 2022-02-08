package net.nawaman.text;

import net.nawaman.util.*;

/**
 * <CODE>VirtualText</CODE> is a <CODE>Text</CODE> that is not real or simulated.
 */
public class VirtualText extends Text {

	private static final long serialVersionUID = -8300172327823863899L;
	
	// Constructors ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

	/** Constructs a VirtualText */
	public VirtualText(CharSequence pCS) {
		this(null, (pCS instanceof Text) ? ((Text)pCS).getAll() : pCS.toString().toCharArray());
	}
	/** Constructs a VirtualText */
	public VirtualText(char[] cs) {
		this(null, cs);
	}
	/** Constructs a VirtualText */
	public VirtualText(FixedLengthText A) {
		this(null, A);
	}
	/** Constructs a VirtualText */
	public VirtualText(Text T, int pPos, int pLength) {
		this(null, T, pPos, pLength);
	}
	
	/** Constructs a VirtualText */
	public VirtualText(Object pExtraInfo, CharSequence pCS) {
		this(pExtraInfo, (pCS instanceof Text) ? ((Text)pCS).getAll() : pCS.toString().toCharArray());
	}
	/** Constructs a VirtualText */
	public VirtualText(Object pExtraInfo, char[] cs) {
		this(pExtraInfo, new FixedLengthText(cs), 0, (cs != null) ? cs.length : 0);
	}
	/** Constructs a VirtualText */
	public VirtualText(Object pExtraInfo, FixedLengthText A) {
		this(pExtraInfo, A, 0, (A != null) ? A.length() : 0);
	}
	/** Constructs a VirtualText */
	public VirtualText(Object pExtraInfo, Text T, int pPos, int pLength) {
		super(pExtraInfo);
		// Assign the internal data
		this.Parent = T;
		this.Pos    = pPos;
		this.Length = pLength;

		// Ensure non-negative position
		if (this.Pos < 0)
			this.Pos = 0;

		// Ensure not too long length
		if ((this.Pos + this.Length) > T.length())
			this.Length = T.length() - this.Pos;
	}
	
	// Characteristic +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	
	/**{@inheritDoc}*/ @Override
	public boolean isVirtual() {
		return  true;
	}
	/**{@inheritDoc}*/ @Override
	public VirtualText asVirtual() {
		return  this;
	}

	// Internal data +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

	private Text Parent = null;
	private int       Pos    = 0;
	private int       Length = 0;

	// External behavior +++++++++++++++++++++++++++++++++++++++++++++++++++++++
	/**
     * Generates and returns a sub VirualText that mapped from pPos to the
     * end of this Text
     */
	public Text generateSub(int pPos) {
		return new VirtualText(this, pPos, this.Length);
	}

	/**
     * Generates and returns a sub VirualText that mapped from pPos to pPos +
     * pLength
     */
	public Text generateSub(int pPos, int pLength) {
		return new VirtualText(this, pPos, pLength);
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
	
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	
	/**{@inheritDoc}*/ @Override
	public String getString() {
		return new String(this.getAll());
	}

}