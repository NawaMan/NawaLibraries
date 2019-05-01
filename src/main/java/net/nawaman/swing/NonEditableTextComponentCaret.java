/*----------------------------------------------------------------------------------------------------------------------
 * Copyright (C) 2008-2019 Nawapunth Manusitthipol. Implements with and for Sun Java 1.6 JDK.
 *----------------------------------------------------------------------------------------------------------------------
 * LICENSE:
 * 
 * This file is part of Nawa's Library.
 * 
 * The project is a free software; you can redistribute it and/or modify it under the SIMILAR terms of the GNU General
 * Public License as published by the Free Software Foundation; either version 2 of the License, or any later version.
 * You are only required to inform me about your modification and redistribution as or as part of commercial software
 * package. You can inform me via nawaman<at>gmail<dot>com.
 * 
 * The project is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the 
 * implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for
 * more details.
 * ---------------------------------------------------------------------------------------------------------------------
 */

package net.nawaman.swing;

import java.util.Timer;
import java.util.TimerTask;

import javax.swing.plaf.UIResource;
import javax.swing.text.DefaultCaret;

/**
 * This class is a copy of javax.swing.text.DefaultCaret I only change it to allow it to be display in the
 * non-editable text component
 */
public class NonEditableTextComponentCaret extends DefaultCaret implements UIResource {

	private static final long serialVersionUID = 2953890772501058583L;
	
	private boolean   IsBlindOff = false;
	private TimerTask Blinker    = null;
	private Timer     Timer      = null;
	
    /** Constructs a NonEditableTextComponent caret. */
    public NonEditableTextComponentCaret() {
    	super();
    	this.setBlinkRate(0);
    }
    
    /**{@inheritDoc}*/ @Override
    public void setBlinkRate(int rate) {
    	super.setBlinkRate(rate);
    	
    	// Do nothing if the value is not changed
    	if((this.getBlinkRate() == rate) && (this.Timer != null)) return;
    	
    	// Cancel the old task and remove the the timer when unneeded
		if(this.Timer != null) {
			try {
				this.Timer.cancel();
				this.Timer.purge();
			} catch (Exception e) {}

	    	if(rate <= 0) {
	    		if(this.Timer != null) this.Timer = null;
	    		return;
	    	}
		} else {
			if(rate == 0) return;
			this.Timer = new Timer();
		}
		
    	
		// Create the blinker if needed
    	if(this.Blinker == null) {
    		final NonEditableTextComponentCaret This = this;
    		this.Blinker = new TimerTask() {
    			/**{@inheritDoc}*/ @Override
    			public void run() {
    				This.IsBlindOff = (This.getBlinkRate() == 0)?true:!This.IsBlindOff;
    				This.repaint();
    			}
    		};
    	}
    	
    	// Schedule the blinker
    	this.Timer.schedule(this.Blinker, rate, rate);
    }
	
    // Forcefully visible when the component owns the focus or the selection is being shown
    /**{@inheritDoc}*/ @Override
    public void setVisible(boolean isVisible) {
		if(!isVisible && this.isSelectionVisible() && this.getComponent().isFocusOwner()) return;
		super.setVisible(isVisible);
	}
    // The visibility of the caret depends on whether or not the selection is visible
    /**{@inheritDoc}*/ @Override
    public boolean isVisible() {
		return this.isSelectionVisible() && this.IsBlindOff;
	}
	
}