package net.nawaman.util.bytearray;

import net.nawaman.util.*;

/** This interface represents classes that simulate a byte array. */
public interface ByteArray extends DataArray<Byte>{

	// Characteristic +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	/** Checks if this ByteArray contains an actual bytes*/
	public boolean isActual();

	/** Checks if this ByteArray is an AppendableByteArray */
	public boolean isAppendable();

	/** Returns this ByteArray as an AppendableByteArray */
	public AppendableByteArray asAppendable();

	/** Checks if this ByteArray is a VirtualByteArray */
	public boolean isVirtual();

	/** Returns this ByteArray as a VirtualByteArray */
	public VirtualByteArray asVirtual();

	/** Checks if this ByteArray is a StructureByteArray */
	public boolean isStructure();

	/** Checks if this ByteArray is a StructureByteArray */
	public StructureByteArray asStructure();

	// External behavior +++++++++++++++++++++++++++++++++++++++++++++++++++++++

	/** Returns the length of this array */
	public int length();

	/** Returns the byte at the position pPos */
	public byte get(int pPos);

	/** Sets the byte value at the position pPos */
	public byte set(int pPos, byte pByte);

	/** Returns byte[] from pPos to pPos + pLength */
	public byte[] get(int pPos, int pLength);

	/** Set pBytes from pPos */
	public byte[] set(int pPos, byte[] pBytes);

	/** Returns all bytes as byte[] */
	public byte[] getAll();

	/** Returns the hexadecimal representation of this bytes in this array */
	public String toHex();
	
}
