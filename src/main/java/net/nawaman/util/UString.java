package net.nawaman.util;

import java.io.*;

import net.nawaman.util.bytearray.*;
import net.nawaman.util.chararray.*;

/**
 * String utility
 *
 * @author	Nawapunth Manusitthipol
 **/
public class UString {
	
	/** String array of 0 member. This is used to reduce unnecessary object creation. */
	static public final String[] EmptyStringArray = new String[0];
	
	private UString() {}
	
	/** Checks if string S1 equal to string S2 */
	static public boolean equal(String S1, String S2) {
		if(S1 ==   S2) return  true;
		if(S1 == null) return false;
		if(S2 == null) return false;
		return S1.equals(S2);
		//return (hash(S1) == hash(S2));
	}
	/** Returns hash value of string S */
	static public int hash(String S) {
		if(S == null) return 0;
		int h = 0;
		int p = 0;
		for(int i = S.length(); --i >= 0;) { h += ((p + 10) << 4)*S.charAt(i); p++; }
		return (h < 0)?-h:h;
	}

	/**
	 * Put leading space until the string is width
	 * 
	 * @param	pStr	the input string
	 * @param	width	the length of the result string 
	 * @return	the input string with leading space, the result string will have the length "width"
	 **/
	static public String ls(String pStr, int width) {
		while (pStr.length() < width)
			pStr = " " + pStr;
		return pStr;
	}

	/**
	 * Put leading tab until the string is width
	 * 
	 * @param	pStr	the input string
	 * @param	width	the length of the result string 
	 * @return	the input string with leading space, the result string will have the length "width"
	 **/
	static public String lt(String pStr, int width) {
		while (pStr.length() < width)
			pStr = "\t" + pStr;
		return pStr;
	}

	/**
	 * Put tailing space until the string is width
	 * 
	 * @param	pStr	the input string
	 * @param	width	the length of the result string 
	 * @return	the input string with leading space, the result string will have the length "width"
	 **/
	static public String ts(String pStr, int width) {
		while (pStr.length() < width)
			pStr = pStr + " ";
		return pStr;
	}

	/**
	 * Put leading char until the string is width
	 * 
	 * @param	pStr	the input string
	 * @param	width	the length of the result string 
	 * @return	the input string with leading space, the result string will have the length "width"
	 **/
	static public String lc(String pStr, char pFill,int width) {
		while (pStr.length() < width) pStr = pFill + pStr;
		return pStr;
	}

	/**
	 * Put tailing char until the string is width
	 * 
	 * @param	pStr	the input string
	 * @param	width	the length of the result string 
	 * @return	the input string with leading space, the result string will have the length "width"
	 **/
	static public String tc(String pStr, char pFill, int width) {
		StringBuffer SB = new StringBuffer();
		SB.append(pStr);
		while (SB.length() < width) SB.append(pFill);
		return SB.toString();
	}
	
	static public String repeat(String pStr, int Time) {
		StringBuffer SB = new StringBuffer();
		for(int i = Time; --i >= 0; ) SB.append(pStr);
		return SB.toString();
	}
	
	/**
	 * Loads a text from a file
	 */
	static public String loadTextFile(String pTextFileName)  throws IOException {
		return UString.loadTextFile(new File(pTextFileName));
	}
	
	/**
	 * Loads a text from a file
	 */
	static public String loadTextFile(File pTextFile) throws IOException {
		FileInputStream   FIS = new FileInputStream(pTextFile);
		InputStreamReader IDR = new InputStreamReader(FIS);
		BufferedReader    BR  = new BufferedReader(IDR);
	        
		StringBuffer SB  = new StringBuffer();
		String Line;
		while ((Line = BR.readLine()) != null) SB.append(Line).append('\n');
	        
		BR.close();
		return SB.toString().substring(0, SB.length() - 1);
	}
	
	/**
	 * Saves a text to a file
	 */
	static public void saveTextToFile(String pTextFileName, String pText)  throws IOException {
		UString.saveTextToFile(new File(pTextFileName), pText);
	}
	
	/**
	 * Saves a text to a file
	 */
	static public void saveTextToFile(File pTextFile, CharSequence pText) throws IOException {
		BufferedWriter TextOut = new BufferedWriter(new FileWriter(pTextFile));
		TextOut.write(pText.toString());
		TextOut.close();
	}
	
	/**
     * Convert char[] to hex string<br>
     * Zeroth byte is display first(e.g. 0th....nth ) by default
     */
	static public String chararray2hex(CharArray pCA) {
		return UString.chars2hex(pCA.getAll());
	}
	
