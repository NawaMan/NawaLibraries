package net.nawaman.util.bytearray;

import java.io.*;

/**
 * Byte data utility
 *
 * @author	Nawapunth Manusitthipol
 **/
public class UByte {

	UByte() {}

	/**
	 * Returns a valid value of byte (positive value with in the byte range, i.e. 0..255)
	 * 
	 * @param	pByte	the input byte
	 * @return	the valid value of the input
	 **/
	static public int getValidByte(int pByte) {
		if (pByte < 0)
			return (255 + pByte + 1);
		return pByte;
	}

	// Display +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

	/**
	 * A constant used for the displaying in hex
	 **/
	static final protected String HEX = "0123456789ABCDEF";

	/**
	 * Converts integer to a bit string (32-bit sequence of "0" and "1" )
	 * 
	 * @param	pInt	the input integer
	 * @return	the 32-bit binary sequence string if the pInt value
	 **/
	static public String int2bit(int pInt) {
		String Str = "";
		int bit = 0x1;
		for (int i = 0; i < 32; i++) {
			Str = (((pInt & bit) != 0) ? "1" : "0") + Str;
			bit = bit << 1;
		}
		return Str;
	}

	/**
	 * Converts byte to a bit string (8-bit sequence of "0" and "1" )
	 * 
	 * @param	pByte	the input byte
	 * @return	the 8-bit binary sequence string if the pByte value
	 **/
	static public String byte2bit(byte pByte) {
		String Str = "";
		int bit = 0x1;
		for (int i = 0; i < 8; i++) {
			Str = (((pByte & bit) != 0) ? "1" : "0") + Str;
			bit = bit << 1;
		}
		return Str;
	}

	/**
	 * Puts leading zeroes to fill in 32 digit
	 * 
	 * @param	pStr	n-bit binary sequence string of "0" and "1"	where n less than 32 
	 * @return	32-bit binary sequence string of the input with "0" leading zero 
	 **/
	static public String lz32(String pStr) {
		while (pStr.length() < 32)
			pStr = "0" + pStr;
		return pStr;
	}

	/**
	 * Put leading zeroes to fill in a arbitrary "width" digit
	 * 
	 * @param	pStr	n-bit binary sequence string of "0" and "1" where n less than width
	 * @param	width	the length of the result string 
	 * @return	width-bit binary sequence string of the input with "0" leading zero
	 **/
	static public String lz(String pStr, int width) {
		while (pStr.length() < width)
			pStr = "0" + pStr;
		return pStr;
	}

	/**
     * Convert byte[] to hex string<br>
     * Zeroth byte is display first(e.g. 0th....nth ) by default
     * 
     * @param	pBytes	the input byte array
     * @return	hex sequence string of the input bytes (including leading "0x") 
     **/
	static public String bc2hex(byte[] pBytes) {
		if (pBytes == null)
			return "0x<NULL>";
		if (pBytes.length == 0)
			return "0x";
		char[] Chars = new char[2 * pBytes.length + 2];
		Chars[0] = '0';
		Chars[1] = 'x';

		int Pos = 2;
		for (int i = 0; i < pBytes.length; i++) {
			int i0 = getValidByte(pBytes[i]) >> 4;
			int i1 = getValidByte(pBytes[i]) & 0x0F;
			Chars[Pos++] = HEX.charAt(i0);
			Chars[Pos++] = HEX.charAt(i1);
		}
		return new String(Chars);
	}

	/**
     * Convert ByteArray to hex string<br>
     * Zeroth byte is display first(e.g. 0th....nth ) by default
     * 
     * @param	pBA	the input {@link net.nawaman.util.bytearray.ByteArray ByteArray}
     * @return	hex sequence string of the input bytes (including leading "0x") 
     */
	static public String bc2hex(ByteArray pBA) {
		if (pBA == null)
			return "0x<NULL>";
		if (pBA.length() == 0)
			return "0x";

		int l = pBA.length();
		char[] Chars = new char[2 * l + 2];
		Chars[0] = '0';
		Chars[1] = 'x';

		int Pos = 2;
		for (int i = 0; i < l; i++) {
			int b = pBA.get(i);
			int i0 = getValidByte(b) >> 4;
			int i1 = getValidByte(b) & 0x0F;
			Chars[Pos++] = HEX.charAt(i0);
			Chars[Pos++] = HEX.charAt(i1);
		}
		return new String(Chars);
	}

	// Save and Load -----------------------------------------------------------

	/**
	 * Saves a ByteArray to a file named pFName
	 * 
	 * @param	pFName	the file name to be saved to
	 * @param	BA		the input {@link net.nawaman.util.bytearray.ByteArray ByteArray}
	 * @throws	IOException	when there is an error
	 **/
	static public void saveBytesToFile(String pFName, ByteArray BA)
			throws IOException {
		File F = new File(pFName);
		UByte.saveBytesToFile(F, BA);
	}

	/**
	 * Saves a ByteArray to a file
	 * 
	 * @param	pF		the file to be saved to
	 * @param	BA		the input {@link ByteArray ByteArray}
	 * @throws	IOException	when there is an error
	 **/
	static public void saveBytesToFile(File pF, ByteArray BA)
			throws IOException {
		OutputStream OS = new BufferedOutputStream(new FileOutputStream(pF));
		int buffsize = 4096;
		int pPos = 0;
		while (pPos <= BA.length()) {
			int Length = ((pPos + buffsize) > BA.length()) ? (BA.length() - pPos)
					: buffsize;
			OS.write(BA.get(pPos, Length));
			OS.flush();
			pPos += buffsize;
		}
		OS.close();
		return;
	}

	/**
	 * Loads a ByteArray from a file named pFName
	 * 
	 * @param	pFName	the name of the file to be load from
	 * @return	the {@link	ByteArray	ByteArray} of the file content
	 * @throws	IOException	when there is an error
	 **/
	static public ByteArray loadBytesFromFile(String pFName) throws IOException {
		File F = new File(pFName);
		return UByte.loadBytesFromFile(F);
	}

