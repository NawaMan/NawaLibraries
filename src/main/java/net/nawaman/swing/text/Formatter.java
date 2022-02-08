package net.nawaman.swing.text;

import java.awt.Color;

/** The format for the HTMLPrintStream */
public class Formatter {
	/** Constructs a format */
	public Formatter() {
		this(null, false);
	}
	/** Constructs a format */
	public Formatter(boolean pIsToCoverNewLine) {
		this(null, pIsToCoverNewLine);
	}
	
	/** Constructs a format */
	public Formatter(String pPattern) {
		this(pPattern, false);
	}
	/** Constructs a format */
	public Formatter(String pPattern, boolean pIsToCoverNewLine) {
		this.Pattern          = pPattern;
		this.IsToCoverNewLine = pIsToCoverNewLine;
	}
	
	String  Pattern          = null;
	boolean IsToCoverNewLine = false;
	
	/**
	 * Format a line of string
	 * 
	 * NOTE: The given line of string may contains a new line. The original text is already converted to HTML and it
	 *    will not convert to HTML again so the added format much be valid HTML style.
	 * NOTE: Do not add more context to the line as it will cause the plain-text to be out of sync with the value in
	 *    the document.
	 **/
	public String formarLine(String pOriginalLine) {
		return ((this.Pattern == null) || (pOriginalLine == null) || (pOriginalLine.length() == 0))
		?pOriginalLine
		:String.format(this.Pattern, pOriginalLine);
	}
	/** Checks if this format is to cover across paragraphs */
	public boolean isCoverNewLine() {
		return this.IsToCoverNewLine;
	}
	
	/** Changes the format pattern */
	public Formatter setPattern(String pPattern) {
		this.setPattern(pPattern, false);
		return this;
	}
	/** Changes the pattern */
	public Formatter setPattern(String pPattern, boolean pIsToCoverNewLine) {
		this.Pattern = pPattern;
		if(this.Pattern == null) pIsToCoverNewLine = false;
		return this;
	}
	
	/** Returns the pattern */
	public String getPattern() {
		return this.Pattern;
	}
	
	/** Appends the format */
	public Formatter addPattern(String pPattern) {
		String NewP = pPattern;
		String OldP = this.getPattern();
		
		if((NewP != null) && (NewP.length() != 0)) {
			if((OldP != null) && (OldP.length() != 0))
				 NewP = String.format(OldP, NewP);
			this.setPattern(NewP, false);
			
		} else this.setPattern(null, false);
		return this;
	}
	
	/** Changes the format pattern */
	public Formatter clearPattern() {
		this.setPattern(null, false);
		return this;
	}
	
	// Pre-define -----------------------------------------------------------------------------
	
	/** Add bold font style to the formatter */
	public Formatter addBold() {
		this.addPattern("<b>%s</b>");
		return this;
	}
	/** Add italic font style to the formatter */
	public Formatter addItalic() {
		this.addPattern("<i>%s</i>");
		return this;
	}
	/** Add italic font style to the formatter */
	public Formatter addUnderline() {
		this.addPattern("<u>%s</u>");
		return this;
	}
	/** Add pre-formatted style to the formatter */
	public Formatter addPreformatted() {
		this.addPattern("<pre>%s</pre>");
		return this;
	}
	/** Add pre-formatted style to the formatter */
	public Formatter changeColor(Color C) {
		if(C == null) C = Color.BLACK;
		String S = String.format("%6X", C.getRGB() & 0xFFFFFF).trim();
		while(S.length() < 6) S = "0" + S;
		this.addPattern(String.format("<font color='#%s'>%s</font>", S, "%s"));
		return this;
	}
	/** Add relative font size */
	public Formatter changeAbsoluteFontSize(int pSize) {
		if(pSize < 0) pSize = 0;
		this.addPattern("<font size="+pSize+">%s</font>");
		return this;
	}
	/** Add relative font size e.g. +1 or -1 */
	public Formatter changeRelativeFontSize(int pSize) {
		String S = ((pSize > 0)?"+":"") + pSize;
		this.addPattern("<font size="+S+">%s</font>");
		return this;
	}
}