	/**
     * Convert char[] to hex string<br>
     * Zeroth byte is display first(e.g. 0th....nth ) by default
     */
	static public String chars2hex(char[] pChars) {
		if (pChars == null)
			return "0x<NULL>";
		if (pChars.length == 0)
			return "0x";
		StringBuffer SB = new StringBuffer();
		SB.append("0x");

		for (int i = 0; i < pChars.length; i++) {
			byte[] bs = UByte.char2bc(pChars[i]);
			for (int b = 0; b < bs.length; b++) SB.append(bs[b]); 
		}
		return SB.toString();
	}

	/**
	 * Converts the CharSequence to a escape text
	 * For example Tab => '\t', Newline => '\n' e.g.
	 */
	static public CharSequence escapeText(CharSequence CS) {
		//if(CS == null) return null;
		StringBuffer SB = new StringBuffer();
		for(int i = 0; i < CS.length(); i++) {
			char c = ' ';
			try {
				c = CS.charAt(i);
			} catch(java.lang.StringIndexOutOfBoundsException E) {
				System.out.println("escapeText: " + E.toString());
			}
			if(c ==     '\t') { SB.append("\\t");  continue; }
			if(c ==     '\n') { SB.append("\\n");  continue; }
			if(c ==     '\r') { SB.append("\\r");  continue; }
			if(c ==     '\f') { SB.append("\\f");  continue; }
			if(c ==     '\b') { SB.append("\\b");  continue; }
			if(c ==     '\'') { SB.append("\\\'"); continue; }
			if(c ==     '\"') { SB.append("\\\""); continue; }
			if(c ==     '\\') { SB.append("\\\\"); continue; }
			//if(c <       ' ') { SB.append("\\u" + ls(UByte.bc2hex(UByte.char2bc(c)), 4)); continue; }
			//if(c >= '\u007F') { SB.append("\\u" + ls(UByte.bc2hex(UByte.char2bc(c)), 4)); continue; }
			SB.append(c);
		}
		return SB;
	}
	
	/**
	 * Converts the CharSequence to a escape text
	 * For example '\t' => Tab, '\n' => Newline e.g.
	 */
	static public CharSequence invertEscapeText(CharSequence CS) {
		StringBuffer SB = new StringBuffer();
		for(int i = 0; i < CS.length(); i++) {
			char c = ' ';
			try {
				c = CS.charAt(i);
			} catch(java.lang.StringIndexOutOfBoundsException E) {
				System.out.println("invertEscapeText: " + E.toString());
			}
			if(c != '\\') { SB.append(c); continue; }
			i++;
			c = CS.charAt(i);
			if(c ==  't') { SB.append("\t"); continue; }
			if(c ==  'n') { SB.append("\n"); continue; }
			if(c ==  'r') { SB.append("\r"); continue; }
			if(c ==  'f') { SB.append("\f"); continue; }
			if(c ==  'b') { SB.append("\b"); continue; }
			if(c == '\'') { SB.append("\'"); continue; }
			if(c == '\"') { SB.append("\""); continue; }
			if(c == '\\') { SB.append("\\"); continue; }
			
			if(c == 'u') {
				if(i + 6 >= CS.length()) { SB.append(c); continue; }
				// Found Hexadecimal Unicode Escape
				String UniEsc = CS.subSequence(i + 1, i + 5).toString().toUpperCase();
				// 4-digit hexadecimal
				int C    = 0;
				int Base = 1;
				for(int uc = (UniEsc.length() - 1); uc >= 0; uc--) {
					int I = "0123456789ABCDEF".indexOf(UniEsc.charAt(uc));
					if(I == -1) { SB.append("(*ERROR*)"); break; }
					C += I*Base;
					Base *= 16;
				}
				SB.append("" + C);
				i+= 5;
			}
			if((c >= '0') || (c <= '7')) {
				// Founc Octal Unicode Escape
				String UniEsc = CS.subSequence(i, i + 3).toString().toUpperCase();
				// 2-digit octal
				int C    = 0;
				int Base = 1;
				for(int uc = (UniEsc.length() - 1); uc >= 0; uc--) {
					int I = "01234567".indexOf(UniEsc.charAt(uc));
					if(I == -1) { SB.append("(*ERROR*)"); break; }
					C += I*Base;
					Base *= 8;
				}
				SB.append("" + C);
			}
		}
		return SB;
	}

	// Exception -------------------------------------------------------------------------------------------------------
	
	static public String getThrowableToString(Throwable T) {
		StringBuffer SB = new StringBuffer();
		SB.append(T.toString());
		SB.append("\n");
		ByteArrayOutputStream BAOS = new ByteArrayOutputStream();
		PrintStream PS = new PrintStream(BAOS);
		T.printStackTrace(PS);
		SB.append(BAOS.toString());
		return SB.toString();
	}
	
}
