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

package sun.sdn.swing.treetable;

import javax.swing.tree.TreePath;

import javax.swing.JTree;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.table.AbstractTableModel;

/**
 * This is a wrapper class takes a TreeTableModel and implements the table model interface. The
 *   implementation is trivial, with all of the event dispatching support provided by the
 *   superclass: the AbstractTableModel.
 * 
 * @version %I% %G%
 * 
 * @author Philip Milne
 * @author Scott Violet
 */

public class TreeTableModelAdapter extends AbstractTableModel {
    
    private static final long serialVersionUID = 9184649604812742836L;
    
    final JTree          Tree;
    final TreeTableModel TreeTableModel;
    
    public TreeTableModelAdapter(
            final TreeTableModel pTreeTableModel,
            final JTree          pTree) {
        this.Tree = pTree;
        this.TreeTableModel = pTreeTableModel;
        
        pTree.addTreeExpansionListener(new TreeExpansionListener() {
            // Don't use fireTableRowsInserted() here;
            // the selection model would get updated twice.
            public void treeExpanded(TreeExpansionEvent event) {
                fireTableDataChanged();
            }
            
            public void treeCollapsed(TreeExpansionEvent event) {
                fireTableDataChanged();
            }
        });
    }
    
    public TreeTableModel getTreeTableModel() {
        return this.TreeTableModel;
    }
    
    // Wrappers, implementing TableModel interface.
    
    public int getColumnCount() {
        return this.TreeTableModel.getColumnCount();
    }
    
    @Override
    public String getColumnName(final int pColumn) {
        return this.TreeTableModel.getColumnName(pColumn);
    }
    
    @Override
    public Class<?> getColumnClass(final int pColumn) {
        return this.TreeTableModel.getColumnClass(pColumn);
    }
    
    public int getRowCount() {
        return this.Tree.getRowCount();
    }
    
    protected Object nodeForRow(final int pRow) {
        final TreePath aTreePath = this.Tree.getPathForRow(pRow);
        return aTreePath.getLastPathComponent();
    }
    
    public Object getValueAt(
            final int pRow,
            final int pColumn) {
        final Object aNode = this.nodeForRow(pRow);
        return this.TreeTableModel.getValueAt(aNode, pColumn);
    }
    
    @Override
    public boolean isCellEditable(
            final int pRow,
            final int pColumn) {
        final Object aNode = this.nodeForRow(pRow);
        return this.TreeTableModel.isCellEditable(aNode, pColumn);
    }
    
    @Override
    public void setValueAt(
            final Object pValue,
            final int    pRow,
            final int    pColumn) {
        final Object aNode = this.nodeForRow(pRow);
        this.TreeTableModel.setValueAt(pValue, aNode, pColumn);
    }
}