	/**
	 * Loads a ByteArray from a file
	 * 
	 * @param	pF	the file to be load from
	 * @return	the {@link	ByteArray	ByteArray} of the file content
	 * @throws	IOException	when there is an error
	 **/
	static public ByteArray loadBytesFromFile(File pF) throws IOException {
		if (pF == null)
			throw new FileNotFoundException("<NULL>");
		if (!pF.exists())
			throw new FileNotFoundException(pF.getName());

		AppendableByteArray ABA = new AppendableByteArray();

		InputStream IS = new BufferedInputStream(new FileInputStream(pF));
		int l = 0;
		int buffsize = 4096;
		byte[] bs = new byte[buffsize];

		do {
			l = IS.read(bs);
			if (l == buffsize) {
				ABA.append(bs);
				continue;
			}

			if (l != -1) {
				byte[] bs2 = new byte[l];
				System.arraycopy(bs, 0, bs2, 0, l);
				ABA.append(bs2);
			}
		} while (l == buffsize);
		IS.close();
		return ABA;
	}

	// Conversion to/from byte code --------------------------------------------

	// Boolean +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	/**
     * Converts a boolean to byte[]<br>
     * 0 = FALSE, otherwise TRUE
     * 
     * @param	pBoolean	the boolean input value
     * @return	the byte array output value
     */
	static public byte[] boolean2bc(boolean pBoolean) {
		byte[] bytes = new byte[1];
		bytes[0] = (byte) (pBoolean ? 1 : 0);
		return bytes;
	}

	/**
     * Converts byte[] to a boolean<br>
     * 0 = FALSE, otherwise TRUE
     * 
     * @param	pBytes	the byte array input value
     * @return	the boolean output value
     */
	static public boolean bc2boolean(byte[] pBytes) {
		if (pBytes == null)
			return false;
		if (pBytes.length < 1)
			return false;
		return (pBytes[0] != 0);
	}

	/**
     * Converts ByteArray to a boolean<br>
     * 0 = FALSE, otherwise TRUE
     * 
     * @param	pBA	the ByteArray input value
     * @return	the boolean output value
     */
	static public boolean bc2boolean(ByteArray pBA) {
		if (pBA == null)
			return false;
		if (pBA.length() < 1)
			return false;
		return (pBA.get(0) != 0);
	}

	// Boolean[] +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	/**
     * Converts an array of booleans to byte[]<br>
     * 0 = FALSE, otherwise TRUE<br>
     * Bit 0th...nth
     */
	static public byte[] booleanArray2bc(boolean[] pBooleans) {
		if (pBooleans == null)
			return new byte[0];

		byte[] Count = UByte.en2bc(pBooleans.length);
		int count = Count.length + (pBooleans.length / 8)
				+ (((pBooleans.length % 8) > 0) ? 1 : 0);
		byte[] bytes = new byte[count];

		// Copy the count of this array
		System.arraycopy(Count, 0, bytes, 0, Count.length);

		// Reuse the variable
		// count of byte that will be fully populated by the bits
		count = pBooleans.length / 8;

		int Pos = Count.length;
		int BPos = 0;

		// Populate the bits that are fit in full bytes
		for (int i = 0; i < count; i++) {
			int b = 0;
			b |= pBooleans[BPos] ? (1 << 7) : 0;
			BPos++;
			b |= pBooleans[BPos] ? (1 << 6) : 0;
			BPos++;
			b |= pBooleans[BPos] ? (1 << 5) : 0;
			BPos++;
			b |= pBooleans[BPos] ? (1 << 4) : 0;
			BPos++;
			b |= pBooleans[BPos] ? (1 << 3) : 0;
			BPos++;
			b |= pBooleans[BPos] ? (1 << 2) : 0;
			BPos++;
			b |= pBooleans[BPos] ? (1 << 1) : 0;
			BPos++;
			b |= pBooleans[BPos] ? (1 << 0) : 0;
			BPos++;

			bytes[Pos] = (byte) b;
			Pos++;
		}

		// Populate the rest of the bits
		int bit = 0x01 << 7;
		while (BPos < pBooleans.length) {
			bytes[Pos] |= pBooleans[BPos] ? bit : 0;
			bit = bit >> 1;
			BPos++;
		}

		return bytes;
	}

	/**
     * Converts byte[] to an array of booleans<br>
     * 0 = FALSE, otherwise TRUE<br>
     * Bit 0th...nth
     */
	static public boolean[] bc2booleanArray(byte[] pBytes) {
		if (pBytes == null)
			return null;

		int length = (int) bc2en(pBytes);
		int count = enCount(pBytes) + (length / 8)
				+ (((length % 8) > 0) ? 1 : 0);
		if (pBytes.length < count)
			return null;

		boolean[] bits = new boolean[length];

		// Reuse the variable
		// count of byte that will be fully populated by the bits
		count = length / 8;

		int Pos = enCount(pBytes);
		int BPos = 0;

		// Populate the bits that are fit in full bytes
		for (int i = 0; i < count; i++) {
			int b = pBytes[Pos];
			bits[BPos] = ((b & (1 << 7)) != 0);
			BPos++;
			bits[BPos] = ((b & (1 << 6)) != 0);
			BPos++;
			bits[BPos] = ((b & (1 << 5)) != 0);
			BPos++;
			bits[BPos] = ((b & (1 << 4)) != 0);
			BPos++;
			bits[BPos] = ((b & (1 << 3)) != 0);
			BPos++;
			bits[BPos] = ((b & (1 << 2)) != 0);
			BPos++;
			bits[BPos] = ((b & (1 << 1)) != 0);
			BPos++;
			bits[BPos] = ((b & (1 << 0)) != 0);
			BPos++;
			Pos++;
		}

		// Populate the rest of the bits
		int bit = 0x01 << 7;
		while (BPos < length) {
			bits[BPos] = ((pBytes[Pos] & bit) != 0);
			bit = bit >> 1;
			BPos++;
		}

		return bits;
	}

