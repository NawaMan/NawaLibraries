package net.nawaman.util.bytearray;

import java.io.*;

/** A writer for writing to ByteArray */
public class ByteArrayWriter {

	boolean IsDeposed = false;

	/** Checks if the ByteArrayWriter is deposed */
	final public boolean isDeposed() {
		return this.IsDeposed;
	}

	/** Deposes the BytArrayWriter */
	public void depose() {
		this.SBA = null;
		this.IsDeposed = true;
	}

	/** Internal ByteArray */
	protected StructureByteArray SBA = null;

	/** Constructs a ByteArrayWriter */
	public ByteArrayWriter() {
		this.SBA = new StructureByteArray();
	}

	/** Writes ByeArray into this writer */
	public void write(ByteArray BA) {
		if (this.isDeposed())
			return;
		this.SBA.append(BA);
	}

	/** Writes byte[] into this writer */
	public void write(byte[] Bs) {
		if (this.isDeposed())
			return;
		this.SBA.append(Bs);
	}

	/** Writes ByteArrayWriter into this writer */
	public void write(ByteArrayWriter BAW) {
		if (this.isDeposed())
			return;
		this.SBA.append(BAW.SBA);
	}

	/** Writes a boolean into this writer */
	public void writeBoolean(boolean B) {
		if (this.isDeposed())
			return;
		this.SBA.append(UByte.boolean2bc(B));
	}

	/** Writes an array of booleans into this writer */
	public void writeBooleans(boolean[] Bs) {
		if (this.isDeposed())
			return;
		this.SBA.append(UByte.booleanArray2bc(Bs));
	}

	/** Writes a char into this writer */
	public void writeChar(char C) {
		if (this.isDeposed())
			return;
		this.SBA.append(UByte.char2bc(C));
	}

	/** Writes an extensible number (up to 9-byte signed number) into this writer */
	// Size will be adjusted by the value
	public void writeNumber(long N) {
		if (this.isDeposed())
			return;
		byte[] Bs = UByte.en2bc(N);
		this.SBA.append(Bs);
	}

	/** Writes a byte into this writer */
	public void writeByte(byte B) {
		if (this.isDeposed())
			return;
		byte[] b = new byte[1];
		b[0] = B;
		this.SBA.append(b);
	}

	/** Writes a two-byte number into this writer */
	public void writeShort(short S) {
		if (this.isDeposed())
			return;
		byte[] Bs = UByte.short2bc(S);
		this.SBA.append(Bs);
	}

	/** Writes a four-byte number into this writer */
	public void writeInteger(int I) {
		if (this.isDeposed())
			return;
		byte[] Bs = UByte.int2bc(I);
		this.SBA.append(Bs);
	}

	/** Writes a eight-byte number into this writer */
	public void writeLong(long L) {
		if (this.isDeposed())
			return;
		byte[] Bs = UByte.long2bc(L);
		this.SBA.append(Bs);
	}

	/** Writes a four-byte real number into this writer */
	public void writeFloat(float F) {
		if (this.isDeposed())
			return;
		byte[] Bs = UByte.float2bc(F);
		this.SBA.append(Bs);
	}

	/** Writes a eight-byte real number into this writer */
	public void writeDouble(double D) {
		if (this.isDeposed())
			return;
		byte[] Bs = UByte.double2bc(D);
		this.SBA.append(Bs);
	}

	/**
     * Writes a string into this writer<br>
     * The string start with up to-two byte number indicating the length of the
     * string, then the array of chars are read.
     */
	public void writeString(String S) {
		if (this.isDeposed())
			return;
		if (S == null) {
			this.writeNumber(-1);
			return;
		}
		this.writeNumber(S.length());
		for (int i = 0; i < S.length(); i++)
			this.writeChar(S.charAt(i));
	}

	/** Clears all bytes in this writer */
	public void clear() {
		if (this.isDeposed())
			return;
		this.SBA.clear();
	}

	/** Returns ByteArray representation of this Writer */
	public ByteArray toByteArray() {
		if (this.isDeposed())
			return null;
		return this.SBA;
	}

	/** Returns byte[] representation of this Writer */
	public byte[] toBytes() {
		if (this.isDeposed())
			return null;
		return this.SBA.getAll();
	}

	/** Notifying just before the saving */
	protected String onBeforeSave(String pFName) {
		return null;
	}

	/** Saves this writer to the file named pName */
	public String saveToFile(String pFName) {
		if (this.isDeposed())
			return "The writer has been deposed.";
		String S = this.onBeforeSave(pFName);
		if (S != null)
			return S;
		try {
			UByte.saveBytesToFile(pFName, this.SBA);
			return null;
		} catch (Exception E) {
			return E.toString();
		}
	}

	/** Saves this writer to the file pFile */
	public String saveToFile(File pFile) {
		if (this.isDeposed())
			return "The writer has been deposed.";
		String S = this.onBeforeSave(pFile.getAbsoluteFile().getName());
		if (S != null)
			return S;
		try {
			UByte.saveBytesToFile(pFile, this.SBA);
			return null;
		} catch (Exception E) {
			return E.toString();
		}
	}

	/** Returns the Hexadecimal representation of this Writer */
	public String toHex() {
		if (this.isDeposed())
			return "The writer has been deposed.";
		return UByte.bc2hex(this.SBA);
	}
}