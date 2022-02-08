package net.nawaman.swing.textpanel;

import java.awt.Color;

import javax.swing.border.LineBorder;

/** Block border is a line border that can its color can be changed. */
public class BlockBorder extends LineBorder {

	private static final long serialVersionUID = 5174149401851497207L;

	/** Create a new block border */
	public BlockBorder(Color color) { super(color, 1); }

	/** Change to color of the block border */
	public void setBorderColor(Color aLineColor) {
		this.lineColor = aLineColor;
	}

}