	/**
     * Converts ByteArray to an array of booleans<br>
     * 0 = FALSE, otherwise TRUE<br>
     * Bit 0th...nth
     */
	static public boolean[] bc2booleanArray(ByteArray pBA) {
		if (pBA == null)
			return null;

		int length = (int) bc2en(pBA);
		int count = enCount(pBA) + (length / 8) + (((length % 8) > 0) ? 1 : 0);
		if (pBA.length() < count)
			return null;

		boolean[] bits = new boolean[length];

		// Reuse the variable
		// count of byte that will be fully populated by the bits
		count = length / 8;

		int Pos = enCount(pBA);
		int BPos = 0;

		// Populate the bits that are fit in full bytes
		for (int i = 0; i < count; i++) {
			int b = pBA.get(Pos);
			bits[BPos] = ((b & (1 << 7)) != 0);
			BPos++;
			bits[BPos] = ((b & (1 << 6)) != 0);
			BPos++;
			bits[BPos] = ((b & (1 << 5)) != 0);
			BPos++;
			bits[BPos] = ((b & (1 << 4)) != 0);
			BPos++;
			bits[BPos] = ((b & (1 << 3)) != 0);
			BPos++;
			bits[BPos] = ((b & (1 << 2)) != 0);
			BPos++;
			bits[BPos] = ((b & (1 << 1)) != 0);
			BPos++;
			bits[BPos] = ((b & (1 << 0)) != 0);
			BPos++;
			Pos++;
		}

		// Populate the rest of the bits
		int bit = 0x01 << 7;
		while (BPos < length) {
			bits[BPos] = ((pBA.get(Pos) & bit) != 0);
			bit = bit >> 1;
			BPos++;
		}

		return bits;
	}

	// Short +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	/**
     * Converts a two-byte number to byte[]<br>
     * By default - Big Endian or the most significant byte first<br>
     * e.g. 0102H == bc{01H, 02H};
     */
	static public byte[] short2bc(short pInt) {
		boolean negative = (pInt < 0);
		if (negative)
			pInt = (short) ((-pInt) - 1);
		byte[] bytes = new byte[2];

		// Use no loop for the better performance
		bytes[1] = (byte) (pInt & 0x00FF);
		pInt = (short) (pInt >> 8);
		bytes[0] = (byte) (pInt & 0x00FF);

		if (negative) {
			// Use no loop for the better performance
			bytes[0] = (byte) (bytes[0] ^ 0xFF);
			bytes[1] = (byte) (bytes[1] ^ 0xFF);
		}
		return bytes;
	}

	/**
     * Converts byte[] to a two-byte number<br>
     * By default - Big Endian or the most significant byte first<br>
     * e.g. 0102H == bc{01H, 02H};
     */
	static public short bc2short(byte[] pBytes) {
		if (pBytes.length < 2)
			return 0;

		// Use no loop for the better performance
		// Use int instead of byte to avoid negative problem
		int i0 = pBytes[0];
		if (i0 < 0)
			i0 = 255 + i0 + 1;
		int i1 = pBytes[1];
		if (i1 < 0)
			i1 = 255 + i1 + 1;

		return (short) ((i0 << 8) + i1);
	}

	/**
     * Converts ByteArray to a two-byte number<br>
     * By default - Big Endian or the most significant byte first<br>
     * e.g. 0102H == bc{01H, 02H};
     */
	static public short bc2short(ByteArray pBA) {
		if (pBA == null)
			return 0;
		if (pBA.length() < 2)
			return 0;

		// Use no loop for the better performance
		// Use int instead of byte to avoid negative problem
		int i0 = pBA.get(0);
		if (i0 < 0)
			i0 = 255 + i0 + 1;
		int i1 = pBA.get(1);
		if (i1 < 0)
			i1 = 255 + i1 + 1;

		return (short) ((i0 << 8) + i1);
	}

	// Char ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	/**
     * Converts a two-byte char to byte[]<br>
     * Up to two-byte char is convert to byte[]. Note that this is not UTF8 or
     * UTF16 coding. It uses more space-effective coding as follow:
     * 
     * <pre>
     *    1: 0000 to 007F : 1 byte  - 0xxx xxxx           -&gt; 0xxx xxxx
     *    1: 007F to 7FFF : 2 bytes - 0xxx xxxx yyyy yyyy -&gt; 1xxx xxxx yyyy yyyy
     * </pre>
     * 
     * By default - Big Endian or the most significant byte first</br> e.g.
     * 0102H == bc{01H, 02H};
     */
	static public byte[] char2bc(char pC) {
		if (pC < 0x80) {
			byte[] bytes = new byte[1];
			bytes[0] = (byte) pC;
			return bytes;
		}
		byte[] bytes = new byte[2];

		// Use no loop for the better performance
		bytes[1] = (byte) (pC & 0x00FF);
		pC = (char) (pC >> 8);
		bytes[0] = (byte) ((pC & 0x007F) | 0x80);
		return bytes;
	}

	/**
     * Converts byte[] to a two-byte chat<br>
     * Upto two-byte char is convert to byte[]. Note that this is not UTF8 or
     * UTF16 coding. It uses more space-effective coding as follow:
     * 
     * <pre>
     *    1: 0000 to 007F : 1 byte  - 0xxx xxxx           -&gt; 0xxx xxxx
     *    1: 007F to 7FFF : 2 bytes - 0xxx xxxx yyyy yyyy -&gt; 1xxx xxxx yyyy yyyy
     * </pre>
     * 
     * By default - Big Endian or the most significant byte first</br> e.g.
     * 0102H == bc{01H, 02H};
     */
	static public char bc2char(byte[] pBytes) {
		if (pBytes.length < 1)
			return 0;

		// Use no loop for the better performance
		// Use int instead of byte to avoid negative problem
		int i0 = pBytes[0];
		if ((i0 & 0x80) == 0)
			return (char) i0;

		if (pBytes.length < 2)
			return 0;
		i0 = (255 + i0 + 1) & 0x7F;
		int i1 = pBytes[1];
		if (i1 < 0)
			i1 = 255 + i1 + 1;

		return (char) ((i0 << 8) + i1);
	}

