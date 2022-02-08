package net.nawaman.io;

import java.io.OutputStream;
import java.io.PrintStream;

/**
 * Filtered PrintStream that converts the printed text into a HTML code.
 * 
 \* @author Nawapunth Manusitthipol (https://github.com/NawaMan)
 */
public class HTMLPrintStream extends FilteredPrintStream {

	/** Minimum number for tab size (use in HTML mode) */
	static public final int MinTabSize =  0;
	/** Maximum number for tab size (use in HTML mode) */
	static public final int MaxTabSize = 16;
	
	private int TabSize = 4;
	
	public HTMLPrintStream(OutputStream pOut)                           { this(pOut, true, (PrintStream[])null); }
	public HTMLPrintStream(OutputStream pOut, PrintStream ... pTargets) { this(pOut, true, pTargets);            }
	
	public HTMLPrintStream(OutputStream pOut, boolean pIsAutoFlush, PrintStream ... pTargets) {
		super(pOut, pIsAutoFlush, pTargets);
	}
	
	/** Set the tab size */
	protected void setTabSize(int pTabSize) {
		if(pTabSize < MinTabSize) pTabSize = MinTabSize;
		if(pTabSize > MaxTabSize) pTabSize = MaxTabSize;
		this.TabSize = pTabSize;
	}
	
	/** Returns the tab-size of the this print stream */
	public int getTabSize() {
		return this.TabSize;
	}

	static java.io.PrintStream TheOUT = System.out;
	static java.io.PrintStream TheERR = System.err;
	
	/** Do the filtering */
	@Override protected String filter(String S) {
		S = super.filter(S);
		
		if(S.startsWith("<html>")) {
			S = S.substring("<html>".length());
			StringBuffer SB = new StringBuffer();
			for(int i = 0; i < S.length(); i++) {
				char C = S.charAt(i);
				switch(C) {
					case ' ' : SB.append("&nbsp;");                                                   break;
					case '\t': SB.append("\t"); for(int t = TabSize; --t >= 1; ) SB.append("&nbsp;"); break;
					default:   SB.append(C);                                                          break;
				}
			}
			S = SB.toString();
		} else {
			StringBuffer SB = new StringBuffer();
			for(int i = 0; i < S.length(); i++) {
				char C = S.charAt(i);
				switch(C) {
					case ' ' : SB.append("&nbsp;");                                                   break;
					case '\n': SB.append(this.getNewLineReplacement());                               break;
					case '<' : SB.append("&lt;");                                                     break;
					case '>' : SB.append("&gt;");                                                     break;
					case '\t': SB.append("\t"); for(int t = TabSize; --t >= 1; ) SB.append("&nbsp;"); break;
					default:   SB.append(C);                                                          break;
				}
			}
			S = SB.toString();
		}
		
		return S;
	}
	
	/** The replacement for a new line */
	protected String getNewLineReplacement() { return "\n<br/>"; }
	/** The replacement for a space */
	protected String getSpaceReplacement()   { return "&nbsp;";  }

	/** Converts the given HTML code into a plain text (should work correctly if the given HTMLText is created with this
	 * HTML PrintStream).
	 **/
	public String getPlainText(String pHTMLText) {
		if(pHTMLText == null) return null;
		
		String T  = pHTMLText;
		int    TS = this.getTabSize(); 
		StringBuffer SB = new StringBuffer();
		for(int i = 0; i < T.length(); i++) {
			char c = T.charAt(i);
			switch(c) {
				// New line
				case  10: {
					SB.append("\n");
					// This is an empty line ('\n\n' => '\n \n' => '\n\n')
					if((i < (T.length() + 1)) && (T.charAt(i + 1) == 32) && (T.charAt(i + 2) == 10)) {
						SB.append("\n");
						i += 2;
					}
					break;
				}
				// Space
				case 160: SB.append(" ");  break;
				// Tab ('\t' => '\t&nbsp;&nbsp;&nbsp;' => '\t' (in case of 4 tab size)))
				case  32: {
					boolean isTab = ((i + TS) < T.length());
					for(int t = TS; (--t > 0) && isTab; ) isTab = (T.charAt(i + t) == 160);
					if(isTab) { i += (TS - 1); SB.append("\t"); break; }
				}
				default: SB.append(c);
			}
			
		}
		return SB.toString();	
	}
	
}