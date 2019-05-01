package net.nawaman.io;

import java.io.OutputStream;
import java.io.PrintStream;

/** Print stream with format */
public class HTMLFormatPrintStream extends HTMLPrintStream {
	private String  Format  = null;
	
	public HTMLFormatPrintStream(String pFormat, OutputStream pOut) {
		this(pFormat, pOut, true, (PrintStream[])null);
	}
	public HTMLFormatPrintStream(String pFormat, OutputStream pOut, PrintStream ... pTargets) {
		this(pFormat, pOut, true, pTargets);
	}
	
	public HTMLFormatPrintStream(String pFormat, OutputStream pOut, boolean pIsAutoFlush, PrintStream ... pTargets) {
		super(pOut, pIsAutoFlush, pTargets);
		this.Format = pFormat;
	}

	/** Do the filtering */ @Override
	protected String filter(String S) {
		// Do HTML Transformation first
		S = super.filter(S);
		// Add format
		if(this.Format != null) S = String.format(this.Format, S);
		// Return
		return S;
	}
}