	/**
     * Converts ByteArray to a two-byte chat<br>
     * Up to two-byte char is convert to byte[]. Note that this is not UTF8 or
     * UTF16 coding. It uses more space-effective coding as follow:
     * 
     * <pre>
     *    1: 0000 to 007F : 1 byte  - 0xxx xxxx           -&gt; 0xxx xxxx
     *    1: 007F to 7FFF : 2 bytes - 0xxx xxxx yyyy yyyy -&gt; 1xxx xxxx yyyy yyyy
     * </pre>
     * 
     * By default - Big Endian or the most significant byte first</br> e.g.
     * 0102H == bc{01H, 02H};
     */
	static public char bc2char(ByteArray pBA) {
		if (pBA == null)
			return 0;
		if (pBA.length() < 1)
			return 0;

		// Use no loop for the better performance
		// Use int instead of byte to avoid negative problem
		int i0 = pBA.get(0);
		if ((i0 & 0x80) == 0)
			return (char) i0;

		if (pBA.length() < 2)
			return 0;
		i0 = (255 + i0 + 1) & 0x7F;
		int i1 = pBA.get(1);
		if (i1 < 0)
			i1 = 255 + i1 + 1;

		return (char) ((i0 << 8) + i1);
	}

	// Integer ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	/**
     * Converts a four-byte number to byte[]<br>
     * By default - Big Endian or the most significant byte first<br>
     * e.g. 01020304H == bc{01H, 02H, 03H, 04H};
     */
	static public byte[] int2bc(int pInt) {
		boolean negative = (pInt < 0);
		if (negative)
			pInt = (-pInt) - 1;
		byte[] bytes = new byte[4];

		// Use no loop for the better performance
		bytes[3] = (byte) (pInt & 0x000000FF);
		pInt = (pInt >> 8);
		bytes[2] = (byte) (pInt & 0x000000FF);
		pInt = (pInt >> 8);
		bytes[1] = (byte) (pInt & 0x000000FF);
		pInt = (pInt >> 8);
		bytes[0] = (byte) (pInt & 0x000000FF);

		if (negative) {
			// Use no loop for the better performance
			bytes[0] = (byte) (bytes[0] ^ 0xFF);
			bytes[1] = (byte) (bytes[1] ^ 0xFF);
			bytes[2] = (byte) (bytes[2] ^ 0xFF);
			bytes[3] = (byte) (bytes[3] ^ 0xFF);
		}
		return bytes;
	}

	/**
     * Converts byte[] to a four-byte number<br>
     * By default - Big Endian or the most significant byte first<br>
     * e.g. 001020304H == bc{01H, 02H, 03H, 04H};
     */
	static public int bc2int(byte[] pBytes) {
		if (pBytes.length < 4)
			return 0;

		// Use no loop for the better performance
		// Use int instead of byte to avoid negative problem
		int i0 = pBytes[0];
		if (i0 < 0)
			i0 = 255 + i0 + 1;
		int i1 = pBytes[1];
		if (i1 < 0)
			i1 = 255 + i1 + 1;
		int i2 = pBytes[2];
		if (i2 < 0)
			i2 = 255 + i2 + 1;
		int i3 = pBytes[3];
		if (i3 < 0)
			i3 = 255 + i3 + 1;

		return (i0 << 24) + (i1 << 16) + (i2 << 8) + i3;
	}

	/**
     * Converts ByteArray to a four-byte number<br>
     * By default - Big Endian or the most significant byte first<br>
     * e.g. 01020304H == bc{01H, 02H, 03H, 04H};
     */
	static public int bc2int(ByteArray pBA) {
		if (pBA == null)
			return 0;
		if (pBA.length() < 4)
			return 0;

		// Use no loop for the better performance
		// Use int instead of byte to avoid negative problem
		int i0 = pBA.get(0);
		if (i0 < 0)
			i0 = 255 + i0 + 1;
		int i1 = pBA.get(1);
		if (i1 < 0)
			i1 = 255 + i1 + 1;
		int i2 = pBA.get(2);
		if (i2 < 0)
			i2 = 255 + i2 + 1;
		int i3 = pBA.get(3);
		if (i3 < 0)
			i3 = 255 + i3 + 1;

		return (i0 << 24) + (i1 << 16) + (i2 << 8) + i3;
	}

	// Long ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	/**
     * Converts a eight-byte number to byte[]<br>
     * By default - Big Endian or the most significant byte first<br>
     * e.g. 0102030405060708H == bc{01H, 02H, 03H, 04H, 05H, 06H, 07H, 08H};
     */
	static public byte[] long2bc(long pLong) {
		boolean negative = (pLong < 0);
		if (negative)
			pLong = (-pLong) - 1;
		byte[] bytes = new byte[8];

		// Use no loop for the better performance
		bytes[7] = (byte) (pLong & 0x000000FF);
		pLong = (pLong >> 8);
		bytes[6] = (byte) (pLong & 0x000000FF);
		pLong = (pLong >> 8);
		bytes[5] = (byte) (pLong & 0x000000FF);
		pLong = (pLong >> 8);
		bytes[4] = (byte) (pLong & 0x000000FF);
		pLong = (pLong >> 8);
		bytes[3] = (byte) (pLong & 0x000000FF);
		pLong = (pLong >> 8);
		bytes[2] = (byte) (pLong & 0x000000FF);
		pLong = (pLong >> 8);
		bytes[1] = (byte) (pLong & 0x000000FF);
		pLong = (pLong >> 8);
		bytes[0] = (byte) (pLong & 0x000000FF);

		if (negative) {
			// Use no loop for the better performance
			bytes[0] = (byte) (bytes[0] ^ 0xFF);
			bytes[1] = (byte) (bytes[1] ^ 0xFF);
			bytes[2] = (byte) (bytes[2] ^ 0xFF);
			bytes[3] = (byte) (bytes[3] ^ 0xFF);
			bytes[4] = (byte) (bytes[4] ^ 0xFF);
			bytes[5] = (byte) (bytes[5] ^ 0xFF);
			bytes[6] = (byte) (bytes[6] ^ 0xFF);
			bytes[7] = (byte) (bytes[7] ^ 0xFF);
		}
		return bytes;
	}

