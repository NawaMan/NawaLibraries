package net.nawaman.swing.text;

import java.awt.Dimension;
import java.awt.Graphics;

import net.nawaman.swing.FixedPanel;

/** Panel that will replace a tab - simulate tab (adjust width appropriately) */
public class TabElement extends FixedPanel {

	private static final long serialVersionUID = -7138614251252322031L;
	
	/** The adjustment for the tab position calculation - why 3? I don't know */
	// Already try the insets but no luck so use 3 for the moment.
	static final int Adjustment      =   3;
	static final int TabHeight       =   1;
	static final int InitialTabWidth = 100;
	
	TabElement() {
		this.setFixed(true);
		
		try {
			this.setSize(         new Dimension(InitialTabWidth, TabHeight));
			this.setPreferredSize(new Dimension(InitialTabWidth, TabHeight));
			this.setMaximumSize(  new Dimension(InitialTabWidth, TabHeight));
		} catch(Exception E) {}
	}
	
	/**{@inheritDoc}*/ @Override
	public void paint(Graphics g) {
		HTMLOutputPane HOP = ((HTMLOutputComponent)(this.getParent().getParent())).HOP;
		
		int TabWidth = HOP.getTabWidth();
		
		int X = this.getParent().getX();
		int W = ((X + TabWidth) / TabWidth)*TabWidth - X + 3;
		if(W == this.getWidth()) return;

		this.setSize(         new Dimension(W, TabHeight));
		this.setPreferredSize(new Dimension(W, TabHeight));
		this.setMaximumSize(  new Dimension(W, TabHeight));
		
		// Mark so that we can be sure the text component repainted.
		HOP.ToForceRedraw = true;
	} 
}