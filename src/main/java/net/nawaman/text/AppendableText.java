package net.nawaman.text;

import net.nawaman.util.*;

/**
 * <CODE>AppendableText</CODE> is a <CODE>Text</CODE> that can
 * be appended.
 */
final public class AppendableText extends Text implements Appendable {

	private static final long serialVersionUID = -7246036972286068284L;

	// Constructor +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	/** Constructs an AppendableText * */
	public AppendableText() {
	}
	/** Constructs an AppendableText with the initial length pLength * */
	public AppendableText(int pLength) {
		this(null, pLength);
	}
	/** Constructs an AppendableText from the initial pChars * */
	public AppendableText(char[] pChars) {
		this(null, pChars);
	}
	/** Constructs an AppendableText from the initial pChars * */
	public AppendableText(CharSequence pCS) {
		this(null, (pCS instanceof Text) ? ((Text)pCS).getAll() : pCS.toString().toCharArray());
	}
	
	/** Constructs an AppendableText * */
	public AppendableText(Object pExtraInfo) {
		super(pExtraInfo);
	}
	/** Constructs an AppendableText with the initial length pLength * */
	public AppendableText(Object pExtraInfo, int pLength) {
		super(pExtraInfo);
		if (pLength < 0)
			pLength = 0;
		this.Data = new char[pLength];
	}
	/** Constructs an AppendableText from the initial pChars * */
	public AppendableText(Object pExtraInfo, char[] pChars) {
		super(pExtraInfo);
		this.Data = pChars;
		this.DLength = (this.Data == null) ? 0 : this.Data.length;
	}
	/** Constructs an AppendableText from the initial pChars * */
	public AppendableText(Object pExtraInfo, CharSequence pCS) {
		this(pExtraInfo, (pCS instanceof Text) ? ((Text)pCS).getAll() : pCS.toString().toCharArray());
	}

	// Characteristic +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

	/**{@inheritDoc}*/ @Override
	public boolean isActual() {
		return true;
	}
	
	/**{@inheritDoc}*/ @Override
	public boolean isAppendable() {
		return true;
	}
	/**{@inheritDoc}*/ @Override
	public AppendableText asAppendable() {
		return this;
	}

	// Internal Data +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

	private char[] Data    = null;
	private int    DLength =    0;

	// External behavior +++++++++++++++++++++++++++++++++++++++++++++++++++++++

	/**{@inheritDoc}*/ @Override
	public int length() {
		return this.DLength;
	}

	/**{@inheritDoc}*/ @Override
	public char get(int pPos) {
		return ((pPos < 0) || (pPos >= this.DLength)) ? 0 : this.Data[pPos];
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

		// Dublicate
		char[] Temp = new char[pLength];
		System.arraycopy(this.Data, pPos, Temp, 0, pLength);
		return Temp;
	}

	/**{@inheritDoc}*/ @Override
	public char[] set(int pPos, char[] pChars) {
		if (this.Data ==         null) throw new NullPointerException();
		if (pPos      >= this.DLength) throw new ArrayIndexOutOfBoundsException(pPos);

		int pLength = pChars.length;
		if (pLength == 0) return null;

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

	/** Sets the capacity of this appendable array ()extend only */
	public void setCapacity(int capacity) {
		if (capacity < this.DLength)
			return;

		char[] Temp = new char[capacity];
		System.arraycopy(this.Data, 0, Temp, 0, this.DLength);
		this.Data = Temp;
	}
	


    /** Appends the specified character sequence to this <tt>Appendable</tt>. */
    public Appendable append(CharSequence pCS) {
    	if(pCS instanceof Text) this.append(((Text)pCS).getAll());
    	else                    this.append(pCS.toString().toCharArray());
    	return this;
    }

    /** Appends a subsequence of the specified character sequence to this <tt>Appendable</tt>. */
    public Appendable append(CharSequence pCS, int pStart, int pEnd) {
    	if(pCS instanceof Text) this.append(((Text)pCS).get(pStart, pEnd - pStart));
    	else                    this.append(pCS.subSequence(pStart, pEnd).toString().toCharArray());
    	return this;
    }

	/** Appends pChar to this Text */ @Override
	public Appendable append(char pChar) {
		// Increase capacity
		if (this.DLength >= this.Data.length) {
			char[] Temp = new char[this.DLength + 10];
			System.arraycopy(this.Data, 0, Temp, 0, this.DLength);
			this.Data = Temp;
		}

		// Assign data
		this.Data[this.DLength] = pChar;
		this.DLength++;
		
		return this;
	}

	/** Appends pChars to this Text */
	public Appendable append(char[] pChars) {
		if (pChars == null) return this;
		if (this.Data == null)
			this.Data = new char[5];

		// Increase capacity
		if ((this.DLength + pChars.length) >= this.Data.length) {
			char[] Temp = new char[this.DLength + pChars.length];
			System.arraycopy(this.Data, 0, Temp, 0, this.DLength);
			this.Data = Temp;
		}

		// Assign data
		System.arraycopy(pChars, 0, this.Data, this.DLength, pChars.length);
		this.DLength += pChars.length;
		return this;
	}

	/**{@inheritDoc}*/ @Override
	public String toHex() {
		return UString.chars2hex(this.Data);
	}

	/** Returns the absolute position of the position pPos */
	public int getAbsolutePosition(int pPos) {
		return pPos;
	}

	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	/**{@inheritDoc}*/ @Override
	public String getString() {
		return new String(this.Data);
	}
}