package net.nawaman.util.bytearray;

import java.io.*;

/** A reader to read from ByteArray */
public class ByteArrayReader {

	boolean IsDeposed = false;

	/** Checks if the ByteArrayWriter is deposed */
	final public boolean isDeposed() {
		return this.IsDeposed;
	}

	/** Deposes the BytArrayWriter */
	public void depose() {
		this.ABA = null;
		this.IsDeposed = true;
	}

	/** ByteArray */
	protected AppendableByteArray ABA = null;

	/** Array of Error Messages */
	protected String[] Errors = null;

	/** Number of Errors */
	protected int ECount = 0;

	/** Appends an error message into the error array */
	protected void addError(String pErr) {
		if (this.IsDeposed)
			return;

		if (pErr == null)
			return;
		if (this.Errors == null) {
			this.Errors = new String[5];
			for (int i = 0; i < this.Errors.length; i++)
				this.Errors[i] = null;
		}
		if (this.ECount >= this.Errors.length) {
			String[] Errs = new String[this.Errors.length + 5];
			System.arraycopy(this.Errors, 0, Errs, 0, this.Errors.length);
			for (int i = this.Errors.length; i < Errs.length; i++)
				Errs[i] = null;
			this.Errors = Errs;
		}
		this.Errors[this.ECount] = pErr;
		this.ECount++;
	}

	/** Clears the error message array */
	public void clearError() {
		if (this.IsDeposed)
			return;
		this.Errors = null;
		this.ECount = 0;
	}

	/** Returns the error messages as on String */
	public String getErrors() {
		if (this.IsDeposed)
			return null;

		if (this.Errors == null)
			return null;
		StringBuffer SB = new StringBuffer();
		for (int i = 0; i < this.ECount; i++)
			SB.append(this.Errors[i]);
		return SB.toString();
	}

	/** Cursor position */
	long Pos = 0;

	/** Constructs a ByteArrayReader */
	protected ByteArrayReader() {
	}

	/** Constructs a ByteArrayReader by loading from a file pFullName */
	protected ByteArrayReader(String pFullName) {
		this(new File(pFullName));
	}

	/** Constructs a ByteArrayReader by loading from a File */
	protected ByteArrayReader(File pFile) {
		if ((pFile == null) || !pFile.isFile()) {
			this.addError("File not found or not a file.");
			return;
		}
		this.ABA = null;
		try {
			this.ABA = (AppendableByteArray) (UByte.loadBytesFromFile(pFile));
			this.clearError();
		} catch (Exception E) {
			this.addError(E.toString());
		}
	}

	/** Constructs a ByteArrayReader by loading from a ByteArray */
	protected ByteArrayReader(ByteArray BA) {
		this.ABA = new AppendableByteArray(BA.getAll());
	}

	/** Reads ByteArray from the file named pFullName */
	static public ByteArrayReader loadByteArray(String pFullName) {
		if (pFullName == null)
			return null;
		File F = new File(pFullName);
		if (!F.exists())
			return null;
		return ByteArrayReader.loadByteArray(F);
	}

	/** Reads ByteArray from the file pFile */
	static public ByteArrayReader loadByteArray(File pFile) {
		if (pFile == null)
			return null;
		if (!pFile.exists())
			return null;
		return new ByteArrayReader(pFile);
	}

	/** Reads ByteArray from a ByteArray */
	static public ByteArrayReader loadByteArray(ByteArray BA) {
		if (BA == null)
			return null;
		return new ByteArrayReader(BA);
	}

	/** Reads and returns ByteArray of the size Size from the current position */
	public ByteArray readByteArray(int Size) {
		if (this.IsDeposed)
			return null;

		if ((this.Pos + Size) >= this.ABA.length()) {
			this.addError("Invalid Length (code: readByteArray)!!!");
			return null;
		}
		ByteArray BA = new VirtualByteArray(this.ABA, (int) this.Pos, Size);
		this.Pos += Size;
		return BA;
	}

