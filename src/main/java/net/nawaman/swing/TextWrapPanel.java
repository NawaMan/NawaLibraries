package net.nawaman.swing;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.text.JTextComponent;

/**
 * Panel that will helps making the text panel wrapped enabled/disabled.
 * 
 * This is done by adjusting the size of the inner panel and turning the horizontal scroll bar on/off.
 * If the size of the inner panel changes follows the preferred size of the text component and the horizontal bar is on,
 *    the component will look like it is on a no-wrap mode.
 * If the size of the inner panel changes follows the scroll pane view port and the horizontal bar is off, the component
 *    will look like it is on a wrap mode.
 **/
public class TextWrapPanel extends JScrollPane {

	private static final long serialVersionUID = -1437886380868517061L;

	/** The inner panel of this panel */
	static class InnerPanel extends FixedPanel {
		
		private static final long serialVersionUID = -5308257118422152798L;

		InnerPanel(JTextComponent pTextComponent) {
			this.setLayout(new BorderLayout());
			this.add(pTextComponent);
			this.setFixed(true);
		}

		boolean IsToWrap = false;
		
		/**{@inheritDoc}*/ @Override
		public void setSize(Dimension d) {
			if(this.IsToWrap && (d.width > this.getParent().getSize().width))
				d.width = this.getParent().getSize().width;

			super.setSize(d);
		}
	}
	
	/** Constructs a TextWrapPanel */
	public TextWrapPanel(JTextComponent pTextComponent) {
		super(new InnerPanel(pTextComponent));
		this.InnerPanel = (InnerPanel)((JViewport)this.getComponent(0)).getComponent(0);
	}
	
	InnerPanel InnerPanel = null;
		
	/** Set the text wrap behavior */
	public void setTextWrap(boolean pIsToWrap) {
		this.InnerPanel.IsToWrap = pIsToWrap;
		
		if(this.InnerPanel.IsToWrap)
			 this.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		else this.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	}
	
	/** Checks if text wrap is enabled */
	public boolean isTextWrapped() {
		return this.InnerPanel.IsToWrap;
	}
}
