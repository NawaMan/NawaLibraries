package net.nawaman.swing;

import java.awt.Component;

import javax.swing.JPanel;

/**
 * A JPanel that does not allow its component to be changed
 **/
public class FixedPanel extends JPanel {

	private static final long serialVersionUID = -215157383457777638L;
	
	private boolean IsFixed = false;
	/** Set if the panel is to be fixed */
	protected void setFixed(boolean pIsFixed) {
		this.IsFixed = pIsFixed;
	}
	/** Checks if the panel is fixed */
	public boolean isFixed() {
		return this.IsFixed;
	}

    /**{@inheritDoc}*/ @Override
    protected void addImpl(Component comp, Object constraints, int index) {
    	if(this.isFixed()) return;
    	super.addImpl(comp, constraints, index);
    }

    /**{@inheritDoc}*/ @Override
    public void remove(int index) {
    	if(this.isFixed()) return;
    	super.remove(index);
    }

    /**{@inheritDoc}*/ @Override
    public void remove(Component comp) {
    	if(this.isFixed()) return;
    	super.remove(comp);
    }

    /**{@inheritDoc}*/ @Override
    public void removeAll() {
    	if(this.isFixed()) return;
    	super.removeAll();
    }

}