	/** Reads and returns byte[] of the size Size from the current position */
	public byte[] readBytes(int Size) {
		if (this.IsDeposed)
			return null;

		if ((this.Pos + Size) > this.ABA.length()) {
			this.addError("Invalid Length (code: readByteArray)!!!");
			return null;
		}
		ByteArray BA = new VirtualByteArray(this.ABA, (int) this.Pos, Size);
		this.Pos += Size;
		return BA.getAll();
	}

	/** Creates a ByteArrayReader of the size Size from the current position */
	public ByteArrayReader readByteArrayReader(int Size) {
		if (this.IsDeposed)
			return null;

		if ((this.Pos + Size) >= this.ABA.length()) {
			this.addError("Invalid Length (code: readByteArray)!!!");
			return null;
		}
		ByteArray BA = new VirtualByteArray(this.ABA, (int) this.Pos, Size);
		this.Pos += Size;
		ByteArrayReader BAR = new ByteArrayReader();
		BAR.ABA = new AppendableByteArray(BA.getAll());
		return BAR;
	}

	/** Reads and returns a boolean value from the current position */
	public boolean readBoolean() {
		if (this.IsDeposed)
			return false;

		ByteArray Current = new VirtualByteArray(this.ABA, (int) this.Pos, 1);
		if (Current.length() < 1) {
			this.addError("Invalid Length (code: readBoolean)!!!");
			return false;
		}
		boolean B = UByte.bc2boolean(Current);
		this.Pos++;
		return B;
	}

	/** Reads and returns an array of booleans from the current position. */
	public boolean[] readBooleans() {
		if (this.IsDeposed)
			return null;

		ByteArray Current = new VirtualByteArray(this.ABA, (int) this.Pos);
		boolean[] Bs = UByte.bc2booleanArray(Current);
		int length = (int) UByte.bc2en(this.ABA);
		int count = UByte.enCount(this.ABA) + (length / 8)
				+ (((length % 8) > 0) ? 1 : 0);
		this.Pos += count;
		if ((Bs == null) || (count > Current.length())) {
			this.addError("Invalid Length (code: readBooleans)!!!");
			return new boolean[0];
		}
		return Bs;
	}

	/**
     * Reads and returns a character value from the current position. There are
     * upto-two-byte characters. 1. 0000-007F -> 1 byte -> 0xxx xxx 2. 0080-7FFF ->
     * 2 bytes -> 1xxx xxx xxxx xxxx
     */
	public char readChar() {
		if (this.IsDeposed)
			return '\0';

		ByteArray Current = new VirtualByteArray(this.ABA, (int) this.Pos, 2);
		char C = UByte.bc2char(Current);
		int L = ((C > 0x7F) ? 2 : 1);
		if (L > Current.length()) {
			this.addError("Invalid Length (code: readChar)!!!");
			return '\0';
		}
		this.Pos += L;
		return C;
	}

	/**
     * Reads and returns a number value from the current position. There are
     * upto-night-byte numbers.
     * 
     * <pre>
     *    1 byte  (                -3FH to                 3FH) = s0dddddd
     *    2 bytes (              -1FFFH to               1FFFH) = s10ddddd dddddddd
     *    4 bytes (           -FFFFFFFH to            FFFFFFFH) = s110dddd dddddddd dddddddd dddddddd
     *    5 bytes (         -7FFFFFFFFH to          7FFFFFFFFH) = s1110ddd dddddddd dddddddd dddddddd dddddddd
     *    9 bytes (-7FFFFFFFFFFFFFFFFFH to 7FFFFFFFFFFFFFFFFFH) = s1111000 dddddddd dddddddd dddddddd dddddddd dddddddd dddddddd dddddddd dddddddd
     * </pre>
     */
	public long readNumber() {
		if (this.IsDeposed)
			return 0;

		ByteArray Current = new VirtualByteArray(this.ABA, (int) this.Pos);
		if (UByte.enCount(Current) > Current.length()) {
			this.addError("Invalid Length (code: readNumber 1)!!!");
			return 0;
		}

		long L = UByte.bc2en(Current);
		this.Pos += UByte.enCount(Current);
		return L;
	}

