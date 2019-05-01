package net.nawaman.io;

import java.io.OutputStream;
import java.io.PrintStream;

/**
 * A PrintStream that transforms the printing text before actually prints it.
 * 
 * @author Nawapunth Manusitthipol (https://github.com/NawaMan)
 **/
public class FilteredPrintStream extends TeePrintStream {
	
	public FilteredPrintStream(OutputStream pOut, boolean pIsAutoFlush, PrintStream ... pTargets) {
		super((pOut == null)?NullOutputStream.Instance:pOut, pIsAutoFlush, pTargets);
	}

	/** Do the filtering */
	protected String filter(String S) {
		return (S == null)?"null":S;
	}
	
	/** Write the filtered string */
	protected void write(String S, boolean isNewLine) {
		this.writeRaw(this.filter(S + (isNewLine?"\n":"")));
	}
	
	/** Write the raw text into the output string */
	final protected void writeRaw(String S) {
		byte[] Bs = S.getBytes();
		super.write(Bs, 0, Bs.length);
	}

	/**{@inheritDoc}*/ @Override
	final public void write(int x) {
		this.write("" + (char)x, false);
	}	
	/**{@inheritDoc}*/ @Override 
	final public void write(byte[] pBytes, int pOffset, int pLength) {
		this.write(new String(pBytes, pOffset, pLength), false);
	}
	
	/* Methods that do not terminate lines */
	
	/**{@inheritDoc}*/ @Override final public void print(boolean b)  { this.write(b ? "true" : "false", false); }
	/**{@inheritDoc}*/ @Override final public void print(char c)     { this.write(String.valueOf(c),    false); }
	/**{@inheritDoc}*/ @Override final public void print(int i)      { this.write(String.valueOf(i),    false); }
	/**{@inheritDoc}*/ @Override final public void print(long l)     { this.write(String.valueOf(l),    false); }
	/**{@inheritDoc}*/ @Override final public void print(float f)    { this.write(String.valueOf(f),    false); }
	/**{@inheritDoc}*/ @Override final public void print(double d)   { this.write(String.valueOf(d),    false); }
	/**{@inheritDoc}*/ @Override final public void print(char s[])   { this.write(new String(s),        false); }
	/**{@inheritDoc}*/ @Override final public void print(String s)   { this.write((s == null)?"null":s, false); }
	/**{@inheritDoc}*/ @Override final public void print(Object obj) { this.write(String.valueOf(obj),  false); }

	/**{@inheritDoc}*/ @Override final public void println()           { this.write("", true);                   }
	/**{@inheritDoc}*/ @Override final public void println(boolean b)  { this.write(b ? "true" : "false", true); }
	/**{@inheritDoc}*/ @Override final public void println(char c)     { this.write(String.valueOf(c)   , true); }
	/**{@inheritDoc}*/ @Override final public void println(int i)      { this.write(String.valueOf(i)   , true); }
	/**{@inheritDoc}*/ @Override final public void println(long l)     { this.write(String.valueOf(l)   , true); }
	/**{@inheritDoc}*/ @Override final public void println(float f)    { this.write(String.valueOf(f)   , true); }
	/**{@inheritDoc}*/ @Override final public void println(double d)   { this.write(String.valueOf(d)   , true); }
	/**{@inheritDoc}*/ @Override final public void println(char s[])   { this.write(new String(s)       , true); }
	/**{@inheritDoc}*/ @Override final public void println(String s)   { this.write((s == null)?"null":s, true); }
	/**{@inheritDoc}*/ @Override final public void println(Object obj) { this.write(String.valueOf(obj) , true); }

	/**{@inheritDoc}*/ @Override
	final public PrintStream append(CharSequence csq, int start, int end) {
		CharSequence cs = (csq == null ? "null" : csq);
		this.write(cs.subSequence(start, end).toString(), false);
		return this;
	}
	
	// Others ----------------------------------------------------------------------------------------------------------
	
	/**{@inheritDoc}*/ @Override final public    boolean checkError() { return false; }
	/**{@inheritDoc}*/ @Override final protected void    setError()   { } 
	/**{@inheritDoc}*/ @Override final public    void    close()      { }
	/**{@inheritDoc}*/ @Override final public    void    flush()      { }

}