	/**
     * Converts byte[] to a eight-byte number<br>
     * By default - Big Endian or the most significant byte first<br>
     * e.g. 0102030405060708H == bc{01H, 02H, 03H, 04H, 05H, 06H, 07H, 08H};
     */
	static public long bc2long(byte[] pBytes) {
		if (pBytes == null)
			return 0;
		if (pBytes.length < 8)
			return 0;

		// Use no loop for the better performance
		// Use int instead of byte to avoid negative problem
		long l0 = pBytes[0];
		if (l0 < 0)
			l0 = 255 + l0 + 1;
		long l1 = pBytes[1];
		if (l1 < 0)
			l1 = 255 + l1 + 1;
		long l2 = pBytes[2];
		if (l2 < 0)
			l2 = 255 + l2 + 1;
		long l3 = pBytes[3];
		if (l3 < 0)
			l3 = 255 + l3 + 1;
		long l4 = pBytes[4];
		if (l4 < 0)
			l4 = 255 + l4 + 1;
		long l5 = pBytes[5];
		if (l5 < 0)
			l5 = 255 + l5 + 1;
		long l6 = pBytes[6];
		if (l6 < 0)
			l6 = 255 + l6 + 1;
		long l7 = pBytes[7];
		if (l7 < 0)
			l7 = 255 + l7 + 1;

		return (l0 << 56) + (l1 << 48) + (l2 << 40) + (l3 << 32) + (l4 << 24)
				+ (l5 << 16) + (l6 << 8) + l7;
	}

	/**
     * Converts ByteArray to a eight-byte number<br>
     * By default - Big Endian or the most significant byte first<br>
     * e.g. 0102030405060708H == bc{01H, 02H, 03H, 04H, 05H, 06H, 07H, 08H};
     */
	static public long bc2long(ByteArray pBA) {
		if (pBA == null)
			return 0;
		if (pBA.length() < 8)
			return 0;

		// Use no loop for the better performance
		// Use int instead of byte to avoid negative problem
		long l0 = pBA.get(0);
		if (l0 < 0)
			l0 = 255 + l0 + 1;
		long l1 = pBA.get(1);
		if (l1 < 0)
			l1 = 255 + l1 + 1;
		long l2 = pBA.get(2);
		if (l2 < 0)
			l2 = 255 + l2 + 1;
		long l3 = pBA.get(3);
		if (l3 < 0)
			l3 = 255 + l3 + 1;
		long l4 = pBA.get(4);
		if (l4 < 0)
			l4 = 255 + l4 + 1;
		long l5 = pBA.get(5);
		if (l5 < 0)
			l5 = 255 + l5 + 1;
		long l6 = pBA.get(6);
		if (l6 < 0)
			l6 = 255 + l6 + 1;
		long l7 = pBA.get(7);
		if (l7 < 0)
			l7 = 255 + l7 + 1;

		return (l0 << 56) + (l1 << 48) + (l2 << 40) + (l3 << 32) + (l4 << 24)
				+ (l5 << 16) + (l6 << 8) + l7;
	}

	// Number ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	/**
     * Returns the number of byte of expandable number.<br>
     * This is determined from the first byte of pFBytes<br>
     * 
     * <pre>
     *    -0------ -&gt; 1 byte
     *    -10----- -&gt; 2 bytes
     *    -110---- -&gt; 4 bytes
     *    -1110--- -&gt; 5 bytes
     *    -11110-- -&gt; 9 bytes
     * </pre>
     */
	static public int enCount(byte[] pFByte) {
		if (pFByte == null)
			return -1;
		if (pFByte.length == 0)
			return 0;

		byte b0 = pFByte[0];
		if ((b0 & 0x40) == 0x00)
			return 1; // b0 & 01000000b == 00000000b
		if ((b0 & 0x60) == 0x40)
			return 2; // b0 & 01100000b == 01000000b
		if ((b0 & 0x70) == 0x60)
			return 4; // b0 & 01110000b == 01100000b
		if ((b0 & 0x78) == 0x70)
			return 5; // b0 & 01111000b == 01110000b
		if ((b0 & 0x7F) == 0x78)
			return 9; // b0 & 01111111b == 01111000b
		return -1;
	}

	/**
     * Returns the number of byte of expandable number.<br>
     * This is determined from the first byte of pBA<br>
     * 
     * <pre>
     *    -0------ -&gt; 1 byte
     *    -10----- -&gt; 2 bytes
     *    -110---- -&gt; 4 bytes
     *    -1110--- -&gt; 5 bytes
     *    -11110-- -&gt; 9 bytes
     * </pre>
     */
	static public int enCount(ByteArray pBA) {
		if (pBA == null)
			return -1;
		if (pBA.length() < 1)
			return 0;

		byte b0 = pBA.get(0);
		if ((b0 & 0x40) == 0x00)
			return 1; // b0 & 01000000b == 00000000b
		if ((b0 & 0x60) == 0x40)
			return 2; // b0 & 01100000b == 01000000b
		if ((b0 & 0x70) == 0x60)
			return 4; // b0 & 01110000b == 01100000b
		if ((b0 & 0x78) == 0x70)
			return 5; // b0 & 01111000b == 01110000b
		if ((b0 & 0x7F) == 0x78)
			return 9; // b0 & 01111111b == 01111000b
		return -1;
	}

