package net.nawaman.io;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Vector;

/**
 * Copied from
 * http://www.java2s.com/Code/Java/File-Input-Output/TeePrintStreamteesallPrintStreamoperationsintoafileratherliketheUNIXtee1command.htm
 * 
 * Written Iam F. Darwin.
 * 
 * Modified by adding:
 * 	- Multiple target
 *  - NullOutputStream if the given OutputStream is given as null.
 */

/** Distribute the printed stream to many print streams */
public class TeePrintStream extends PrintStream {
	
	protected PrintStream[] Targets;

	/** Construct a TeePrintStream given an existing PrintStream and an opened OutputStream. */
	public TeePrintStream(OutputStream OUT, PrintStream ... pTargets) {
		this(OUT, true, pTargets);
	}
	/** Construct a TeePrintStream given an existing PrintStream and an opened OutputStream. */
	public TeePrintStream(OutputStream OUT, boolean pIsAutoFlush, PrintStream ... pTargets) {
		super((OUT == null)?NullOutputStream.Instance:OUT, pIsAutoFlush);
		Vector<PrintStream> Ts = new Vector<PrintStream>();
		if(pTargets != null )for(PrintStream T : pTargets) { if(T != null) Ts.add(T); }
		this.Targets = Ts.toArray(new PrintStream[Ts.size()]);
	}

	/**{@inheritDoc}*/ @Override
	public boolean checkError() {
		if(super.checkError()) return false;
		for(PrintStream Target : this.Targets) if(Target.checkError()) return false;
		return true;
	}
	
	/**{@inheritDoc}*/ @Override
	public void write(int x) {
		for(PrintStream Target : this.Targets) Target.write(x); // "write once;
	    super.write(x); // write somewhere else."
	}
	
	/**{@inheritDoc}*/ @Override
	public void write(byte[] x, int o, int l) {
		for(PrintStream Target : this.Targets) Target.write(x, o, l); // "write once;
		super.write(x, o, l); // write somewhere else."
	}
	
	/**{@inheritDoc}*/ @Override
	public void close() {
		for(PrintStream Target : this.Targets) Target.close();
		super.close();
	}
	
	/**{@inheritDoc}*/ @Override
	public void flush() {
		for(PrintStream Target : this.Targets) Target.flush();
		super.flush();
	}
}