package net.nawaman.swing;

import java.awt.Component;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

import javax.swing.JOptionPane;

/** InputStream from JOptionPane */
public class JOptionPaneInputStream extends InputStream {

	public JOptionPaneInputStream() {
		this(null);
	}
	public JOptionPaneInputStream(Component pParent) {
		this.Parent = pParent;
	}
	
	Component             Parent = null;
	ByteArrayInputStream  BAIS   = null;

	
    /** {@inheritDoc} */ @Override public int read() {
    	if(this.BAIS == null) {
    		String I = JOptionPane.showInputDialog(this.Parent, "Input a text: ");
    		if(I != null) {
	    		ByteArrayOutputStream BAOS = new ByteArrayOutputStream();
	    		PrintStream           PS   = new PrintStream(BAOS);
	    		PS.print(I);
	    		PS.print("\n");
	    		this.BAIS =  new ByteArrayInputStream(BAOS.toByteArray());
    		}
    	}
    	return this.BAIS.read();
    }

    /** {@inheritDoc} */ @Override public int available() {
    	return this.BAIS.available();
    }

    /** {@inheritDoc} */ @Override public void close() throws IOException {
    	this.BAIS.close();
    	this.BAIS = null;
    }

} 