	/**
     * Converts an expandable singed number to byte[]<br>
     * Up to nine-byte signed number is convert to byte[].<br>
     * 
     * <pre>
     *    1 byte  (                -3FH to                 3FH) = s0dddddd
     *    2 bytes (              -1FFFH to               1FFFH) = s10ddddd dddddddd
     *    4 bytes (           -FFFFFFFH to            FFFFFFFH) = s110dddd dddddddd dddddddd dddddddd
     *    5 bytes (         -7FFFFFFFFH to          7FFFFFFFFH) = s1110ddd dddddddd dddddddd dddddddd dddddddd
     *    9 bytes (-7FFFFFFFFFFFFFFFFFH to 7FFFFFFFFFFFFFFFFFH) = s1111000 dddddddd dddddddd dddddddd dddddddd dddddddd dddddddd dddddddd dddddddd
     * </pre>
     * 
     * By default - Big Endian or the most significant byte first</br> e.g.
     * 0102H == bc{01H, 02H};
     */
	static public byte[] en2bc(long pLong) {
		int count = 1;
		boolean negative = (pLong < 0);
		if (negative)
			pLong = -pLong;

		// Find the appropriate length
		if (pLong <= 0x3FL)
			count = 1;
		else if (pLong <= 0x1FFFL)
			count = 2;
		else if (pLong <= 0xFFFFFFFL)
			count = 4;
		else if (pLong <= 0x7FFFFFFFFL)
			count = 5;
		else
			count = 9;

		// Create the byte array
		byte[] bs = new byte[count];
		byte[] bsInt = UByte.long2bc(pLong);
		if (count < 5)
			System.arraycopy(bsInt, (8 - count), bs, 0, count);
		else if (count == 5)
			System.arraycopy(bsInt, 4, bs, 1, 4);
		else
			System.arraycopy(bsInt, 0, bs, 1, 8);

		// Set sign bit
		if (negative)
			bs[0] = (byte) (bs[0] | 0x80); // 1XXXXXXXb

		// Set the count bits
		if (count == 1)
			bs[0] = (byte) (bs[0] & 0xBF); // X0XXXXXXb
		if (count == 2)
			bs[0] = (byte) ((bs[0] | 0x40) & 0xDF); // X10XXXXXb
		if (count == 4)
			bs[0] = (byte) ((bs[0] | 0x60) & 0xEF); // X110XXXXb
		if (count == 5)
			bs[0] = (byte) ((bs[0] | 0x70) & 0xF7); // X1110XXXb
		if (count == 9)
			bs[0] = 0x78; // X1111000b

		// Return
		return bs;
	}

	/**
     * Converts byte[] to an expandable singed number<br>
     * Upto nine-byte signed number is convert to byte[].<br>
     * 
     * <pre>
     *    1 byte  (                -3FH to                 3FH) = s0dddddd
     *    2 bytes (              -1FFFH to               1FFFH) = s10ddddd dddddddd
     *    4 bytes (           -FFFFFFFH to            FFFFFFFH) = s110dddd dddddddd dddddddd dddddddd
     *    5 bytes (         -7FFFFFFFFH to          7FFFFFFFFH) = s1110ddd dddddddd dddddddd dddddddd dddddddd
     *    9 bytes (-7FFFFFFFFFFFFFFFFFH to 7FFFFFFFFFFFFFFFFFH) = s1111000 dddddddd dddddddd dddddddd dddddddd dddddddd dddddddd dddddddd dddddddd
     * </pre>
     * 
     * By default - Big Endian or the most significant byte first</br> e.g.
     * 0102H == bc{01H, 02H};
     */
	static public long bc2en(byte[] pBytes) {
		// Get the length of the value
		int count = enCount(pBytes);
		if (count < 1)
			return 0;

		// Get sign bit
		byte b0 = pBytes[0];
		boolean negative = (b0 & 0x80) != 0x00; // 1XXXXXXXb
		if (negative)
			b0 = (byte) (b0 & 0x7F);

		// Remove the count bits
		if (count == 1)
			b0 = (byte) (b0 & 0x3F);
		if (count == 2)
			b0 = (byte) (b0 & 0x1F);
		if (count == 4)
			b0 = (byte) (b0 & 0x0F);
		if (count == 5)
			b0 = (byte) (b0 & 0x07);

		pBytes[0] = b0;

		// Transfer data
		byte[] bs = new byte[8];
		if (count < 5)
			System.arraycopy(pBytes, 0, bs, (8 - count), count);
		else if (count == 5)
			System.arraycopy(pBytes, 1, bs, 4, 4);
		else
			System.arraycopy(pBytes, 1, bs, 0, 8);

		// Convert
		return ((negative) ? -1 : 1) * UByte.bc2long(bs);
	}

	/**
     * Converts ByteArray to an expansible singed number<br>
     * Upto nine-byte signed number is convert to byte[].<br>
     * 
     * <pre>
     *    1 byte  (                -3FH to                 3FH) = s0dddddd
     *    2 bytes (              -1FFFH to               1FFFH) = s10ddddd dddddddd
     *    4 bytes (           -FFFFFFFH to            FFFFFFFH) = s110dddd dddddddd dddddddd dddddddd
     *    5 bytes (         -7FFFFFFFFH to          7FFFFFFFFH) = s1110ddd dddddddd dddddddd dddddddd dddddddd
     *    9 bytes (-7FFFFFFFFFFFFFFFFFH to 7FFFFFFFFFFFFFFFFFH) = s1111000 dddddddd dddddddd dddddddd dddddddd dddddddd dddddddd dddddddd dddddddd
     * </pre>
     * 
     * By default - Big Endian or the most significant byte first</br> e.g.
     * 0102H == bc{01H, 02H};
     */
	static public long bc2en(ByteArray pBA) {
		// Get the length of the value
		int count = enCount(pBA);
		if (count < 1)
			return 0;

		return UByte.bc2en(pBA.get(0, count));
	}