	/** Reads and returns one-byte number value from the current position. */
	public byte readByte() {
		if (this.IsDeposed)
			return 0;

		ByteArray Current = new VirtualByteArray(this.ABA, (int) this.Pos);
		if (Current.length() < 1) {
			this.addError("Invalid Length (code: readByte)!!!");
			return (byte) 0;
		}
		this.Pos++;
		return Current.get(0);
	}

	/** Reads and returns two-byte number value from the current position. */
	public short readShort() {
		if (this.IsDeposed)
			return 0;

		ByteArray Current = new VirtualByteArray(this.ABA, (int) this.Pos);
		if (Current.length() < 2) {
			this.addError("Invalid Length (code: readShort)!!!");
			return '\0';
		}
		this.Pos += 2;
		return UByte.bc2short(Current);
	}

	/** Reads and returns four-byte number value from the current position. */
	public int readInteger() {
		if (this.IsDeposed)
			return 0;

		ByteArray Current = new VirtualByteArray(this.ABA, (int) this.Pos);
		if (Current.length() < 4) {
			this.addError("Invalid Length (code: readInteger)!!!");
			return 0;
		}
		this.Pos += 4;
		return UByte.bc2int(Current);
	}

	/** Reads and returns eight-byte number value from the current position. */
	public long readLong() {
		if (this.IsDeposed)
			return 0;

		ByteArray Current = new VirtualByteArray(this.ABA, (int) this.Pos);
		if (Current.length() < 8) {
			this.addError("Invalid Length (code: readLong)!!!");
			return 0;
		}
		this.Pos += 8;
		return UByte.bc2long(Current);
	}

	/** Reads and returns four-byte real number value from the current position. */
	public float readFloat() {
		if (this.IsDeposed)
			return 0;

		ByteArray Current = new VirtualByteArray(this.ABA, (int) this.Pos);
		if (Current.length() < 4) {
			this.addError("Invalid Length (code: readFloat)!!!");
			return 0;
		}
		this.Pos += 4;
		return UByte.bc2float(Current);
	}

	/** Reads and returns eight-byte real number value from the current position. */
	public double readDouble() {
		if (this.IsDeposed)
			return 0;

		ByteArray Current = new VirtualByteArray(this.ABA, (int) this.Pos);
		if (Current.length() < 8) {
			this.addError("Invalid Length (code: readDouble)!!!");
			return 0;
		}
		this.Pos += 8;
		return UByte.bc2double(Current);
	}

	/**
     * Reads and returns string value from the current position.<br>
     * The string start with upto-two byte number indicating the length of the
     * string, then the array of chars are read.
     */
	public String readString() {
		if (this.IsDeposed)
			return "";

		//VirtualByteArray Current = new VirtualByteArray(this.ABA, (int) this.Pos);
		int length = (int) this.readNumber();
		if (length == -1)
			return null;

		StringBuffer SB = new StringBuffer();
		for (int i = 0; i < length; i++) {
			char C = this.readChar();
			SB.append(C);
			if (this.getErrors() != null)
				return null;
		}
		return SB.toString();
	}

	/** Moves the current position to the beginning */
	public void toBeginning() {
		if (this.IsDeposed)
			return;

		this.Pos = 0;
	}

	/** Checks if the current position is at the end of the ByteArray */
	public boolean isEnd() {
		if (this.IsDeposed)
			return true;

		if (this.ABA == null)
			return true;
		return (this.Pos >= this.ABA.length());
	}

	/** Returns the ByteArray of this Reader */
	public ByteArray toByteArray() {
		if (this.IsDeposed)
			return null;

		return this.ABA;
	}

	/** Returns the byte[] of this Reader */
	public byte[] toBytes() {
		if (this.IsDeposed)
			return null;

		return this.ABA.getAll();
	}

	/** Returns the Hexadecimal representation of this Reader */
	public String toHex() {
		if (this.IsDeposed)
			return null;

		if (this.ABA == null)
			return null;
		return UByte.bc2hex(this.ABA);
	}
}