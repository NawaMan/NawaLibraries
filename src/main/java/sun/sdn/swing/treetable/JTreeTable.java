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

import java.awt.*;

import javax.swing.*;
import javax.swing.table.*;
import javax.swing.tree.*;

/**
 * This example shows how to create a simple JTreeTable component, by using a JTree as a renderer 
 *   (and editor) for the cells in a particular column in the JTable.
 * 
 * @version %I% %G%
 * 
 * @author Philip Milne
 * @author Scott Violet
 */

public class JTreeTable extends JTable {
    
    private static final long serialVersionUID = 4998157045323616581L;
    
    protected TreeTableCellRenderer Tree;
    
    public JTreeTable(final TreeTableModel pTreeTableModel) {
        super();
        
        // Create the tree. It will be used as a renderer and editor.
        this.Tree = new TreeTableCellRenderer(pTreeTableModel);
        
        // Install a tableModel representing the visible rows in the tree.
        super.setModel(new TreeTableModelAdapter(pTreeTableModel, Tree));
        
        final JTreeTable This = this;
        
        // Force the JTable and JTree to share their row selection models.
        this.Tree.setSelectionModel(new DefaultTreeSelectionModel() {
            private static final long serialVersionUID = -6237281139785430028L;
            /* Static */ {
                This.setSelectionModel(listSelectionModel);
            }
        });
        // Make the tree and table row heights the same.
        this.Tree.setRowHeight(this.getRowHeight());
        
        // Install the tree editor renderer and editor.
        this.setDefaultRenderer(TreeTableModel.class, Tree);
        this.setDefaultEditor(TreeTableModel.class, new TreeTableCellEditor());
        
        this.setShowGrid(false);
        this.setIntercellSpacing(new Dimension(0, 0));
        
        //this.setFont(new Font("DejaVu Sans Mono", 0, 15));
        this.setFont(new Font("Monospaced", 0, 15));
    }
    
    /*
     * Workaround for BasicTableUI anomaly. Make sure the UI never tries to paint the editor. The
     *   UI currently uses different techniques to paint the renderers and editors and overriding 
     *   setBounds() below is not the right thing to do for an editor. Returning -1 for the editing
     *   row in this case, ensures the editor is never painted.
     */
    @Override
    public int getEditingRow() {
        return (this.getColumnClass(editingColumn) == TreeTableModel.class) ? -1 : editingRow;
    }
    
    // 
    // The renderer used to display the tree nodes, a JTree.
    //
    
    public class TreeTableCellRenderer extends JTree implements TableCellRenderer {
        
        private static final long serialVersionUID = -4533416083965540984L;
        
        protected int VisibleRow;
        
        public TreeTableCellRenderer(final TreeModel model) {
            super(model);
        }
        
        @Override public void setPreferredSize(final Dimension pPreferredSize) {
            super.setPreferredSize(
                    new Dimension(
                            Math.max(pPreferredSize.width, 200),
                            JTreeTable.this.getHeight()
                    )
            );
        }
        
        @Override public void setBounds(
                final int pX,
                final int pY,
                final int pW,
                final int pH) {
            super.setBounds(pX, 0, Math.max(pW, 200), JTreeTable.this.getHeight());
            super.setPreferredSize(new Dimension(Math.max(pW, 200), JTreeTable.this.getHeight()));
        }
        
        @Override public void paint(final Graphics g) {
            g.translate(0, -this.VisibleRow * this.getRowHeight());
            super.paint(g);
        }
        
        public Component getTableCellRendererComponent(
                final JTable  pTable,
                final Object  pValue,
                final boolean pIsSelected,
                final boolean pHasFocus,
                final int     pRow,
                final int     pColumn) {
            
            if (pIsSelected)
                this.setBackground(pTable.getSelectionBackground());
            else
                this.setBackground(pTable.getBackground());
            
            this.VisibleRow = pRow;
            return this;
        }
    }
    
    // 
    // The editor used to interact with tree nodes, a JTree.
    //
    
    public class TreeTableCellEditor extends AbstractCellEditor implements TableCellEditor {
        public Component getTableCellEditorComponent(
                final JTable  pTable,
                final Object  pValue,
                final boolean pIsSelected,
                final int     pRow,
                final int     pColomn) {
            return Tree;
        }
    }
    
}