	// String ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	/**
     * Converts a string to byte[]<br>
     * The convertion is the Java-version of UTF-16 by the method <CODE>getBytes("UTF-8")</CODE>
     * of the class string.
     */
	static public byte[] str2bc(String pStr) {
		if (pStr == null)
			return null;

		byte[] text = null;
		try {
			text = pStr.getBytes("UTF-8");
		} catch (Exception E) {
			return null;
		}
		byte[] count = UByte.char2bc((char) text.length);

		byte[] bs = new byte[count.length + text.length];
		System.arraycopy(count, 0, bs, 0, count.length);
		System.arraycopy(text, 0, bs, count.length, text.length);
		return bs;
	}

	/**
     * Converts byte[] to a string<br>
     * The convertion is the Java-version of UTF-16 by the method <CODE>getBytes("UTF-8")</CODE>
     * of the class string.
     */
	static public String bc2str(byte[] pBytes) {
		if (pBytes == null)
			return null;

		int size = bc2char(pBytes);
		int count = (size <= 0x7F) ? 1 : 2;
		byte[] bs = new byte[size];
		System.arraycopy(pBytes, count, bs, 0, size);

		try {
			return new String(bs, "UTF-8");
		} catch (Exception E) {
			return null;
		}
	}

	/**
     * Converts ByteArray to a string<br>
     * The convertion is the Java-version of UTF-16 by the method <CODE>getBytes("UTF-8")</CODE>
     * of the class string.
     */
	static public String bc2str(ByteArray pBA) {
		if (pBA == null)
			return null;

		int size = bc2char(pBA);
		int count = (size <= 0x7F) ? 1 : 2;
		byte[] bs = pBA.get(count, size);

		try {
			return new String(bs, "UTF-8");
		} catch (Exception E) {
			return null;
		}
	}

	// Single Floating-point number
	// +++++++++++++++++++++++++++++++++++++++++++++
	/**
     * Converts a four-byte floating-point number to byte[]<br>
     * Single-Precision floating-point number (IEEE754 floating-point)
     * -(2-2^-23)x2^126 -> (2-2^-23)x2^127, (+-)INF and NAN
     */
	static public byte[] float2bc(float aF) {
		long aL = 0;
		boolean s = (aF < 0);
		if (aF < 0)
			aF = -aF;

		if (Float.isNaN(aF)) {
			aL = 0x7FC00000;
		} else if (Float.isInfinite(aF)) {
			aL = s ? 0xFF800000 : 0x7F800000;
		} else if (aF != 0.0f) {
			long m = 0;
			long e = 0;

			// Prepare often used constant
			int maxV = (2 << 22);

			// Prepare tempolary variables
			int e0 = 0;
			int e1 = 0;
			float tF = aF;
			if (tF < 0)
				tF = -tF;

			// In case the number is too large
			while (tF > maxV) {
				tF = tF / 2;
				e0++;
			}
			// In case the number is too small
			while (tF < 1) {
				tF = tF * 2;
				e0--;
			}

			// Normalize the mantissa
			// This portion can be merge with the "too large" case
			// but "/" operation perform faster with integer.
			int iF = (int) Math.floor(tF);
			while (iF > 1) {
				iF = iF / 2;
				e1++;
			}

			// Optimize the mantissa bit usage to 23 bits
			// Should later try if using Math.pow() give better performaces.
			for (int i = e1; i <= 22; i++) {
				tF = tF * 2;
			}

			// Calculate the final value
			m = ((int) Math.floor(tF)) & 0x007FFFFF;
			e = e0 + e1;

			aL = ((e + 127) << 23) + m;
		}

		byte[] bytes = new byte[4];
		for (int i = 3; i >= 0; i--) {
			bytes[i] = (byte) (aL & 0x000000FF);
			aL = (aL >> 8);
		}
		bytes[0] |= (byte) (s ? (1 << 7) : 0);

		return bytes;
	}

	/**
     * Converts byte[] to a four-byte floating-point number<br>
     * Single-Precision floating-point number (IEEE754 floating-point)
     * -(2-2^-23)x2^126 -> (2-2^-23)x2^127, (+-)INF and NAN
     */
	static public float bc2float(byte[] pBytes) {
		if (pBytes == null)
			return 0.0f;
		if (pBytes.length < 4)
			return 0.0f;

		int[] Is = new int[4];
		for (int i = 0; i < 4; i++)
			Is[i] = getValidByte(pBytes[i]);
		long l = (Is[0] << 24) | (Is[1] << 16) | (Is[2] << 8) | Is[3];

		if (l == 0)
			return 0.0f;
		if (l == 0x7fc00000)
			return Float.NaN;
		if (l == 0x7f800000)
			return Float.POSITIVE_INFINITY;
		if (l == 0xff800000)
			return Float.NEGATIVE_INFINITY;

		int s =       (((l & 0x80000000) == 0) ? 0 : 1);
		int m = (int) (l & 0x007FFFFF) + 0x00800000;
		int e = (int) ((l & 0x7F800000) >> 23) - 127 - 23;
		return (float) ((((s == 0) ? 1 : -1) * m * Math.pow(2, e)));
	}

