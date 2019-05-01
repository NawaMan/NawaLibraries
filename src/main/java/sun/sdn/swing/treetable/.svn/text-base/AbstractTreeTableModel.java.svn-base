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

import javax.swing.event.*;
import javax.swing.tree.TreePath;

/**
 * An abstract implementation of the TreeTableModel interface, handling the list of listeners.
 *
 * @version %I% %G%
 * 
 * @author Philip Milne
 */

public abstract class AbstractTreeTableModel implements TreeTableModel {
    
    protected Object            Root;
    protected EventListenerList ListenerList = new EventListenerList();
    
    public AbstractTreeTableModel(final Object pRoot) {
        this.Root = pRoot;
    }
    
    //
    // Default implementations for methods in the TreeModel interface. 
    //
    
    public Object getRoot() {
        return Root;
    }
    
    public boolean isLeaf(final Object pNode) {
        return (this.getChildCount(pNode) == 0);
    }
    
    public void valueForPathChanged(
            final TreePath pPath,
            final Object   pNewValue) {
    }
    
    // This is not called in the JTree's default mode: use a naive implementation. 
    public int getIndexOfChild(
            final Object pParent,
            final Object pChild) {
        for (int i = 0; i < this.getChildCount(pParent); i++) {
            final Object aChild = this.getChild(pParent, i);
            if (aChild.equals(pChild)) {
                return i;
            }
        }
        return -1;
    }
    
    public void addTreeModelListener(final TreeModelListener pListener) {
        this.ListenerList.add(TreeModelListener.class, pListener);
    }
    
    public void removeTreeModelListener(final TreeModelListener pListener) {
        this.ListenerList.remove(TreeModelListener.class, pListener);
    }
    
    /**
     * Notify all listeners that have registered interest for notification on this event type. The
     *   event instance is lazily created using the parameters passed into the fire method.
     * @see EventListenerList
     */
    protected void fireTreeNodesChanged(
            final Object   pSource,
            final Object[] pPath,
            final int[]    pChildIndices,
            final Object[] pChildren) {
        
        // Guaranteed to return a non-null array
        final Object[] aListeners = ListenerList.getListenerList();
        
        TreeModelEvent e = null;
        // Process the listeners last to first, notifying
        // those that are interested in this event
        for (int i = aListeners.length - 2; i >= 0; i -= 2) {
            if (aListeners[i] == TreeModelListener.class) {
                // Lazily create the event:
                if (e == null)
                    e = new TreeModelEvent(pSource, pPath, pChildIndices, pChildren);
                
                final TreeModelListener aNextListener = (TreeModelListener)aListeners[i + 1];
                aNextListener.treeNodesChanged(e);
            }
        }
    }
    
    /**
     * Notify all listeners that have registered interest for notification on this event type. The
     *   event instance is lazily created using the parameters passed into the fire method.
     * @see EventListenerList
     */
    protected void fireTreeNodesInserted(
            final Object   pSource,
            final Object[] pPath,
            final int[]    pChildIndices,
            final Object[] pChildren) {
        
        // Guaranteed to return a non-null array
        final Object[] aListeners = this.ListenerList.getListenerList();
        TreeModelEvent e = null;
        // Process the listeners last to first, notifying
        // those that are interested in this event
        for (int i = aListeners.length - 2; i >= 0; i -= 2) {
            if (aListeners[i] == TreeModelListener.class) {
                // Lazily create the event:
                if (e == null)
                    e = new TreeModelEvent(pSource, pPath, pChildIndices, pChildren);
                
                final TreeModelListener aNextListener = (TreeModelListener)aListeners[i + 1];
                aNextListener.treeNodesInserted(e);
            }
        }
    }
    
    /**
     * Notify all listeners that have registered interest for notification on this event type. The
     *   event instance is lazily created using the parameters passed into the fire method.
     * @see EventListenerList
     */
    protected void fireTreeNodesRemoved(
            final Object   pSource,
            final Object[] pPath,
            final int[]    pChildIndices,
            final Object[] pChildren) {
        
        // Guaranteed to return a non-null array
        final Object[] aListeners = this.ListenerList.getListenerList();
        TreeModelEvent e = null;
        // Process the listeners last to first, notifying
        // those that are interested in this event
        for (int i = aListeners.length - 2; i >= 0; i -= 2) {
            if (aListeners[i] == TreeModelListener.class) {
                // Lazily create the event:
                if (e == null)
                    e = new TreeModelEvent(pSource, pPath, pChildIndices, pChildren);
                
                final TreeModelListener aNextListener = (TreeModelListener)aListeners[i + 1];
                aNextListener.treeNodesRemoved(e);
            }
        }
    }
    
    /**
     * Notify all listeners that have registered interest for notification on this event type. The
     *   event instance is lazily created using the parameters passed into the fire method.
     * @see EventListenerList
     */
    protected void fireTreeStructureChanged(
            final Object   pSource,
            final Object[] pPath,
            final int[]    pChildIndices,
            final Object[] pChildren) {
        
        // Guaranteed to return a non-null array
        final Object[] aListeners = this.ListenerList.getListenerList();
        TreeModelEvent e = null;
        // Process the listeners last to first, notifying
        // those that are interested in this event
        for (int i = aListeners.length - 2; i >= 0; i -= 2) {
            if (aListeners[i] == TreeModelListener.class) {
                // Lazily create the event:
                if (e == null)
                    e = new TreeModelEvent(pSource, pPath, pChildIndices, pChildren);
                
                final TreeModelListener aNextListener = (TreeModelListener)aListeners[i + 1];
                aNextListener.treeStructureChanged(e);
            }
        }
    }
    
    // Default implementations for methods in the TreeTableModel interface. ------------------------ 
    
    public Class<?> getColumnClass(final int pColumn) {
        return Object.class;
    }
    
    /**
     * By default, make the column with the Tree in it the only editable one. Making this column
     * editable causes the JTable to forward mouse and keyboard events in the Tree column to the
     * underlying JTree. 
     */
    public boolean isCellEditable(
            final Object pNode,
            final int    pColumn) {
        return (this.getColumnClass(pColumn) == TreeTableModel.class);
    }
    
    public void setValueAt(
            final Object pValue,
            final Object pNode,
            final int    pColumn) {
    }
    
    // Left to be implemented in the subclass:
    
    /* 
     *   public Object getChild      (final Object pParent, final int pIndex)
     *   public int    getChildCount (final Object pParent) 
     *   public int    getColumnCount() 
     *   public String getColumnName (final Object pNode, final int pColumn)  
     *   public Object getValueAt    (final Object pNode, final int pColumn) 
     */

}
