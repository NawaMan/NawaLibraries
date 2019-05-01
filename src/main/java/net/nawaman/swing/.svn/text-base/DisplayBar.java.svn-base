package net.nawaman.swing;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.LayoutManager;

import javax.swing.JComponent;

/** A bar for displaying information */
public class DisplayBar extends FixedPanel {
	
	private static final long serialVersionUID = -2821829515599805345L;
	
	public DisplayBar(JComponent pLeft, JComponent pCenter, JComponent pRight) {
		this.setLayout(new BorderLayout());
		if(pLeft   != null) this.add(pLeft,   BorderLayout.LINE_START);
		if(pCenter != null) this.add(pCenter, BorderLayout.CENTER);
		if(pRight  != null) this.add(pRight,  BorderLayout.LINE_END);
		this.setFixed(true);
	}
	
	/**{@inheritDoc}*/ @Override final public void setLayout(LayoutManager pLayout) {
		if(!(pLayout instanceof BorderLayout)) return;
		super.setLayout(pLayout);
	} 
	
	protected void setComponent(JComponent pComponent, String pConstraint) {
		BorderLayout Layout = (BorderLayout)this.getLayout();
		Component C = Layout.getLayoutComponent(this, pConstraint);
		
		if(C          != null) this.remove(C);
		if(pComponent != null) this.add(pComponent, pConstraint);
		else {
			this.setVisible(
				((Layout.getLayoutComponent(BorderLayout.LINE_START) == null) &&
				(Layout.getLayoutComponent(BorderLayout.CENTER)      == null) &&
				(Layout.getLayoutComponent(BorderLayout.LINE_END)    == null))
			);
		}
	}

	public void setLeft(  JComponent pLeft)   { this.setComponent(pLeft,   BorderLayout.LINE_START); }
	public void setCenter(JComponent pCenter) { this.setComponent(pCenter, BorderLayout.CENTER);     }
	public void setRight( JComponent pRight)  { this.setComponent(pRight,  BorderLayout.LINE_END);   }
	
	public JComponent getLeft()   { return (JComponent)((BorderLayout)this.getLayout()).getLayoutComponent(BorderLayout.LINE_START); }
	public JComponent getCenter() { return (JComponent)((BorderLayout)this.getLayout()).getLayoutComponent(BorderLayout.CENTER);     }
	public JComponent getRight()  { return (JComponent)((BorderLayout)this.getLayout()).getLayoutComponent(BorderLayout.LINE_END);   }
	
}
