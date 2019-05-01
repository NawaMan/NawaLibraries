package sun.sdn.swing.treetable;

/*
 * %W% %E%
 *
 * Copyright 1997, 1998 Sun Microsystems, Inc. All Rights Reserved.
 * 
 * Redistribution and use in source and binary forms, with or
 * without modification, are permitted provided that the following
 * conditions are met:
 * 
 * - Redistributions of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer. 
 *   
 * - Redistribution in binary form must reproduce the above
 *   copyright notice, this list of conditions and the following
 *   disclaimer in the documentation and/or other materials
 *   provided with the distribution. 
 *   
 * Neither the name of Sun Microsystems, Inc. or the names of
 * contributors may be used to endorse or promote products derived
 * from this software without specific prior written permission.  
 * 
 * This software is provided "AS IS," without a warranty of any
 * kind. ALL EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND
 * WARRANTIES, INCLUDING ANY IMPLIED WARRANTY OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE OR NON-INFRINGEMENT, ARE HEREBY
 * EXCLUDED. SUN AND ITS LICENSORS SHALL NOT BE LIABLE FOR ANY
 * DAMAGES OR LIABILITIES SUFFERED BY LICENSEE AS A RESULT OF OR
 * RELATING TO USE, MODIFICATION OR DISTRIBUTION OF THIS SOFTWARE OR
 * ITS DERIVATIVES. IN NO EVENT WILL SUN OR ITS LICENSORS BE LIABLE 
 * FOR ANY LOST REVENUE, PROFIT OR DATA, OR FOR DIRECT, INDIRECT,   
 * SPECIAL, CONSEQUENTIAL, INCIDENTAL OR PUNITIVE DAMAGES, HOWEVER  
 * CAUSED AND REGARDLESS OF THE THEORY OF LIABILITY, ARISING OUT OF 
 * THE USE OF OR INABILITY TO USE THIS SOFTWARE, EVEN IF SUN HAS 
 * BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGES.
 * 
 * You acknowledge that this software is not designed, licensed or
 * intended for use in the design, construction, operation or
 * maintenance of any nuclear facility.
 */

import javax.swing.*;
import javax.swing.event.*;
import java.util.EventObject;

/**
 * @version %I% %G% 
 * 
 * A base class for CellEditors, providing default implementations for all methods in the CellEditor
 *   interface and support for managing a series of listeners. 
 *
 * @author Philip Milne
 */

public class AbstractCellEditor implements CellEditor {
    
    protected EventListenerList ListenerList = new EventListenerList();
    
    public Object getCellEditorValue() {
        return null;
    }
    
    public boolean isCellEditable(EventObject pEvent) {
        return true;
    }
    
    public boolean shouldSelectCell(EventObject pEvent) {
        return false;
    }
    
    public boolean stopCellEditing() {
        return true;
    }
    
    public void cancelCellEditing() {
    }
    
    public void addCellEditorListener(final CellEditorListener pListener) {
        this.ListenerList.add(CellEditorListener.class, pListener);
    }
    
    public void removeCellEditorListener(final CellEditorListener pListener) {
        this.ListenerList.remove(CellEditorListener.class, pListener);
    }
    
    /**
     * Notify all listeners that have registered interest for notification on this event type.
     * @see EventListenerList
     */
    protected void fireEditingStopped() {
        // Guaranteed to return a non-null array
        final Object[] aListeners = this.ListenerList.getListenerList();
        // Process the listeners last to first, notifying
        // those that are interested in this event
        for (int i = aListeners.length - 2; i >= 0; i -= 2) {
            if (aListeners[i] == CellEditorListener.class) {
                final CellEditorListener aNextListener = (CellEditorListener) aListeners[i + 1];
                final ChangeEvent        aChageEvent   = new ChangeEvent(this);
                aNextListener.editingStopped(aChageEvent);
            }
        }
    }
    
    /**
     * Notify all listeners that have registered interest for notification on this event type.
     * @see EventListenerList
     */
    protected void fireEditingCanceled() {
        // Guaranteed to return a non-null array
        final Object[] aListeners = this.ListenerList.getListenerList();
        // Process the listeners last to first, notifying
        // those that are interested in this event
        for (int i = aListeners.length - 2; i >= 0; i -= 2) {
            if (aListeners[i] == CellEditorListener.class) {
                final CellEditorListener aNextListener = (CellEditorListener) aListeners[i + 1];
                final ChangeEvent        aChageEvent   = new ChangeEvent(this);
                aNextListener.editingCanceled(aChageEvent);
            }
        }
    }
}
