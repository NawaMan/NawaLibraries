package net.nawaman.swing.text;

import java.awt.Color;
import java.io.OutputStream;

import net.nawaman.io.FilteredPrintStream;
import net.nawaman.io.NullOutputStream;

/** Formatter PrintStream */
public class FormatterPrintStream extends FilteredPrintStream {

	HTMLOutputPane TheHOP;
	Formatter      TheFormatter; 

	public FormatterPrintStream(HTMLOutputPane pHOP) {
		this(pHOP, null, true, (java.io.PrintStream[])null);
	}
	public FormatterPrintStream(HTMLOutputPane pHOP, OutputStream pOut) {
		this(pHOP, pOut, true, (java.io.PrintStream[])null);
	}
	public FormatterPrintStream(HTMLOutputPane pHOP, OutputStream pOut, boolean pIsAutoFlush) {
		this(pHOP, pOut, pIsAutoFlush, (java.io.PrintStream[])null);
	}
	public FormatterPrintStream(HTMLOutputPane pHOP, OutputStream pOut, boolean pIsAutoFlush,
			java.io.PrintStream ... pTargets) {
		this(pHOP, new Formatter(), pOut, pIsAutoFlush, pTargets);
	}
	
	public FormatterPrintStream(HTMLOutputPane pHOP, Formatter pFormat) {
		this(pHOP, pFormat, null, true, (java.io.PrintStream[])null);
	}
	public FormatterPrintStream(HTMLOutputPane pHOP, Formatter pFormat, OutputStream pOut) {
		this(pHOP, pFormat, pOut, true, (java.io.PrintStream[])null);
	}
	public FormatterPrintStream(HTMLOutputPane pHOP, Formatter pFormat, OutputStream pOut, boolean pIsAutoFlush) {
		this(pHOP, pFormat, pOut, pIsAutoFlush, (java.io.PrintStream[])null);
	}
	public FormatterPrintStream(HTMLOutputPane pHOP, Formatter pFormat, OutputStream pOut, boolean pIsAutoFlush,
			java.io.PrintStream ... pTargets) {
		super((pOut == null)?NullOutputStream.Instance:pOut, pIsAutoFlush, pTargets);
		if(pHOP == null) throw new NullPointerException();
			
		this.TheHOP       = pHOP;
		this.TheFormatter = pFormat;
	}
	
	/**{@inheritDoc}*/ @Override
	protected void write(String S, boolean isNewLine) {
		((HTMLPrintStream)this.TheHOP.getPlainTextPrintStream()).writeWithFormat(S, isNewLine, this.TheFormatter);
		super.write(S, isNewLine);
	}
	
	/** Returns the current formatter */
	public Formatter getFormatter() {
		return this.TheFormatter;
	}
		
	// Special printing ---------------------------------------------------------
	
	/** Prints a hyper-link */
	public void printLink(String Link, String Text) {
		String  P = this.getPattern();
		boolean B = this.isCoverNewLine();
		this.addPattern(String.format("<a href='%s'>%s</a>", Link, Text));
		this.setPattern(P, B);
	}
	
	// Format -------------------------------------------------------------------
	
	/** Checks if this format is to cover across paragraphs */
	public boolean isCoverNewLine() {
		return this.getFormatter().isCoverNewLine();
	}

	/** Changes the format pattern */
	public FormatterPrintStream setPattern(String pPattern) {
		this.getFormatter().setPattern(pPattern, false);
		return this;
	}
	/** Changes the pattern */
	public FormatterPrintStream setPattern(String pPattern, boolean pIsToCoverNewLine) {
		this.getFormatter().setPattern(pPattern, pIsToCoverNewLine);
		return this;
	}
	
	/** Returns the pattern */
	public String getPattern() {
		return this.getFormatter().getPattern();
	}
	
	/** Appends the format */
	public FormatterPrintStream addPattern(String pPattern) {
		this.getFormatter().addPattern(pPattern);
		return this;
	}
	
	/** Changes the format pattern */
	public FormatterPrintStream clearPattern() {
		this.getFormatter().clearPattern();
		return this;
	}
	
	// Pre-define -----------------------------------------------------------------------------
	/** Add bold font style to the formatter */
	public FormatterPrintStream addBold() {
		this.getFormatter().addBold();
		return this;
	}
	/** Add italic font style to the formatter */
	public FormatterPrintStream addItalic() {
		this.getFormatter().addItalic();
		return this;
	}
	/** Add italic font style to the formatter */
	public FormatterPrintStream addUnderline() {
		this.getFormatter().addUnderline();
		return this;
	}
	/** Add pre-formatted style to the formatter */
	public FormatterPrintStream addPreformatted() {
		this.getFormatter().addPreformatted();
		return this;
	}
	/** Add pre-formatted style to the formatter */
	public FormatterPrintStream changeColor(Color C) {
		this.getFormatter().changeColor(C);
		return this;
	}
	/** Add relative font size */
	public FormatterPrintStream changeAbsoluteFontSize(int pSize) {
		this.getFormatter().changeAbsoluteFontSize(pSize);
		return this;
	}
	/** Add relative font size e.g. +1 or -1 */
	public FormatterPrintStream changeRelativeFontSize(int pSize) {
		this.getFormatter().changeRelativeFontSize(pSize);
		return this;
	}
}