	/**
     * Converts ByteArray to a four-byte floating-point number<br>
     * Single-Precision floating-point number (IEEE754 floating-point)
     * -(2-2^-23)x2^126 -> (2-2^-23)x2^127, (+-)INF and NAN
     */
	static public float bc2float(ByteArray pBA) {
		if (pBA == null)
			return 0.0f;
		if (pBA.length() < 4)
			return 0.0f;

		int[] Is = new int[4];
		for (int i = 0; i < 4; i++)
			Is[i] = getValidByte(pBA.get(i));
		long l = (Is[0] << 24) | (Is[1] << 16) | (Is[2] << 8) | Is[3];

		if (l == 0)
			return 0.0f;
		if (l == 0x7fc00000)
			return Float.NaN;
		if (l == 0x7f800000)
			return Float.POSITIVE_INFINITY;
		if (l == 0xff800000)
			return Float.NEGATIVE_INFINITY;

		int s =       (((l & 0x80000000) == 0) ? 0 : 1);
		int m = (int) (l & 0x007FFFFF) + 0x00800000;
		int e = (int) ((l & 0x7F800000) >> 23) - 127 - 23;
		return (float) ((((s == 0) ? 1 : -1) * m * Math.pow(2, e)));
	}

	// Double-Precision Floating-point number ++++++++++++++++++++++++++++++++++
	/**
     * Converts an eight-byte floating-point number to byte[]<br>
     * Double-Precision floating-point number (IEEE754 floating-point)
     * -(2-2^-52)x2^1023 -> (2-2^-52)x2^1024, (+-)INF and NAN
     */
	static public byte[] double2bc(double aD) {
		long aL = 0;
		boolean s = (aD < 0);
		if (aD < 0)
			aD = -aD;

		if (Double.isNaN(aD)) {
			aL = 0x7FF8000000000000L;
		} else if (Double.isInfinite(aD)) {
			aL = s ? 0xFFF0000000000000L : 0x7FF0000000000000L;
		} else if (aD != 0.0) {
			long m = 0;
			long e = 0;

			// Prepare often used constant
			int maxV = (2 << 51);

			// Prepare tempolary variables
			int e0 = 0;
			int e1 = 0;
			double tD = aD;
			if (tD < 0)
				tD = -tD;

			// In case the number is too large
			while (tD > maxV) {
				tD = tD / 2;
				e0++;
			}
			// In case the number is too small
			while (tD < 1) {
				tD = tD * 2;
				e0--;
			}

			// Normalize the mantissa
			// This portion can be merge with the "too large" case
			// but "/" operation perform faster with integer.
			long iD = (long) Math.floor(tD);
			while (iD > 1) {
				iD = iD / 2;
				e1++;
			}

			// Optimize the mantissa bit usage to 23 bits
			// Should later try if using Math.pow() give better performaces.
			for (int i = e1; i <= 51; i++) {
				tD = tD * 2;
			}

			// Calculate the final value
			m = ((long) Math.floor(tD)) & 0x000FFFFFFFFFFFFFL;
			e = e0 + e1;

			aL = ((e + 1023) << 52) + m;
		}

		byte[] bytes = new byte[8];
		for (int i = 7; i >= 0; i--) {
			bytes[i] = (byte) (aL & 0x000000FF);
			aL = (aL >> 8);
		}
		bytes[0] |= (byte) (s ? (1 << 7) : 0);

		return bytes;
	}

	/**
     * Converts byte[] to an eight-byte floating-point number<br>
     * Double-Precision floating-point number (IEEE754 floating-point)
     * -(2-2^-52)x2^1023 -> (2-2^-52)x2^1024, (+-)INF and NAN
     */
	static public double bc2double(byte[] pBytes) {
		if (pBytes == null)
			return 0.0;
		if (pBytes.length < 8)
			return 0.0;

		long[] Is = new long[8];
		for (int i = 0; i < 8; i++)
			Is[i] = getValidByte(pBytes[i]);

		boolean s = ((Is[0] & 0x80) != 0);
		Is[0] &= 0x7F;
		long l = (Is[0] << 56) | (Is[1] << 48) | (Is[2] << 40) | (Is[3] << 32)
				| (Is[4] << 24) | (Is[5] << 16) | (Is[6] << 8) | Is[7];

		if (l == 0)
			return 0.0;
		if (l == 0x7FF8000000000000L)
			return Double.NaN;
		if (l == 0x7FF0000000000000L)
			return Double.POSITIVE_INFINITY;
		if (l == 0xFFF0000000000000L)
			return Double.NEGATIVE_INFINITY;

		long m = (l & 0x000FFFFFFFFFFFFFL) + 0x0010000000000000L;
		long e = ((l & 0x7FF0000000000000L) >> 52) - 1023 - 52;
		return (((s ? -1 : 1) * m * Math.pow(2, e)));
	}

	/**
     * Converts ByteArray to an eight-byte floating-point number<br>
     * Double-Precision floating-point number (IEEE754 floating-point)
     * -(2-2^-52)x2^1023 -> (2-2^-52)x2^1024, (+-)INF and NAN
     */
	static public double bc2double(ByteArray pBA) {
		if (pBA == null)
			return 0.0;
		if (pBA.length() < 8)
			return 0.0;

		long[] Is = new long[8];
		for (int i = 0; i < 8; i++)
			Is[i] = getValidByte(pBA.get(i));

		boolean s = ((Is[0] & 0x80) != 0);
		Is[0] &= 0x7F;
		long l = (Is[0] << 56) | (Is[1] << 48) | (Is[2] << 40) | (Is[3] << 32)
				| (Is[4] << 24) | (Is[5] << 16) | (Is[6] << 8) | Is[7];

		if (l == 0)
			return 0.0;
		if (l == 0x7FF8000000000000L)
			return Double.NaN;
		if (l == 0x7FF0000000000000L)
			return Double.POSITIVE_INFINITY;
		if (l == 0xFFF0000000000000L)
			return Double.NEGATIVE_INFINITY;

		long m = (l & 0x000FFFFFFFFFFFFFL) + 0x0010000000000000L;
		long e = ((l & 0x7FF0000000000000L) >> 52) - 1023 - 52;
		return (((s ? -1 : 1) * m * Math.pow(2, e)));
	}
}